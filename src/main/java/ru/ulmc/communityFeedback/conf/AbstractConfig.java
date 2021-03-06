package ru.ulmc.communityFeedback.conf;

import org.apache.commons.configuration.CompositeConfiguration;
import ru.ulmc.communityFeedback.conf.reference.ConfParam;
import ru.ulmc.communityFeedback.utils.ServletContainer;

import javax.servlet.ServletContext;

/**
 * User: Kolmogorov Alex
 * Date: 07.12.2015
 */
public abstract class AbstractConfig {
   protected ServletContainer server;
   protected ServletContext context;
   protected CompositeConfiguration config = null;

    public AbstractConfig(ServletContext context, ServletContainer server) {
        this.context = context;
        this.server = server;
    }

    public CompositeConfiguration getConfig() {
        return config;
    }

    public String getProperty(ConfParam<String> param) {
        return config.getString(param.getName(), param.getDefaultValue());
    }

    public Integer getIntProperty(ConfParam<Integer> param) {
        return config.getInteger(param.getName(), param.getDefaultValue());
    }

    public Boolean getBoolProperty(ConfParam<Boolean> param) {
        return config.getBoolean(param.getName(), param.getDefaultValue());
    }

    public String[] getPropertyArray(ConfParam<String[]> param) {
        return config.getStringArray(param.getName());
    }

}
