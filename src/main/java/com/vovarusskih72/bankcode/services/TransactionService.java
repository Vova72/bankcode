package com.vovarusskih72.bankcode.services;

import com.vovarusskih72.bankcode.model.Card;
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

    @Transactional
    public boolean makeNewTransaction(String donorCard, String pinCode, String recipientCard, String exchange, double amount) {
        if(cardService.checkPin(donorCard, pinCode)) {
            Exchanges exchanges = Exchanges.UAH;
            switch (exchange) {
                case "UAH":
                    exchanges = Exchanges.UAH;
                    break;
                case "USD":
                    exchanges = Exchanges.USD;
                    break;
                case "EUR":
                    exchanges = Exchanges.EUR;
                    break;
            }


            Card realDonorCard = cardService.getCardByNumber(donorCard);
            realDonorCard.setAmount(realDonorCard.getAmount() - amount);
            cardRepository.save(realDonorCard);

            Card realRecipientCard = cardService.getCardByNumber(recipientCard);
            realRecipientCard.setAmount(realRecipientCard.getAmount() + amount);
            cardRepository.save(realDonorCard);

            Transaction transaction = new Transaction(realDonorCard, realRecipientCard, exchanges, amount);
            transactionRepository.save(transaction);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsIn(String number, Pageable pageable) {return transactionRepository.findTransactionInByCardNumber(number, pageable); }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsOut(String number, Pageable pageable) {return transactionRepository.findTransactionOutByCardNumber(number, pageable); }
}
