package com.standardchartered.jpademo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * =================================================================================
 * JPA ENTITY & DATABASE TABLE MAPPING OVERVIEW
 * =================================================================================
 * 
 * Target Database Table Name: "employees"
 * 
 * Relational Schema Summary:
 * - Table Name: `employees`
 * - Columns:
 *     1. `id`          (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
 *     2. `name`        (VARCHAR(100), NOT NULL)
 *     3. `email`       (VARCHAR(100), UNIQUE, NOT NULL)
 *     4. `department`  (VARCHAR(50), NOT NULL)
 *     5. `salary`      (NUMERIC(12, 2))
 *     6. `designation` (VARCHAR(50))
 * 
 * ARCHITECTURAL NOTE:
 * This class represents a standalone Single-Table JPA Entity without foreign key 
 * relationships to other tables. It demonstrates fundamental 1:1 direct property 
 * to SQL column mapping rules in Spring Data JPA / Hibernate.
 * =================================================================================
 */
@Entity
@Table(name = "employees") // Specifies exact SQL table name in the DB ("employees")
public class Employee {

    /**
     * FIELD: id
     * -----------------------------------------------------------------------------
     * Database Column Name : `id`
     * Database Data Type   : BIGINT (PostgreSQL/MySQL/H2)
     * Key Type             : Primary Key
     * Generation Strategy  : GenerationType.IDENTITY (Uses SQL AUTO_INCREMENT / SERIAL)
     * 
     * WHY THIS MAPPING:
     * - @Id informs JPA that `id` is the Primary Key column of table `employees`.
     * - @GeneratedValue(strategy = GenerationType.IDENTITY) relies on database identity 
     *   columns to automatically increment and assign primary keys upon SQL INSERT.
     * -----------------------------------------------------------------------------
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * FIELD: name
     * -----------------------------------------------------------------------------
     * Database Column Name : `name`
     * Database Data Type   : VARCHAR(100)
     * SQL Constraints      : NOT NULL (nullable = false)
     * 
     * WHY THIS MAPPING:
     * - @Column(name = "name", nullable = false, length = 100) explicitly maps Java `name`
     *   to column `name` in table `employees` with max size 100 characters and mandatory value.
     * -----------------------------------------------------------------------------
     */
    @Column(name = "freak", nullable = false, length = 100)
    private String name;

    /**
     * FIELD: email
     * -----------------------------------------------------------------------------
     * Database Column Name : `email`
     * Database Data Type   : VARCHAR(100)
     * SQL Constraints      : UNIQUE, NOT NULL (unique = true, nullable = false)
     * 
     * WHY THIS MAPPING:
     * - `unique = true` creates a database UNIQUE index/constraint on the `email` column,
     *   guaranteeing no duplicate employee email addresses exist in the DB.
     * -----------------------------------------------------------------------------
     */
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    /**
     * FIELD: department
     * -----------------------------------------------------------------------------
     * Database Column Name : `department`
     * Database Data Type   : VARCHAR(50)
     * SQL Constraints      : NOT NULL (nullable = false)
     * 
     * WHY THIS MAPPING:
     * - Maps Java `department` to column `department` with VARCHAR length 50 in table `employees`.
     * -----------------------------------------------------------------------------
     */
    @Column(name = "department", nullable = false, length = 50)
    private String department;

    /**
     * FIELD: salary
     * -----------------------------------------------------------------------------
     * Database Column Name : `salary`
     * Database Data Type   : NUMERIC(12, 2) / DECIMAL(12, 2)
     * SQL Precision & Scale: Precision = 12 (total digits), Scale = 2 (fractional digits)
     * 
     * WHY THIS MAPPING:
     * - `BigDecimal` prevents floating-point rounding errors common with `double`/`float`.
     * - `precision = 12, scale = 2` maps to SQL column definition `DECIMAL(12, 2)`, 
     *   allowing salary amounts up to 9,999,999,999.99.
     * -----------------------------------------------------------------------------
     */
    @Column(name = "salary", precision = 12, scale = 2)
    private BigDecimal salary;

    /**
     * FIELD: designation
     * -----------------------------------------------------------------------------
     * Database Column Name : `designation`
     * Database Data Type   : VARCHAR(50)
     * SQL Constraints      : NULLABLE (nullable defaults to true)
     * 
     * WHY THIS MAPPING:
     * - Maps Java `designation` to column `designation` with VARCHAR length 50 in table `employees`.
     * -----------------------------------------------------------------------------
     */
    @Column(name = "designation", length = 50)
    private String designation;

    // Default No-Arg Constructor (Required by JPA specification)
    public Employee() {}

    public Employee(String name, String email, String department, BigDecimal salary, String designation) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.designation = designation;
    }

    public Employee(Long id, String name, String email, String department, BigDecimal salary, String designation) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.designation = designation;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
}
