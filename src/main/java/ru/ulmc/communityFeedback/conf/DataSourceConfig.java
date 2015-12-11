package ru.ulmc.communityFeedback.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import ru.ulmc.communityFeedback.conf.reference.ConfParam;

import javax.sql.DataSource;

@Configuration
@Configurable
public class DataSourceConfig {
    @Autowired
    ServerSideConfig config;

    private String driverClass;
    private String url;

    @Bean()
    DataSource dataSource() throws Exception {
        setupValues();
        SingleConnectionDataSource dataSourceBuilder = new SingleConnectionDataSource();
        dataSourceBuilder.setSuppressClose(true);
        dataSourceBuilder.setDriverClassName(driverClass);
        dataSourceBuilder.setUrl(url);
        return dataSourceBuilder;
    }

    private synchronized void setupValues() throws Exception {
        if (driverClass == null || url == null) {
            driverClass = config.getProperty(ConfParam.DB_DRIVER_CLASS);
            url = config.getProperty(ConfParam.DB_URL);
        }
    }

}
