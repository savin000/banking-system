package com.savin.bank;

import com.savin.entities.Teller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a bank with tellers and cash office
 *
 * @author Mikhail Savin
 */
public class Bank {

    /**
     * Cash office of the bank
     */
    private final CashOffice cashOffice;

    /**
     * A list of working tellers
     */
    private final List<Teller> tellers;

    /**
     * Creates a new Bank
     */
    public Bank() {
        this.cashOffice = new CashOffice();
        tellers = new ArrayList<>();

        for (int i = 0; i < BankConstants.NUMBER_OF_TELLERS; i++) {
            Teller teller = new Teller(cashOffice, String.valueOf(i + 1));
            new Thread(teller).start();
            tellers.add(teller);
        }
    }

    /**
     * Creates a new Bank with the specified amount of money in the bank and number of tellers
     *
     * @param bankAccount specified bank account (amount of money in the bank)
     * @param numberOfTellers specified number of tellers
     */
    public Bank(int bankAccount, int numberOfTellers) {
        this.cashOffice = new CashOffice(bankAccount);
        tellers = new ArrayList<>();

        for (int i = 0; i < numberOfTellers; i++) {
            Teller teller = new Teller(cashOffice, String.valueOf(i + 1));
            new Thread(teller).start();
            tellers.add(teller);
        }
    }

    /**
     * This method decides which teller to refer the client to. If the number of clients for all tellers is the same,
     * the client is referred to a random worker. Otherwise, the client is referred to a teller with a minimum number
     * of clients in the queue
     *
     * @return chosen teller
     */
    public Teller chooseTeller() {
        Random random = new Random();
        Teller chosenTeller = tellers.get(random.nextInt(tellers.size()));
        int minimumClients = chosenTeller.getClients().size();

        for (Teller teller : tellers) {
            if (teller.getClients().size() < minimumClients) {
                minimumClients = teller.getClients().size();
                chosenTeller = teller;
            }
        }
        return chosenTeller;
    }
}