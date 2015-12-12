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
                //e.printStackTrace();
                XMLConfiguration props = new XMLConfiguration();
                props.setListDelimiter(Constants.CONFIG_LIST_DELIMITER);
                config.addConfiguration(props);
                try {
                    props.load("gui_defaults.xml");
                } catch (ConfigurationException e1) {
                    e1.printStackTrace();
                    throw new RuntimeException(e); // звезда рулю
                }
            }
        }
        return config;
    }
}
