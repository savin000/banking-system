package com.savin.bank;

import com.savin.entities.Client;
import com.savin.entities.Teller;
import com.savin.enums.BankingOperationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.Random;

/**
 * This class generates new clients to simulate bank's work
 *
 * @author Mikhail Savin
 */
public class ClientsGenerator implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * The bank instance for which the clients generator works
     */
    private final Bank bank;

    /**
     * Creates a new ClientsGenerator with the specified bank instance
     *
     * @param bank specified bank
     */
    public ClientsGenerator(Bank bank) {
        this.bank = bank;
    }

    /**
     * This method generates banking operation type for client
     *
     * @return generated banking operation type
     */
    private BankingOperationType generateBankingOperationType() {
        if (Math.random() < 0.5) {
            return BankingOperationType.WITHDRAW;
        } else {
            return BankingOperationType.DEPOSIT;
        }
    }

    /**
     * This method generates transaction amount for client
     *
     * @return generated transaction amount
     */
    private double generateTransactionAmount() {
        return BankConstants.TRANSACTION_AMOUNT +
                Math.random() * (BankConstants.TRANSACTION_AMOUNT_BOUNDARY + BankConstants.TRANSACTION_AMOUNT_BOUNDARY) -
                BankConstants.TRANSACTION_AMOUNT_BOUNDARY;
    }

    /**
     * This method generates servicing time for client
     *
     * @return generated servicing time
     */
    private LocalTime generateServicingTime() {
        Random random = new Random();
        return LocalTime.ofSecondOfDay(BankConstants.SERVICE_TIME +
                random.nextInt(BankConstants.SERVICE_TIME_BOUNDARY + BankConstants.SERVICE_TIME_BOUNDARY) -
                BankConstants.SERVICE_TIME_BOUNDARY);
    }

    /**
     * This method generates new clients
     */
    @Override
    public void run() {
        Client client = new Client(generateBankingOperationType(), generateTransactionAmount(), generateServicingTime());

        Teller chosenTeller = bank.chooseTeller();
        chosenTeller.addClient(client);
        LOGGER.info("Client added [operation: {}, transaction: {}, servicing time: {}] to Teller {}",
                client.getBankingOperationType(), client.getTransactionAmount(), client.getServicingTime(),
                chosenTeller.getName());
    }
}
