package com.balakumar.pm.datamodel.objects;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Stock extends Scrip {

    private String exchange;
    private double close;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
        setValue(close);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "exchange='" + exchange + '\'' +
                '}';
    }
}
