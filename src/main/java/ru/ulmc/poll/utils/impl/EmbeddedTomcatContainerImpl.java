package ru.ulmc.poll.utils.impl;

import ru.ulmc.poll.utils.ServletContainer;

import javax.servlet.ServletContext;
import java.io.File;

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
