package com.example.customer.repository; // Declares repository package namespace

import com.example.customer.model.Customer; // Imports Customer JPA entity class
import org.springframework.data.jpa.repository.JpaRepository; // Imports Spring Data JPA repository interface
import org.springframework.stereotype.Repository; // Imports Spring stereotype annotation for repositories

/**
 * Spring Data JPA Repository interface for Customer entity.
 * Provides out-of-the-box CRUD methods: findAll(), findById(), save(), deleteById().
 */
@Repository // Indicates that this interface is a Data Access Object (DAO) repository component
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Inherits all standard database CRUD methods for Customer entity with Long primary key
}
