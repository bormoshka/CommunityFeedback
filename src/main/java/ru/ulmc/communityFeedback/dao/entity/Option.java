package ru.ulmc.communityFeedback.dao.entity;

import java.io.Serializable;

public class Option implements Serializable {
    private Long id;
    private String about;
    private String displayedName;
    private Long topicID;

    // из USER_TO_NOMINATION
    private Integer voteCountFinished;
    private Integer voteCount;

    public Option() {
    }

    public Option(String about, String username, String displayedName, Long topicID) {
        this.about = about;
        this.displayedName = displayedName;
        this.topicID = topicID;
    }


    public Integer getVoteCountFinished() {
        return voteCountFinished;
    }

    public void setVoteCountFinished(Integer voteCountFinished) {
        this.voteCountFinished = voteCountFinished;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }


    public Long getTopicID() {
        return topicID;
    }

    public void setTopicID(Long topicID) {
        this.topicID = topicID;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        if (about != null ? !about.equals(option.about) : option.about != null) return false;
        return !(topicID != null ? !topicID.equals(option.topicID) : option.topicID != null);
    }

    @Override
    public int hashCode() {
        int result = about != null ? about.hashCode() : 0;
        result = 31 * result + (topicID != null ? topicID.hashCode() : 0);
        return result;
    }
}
