package ru.ulmc.poll.utils;

import javax.servlet.ServletContext;


public interface ServletContainer {

    boolean isApplied(ServletContext context);

    String getConfigHome(ServletContext context);

}
