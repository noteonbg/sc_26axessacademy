package com.example.demo;

import com.example.demo.repository.BookRepository;
import com.example.demo.repository.impl.BookRepositoryImpl;
import com.example.demo.service.BookService;

/**
 * MAIN APPLICATION (Independent Runnable Class)
 *
 * NOTE FOR STUDENTS:
 * - This class represents running the application with the REAL BookRepositoryImpl.
 * - Run this class using 'mvn exec:java' or directly in your IDE.
 * - Observe the output:
 *   1. You will see [REAL REPOSITORY] logs printed to the console.
 *   2. It takes ~4 seconds to complete because each real repository call has a 2-second delay.
 */
public class MainApp {

    public static void main(String[] args) {
        System.out.println("=========================================================================");
        System.out.println("   RUNNING MAIN APPLICATION (USING REAL REPOSITORY WITH DATABASE DELAYS)");
        System.out.println("=========================================================================\n");

        long startTime = System.currentTimeMillis();

        // 1. Instantiate the REAL repository with built-in slowness
        BookRepository realRepository = new BookRepositoryImpl();

        // 2. Inject real repository into BookService
        BookService bookService = new BookService(realRepository);

        // 3. Call Function 1 (takes ~2 seconds)
        System.out.println("Calling bookService.getBookById(1)...");
        String title = bookService.getBookById(1);
        System.out.println("--> Result: " + title + "\n");

        // 4. Call Function 2 (takes ~2 seconds)
        System.out.println("Calling bookService.addBook('Effective Java')...");
        boolean saved = bookService.addBook("Effective Java");
        System.out.println("--> Result Saved: " + saved + "\n");

        long endTime = System.currentTimeMillis();
        double totalSeconds = (endTime - startTime) / 1000.0;

        System.out.println("=========================================================================");
        System.out.println(" MAIN APPLICATION COMPLETED IN: " + totalSeconds + " SECONDS");
        System.out.println(" Notice the slowness and [REAL REPOSITORY] sysout messages above!");
        System.out.println(" Compare this with 'mvn test', which runs INSTANTLY without real repository!");
        System.out.println("=========================================================================");
    }
}
