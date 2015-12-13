package ru.ulmc.communityFeedback.dao.entity;

import ru.ulmc.communityFeedback.dao.entity.constant.ResultPublishMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TOPIC")
public class Topic extends BaseEntity<Long> {

    @Column(name = "TOPIC_NAME")
    private String name;

    @Column(name = "TOPIC_DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "TOPIC_ORDER")
    private Integer order;

    @Column(name = "PUBLISH_RESULTS_MODE")
    @Enumerated(value = EnumType.ORDINAL)
    private ResultPublishMode resultPublishMode;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "topic", cascade = CascadeType.ALL)
    protected List<Option> options;

    public Topic(Long id, String name, Integer order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

    public Topic() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic that = (Topic) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
