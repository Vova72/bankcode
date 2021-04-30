package com.vovarusskih72.bankcode.repositories;

import com.vovarusskih72.bankcode.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT tr FROM Transaction tr")
    List<Transaction> findAllTransactionOut(Pageable pageable);

    @Query("SELECT tr FROM Transaction tr")
    List<Transaction> findAllTransactionIn(Pageable pageable);

    @Query("SELECT tr FROM Transaction tr WHERE tr.cardDonor.wallet.walletName = :walletName OR tr.cardRecipient.wallet.walletName = :walletName ORDER BY tr.date DESC")
    List<Transaction> findAllByWalletName(@Param("walletName") String walletName, Pageable pageable);

    @Query("SELECT COUNT(tr) FROM Transaction tr WHERE tr.cardDonor.wallet.walletName = :walletName OR tr.cardRecipient.wallet.walletName = :walletName")
    public Long count(@Param("walletName") String walletName);
}
