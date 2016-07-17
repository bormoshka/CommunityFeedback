package ru.ulmc.communityFeedback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulmc.communityFeedback.dao.entity.Authority;
import ru.ulmc.communityFeedback.dao.entity.User;
import ru.ulmc.communityFeedback.dao.impl.UserDAO;
import ru.ulmc.communityFeedback.service.api.OptionDTO;
import ru.ulmc.communityFeedback.service.api.TopicDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * Created by 45 on 13.12.2015.
 */
@Service
@Transactional
public class DemoService {
    @Autowired
    CommunityService service;

    @Autowired
    UserDAO userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

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

        Authority authority = new Authority();
        authority.setName("VIEWER");

        Authority authorityEditor = new Authority();
        authorityEditor.setName("EDITOR");
        userDao.save(authority);
        userDao.save(authorityEditor);

        User user = new User();
        user.setUsername("demo");
        user.setEmail("demo@ulmc.ru");
        user.setEmailConfirmed(true);
        user.setPassword(passwordEncoder.encode("demo"));
        user.setRegistrationDate(new Date());
        user.setAuthorities(Arrays.asList(authority, authorityEditor));
        userDao.save(user);
/*
        authority.setUsers(Collections.singletonList(user));
        authorityEditor.setUsers(Collections.singletonList(user));

        userDao.save(authority);
        userDao.save(authorityEditor);*/
    }

}
