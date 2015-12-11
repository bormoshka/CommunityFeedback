package ru.ulmc.communityFeedback.conf.reference;

import org.apache.commons.configuration.CompositeConfiguration;
import ru.ulmc.communityFeedback.conf.ClientSideConfig;

public class ConfigBeanUI {
    private String loginFormHeader;
    private String commonTitle;
    private String mainPageFooterText;

    public ConfigBeanUI(ClientSideConfig clientSideConfig) {
        CompositeConfiguration config = clientSideConfig.getConfig();
        loginFormHeader = config.getString("loginForm.header");
        commonTitle = config.getString("common.title");
        mainPageFooterText = config.getString("mainPage.footer.text");
    }

    public String getLoginFormHeader() {
        return loginFormHeader;
    }

    public String getCommonTitle() {
        return commonTitle;
    }

    public String getMainPageFooterText() {
        return mainPageFooterText;
    }
}
