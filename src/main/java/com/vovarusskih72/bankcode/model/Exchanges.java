package com.vovarusskih72.bankcode.model;

public enum Exchanges {
    UAH, USD, EUR;

    @Override
    public String toString() {
        return name();
    }
}
