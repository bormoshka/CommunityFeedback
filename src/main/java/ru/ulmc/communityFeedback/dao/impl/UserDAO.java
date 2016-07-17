package ru.ulmc.communityFeedback.dao.impl;

import org.hibernate.jpa.criteria.CriteriaQueryImpl;
import org.springframework.stereotype.Repository;
import ru.ulmc.communityFeedback.dao.AbstractDAO;
import ru.ulmc.communityFeedback.dao.entity.Authority;
import ru.ulmc.communityFeedback.dao.entity.Topic;
import ru.ulmc.communityFeedback.dao.entity.User;
import ru.ulmc.communityFeedback.utils.EncryptionUtils;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by 45 on 12.12.2015.
 */
@Repository
public class UserDAO extends AbstractDAO {

    @Override
    public Class getEntityClass() {
        return User.class;
    }

    public User getUser(String username) {
        Query query = manager.createQuery("select u from User u where u.username=:username");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }

    public User getUserByHash(String hash) {
        Query query = manager.createQuery("select u from User u where u.hash=:hash");
        query.setParameter("hash", hash);
        return (User) query.getSingleResult();
    }

    public boolean checkUsersAuthority(String hash, String authCode) {
        Query query = manager.createQuery("select count(u.id) from User u JOIN u.authorities a where u.hash=:hash and a.name=:authCode");
        query.setParameter("hash", hash);
        query.setParameter("authCode", authCode);
        return (Long)query.getSingleResult() != 0;
    }

    public List<Authority> getAuthorities() {
        return manager.createQuery("from Authority").getResultList();
    }

    public void save(User user) {
        String hash = EncryptionUtils.createHash(user.getUsername() + user.getEmail() + user.getRegistrationDate()); //не совсем функция DAO
        user.setHash(hash);
        if (user.getId() != null) {
            manager.merge(user);
        } else {
            manager.persist(user);
        }
    }

    public void save(Authority authority) {
        if (authority.getId() != null) {
            manager.merge(authority);
        } else {
            manager.persist(authority);
        }
    }
}
