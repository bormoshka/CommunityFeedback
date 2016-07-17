package ru.ulmc.communityFeedback.conf;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import ru.ulmc.communityFeedback.conf.reference.ConfParam;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Configurable
public class DataSourceConfig {

    @Autowired
    @Bean
    public DataSource dataSource(ServerSideConfig config) throws Exception {
        ComboPooledDataSource dataSourceBuilder = new ComboPooledDataSource();
        dataSourceBuilder.setDriverClass(config.getProperty(ConfParam.DB_DRIVER_CLASS));
        dataSourceBuilder.setJdbcUrl(config.getProperty(ConfParam.DB_URL));
        dataSourceBuilder.setUser(config.getProperty(ConfParam.DB_USER));
        dataSourceBuilder.setPassword(config.getProperty(ConfParam.DB_PASSWORD));
        return dataSourceBuilder;
    }

    @Autowired
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,ServerSideConfig config) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("ru.ulmc.communityFeedback.dao.entity");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(getHibernateProperties(config));
        return em;
    }

    @Autowired
    @Bean(name = "txManager")
    public JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    protected Properties getHibernateProperties(ServerSideConfig config) {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", config.getProperty(ConfParam.DB_DIALECT));
        props.setProperty("hibernate.connection.autoReconnect", "true");
        props.setProperty("hibernate.c3p0.min_size", "1");
        props.setProperty("hibernate.c3p0.max_size", "200");
        props.setProperty("hibernate.c3p0.timeout", "60");
        props.setProperty("hibernate.c3p0.idle_test_period", "6000");
        props.setProperty("hibernate.c3p0.numHelperThreads", "8");
        props.setProperty("hibernate.show_sql", "true");
        if (config.getBoolProperty(ConfParam.APP_CREATE_DEMO_DATA)) {
            props.setProperty("hibernate.hbm2ddl.auto", "create");
        }
        props.setProperty("hibernate.cache.use_second_level_cache", "true");
        return props;
    }
}
