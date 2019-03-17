package com.balakumar.pm.datamodel.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class MutualFund extends Scrip {

    @Column(nullable = false)
    private double nav;

    public double getNav() {
        return nav;
    }

    public void setNav(double nav) {
        this.nav = nav;
    }

    @Override
    public String toString() {
        return "MutualFund{" +
                "nav=" + nav +
                '}';
    }
}
