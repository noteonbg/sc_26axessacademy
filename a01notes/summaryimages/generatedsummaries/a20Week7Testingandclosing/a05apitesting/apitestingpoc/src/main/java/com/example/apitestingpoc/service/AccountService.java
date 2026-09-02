package com.example.apitestingpoc.service;

import com.example.apitestingpoc.exception.AccountNotFoundException;
import com.example.apitestingpoc.model.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Account Service managing accounts purely IN-MEMORY.
 * ZERO Database / JPA / JDBC used here.
 * 
 * @Service registers this class as a Spring-managed Service bean in the application context.
 */
@Service
public class AccountService {

    // ConcurrentHashMap provides thread-safe in-memory storage for accounts
    private final Map<Long, Account> accountStore = new ConcurrentHashMap<>();
    
    // AtomicLong provides thread-safe auto-incrementing ID generation
    private final AtomicLong idGenerator = new AtomicLong(100);

    public AccountService() {
        // Pre-populate with sample data for demonstration
        createAccount(new Account(null, "ACC1001", "Alice Smith", new BigDecimal("2500.50"), "SAVINGS"));
        createAccount(new Account(null, "ACC1002", "Bob Jones", new BigDecimal("1200.00"), "CHECKING"));
    }

    /**
     * Retrieve all accounts from memory.
     */
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accountStore.values());
    }

    /**
     * Retrieve a single account by ID.
     * Throws AccountNotFoundException if not present in memory.
     */
    public Account getAccountById(Long id) {
        Account account = accountStore.get(id);
        if (account == null) {
            throw new AccountNotFoundException("Account not found with ID: " + id);
        }
        return account;
    }

    /**
     * Create a new account in memory.
     */
    public Account createAccount(Account account) {
        if (account.getAccountHolderName() == null || account.getAccountHolderName().trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be empty");
        }
        if (account.getBalance() == null || account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }

        Long newId = idGenerator.incrementAndGet();
        account.setId(newId);
        accountStore.put(newId, account);
        return account;
    }

    /**
     * Update an existing account in memory.
     */
    public Account updateAccount(Long id, Account updatedAccount) {
        if (!accountStore.containsKey(id)) {
            throw new AccountNotFoundException("Cannot update. Account not found with ID: " + id);
        }
        updatedAccount.setId(id);
        accountStore.put(id, updatedAccount);
        return updatedAccount;
    }

    /**
     * Delete an account from memory by ID.
     */
    public void deleteAccount(Long id) {
        if (!accountStore.containsKey(id)) {
            throw new AccountNotFoundException("Cannot delete. Account not found with ID: " + id);
        }
        accountStore.remove(id);
    }
}
