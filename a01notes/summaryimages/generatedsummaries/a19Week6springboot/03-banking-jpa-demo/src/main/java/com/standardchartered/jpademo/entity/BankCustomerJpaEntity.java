package com.standardchartered.jpademo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
 * SYNTAX COMMENTARY: ORM Entity Mapping Annotations
 *
 * @Entity:
 * - Declares that this Java class is a persistent JPA entity mapped to a relational database table.
 *
 * @Table(name = "customers"):
 * - Specifies the exact SQL table name ("customers") in PostgreSQL / H2.
 */
@Entity
@Table(name = "customers")
public class BankCustomerJpaEntity {

    /*
     * SYNTAX COMMENTARY: Primary Key & Generation Strategy
     *
     * @Id: Specifies that this field is the Primary Key of the entity.
     * @GeneratedValue(strategy = GenerationType.IDENTITY): Uses auto-incrementing identity columns (SERIAL in PostgreSQL / AUTO_INCREMENT in MySQL/H2).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * SYNTAX COMMENTARY: Column Mapping & Constraints
     *
     * @Column: Maps the Java field to an explicit SQL column name and enforces table constraints.
     */
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "status")
    private String status = "ACTIVE";

    /*
     * SYNTAX COMMENTARY: One-to-Many Association Mapping & Cascading
     *
     * @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true):
     * - mappedBy = "customer": Indicates that the child entity (BankAccountJpaEntity) owns the foreign key column.
     * - cascade = CascadeType.ALL: Propagates all entity lifecycle operations (PERSIST, MERGE, REMOVE, REFRESH, DETACH) from parent to child.
     *   Example: Saving a customer automatically inserts all associated accounts into the DB in one operation.
     * - orphanRemoval = true: Deleting an account from the Java list automatically executes an SQL DELETE for that child record.
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BankAccountJpaEntity> accounts = new ArrayList<>();

    public BankCustomerJpaEntity() {}

    public BankCustomerJpaEntity(Long id, String firstName, String lastName, String email, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
    }

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

    public void addAccount(BankAccountJpaEntity account) {
        accounts.add(account);
        account.setCustomer(this);
    }
}
