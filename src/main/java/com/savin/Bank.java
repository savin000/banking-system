package com.savin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bank {
    private static final int CLIENTS_PER_MINUTE = 60;

    public static void main(String[] args) {
        ScheduledExecutorService executorService1 = Executors.newScheduledThreadPool(BankConstants.NUMBER_OF_TELLERS);
        CashOffice cashOffice = new CashOffice();
        List<Teller> tellers = new ArrayList<>();
        for (int i = 0; i < BankConstants.NUMBER_OF_TELLERS; i++) {
            Teller teller = new Teller(cashOffice, String.valueOf(i + 1));
            executorService1.submit(teller);
            //executorService1.scheduleAtFixedRate(teller, 0, 1, TimeUnit.SECONDS);
            //executorService1.execute(teller);
            tellers.add(teller);
        }

        ScheduledExecutorService executorService2 = Executors.newScheduledThreadPool(1);
        Runnable clientsGenerator = new ClientsGenerator(cashOffice, tellers);
        executorService2.scheduleAtFixedRate(clientsGenerator,
                0, 60 / CLIENTS_PER_MINUTE, TimeUnit.SECONDS);
    }
}
