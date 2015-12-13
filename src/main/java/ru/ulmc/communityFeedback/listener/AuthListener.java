package ru.ulmc.communityFeedback.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapAuthority;
import org.springframework.stereotype.Component;
import ru.ulmc.communityFeedback.conf.ServerSideConfig;
import ru.ulmc.communityFeedback.conf.reference.ConfParam;
import ru.ulmc.communityFeedback.conf.reference.Constants;
import ru.ulmc.communityFeedback.dao.impl.VotingDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Configurable
public class AuthListener {
    private static String[] admins = null;
    private static Boolean createLocalAdmins = null;
    private static String userDN = null;

    @Autowired
    VotingDAO dao;

    @Autowired
    ServerSideConfig config;

    @EventListener
    public void handleAuthenticationSuccessEvent(InteractiveAuthenticationSuccessEvent event) {
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        System.out.println(new Date().getTime() + " loggedIn: " + userDetails.getUsername());
        setupAdmins();
        if (createLocalAdmins) {
            for (String superuser : admins) {
                if (superuser.equals(userDetails.getUsername())) {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    List<GrantedAuthority> authorities = new ArrayList<>(auth.getAuthorities());
                    LdapAuthority authority = new LdapAuthority(Constants.USERS_ROLE_ADMIN,
                            userDN.replace(Constants.AUTH_LDAP_USERNAME_PLACEHOLDER, userDetails.getUsername()));
                    authorities.add(authority);
                    Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(),
                            auth.getCredentials(), authorities);
                    SecurityContextHolder.getContext().setAuthentication(newAuth);
                    break;
                }
            }
        }
        //dao.createNewUserIfNotExist(userDetails.getUsername()); //todo: it's only for special ldap's case
    }

    private void setupAdmins() {
        if (createLocalAdmins == null) {
            createLocalAdmins = config.getBoolProperty(ConfParam.APP_USERS_CREATE_LOCAL_ADMINS);
        }
        if (createLocalAdmins) {
            if (admins == null) {
                admins = config.getPropertyArray(ConfParam.APP_USERS_ADMINS);
                userDN = config.getProperty(ConfParam.AUTH_LDAP_USER_DN_PATTERNS) +
                        config.getProperty(ConfParam.AUTH_LDAP_BASE_SEARCH_DN);
            }
        }
    }
}