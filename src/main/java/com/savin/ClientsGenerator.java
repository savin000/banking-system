package com.savin;

import com.savin.enums.BankingOperationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

public class ClientsGenerator implements Runnable {
    private static final Logger LOG = LogManager.getLogger();

    private static final int SERVICE_TIME_BOUND = 5;

    private static final int TRANSACTION_AMOUNT_BOUND = 1000; // 1000$

    private CashOffice cashOffice;

    private List<Teller> tellers;

    public ClientsGenerator(CashOffice cashOffice, List<Teller> tellers) {
        this.cashOffice = cashOffice;
        this.tellers = tellers;
    }

    public CashOffice getCashOffice() {
        return cashOffice;
    }

    public void setCashOffice(CashOffice cashOffice) {
        this.cashOffice = cashOffice;
    }

    public List<Teller> getTellers() {
        return tellers;
    }

    public void setTellers(List<Teller> tellers) {
        this.tellers = tellers;
    }

    private BankingOperationType generateBankingOperationType() {
        if (Math.random() < 0.5) {
            return BankingOperationType.WITHDRAW;
        } else {
            return BankingOperationType.DEPOSIT;
        }
    }

    private double generateTransactionAmount() {
        return BankConstants.TRANSACTION_AMOUNT +
                Math.random() * (TRANSACTION_AMOUNT_BOUND + TRANSACTION_AMOUNT_BOUND) - TRANSACTION_AMOUNT_BOUND;
    }

    private LocalTime generateServicingTime() {
        Random random = new Random();
        return LocalTime.ofSecondOfDay(BankConstants.SERVICE_TIME +
                random.nextInt(SERVICE_TIME_BOUND + SERVICE_TIME_BOUND) - SERVICE_TIME_BOUND);
    }

    @Override
    public void run() {
        Client client = new Client(generateBankingOperationType(), generateTransactionAmount(), generateServicingTime());

        int minimumClients = 50;
        Random random = new Random();

        Teller chosenTeller = tellers.get(random.nextInt(tellers.size()));

        for (Teller teller : tellers) {
            if (teller.getClients().size() < minimumClients) {
                minimumClients = teller.getClients().size();
                chosenTeller = teller;
            }
        }
        chosenTeller.getClients().add(client);
        LOG.info("Client added [operation: {}, transaction: {}, servicing time: {}] to Teller {}",
                client.getBankingOperationType(), client.getTransactionAmount(), client.getServicingTime(),
                chosenTeller.getName());
    }
}
