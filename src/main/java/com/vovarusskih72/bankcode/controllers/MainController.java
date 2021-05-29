package com.vovarusskih72.bankcode.controllers;

import com.vovarusskih72.bankcode.model.*;
import com.vovarusskih72.bankcode.model.dto.AccountDTO;
import com.vovarusskih72.bankcode.model.dto.CardDTO;
import com.vovarusskih72.bankcode.model.dto.MainPageInfo;
import com.vovarusskih72.bankcode.model.dto.TransactionDTO;
import com.vovarusskih72.bankcode.model.dto.results.BadRequestResult;
import com.vovarusskih72.bankcode.model.dto.results.ErrorInformation;
import com.vovarusskih72.bankcode.model.dto.results.ResultDTO;
import com.vovarusskih72.bankcode.model.dto.results.SuccessResult;
import com.vovarusskih72.bankcode.repositories.AccountRepository;
import com.vovarusskih72.bankcode.repositories.CardRepository;
import com.vovarusskih72.bankcode.repositories.MailRepository;
import com.vovarusskih72.bankcode.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ExchangeTableService exchangeTableService;

    @Autowired
    private CardService cardService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MailRepository mailRepository;

    private final static int CARD_SIZE = 6;
    private final static int TRANS_SIZE = 10;

    @RequestMapping("/account")
    public AccountDTO account() {
        User user = getCurrentAccount();
        Account account = accountService.findByLogin(user.getUsername());
        AccountDTO accountDTO = new AccountDTO(account.getLogin(), account.getName(), account.getSurname(), account.getEmail(), account.getPhone());
        return accountDTO;
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public ResponseEntity<ResultDTO> addAccount(HttpServletRequest http, @RequestBody Account accountJson) throws MessagingException {
        Account account = accountJson;
        if(accountRepository.existsByLogin(account.getLogin()) || accountRepository.existsByEmail(account.getEmail())) {
            System.out.println("Error: Failed to create account");
            return new ResponseEntity<>(new BadRequestResult(), HttpStatus.BAD_REQUEST);
        }
        System.out.println(account);
        String passHash = passwordEncoder.encode(account.getPassword());
        String code = passwordEncoder.encode(account.getLogin() + account.getName() + account.getSurname() + account.getPassword());
        Account accountSave = new Account(account.getLogin(), account.getName(), account.getSurname(), passHash, account.getPhone(), account.getEmail(), code, UserRoles.USER);
        EmailSender emailSender = new EmailSender(http.getHeader("host"), account.getEmail(), code);
        mailRepository.save(emailSender);
        System.out.println("Success: Create account");
        accountRepository.save(accountSave);
        return new ResponseEntity<>(new SuccessResult(), HttpStatus.OK);
    }

    @RequestMapping(value = "/newcard", method = RequestMethod.POST)
    public void newCard(@RequestBody CardDTO card) {
        System.out.println(card.getExchange() + " | " + card.getPinCode());
        Exchanges exchanges = Exchanges.UAH;
        switch (card.getExchange()) {
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
        cardService.addCard(getCurrentWallet(), exchanges, 100000, passwordEncoder.encode(card.getPinCode()));
    }

    @RequestMapping(value = "/updateuser")
    public void updateUser(@RequestBody AccountDTO account) {
        String login = getCurrentAccount().getUsername();
        accountService.updateAccount(login, account.getName(), account.getSurname(), account.getPhone());
    }

    @RequestMapping(value = "/cardlist")
    public List<CardDTO> getCardList(@RequestParam(defaultValue = "0", required = false) Integer page) {
        Wallet wallet = getCurrentWallet();
        List<CardDTO> cards = walletService.getAllCards(wallet.getWalletName(),
                                                        PageRequest.of(page, CARD_SIZE, Sort.Direction.DESC, "id"));
        return cards;
    }

    @RequestMapping(value = "/maketransaction", method = RequestMethod.POST)
    public ErrorInformation makeTransaction(@RequestBody TransactionDTO transaction) {
        System.out.println(transaction.toString());
        transaction.setDonorNumber(transaction.getDonorNumber().replace(" ", ""));
        String pinCode = transaction.getPinCode();
        System.out.println("-----------------------------" + transaction.getRecipientNumber() + " | " + transaction.getAmount());
        String check = transactionService.makeNewTransaction(transaction.getDonorNumber(), pinCode, transaction.getRecipientNumber(), transaction.getAmount(), transaction.getComment());
        return new ErrorInformation(check);
        //        if(check) {
//            System.out.println("Pin code right");
//            return true;
//            return new ResponseEntity<>(new SuccessResult(), HttpStatus.OK);
//        } else {
//            System.out.println("Pin code wrong");
//            return false;
//            return new ResponseEntity<>(new BadRequestResult(), HttpStatus.BAD_REQUEST);
//        }
    }

    @RequestMapping(value = "/checkaccountemail", method = RequestMethod.POST)
    public ResponseEntity<ResultDTO> resetPassword(HttpServletRequest http, @RequestBody Account accountJson) throws MessagingException {
        String email = accountJson.getEmail();
        boolean accountBool = accountRepository.existsByEmail(email);
        if(accountBool) {
            Account account = accountRepository.findAccountByEmail(email);
            String code = passwordEncoder.encode(account.getPassword() + account.getName() + email);
            account.setActivateCode(code);
            sendEmailToReset(http.getHeader("host"), email, code);
            return new ResponseEntity<>(new SuccessResult(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BadRequestResult(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/setnewpassword")
    public ResponseEntity<ResultDTO> setNewPassword(@RequestBody Account accountJson) {
        Account account = accountRepository.findAccountByActivateCode(accountJson.getActivateCode());
        if (account == null) {
            return new ResponseEntity<>(new BadRequestResult(), HttpStatus.BAD_REQUEST);
        }
        account.setPassword(passwordEncoder.encode(accountJson.getPassword()));
        account.setActivateCode(null);
        accountRepository.save(account);
        return new ResponseEntity<>(new SuccessResult(), HttpStatus.OK);
    }

    @RequestMapping(value = "/transactioninlist")
    public List<TransactionDTO> getTransactionInList(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        List<TransactionDTO> transactions = transactionService.getTransactionsIn(PageRequest.of(page, TRANS_SIZE, Sort.Direction.DESC, "id"));
        return transactions;
    }

    @RequestMapping(value = "/transactionoutlist")
    public List<TransactionDTO> getTransactionOutList(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        List<TransactionDTO> transactions = transactionService.getTransactionsOut(PageRequest.of(page, TRANS_SIZE, Sort.Direction.DESC, "id"));
        return transactions;
    }

    @RequestMapping(value = "/alltransactions")
    public List<TransactionDTO> getAllTransactions(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        String walletName = getCurrentWallet().getWalletName();
        List<TransactionDTO> transaction = transactionService.getAllTransactions(walletName, PageRequest.of(page, TRANS_SIZE, Sort.Direction.DESC, "id"));
        return transaction;
    }

    @RequestMapping(value = "/counttransactions")
    public PagesAllCount getTransactionsCount() {
        String walletName = getCurrentWallet().getWalletName();
        return new PagesAllCount(transactionService.countTransactions(walletName));
    }

    public User getCurrentAccount() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Wallet getCurrentWallet() {
        User user = getCurrentAccount();
        Account account = accountService.findByLogin(user.getUsername());
        Wallet wallet = account.getWallet();
        return wallet;
    }

    public void sendEmailToReset(String host, String email, String code) throws MessagingException {
        MimeMessage mm = mailSender.createMimeMessage();
        MimeMessageHelper msg = new MimeMessageHelper(mm);
        msg.setTo(email);

        String htmlText = "<a href='http://"+ host + "/resetpassword.html?code=" + code + "'>Click here to change account password</a>";
        msg.setSubject("Change account password");
        msg.setText(htmlText, true);

        mailSender.send(mm);
    }

    @RequestMapping("/maininfo")
    public MainPageInfo getMainInfo() {
        ExchangeTable exchangeToday = exchangeTableService.getTodayExchange();
        Wallet wallet = getCurrentWallet();
        Long cardsCount = cardService.count(wallet.getWalletName());
        return new MainPageInfo(cardsCount, exchangeToday.getUsdBuy(), exchangeToday.getUsdSale(), exchangeToday.getEurBuy(), exchangeToday.getEurSale());
    }

    @RequestMapping("/cardsnumbers")
    public List<String> getCardsNumbers() {
        Wallet wallet = getCurrentWallet();
        List<String> numbers = cardService.getAllCardsNumber(wallet.getWalletName());
        return numbers;
    }

    @RequestMapping("/cardcount")
    public PagesAllCount getPagesCount() {
        Wallet wallet = getCurrentWallet();
        Long pages = cardService.count(wallet.getWalletName());
        return new PagesAllCount(pages);
    }
}
