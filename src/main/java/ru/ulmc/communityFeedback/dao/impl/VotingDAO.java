package ru.ulmc.communityFeedback.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.ulmc.communityFeedback.dao.IVotingDAO;
import ru.ulmc.communityFeedback.dao.entity.Option;
import ru.ulmc.communityFeedback.dao.entity.Topic;
import ru.ulmc.communityFeedback.dao.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VotingDAO implements IVotingDAO {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Override
    public void initDataBase() {
        Statement stmt = null;
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("connection successful!");

            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS USER " +
                    "('ID' LONG PRIMARY KEY NOT NULL," +
                    " 'NAME' TEXT NOT NULL," +
                    "'IS_FINISHED' INT NOT NULL);";
            sql += "CREATE TABLE IF NOT EXISTS Candidate" +
                    "('ID' LONG PRIMARY KEY NOT NULL, " +
                    " 'USERNAME' TEXT," +
                    " 'DISPLAYED_NAME' TEXT NOT NULL," +
                    "'ABOUT' TEXT NOT NULL," +
                    "NOMINATION_ID LONG NOT NULL);";
            sql += "CREATE TABLE IF NOT EXISTS NOMINATION" +
                    "('ID' LONG PRIMARY KEY NOT NULL, " +
                    " 'NAME' TEXT NOT NULL," +
                    "'ORDER_VALUE' INT NOT NULL);";
            sql += "CREATE TABLE IF NOT EXISTS USER_TO_NOMINATION" +
                    "('USER_ID' LONG NOT NULL, " +
                    " 'NOMINATION_ID' LONG NOT NULL," +
                    "'CANDIDATE_ID' INT NOT NULL);";
            sql += "CREATE TABLE IF NOT EXISTS APP_STATE" +
                    "('IS_ENABLED' INT NOT NULL, " +
                    " 'MESSAGE' TEXT NOT NULL);";
            stmt.executeUpdate(sql);
            stmt.close();
            createNomination(0L, "Генератор идей", 1);
            createNomination(1L, "Золотые руки", 2);
            createNomination(2L, "Флагман - 2015", 3);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Tables created successfully");
    }

    public void createNomination(Long id, String name, int order) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT OR REPLACE INTO NOMINATION (ID, NAME, ORDER_VALUE) VALUES (?, ?, ?);")) {
                connection.setAutoCommit(false);
                System.out.println("Opened database successfully");
                stmt.setLong(1, id);
                stmt.setString(2, name);
                stmt.setInt(3, order);
                stmt.executeUpdate();
                connection.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void createCandidate(String username, String displayedName, String about, Long nominationID) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO CANDIDATE (ID, USERNAME, DISPLAYED_NAME, ABOUT, NOMINATION_ID) VALUES (?, ?, ?, ?, ?);")) {
                connection.setAutoCommit(true);
                stmt.setLong(1, getMaxCandidateID());
                stmt.setString(2, username);
                stmt.setString(3, displayedName);
                stmt.setString(4, about);
                stmt.setLong(5, nominationID);
                stmt.executeUpdate();
                connection.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public Map<Topic, List<Option>> getNominationsWithCandidates() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT c.id as candidate_ID," +
                            "c.username as candidate_name, " +
                            "c.about as about_candidate, " +
                            "c.displayed_name as displayed_name, " +
                            "n.name as nomination_name, " +
                            "n.ORDER_VALUE as nomination_order, " +
                            "n.id as nomination_id " +
                            "FROM CANDIDATE c LEFT JOIN NOMINATION n ON c.NOMINATION_ID = n.id " +
                            "ORDER BY RANDOM();")) {
                connection.setAutoCommit(true);
                Map<Topic, List<Option>> result = new HashMap<>();
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Topic topic = new Topic(rs.getLong("nomination_id"),
                                rs.getString("nomination_name"),
                                rs.getInt("nomination_order"));
                        Option option = new Option(rs.getString("about_candidate"),
                                rs.getString("candidate_name"),
                                rs.getString("displayed_name"),
                                rs.getLong("nomination_id"));
                        option.setId(rs.getLong("candidate_ID"));
                        if (result.get(topic) != null) {
                            result.get(topic).add(option);
                        } else {
                            List<Option> cands = new ArrayList<>();
                            cands.add(option);
                            result.put(topic, cands);
                        }
                    }
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    public Map<Topic, List<Option>> getNominationsWithCandidatesAndVotes() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT c.id as candidate_ID,\n" +
                            " c.username as candidate_name,\n" +
                            " c.about as about_candidate, \n" +
                            " c.displayed_name as displayed_name, \n" +
                            " n.name as nomination_name, \n" +
                            " n.ORDER_VALUE as nomination_order,\n" +
                            " (select count(u.USER_ID) from USER_TO_NOMINATION u" +
                            " LEFT JOIN USER k on k.id = u.user_id " +
                            " WHERE u.NOMINATION_ID = n.id and u.CANDIDATE_ID = c.id and k.is_finished = 1)  as vote_count_finished, " +
                            " (select count(u.USER_ID) from USER_TO_NOMINATION u " +
                            " WHERE u.NOMINATION_ID = n.id and u.CANDIDATE_ID = c.id) as vote_count, " +
                            " n.id as nomination_id \n" +
                            " FROM CANDIDATE c \n" +
                            " LEFT JOIN NOMINATION n ON c.NOMINATION_ID = n.id" +
                            " GROUP BY candidate_ID\n" +
                            " ORDER BY vote_count DESC;")) {
                connection.setAutoCommit(true);
                Map<Topic, List<Option>> result = new HashMap<>();
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Topic topic = new Topic(rs.getLong("nomination_id"),
                                rs.getString("nomination_name"),
                                rs.getInt("nomination_order"));
                        Option option = new Option(rs.getString("about_candidate"),
                                rs.getString("candidate_name"),
                                rs.getString("displayed_name"),
                                rs.getLong("nomination_id"));
                        option.setId(rs.getLong("candidate_ID"));
                        option.setVoteCount(rs.getInt("vote_count"));
                        option.setVoteCountFinished(rs.getInt("vote_count_finished"));
                        if (result.get(topic) != null) {
                            result.get(topic).add(option);
                        } else {
                            List<Option> cands = new ArrayList<>();
                            cands.add(option);
                            result.put(topic, cands);
                        }
                    }
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    public List<User> getUsersVotingStatus() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT u.id, u.name, u.is_finished as candidate_ID" +
                            " FROM User u \n" +
                            " ORDER BY name;")) {
                connection.setAutoCommit(true);
                List<User> result = new ArrayList<>();
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        User user = new User();
                        user.setId(rs.getLong(1));
                        user.setUsername(rs.getString(2));
                        user.setIsFinished(rs.getInt(3) == 1);
                        result.add(user);
                    }
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    public void createNewUserIfNotExist(String username) {
        if (findUser(username) != null) {
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO User (ID, NAME, IS_FINISHED) VALUES (?, ?, 0);")) {
                connection.setAutoCommit(false);
                stmt.setLong(1, getMaxUserID());
                stmt.setString(2, username);
                stmt.executeUpdate();
                connection.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void changeUserVoteStatus(String username, boolean voteFinished) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement("UPDATE User SET IS_FINISHED = ? where name = ?;")) {
                connection.setAutoCommit(false);
                stmt.setLong(1, voteFinished ? 1 : 0);
                stmt.setString(2, username);
                stmt.executeUpdate();
                connection.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public Long vote(String username, Long nominationID, Long candidateID) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            Long userID = findUser(username);
            try (PreparedStatement clearStatement = connection.prepareStatement(
                    "DELETE FROM USER_TO_NOMINATION " +
                            "WHERE USER_ID = ? AND NOMINATION_ID = ?;")) {
                clearStatement.setLong(1, userID);
                clearStatement.setLong(2, nominationID);
                clearStatement.executeUpdate();
                connection.commit();
            }
            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO USER_TO_NOMINATION (USER_ID, NOMINATION_ID, CANDIDATE_ID) VALUES (?, ?, ?);")) {
                stmt.setLong(1, userID);
                stmt.setLong(2, nominationID);
                stmt.setLong(3, candidateID);
                stmt.executeUpdate();
                connection.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    public Long cancelVote(String username, Long nominationID, Long candidateID) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            Long userID = findUser(username);
            try (PreparedStatement clearStatement = connection.prepareStatement(
                    "DELETE FROM USER_TO_NOMINATION " +
                            "WHERE USER_ID = ? AND NOMINATION_ID = ?;")) {
                clearStatement.setLong(1, userID);
                clearStatement.setLong(2, nominationID);
                clearStatement.executeUpdate();
                connection.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    public Long deleteCandidate(Long nominationID, Long candidateID) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement clearStatement = connection.prepareStatement(
                    "DELETE FROM CANDIDATE " +
                            "WHERE ID = ? AND NOMINATION_ID = ?;")) {
                clearStatement.setLong(1, candidateID);
                clearStatement.setLong(2, nominationID);
                clearStatement.executeUpdate();
                connection.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    public Map<Long, Long> findVotes(String username) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            Long userID = findUser(username);
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM USER_TO_NOMINATION WHERE USER_ID = ?;")) {
                stmt.setLong(1, userID);
                ResultSet rs = stmt.executeQuery();
                Map<Long, Long> result = new HashMap<>();
                while (rs.next()) {
                    result.put(rs.getLong("NOMINATION_ID"), rs.getLong("CANDIDATE_ID"));
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    public boolean isUserFinishedVoting(String username) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT IS_FINISHED FROM USER WHERE NAME = ?;")) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                boolean isFinished = false;
                while (rs.next()) {
                    isFinished = rs.getLong("IS_FINISHED") == 1;
                }
                return isFinished;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    public Long findUser(String username) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement("SELECT ID FROM User where name = ?")) {
                connection.setAutoCommit(false);
                System.out.println("Opened database successfully");
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                Long id = null;
                if (rs.next()) {
                    id = rs.getLong("ID");
                }
                rs.close();
                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    public Long getMaxUserID() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement("SELECT Max(id) as MAX_ID FROM User;")) {
                connection.setAutoCommit(false);
                System.out.println("Opened database successfully");
                ResultSet rs = stmt.executeQuery();
                Long id = 0L;
                if (rs.next()) {
                    id = rs.getLong("MAX_ID") + 1;
                }
                rs.close();
                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return 0L;
    }

    public Long getMaxCandidateID() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement("SELECT Max(id) as MAX_ID FROM CANDIDATE;")) {
                connection.setAutoCommit(false);
                System.out.println("Opened database successfully");
                ResultSet rs = stmt.executeQuery();
                Long id = 0L;
                if (rs.next()) {
                    id = rs.getLong("MAX_ID") + 1;
                }
                rs.close();
                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return 0L;
    }
}
