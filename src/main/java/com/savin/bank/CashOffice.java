package com.savin.bank;

/**
 * This class represents cash office of the bank
 *
 * @author Mikhail Savin
 */
public class CashOffice {

    /**
     * The amount of money in the bank
     */
    private double account;

    /**
     * Creates a new Cash office with default account
     */
    public CashOffice() {
        this.account = BankConstants.DEFAULT_ACCOUNT;
    }

    /**
     * Creates a new Repository with the specified amount of money in the bank (account)
     *
     * @param account specified account
     */
    public CashOffice(double account) {
        this.account = account;
    }

    /**
     * @return current account
     */
    public double getAccount() {
        return account;
    }

    /**
     * @param account cash office's account to set
     */
    public void setAccount(double account) {
        this.account = account;
    }
}