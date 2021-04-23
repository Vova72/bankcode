package com.vovarusskih72.bankcode.model.dto;

public class MainPageInfo {

    private Long cardsCount;
    private double usdBuy;
    private double usdSale;
    private double eurBuy;
    private double eurSale;

    public MainPageInfo(Long cardsCount, double usdBuy, double usdSale, double eurBuy, double eurSale) {
        this.cardsCount = cardsCount;
        this.usdBuy = usdBuy;
        this.usdSale = usdSale;
        this.eurBuy = eurBuy;
        this.eurSale = eurSale;
    }

    public Long getCardsCount() {
        return cardsCount;
    }

    public void setCardsCount(Long cardsCount) {
        this.cardsCount = cardsCount;
    }

    public double getUsdBuy() {
        return usdBuy;
    }

    public void setUsdBuy(double usdBuy) {
        this.usdBuy = usdBuy;
    }

    public double getUsdSale() {
        return usdSale;
    }

    public void setUsdSale(double usdSale) {
        this.usdSale = usdSale;
    }

    public double getEurBuy() {
        return eurBuy;
    }

    public void setEurBuy(double eurBuy) {
        this.eurBuy = eurBuy;
    }

    public double getEurSale() {
        return eurSale;
    }

    public void setEurSale(double eurSale) {
        this.eurSale = eurSale;
    }
}
