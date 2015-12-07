package ru.ulmc.poll.dao;

import ru.ulmc.poll.dao.entity.Candidate;
import ru.ulmc.poll.dao.entity.Nomination;
import ru.ulmc.poll.dao.entity.User;

import java.util.List;
import java.util.Map;

public interface IVotingDAO {

    void initDataBase();

    Map<Nomination, List<Candidate>> getNominationsWithCandidates();

    void createNewUserIfNotExist(String username);

    void createCandidate(String username, String displayedName, String about, Long nominationID);

    Long findUser(String username);

    Long vote(String username, Long nominationID, Long candidateID);

    Map<Long, Long> findVotes(String username);

    Map<Nomination, List<Candidate>> getNominationsWithCandidatesAndVotes();

    Long cancelVote(String username, Long nominationID, Long candidateID);

    boolean isUserFinishedVoting(String username);

    void changeUserVoteStatus(String username, boolean voteFinished);

    List<User> getUsersVotingStatus();

    Long deleteCandidate(Long nominationID, Long candidateID);

}
