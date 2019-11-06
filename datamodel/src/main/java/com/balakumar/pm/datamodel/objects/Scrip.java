package com.balakumar.pm.datamodel.objects;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public abstract class Scrip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private double value;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "scrip")
    private Set<ScripHistory> scripHistories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public double getValue() {
        return value;
    }

    protected void setValue(double value) {
        this.value = value;
    }

    public Set<ScripHistory> getScripHistories() {
        return scripHistories;
    }

    public void setScripHistories(Set<ScripHistory> scripHistories) {
        this.scripHistories = scripHistories;
    }

    @Override
    public String toString() {
        return "Scrip{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", lastUpdated=" + lastUpdated +
                ", scripHistories=" + scripHistories +
                '}';
    }
}
