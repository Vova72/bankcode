package com.vovarusskih72.bankcode.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_recipient_id")
    private Card cardRecipient;

    @ManyToOne
    @JoinColumn(name = "card_donor_id")
    private Card cardDonor;

    private Exchanges exchange;
    private double amount;
    private Date date;

    public Transaction() {}

    public Transaction(Card cardRecipient, Card cardDonor, Exchanges exchange, double amount) {
        this.cardRecipient = cardRecipient;
        this.cardDonor = cardDonor;
        this.exchange = exchange;
        this.amount = amount;
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Card getCardRecipient() {
        return cardRecipient;
    }

    public void setCardRecipient(Card cardRecipient) {
        this.cardRecipient = cardRecipient;
    }

    public Card getCardDonor() {
        return cardDonor;
    }

    public void setCardDonor(Card cardDonor) {
        this.cardDonor = cardDonor;
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
}
