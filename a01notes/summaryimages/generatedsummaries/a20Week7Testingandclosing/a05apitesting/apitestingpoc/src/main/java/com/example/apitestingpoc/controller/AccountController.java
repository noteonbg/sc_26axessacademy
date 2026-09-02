package com.example.apitestingpoc.controller;

import com.example.apitestingpoc.model.Account;
import com.example.apitestingpoc.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Bank Account Management.
 * 
 * SYNTAX EXPLANATIONS:
 * --------------------+
 * 1. @RestController: Combines @Controller and @ResponseBody. Tells Spring that every method
 *    returns domain objects directly serialized into HTTP Response Body (JSON by default).
 * 
 * 2. @RequestMapping("/api/v1/accounts"): Sets the base URI path for all endpoints in this controller.
 * 
 * 3. ResponseEntity<T>: A wrapper representing the entire HTTP response (Status Code, Headers, and Body).
 */
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    // Constructor Dependency Injection (Best practice in Spring)
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * GET /api/v1/accounts
     * Retrieves all accounts.
     * Returns: HTTP 200 OK with JSON array of Account objects.
     */
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts); // Returns HTTP 200 OK with body
    }

    /**
     * GET /api/v1/accounts/{id}
     * Retrieves a specific account by ID.
     * 
     * SYNTAX: @PathVariable("id") extracts the variable {id} from the URI path.
     * Returns: HTTP 200 OK with Account JSON, or 404 via GlobalExceptionHandler if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    /**
     * POST /api/v1/accounts
     * Creates a new bank account.
     * 
     * SYNTAX: @RequestBody binds the incoming HTTP request JSON body to the Account Java object.
     * Returns: HTTP 201 CREATED with the created Account object (including generated ID).
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount); // Returns HTTP 201 Created
    }

    /**
     * PUT /api/v1/accounts/{id}
     * Replaces/Updates an existing account.
     * 
     * Returns: HTTP 200 OK with updated Account JSON.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
        Account updatedAccount = accountService.updateAccount(id, accountDetails);
        return ResponseEntity.ok(updatedAccount);
    }

    /**
     * DELETE /api/v1/accounts/{id}
     * Deletes an account by ID.
     * 
     * Returns: HTTP 204 NO CONTENT (standard for successful deletion with no body returned).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build(); // Returns HTTP 204 No Content
    }
}
