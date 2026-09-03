package com.sc.jmeterpoc.model;

import java.io.Serializable;

/**
 * =====================================================================================
 * Model: Employee Domain Entity
 * =====================================================================================
 * 
 * WHY THIS CLASS WAS CREATED:
 * ---------------------------
 * Represents the data contract exchanged between Apache JMeter and the Spring Boot API.
 * When JMeter sends a POST request with a JSON payload, Jackson automatically deserializes
 * the JSON into this Java POJO. When JMeter issues a GET request, this object is serialized
 * back into JSON format and returned with an HTTP 200 status code.
 */
public class Employee implements Serializable {

    // Unique version identifier for Java serialization mechanism
    private static final long serialVersionUID = 1L;

    // Unique numeric primary key for the employee
    private Long id;

    // Full name of the employee (e.g., "Kavita Iyer")
    private String name;

    // Bank department name (e.g., "Transaction Banking", "Core Banking")
    private String department;

    // Job title / organizational role (e.g., "FullStack Engineer")
    private String role;

    // Annual compensation in local currency units
    private Double salary;

    /**
     * Default No-Args Constructor:
     * WHY IT EXISTS: Spring's Jackson JSON serializer (ObjectMapper) requires a default
     * zero-argument constructor to instantiate empty POJOs during JSON deserialization.
     */
    public Employee() {
    }

    /**
     * Parameterized Constructor:
     * WHY IT EXISTS: Conveniently initializes all fields at once when seeding data
     * or creating entities inside unit tests and service logic.
     */
    public Employee(Long id, String name, String department, String role, Double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.role = role;
        this.salary = salary;
    }

    // --- GETTERS AND SETTERS ---
    // Jackson uses these getters and setters to map JSON keys to private Java fields.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * toString() implementation:
     * WHY IT EXISTS: Enables clean console logging and debugging during performance tests.
     */
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", role='" + role + '\'' +
                ", salary=" + salary +
                '}';
    }
}
