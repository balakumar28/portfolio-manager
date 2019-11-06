package com.balakumar.pm.datamodel.objects;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class ScripHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Scrip scrip;
    private Date date;
    private double open;
    @Column(nullable = false)
    private double close;
    private double low;
    private double high;
    private long tradedQty;
    private double tradedValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Scrip getScrip() {
        return scrip;
    }

    public void setScrip(Scrip scrip) {
        this.scrip = scrip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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

    public double getTradedValue() {
        return tradedValue;
    }

    public void setTradedValue(double tradedValue) {
        this.tradedValue = tradedValue;
    }

    @Override
    public String toString() {
        return "ScripHistory{" +
                "id=" + id +
                ", scrip=" + scrip +
                ", date=" + date +
                ", open=" + open +
                ", close=" + close +
                ", low=" + low +
                ", high=" + high +
                ", tradedQty=" + tradedQty +
                ", tradedValue=" + tradedValue +
                '}';
    }
}
