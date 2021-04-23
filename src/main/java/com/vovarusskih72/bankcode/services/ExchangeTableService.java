package com.vovarusskih72.bankcode.services;

import com.vovarusskih72.bankcode.model.ExchangeTable;
import com.vovarusskih72.bankcode.repositories.ExchangeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExchangeTableService {

    @Autowired
    private ExchangeTableRepository exchangeTableRepository;

    @Transactional(readOnly = true)
    public ExchangeTable getTodayExchange() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String today = format.format(new Date());
        return exchangeTableRepository.findExchangeTableByDate(today);
    }

    @Transactional(readOnly = true)
    public List<ExchangeTable> getExchangeList(Pageable pageable) {
        return exchangeTableRepository.findAllByPage(pageable);
    }

    @Transactional(readOnly = true)
    public Long countExchanges() {
        return exchangeTableRepository.countAllByUahSale(1);
    }

}
