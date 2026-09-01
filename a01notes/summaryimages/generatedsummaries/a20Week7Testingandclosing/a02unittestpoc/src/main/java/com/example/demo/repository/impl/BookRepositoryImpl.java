package com.example.demo.repository.impl;

import com.example.demo.repository.BookRepository;

/**
 * REAL REPOSITORY IMPLEMENTATION (Simulates Real Database Access)
 *
 * NOTE FOR STUDENTS:
 * - This is the actual concrete implementation class of BookRepository.
 * - In a real enterprise application, this class connects to a real database (PostgreSQL, MySQL, Oracle, etc.).
 * - To demonstrate real-world behavior, this class includes:
 *   1. System.out.println statements showing database queries executing.
 *   2. Artificial slowness (Thread.sleep of 2 seconds) simulating network/database query latency.
 *
 * When running MainApp.java:
 * - You WILL see the System.out logs printed.
 * - You WILL feel the 2-second delay per call (total ~4 seconds).
 *
 * When running Unit Tests (BookServiceTest.java):
 * - Mockito replaces this entire class with a fast MOCK object.
 * - Therefore, this code, its System.out logs, and its 2-second sleep NEVER execute during unit testing!
 */
public class BookRepositoryImpl implements BookRepository {

    @Override
    public String findById(int id) {
        System.out.println(">>> [REAL REPOSITORY] Connecting to Database to query book by ID: " + id + "...");
        try {
            // Simulate 2 seconds database query delay / network latency
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(">>> [REAL REPOSITORY] Database query completed successfully!");

        if (id == 1) {
            return "Clean Code";
        }
        return null;
    }

    @Override
    public boolean save(String title) {
        System.out.println(">>> [REAL REPOSITORY] Executing SQL INSERT INTO books VALUES ('" + title + "')...");
        try {
            // Simulate 2 seconds database write transaction delay
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(">>> [REAL REPOSITORY] Database transaction committed successfully!");
        return true;
    }
}
