package com.sc.jmeterpoc.controller;

import com.sc.jmeterpoc.model.Employee;
import com.sc.jmeterpoc.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * =====================================================================================
 * REST Controller: EmployeeController
 * =====================================================================================
 * 
 * WHY THIS CLASS WAS CREATED:
 * ---------------------------
 * This class exposes the HTTP REST API endpoints required by Week 7 Handson 1 (Slide 14):
 * 1. "Write a load test using JMeter to hit the endpoint http://localhost:3000/sc_employees"
 * 2. "Add response assertions for HTTP 200/201 and a Summary Report listener."
 * 
 * This controller handles:
 * - GET  /sc_employees -> Returns HTTP 200 OK with the employee collection.
 * - POST /sc_employees -> Creates a new employee and returns HTTP 201 CREATED.
 * - GET  /sc_employees/stats -> Exposes real-time throughput metrics for live monitoring.
 */
@RestController
// ^-- @RestController combines @Controller and @ResponseBody.
//     Every method returns serialized JSON directly in the HTTP response body.

@RequestMapping(value = "/sc_employees", produces = MediaType.APPLICATION_JSON_VALUE)
// ^-- Sets the root URL path to "/sc_employees" and specifies JSON content-type header.

@CrossOrigin(origins = "*")
// ^-- Permits Cross-Origin Resource Sharing from any client / web UI.
public class EmployeeController {

    // Dependency Injection: Service layer reference
    private final EmployeeService employeeService;

    // Metrics counters for performance tracking
    private final AtomicLong getCounter = new AtomicLong(0);
    private final AtomicLong postCounter = new AtomicLong(0);
    private final long serverStartTime = System.currentTimeMillis();

    /**
     * Constructor Injection:
     * WHY IT EXISTS: Best practice in Spring Boot for loose coupling and testability.
     * Spring automatically injects the singleton EmployeeService bean.
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * =========================================================================
     * Endpoint 1: GET http://localhost:3000/sc_employees
     * =========================================================================
     * 
     * WHY THIS METHOD EXISTS:
     * -----------------------
     * Handson 1 requirement: JMeter simulates 50 concurrent users reading employee records.
     * 
     * HTTP STATUS RETURNED:
     * ---------------------
     * HTTP 200 OK (Validated by JMeter Response Assertion).
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEmployees() {
        // Line below tracks how many GET requests have hit this instance
        getCounter.incrementAndGet();

        // Fetch employee collection from business service
        List<Employee> list = employeeService.getAllEmployees();

        // Assemble standardized JSON response payload
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("count", list.size());
        response.put("data", list);

        // Returns HTTP 200 OK status code with JSON body
        return ResponseEntity.ok(response);
    }

    /**
     * =========================================================================
     * Endpoint 2: GET http://localhost:3000/sc_employees/{id}
     * =========================================================================
     * 
     * WHY THIS METHOD EXISTS:
     * -----------------------
     * Allows retrieving a specific employee by ID. Returns HTTP 200 if found or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEmployeeById(@PathVariable Long id) {
        getCounter.incrementAndGet();

        return employeeService.getEmployeeById(id)
                .map(emp -> {
                    Map<String, Object> resp = new HashMap<>();
                    resp.put("status", "success");
                    resp.put("data", emp);
                    return ResponseEntity.ok(resp);
                })
                .orElseGet(() -> {
                    Map<String, Object> resp = new HashMap<>();
                    resp.put("status", "error");
                    resp.put("message", "Employee with ID " + id + " not found");
                    // Returns HTTP 404 Not Found if record is missing
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
                });
    }

    /**
     * =========================================================================
     * Endpoint 3: POST http://localhost:3000/sc_employees
     * =========================================================================
     * 
     * WHY THIS METHOD EXISTS:
     * -----------------------
     * Handson 1 requirement: JMeter simulates users creating new employees under load.
     * 
     * HTTP STATUS RETURNED:
     * ---------------------
     * HTTP 201 CREATED (Validated by JMeter Response Assertion for POST).
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody Employee employee) {
        // Line below tracks how many POST creation requests have occurred
        postCounter.incrementAndGet();

        // Persist the new employee record
        Employee created = employeeService.createEmployee(employee);

        // Assemble JSON confirmation payload
        Map<String, Object> response = new HashMap<>();
        response.put("status", "created");
        response.put("message", "Employee record created successfully");
        response.put("data", created);

        // Explicitly return HTTP 201 CREATED status code as asserted in JMeter
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * =========================================================================
     * Endpoint 4: GET http://localhost:3000/sc_employees/stats
     * =========================================================================
     * 
     * WHY THIS METHOD EXISTS:
     * -----------------------
     * Diagnostic endpoint. During and after a load test, you can curl this endpoint
     * to view live total request counts, uptime, and server-side throughput (TPS).
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getPerformanceStats() {
        long uptimeSeconds = Math.max(1, (System.currentTimeMillis() - serverStartTime) / 1000);
        long totalReqs = getCounter.get() + postCounter.get();
        double tps = (double) totalReqs / uptimeSeconds;

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRequests", totalReqs);
        stats.put("getRequests", getCounter.get());
        stats.put("postRequests", postCounter.get());
        stats.put("totalEmployeesInDb", employeeService.getTotalCount());
        stats.put("uptimeSeconds", uptimeSeconds);
        stats.put("throughputTps", Math.round(tps * 100.0) / 100.0);

        return ResponseEntity.ok(stats);
    }
}
