package com.standardchartered.jpademo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * =================================================================================
 * JPA ENTITY & DATABASE TABLE MAPPING OVERVIEW
 * =================================================================================
 * 
 * Target Database Table Name: "accounts"
 * 
 * Relational Schema Summary:
 * - Table Name: `accounts`
 * - Columns:
 *     1. `id`              (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
 *     2. `account_number`   (VARCHAR(255), UNIQUE, NOT NULL)
 *     3. `account_type`     (VARCHAR(255), NOT NULL)
 *     4. `account_balance`  (NUMERIC/DECIMAL, NOT NULL)
 *     5. `customer_id`      (BIGINT, FOREIGN KEY referencing `customers(id)`, NOT NULL)
 * 
 * IMPORTANT DB RELATIONSHIP DESIGN NOTE:
 * This class (`BankAccountJpaEntity`) represents the CHILD side in the 
 * One-to-Many / Many-to-One relationship.
 * In relational SQL design, child tables (`accounts`) hold the Foreign Key column (`customer_id`) 
 * pointing back to the Primary Key (`id`) of the parent table (`customers`).
 * Therefore, this class is the OWNING SIDE of the relationship!
 * =================================================================================
 */
@Entity
@Table(name = "accounts") // Specifies exact SQL table name in the DB ("accounts")
public class BankAccountJpaEntity {

    /**
     * FIELD: id
     * -----------------------------------------------------------------------------
     * Database Column Name : `id`
     * Database Data Type   : BIGINT (PostgreSQL/MySQL/H2)
     * Key Type             : Primary Key
     * Generation Strategy  : GenerationType.IDENTITY (Uses SQL AUTO_INCREMENT / SERIAL)
     * 
     * WHY THIS MAPPING:
     * - @Id marks this field as the Primary Key for table `accounts`.
     * - @GeneratedValue(strategy = GenerationType.IDENTITY) relies on SQL database 
     *   identity column for auto-generating unique IDs upon insertion.
     * -----------------------------------------------------------------------------
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * FIELD: accountNumber
     * -----------------------------------------------------------------------------
     * Database Column Name : `account_number`
     * Database Data Type   : VARCHAR(255)
     * SQL Constraints      : UNIQUE, NOT NULL (unique = true, nullable = false)
     * 
     * WHY THIS MAPPING:
     * - Java camelCase (`accountNumber`) maps to SQL snake_case (`account_number`).
     * - @Column(name = "account_number") specifies exact DB column name.
     * - `unique = true` enforces database-level uniqueness constraint so no two 
     *   accounts share the same account number.
     * -----------------------------------------------------------------------------
     */
    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    /**
     * FIELD: accountType
     * -----------------------------------------------------------------------------
     * Database Column Name : `account_type`
     * Database Data Type   : VARCHAR(255)
     * SQL Constraints      : NOT NULL (nullable = false)
     * 
     * WHY THIS MAPPING:
     * - Maps Java `accountType` to SQL `account_type` column (e.g. "SAVINGS", "CHECKING").
     * -----------------------------------------------------------------------------
     */
    @Column(name = "account_type", nullable = false)
    private String accountType;

    /**
     * FIELD: accountBalance
     * -----------------------------------------------------------------------------
     * Database Column Name : `account_balance`
     * Database Data Type   : NUMERIC / DECIMAL (Exact decimal arithmetic in SQL)
     * SQL Constraints      : NOT NULL (nullable = false)
     * 
     * WHY THIS MAPPING:
     * - Java `BigDecimal` is mapped to SQL `DECIMAL`/`NUMERIC` to prevent double/float 
     *   rounding inaccuracies in financial transaction calculations.
     * - @Column(name = "account_balance") maps to `account_balance` in `accounts` table.
     * -----------------------------------------------------------------------------
     */
    @Column(name = "account_balance", nullable = false)
    private BigDecimal accountBalance;

    /**
     * RELATIONSHIP FIELD: customer (PHYSICAL FOREIGN KEY HOLDER)
     * -----------------------------------------------------------------------------
     * Association Type     : Many-to-One (Many Accounts -> One Customer)
     * Database Column Name : `customer_id` (Physical Column in `accounts` table)
     * Foreign Key Target   : `customers.id` (Primary Key in `customers` table)
     * Owner of Relationship: BankAccountJpaEntity (THIS CLASS)
     * 
     * DETAILED EXPLANATION OF ANNOTATIONS & WORKING:
     * 
     * 1. @ManyToOne(fetch = FetchType.LAZY):
     *    - Indicates multiple records in `accounts` table can link to a single record in `customers` table.
     *    - `FetchType.LAZY`: Optimization strategy! Tells Hibernate NOT to perform an immediate 
     *      SQL JOIN or SELECT on table `customers` when reading an account. The customer entity 
     *      is loaded on demand (via a proxy object) only when `account.getCustomer()` properties are accessed.
     * 
     * 2. @JoinColumn(name = "customer_id", nullable = false):
     *    - CRITICAL ANNOTATION! This declares `BankAccountJpaEntity` as the OWNER of the relationship.
     *    - `name = "customer_id"` explicitly specifies the physical Foreign Key column name created in SQL 
     *      in table `accounts`.
     *    - `nullable = false` adds an SQL constraint: `customer_id BIGINT NOT NULL`. Every account row MUST 
     *      be attached to a valid customer row in SQL.
     *    - Under the hood, JPA executes:
     *      ALTER TABLE accounts ADD CONSTRAINT fk_accounts_customer FOREIGN KEY (customer_id) REFERENCES customers(id);
     * 
     * 3. @JsonIgnore:
     *    - Prevents Spring Boot / Jackson JSON serializer from endlessly cycling between 
     *      Account -> Customer -> Accounts -> Customer when returning entities from REST API endpoints.
     * -----------------------------------------------------------------------------
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private BankCustomerJpaEntity customer;

    // Default No-Arg Constructor (Required by JPA specification)
    public BankAccountJpaEntity() {}

    public BankAccountJpaEntity(Long id, String accountNumber, String accountType, BigDecimal accountBalance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }

    // Getters and Setters
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
