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
 *
 * @author Mikhail Savin
 */
public class Teller implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * A queue of clients
     */
    private final Queue<Client> clients;

    /**
     * CashOffice instance
     */
    private final CashOffice cashOffice;

    /**
     * Name of the teller
     */
    private String name;

    /**
     * @return this teller's queue of clients
     */
    public Queue<Client> getClients() {
        return clients;
    }

    /**
     * @return teller name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name teller name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Creates Teller instance with the specified cash office and given name
     *
     * @param cashOffice specified cash office
     * @param name specified name
     */
    public Teller(CashOffice cashOffice, String name) {
        this.cashOffice = cashOffice;
        this.name = name;
        clients = new ArrayDeque<>();
    }

    /**
     * This method adds a client to the queue
     *
     * @param client client to add
     */
    public void addClient(Client client) {
        synchronized (clients) {
            clients.add(client);
            clients.notify();
        }
    }

    /**
     * This method imitates teller's work
     */
    @Override
    public void run() {
        LOGGER.info("Teller {} started his work", name);
        while (true) {
                synchronized (clients) {
                    try {
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
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    /**
     * Teller puts money into a bank account
     *
     * @param transactionAmount the amount of money the operation requires
     * @param servicingTime required service time to serve a client
     * @throws InterruptedException if thread is interrupted, either before or during the activity
     */
    private void topUpAnAccount(double transactionAmount, LocalTime servicingTime) throws InterruptedException {
        Thread.sleep(TimeUnit.SECONDS.toMillis(servicingTime.getSecond()));
        cashOffice.setAccount(cashOffice.getAccount() + transactionAmount);
        LOGGER.info("Teller {} served a client in {}. Account: {}$", name, servicingTime, cashOffice.getAccount());
    }

    /**
     * Teller withdraws money from a bank account
     *
     * @param transactionAmount the amount of money the operation requires
     * @param servicingTime required service time to serve a client
     * @throws InterruptedException if thread is interrupted, either before or during the activity
     */
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
