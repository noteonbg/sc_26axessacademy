package com.campus.finance.model;

/**
 * A single money movement on an account.
 * type is "CREDIT" (money in) or "DEBIT" (money out).
 */
public class Transaction {

    private int accountId;
    private String type;      // "CREDIT" or "DEBIT"
    private String category;  // e.g. "SALARY", "SHOPPING", "FUEL"
    private double amount;

    public Transaction(int accountId, String type, String category, double amount) {
        this.accountId = accountId;
        this.type = type;
        this.category = category;
        this.amount = amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return type + " of " + amount + " (" + category + ") on account " + accountId;
    }
}
