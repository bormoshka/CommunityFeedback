package ru.ulmc.poll.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.ulmc.poll.dao.IVotingDAO;
import ru.ulmc.poll.service.IVoteProcessingService;

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
