package com.standardchartered.controllerdemo.model;

import java.math.BigDecimal;

/*
 * Domain Model POJO for Customer Profile
 * Serialized automatically to JSON by Jackson library when returned from @RestController methods.
 */
public class CustomerProfile {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String accountType;
    private BigDecimal balance;

    public CustomerProfile() {}

    public CustomerProfile(Long id, String firstName, String lastName, String email, String accountType, BigDecimal balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountType = accountType;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
