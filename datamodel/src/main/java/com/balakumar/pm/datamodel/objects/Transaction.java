package com.balakumar.pm.datamodel.objects;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Scrip scrip;
    private Date date;
    private long qty;
    private double costPerUnit;
    private double value;

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

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
        updateValue();
    }

    public double getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(double costPerUnit) {
        this.costPerUnit = costPerUnit;
        updateValue();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    private void updateValue() {
        value = qty * costPerUnit;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", scrip=" + scrip +
                ", date=" + date +
                ", qty=" + qty +
                ", costPerUnit=" + costPerUnit +
                ", value=" + value +
                '}';
    }
}
