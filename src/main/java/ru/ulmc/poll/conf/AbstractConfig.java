package ru.ulmc.poll.conf;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import ru.ulmc.poll.conf.reference.ConfParam;
import ru.ulmc.poll.utils.ServletContainer;

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

    public Boolean getBooleanProperty(ConfParam<Boolean> param) {
        return config.getBoolean(param.getName(), param.getDefaultValue());
    }

    public String[] getPropertyArray(ConfParam<String[]> param) {
        return config.getStringArray(param.getName());
    }

}
