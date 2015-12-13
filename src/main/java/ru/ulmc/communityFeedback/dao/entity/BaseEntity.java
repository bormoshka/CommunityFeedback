package ru.ulmc.communityFeedback.dao.entity;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity<I extends Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    protected I id;

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }
}
