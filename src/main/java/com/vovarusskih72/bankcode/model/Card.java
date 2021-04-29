package com.vovarusskih72.bankcode.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Card {

    @Id
    @GeneratedValue
    private Long id;

    @GeneratedValue
    private String number;
    private String cvv;
    private Exchanges exchange;
    private double amount;
    private String pinCode;

    private Date createDate;
    private Date killDate;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardDonor")
    private List<Transaction> transactionsOut = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardRecipient")
    private List<Transaction> transactionsIn = new ArrayList<>();

    public Card() {}

    public Card(String number, String cvv, Exchanges exchange, double amount, String pinCode) {
        this.number = number;
        this.cvv = cvv;
        this.exchange = exchange;
        this.amount = amount;
        this.createDate = new Date();
        this.killDate = new Date();
        this.killDate.setTime(this.killDate.getTime() + 126144000001L);
        this.pinCode = pinCode;
    }

    public void addTransactionOut(Transaction transactionOut) {
        transactionOut.setCardDonor(this);
        transactionsOut.add(transactionOut);
    }

    public void addTransactionIn(Transaction transactionIn) {
        transactionIn.setCardRecipient(this);
        transactionsIn.add(transactionIn);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exchanges getExchange() {
        return exchange;
    }

    public void setExchange(Exchanges exchange) {
        this.exchange = exchange;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getKillDate() {
        return killDate;
    }

    public void setKillDate(Date killDate) {
        this.killDate = killDate;
    }

    public List<Transaction> getTransactionsOut() {
        return transactionsOut;
    }

    public void setTransactionsOut(List<Transaction> transactionsOut) {
        this.transactionsOut = transactionsOut;
    }

    public List<Transaction> getTransactionsIn() {
        return transactionsIn;
    }

    public void setTransactionsIn(List<Transaction> transactionsIn) {
        this.transactionsIn = transactionsIn;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", cvv='" + cvv + '\'' +
                ", exchange=" + exchange +
                ", amount=" + amount +
                ", pinCode='" + pinCode + '\'' +
                ", createDate=" + createDate +
                ", killDate=" + killDate +
                ", wallet=" + wallet +
                ", transactionsOut=" + transactionsOut +
                ", transactionsIn=" + transactionsIn +
                '}';
    }
}
