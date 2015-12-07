package ru.ulmc.poll.dao.entity;

import java.io.Serializable;

public class Nomination implements Serializable{
    private Long id;
    private String name;
    private Integer order;

    public Nomination(Long id, String name, Integer order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

    public Nomination() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nomination that = (Nomination) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
