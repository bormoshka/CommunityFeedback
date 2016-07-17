package ru.ulmc.communityFeedback.dao.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by 45 on 17.07.2016.
 */
@Entity
@Table(name = "AUTHORITY")
@SequenceGenerator(name = "authoritySeq", sequenceName="AUTHORITY_SEQ", allocationSize=1)
public class Authority extends BaseEntity<Long> {
    @Column(name = "AUTHORITY_NAME")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "authorities")
    private List<User> users;

    public Authority() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static enum Code {
        VIEWER("VIEWER"), VOTER("VOTER"), EDITOR("EDITOR"), ADMIN("ADMIN");
        String code;

        Code(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
