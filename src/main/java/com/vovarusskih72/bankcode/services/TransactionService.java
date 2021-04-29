package com.vovarusskih72.bankcode.services;

import com.vovarusskih72.bankcode.model.Card;
import com.vovarusskih72.bankcode.model.ExchangeTable;
import com.vovarusskih72.bankcode.model.Exchanges;
import com.vovarusskih72.bankcode.model.Transaction;
import com.vovarusskih72.bankcode.repositories.CardRepository;
import com.vovarusskih72.bankcode.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ExchangeTableService exchangeTableService;

    @Transactional
    public boolean makeNewTransaction(String donorCard, String pinCode, String recipientCard, double amount) {
        if(cardService.checkPin(donorCard, pinCode)) {
            Exchanges exchanges = Exchanges.UAH;

            Card realDonorCard = cardService.getCardByNumber(donorCard);
            Card realRecipientCard = cardService.getCardByNumber(recipientCard);
            ExchangeTable exchangeTable = exchangeTableService.getTodayExchange();

            double uah = exchangeTable.getUahBuy();
            double usd = exchangeTable.getUsdBuy();
            double eur = exchangeTable.getEurBuy();

            double mainAmount;

            switch (realDonorCard.getExchange().toString()) {
                case "UAH":
                    mainAmount = amount * uah;
                    break;
                case "USD":
                    mainAmount = amount * usd;
                    break;
                case "EUR":
                    mainAmount = amount * eur;
                    break;
            }

            double lastAmount = 0;

            switch (realRecipientCard.getExchange().toString()) {
                case "UAH":
                    lastAmount = amount / uah;
                    break;
                case "USD":
                    lastAmount = amount / usd;
                    break;
                case "EUR":
                    lastAmount = amount / eur;
                    break;
            }

            realRecipientCard.setAmount(realRecipientCard.getAmount() + lastAmount);
            cardRepository.save(realRecipientCard);

            realDonorCard.setAmount(realDonorCard.getAmount() - amount);
            cardRepository.save(realDonorCard);

            Transaction transaction = new Transaction(realDonorCard, realRecipientCard, exchanges, lastAmount);
            transactionRepository.save(transaction);
            return true;
        } else {
            System.out.println("different pinCodes");
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsIn(String number, Pageable pageable) {return transactionRepository.findTransactionInByCardNumber(number, pageable); }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsOut(String number, Pageable pageable) {return transactionRepository.findTransactionOutByCardNumber(number, pageable); }
}
