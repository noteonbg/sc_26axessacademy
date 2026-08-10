/**
 * ============================================================================
 * MODULE 1: JAVASCRIPT FUNDAMENTALS
 * ============================================================================
 * Topics Covered:
 * - What is JavaScript (cross-platform, object-oriented, dynamic & loose typing)
 * - Client-side vs Server-side scripting in Financial Applications
 * - Script placement (Inline vs External)
 * 
 * Financial Domain Context:
 * - Bank transaction validation before sending data to core banking APIs.
 * - Dynamic currency calculation and display on retail banking UI.
 * 
 * React Connection:
 * - React builds on client-side JS fundamentals. Understanding loose typing & dynamic evaluation
 *   is key to preventing runtime bugs in JSX templates and state updates.
 * 
 * Industry Best Practices:
 * - Always use strict mode ('use strict') to prevent undeclared variables.
 * - Perform client-side validation for instant UX feedback, but ALWAYS validate again on the server.
 * ============================================================================
 */

'use strict';

console.log("=== Module 1: JavaScript Fundamentals ===");

// 1. Dynamic & Loose Typing Demonstration
// In JavaScript, variables can hold values of any data type without explicit declaration.
let accountBalance = 50000; // initially a number (Currency in USD/INR)
console.log(`Initial Balance type: ${typeof accountBalance}, Value: ${accountBalance}`);

accountBalance = "$50,000.00"; // dynamically changed to string for UI formatting
console.log(`Formatted Balance type: ${typeof accountBalance}, Value: ${accountBalance}`);

// 2. Client-side Financial Validation Example
// Client-side scripting provides instant feedback to users (e.g., ATM withdrawal check)
function validateWithdrawal(availableBalance, requestedAmount) {
    console.log(`\nProcessing withdrawal request of $${requestedAmount}...`);
    
    if (typeof requestedAmount !== 'number' || isNaN(requestedAmount)) {
        return { success: false, message: "Invalid amount entered. Please enter a valid number." };
    }
    
    if (requestedAmount <= 0) {
        return { success: false, message: "Withdrawal amount must be greater than zero." };
    }
    
    if (requestedAmount > availableBalance) {
        return { success: false, message: "Insufficient funds in your account." };
    }
    
    const remainingBalance = availableBalance - requestedAmount;
    return { 
        success: true, 
        message: "Transaction Approved. Please collect your cash.", 
        newBalance: remainingBalance 
    };
}

const currentBalance = 2500.50;
console.log(validateWithdrawal(currentBalance, 500));  // Valid
console.log(validateWithdrawal(currentBalance, 3000)); // Overdraft attempt
console.log(validateWithdrawal(currentBalance, "abc")); // Invalid input

/**
 * ============================================================================
 * REACT IMPORTANCE & INDUSTRY BEST PRACTICES
 * ============================================================================
 * 1. React Relevance:
 *    - In React, component state (e.g., const [balance, setBalance] = useState(2500.50))
 *      relies on JavaScript's dynamic typing. However, loose typing can cause bugs like
 *      concatenating numbers as strings ("2500" + 500 = "2500500").
 * 
 * 2. Industry Best Practices:
 *    - Client vs Server: Client-side validation improves speed, but SERVER-SIDE validation
 *      is MANDATORY for financial transactions to prevent tamper attempts.
 *    - Script Placement: External JS files (`<script src="app.js"></script>`) promote modularity
 *      and enable browser caching for faster web app loading.
 * ============================================================================
 */
