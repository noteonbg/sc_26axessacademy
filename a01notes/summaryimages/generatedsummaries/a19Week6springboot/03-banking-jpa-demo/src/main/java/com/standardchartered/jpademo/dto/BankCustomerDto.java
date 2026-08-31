package com.standardchartered.jpademo.dto;

import java.util.List;

/**
 * =================================================================================
 * JAVA RECORD DTO: BANK CUSTOMER WITH NESTED DTO COLLECTION
 * =================================================================================
 * 
 * SYNTAX EXPLANATION:
 * - `List<BankAccountDto> accounts`: Demonstrates DTO nesting.
 * - By nesting `BankAccountDto` instead of `BankAccountJpaEntity`, we completely isolate
 *   the JSON serializer from JPA circular reference loops and LazyInitializationExceptions.
 * =================================================================================
 */
public record BankCustomerDto(
    
    // Customer primary key identifier
    Long id,
    
    // Customer given first name
    String firstName,
    
    // Customer family last name
    String lastName,
    
    // Customer unique email address
    String email,
    
    // Account status flag (e.g. "ACTIVE", "SUSPENDED")
    String status,
    
    // List of nested account DTO objects linked to this customer
    List<BankAccountDto> accounts
) {}
