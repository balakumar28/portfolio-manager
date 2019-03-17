package com.balakumar.pm.datamodel.objects;

import javax.persistence.*;
import java.util.Set;

import static com.balakumar.pm.datamodel.spring.Constants.DEFAULT_PORTFOLIO;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Portfolio> portfolios;

    @Transient
    private Portfolio defaultPortfolio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Portfolio> getPortfolios() {
        return portfolios;
    }

    public Portfolio getDefaultPortfolio() {
        if(defaultPortfolio == null) {
            for(Portfolio portfolio: portfolios) {
                if (portfolio.getName().equals(DEFAULT_PORTFOLIO)) {
                    defaultPortfolio = portfolio;
                    break;
                }
            }
        }
        return defaultPortfolio;
    }

    public void setPortfolios(Set<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", portfolios=" + portfolios +
                '}';
    }
}
