package com.example.employee.repository;

import com.example.employee.model.Employee;
import java.util.List;
import java.util.Optional;

/**
 * Common Data Access Repository Interface for Employee storage implementations.
 * Demonstrates the Repository Pattern & Dependency Inversion Principle.
 */
public interface EmployeeRepository {

    /**
     * Inserts a new employee into storage.
     * @param employee Employee object to save
     */
    void save(Employee employee);

    /**
     * Finds an employee by their employee number.
     * @param empNo Employee number
     * @return Optional containing Employee if found, empty otherwise
     */
    Optional<Employee> findById(int empNo);

    /**
     * Retrieves all employees currently stored.
     * @return List of all employees
     */
    List<Employee> findAll();

    /**
     * Updates an existing employee's details (empName, email, location ONLY).
     * empNo remains unchanged.
     * @param empNo Fixed identifier of employee
     * @param newEmpName Updated name
     * @param newEmail Updated email
     * @param newLocation Updated location
     * @return true if updated successfully, false if employee was not found
     */
    boolean update(int empNo, String newEmpName, String newEmail, String newLocation);

    /**
     * Deletes an employee by their employee number.
     * @param empNo Employee number to delete
     * @return true if deleted successfully, false if employee was not found
     */
    boolean deleteById(int empNo);

    /**
     * Checks if an employee with given empNo exists in storage.
     * @param empNo Employee number
     * @return true if exists, false otherwise
     */
    boolean existsById(int empNo);
}
