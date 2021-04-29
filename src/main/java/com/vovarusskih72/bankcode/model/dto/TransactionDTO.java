package com.vovarusskih72.bankcode.model.dto;

public class TransactionDTO {

    private String donorNumber;
    private String pinCode;
    private String recipientNumber;
    private double amount;

    public TransactionDTO(String donorNumber, String pinCode, String recipientNumber, double amount) {
        this.donorNumber = donorNumber;
        this.pinCode = pinCode;
        this.recipientNumber = recipientNumber;
        this.amount = amount;
    }

    public String getDonorNumber() {
        return donorNumber;
    }

    public void setDonorNumber(String donorNumber) {
        this.donorNumber = donorNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getRecipientNumber() {
        return recipientNumber;
    }

    public void setRecipientNumber(String recipientNumber) {
        this.recipientNumber = recipientNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "donorNumber='" + donorNumber + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", recipientNumber='" + recipientNumber + '\'' +
                ", amount=" + amount +
                '}';
    }
}
