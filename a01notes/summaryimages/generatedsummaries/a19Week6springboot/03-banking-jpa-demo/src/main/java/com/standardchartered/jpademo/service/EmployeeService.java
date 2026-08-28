package com.standardchartered.jpademo.service;

import com.standardchartered.jpademo.entity.Employee;
import com.standardchartered.jpademo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service Layer encapsulating business logic for Employee single-table CRUD operations.
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // READ: Retrieve all employees
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // READ: Retrieve single employee by ID
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + id));
    }

    // CREATE: Save a new employee
    @Transactional
    public Employee createEmployee(Employee employee) {
        if (employee.getId() != null) {
            employee.setId(null); // Ensure auto-generated ID for creation
        }
        return employeeRepository.save(employee);
    }

    // UPDATE: Update existing employee details
    @Transactional
    public Employee updateEmployee(Long id, Employee updatedDetails) {
        Employee existingEmployee = getEmployeeById(id);
        
        existingEmployee.setName(updatedDetails.getName());
        existingEmployee.setEmail(updatedDetails.getEmail());
        existingEmployee.setDepartment(updatedDetails.getDepartment());
        existingEmployee.setSalary(updatedDetails.getSalary());
        existingEmployee.setDesignation(updatedDetails.getDesignation());
        
        // Automatic dirty checking updates the row in DB at transaction commit
        return employeeRepository.save(existingEmployee);
    }

    // DELETE: Remove employee by ID
    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new NoSuchElementException("Cannot delete. Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // READ: Find employees by department
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartmentIgnoreCase(department);
    }
}
