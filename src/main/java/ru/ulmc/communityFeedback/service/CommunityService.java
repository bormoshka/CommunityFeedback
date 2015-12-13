package ru.ulmc.communityFeedback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulmc.communityFeedback.conf.ServerSideConfig;
import ru.ulmc.communityFeedback.conf.reference.ConfParam;
import ru.ulmc.communityFeedback.dao.entity.Option;
import ru.ulmc.communityFeedback.dao.entity.Topic;
import ru.ulmc.communityFeedback.dao.impl.OptionDAO;
import ru.ulmc.communityFeedback.dao.impl.TopicDAO;
import ru.ulmc.communityFeedback.service.api.OptionDTO;
import ru.ulmc.communityFeedback.service.api.TopicDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommunityService {

    @Autowired
    ServerSideConfig config;

    @Autowired
    TopicDAO topicDAO;

    @Autowired
    OptionDAO optionDAO;

    @Transactional
    public void createTopic(TopicDTO dto) {
        Topic entity = convert(dto);
        topicDAO.persist(entity);
    }

    @Transactional
    public void createOption(Long topicID, OptionDTO dto) {
        Option entity = convert(dto);
        optionDAO.persist(entity);
    }

    @Transactional(readOnly = true)
    public List<TopicDTO> getTopics(int page) {
        int perPage = config.getIntProperty(ConfParam.APP_TOPICS_PER_PAGE);
        return convertTopics(topicDAO.getTopics(page * perPage, perPage));
    }

    protected Topic convert(TopicDTO dto) {
        Topic topic = new Topic();
        topic.setName(dto.getName());
        topic.setOrder(dto.getOrder());
        topic.setOptions(convert(dto.getOptions(), topic));
        return topic;
    }

    protected TopicDTO convert(Topic entity) {
        TopicDTO dto = new TopicDTO();
        dto.setName(entity.getName());
        dto.setOrder(entity.getOrder());
        dto.setOptions(convert(entity.getOptions()));
        return dto;
    }

    protected List<TopicDTO> convertTopics(List<Topic> entities) {
        return entities.stream().map(this::convert).collect(Collectors.toList());
    }

    protected Option convert(OptionDTO dto) {
        Option option = new Option();
        option.setDescription(dto.getAbout());
        option.setDisplayedName(dto.getDisplayedName());
        return option;
    }

    protected List<OptionDTO> convert(List<Option> entities) {
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        List<OptionDTO> list = new ArrayList<>();
        for (Option option : entities) {
            OptionDTO opt = new OptionDTO(option.getDisplayedName(), option.getDescription());
            list.add(opt);
        }
        return list;
    }

    protected List<Option> convert(List<OptionDTO> transfer, Topic parent) {
        if (transfer == null || transfer.isEmpty()) {
            return null;
        }
        List<Option> list = new ArrayList<>();
        for (OptionDTO option : transfer) {
            Option opt = new Option(option.getDisplayedName(), option.getAbout());
            opt.setTopic(parent);
            list.add(opt);
        }
        return list;
    }
}
