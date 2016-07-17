package ru.ulmc.communityFeedback.system.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by 45 on 17.07.2016.
 */
@Component
public class UserSession {
    private UserSession self;
    private String username;
    private String userHash;

    @Bean
    @Scope(value="session")
    public UserSession getUserSession() throws Exception {
        if (self == null) {
            self = new UserSession();
        }
        return self;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(String userHash) {
        this.userHash = userHash;
    }
}
