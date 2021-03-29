package com.savin;

public class CashOffice {

    private static final double DEFAULT_ACCOUNT = 100000; // 100_000$

    private double account;

    public CashOffice() {
        this.account = DEFAULT_ACCOUNT;
    }

    public CashOffice(double account) {
        this.account = account;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }
}