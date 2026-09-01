package com.example.demo.repository;

/**
 * REPOSITORY LAYER (Contract/Interface)
 * - In a real application, this layer would connect to a database (PostgreSQL, MySQL, JPA, etc.).
 * - For unit testing with Mockito, we ONLY need this interface definition.
 * - We DO NOT create a real database class because Mockito generates a fake dynamic implementation at runtime.
 */
public interface BookRepository {

    /**
     * Function 1: Retrieves a book title given its unique identifier (ID).
     * @param id The unique integer ID of the book.
     * @return The book title as a String, or null if not found.
     */
    String findById(int id);

    /**
     * Function 2: Saves a new book title to the data store.
     * @param title The title of the book to save.
     * @return true if saved successfully, false otherwise.
     */
    boolean save(String title);
}
