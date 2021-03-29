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

    private Queue<Client> clients;

    private CashOffice cashOffice;

    public Queue<Client> getClients() {
        return clients;
    }

    public Teller(CashOffice cashOffice) {
        this.cashOffice = cashOffice;
        clients = new ArrayDeque<>();
    }

    @Override
    public void run() {
        LOG.info("Teller " + Thread.currentThread().getName() + " started his work");
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
    }

    private synchronized void topUpAnAccount(double transactionAmount, LocalTime servicingTime) throws InterruptedException {
        this.wait(servicingTime.getLong(ChronoField.SECOND_OF_DAY));
        cashOffice.setAccount(cashOffice.getAccount() + transactionAmount);
        LOG.info("Денег в банке: " + cashOffice.getAccount());
        notify();
    }

    private synchronized void cashOutAnAccount(double transactionAmount, LocalTime servicingTime) throws InterruptedException {
        if (cashOffice.getAccount() > transactionAmount) {
            wait(servicingTime.getLong(ChronoField.SECOND_OF_DAY));
            cashOffice.setAccount(cashOffice.getAccount() - transactionAmount);
            LOG.info("Денег в банке: " + cashOffice.getAccount());
        } else {
            LOG.info("Клиенту отказано в обслуживании");
        }
        notify();
    }
}
