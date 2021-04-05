package com.savin;

import com.savin.bank.Bank;
import com.savin.bank.BankConstants;
import com.savin.bank.CashOffice;
import com.savin.bank.ClientsGenerator;
import com.savin.entities.Teller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class runs banking-system imitation
 *
 * @author Mikhail Savin
 */
public class Application {
    public static void main(String[] args) {
        Bank bank = new Bank();

        ScheduledExecutorService executorService2 = Executors.newScheduledThreadPool(1);
        Runnable clientsGenerator = new ClientsGenerator(bank);
        executorService2.scheduleAtFixedRate(clientsGenerator,
                0, 60 / BankConstants.CLIENTS_PER_MINUTE, TimeUnit.SECONDS);
    }
}
