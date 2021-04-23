package com.vovarusskih72.bankcode.repositories;

import com.vovarusskih72.bankcode.model.Card;
import com.vovarusskih72.bankcode.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByAccountEmail(String email);
    Wallet findByAccountPhone(String phone);
    Wallet findByAccountLogin(String login);

}
