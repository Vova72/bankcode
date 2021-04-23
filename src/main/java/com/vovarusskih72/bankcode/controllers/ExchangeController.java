package com.vovarusskih72.bankcode.controllers;

import com.vovarusskih72.bankcode.model.ExchangeTable;
import com.vovarusskih72.bankcode.model.PagesAllCount;
import com.vovarusskih72.bankcode.services.ExchangeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExchangeController {

    @Autowired
    private ExchangeTableService exchangeTableService;

    private static final Integer EXCHANGE_SIZE = 14;

    @RequestMapping("/exchangetoday")
    public ExchangeTable getToday() {
        return exchangeTableService.getTodayExchange();
    }

    @RequestMapping("/exchangelist")
    public List<ExchangeTable> getExchangeList(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        return exchangeTableService.getExchangeList(PageRequest.of(page, EXCHANGE_SIZE, Sort.Direction.DESC, "id"));
    }

    @RequestMapping("/countexchanges")
    public PagesAllCount countPages() {
        Long pages = exchangeTableService.countExchanges();
        return new PagesAllCount(pages);
    }
}
