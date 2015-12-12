package ru.ulmc.communityFeedback.dao.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "USER_OPINION")
public class UserOpinion extends BaseEntity<Long> {
    @Lob
    @Type(type="org.hibernate.type.MaterializedClobType")
    @Column(name = "OPINION_TEXT")
    private String opinionText;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TOPIC_ID", nullable = false)
    private Topic topic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OPTION_ID", nullable = false)
    private Option option;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public String getOpinionText() {
        return opinionText;
    }

    public void setOpinionText(String opinionText) {
        this.opinionText = opinionText;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
