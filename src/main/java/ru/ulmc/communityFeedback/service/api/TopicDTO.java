package ru.ulmc.communityFeedback.service.api;

import java.util.List;

public class TopicDTO extends BaseDTO<Long> {
    private String name;
    private Integer order;
    private List<OptionDTO> options;

    public TopicDTO() {
    }

    public TopicDTO(String name, Integer order) {
        this.name = name;
        this.order = order;
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

    public List<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDTO> options) {
        this.options = options;
    }
}
