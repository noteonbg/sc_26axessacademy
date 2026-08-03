package com.example.employee.model;

import java.util.Objects;

/**
 * Domain model representing an Employee.
 * empNo is the unique immutable identifier.
 * empName, email, and location are updateable attributes.
 */
public class Employee {
    private final int empNo;
    private String empName;
    private String email;
    private String location;

    public Employee(int empNo, String empName, String email, String location) {
        this.empNo = empNo;
        this.empName = empName;
        this.email = email;
        this.location = location;
    }

    // Getter for empNo (No setter provided as empNo is immutable)
    public int getEmpNo() {
        return empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return empNo == employee.empNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo);
    }

    @Override
    public String toString() {
        return String.format("Employee [empNo=%d, empName='%s', email='%s', location='%s']",
                empNo, empName, email, location);
    }
}
