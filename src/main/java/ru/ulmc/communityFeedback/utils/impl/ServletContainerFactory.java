package ru.ulmc.communityFeedback.utils.impl;

import ru.ulmc.communityFeedback.utils.ServletContainer;

/**
 * User: Kolmogorov Alex
 * Date: 07.12.2015
 */
public class ServletContainerFactory {
    public static ServletContainer getCurrentContainer() {
        if(System.getProperty("app.config.dir") != null) {
            return new EmbeddedTomcatContainerImpl(System.getProperty("app.config.dir"));
        } else {
            return new TomcatContainerImpl();
        }
    }
}
