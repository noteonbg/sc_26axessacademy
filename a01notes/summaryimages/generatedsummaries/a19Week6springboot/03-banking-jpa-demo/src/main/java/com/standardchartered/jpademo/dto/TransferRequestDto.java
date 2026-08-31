package com.standardchartered.jpademo.dto;

import java.math.BigDecimal;

/**
 * =================================================================================
 * JAVA RECORD DTO: FUND TRANSFER REQUEST PAYLOAD
 * =================================================================================
 * 
 * SYNTAX EXPLANATION:
 * - Encapsulates input fields sent in POST `/api/v1/jpa/customers/transfer` JSON body.
 * - Jackson automatically deserializes incoming JSON attributes (`sourceAccountId`, 
 *   `targetAccountId`, `amount`) into this immutable record instance.
 * =================================================================================
 */
public record TransferRequestDto(
    
    // ID of the debiting account
    Long sourceAccountId,
    
    // ID of the crediting account
    Long targetAccountId,
    
    // Monetary amount to transfer between accounts
    BigDecimal amount
) {}
