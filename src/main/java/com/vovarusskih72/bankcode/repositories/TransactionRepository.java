package com.vovarusskih72.bankcode.repositories;

import com.vovarusskih72.bankcode.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT tr FROM Transaction tr WHERE tr.cardDonor.number = :number")
    List<Transaction> findTransactionOutByCardNumber(@Param("number") String number, Pageable pageable);

    @Query("SELECT tr FROM Transaction tr WHERE tr.cardRecipient.number = :number")
    List<Transaction> findTransactionInByCardNumber(@Param("number") String number, Pageable pageable);
}
