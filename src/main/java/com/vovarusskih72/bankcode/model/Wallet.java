package com.vovarusskih72.bankcode.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Wallet {

    @Id
    @GeneratedValue
    private Long id;

    private String walletName;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(cascade  = CascadeType.ALL, mappedBy = "wallet")
    private List<Card> cards = new ArrayList<>();

    public Wallet() {}

    public Wallet(Account account) {
        this.account = account;
        this.walletName = account.getLogin();
    }

    public void addCard(Card card) {
        card.setWallet(this);
        cards.add(card);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
