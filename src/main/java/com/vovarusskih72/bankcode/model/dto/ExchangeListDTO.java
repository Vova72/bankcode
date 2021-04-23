package com.vovarusskih72.bankcode.model.dto;

import java.util.List;

public class ExchangeListDTO {
    private List<ExchangeDTO> exchangeRate;

    public ExchangeListDTO(List<ExchangeDTO> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public List<ExchangeDTO> getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(List<ExchangeDTO> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return "ExchangeListDTO{" +
                "exchangeRate=" + exchangeRate +
                '}';
    }
}
