package com.vovarusskih72.bankcode.model.dto;

public class CardDTO {
    private String number;
    private String pinCode;
    private String exchange;
    private double amount;
    private String killDate;

    public CardDTO(String number, String pinCode, String exchange, double amount, String killDate) {
        this.number = number;
        this.pinCode = pinCode;
        this.exchange = exchange;
        this.amount = amount;
        this.killDate = killDate;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getKillDate() {
        return killDate;
    }

    public void setKillDate(String killDate) {
        this.killDate = killDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
