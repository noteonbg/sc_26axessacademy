package com.standardchartered.jpademo.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) representing Bank Account details.
 */
public record BankAccountDto(
    Long id,
    String accountNumber,
    String accountType,
    BigDecimal accountBalance
) {}
