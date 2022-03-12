package com.balakumar.pm.datamodel.objects;

import com.balakumar.pm.datamodel.Constants;

import javax.persistence.*;
import java.util.HashSet;
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "portfolio_transactions", joinColumns = @JoinColumn(name = "portfolio_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "transactions_id", referencedColumnName = "id"))
    private Set<Transaction> transactions = new HashSet<>();

    public static final String DEFAULT_PORTFOLIO = Constants.DEFAULT_PORTFOLIO;

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

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
