package ru.ulmc.communityFeedback.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.ulmc.communityFeedback.conf.reference.ConfParam;
import ru.ulmc.communityFeedback.conf.reference.Constants;

import javax.sql.DataSource;

@Configuration
@Configurable
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("login")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }

    @Configuration
    protected static class AuthenticationConfiguration extends
            GlobalAuthenticationConfigurerAdapter {

        @Autowired
        ServerSideConfig config;

        @Autowired
        DataSource dataSource;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            String authProvider = config.getProperty(ConfParam.AUTH_PROVIDER);
            if (Constants.AUTH_PROVIDER_LDAP.equals(authProvider)) {
                String userDnPatterns = config.getProperty(ConfParam.AUTH_LDAP_USER_DN_PATTERNS);
                String ldapURL = config.getProperty(ConfParam.AUTH_LDAP_URL) +
                        config.getProperty(ConfParam.AUTH_LDAP_BASE_SEARCH_DN);
                auth
                        .ldapAuthentication()
                        .userDnPatterns(userDnPatterns)
                        .contextSource()
                        .url(ldapURL);
            } else if (Constants.AUTH_PROVIDER_DB.equals(authProvider)) {
                auth.jdbcAuthentication().dataSource(dataSource)
                        .usersByUsernameQuery(
                                "select USERNAME, USER_PASSWORD from USER where USERNAME = ?");
            }
            //todo: else in memory?
        }
    }
}
