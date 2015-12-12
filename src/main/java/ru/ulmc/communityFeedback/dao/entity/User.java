package ru.ulmc.communityFeedback.dao.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "COMMUNITY_USER")
public class User extends BaseEntity<UUID> {

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "USER_PASSWORD")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_OPINION")
    private List<UserOpinion> opinions;

    public User() {
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
