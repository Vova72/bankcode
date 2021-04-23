package com.vovarusskih72.bankcode.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class ExchangeTable {

    @Id
    @GeneratedValue
    private Long id;

    private double uahSale;
    private double usdSale;
    private double eurSale;

    private double uahBuy;
    private double usdBuy;
    private double eurBuy;

    private String date;

    public ExchangeTable() {}

    public ExchangeTable(Long id, double uahSale, double usdSale, double eurSale, double uahBuy, double usdBuy, double eurBuy, String date) {
        this.id = id;
        this.uahSale = uahSale;
        this.usdSale = usdSale;
        this.eurSale = eurSale;
        this.uahBuy = uahBuy;
        this.usdBuy = usdBuy;
        this.eurBuy = eurBuy;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getUahSale() {
        return uahSale;
    }

    public void setUahSale(double uahSale) {
        this.uahSale = uahSale;
    }

    public double getUsdSale() {
        return usdSale;
    }

    public void setUsdSale(double usdSale) {
        this.usdSale = usdSale;
    }

    public double getEurSale() {
        return eurSale;
    }

    public void setEurSale(double eurSale) {
        this.eurSale = eurSale;
    }

    public double getUahBuy() {
        return uahBuy;
    }

    public void setUahBuy(double uahBuy) {
        this.uahBuy = uahBuy;
    }

    public double getUsdBuy() {
        return usdBuy;
    }

    public void setUsdBuy(double usdBuy) {
        this.usdBuy = usdBuy;
    }

    public double getEurBuy() {
        return eurBuy;
    }

    public void setEurBuy(double eurBuy) {
        this.eurBuy = eurBuy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ExchangeTable{" +
                "id=" + id +
                ", uahSale=" + uahSale +
                ", usdSale=" + usdSale +
                ", eurSale=" + eurSale +
                ", uahBuy=" + uahBuy +
                ", usdBuy=" + usdBuy +
                ", eurBuy=" + eurBuy +
                ", date=" + date +
                '}';
    }
}
