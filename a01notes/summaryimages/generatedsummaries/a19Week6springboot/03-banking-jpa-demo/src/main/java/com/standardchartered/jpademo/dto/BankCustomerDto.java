package com.standardchartered.jpademo.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing Customer details with nested accounts.
 */
public record BankCustomerDto(
    Long id,
    String firstName,
    String lastName,
    String email,
    String status,
    List<BankAccountDto> accounts
) {}
