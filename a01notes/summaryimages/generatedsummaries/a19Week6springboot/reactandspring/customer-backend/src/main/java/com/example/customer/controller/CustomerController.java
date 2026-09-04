package com.example.customer.controller; // Declares REST controller package namespace

import com.example.customer.dto.ApiResponse; // Import generic ApiResponse wrapper DTO
import com.example.customer.dto.CustomerResponseDto; // Import Customer response DTO
import com.example.customer.dto.UpdateCustomerRequestDto; // Import Customer update request DTO
import com.example.customer.service.CustomerService; // Import Customer service interface
import jakarta.validation.Valid; // Annotation triggering automatic Bean Validation on DTO
import org.springframework.http.HttpStatus; // Enum representing HTTP Status codes
import org.springframework.http.ResponseEntity; // Class wrapping HTTP response body and status code
import org.springframework.web.bind.annotation.CrossOrigin; // Annotation enabling CORS for frontend communication
import org.springframework.web.bind.annotation.GetMapping; // Annotation mapping HTTP GET requests
import org.springframework.web.bind.annotation.PathVariable; // Annotation binding URL path variables
import org.springframework.web.bind.annotation.PutMapping; // Annotation mapping HTTP PUT requests
import org.springframework.web.bind.annotation.RequestBody; // Annotation binding HTTP JSON request body to DTO
import org.springframework.web.bind.annotation.RequestMapping; // Annotation defining base URL path mapping
import org.springframework.web.bind.annotation.RestController; // Annotation marking class as RESTful controller

import java.util.List; // Import java.util.List collection interface

/**
 * REST Controller exposing Customer management API endpoints.
 * Requirements Met:
 * 1. Return type uses explicit ResponseEntity<T> (not raw data).
 * 2. Explicit HTTP status codes are returned (HttpStatus.OK).
 * 3. CORS enabled for React frontend at http://localhost:4800.
 */
@RestController // Marks class as REST controller; automatically serializes return objects into JSON
@RequestMapping("/api/customers") // Base route URL path for all customer endpoints
@CrossOrigin(origins = "*") // Allows cross-origin HTTP requests from React frontend application
public class CustomerController {

    private final CustomerService customerService; // Service dependency for business logic

    /**
     * Constructor injecting CustomerService dependency.
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService; // Assign injected service instance
    }

    /**
     * Select / Fetch All Customers endpoint.
     * HTTP GET /api/customers
     * @return ResponseEntity<ApiResponse<List<CustomerResponseDto>>> with HTTP 200 OK status.
     */
    @GetMapping // Maps HTTP GET requests to /api/customers
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> getAllCustomers() {
        List<CustomerResponseDto> customers = customerService.getAllCustomers(); // Call service to retrieve customers list
        
        // Wrap customer list into standardized ApiResponse object
        ApiResponse<List<CustomerResponseDto>> response = new ApiResponse<>(
                true, // Success flag = true
                "Customers retrieved successfully", // Success message string
                customers // Customer list payload
        );
        
        // Return ResponseEntity wrapping ApiResponse payload with explicit HTTP 200 OK status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Fetch Single Customer by ID endpoint.
     * HTTP GET /api/customers/{id}
     * @param id Customer primary key path variable.
     * @return ResponseEntity<ApiResponse<CustomerResponseDto>> with HTTP 200 OK status.
     */
    @GetMapping("/{id}") // Maps HTTP GET requests to /api/customers/{id}
    public ResponseEntity<ApiResponse<CustomerResponseDto>> getCustomerById(@PathVariable("id") Long id) {
        CustomerResponseDto customer = customerService.getCustomerById(id); // Call service to retrieve customer by ID
        
        // Wrap customer object into standardized ApiResponse object
        ApiResponse<CustomerResponseDto> response = new ApiResponse<>(
                true, // Success flag = true
                "Customer retrieved successfully", // Message string
                customer // Customer payload
        );
        
        // Return ResponseEntity wrapping payload with explicit HTTP 200 OK status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update Customer Email and Location ONLY endpoint.
     * HTTP PUT /api/customers/{id}
     * @param id Customer ID from URL path.
     * @param updateDto Validated request body containing ONLY email and location.
     * @return ResponseEntity<ApiResponse<CustomerResponseDto>> with HTTP 200 OK status.
     */
    @PutMapping("/{id}") // Maps HTTP PUT requests to /api/customers/{id}
    public ResponseEntity<ApiResponse<CustomerResponseDto>> updateCustomer(
            @PathVariable("id") Long id, // Extracts {id} from URL path
            @Valid @RequestBody UpdateCustomerRequestDto updateDto) { // Binds JSON body to updateDto & triggers @Valid validation

        CustomerResponseDto updatedCustomer = customerService.updateCustomer(id, updateDto); // Call service to update customer
        
        // Wrap updated customer into standardized ApiResponse object
        ApiResponse<CustomerResponseDto> response = new ApiResponse<>(
                true, // Success flag = true
                "Customer email and location updated successfully", // Message string
                updatedCustomer // Updated customer payload
        );
        
        // Return ResponseEntity wrapping payload with explicit HTTP 200 OK status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
