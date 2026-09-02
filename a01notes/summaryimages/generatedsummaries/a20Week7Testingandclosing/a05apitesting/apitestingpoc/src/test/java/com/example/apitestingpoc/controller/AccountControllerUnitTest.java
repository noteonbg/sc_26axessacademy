package com.example.apitestingpoc.controller;

import com.example.apitestingpoc.exception.AccountNotFoundException;
import com.example.apitestingpoc.model.Account;
import com.example.apitestingpoc.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller Unit Test using @WebMvcTest and MockMvc.
 * 
 * SYNTAX & ANNOTATION EXPLANATIONS FOR API TESTING:
 * --------------------------------------------------
 * 1. @WebMvcTest(AccountController.class):
 *    - Focuses ONLY on Spring MVC components (AccountController, GlobalExceptionHandler).
 *    - Does NOT load full Spring context or database configurations.
 *    - Tests execute in milliseconds without launching a real HTTP server.
 * 
 * 2. @MockBean:
 *    - Creates a Mockito mock instance of AccountService and places it into the test application context.
 *    - Replaces the real service, allowing us to stub behavior using Mockito.when(...).
 * 
 * 3. MockMvc:
 *    - Main entry point for server-side Spring MVC testing.
 *    - Allows sending simulated HTTP requests (GET, POST, PUT, DELETE) and asserting HTTP responses.
 * 
 * 4. jsonPath("$.field"):
 *    - Uses JsonPath expression language to query fields in the returned JSON response body.
 *    - Examples: jsonPath("$.id") queries top-level id, jsonPath("$[0].name") queries first item's name.
 */
