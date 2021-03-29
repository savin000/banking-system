package com.savin;

import com.savin.enums.BankingOperationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * банк. кассир (служащий банка или иного подобного учреждения,
 * принимающий и выплачивающий клиентам наличные деньги, напр., принимающий депозиты и оплачивающий чеки)
 */
public class Teller implements Runnable {
    private static final Logger LOG = LogManager.getLogger();

    private final Queue<Client> clients;

    private CashOffice cashOffice;

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
        LOG.info("Teller " + name + " started his work");
        while (true) {
            synchronized (clients) {
                if (!clients.isEmpty()) {
                    Client client = clients.poll();
                    if (client.getBankingOperationType().equals(BankingOperationType.WITHDRAW)) {
                        try {
                            cashOutAnAccount(client.getTransactionAmount(), client.getServicingTime());
                        } catch (InterruptedException e) { //TODO: CORRECT THIS SHIT! MAKE SINGLE ONE TRY CATCH
                            e.printStackTrace();
                        }
                    } else if (client.getBankingOperationType().equals(BankingOperationType.DEPOSIT)) {
                        try {
                            topUpAnAccount(client.getTransactionAmount(), client.getServicingTime());
                        } catch (InterruptedException e) {
                            e.printStackTrace(); //TODO: CORRECT THIS SHIT!
                        }
                    }
                }
                else {

                }
            }
        }
    }

    private synchronized void topUpAnAccount(double transactionAmount, LocalTime servicingTime) throws InterruptedException {
        synchronized (cashOffice) {
            cashOffice.wait();
            Thread.sleep(servicingTime.getLong(ChronoField.SECOND_OF_DAY));
            cashOffice.setAccount(cashOffice.getAccount() + transactionAmount);
            cashOffice.notify();
            LOG.info("Teller {} served a client in {}. Account: " + cashOffice.getAccount() + "$", name, servicingTime);
        }
    }

    private synchronized void cashOutAnAccount(double transactionAmount, LocalTime servicingTime) throws InterruptedException {
        synchronized (cashOffice) {
            if (cashOffice.getAccount() > transactionAmount) {
                cashOffice.wait();
                Thread.sleep(servicingTime.getLong(ChronoField.SECOND_OF_DAY));
                cashOffice.setAccount(cashOffice.getAccount() - transactionAmount);
                cashOffice.notify();
                LOG.info("Teller {} served a client in {}. Account: " + cashOffice.getAccount() + "$", name, servicingTime);
            } else {
                LOG.info("Client is denied");
            }
        }
    }
}
