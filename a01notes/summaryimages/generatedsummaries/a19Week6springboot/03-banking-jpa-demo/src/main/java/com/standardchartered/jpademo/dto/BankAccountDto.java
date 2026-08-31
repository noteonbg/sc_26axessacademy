package com.standardchartered.jpademo.dto;

import java.math.BigDecimal;

/**
 * =================================================================================
 * JAVA RECORD DTO: BANK ACCOUNT PAYLOAD
 * =================================================================================
 * 
 * SYNTAX EXPLANATION:
 * - Java `record` provides concise syntax for immutable financial account payloads.
 * - `BigDecimal` is used for `accountBalance` to prevent floating-point binary rounding 
 *   errors common with `double`/`float` primitives.
 * =================================================================================
 */
public record BankAccountDto(
    
    // Unique account surrogate ID primary key
    Long id,
    
    // Unique public account number string (e.g., "ACC101")
    String accountNumber,
    
    // Account type category (e.g., "SAVINGS", "CHECKING", "CURRENT")
    String accountType,
    
    // Monetary balance using exact decimal precision
    BigDecimal accountBalance
) {}
