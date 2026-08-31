package com.bank.account.service;

import com.bank.account.model.Account;
import com.bank.account.repository.AccountRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Service Layer Unit Test Suite.
 * Uses Mockito to mock the AccountRepository, completely bypassing the simulated database delay!
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Account Service Unit Tests (Mocked Repo)")
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account sampleAccount;

    @BeforeAll
    static void beforeAllTests() {
        System.out.println("=== [JUnit Lifecycle] @BeforeAll: Initializing Test Suite Environment ===");
    }

    @AfterAll
    static void afterAllTests() {
        System.out.println("=== [JUnit Lifecycle] @AfterAll: Cleaning Up Test Suite Resources ===");
    }

    @BeforeEach
    void setUpEachTest() {
        System.out.println("--- [JUnit Lifecycle] @BeforeEach: Preparing fresh test data fixture ---");
        sampleAccount = new Account("ACC1001", "Alice Smith", 5000.00, "SAVINGS", true);
    }

    @AfterEach
    void tearDownEachTest() {
        System.out.println("--- [JUnit Lifecycle] @AfterEach: Resetting state post execution ---");
    }

    @Test
    @DisplayName("Should successfully fetch account details when account exists and is active")
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS) // Guaranteed fast execution due to Mocking!
    void testGetAccountDetails_Success() {
        // Stubbing repository response (bypasses 2-second sleep!)
        when(accountRepository.findById("ACC1001")).thenReturn(Optional.of(sampleAccount));

        Account result = accountService.getAccountDetails("ACC1001");

        assertNotNull(result, "Returned account should not be null");
        assertAll("Account Object Fields Validation",
                () -> assertEquals("ACC1001", result.getAccountId()),
                () -> assertEquals("Alice Smith", result.getHolderName()),
                () -> assertEquals(5000.00, result.getBalance()),
                () -> assertEquals("SAVINGS", result.getAccountType()),
                () -> assertTrue(result.isActive())
        );

        // Verify repository method was called exactly once
        verify(accountRepository, times(1)).findById("ACC1001");
    }

    @Test
    @DisplayName("Should throw RuntimeException when account ID does not exist")
    void testGetAccountDetails_NotFound() {
        when(accountRepository.findById("ACC9999")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.getAccountDetails("ACC9999");
        });

        assertEquals("Account not found with ID: ACC9999", exception.getMessage());
        verify(accountRepository, times(1)).findById("ACC9999");
    }

    @Test
    @DisplayName("Should throw IllegalStateException when account is inactive")
    void testGetAccountDetails_InactiveAccount() {
        Account inactiveAccount = new Account("ACC1003", "Charlie", 500.0, "SAVINGS", false);
        when(accountRepository.findById("ACC1003")).thenReturn(Optional.of(inactiveAccount));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            accountService.getAccountDetails("ACC1003");
        });

        assertTrue(exception.getMessage().contains("Account is inactive"));
        verify(accountRepository, times(1)).findById("ACC1003");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @DisplayName("Should throw IllegalArgumentException for blank or empty account IDs")
    void testGetAccountDetails_InvalidInput(String invalidId) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.getAccountDetails(invalidId);
        });

        assertEquals("Account ID cannot be null or empty", exception.getMessage());
        // Verify repo is NEVER invoked for invalid input validation failures
        verify(accountRepository, never()).findById(anyString());
    }

    @Test
    @DisplayName("Should create account when initial deposit is valid and ID is unique")
    void testCreateAccount_Success() {
        when(accountRepository.findById("ACC3001")).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account created = accountService.createAccount("ACC3001", "John Doe", 250.00, "CHECKING");

        assertNotNull(created);
        assertEquals("ACC3001", created.getAccountId());
        assertEquals(250.00, created.getBalance());
        assertTrue(created.isActive());

        verify(accountRepository, times(1)).findById("ACC3001");
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when initial deposit is below minimum threshold")
    void testCreateAccount_BelowMinimumDeposit() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.createAccount("ACC3002", "Jane Doe", 50.00, "CHECKING");
        });

        assertEquals("Minimum initial deposit required is $100.00", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @Disabled("Demonstrating @Disabled: Feature pending legacy system deprecation")
    @DisplayName("Legacy Account Transfer (Disabled Test)")
    void testLegacyTransferFeature() {
        fail("This test is disabled and should not execute.");
    }

    @Nested
    @DisplayName("Financial Deposit & Withdrawal Operations")
    class TransactionOperationsTests {

        @Test
        @DisplayName("Should deposit funds correctly and update balance")
        void testDeposit_Success() {
            when(accountRepository.findById("ACC1001")).thenReturn(Optional.of(sampleAccount));
            when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));

            Account updated = accountService.deposit("ACC1001", 1500.00);

            assertEquals(6500.00, updated.getBalance());
            verify(accountRepository, times(1)).save(sampleAccount);
        }

        @Test
        @DisplayName("Should withdraw funds successfully when balance is sufficient")
        void testWithdraw_Success() {
            when(accountRepository.findById("ACC1001")).thenReturn(Optional.of(sampleAccount));
            when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));

            Account updated = accountService.withdraw("ACC1001", 2000.00);

            assertEquals(3000.00, updated.getBalance());
            verify(accountRepository, times(1)).save(sampleAccount);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when withdrawal exceeds available balance")
        void testWithdraw_InsufficientFunds() {
            when(accountRepository.findById("ACC1001")).thenReturn(Optional.of(sampleAccount));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.withdraw("ACC1001", 10000.00);
            });

            assertTrue(exception.getMessage().contains("Insufficient funds"));
            verify(accountRepository, never()).save(any(Account.class));
        }

        @ParameterizedTest(name = "Deposit amount {0} should throw IllegalArgumentException")
        @ValueSource(doubles = {0.0, -100.0, -0.01})
        @DisplayName("Parameterized Test: Invalid deposit amounts")
        void testDeposit_InvalidAmounts(double invalidAmount) {
            assertThrows(IllegalArgumentException.class, () -> {
                accountService.deposit("ACC1001", invalidAmount);
            });
            verify(accountRepository, never()).save(any(Account.class));
        }
    }

    @Nested
    @DisplayName("High-Value Customer Scoring Tests")
    class HighValueCustomerTests {

        @ParameterizedTest(name = "Balance {0} -> High Value Status: {1}")
        @CsvSource({
                "49999.99, false",
                "50000.00, true",
                "150000.00, true",
                "100.00, false"
        })
        @DisplayName("Parameterized Test: High-Value Customer status thresholds")
        void testIsHighValueCustomer(double balance, boolean expectedStatus) {
            Account testAcc = new Account("ACC500", "Customer", balance, "SAVINGS", true);
            when(accountRepository.findById("ACC500")).thenReturn(Optional.of(testAcc));

            boolean status = accountService.isHighValueCustomer("ACC500");

            assertEquals(expectedStatus, status);
        }
    }
}
