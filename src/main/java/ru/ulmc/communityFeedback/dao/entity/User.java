package ru.ulmc.communityFeedback.dao.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "APP_USER")
public class User extends BaseEntity<UUID> {

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "DATE_REG")
    private Date registrationDate;

    @Column(name = "BASE_HASH")
    private String hash;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "EMAIL_CONFIRMED")
    private Boolean isEmailConfirmed;

    @Column(name = "IS_ENABLED")
    private Boolean isEnabled = true;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_OPINION")
    private List<UserOpinion> opinions;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "AUTHORITY_TO_USER")
    private List<Authority> authorities;

    public User() {
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailConfirmed() {
        return isEmailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        isEmailConfirmed = emailConfirmed;
    }

    public List<UserOpinion> getOpinions() {
        return opinions;
    }

    public void setOpinions(List<UserOpinion> opinions) {
        this.opinions = opinions;
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
