package ru.ulmc.communityFeedback.dao.entity;

import java.io.Serializable;

public class UserToTopic implements Serializable {
    private Long userID;
    private Long topicID;
    private Long optionID;

    public UserToTopic() {
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getTopicID() {
        return topicID;
    }

    public void setTopicID(Long topicID) {
        this.topicID = topicID;
    }

    public Long getOptionID() {
        return optionID;
    }

    public void setOptionID(Long optionID) {
        this.optionID = optionID;
    }
}
