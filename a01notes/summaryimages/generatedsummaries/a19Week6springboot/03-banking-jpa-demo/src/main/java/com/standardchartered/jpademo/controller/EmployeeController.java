package com.standardchartered.jpademo.controller;

import com.standardchartered.jpademo.dto.EmployeeDto;
import com.standardchartered.jpademo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller providing complete Single-Table CRUD endpoints using EmployeeDto payloads.
 * 
 * Endpoints:
 * - GET /api/v1/jpa/employees (List all)
 * - GET /api/v1/jpa/employees/{id} (Get by ID)
 * - POST /api/v1/jpa/employees (Create new)
 * - PUT /api/v1/jpa/employees/{id} (Update existing)
 * - DELETE /api/v1/jpa/employees/{id} (Delete by ID)
 * - GET /api/v1/jpa/employees/department/{dept} (Filter by department)
 */
@RestController
@RequestMapping("/api/v1/jpa/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 1. GET ALL EMPLOYEES
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // 2. GET EMPLOYEE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // 3. CREATE EMPLOYEE
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    // 4. UPDATE EMPLOYEE
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable("id") Long id,
            @RequestBody EmployeeDto employeeDetails) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    // 5. DELETE EMPLOYEE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // 6. GET BY DEPARTMENT
    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByDepartment(@PathVariable("department") String department) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(department));
    }
}
