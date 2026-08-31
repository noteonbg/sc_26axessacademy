package com.standardchartered.jpademo.controller;

import com.standardchartered.jpademo.dto.BankCustomerDto;
import com.standardchartered.jpademo.dto.TransferRequestDto;
import com.standardchartered.jpademo.service.BankingJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * =================================================================================
 * BANKING CUSTOMER REST CONTROLLER SYNTAX & ANNOTATION GUIDE
 * =================================================================================
 * 
 * SYNTAX EXPLANATIONS:
 * - `@RequestMapping("/api/v1/jpa/customers")`: Defines base route for banking customer endpoints.
 * - Endpoints exclusively return `BankCustomerDto` and `Page<BankCustomerDto>` to avoid 
 *   exposing internal database entity state or triggering LazyInitializationExceptions.
 * =================================================================================
 */
@RestController
@RequestMapping("/api/v1/jpa/customers")
public class JpaDemoController {

    @Autowired
    private BankingJpaService jpaService;

    /**
     * GET ALL CUSTOMERS
     * SYNTAX: `@GetMapping` maps GET `/api/v1/jpa/customers`
     * RETURNS: List of BankCustomerDto objects (including nested BankAccountDto lists).
     */
    @GetMapping
    public ResponseEntity<List<BankCustomerDto>> getAll() {
        return ResponseEntity.ok(jpaService.getAll());
    }

    /**
     * GET PAGINATED & SORTED CUSTOMERS
     * SYNTAX:
     * - `@RequestParam(defaultValue = "0")`: Extracts HTTP Query Parameter `?page=0`.
     * - `Page<BankCustomerDto>`: Spring Data pagination container holding page elements, 
     *   total elements count, total pages, page size, and page number.
     */
    @GetMapping("/paged")
    public ResponseEntity<Page<BankCustomerDto>> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok(jpaService.getPaginatedCustomers(page, size, sortBy));
    }

    /**
     * UPDATE CUSTOMER EMAIL
     * SYNTAX: `@PutMapping("/{id}/email")` maps PUT requests updating a specific field.
     */
    @PutMapping("/{id}/email")
    public ResponseEntity<BankCustomerDto> updateEmail(@PathVariable("id") Long id, @RequestParam("email") String email) {
        return ResponseEntity.ok(jpaService.updateCustomerEmail(id, email));
    }

    /**
     * TRANSACTIONAL FUND TRANSFER
     * SYNTAX:
     * - `@PostMapping("/transfer")`: Maps POST request containing transfer instructions.
     * - `@RequestBody TransferRequestDto request`: Jackson binds incoming JSON body to record component getters 
     *   (`request.sourceAccountId()`, `request.targetAccountId()`, `request.amount()`).
     */
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDto request) {
        jpaService.transferFunds(request.sourceAccountId(), request.targetAccountId(), request.amount());
        return ResponseEntity.ok("Successfully transferred $" + request.amount());
    }
}
