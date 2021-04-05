package com.savin.entities;

import com.savin.enums.BankingOperationType;

import java.time.LocalTime;

/**
 * This class represents bank client entity
 *
 * @author Mikhail Savin
 */
public class Client {

    /**
     * Client's banking operation (e.g. withdraw, deposit)
     */
    private BankingOperationType bankingOperationType;

    /**
     * The amount of money the client is operating
     */
    private double transactionAmount;

    /**
     * The time it takes to service a client
     */
    private LocalTime servicingTime;

    /**
     * Creates a new Client with the given banking operation type, transaction amount and servicing time
     *
     * @param bankingOperationType client's banking operation
     * @param transactionAmount the amount of money the client is operating
     * @param servicingTime client's servicing time
     */
    public Client(BankingOperationType bankingOperationType, double transactionAmount, LocalTime servicingTime) {
        this.bankingOperationType = bankingOperationType;
        this.transactionAmount = transactionAmount;
        this.servicingTime = servicingTime;
    }

    /**
     * @return this client's banking operation type
     */
    public BankingOperationType getBankingOperationType() {
        return bankingOperationType;
    }

    /**
     * @param bankingOperationType client's banking operation type to set
     */
    public void setBankingOperationType(BankingOperationType bankingOperationType) {
        this.bankingOperationType = bankingOperationType;
    }

    /**
     * @return this client's amount of money
     */
    public double getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * @param transactionAmount client's transaction amount to set
     */
    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * @return this client's servicing time
     */
    public LocalTime getServicingTime() {
        return servicingTime;
    }

    /**
     * @param servicingTime client's servicing time to set
     */
    public void setServicingTime(LocalTime servicingTime) {
        this.servicingTime = servicingTime;
    }
}
