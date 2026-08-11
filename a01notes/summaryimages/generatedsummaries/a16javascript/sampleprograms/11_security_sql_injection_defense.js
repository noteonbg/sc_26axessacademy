/**
 * ============================================================================
 * PRACTICAL LAB 1: PREVENTING SQL INJECTION (SQLi) IN FINANCIAL SYSTEMS
 * ============================================================================
 * Concepts Demonstrated:
 * - SQL Injection vulnerability using dynamic string concatenation.
 * - Parameterized Queries / Prepared Statements (Industry Defense Standard).
 * ============================================================================
 */

'use strict';

console.log("=== Cybersecurity Lab 1: Preventing SQL Injection ===");

// Simulated Database Query Engine
function mockExecuteDatabaseQuery(sqlQueryString, parameters = []) {
    console.log("\nExecuting DB Query String:");
    console.log(`  SQL: "${sqlQueryString}"`);
    if (parameters.length > 0) {
        console.log(`  Bound Parameters:`, parameters);
    }
}

// ----------------------------------------------------------------------------
// 1. Vulnerable Implementation (DO NOT USE IN PRODUCTION!)
// ----------------------------------------------------------------------------
function unsafeLoginHandler(userProvidedUsername, userProvidedPassword) {
    console.log("\n [UNSAFE IMPLEMENTATION] Processing Login Attempt...");
    
    // Attacker inputs malicious payload: "' OR '1'='1"
    const sql = `SELECT * FROM users WHERE username = '${userProvidedUsername}' AND password = '${userProvidedPassword}'`;
    
    mockExecuteDatabaseQuery(sql);
    console.log("-> VULNERABILITY: If username is \"' OR '1'='1\", SQL evaluates to true for all rows!");
}

// ----------------------------------------------------------------------------
// 2. Secure Implementation (Parameterized Prepared Statement)
// ----------------------------------------------------------------------------
function secureLoginHandler(userProvidedUsername, userProvidedPassword) {
    console.log("\n✅ [SECURE IMPLEMENTATION] Processing Login Attempt...");
    
    // SQL engine parses query structure BEFORE binding parameter values!
    const sql = `SELECT * FROM users WHERE username = ? AND password = ?`;
    
    mockExecuteDatabaseQuery(sql, [userProvidedUsername, userProvidedPassword]);
    console.log("-> PROTECTION: Input is safely treated purely as literal string data, neutralising payload!");
}

// Attacker Payload Test
const maliciousInput = "' OR '1'='1";
const dummyPassword = "password123";

unsafeLoginHandler(maliciousInput, dummyPassword);
secureLoginHandler(maliciousInput, dummyPassword);
