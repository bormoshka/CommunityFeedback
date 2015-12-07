package ru.ulmc.poll.dao.entity;

import java.io.Serializable;

public class Candidate implements Serializable {
    private Long id;
    private String about;
    private String username;
    private String displayedName;
    private Long nominationID;

    // из USER_TO_NOMINATION
    private Integer voteCountFinished;
    private Integer voteCount;

    public Candidate() {
    }

    public Candidate(String about, String username, String displayedName, Long nominationID) {
        this.about = about;
        this.username = username;
        this.displayedName = displayedName;
        this.nominationID = nominationID;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getNominationID() {
        return nominationID;
    }

    public void setNominationID(Long nominationID) {
        this.nominationID = nominationID;
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

        Candidate candidate = (Candidate) o;

        if (about != null ? !about.equals(candidate.about) : candidate.about != null) return false;
        if (username != null ? !username.equals(candidate.username) : candidate.username != null) return false;
        return !(nominationID != null ? !nominationID.equals(candidate.nominationID) : candidate.nominationID != null);

    }

    @Override
    public int hashCode() {
        int result = about != null ? about.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (nominationID != null ? nominationID.hashCode() : 0);
        return result;
    }
}
