package ru.ulmc.poll.controller.model;

import java.io.Serializable;
import java.util.List;

public class NominationUI implements Serializable{
    private String nominationHeader;
    private long id;
    private List<CandidateUI> candidates;

    public NominationUI(String nominationHeader, long id, List<CandidateUI> candidates) {
        this.nominationHeader = nominationHeader;
        this.id = id;
        this.candidates = candidates;
    }

    public NominationUI() {

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

    public List<CandidateUI> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateUI> candidates) {
        this.candidates = candidates;
    }

    public int getCount() {
        return candidates.size();
    }

}
