package com.sc.jmeterpoc.service;

import com.sc.jmeterpoc.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * =====================================================================================
 * Service Layer: EmployeeService
 * =====================================================================================
 * 
 * WHY THIS CLASS WAS CREATED:
 * ---------------------------
 * 1. Business Logic Isolation: Keeps controller thin and separates business operations.
 * 2. Thread Safety Under Concurrency: When JMeter hits the API with 50 simultaneous threads,
 *    standard collections like HashMap or ArrayList will corrupt or throw ConcurrentModificationException.
 *    We use ConcurrentHashMap and AtomicLong to guarantee thread safety with zero locking overhead.
 * 3. Realistic Latency Simulation: Real enterprise database queries take 20ms–80ms.
 *    We simulate realistic network/disk latency so JMeter metrics reflect genuine production behavior.
 */
@Service
// ^-- @Service informs Spring IoC container to manage this class as a singleton bean.
public class EmployeeService {

    // ConcurrentHashMap provides thread-safe concurrent reads and writes for 50+ threads
    private final Map<Long, Employee> employeeRepository = new ConcurrentHashMap<>();

    // AtomicLong generates thread-safe auto-incrementing IDs without race conditions
    private final AtomicLong idSequence = new AtomicLong(0);

    /**
     * Constructor seeds initial banking employee records into memory.
     */
    public EmployeeService() {
        saveEmployee(new Employee(null, "Aarav Sharma", "Retail Banking", "FullStack Engineer", 85000.0));
        saveEmployee(new Employee(null, "Priya Nair", "Wealth Management", "Data Analyst", 92000.0));
        saveEmployee(new Employee(null, "Rahul Verma", "Risk & Compliance", "QA Automation Lead", 88000.0));
        saveEmployee(new Employee(null, "Sneha Patel", "Core Banking", "Backend Specialist", 95000.0));
        saveEmployee(new Employee(null, "Vikram Singh", "Cybersecurity", "Security Architect", 110000.0));
    }

    /**
     * Retrieves all employees.
     * Simulates 25ms - 65ms query time.
     * 
     * @return List of all current employee entities
     */
    public List<Employee> getAllEmployees() {
        // Line below introduces realistic read latency
        simulateDatabaseDelay(25, 65);
        // Returns a safe snapshot copy of current values
        return new ArrayList<>(employeeRepository.values());
    }

    /**
     * Retrieves a single employee by their ID.
     * 
     * @param id Employee primary key
     * @return Optional containing the employee if found, or empty Optional
     */
    public Optional<Employee> getEmployeeById(Long id) {
        simulateDatabaseDelay(15, 35);
        return Optional.ofNullable(employeeRepository.get(id));
    }

    /**
     * Creates and persists a new employee record.
     * Simulates 35ms - 80ms disk write / index commit latency.
     * 
     * @param employee The employee data submitted in the POST request body
     * @return Saved employee with assigned auto-generated ID
     */
    public Employee createEmployee(Employee employee) {
        simulateDatabaseDelay(35, 80);
        return saveEmployee(employee);
    }

    /**
     * Internal helper to assign ID and put into repository.
     */
    private Employee saveEmployee(Employee employee) {
        // Atomic increment ensures no two concurrent threads get the same ID
        long newId = idSequence.incrementAndGet();
        employee.setId(newId);
        employeeRepository.put(newId, employee);
        return employee;
    }

    /**
     * Returns the total count of employees currently stored in memory.
     */
    public long getTotalCount() {
        return employeeRepository.size();
    }

    /**
     * Simulates real-world database I/O latency.
     * Uses ThreadLocalRandom for high-performance non-blocking random number generation.
     *
     * @param minMs Minimum milliseconds to pause
     * @param maxMs Maximum milliseconds to pause
     */
    private void simulateDatabaseDelay(int minMs, int maxMs) {
        try {
            int delay = ThreadLocalRandom.current().nextInt(minMs, maxMs + 1);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            // Restore interrupted status for clean thread termination
            Thread.currentThread().interrupt();
        }
    }
}
