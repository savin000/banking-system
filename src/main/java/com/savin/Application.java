package com.savin;

import com.savin.bank.Bank;
import com.savin.bank.BankConstants;
import com.savin.bank.CashOffice;
import com.savin.bank.ClientsGenerator;
import com.savin.entities.Teller;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
//        ExecutorService executorService1 = Executors.newFixedThreadPool(BankConstants.NUMBER_OF_TELLERS);
//        CashOffice cashOffice = new CashOffice();
//        List<Teller> tellers = new ArrayList<>();
//        for (int i = 0; i < BankConstants.NUMBER_OF_TELLERS; i++) {
//            Teller teller = new Teller(cashOffice, String.valueOf(i + 1));
//            tellers.add(teller);
//            executorService1.submit(teller);
//            //executorService1.execute(teller);
//        }

        Bank bank = new Bank();
        for (int i = 0; i < BankConstants.NUMBER_OF_TELLERS; i++) {
            Teller teller = new Teller(cashOffice, String.valueOf(i + 1));
            new Thread(teller,  String.valueOf(i + 1)).start();
            tellers.add(teller);
        }

//        ScheduledExecutorService executorService2 = Executors.newScheduledThreadPool(1);
//        Runnable clientsGenerator = new ClientsGenerator(cashOffice, tellers);
//        executorService2.scheduleAtFixedRate(clientsGenerator,
//                0, 60 / CLIENTS_PER_MINUTE, TimeUnit.SECONDS);


        Runnable clientsGenerator = new ClientsGenerator(cashOffice, tellers);
        new Thread(clientsGenerator).start();
    }
}
