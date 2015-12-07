package ru.ulmc.poll.utils.impl;

import ru.ulmc.poll.utils.ServletContainer;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * User: Kolmogorov Alex
 * Date: 06.12.2015
 */
public class TomcatContainerImpl implements ServletContainer {
    public TomcatContainerImpl() {
    }

    public boolean isApplied(ServletContext context) {
        return context.getServerInfo().contains("Tomcat") && System.getProperty("catalina.base") != null;
    }

    public String getConfigHome(ServletContext context) {
        System.out.println("!!!!!!!!!!!:" + context.getServerInfo());
        String appName = context.getServletContextName();
        if (appName == null) {
            appName = context.getContextPath().replaceAll("/", "");
        }
        return System.getProperty("catalina.base") + File.separator + "conf" + File.separator + appName + File.separator;
    }

}
