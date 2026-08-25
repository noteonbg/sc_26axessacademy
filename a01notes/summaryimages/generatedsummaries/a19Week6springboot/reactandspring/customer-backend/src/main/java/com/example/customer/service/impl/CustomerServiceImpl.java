package com.example.customer.service.impl; // Declares service implementation package namespace

import com.example.customer.dto.CustomerResponseDto; // Import response DTO
import com.example.customer.dto.UpdateCustomerRequestDto; // Import update request DTO
import com.example.customer.exception.CustomerNotFoundException; // Import custom exception
import com.example.customer.model.Customer; // Import Customer entity
import com.example.customer.repository.CustomerRepository; // Import Customer JPA repository
import com.example.customer.service.CustomerService; // Import Customer service interface
import jakarta.annotation.PostConstruct; // Annotation for post-initialization callback
import org.springframework.stereotype.Service; // Annotation marking class as Spring service component
import org.springframework.transaction.annotation.Transactional; // Annotation for transaction management

import java.util.List; // Import java.util.List
import java.util.stream.Collectors; // Import Stream collectors

/**
 * Service implementation for managing customer business operations.
 * Enforces rule: ONLY email and location can be updated.
 */
@Service // Registers class as a Spring Service component bean
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository; // Repository dependency for database operations

    /**
     * Constructor injection of CustomerRepository (Spring recommended best practice).
     */
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository; // Inject repository instance
    }

    /**
     * PostConstruct method populates sample seed data into H2 in-memory DB when application boots up.
     */
    @PostConstruct // Runs automatically after bean construction
    public void initDatabase() {
        if (customerRepository.count() == 0) { // Check if database table is empty
            customerRepository.save(new Customer("Alice Johnson", "alice@example.com", "New York")); // Seed 1
            customerRepository.save(new Customer("Bob Smith", "bob@example.com", "Chicago")); // Seed 2
            customerRepository.save(new Customer("Charlie Brown", "charlie@example.com", "San Francisco")); // Seed 3
            customerRepository.save(new Customer("Diana Prince", "diana@example.com", "Seattle")); // Seed 4
        }
    }

    /**
     * Selects all customers from database and converts entities to Response DTOs.
     */
    @Override
    @Transactional(readOnly = true) // Read-only transaction optimization
    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll() // Fetch all customer entity records from database
                .stream() // Convert list to Java Stream
                .map(this::convertToResponseDto) // Map each Customer entity to CustomerResponseDto
                .collect(Collectors.toList()); // Collect mapped DTOs into a List
    }

    /**
     * Finds single customer by ID or throws CustomerNotFoundException.
     */
    @Override
    @Transactional(readOnly = true) // Read-only transaction optimization
    public CustomerResponseDto getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId) // Query DB by ID
                .orElseThrow(() -> new CustomerNotFoundException(customerId)); // Throw 404 exception if not found
        return convertToResponseDto(customer); // Return converted DTO
    }

    /**
     * Updates customer email and location ONLY.
     * Rules Enforced: customerId and name are left untouched.
     */
    @Override
    @Transactional // Executes within write transaction boundary
    public CustomerResponseDto updateCustomer(Long customerId, UpdateCustomerRequestDto updateDto) {
        // Step 1: Look up existing customer by ID or throw exception if not found
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        // Step 2: Strict Enforcement - ONLY email and location can be updated!
        // customerId and name setters are NEVER called!
        customer.setEmail(updateDto.getEmail()); // Update email address field
        customer.setLocation(updateDto.getLocation()); // Update location field

        // Step 3: Save updated entity back to database
        Customer updatedCustomer = customerRepository.save(customer);

        // Step 4: Convert and return updated DTO
        return convertToResponseDto(updatedCustomer);
    }

    /**
     * Helper mapping method converting Customer Entity to CustomerResponseDto.
     */
    private CustomerResponseDto convertToResponseDto(Customer customer) {
        return new CustomerResponseDto(
                customer.getCustomerId(), // Copy customer ID
                customer.getName(), // Copy customer name
                customer.getEmail(), // Copy customer email
                customer.getLocation() // Copy customer location
        );
    }
}
