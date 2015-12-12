package ru.ulmc.communityFeedback.service.api;

import java.io.Serializable;

public class BaseDTO<T> implements Serializable {
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
