package com.example.demo.service;

import com.example.demo.repository.BookRepository;

/**
 * SERVICE LAYER (Business Logic)
 * - Contains core application logic (validation, null checks, business rules).
 * - Depends on BookRepository to fetch and store data.
 * - Uses Constructor Injection so Mockito can easily inject a mock repository during unit testing.
 */
public class BookService {

    // Dependency on Repository interface
    private final BookRepository bookRepository;

    /**
     * Constructor for Dependency Injection.
     * During unit testing, Mockito will pass a fake (mocked) BookRepository instance here.
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Function 1: Fetch book details by ID.
     * Business Rule: If repository returns null, return "Book Not Found".
     *
     * @param id Book ID to search for
     * @return Book title or "Book Not Found"
     */
    public String getBookById(int id) {
        // Step 1: Call repository method (intercepted by Mockito in unit tests)
        String title = bookRepository.findById(id);
        System.out.println("inside book service getbookbyid"); // Debugging/logging

        // Step 2: Perform business logic on the returned data
        if (title == null) {
            return "Book Not Found";
        }
        return title;
    }

    /**
     * Function 2: Add a new book.
     * Business Rule: Do not save if title is null or blank.
     *
     * @param title Book title to save
     * @return true if added successfully, false otherwise
     */
    public boolean addBook(String title) {
        // Step 1: Input validation (business logic check)
        if (title == null || title.isBlank()) {
            return false;
        }

        // Step 2: Delegate saving to repository (intercepted by Mockito in unit tests)
        return bookRepository.save(title);
    }
}
