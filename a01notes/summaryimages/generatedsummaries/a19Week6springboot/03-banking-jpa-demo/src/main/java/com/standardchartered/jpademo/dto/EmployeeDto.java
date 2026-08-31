package com.standardchartered.jpademo.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for Employee API requests and responses.
 * Decouples API consumers from the JPA Employee entity.
 */
public record EmployeeDto(
    Long id,
    String name,
    String email,
    String department,
    BigDecimal salary,
    String designation
) {}
