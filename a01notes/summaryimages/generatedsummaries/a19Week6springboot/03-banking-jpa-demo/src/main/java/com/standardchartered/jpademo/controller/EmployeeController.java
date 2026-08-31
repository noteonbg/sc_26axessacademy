package com.standardchartered.jpademo.controller;

import com.standardchartered.jpademo.dto.EmployeeDto;
import com.standardchartered.jpademo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * =================================================================================
 * SPRING BOOT REST CONTROLLER SYNTAX & ANNOTATION GUIDE
 * =================================================================================
 * 
 * ANNOTATION SYNTAX EXPLANATIONS:
 * 
 * 1. `@RestController`:
 *    - Combination of `@Controller` + `@ResponseBody`.
 *    - Instructs Spring Framework that method return values are automatically serialized 
 *      into JSON HTTP response bodies using Jackson ObjectMapper.
 * 
 * 2. `@RequestMapping("/api/v1/jpa/employees")`:
 *    - Sets the base URL path prefix for all endpoints exposed by this controller class.
 * 
 * 3. `ResponseEntity<T>`:
 *    - Wrapper class representing the complete HTTP response (Status Code + Headers + Body).
 *    - Best practice for explicit control over HTTP response codes (e.g. 200 OK, 201 Created, 204 No Content).
 * =================================================================================
 */
@RestController
@RequestMapping("/api/v1/jpa/employees")
public class EmployeeController {

    // Final dependency field ensuring immutability & thread-safety
    private final EmployeeService employeeService;

    /**
     * SYNTAX: Constructor Injection with `@Autowired`
     * - Spring automatically injects the singleton `EmployeeService` bean at application startup.
     */
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 1. GET ALL EMPLOYEES
     * SYNTAX: `@GetMapping` maps HTTP GET requests to `/api/v1/jpa/employees`
     * RETURNS: List of EmployeeDto records wrapped in HTTP 200 OK status code.
     */
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /**
     * 2. GET EMPLOYEE BY ID
     * SYNTAX:
     * - `@GetMapping("/{id}")`: Binds URL template variable `{id}` (e.g. `/api/v1/jpa/employees/5`).
     * - `@PathVariable("id")`: Extracts path parameter value from URL into Java `Long id` method variable.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    /**
     * 3. CREATE EMPLOYEE
     * SYNTAX:
     * - `@PostMapping`: Maps HTTP POST requests for resource creation.
     * - `@RequestBody`: Deserializes incoming JSON payload into an `EmployeeDto` Java record.
     * - `HttpStatus.CREATED`: Explicitly returns HTTP 201 Created status code upon success.
     */
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    /**
     * 4. UPDATE EMPLOYEE
     * SYNTAX:
     * - `@PutMapping("/{id}")`: Maps HTTP PUT requests for updating existing resources.
     * - Combines `@PathVariable("id")` for resource identity and `@RequestBody` for updated DTO values.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable("id") Long id,
            @RequestBody EmployeeDto employeeDetails) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     * 5. DELETE EMPLOYEE
     * SYNTAX:
     * - `@DeleteMapping("/{id}")`: Maps HTTP DELETE requests.
     * - `ResponseEntity.noContent().build()`: Returns HTTP 204 No Content response with empty body.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 6. GET BY DEPARTMENT
     * SYNTAX: `@GetMapping("/department/{department}")`: Maps GET `/api/v1/jpa/employees/department/Engineering`
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByDepartment(@PathVariable("department") String department) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(department));
    }
}
