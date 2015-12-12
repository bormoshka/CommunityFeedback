package ru.ulmc.communityFeedback.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "TOPIC_OPTION")
public class Option extends BaseEntity<Long> {

    @Column(name = "ABOUT")
    protected String about;

    @Column(name = "DISPLAYED_NAME")
    protected String displayedName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOPIC_ID", nullable = false)
    protected Topic topic;

    public Option() {
    }

    public Option(String displayedName, String about) {
        this.about = about;
        this.displayedName = displayedName;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}
