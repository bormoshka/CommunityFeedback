package ru.ulmc.communityFeedback.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "TOPIC_OPTION")
public class Option extends BaseEntity<Long> {

    @Column(name = "OPTION_DESCRIPTION")
    protected String description;

    @Column(name = "OPTION_DISPLAYED_NAME")
    protected String displayedName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "TOPIC_ID", nullable = false, referencedColumnName = "id")
    protected Topic topic;

    public Option() {
    }

    public Option(String displayedName, String description) {
        this.description = description;
        this.displayedName = displayedName;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
