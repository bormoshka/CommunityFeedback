package ru.ulmc.communityFeedback.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ulmc.communityFeedback.conf.reference.ConfigBeanUI;
import ru.ulmc.communityFeedback.utils.ServletContainer;
import ru.ulmc.communityFeedback.utils.impl.ServletContainerFactory;

import javax.servlet.ServletContext;

@Configuration
public class ApplicationConfig {
    @Autowired
    ServletContext context;

    private ServletContainer server = ServletContainerFactory.getCurrentContainer(); // todo: determine current servlet container
    private ServerSideConfig config = null;
    private ConfigBeanUI configBeanUI = null;

    @Bean
    synchronized ServerSideConfig getServerSideConfig() throws Exception {
        if (config == null) {
            config = new ServerSideConfig(context, server);
        }
        return config;
    }

    @Bean
    synchronized ConfigBeanUI getConfigBeanUI() throws Exception {
        if (configBeanUI == null) {
            configBeanUI = new ConfigBeanUI(new ClientSideConfig(context, server));
        }
        return configBeanUI;
    }


}
