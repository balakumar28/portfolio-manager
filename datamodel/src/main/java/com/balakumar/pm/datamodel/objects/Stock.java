package com.balakumar.pm.datamodel.objects;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Stock extends Scrip {

    private double open;
    @Column(nullable = false)
    private double close;
    private double low;
    private double high;
    private long tradedQty;
    private long tradedValue;

    @Override
    public String toString() {
        return "Stock{" +
                "open=" + open +
                ", close=" + close +
                ", low=" + low +
                ", high=" + high +
                ", tradedQty=" + tradedQty +
                ", tradedValue=" + tradedValue +
                '}';
    }
}
