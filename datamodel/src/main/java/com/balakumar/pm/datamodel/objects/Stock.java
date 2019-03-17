package com.balakumar.pm.datamodel.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Stock extends Scrip {

    private String exchange;
    private double open;
    @Column(nullable = false)
    private double close;
    private double low;
    private double high;
    private long tradedQty;
    private long tradedValue;

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public long getTradedQty() {
        return tradedQty;
    }

    public void setTradedQty(long tradedQty) {
        this.tradedQty = tradedQty;
    }

    public long getTradedValue() {
        return tradedValue;
    }

    public void setTradedValue(long tradedValue) {
        this.tradedValue = tradedValue;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

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
