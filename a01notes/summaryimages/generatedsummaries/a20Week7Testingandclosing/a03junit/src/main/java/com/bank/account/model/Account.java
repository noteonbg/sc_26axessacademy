package com.bank.account.model;

import java.util.Objects;

/**
 * Domain model representing a Financial Bank Account.
 */
public class Account {
    private String accountId;
    private String holderName;
    private double balance;
    private String accountType; // e.g. SAVINGS, CHECKING
    private boolean active;

    public Account() {
    }

    public Account(String accountId, String holderName, double balance, String accountType, boolean active) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.balance = balance;
        this.accountType = accountType;
        this.active = active;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 &&
                active == account.active &&
                Objects.equals(accountId, account.accountId) &&
                Objects.equals(holderName, account.holderName) &&
                Objects.equals(accountType, account.accountType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, holderName, balance, accountType, active);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", holderName='" + holderName + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", active=" + active +
                '}';
    }
}
