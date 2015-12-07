package ru.ulmc.poll.controller.model;

import java.io.Serializable;

public class VotesUI implements Serializable{
    private Long nominationID;
    private Long candidateID;

    public VotesUI(Long nominationID, Long candidateID) {
        this.nominationID = nominationID;
        this.candidateID = candidateID;
    }

    public VotesUI() {
    }

    public Long getNominationID() {
        return nominationID;
    }

    public void setNominationID(Long nominationID) {
        this.nominationID = nominationID;
    }

    public Long getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Long candidateID) {
        this.candidateID = candidateID;
    }
}
