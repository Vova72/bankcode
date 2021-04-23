package com.vovarusskih72.bankcode.repositories;

import com.vovarusskih72.bankcode.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Account findAccountByLogin(String login);
    Account findAccountByEmail(String email);
    Account findAccountByPhone(String phone);
    Account findAccountByActivateCode(String activateCode);
}
