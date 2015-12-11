package ru.ulmc.communityFeedback.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.ulmc.communityFeedback.dao.impl.VotingDAO;

@Component
public class AppListener {
    @Autowired
    VotingDAO dao;

    @EventListener
    public void handleAuthenticationSuccessEvent(ContextRefreshedEvent event) {
        dao.initDataBase();
        System.out.println("App initialized");
    }
}