package ru.ulmc.communityFeedback.controller.model;

import java.io.Serializable;

public class VotesUI implements Serializable{
    private Long topicID;
    private Long optionID;

    public VotesUI(Long topicID, Long optionID) {
        this.topicID = topicID;
        this.optionID = optionID;
    }

    public VotesUI() {
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
