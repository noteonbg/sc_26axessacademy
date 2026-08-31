package com.example.customer;

import com.example.customer.dto.CustomerResponseDto;
import com.example.customer.dto.UpdateCustomerRequestDto;
import com.example.customer.exception.CustomerNotFoundException;
import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CustomerServiceImpl verifying business logic and DTO conversions.
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer sampleCustomer;

    @BeforeEach
    void setUp() {
        sampleCustomer = new Customer(1L, "Alice Johnson", "alice@example.com", "New York");
    }

    @Test
    @DisplayName("getAllCustomers should return list of CustomerResponseDto")
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of(sampleCustomer));

        List<CustomerResponseDto> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Alice Johnson", result.get(0).getName());
        assertEquals("alice@example.com", result.get(0).getEmail());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getCustomerById should return CustomerResponseDto when found")
    void testGetCustomerByIdSuccess() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(sampleCustomer));

        CustomerResponseDto result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getCustomerId());
        assertEquals("Alice Johnson", result.getName());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getCustomerById should throw CustomerNotFoundException when ID does not exist")
    void testGetCustomerByIdNotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(99L));
        verify(customerRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("updateCustomer should update ONLY email and location, preserving name")
    void testUpdateCustomerSuccess() {
        UpdateCustomerRequestDto updateDto = new UpdateCustomerRequestDto("alice.new@example.com", "Boston");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(sampleCustomer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CustomerResponseDto result = customerService.updateCustomer(1L, updateDto);

        assertNotNull(result);
        assertEquals("Alice Johnson", result.getName()); // Name remains untouched
        assertEquals("alice.new@example.com", result.getEmail());
        assertEquals("Boston", result.getLocation());
        verify(customerRepository, times(1)).save(sampleCustomer);
    }
}
