package com.vovarusskih72.bankcode.model.dto;

public class TransactionDTO {

    private String donorNumber;
    private String pinCode;
    private String recipientNumber;
    private double amount;
    private String comment;
    private String type;

    public TransactionDTO(String donorNumber, String pinCode, String recipientNumber, double amount, String comment, String type) {
        this.donorNumber = donorNumber;
        this.pinCode = pinCode;
        this.recipientNumber = recipientNumber;
        this.amount = amount;
        this.comment = comment;
        this.type = type;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "donorNumber='" + donorNumber + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", recipientNumber='" + recipientNumber + '\'' +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
