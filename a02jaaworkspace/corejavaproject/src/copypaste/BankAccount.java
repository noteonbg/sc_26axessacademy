package copypaste;


import java.time.LocalDate;

public class BankAccount {

    public enum AccountType {
        SAVINGS,
        CHECKING,
        FIXED_DEPOSIT,
        CURRENT
    }

    private String accountNumber;
    private String accountHolderName;
    private AccountType accountType;
    private double balance;
    private LocalDate dateOpened;
    private boolean isActive;

    // Constructor
    public BankAccount(String accountNumber, String accountHolderName, AccountType accountType, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = initialDeposit;
        this.dateOpened = LocalDate.now();
        this.isActive = true;
    }

    // Deposit funds
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
    }

    // Withdraw funds
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }

        if (amount > balance) {
            return false; // insufficient funds
        }

        balance -= amount;
        return true;
    }

    // Close account
    public void closeAccount() {
        isActive = false;
    }

    // Check if account is active
    public boolean isActive() {
        return isActive;
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    // toString method for debugging/logging
    @Override
    public String toString() {
        return String.format(
                "BankAccount{accountNumber='%s', holder='%s', type=%s, balance=%.2f, opened=%s, active=%s}",
                accountNumber, accountHolderName, accountType, balance, dateOpened, isActive
        );
    }
}

