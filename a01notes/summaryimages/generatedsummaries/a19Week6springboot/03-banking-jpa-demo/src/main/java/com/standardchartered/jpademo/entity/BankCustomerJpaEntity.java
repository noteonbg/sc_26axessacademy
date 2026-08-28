package com.standardchartered.jpademo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * =================================================================================
 * JPA ENTITY & DATABASE TABLE MAPPING OVERVIEW
 * =================================================================================
 * 
 * Target Database Table Name: "customers"
 * 
 * Relational Schema Summary:
 * - Table Name: `customers`
 * - Columns:
 *     1. `id`         (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
 *     2. `first_name` (VARCHAR(50), NOT NULL)
 *     3. `last_name`  (VARCHAR(50), NOT NULL)
 *     4. `email`      (VARCHAR(100), UNIQUE, NOT NULL)
 *     5. `status`     (VARCHAR(255))
 * 
 * IMPORTANT DB RELATIONSHIP DESIGN NOTE:
 * Notice that the `customers` database table DOES NOT contain any Foreign Key column 
 * or array column referencing accounts! In relational SQL databases, a parent row 
 * cannot store a collection of child IDs. 
 * Instead, the relationship is established by putting a Foreign Key column (`customer_id`) 
 * inside the child table (`accounts`).
 * =================================================================================
 */
@Entity
@Table(name = "customers") // Specifies exact SQL table name in the DB ("customers")
public class BankCustomerJpaEntity {

    /**
     * FIELD: id
     * -----------------------------------------------------------------------------
     * Database Column Name : `id`
     * Database Data Type   : BIGINT (PostgreSQL/MySQL/H2)
     * Key Type             : Primary Key
     * Generation Strategy  : GenerationType.IDENTITY (Uses SQL AUTO_INCREMENT / SERIAL)
     * 
     * WHY THIS MAPPING:
     * - @Id informs JPA that `id` uniquely identifies each record in `customers`.
     * - @GeneratedValue(strategy = GenerationType.IDENTITY) delegates primary key 
     *   generation to the database's auto-increment mechanism upon SQL INSERT.
     * -----------------------------------------------------------------------------
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * FIELD: firstName
     * -----------------------------------------------------------------------------
     * Database Column Name : `first_name`
     * Database Data Type   : VARCHAR(50)
     * SQL Constraints      : NOT NULL (nullable = false)
     * 
     * WHY THIS MAPPING:
     * - Java naming convention uses camelCase (`firstName`), whereas SQL database 
     *   naming convention standardly uses snake_case (`first_name`).
     * - @Column(name = "first_name") explicitly maps this Java field to the `first_name` 
     *   column in the `customers` SQL table.
     * -----------------------------------------------------------------------------
     */
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    /**
     * FIELD: lastName
     * -----------------------------------------------------------------------------
     * Database Column Name : `last_name`
     * Database Data Type   : VARCHAR(50)
     * SQL Constraints      : NOT NULL (nullable = false)
     * 
     * WHY THIS MAPPING:
     * - Maps Java `lastName` (camelCase) to SQL `last_name` (snake_case).
     * -----------------------------------------------------------------------------
     */
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    /**
     * FIELD: email
     * -----------------------------------------------------------------------------
     * Database Column Name : `email`
     * Database Data Type   : VARCHAR(100)
     * SQL Constraints      : UNIQUE, NOT NULL (unique = true, nullable = false)
     * 
     * WHY THIS MAPPING:
     * - `unique = true` creates a UNIQUE constraint on the `email` column in the DB table,
     *   preventing duplicate customer emails at the database level.
     * -----------------------------------------------------------------------------
     */
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    /**
     * FIELD: status
     * -----------------------------------------------------------------------------
     * Database Column Name : `status`
     * Database Data Type   : VARCHAR(255)
     * Default Java Value   : "ACTIVE"
     * 
     * WHY THIS MAPPING:
     * - Maps Java `status` field directly to the `status` column in `customers` table.
     * -----------------------------------------------------------------------------
     */
    @Column(name = "status")
    private String status = "ACTIVE";

    /**
     * RELATIONSHIP FIELD: accounts
     * -----------------------------------------------------------------------------
     * Association Type     : One-to-Many (One Customer -> Many Bank Accounts)
     * Database Column Name : NONE in `customers` table!
     * Physical FK Location : `customer_id` column in the child table `accounts`
     * Owner of Relationship: BankAccountJpaEntity (Child Side)
     * Inverse/Non-Owner    : BankCustomerJpaEntity (Parent Side - THIS CLASS)
     * 
     * DETAILED EXPLANATION OF ANNOTATIONS & WORKING:
     * 
     * 1. @OneToMany(mappedBy = "customer"):
     *    - `mappedBy = "customer"` is CRITICAL. It tells Hibernate/JPA:
     *      "Do NOT create a foreign key column or join table for this field in `customers` table.
     *       The relationship is ALREADY mapped by the Java field named `customer` inside 
     *       the `BankAccountJpaEntity` class."
     *    - `BankAccountJpaEntity.customer` has `@JoinColumn(name = "customer_id")`, which holds 
     *      the actual SQL foreign key column `customer_id` in the `accounts` table.
     * 
     * 2. cascade = CascadeType.ALL:
     *    - Propagates all entity lifecycle operations (PERSIST, MERGE, REMOVE, REFRESH, DETACH) 
     *      from parent (Customer) to children (Accounts).
     *    - Example: Saving a new customer via `customerRepository.save(customer)` will 
     *      automatically generate SQL `INSERT INTO accounts ...` statements for all 
     *      accounts in this list.
     * 
     * 3. orphanRemoval = true:
     *    - When an account object is removed from this Java `accounts` list 
     *      (e.g., `customer.getAccounts().remove(account)`), JPA automatically executes 
     *      an SQL `DELETE FROM accounts WHERE id = ?` upon flush to clean up orphaned DB rows.
     * 
     * 4. @JsonManagedReference:
     *    - Jackson JSON serialization helper. Pairs with @JsonIgnore / @JsonBackReference in 
     *      BankAccountJpaEntity to prevent infinite JSON recursion loops (Customer -> Accounts -> Customer -> ...).
     * -----------------------------------------------------------------------------
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BankAccountJpaEntity> accounts = new ArrayList<>();

    // Default No-Arg Constructor (Required by JPA specification for entity instantiation)
    public BankCustomerJpaEntity() {}

    public BankCustomerJpaEntity(Long id, String firstName, String lastName, String email, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<BankAccountJpaEntity> getAccounts() { return accounts; }
    public void setAccounts(List<BankAccountJpaEntity> accounts) { this.accounts = accounts; }

    /**
     * Convenience helper method to maintain bidirectional synchronization.
     * Keeps both sides of the Java object graph in sync before persisting to DB.
     */
    public void addAccount(BankAccountJpaEntity account) {
        accounts.add(account);
        account.setCustomer(this); // Sets foreign key reference on child object
    }
}
