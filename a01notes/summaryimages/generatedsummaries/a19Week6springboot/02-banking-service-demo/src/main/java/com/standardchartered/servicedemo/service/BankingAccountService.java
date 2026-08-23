package com.standardchartered.servicedemo.service;

import com.standardchartered.servicedemo.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/*
 * SYNTAX COMMENTARY: Service Layer & Dependency Injection Patterns
 *
 * 1. Layered Architecture Separation:
 *    - The Service layer acts as the plumbing between Controllers (HTTP/Web) and Data/Repository layers.
 *    - Implements business validations, rule checks, and calculation orchestration.
 *
 * 2. Dependency Injection (DI) & Inversion of Control (IoC):
 *    - Constructor Injection (RECOMMENDED): Ensures dependencies cannot be null, improves testability with mocks.
 */
@Service
public class BankingAccountService {

    // Dependency 1: Helper Service injected via Spring IoC Container
    private final InterestCalculationService interestService;

    private final Map<String, BankAccount> accountStore = new HashMap<>();

    /*
     * SYNTAX COMMENTARY: Recommended Constructor Injection Syntax
     *
     * @Autowired on constructor (Optional in Spring 4.3+ when single constructor present):
     * - Spring automatically satisfies the dependency 'InterestCalculationService' from its Bean container when instantiating BankingAccountService.
     */
    @Autowired
    public BankingAccountService(InterestCalculationService interestService) {
        this.interestService = interestService;

        // Seed initial in-memory bank accounts
        accountStore.put("ACC-101", new BankAccount("ACC-101", "Sandra Rogers", "SAVINGS", new BigDecimal("50000.00")));
        accountStore.put("ACC-102", new BankAccount("ACC-102", "Steve Casey", "CURRENT", new BigDecimal("120000.00")));
    }

    /*
     * Business Operation 1: Fetch Account with Interest Calculation
     */
    public BankAccount getAccountDetails(String accountNumber) {
        BankAccount account = accountStore.get(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account " + accountNumber + " does not exist.");
        }
        return account;
    }

    /*
     * Business Operation 2: Apply Quarterly Interest to Savings Balance
     */
    public BigDecimal applyQuarterlyInterest(String accountNumber) {
        BankAccount account = getAccountDetails(accountNumber);
        
        // Delegates interest calculation to injected helper service
        BigDecimal interestEarned = interestService.calculateQuarterlyInterest(account.getBalance(), account.getAccountType());
        
        // Updates account balance with earned interest
        account.setBalance(account.getBalance().add(interestEarned));
        return interestEarned;
    }

    /*
     * Business Operation 3: Money Transfer Validation & Processing
     */
    public String transferMoney(String sourceAccNo, String targetAccNo, BigDecimal amount) {
        // Validation Rule 1: Amount must be positive
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }

        BankAccount source = getAccountDetails(sourceAccNo);
        BankAccount target = getAccountDetails(targetAccNo);

        // Validation Rule 2: Sufficient funds check
        if (source.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds in account " + sourceAccNo + ". Available: $" + source.getBalance());
        }

        // Execute balance changes
        source.setBalance(source.getBalance().subtract(amount));
        target.setBalance(target.getBalance().add(amount));

        return "Transferred $" + amount + " from " + sourceAccNo + " to " + targetAccNo;
    }
}
