package com.standardchartered.jpademo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;

/*
 * SYNTAX COMMENTARY: Child Entity & Foreign Key Mapping
 */
@Entity
@Table(name = "accounts")
public class BankAccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(name = "account_balance", nullable = false)
    private BigDecimal accountBalance;

    /*
     * SYNTAX COMMENTARY: Many-to-One Association & Foreign Key Column
     *
     * @ManyToOne(fetch = FetchType.LAZY):
     * - Many bank accounts belong to One customer.
     * - FetchType.LAZY: Customer entity is fetched from DB only when explicitly accessed, optimizing performance.
     *
     * @JoinColumn(name = "customer_id", nullable = false):
     * - Creates the foreign key column 'customer_id' in the 'accounts' SQL table referencing 'customers(id)'.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private BankCustomerJpaEntity customer;

    public BankAccountJpaEntity() {}

    public BankAccountJpaEntity(Long id, String accountNumber, String accountType, BigDecimal accountBalance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public BigDecimal getAccountBalance() { return accountBalance; }
    public void setAccountBalance(BigDecimal accountBalance) { this.accountBalance = accountBalance; }

    public BankCustomerJpaEntity getCustomer() { return customer; }
    public void setCustomer(BankCustomerJpaEntity customer) { this.customer = customer; }
}
