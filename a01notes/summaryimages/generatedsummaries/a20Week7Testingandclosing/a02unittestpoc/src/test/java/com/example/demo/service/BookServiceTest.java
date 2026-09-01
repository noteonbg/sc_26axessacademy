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
 * Key Concepts Demonstrated:
 * 1. @ExtendWith(MockitoExtension.class): Integrates Mockito framework with JUnit 5.
 * 2. @Mock: Creates a fake/mock implementation of BookRepository.
 * 3. @InjectMocks: Creates a real BookService instance and automatically injects the fake BookRepository into it.
 * 4. when(...).thenReturn(...): Stubbing behavior — defining what fake data the mock should return when invoked.
 * 5. verify(...): Behavioral verification — checking that the mock method was actually called with expected arguments.
 */
@ExtendWith(MockitoExtension.class) // Enables Mockito annotations in JUnit 5
public class BookServiceTest {

    /**
     * @Mock creates a dummy object of BookRepository.
     * Mockito automatically intercepts calls to 'bookRepository' so NO real database or code execution occurs.
     */
    @Mock
    private BookRepository bookRepository;

    /**
     * @InjectMocks creates a real instance of 'BookService'
     * and automatically injects the '@Mock private BookRepository bookRepository' into its constructor.
     */
    @InjectMocks
    private BookService bookService;

    /**
     * TEST CASE 1: Testing Function 1 (getBookById)
     * Goal: Verify that when getBookById(1) is called on Service, it delegates to
     * Repository.findById(1) and returns the correct title.
     */
    @Test
    void testGetBookById() {
        // =========================================================================
        // STEP 1: ARRANGE (Setup expectations / stubbing)
        // "When the service calls bookRepository.findById(1), return 'Clean Code'"
        // =========================================================================
        when(bookRepository.findById(1)).thenReturn("Clean Code");

        // =========================================================================
        // STEP 2: ACT (Execute the actual service method being tested)
        // =========================================================================
        String result = bookService.getBookById(1);

        // =========================================================================
        // STEP 3: ASSERT (Verify output matches expectations)
        // Check that the service returned "Clean Code"
        // =========================================================================
        assertEquals("Clean Code", result);

        // =========================================================================
        // STEP 4: VERIFY (Confirm Mock interaction)
        // Ensure that bookRepository.findById(1) was executed exactly as expected
        // =========================================================================
        verify(bookRepository).findById(1);
    }

    /**
     * TEST CASE 2: Testing Function 2 (addBook)
     * Goal: Verify that when addBook("Effective Java") is called on Service, it calls
     * Repository.save("Effective Java") and returns true.
     */
    @Test
    void testAddBook() {
        // =========================================================================
        // STEP 1: ARRANGE (Setup expectations / stubbing)
        // "When the service calls bookRepository.save("Effective Java"), return true"
        // =========================================================================
        when(bookRepository.save("Effective Java")).thenReturn(true);

        // =========================================================================
        // STEP 2: ACT (Execute the service method being tested)
        // =========================================================================
        boolean isAdded = bookService.addBook("Effective Java");

        // =========================================================================
        // STEP 3: ASSERT (Verify output matches expectations)
        // Check that the service returned true
        // =========================================================================
        assertTrue(isAdded);

        // =========================================================================
        // STEP 4: VERIFY (Confirm Mock interaction)
        // Ensure that bookRepository.save("Effective Java") was executed by the service
        // =========================================================================
        verify(bookRepository).save("Effective Java");
    }
}
