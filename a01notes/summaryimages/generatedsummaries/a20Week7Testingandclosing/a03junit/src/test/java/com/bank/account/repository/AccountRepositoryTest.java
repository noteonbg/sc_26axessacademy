package com.bank.account.repository;

import com.bank.account.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Repository Layer Direct Tests")
class AccountRepositoryTest {

    private AccountRepository repository;

    @BeforeEach
    void setUp() {
        // Instantiate repository with zero simulated delay for fast direct repository unit testing
        repository = new AccountRepositoryImpl(0);
    }

    @Test
    @DisplayName("Should find pre-populated account by ID")
    void testFindById_Success() {
        Optional<Account> accountOpt = repository.findById("ACC1001");
        assertTrue(accountOpt.isPresent(), "Account ACC1001 should exist");
        assertEquals("Alice Smith", accountOpt.get().getHolderName());
    }

    @Test
    @DisplayName("Should return empty optional for non-existing account ID")
    void testFindById_NotFound() {
        Optional<Account> accountOpt = repository.findById("ACC9999");
        assertFalse(accountOpt.isPresent(), "Account ACC9999 should not exist");
    }

    @Test
    @DisplayName("Should save new account successfully")
    void testSave_Success() {
        Account newAcc = new Account("ACC2001", "David Miller", 1500.0, "SAVINGS", true);
        Account saved = repository.save(newAcc);

        assertNotNull(saved);
        assertEquals("ACC2001", saved.getAccountId());
        
        Optional<Account> fetched = repository.findById("ACC2001");
        assertTrue(fetched.isPresent());
        assertEquals(1500.0, fetched.get().getBalance());
    }

    @Test
    @DisplayName("Should verify repository operation completes within timeout")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testFindAll_TimeoutConstraint() {
        List<Account> accounts = repository.findAll();
        assertTrue(accounts.size() >= 3, "Seed accounts should be retrieved instantly");
    }
}
