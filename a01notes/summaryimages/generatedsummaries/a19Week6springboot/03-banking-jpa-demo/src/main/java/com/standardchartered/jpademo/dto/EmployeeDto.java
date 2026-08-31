package com.standardchartered.jpademo.dto;

import java.math.BigDecimal;

/**
 * =================================================================================
 * JAVA 21 RECORD / DATA TRANSFER OBJECT (DTO) SYNTAX OVERVIEW
 * =================================================================================
 * 
 * WHY USE JAVA RECORDS FOR DTOs:
 * - `record`: Introduced in Java 14+ / 17+ / 21 as an immutable data-carrier class.
 * - Auto-generates:
 *     1. private final instance fields for all components.
 *     2. Canonical constructor initializing all components.
 *     3. Getter methods matching component names (e.g. `id()`, `name()`).
 *     4. `equals()`, `hashCode()`, and `toString()` implementations.
 * - Prevents unintended side-effects because DTO objects are completely immutable once created.
 * - Spring Boot's Jackson ObjectMapper automatically serializes Java Records to JSON objects.
 * =================================================================================
 */
public record EmployeeDto(
    
    // Primary key identifier mapped to JSON field "id"
    Long id,
    
    // Full name of the employee mapped to JSON field "name"
    String name,
    
    // Unique email address mapped to JSON field "email"
    String email,
    
    // Department name mapped to JSON field "department"
    String department,
    
    // High-precision monetary salary amount mapped to JSON field "salary"
    BigDecimal salary,
    
    // Job title / designation mapped to JSON field "designation"
    String designation
) {}