@WebMvcTest(AccountController.class)
public class AccountControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper; // Jackson utility to convert Java Objects <-> JSON string

    // =========================================================================
    // 1. GET /api/v1/accounts - TEST CASES
    // =========================================================================

    @Test
    @DisplayName("GET /api/v1/accounts - Should Return 200 OK and List of Accounts")
    public void getAllAccounts_ShouldReturnAccountList() throws Exception {
        // Arrange (Given)
        Account acc1 = new Account(101L, "ACC1001", "Alice Smith", new BigDecimal("2500.50"), "SAVINGS");
        Account acc2 = new Account(102L, "ACC1002", "Bob Jones", new BigDecimal("1200.00"), "CHECKING");
        Mockito.when(accountService.getAllAccounts()).thenReturn(List.of(acc1, acc2));

        // Act & Assert (When & Then)
        mockMvc.perform(get("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert HTTP Status 200 OK
                .andExpect(status().isOk())
                // Assert Content-Type is JSON
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Assert JSON array length is 2
                .andExpect(jsonPath("$", hasSize(2)))
                // Assert first element values
                .andExpect(jsonPath("$[0].id", is(101)))
                .andExpect(jsonPath("$[0].accountNumber", is("ACC1001")))
                .andExpect(jsonPath("$[0].accountHolderName", is("Alice Smith")))
                // Assert second element values
                .andExpect(jsonPath("$[1].accountNumber", is("ACC1002")));
    }

    // =========================================================================
    // 2. GET /api/v1/accounts/{id} - TEST CASES
    // =========================================================================

    @Test
    @DisplayName("GET /api/v1/accounts/{id} - Should Return 200 OK when Account Exists")
    public void getAccountById_WhenExists_ShouldReturnAccount() throws Exception {
        // Arrange
        Account acc = new Account(101L, "ACC1001", "Alice Smith", new BigDecimal("2500.50"), "SAVINGS");
        Mockito.when(accountService.getAccountById(101L)).thenReturn(acc);

        // Act & Assert
        mockMvc.perform(get("/api/v1/accounts/101")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(101)))
                .andExpect(jsonPath("$.accountNumber", is("ACC1001")))
                .andExpect(jsonPath("$.accountHolderName", is("Alice Smith")))
                .andExpect(jsonPath("$.balance", is(2500.50)))
                .andExpect(jsonPath("$.accountType", is("SAVINGS")));
    }

    @Test
    @DisplayName("GET /api/v1/accounts/{id} - Should Return 404 NOT FOUND when Account Does Not Exist")
    public void getAccountById_WhenNotFound_ShouldReturn404() throws Exception {
        // Arrange: Service throws AccountNotFoundException
        Mockito.when(accountService.getAccountById(999L))
               .thenThrow(new AccountNotFoundException("Account not found with ID: 999"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/accounts/999"))
                // Assert GlobalExceptionHandler catches exception and returns HTTP 404
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Resource Not Found")))
                .andExpect(jsonPath("$.message", is("Account not found with ID: 999")));
    }

    // =========================================================================
    // 3. POST /api/v1/accounts - TEST CASES
    // =========================================================================

    @Test
    @DisplayName("POST /api/v1/accounts - Should Return 201 CREATED when Valid Account is Posted")
    public void createAccount_WhenValid_ShouldReturn201Created() throws Exception {
        // Arrange: Input object (without ID) and saved object (with generated ID 103)
        Account newAccountInput = new Account(null, "ACC1003", "Charlie Brown", new BigDecimal("5000.00"), "SAVINGS");
        Account savedAccount = new Account(103L, "ACC1003", "Charlie Brown", new BigDecimal("5000.00"), "SAVINGS");

        Mockito.when(accountService.createAccount(any(Account.class))).thenReturn(savedAccount);

        // Convert Java object to JSON String
        String jsonPayload = objectMapper.writeValueAsString(newAccountInput);

        // Act & Assert
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                // Assert HTTP Status 201 Created
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(103)))
                .andExpect(jsonPath("$.accountNumber", is("ACC1003")))
                .andExpect(jsonPath("$.accountHolderName", is("Charlie Brown")));
    }

    @Test
    @DisplayName("POST /api/v1/accounts - Should Return 400 BAD REQUEST when Validation Fails")
    public void createAccount_WhenInvalid_ShouldReturn400BadRequest() throws Exception {
        // Arrange: Service throws IllegalArgumentException for negative balance
        Mockito.when(accountService.createAccount(any(Account.class)))
               .thenThrow(new IllegalArgumentException("Initial balance cannot be negative"));

        Account invalidAccount = new Account(null, "ACC999", "Invalid User", new BigDecimal("-100.00"), "SAVINGS");

        // Act & Assert
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAccount)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Bad Request - Validation Error")))
                .andExpect(jsonPath("$.message", is("Initial balance cannot be negative")));
    }

    // =========================================================================
    // 4. PUT /api/v1/accounts/{id} - TEST CASES
    // =========================================================================

    @Test
    @DisplayName("PUT /api/v1/accounts/{id} - Should Return 200 OK when Updating Existing Account")
    public void updateAccount_WhenExists_ShouldReturnUpdatedAccount() throws Exception {
        // Arrange
        Account updateInput = new Account(101L, "ACC1001", "Alice Smith Updated", new BigDecimal("3000.00"), "SAVINGS");
        Mockito.when(accountService.updateAccount(eq(101L), any(Account.class))).thenReturn(updateInput);

        // Act & Assert
        mockMvc.perform(put("/api/v1/accounts/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountHolderName", is("Alice Smith Updated")))
                .andExpect(jsonPath("$.balance", is(3000.00)));
    }

    // =========================================================================
    // 5. DELETE /api/v1/accounts/{id} - TEST CASES
    // =========================================================================

    @Test
    @DisplayName("DELETE /api/v1/accounts/{id} - Should Return 204 NO CONTENT on Successful Deletion")
    public void deleteAccount_WhenExists_ShouldReturn204NoContent() throws Exception {
        // Arrange: Void method does nothing on success
        Mockito.doNothing().when(accountService).deleteAccount(101L);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/accounts/101"))
                // Assert HTTP Status 204 No Content
                .andExpect(status().isNoContent());
    }
}
