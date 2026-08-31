package com.standardchartered.jpademo.service;

import com.standardchartered.jpademo.dto.EmployeeDto;
import com.standardchartered.jpademo.entity.Employee;
import com.standardchartered.jpademo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * =================================================================================
 * SERVICE LAYER & ENTITY <-> DTO CONVERSION SYNTAX GUIDE
 * =================================================================================
 * 
 * SYNTAX EXPLANATIONS:
 * 
 * 1. `@Service`:
 *    - Spring component stereotype marking this class as part of the Service Layer holding 
 *      business logic, validation, and transaction management rules.
 * 
 * 2. `@Transactional(readOnly = true)` vs `@Transactional`:
 *    - `readOnly = true`: Performance optimization for database READ queries. Tells Hibernate 
 *      to skip dirty-checking entity state changes at transaction commit.
 *    - `@Transactional`: Opens write transaction context. Modifications to MANAGED entities 
 *      are automatically synchronized to the SQL database.
 * =================================================================================
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * SYNTAX: Entity -> DTO Mapper Method
     * Converts internal JPA `Employee` entity to clean `EmployeeDto` record.
     */
    public EmployeeDto mapToDto(Employee employee) {
        if (employee == null) return null;
        return new EmployeeDto(
            employee.getId(),
            employee.getName(),
            employee.getEmail(),
            employee.getDepartment(),
            employee.getSalary(),
            employee.getDesignation()
        );
    }

    /**
     * SYNTAX: DTO -> Entity Mapper Method
     * Converts incoming `EmployeeDto` record into internal JPA `Employee` entity.
     */
    public Employee mapToEntity(EmployeeDto dto) {
        if (dto == null) return null;
        return new Employee(
            dto.id(),
            dto.name(),
            dto.email(),
            dto.department(),
            dto.salary(),
            dto.designation()
        );
    }

    // READ: Retrieve all employees converted to DTO list
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDto) // Stream syntax: maps each Employee entity to EmployeeDto
                .toList(); // Collects mapped elements into an unmodifiable List (Java 16+)
    }

    // READ: Retrieve single employee by ID as DTO
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + id));
    }

    // CREATE: Save a new employee from DTO payload
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto dto) {
        Employee employee = mapToEntity(dto);
        employee.setId(null); // Explicitly ensure ID is null for SQL INSERT generation
        Employee saved = employeeRepository.save(employee);
        return mapToDto(saved);
    }

    // UPDATE: Update existing employee entity fields using DTO values
    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeDto updatedDetails) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + id));
        
        // Update fields on the MANAGED entity instance
        existingEmployee.setName(updatedDetails.name());
        existingEmployee.setEmail(updatedDetails.email());
        existingEmployee.setDepartment(updatedDetails.department());
        existingEmployee.setSalary(updatedDetails.salary());
        existingEmployee.setDesignation(updatedDetails.designation());
        
        // Automatic dirty-checking syncs changes to DB; repository.save returns saved entity
        Employee saved = employeeRepository.save(existingEmployee);
        return mapToDto(saved);
    }

    // DELETE: Remove employee by ID
    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new NoSuchElementException("Cannot delete. Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // READ: Find employees by department as DTO list
    @Transactional(readOnly = true)
    public List<EmployeeDto> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartmentIgnoreCase(department).stream()
                .map(this::mapToDto)
                .toList();
    }
}
