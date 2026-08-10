/**
 * ============================================================================
 * PRACTICAL LAB 2: SECURE PASSWORD HASHING WITH RANDOM SALTS
 * ============================================================================
 * Concepts Demonstrated:
 * - Why Plaintext passwords must NEVER be stored in databases.
 * - Using Crypto PBKDF2 (Password-Based Key Derivation Function) with random salts.
 * - Defense against Rainbow Table pre-computed hash attacks.
 * ============================================================================
 */

'use strict';

const crypto = require('crypto');

console.log("=== Cybersecurity Lab 2: Secure Password Hashing ===");

// Secure Salted Password Hashing Utility
function hashPassword(plainTextPassword) {
    // Generate a cryptographically strong 16-byte random salt
    const salt = crypto.randomBytes(16).toString('hex');
    
    // Hash password + salt using PBKDF2 with 100,000 iterations
    const hash = crypto.pbkdf2Sync(plainTextPassword, salt, 100000, 64, 'sha512').toString('hex');
    
    return { salt, hash };
}

// Function to verify login password attempt against stored salt + hash
function verifyPassword(attemptPassword, storedSalt, storedHash) {
    const attemptHash = crypto.pbkdf2Sync(attemptPassword, storedSalt, 100000, 64, 'sha512').toString('hex');
    return attemptHash === storedHash;
}

// Demo Execution
const userPassword = "SuperSecureBankPassword2026!";
console.log(`Original Plaintext Password: "${userPassword}"`);

// Storing in database
const storedCredentials = hashPassword(userPassword);
console.log(`\nDatabase Stored Salt: ${storedCredentials.salt}`);
console.log(`Database Stored Hash: ${storedCredentials.hash}`);

// Testing Verification
console.log("\n--- Verification Testing ---");
const isValidCorrect = verifyPassword("SuperSecureBankPassword2026!", storedCredentials.salt, storedCredentials.hash);
console.log(`Correct Password Attempt Result: ${isValidCorrect ? "✓ ACCESS GRANTED" : "❌ DENIED"}`);

const isValidWrong = verifyPassword("WrongPassword123!", storedCredentials.salt, storedCredentials.hash);
console.log(`Incorrect Password Attempt Result: ${isValidWrong ? "✓ ACCESS GRANTED" : "❌ ACCESS DENIED"}`);
