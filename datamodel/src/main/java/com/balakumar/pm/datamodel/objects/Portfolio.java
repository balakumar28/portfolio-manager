package com.balakumar.pm.datamodel.objects;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "name"}))
public class Portfolio {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    @Column(nullable = false)
    private String name;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Scrip> scrips;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Scrip> getScrips() {
        return scrips;
    }

    public void setScrips(Set<Scrip> scrips) {
        this.scrips = scrips;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scrips=" + scrips +
                '}';
    }
}
