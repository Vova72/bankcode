package com.vovarusskih72.bankcode.services;

import com.vovarusskih72.bankcode.model.Account;
import com.vovarusskih72.bankcode.model.UserRoles;
import com.vovarusskih72.bankcode.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public boolean addAccount(String login, String name, String surname, String password, String phone, String email, String activatedCode, UserRoles role) {
        if(accountRepository.existsByEmail(email) || accountRepository.existsByPhone(phone) || accountRepository.existsByLogin(login)) {
            return false;
        }
        Account account = new Account(login, name, surname, password, phone, email, activatedCode, role);
        accountRepository.save(account);

        return true;
    }

    @Transactional
    public void activateAccount(String code) {
        Account account = accountRepository.findAccountByActivateCode(code);
        account.setRole(UserRoles.USER);
        account.setActivateCode(null);
        accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account findByLogin(String login) {
        return accountRepository.findAccountByLogin(login);
    }


}
