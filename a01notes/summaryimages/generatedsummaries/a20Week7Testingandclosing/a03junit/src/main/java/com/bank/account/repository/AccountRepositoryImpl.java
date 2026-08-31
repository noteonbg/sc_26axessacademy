package com.bank.account.repository;

import com.bank.account.model.Account;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-Memory implementation of AccountRepository.
 * Simulates real-world database latency using Thread.sleep in findById.
 */
public class AccountRepositoryImpl implements AccountRepository {

    private final Map<String, Account> db = new ConcurrentHashMap<>();
    private final long simulatedDelayMs;

    public AccountRepositoryImpl() {
        this(2000); // Default 2 seconds simulated database delay
    }

    public AccountRepositoryImpl(long simulatedDelayMs) {
        this.simulatedDelayMs = simulatedDelayMs;
        // Pre-populate with initial seed data
        db.put("ACC1001", new Account("ACC1001", "Alice Smith", 5000.00, "SAVINGS", true));
        db.put("ACC1002", new Account("ACC1002", "Bob Jones", 120000.00, "CHECKING", true));
        db.put("ACC1003", new Account("ACC1003", "Charlie Brown", 500.00, "SAVINGS", false)); // inactive
    }

    @Override
    public Optional<Account> findById(String accountId) {
        // Simulate heavy network / database IO latency
        if (simulatedDelayMs > 0) {
            try {
                System.out.println("[Repo Layer] Simulating slow database lookup for " + accountId + "... (" + simulatedDelayMs + "ms delay)");
                Thread.sleep(simulatedDelayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Database operation interrupted", e);
            }
        }
        return Optional.ofNullable(db.get(accountId));
    }

    @Override
    public Account save(Account account) {
        if (account == null || account.getAccountId() == null) {
            throw new IllegalArgumentException("Account or Account ID cannot be null");
        }
        db.put(account.getAccountId(), account);
        return account;
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean deleteById(String accountId) {
        return db.remove(accountId) != null;
    }
}
