package com.vovarusskih72.bankcode.repositories;

import com.vovarusskih72.bankcode.model.ExchangeTable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExchangeTableRepository extends JpaRepository<ExchangeTable, Long> {
    ExchangeTable findExchangeTableByDate(String date);

    @Query("SELECT ex FROM ExchangeTable ex")
    List<ExchangeTable> findAllByPage(Pageable pageable);

    boolean existsExchangeTableByDate(String date);

    Long countAllByUahSale(double uahSale);
//    List<ExchangeTable> findExchangeTablesByPeriod(String fromDate, String toDate);
}
