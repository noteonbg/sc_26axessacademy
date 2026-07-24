package com.campus.finance.collab;

import java.util.List;

/**
 * User-defined data type used for EVERY concept in this project.
 *
 * Notice the "collaboration" built into the data itself:
 *  - owners            : one or MORE people can jointly own an account (joint account)
 *  - relationshipManager : the bank employee (a person) responsible for this account
 *
 * So a single Account already ties together multiple people, which is the theme
 * of this demo: functional programming is what lets multiple people work together
 * cleanly on the same List<Account>.
 */
public class Account {

    private int id;
    private String type;                 // "SAVINGS" or "CURRENT"
    private String city;
    private double balance;
    private List<String> owners;         // joint account holders (one or more people)
    private String relationshipManager;  // the bank employee handling this account

    public Account(int id, String type, String city, double balance,
                   List<String> owners, String relationshipManager) {
        this.id = id;
        this.type = type;
        this.city = city;
        this.balance = balance;
        this.owners = owners;
        this.relationshipManager = relationshipManager;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getOwners() {
        return owners;
    }

    public String getRelationshipManager() {
        return relationshipManager;
    }

    @Override
    public String toString() {
        return "Account#" + id + " [" + type + ", " + city + ", balance=" + balance
                + ", owners=" + owners + ", RM=" + relationshipManager + "]";
    }
}
