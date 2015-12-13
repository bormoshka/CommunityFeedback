package ru.ulmc.communityFeedback.dao.impl;

import org.springframework.stereotype.Repository;
import ru.ulmc.communityFeedback.dao.AbstractDAO;
import ru.ulmc.communityFeedback.dao.entity.Option;
import ru.ulmc.communityFeedback.dao.entity.Topic;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by 45 on 12.12.2015.
 */
@Repository
public class TopicDAO extends AbstractDAO<Topic, Long> {

    @Override
    public Class getEntityClass() {
        return Topic.class;
    }

    public List<Topic> getTopics(int start, int perPage) {
        Query query = manager.createQuery("select t from Topic t");
        query.setFirstResult(start);
        query.setMaxResults(perPage);
        return query.getResultList();
    }
}
