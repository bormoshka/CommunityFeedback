package ru.ulmc.communityFeedback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulmc.communityFeedback.dao.entity.Option;
import ru.ulmc.communityFeedback.dao.entity.Topic;
import ru.ulmc.communityFeedback.dao.impl.OptionDAO;
import ru.ulmc.communityFeedback.dao.impl.TopicDAO;
import ru.ulmc.communityFeedback.service.api.OptionDTO;
import ru.ulmc.communityFeedback.service.api.TopicDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommunityService {

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

    protected Topic convert(TopicDTO dto) {
        Topic topic = new Topic();
        topic.setName(dto.getName());
        topic.setOrder(dto.getOrder());
        topic.setOptions(convertToEntities(dto.getOptions()));
        return topic;
    }

    protected TopicDTO convert(Topic entity) {
        TopicDTO dto = new TopicDTO();
        dto.setName(entity.getName());
        dto.setOrder(entity.getOrder());
        dto.setOptions(convert(entity.getOptions()));
        return dto;
    }

    protected Option convert(OptionDTO dto) {
        Option option = new Option();
        option.setAbout(dto.getAbout());
        option.setDisplayedName(dto.getDisplayedName());
        return option;
    }

    protected List<OptionDTO> convert(List<Option> entities) {
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        List<OptionDTO> list = new ArrayList<>();
        for (Option option : entities) {
            OptionDTO opt = new OptionDTO(option.getDisplayedName(), option.getAbout());
            list.add(opt);
        }
        return list;
    }

    protected List<Option> convertToEntities(List<OptionDTO> transfer) {
        if (transfer == null || transfer.isEmpty()) {
            return null;
        }
        List<Option> list = new ArrayList<>();
        for (OptionDTO option : transfer) {
            Option opt = new Option(option.getDisplayedName(), option.getAbout());
            list.add(opt);
        }
        return list;
    }
}
