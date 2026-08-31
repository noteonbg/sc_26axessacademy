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
 * Service Layer encapsulating business logic for Employee single-table CRUD operations.
 * Performs Entity <-> DTO conversions to protect domain entities from being directly exposed.
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Entity -> DTO Mapper
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

    // DTO -> Entity Mapper
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

    // READ: Retrieve all employees
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    // READ: Retrieve single employee by ID
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + id));
    }

    // CREATE: Save a new employee
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto dto) {
        Employee employee = mapToEntity(dto);
        employee.setId(null); // Ensure auto-generated ID for creation
        Employee saved = employeeRepository.save(employee);
        return mapToDto(saved);
    }

    // UPDATE: Update existing employee details
    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeDto updatedDetails) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + id));
        
        existingEmployee.setName(updatedDetails.name());
        existingEmployee.setEmail(updatedDetails.email());
        existingEmployee.setDepartment(updatedDetails.department());
        existingEmployee.setSalary(updatedDetails.salary());
        existingEmployee.setDesignation(updatedDetails.designation());
        
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

    // READ: Find employees by department
    @Transactional(readOnly = true)
    public List<EmployeeDto> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartmentIgnoreCase(department).stream()
                .map(this::mapToDto)
                .toList();
    }
}
