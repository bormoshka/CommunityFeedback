package ru.ulmc.communityFeedback.utils.impl;

import ru.ulmc.communityFeedback.utils.ServletContainer;

import javax.servlet.ServletContext;

/**
 * User: Kolmogorov Alex
 * Date: 06.12.2015
 */
public class EmbeddedTomcatContainerImpl implements ServletContainer {
    private String customConfigDirectory;

    public EmbeddedTomcatContainerImpl(String customConfigDirectory) {
        this.customConfigDirectory = customConfigDirectory;
    }

    public boolean isApplied(ServletContext context) {
        return context.getServerInfo().contains("Tomcat") && System.getProperty("catalina.base") != null;
    }

    public String getConfigHome(ServletContext context) {
        return this.customConfigDirectory;
    }

}
