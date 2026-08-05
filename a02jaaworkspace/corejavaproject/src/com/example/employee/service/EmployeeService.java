package com.example.employee.service;

import com.example.employee.exception.DuplicateEmployeeException;
import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;

import java.util.List;

/**
 * Service Layer for Employee Management operations.
 * Implements Dependency Inversion by accepting any EmployeeRepository implementation
 * (such as EmployeeArrayListRepository or EmployeeHashMapRepository).
 */
public class EmployeeService {

    private final EmployeeRepository repository;

    /**
     * Constructor injection allowing seamless switching between repositories.
     * @param repository The storage repository implementation (ArrayList or HashMap)
     */
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    /**
     * Adds a new employee.
     * Throws DuplicateEmployeeException if empNo already exists.
     */
    public void addEmployee(int empNo, String empName, String email, String location) {
        if (repository.existsById(empNo)) {
            throw new DuplicateEmployeeException("Employee with empNo " + empNo + " already exists.");
        }
        Employee employee = new Employee(empNo, empName, email, location);
        repository.save(employee);
    }

    /**
     * Retrieves all employees.
     */
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    /**
     * Retrieves an employee by empNo.
     * Throws EmployeeNotFoundException if not found.
     */
    public Employee getEmployeeById(int empNo) {
        return repository.findById(empNo)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with empNo " + empNo + " not found."));
    }

    /**
     * Updates an existing employee's details (empName, email, location ONLY).
     * empNo is immutable and cannot be updated.
     * Throws EmployeeNotFoundException if employee does not exist.
     */
    public void updateEmployee(int empNo, String newEmpName, String newEmail, String newLocation) {
        boolean updated = repository.update(empNo, newEmpName, newEmail, newLocation);
        if (!updated) {
            throw new EmployeeNotFoundException("Cannot update. Employee with empNo " + empNo + " does not exist.");
        }
    }

    /**
     * Deletes an employee by empNo.
     * Throws EmployeeNotFoundException if employee does not exist.
     */
    public void deleteEmployee(int empNo) {
        boolean deleted = repository.deleteById(empNo);
        if (!deleted) {
            throw new EmployeeNotFoundException("Cannot delete. Employee with empNo " + empNo + " does not exist.");
        }
    }
}
