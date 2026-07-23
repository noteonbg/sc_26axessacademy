package com.campus.finance.model;

/**
 * A very simple bank account used across all the demos.
 * Plain data holder (also called a POJO) so beginners can focus on the
 * functional programming ideas, not on the model.
 */
public class Account {

    private int id;
    private String ownerName;
    private String city;      // location of the customer
    private String type;      // "SAVINGS" or "CURRENT"
    private double balance;

    public Account(int id, String ownerName, String city, String type, double balance) {
        this.id = id;
        this.ownerName = ownerName;
        this.city = city;
        this.type = type;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", owner='" + ownerName + "', city='" + city
                + "', type='" + type + "', balance=" + balance + "}";
    }
}
