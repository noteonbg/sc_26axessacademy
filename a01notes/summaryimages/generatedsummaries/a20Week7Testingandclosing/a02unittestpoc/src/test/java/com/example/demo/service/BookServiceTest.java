package com.example.demo.service;

import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * UNIT TEST LAYER (Using JUnit 5 + Mockito)
 *
 * KEY LESSON FOR STUDENTS:
 * ---------------------------------------------------------------------------------------
 * 1. When running MainApp.java:
 *    - Uses real 'BookRepositoryImpl'.
 *    - Prints '>>> [REAL REPOSITORY]...' messages to System.out.
 *    - Takes ~4 seconds because each call sleeps for 2 seconds (simulating database latency).
 *
 * 2. When running this Unit Test (BookServiceTest.java via 'mvn test'):
 *    - Mockito creates a MOCK (fake) BookRepository instance via @Mock.
 *    - 'BookRepositoryImpl' is NEVER instantiated or executed!
 *    - NO '>>> [REAL REPOSITORY]...' messages will appear in the output.
 *    - NO 2-second sleep delays occur. The tests run INSTANTLY (in milliseconds)!
 * ---------------------------------------------------------------------------------------
 */
@ExtendWith(MockitoExtension.class) // Integrates Mockito with JUnit 5
public class BookServiceTest {

    /**
     * @Mock creates a fake dynamic proxy of the BookRepository interface.
     * Mockito intercepts all calls to this object and DOES NOT execute any real repository code.
     */
    @Mock
    private BookRepository bookRepository;

    /**
     * @InjectMocks creates a real instance of BookService and injects the fake 'bookRepository' mock into it.
     */
    @InjectMocks
    private BookService bookService;

    /**
     * TEST CASE 1: Testing Function 1 (getBookById) with Mocking
     */
    @Test
    void testGetBookById() {
        System.out.println("\n-------------------------------------------------------------------------");
        System.out.println(" [UNIT TEST] Running testGetBookById() using Mockito Mock...");
        System.out.println(" Notice: NO '[REAL REPOSITORY]' logs and NO 2-second delay!");
        System.out.println("-------------------------------------------------------------------------");

        long start = System.currentTimeMillis();

        // 1. ARRANGE: Stub mock repository to return "Clean Code" for ID 1
        when(bookRepository.findById(1)).thenReturn("Clean Code");

        // 2. ACT: Call service method
        String result = bookService.getBookById(1);

        // 3. ASSERT: Check that service returns expected title
        assertEquals("Clean Code", result);

        // 4. VERIFY: Verify that repository findById(1) was invoked on the mock
        verify(bookRepository).findById(1);

        long end = System.currentTimeMillis();
        System.out.println(">>> SUCCESS: Test completed in ONLY " + (end - start) + " ms! (Fast because of Mocking)\n");
    }

    /**
     * TEST CASE 2: Testing Function 2 (addBook) with Mocking
     */
    @Test
    void testAddBook() {
        System.out.println("\n-------------------------------------------------------------------------");
        System.out.println(" [UNIT TEST] Running testAddBook() using Mockito Mock...");
        System.out.println(" Notice: NO '[REAL REPOSITORY]' logs and NO 2-second delay!");
        System.out.println("-------------------------------------------------------------------------");

        long start = System.currentTimeMillis();

        // 1. ARRANGE: Stub mock repository to return true for "Effective Java"
        when(bookRepository.save("Effective Java")).thenReturn(true);

        // 2. ACT: Call service method
        boolean isAdded = bookService.addBook("Effective Java");

        // 3. ASSERT: Check that service returned true
        assertTrue(isAdded);

        // 4. VERIFY: Verify that repository save("Effective Java") was invoked on the mock
        verify(bookRepository).save("Effective Java");

        long end = System.currentTimeMillis();
        System.out.println(">>> SUCCESS: Test completed in ONLY " + (end - start) + " ms! (Fast because of Mocking)\n");
    }
}
