package ru.ulmc.communityFeedback.controller.model;

import java.io.Serializable;
import java.util.List;

public class TopicUI implements Serializable {
    private String nominationHeader;
    private long id;
    private List<OptionUI> candidates;

    public TopicUI(String nominationHeader, long id, List<OptionUI> candidates) {
        this.nominationHeader = nominationHeader;
        this.id = id;
        this.candidates = candidates;
    }

    public TopicUI() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNominationHeader() {
        return nominationHeader;
    }

    public void setNominationHeader(String nominationHeader) {
        this.nominationHeader = nominationHeader;
    }

    public List<OptionUI> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<OptionUI> candidates) {
        this.candidates = candidates;
    }

    public int getCount() {
        return candidates.size();
    }

}
