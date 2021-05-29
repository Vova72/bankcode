package com.vovarusskih72.bankcode.services;

import com.google.gson.Gson;
import com.vovarusskih72.bankcode.model.ExchangeTable;
import com.vovarusskih72.bankcode.model.dto.ExchangeDTO;
import com.vovarusskih72.bankcode.model.dto.ExchangeListDTO;
import com.vovarusskih72.bankcode.repositories.ExchangeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ExchangeTableComponent {

    @Autowired
    private ExchangeTableRepository exchangeTableRepository;

    @Scheduled(fixedDelay = 86400000)
    public void saveExchange() throws IOException {

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String today = format.format(new Date());
        if(!exchangeTableRepository.existsExchangeTableByDate(today)) {
            System.out.println(today);
            String urlApi = "https://api.privatbank.ua/p24api/exchange_rates?json&date=" + today;
            URL url = new URL(urlApi);

            Gson gson = new Gson();

            InputStreamReader readerIn = new InputStreamReader(url.openStream());
            BufferedReader reader = new BufferedReader(readerIn);
            StringBuffer sb = new StringBuffer();
            String str;
            while((str = reader.readLine())!= null){
                sb.append(str);
            }
            ExchangeListDTO text = gson.fromJson(sb.toString(), ExchangeListDTO.class);
            List<ExchangeDTO> exchanges = text.getExchangeRate();
            ExchangeTable exchangeTable = new ExchangeTable();
            exchangeTable.setDate(today);
            exchangeTable.setUahBuy(1d);
            exchangeTable.setUahSale(1d);
            for(ExchangeDTO exchange : exchanges) {
                if(exchange.getCurrency() != null) {
                    if (exchange.getCurrency().equals("USD")) {
                        exchangeTable.setUsdBuy(exchange.getPurchaseRateNB());
                        exchangeTable.setUsdSale(exchange.getSaleRateNB());
                    }
                    if (exchange.getCurrency().equals("EUR")) {
                        exchangeTable.setEurBuy(exchange.getPurchaseRateNB());
                        exchangeTable.setEurSale(exchange.getSaleRateNB());
                    }
                }
            }
            System.out.println(exchangeTable.toString());

            exchangeTableRepository.save(exchangeTable);
    }
    }
}
