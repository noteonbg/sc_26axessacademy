package com.example.employee.app;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.impl.EmployeeArrayListRepository;
import com.example.employee.repository.impl.EmployeeHashMapRepository;
import com.example.employee.service.EmployeeService;

import java.util.List;

/**
 * Demonstration Application showing CRUD operations on Employee
 * using ArrayList storage initially, and seamlessly switching to HashMap storage.
 */
public class MainApp {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   EMPLOYEE MANAGEMENT SYSTEM DEMO");
        System.out.println("=================================================\n");

        // ---------------------------------------------------------------------
        // PART 1: USING ARRAYLIST STORAGE
        // ---------------------------------------------------------------------
        System.out.println(">>> 1. USING ARRAYLIST REPOSITORY ARCHITECTURE <<<\n");
        EmployeeRepository arrayListRepo = new EmployeeArrayListRepository();
        EmployeeService arrayListService = new EmployeeService(arrayListRepo);

        runCrudDemo(arrayListService, "ArrayList");

        // ---------------------------------------------------------------------
        // PART 2: SWITCHING TO HASHMAP STORAGE
        // ---------------------------------------------------------------------
        System.out.println("\n>>> 2. SWITCHING STORAGE TO HASHMAP REPOSITORY ARCHITECTURE <<<\n");
        EmployeeRepository hashMapRepo = new EmployeeHashMapRepository();
        EmployeeService hashMapService = new EmployeeService(hashMapRepo);

        runCrudDemo(hashMapService, "HashMap");
    }

    private static void runCrudDemo(EmployeeService service, String storageType) {
        // --- 1. INSERT (CREATE) ---
        System.out.println("[" + storageType + "] Inserting Employees...");
        service.addEmployee(101, "Alice Smith", "alice@example.com", "New York");
        service.addEmployee(102, "Bob Jones", "bob@example.com", "Chicago");
        service.addEmployee(103, "Charlie Brown", "charlie@example.com", "San Francisco");
        System.out.println("Successfully added 3 employees.");

        // --- 2. VIEW ALL (READ) ---
        System.out.println("\n[" + storageType + "] Viewing All Employees:");
        printEmployees(service.getAllEmployees());

        // --- 3. UPDATE (ONLY empName, email, location CAN BE UPDATED) ---
        System.out.println("\n[" + storageType + "] Updating Employee 102 (empName, email, location)...");
        System.out.println("Before Update: " + service.getEmployeeById(102));
        service.updateEmployee(102, "Robert Jones Jr.", "robert.j@example.com", "Los Angeles");
        System.out.println("After Update:  " + service.getEmployeeById(102));

        // --- 4. DELETE ---
        System.out.println("\n[" + storageType + "] Deleting Employee 101...");
        service.deleteEmployee(101);
        System.out.println("Employee 101 deleted.");

        // --- 5. VIEW ALL AFTER DELETE ---
        System.out.println("\n[" + storageType + "] Viewing All Employees After Deletion:");
        printEmployees(service.getAllEmployees());
    }

    private static void printEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("  No employees found.");
        } else {
            employees.forEach(emp -> System.out.println("  -> " + emp));
        }
    }
}
