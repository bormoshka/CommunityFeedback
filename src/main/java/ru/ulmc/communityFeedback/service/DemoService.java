package ru.ulmc.communityFeedback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulmc.communityFeedback.service.api.OptionDTO;
import ru.ulmc.communityFeedback.service.api.TopicDTO;

import java.util.ArrayList;

/**
 * Created by 45 on 13.12.2015.
 */
@Service
@Transactional
public class DemoService {
    @Autowired
    CommunityService service;

    @Transactional
    public void setupDemo() {
        TopicDTO topic = new TopicDTO();
        topic.setName("Our projects.");
        topic.setOrder(1);
        topic.setOptions(new ArrayList<>());

        OptionDTO option = new OptionDTO();
        option.setDisplayedName("Option 1");
        option.setAbout("About option 1");

        OptionDTO optionA = new OptionDTO();
        optionA.setDisplayedName("Option A");
        optionA.setAbout("About option A");

        OptionDTO optionB = new OptionDTO();
        optionB.setDisplayedName("Option B");
        optionB.setAbout("About option B");

        topic.getOptions().add(option);
        topic.getOptions().add(optionA);
        topic.getOptions().add(optionB);

        service.createTopic(topic);
    }

}
