package ru.ulmc.communityFeedback.dao.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity<I extends Serializable> {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    protected I id;

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }
}
