package ru.ulmc.communityFeedback.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.ulmc.communityFeedback.conf.ServerSideConfig;
import ru.ulmc.communityFeedback.conf.reference.ConfParam;
import ru.ulmc.communityFeedback.service.DemoService;

@Component
public class AppListener {
    @Autowired
    ServerSideConfig config;

    @Autowired
    DemoService demoService;

    @EventListener
    public void handleAuthenticationSuccessEvent(ContextRefreshedEvent event) {
        // dao.initDataBase();
        if (config.getBooleanProperty(ConfParam.CREATE_DEMO_DATA)) {
            demoService.setupDemo();
        }
        System.out.println("App initialized");
    }
}