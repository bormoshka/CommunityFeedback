package ru.ulmc.communityFeedback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.ulmc.communityFeedback.dao.IVotingDAO;
import ru.ulmc.communityFeedback.service.IVoteProcessingService;

@Component
public class VoteProcessingService implements IVoteProcessingService {

    @Qualifier("votingDAO")
    @Autowired
    private IVotingDAO votingDAO;

    @Override
    public void createTables() {
        votingDAO.initDataBase();
    }


}
