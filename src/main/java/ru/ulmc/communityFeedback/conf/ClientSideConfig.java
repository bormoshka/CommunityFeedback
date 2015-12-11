package ru.ulmc.communityFeedback.conf;

import org.apache.commons.configuration.*;
import ru.ulmc.communityFeedback.conf.reference.Constants;
import ru.ulmc.communityFeedback.utils.ServletContainer;

import javax.servlet.ServletContext;

/**
 * User: Kolmogorov Alex
 * Date: 07.12.2015
 */
public class ClientSideConfig extends AbstractConfig {

    public ClientSideConfig(ServletContext context, ServletContainer server) {
        super(context, server);
        init();
    }

    private synchronized CompositeConfiguration init() {
        if (config == null) {
            config = new CompositeConfiguration();
            String configLocation = server.getConfigHome(context)+ "gui.xml";
            try {
                XMLConfiguration props = new XMLConfiguration();
                props.setListDelimiter(Constants.CONFIG_LIST_DELIMITER);
                props.load(configLocation);
                config.addConfiguration(props);
            } catch (ConfigurationException e) {
                e.printStackTrace();
                //todo: log error? maybe make mock config at default location?
                throw new RuntimeException(e);
            }
        }
        return config;
    }
}
