package ru.ulmc.poll.dao.entity;

import java.io.Serializable;

public class UserToNomination implements Serializable {
    private Long userID;
    private Long nominationID;
    private Long candidateID;

    public UserToNomination() {
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
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
