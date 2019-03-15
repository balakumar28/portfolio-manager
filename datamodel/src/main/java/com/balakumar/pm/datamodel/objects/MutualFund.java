package com.balakumar.pm.datamodel.objects;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MutualFund extends Scrip {

    @Column(nullable = false)
    private double nav;

    @Override
    public String toString() {
        return "MutualFund{" +
                "nav=" + nav +
                '}';
    }
}
