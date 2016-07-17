package ru.ulmc.communityFeedback.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.ulmc.communityFeedback.conf.reference.ConfigBeanUI;
import ru.ulmc.communityFeedback.utils.ServletContainer;
import ru.ulmc.communityFeedback.utils.impl.ServletContainerFactory;

import javax.servlet.ServletContext;

@Configuration
public class ApplicationConfig {
    private static final String codeSalt = "Y4hOtZtpR5wMfoHit9v6Kd64OssP7Ui837.ywG0NLm1Xx9xZKgjR97IMe2epRiP/cgAoSkBG.mE0/kVlSy0aTFSuRH0BMqAwEw4bzawf7yvOLi";


    @Autowired
    ServletContext context;

    private ServletContainer server = ServletContainerFactory.getCurrentContainer(); // todo: determine current servlet container
    private ServerSideConfig config = null;
    private ConfigBeanUI configBeanUI = null;
    private PasswordEncoder passwordEncoder = null;

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

    @Bean
    synchronized PasswordEncoder getPasswordEncoder() throws Exception {
        if (passwordEncoder == null) {
            passwordEncoder = new StandardPasswordEncoder(codeSalt);
        }
        return passwordEncoder;
    }


}

