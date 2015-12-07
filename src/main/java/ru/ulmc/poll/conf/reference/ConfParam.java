package ru.ulmc.poll.conf.reference;


/**
 * User: Kolmogorov Alex
 * Date: 06.12.2015
 */
public class ConfParam<T> extends  AbstractConfParam<T>{
    public final static ConfParam<String> AUTH_PROVIDER = new ConfParam<>("auth.provider", Constants.AUTH_PROVIDER_LDAP);
    public final static ConfParam<String> AUTH_LDAP_URL = new ConfParam<>("auth.ldap.url", null);
    public final static ConfParam<String> AUTH_LDAP_BASE_SEARCH_DN = new ConfParam<>("auth.ldap.baseSearchDn", null);
    public final static ConfParam<String> AUTH_LDAP_USER_DN_PATTERNS =
            new ConfParam<>("auth.ldap.userDnPatterns", Constants.AUTH_LDAP_USERNAME_PLACEHOLDER);

    public final static ConfParam<String> DB_DRIVER_CLASS = new ConfParam<>("db.driverClass", "org.sqlite.JDBC");
    public final static ConfParam<String> DB_URL = new ConfParam<>("db.url", "jdbc:sqlite:ru.ulmc.poll.db");
    public final static ConfParam<String> DB_USER = new ConfParam<>("db.user", null);
    public final static ConfParam<String> DB_PASSWORD = new ConfParam<>("db.password", null);

    public final static ConfParam<String[]> USERS_ADMINS = new ConfParam<>("users.admins", null);
    public final static ConfParam<Boolean> USERS_CREATE_LOCAL_ADMINS = new ConfParam<>("users.create.admins", true);

    public ConfParam(String name, T defaultValue) {
        super(name, defaultValue);
    }
}
