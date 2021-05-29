package com.vovarusskih72.bankcode.services;

import com.vovarusskih72.bankcode.model.Card;
import com.vovarusskih72.bankcode.model.ExchangeTable;
import com.vovarusskih72.bankcode.model.Exchanges;
import com.vovarusskih72.bankcode.model.Transaction;
import com.vovarusskih72.bankcode.model.dto.TransactionDTO;
import com.vovarusskih72.bankcode.repositories.CardRepository;
import com.vovarusskih72.bankcode.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public String makeNewTransaction(String donorCard, String pinCode, String recipientCard, double amount, String comment) {
        if (cardService.checkPin(donorCard, pinCode)) {
            if (cardRepository.existsByNumber(recipientCard) && cardRepository.existsByNumber(donorCard)) {
                Exchanges exchanges = Exchanges.UAH;
                System.out.print("Transaction successful");
                Card realDonorCard = cardService.getCardByNumber(donorCard);
                Card realRecipientCard = cardService.getCardByNumber(recipientCard);
                ExchangeTable exchangeTable = exchangeTableService.getTodayExchange();

                if (realDonorCard.getAmount() < amount) {
                    return "wrongAmount";
                }

                double uah = exchangeTable.getUahBuy();
                double usd = exchangeTable.getUsdBuy();
                double eur = exchangeTable.getEurBuy();

                double mainAmount = 0;

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
                        lastAmount = mainAmount / uah;
                        break;
                    case "USD":
                        lastAmount = mainAmount / usd;
                        break;
                    case "EUR":
                        lastAmount = mainAmount / eur;
                        break;
                }

                realRecipientCard.setAmount(realRecipientCard.getAmount() + lastAmount);
                cardRepository.save(realRecipientCard);

                realDonorCard.setAmount(realDonorCard.getAmount() - amount);
                cardRepository.save(realDonorCard);

                Transaction transaction = new Transaction(realDonorCard, realRecipientCard, exchanges, mainAmount, comment);
                transactionRepository.save(transaction);
                return "OK";
            } else {
                return "wrongCard";
            }
        } else {
            System.out.println("different pinCodes");
            return "wrongPin";
        }
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsIn(Pageable pageable) {
        List<Transaction> transactionList = transactionRepository.findAllTransactionIn(pageable);
        List<TransactionDTO> transactions = new ArrayList<>();
        for (Transaction trans : transactionList) {
            transactions.add(new TransactionDTO(trans.getCardDonor().getNumber(), null, trans.getCardRecipient().getNumber(), trans.getAmount(), trans.getComment(), "in"));
        }
        return transactions;
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsOut(Pageable pageable) {
        List<Transaction> transactionList = transactionRepository.findAllTransactionOut(pageable);
        List<TransactionDTO> transactions = new ArrayList<>();
        for(Transaction trans : transactionList) {
            transactions.add(new TransactionDTO(trans.getCardDonor().getNumber(), null, trans.getCardRecipient().getNumber(), trans.getAmount(), trans.getComment(), "out"));
        }
        return transactions;
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getAllTransactions(String walletName, Pageable pageable) {
        List<Transaction> transactionList = transactionRepository.findAllByWalletName(walletName, pageable);
        List<TransactionDTO> transactions = new ArrayList<>();
        List<String> numbers = cardService.getAllCardsNumber(walletName);
        String type = "in";
        for(Transaction trans : transactionList) {
            if(numbers.contains(trans.getCardRecipient().getNumber())) {
                type = "out";
            }
            transactions.add(new TransactionDTO(trans.getCardDonor().getNumber(), null, trans.getCardRecipient().getNumber(), trans.getAmount(), trans.getComment(), type));
        }
        return transactions;
    }

    @Transactional(readOnly = true)
    public Long countTransactions(String walletName) {return transactionRepository.count(walletName); }
}
