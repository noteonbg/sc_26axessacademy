package com.bank.account;

import com.bank.account.model.Account;
import com.bank.account.repository.AccountRepository;
import com.bank.account.repository.AccountRepositoryImpl;
import com.bank.account.service.AccountService;
import com.bank.account.service.AccountServiceImpl;

public class MainApplication {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("   Starting Banking Account Management System (Live Demo)  ");
        System.out.println("==========================================================");

        // Instantiating layers without a database framework
        AccountRepository repository = new AccountRepositoryImpl(2000); // 2 seconds simulated database delay
        AccountService service = new AccountServiceImpl(repository);

        long startTime = System.currentTimeMillis();

        try {
            System.out.println("\n[Action 1] Fetching Account details for ACC1001...");
            Account acc1 = service.getAccountDetails("ACC1001");
            System.out.println("Result: " + acc1);

            System.out.println("\n[Action 2] Depositing $2500 into ACC1001...");
            Account updatedAcc1 = service.deposit("ACC1001", 2500.0);
            System.out.println("Result: " + updatedAcc1);

            System.out.println("\n[Action 3] Checking if ACC1002 is High-Value Customer...");
            boolean isHvc = service.isHighValueCustomer("ACC1002");
            System.out.println("Result: ACC1002 High Value Status = " + isHvc);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("\n==========================================================");
        System.out.println("Total Execution Time (With Repo Latency): " + elapsedTime + " ms");
        System.out.println("Notice how live repository operations take ~6+ seconds!");
        System.out.println("Unit tests will mock this layer to execute in milliseconds!");
        System.out.println("==========================================================");
    }
}
