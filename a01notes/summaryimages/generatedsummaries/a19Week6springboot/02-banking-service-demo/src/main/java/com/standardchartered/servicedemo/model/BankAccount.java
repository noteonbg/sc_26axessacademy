package com.standardchartered.servicedemo.model;

import java.math.BigDecimal;

public class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private String accountType; // SAVINGS, CURRENT
    private BigDecimal balance;

    public BankAccount() {}

    public BankAccount(String accountNumber, String accountHolderName, String accountType, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountHolderName() { return accountHolderName; }
    public void setAccountHolderName(String accountHolderName) { this.accountHolderName = accountHolderName; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
