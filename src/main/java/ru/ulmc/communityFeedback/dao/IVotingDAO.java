package ru.ulmc.communityFeedback.dao;

import ru.ulmc.communityFeedback.dao.entity.Option;
import ru.ulmc.communityFeedback.dao.entity.Topic;
import ru.ulmc.communityFeedback.dao.entity.User;

import java.util.List;
import java.util.Map;

public interface IVotingDAO {

    void initDataBase();

    Map<Topic, List<Option>> getNominationsWithCandidates();

    void createNewUserIfNotExist(String username);

    void createCandidate(String username, String displayedName, String about, Long nominationID);

    Long findUser(String username);

    Long vote(String username, Long nominationID, Long candidateID);

    Map<Long, Long> findVotes(String username);

    Map<Topic, List<Option>> getNominationsWithCandidatesAndVotes();

    Long cancelVote(String username, Long nominationID, Long candidateID);

    boolean isUserFinishedVoting(String username);

    void changeUserVoteStatus(String username, boolean voteFinished);

    List<User> getUsersVotingStatus();

    Long deleteCandidate(Long nominationID, Long candidateID);

}
