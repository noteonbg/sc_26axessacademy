package com.example.customer.service; // Declares service package namespace

import com.example.customer.dto.CustomerResponseDto; // Import Customer response DTO
import com.example.customer.dto.UpdateCustomerRequestDto; // Import Customer update request DTO

import java.util.List; // Import java.util.List collection interface

/**
 * Service layer interface defining business capabilities for Customer management.
 */
public interface CustomerService {

    /**
     * Selects and returns all customers.
     * @return List of CustomerResponseDto objects.
     */
    List<CustomerResponseDto> getAllCustomers();

    /**
     * Finds customer by ID.
     * @param customerId Customer primary key ID.
     * @return CustomerResponseDto object.
     */
    CustomerResponseDto getCustomerById(Long customerId);

    /**
     * Updates customer email and location ONLY.
     * @param customerId Customer ID to update.
     * @param updateDto DTO containing updated email and location.
     * @return CustomerResponseDto updated customer details.
     */
    CustomerResponseDto updateCustomer(Long customerId, UpdateCustomerRequestDto updateDto);
}
