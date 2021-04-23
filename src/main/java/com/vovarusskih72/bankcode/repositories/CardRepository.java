package com.vovarusskih72.bankcode.repositories;

import com.vovarusskih72.bankcode.model.Card;
import com.vovarusskih72.bankcode.model.dto.CardDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByNumber(String number);
    Card findByNumber(String number);
    Long countByWalletWalletName(String walletName);
    @Query("SELECT w FROM Card w WHERE w.wallet.walletName = :walletName")
    List<Card> findCardByWalletName(@Param("walletName") String walletName, Pageable pageable);

    @Query("SELECT w FROM Card w WHERE w.wallet.walletName = :walletName")
    List<Card> findAllByWalletName(@Param("walletName") String walletName);
}
