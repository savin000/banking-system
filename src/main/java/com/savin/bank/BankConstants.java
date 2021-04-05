package com.savin.bank;

/**
 * This class stores constants for classes in the bank package
 *
 * @author Mikhail Savin
 */
public final class BankConstants {

    /**
     * Default number of tellers in the bank
     */
    public static final int NUMBER_OF_TELLERS = 3;

    /**
     * This is used as a core for generating client's service time
     */
    public static final int SERVICE_TIME = 10;

    /**
     * This is used as a core for generating client's transaction amount.
     * The value is represented in $
     */
    public static final double TRANSACTION_AMOUNT = 1000;

    /**
     * Default amount of money in bank's cash office (100_000$)
     */
    public static final double DEFAULT_ACCOUNT = 100000;

    /**
     * Number of clients per minute
     */
    public static final int CLIENTS_PER_MINUTE = 60;

    /**
     * Boundary for generating client's service time
     */
    public static final int SERVICE_TIME_BOUNDARY = 5;

    /**
     * Boundary for generating client's transaction amount
     */
    public static final int TRANSACTION_AMOUNT_BOUNDARY = 1000; // 1000$
}