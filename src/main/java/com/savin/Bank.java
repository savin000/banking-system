package com.savin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bank {
    public static void main(String[] args) {
        ExecutorService executorService= Executors.newFixedThreadPool(BankConstants.NUMBER_OF_TELLERS);
        CashOffice cashOffice = new CashOffice();
        List<Teller> tellers = new ArrayList<>();
        for (int i = 0; i < BankConstants.NUMBER_OF_TELLERS; i++) {
            Teller teller = new Teller(cashOffice);
            executorService.execute(teller);
            tellers.add(teller);
        }

        Runnable clientsGenerator = new ClientsGenerator(cashOffice, tellers);
        Thread clientsGeneratorThread = new Thread(clientsGenerator);
        clientsGeneratorThread.start();
    }
}
