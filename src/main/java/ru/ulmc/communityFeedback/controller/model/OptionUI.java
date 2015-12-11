package ru.ulmc.communityFeedback.controller.model;

import java.io.Serializable;

public class OptionUI implements Serializable {
    private String name;
    private String about;
    private Boolean isChosen;
    private Long id;
    private Integer count;
    private Integer countFinished;

    public OptionUI() {
    }

    public OptionUI(Long id, String name, String about) {
        this.id = id;
        this.name = name;
        this.about = about;
    }

    public OptionUI(Long id, String name, String about, Integer count) {
        this.name = name;
        this.about = about;
        this.id = id;
        this.count = count;
    }

    public Integer getCountFinished() {
        return countFinished;
    }

    public void setCountFinished(Integer countFinished) {
        this.countFinished = countFinished;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsChosen() {
        return isChosen;
    }

    public void setIsChosen(Boolean isChosen) {
        this.isChosen = isChosen;
    }
}
