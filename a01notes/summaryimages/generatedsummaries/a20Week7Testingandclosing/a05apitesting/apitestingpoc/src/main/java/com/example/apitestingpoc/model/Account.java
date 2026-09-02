package com.example.apitestingpoc.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Account Model (Data Transfer Object / In-Memory Entity).
 * Pure Java POJO representing a Bank Account without any database annotations (@Entity, @Table).
 */
public class Account {

    private Long id;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance;
    private String accountType; // e.g., SAVINGS, CHECKING

    // Default Constructor (Required by Jackson for JSON deserialization)
    public Account() {
    }

    // Parameterized Constructor
    public Account(Long id, String accountNumber, String accountHolderName, BigDecimal balance, String accountType) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.accountType = accountType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
               Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber);
    }
}
