package com.standardchartered.jpademo.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for fund transfer requests.
 */
public record TransferRequestDto(
    Long sourceAccountId,
    Long targetAccountId,
    BigDecimal amount
) {}
