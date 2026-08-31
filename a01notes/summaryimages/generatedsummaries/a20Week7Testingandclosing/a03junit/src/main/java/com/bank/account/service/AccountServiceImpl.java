package com.bank.account.service;

import com.bank.account.model.Account;
import com.bank.account.repository.AccountRepository;

import java.util.Optional;

/**
 * Implementation of AccountService applying banking business rules.
 */
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account getAccountDetails(String accountId) {
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }

        System.out.println("[Service Layer] Calling Repository to fetch account: " + accountId);
        Optional<Account> accountOpt = repository.findById(accountId);

        Account account = accountOpt.orElseThrow(() -> 
            new RuntimeException("Account not found with ID: " + accountId));

        if (!account.isActive()) {
            throw new IllegalStateException("Account is inactive: " + accountId);
        }

        return account;
    }

    @Override
    public Account createAccount(String accountId, String holderName, double initialDeposit, String type) {
        if (accountId == null || holderName == null || holderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid account ID or holder name");
        }

        if (initialDeposit < 100.0) {
            throw new IllegalArgumentException("Minimum initial deposit required is $100.00");
        }

        Optional<Account> existing = repository.findById(accountId);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Account with ID already exists: " + accountId);
        }

        Account newAccount = new Account(accountId, holderName, initialDeposit, type, true);
        return repository.save(newAccount);
    }

    @Override
    public Account deposit(String accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }

        Account account = getAccountDetails(accountId);
        account.setBalance(account.getBalance() + amount);
        return repository.save(account);
    }

    @Override
    public Account withdraw(String accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }

        Account account = getAccountDetails(accountId);
        if (amount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient funds! Available balance: $" + account.getBalance());
        }

        account.setBalance(account.getBalance() - amount);
        return repository.save(account);
    }

    @Override
    public boolean isHighValueCustomer(String accountId) {
        Account account = getAccountDetails(accountId);
        return account.getBalance() >= 50000.0;
    }
}
