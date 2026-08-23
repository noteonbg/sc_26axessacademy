package com.standardchartered.banking.controller;

import com.standardchartered.banking.entity.CustomerEntity;
import com.standardchartered.banking.service.BankCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private BankCustomerService customerService;

    // GET /api/v1/customers -> List all bank customers
    @GetMapping
    public List<CustomerEntity> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // GET /api/v1/customers/paged?page=0&size=5&sortBy=firstName -> Database Pagination & Sorting
    @GetMapping("/paged")
    public Page<CustomerEntity> getCustomersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return customerService.getCustomersPaginated(page, size, sortBy);
    }

    // GET /api/v1/customers/{id} -> Dynamic URL path variable lookup
    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomerById(@PathVariable("id") Long id) {
        CustomerEntity customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    // POST /api/v1/customers -> Create customer from JSON request body
    @PostMapping
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerEntity customer) {
        CustomerEntity created = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/v1/customers/{id} -> Update existing customer
    @PutMapping("/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerEntity customer) {
        CustomerEntity updated = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/v1/customers/{id} -> Delete customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
