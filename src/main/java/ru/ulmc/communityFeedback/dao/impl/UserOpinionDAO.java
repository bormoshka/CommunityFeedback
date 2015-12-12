package ru.ulmc.communityFeedback.dao.impl;

import org.springframework.stereotype.Repository;
import ru.ulmc.communityFeedback.dao.AbstractDAO;
import ru.ulmc.communityFeedback.dao.entity.Topic;
import ru.ulmc.communityFeedback.dao.entity.UserOpinion;

/**
 * Created by 45 on 12.12.2015.
 */
@Repository
public class UserOpinionDAO extends AbstractDAO {

    @Override
    public Class getEntityClass() {
        return UserOpinion.class;
    }
}
