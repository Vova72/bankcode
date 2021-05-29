package com.vovarusskih72.bankcode.services;

import com.vovarusskih72.bankcode.model.Card;
import com.vovarusskih72.bankcode.model.dto.CardDTO;
import com.vovarusskih72.bankcode.repositories.CardRepository;
import com.vovarusskih72.bankcode.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CardRepository cardRepository;

    public List<CardDTO> getAllCards(String walletName, Pageable pageable) {
        List<Card> cards = cardRepository.findCardByWalletName(walletName, pageable);
        List<CardDTO> cardsDTO = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        for(Card card : cards) {
            String killDate = format.format(card.getKillDate());
            cardsDTO.add(new CardDTO(card.getNumber(), null ,card.getExchange().toString(), card.getAmount(), killDate, card.getCvv()));
        }
        return cardsDTO;
    }
}
