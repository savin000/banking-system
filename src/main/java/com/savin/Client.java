package com.savin;

import com.savin.enums.BankingOperationType;

import java.time.LocalTime;

public class Client {
    private BankingOperationType bankingOperationType;

    private double transactionAmount;

    private LocalTime servicingTime;

    public Client(BankingOperationType bankingOperationType, double transactionAmount, LocalTime servicingTime) {
        this.bankingOperationType = bankingOperationType;
        this.transactionAmount = transactionAmount;
        this.servicingTime = servicingTime;
    }

    public BankingOperationType getBankingOperationType() {
        return bankingOperationType;
    }

    public void setBankingOperationType(BankingOperationType bankingOperationType) {
        this.bankingOperationType = bankingOperationType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalTime getServicingTime() {
        return servicingTime;
    }

    public void setServicingTime(LocalTime servicingTime) {
        this.servicingTime = servicingTime;
    }
}
