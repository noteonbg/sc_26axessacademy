package com.example.apitestingpoc.integration;

import com.example.apitestingpoc.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * End-to-End API Integration Test using @SpringBootTest.
 * 
 * SYNTAX & ANNOTATION EXPLANATIONS FOR INTEGRATION TESTING:
 * --------------------------------------------------------
 * 1. @SpringBootTest:
 *    - Loads the ENTIRE Spring Boot Application Context (all beans, services, controllers).
 *    - Unlike @WebMvcTest, NO mocks are used here for AccountService. We test the real
 *      flow from HTTP endpoint -> Controller -> Real In-Memory AccountService -> Response.
 * 
 * 2. @AutoConfigureMockMvc:
 *    - Automatically sets up and configures the MockMvc instance within the full Spring context.
 * 
 * 3. End-to-End Flow Verification:
 *    - Tests CRUD lifecycle: Create an account -> Retrieve it -> Update it -> Delete it.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Integration Test: Complete Account CRUD Lifecycle Flow")
    public void testFullAccountLifecycle_EndToEnd() throws Exception {
        // Step 1: GET initial pre-populated accounts
        mockMvc.perform(get("/api/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(2)));

        // Step 2: POST - Create a new account
        Account newAcc = new Account(null, "ACC2001", "Integration User", new BigDecimal("7500.00"), "SAVINGS");
        
        String responseContent = mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAcc)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber", is("ACC2001")))
                .andExpect(jsonPath("$.accountHolderName", is("Integration User")))
                .andReturn().getResponse().getContentAsString();

        // Extract generated ID from JSON response
        Account createdAccount = objectMapper.readValue(responseContent, Account.class);
        Long createdId = createdAccount.getId();

        // Step 3: GET by ID - Verify account was stored in-memory
        mockMvc.perform(get("/api/v1/accounts/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdId.intValue())))
                .andExpect(jsonPath("$.accountHolderName", is("Integration User")));

        // Step 4: PUT - Update the created account balance
        createdAccount.setBalance(new BigDecimal("9999.99"));
        mockMvc.perform(put("/api/v1/accounts/" + createdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdAccount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", is(9999.99)));

        // Step 5: DELETE - Remove the account
        mockMvc.perform(delete("/api/v1/accounts/" + createdId))
                .andExpect(status().isNoContent());

        // Step 6: GET by ID - Verify account returns 404 NOT FOUND after deletion
        mockMvc.perform(get("/api/v1/accounts/" + createdId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Account not found with ID: " + createdId)));
    }
}
