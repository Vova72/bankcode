package com.vovarusskih72.bankcode.services;

import com.vovarusskih72.bankcode.model.Card;
import com.vovarusskih72.bankcode.model.Exchanges;
import com.vovarusskih72.bankcode.model.Wallet;
import com.vovarusskih72.bankcode.repositories.CardRepository;
import com.vovarusskih72.bankcode.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Transactional(readOnly = true)
    public Card getCardByNumber(String number) {
        Card card = cardRepository.findByNumber(number);
        return card;
    }

    @Transactional
    public void addCard(Wallet wallet, Exchanges exchange, double amount, String pinCode) {
        String number = "";

        for(int i = 0; i < 16; i++) {
            number += (int) (Math.random() * 9);
            if ((i + 1) % 4 == 0) {
                number += " ";
            }
        }

        while (cardRepository.existsByNumber(number)) {
            number = "";

            for (int i = 0; i < 16; i++) {
                number += (int) (Math.random() * 9);
                if ((i + 1) % 4 == 0) {
                    number += " ";
                }
            }
        }

        Card card = new Card(number, exchange, amount, pinCode);
        wallet.addCard(card);

        cardRepository.save(card);
        walletRepository.save(wallet);
    }

    @Transactional(readOnly = true)
    public boolean checkPin(String number, String pinCode) {
        Card card = cardRepository.findByNumber(number);
        if(card.getPinCode().equals(pinCode)) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<String> getAllCardsNumber(String walletName) {
        List<Card> cards = cardRepository.findAllByWalletName(walletName);
        List<String> numbers = new ArrayList<>();
        for(Card card : cards) {
            numbers.add(card.getNumber());
        }
        return numbers;
    }

    public Long count(String walletName) {
        return cardRepository.countByWalletWalletName(walletName);
    }
}
