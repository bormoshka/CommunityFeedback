package ru.ulmc.communityFeedback.dao.impl;

import org.springframework.stereotype.Repository;
import ru.ulmc.communityFeedback.dao.AbstractDAO;
import ru.ulmc.communityFeedback.dao.entity.Option;
import ru.ulmc.communityFeedback.dao.entity.User;

/**
 * Created by 45 on 12.12.2015.
 */
@Repository
public class OptionDAO extends AbstractDAO<Option, Long> {

    @Override
    public Class getEntityClass() {
        return Option.class;
    }
}
