package com.example.customer;

import com.example.customer.controller.CustomerController;
import com.example.customer.dto.CustomerResponseDto;
import com.example.customer.dto.UpdateCustomerRequestDto;
import com.example.customer.exception.CustomerNotFoundException;
import com.example.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * WebMvcTest unit tests for CustomerController verifying REST endpoints, status codes, and JSON response structure.
 */
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/customers should return 200 OK with list of CustomerResponseDto")
    void testGetAllCustomers() throws Exception {
        CustomerResponseDto customer = new CustomerResponseDto(1L, "Alice Johnson", "alice@example.com", "New York");
        when(customerService.getAllCustomers()).thenReturn(List.of(customer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Customers retrieved successfully")))
                .andExpect(jsonPath("$.data[0].name", is("Alice Johnson")))
                .andExpect(jsonPath("$.data[0].email", is("alice@example.com")));
    }

    @Test
    @DisplayName("GET /api/customers/{id} should return 200 OK with single customer DTO")
    void testGetCustomerByIdSuccess() throws Exception {
        CustomerResponseDto customer = new CustomerResponseDto(1L, "Alice Johnson", "alice@example.com", "New York");
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.customerId", is(1)))
                .andExpect(jsonPath("$.data.name", is("Alice Johnson")));
    }

    @Test
    @DisplayName("GET /api/customers/{id} should return 404 Not Found when customer does not exist")
    void testGetCustomerByIdNotFound() throws Exception {
        when(customerService.getCustomerById(99L)).thenThrow(new CustomerNotFoundException(99L));

        mockMvc.perform(get("/api/customers/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")));
    }

    @Test
    @DisplayName("PUT /api/customers/{id} should update email and location and return 200 OK")
    void testUpdateCustomerSuccess() throws Exception {
        UpdateCustomerRequestDto requestDto = new UpdateCustomerRequestDto("alice.new@example.com", "Chicago");
        CustomerResponseDto responseDto = new CustomerResponseDto(1L, "Alice Johnson", "alice.new@example.com", "Chicago");

        when(customerService.updateCustomer(eq(1L), any(UpdateCustomerRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.email", is("alice.new@example.com")))
                .andExpect(jsonPath("$.data.location", is("Chicago")));
    }
}
