package ru.ulmc.communityFeedback.conf;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import ru.ulmc.communityFeedback.conf.reference.Constants;
import ru.ulmc.communityFeedback.utils.ServletContainer;

import javax.servlet.ServletContext;

/**
 * User: Kolmogorov Alex
 * Date: 07.12.2015
 */
public class ServerSideConfig extends AbstractConfig {

    public ServerSideConfig(ServletContext context, ServletContainer server) {
        super(context, server);
        init();
    }

    private synchronized CompositeConfiguration init() {
        if (config == null) {
            config = new CompositeConfiguration();
            config.setListDelimiter(Constants.CONFIG_LIST_DELIMITER);
            config.addConfiguration(new SystemConfiguration());
            String configLocation = server.getConfigHome(context) + "app.properties";
            try {
                PropertiesConfiguration props = new PropertiesConfiguration();
                props.setListDelimiter(Constants.CONFIG_LIST_DELIMITER);
                props.load(configLocation);
                config.addConfiguration(props);
            } catch (ConfigurationException e) {
                //e.printStackTrace(); //todo: log error
                PropertiesConfiguration props = new PropertiesConfiguration();
                props.setListDelimiter(Constants.CONFIG_LIST_DELIMITER);
                config.addConfiguration(props);
                try {
                    props.load("app_defaults.properties");
                } catch (ConfigurationException e1) {
                    e1.printStackTrace();
                    throw new RuntimeException(e); // звезда рулю
                }
            }
        }
        return config;
    }
}
