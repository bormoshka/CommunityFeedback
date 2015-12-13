package ru.ulmc.communityFeedback.dao;

import org.springframework.stereotype.Repository;
import ru.ulmc.communityFeedback.dao.entity.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public abstract class AbstractDAO<T extends BaseEntity<ID>, ID extends Serializable> {
    @PersistenceContext
    protected EntityManager manager;

    public abstract Class<T> getEntityClass();

    public void persist(T entity) {
        manager.persist(entity);
        manager.flush();
    }

    public T find(ID id) {
        return manager.find(getEntityClass(), id);
    }

    public void delete(T entity) {
        manager.remove(entity);
    }

    public List<T> findByQuery(String jpqlQueryString) {
        return findByQueryWithParams(jpqlQueryString, Collections.emptyMap());
    }

    public List<T> findByQueryWithParams(String jpqlQueryString, Map<String, Object> params) {
        TypedQuery<T> query = manager.createQuery(jpqlQueryString, getEntityClass());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }
}
