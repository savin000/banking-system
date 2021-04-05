package com.savin.entities;

import com.savin.bank.CashOffice;
import com.savin.enums.BankingOperationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a bank teller
 */
public class Teller implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Queue<Client> clients;

    private final CashOffice cashOffice;

    private String name;

    public Queue<Client> getClients() {
        return clients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teller(CashOffice cashOffice, String name) {
        this.cashOffice = cashOffice;
        this.name = name;
        clients = new ArrayDeque<>();
    }

    @Override
    public void run() {
        LOGGER.info("Teller {} started his work", name);
        while (true) {
            try {
                synchronized (clients) {
                    if (!clients.isEmpty()) {
                        Client client = clients.poll();
                        if (client.getBankingOperationType().equals(BankingOperationType.WITHDRAW)) {
                            cashOutAnAccount(client.getTransactionAmount(), client.getServicingTime());
                        } else if (client.getBankingOperationType().equals(BankingOperationType.DEPOSIT)) {
                            topUpAnAccount(client.getTransactionAmount(), client.getServicingTime());
                        }
                    } else {
                        LOGGER.info("No clients at teller {}", name);
                        clients.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace(); //TODO:
            }
        }
    }

    private void topUpAnAccount(double transactionAmount, LocalTime servicingTime) throws InterruptedException {
        Thread.sleep(TimeUnit.SECONDS.toMillis(servicingTime.getSecond()));
        cashOffice.setAccount(cashOffice.getAccount() + transactionAmount);
        LOGGER.info("Teller {} served a client in {}. Account: {}$", name, servicingTime, cashOffice.getAccount());
    }

    private void cashOutAnAccount(double transactionAmount, LocalTime servicingTime) throws InterruptedException {
        if (cashOffice.getAccount() > transactionAmount) {
            Thread.sleep(TimeUnit.SECONDS.toMillis(servicingTime.getSecond()));
            cashOffice.setAccount(cashOffice.getAccount() - transactionAmount);
            LOGGER.info("Teller {} served a client in {}. Account: {}$", name, servicingTime, cashOffice.getAccount());
        } else {
            LOGGER.info("Client is denied");
        }
    }
}
