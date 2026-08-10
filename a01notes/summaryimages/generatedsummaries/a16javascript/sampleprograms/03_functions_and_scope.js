/**
 * ============================================================================
 * MODULE 3: JAVASCRIPT FUNCTIONS AND SCOPE
 * ============================================================================
 * Topics Covered:
 * - Function Declaration vs Function Expression (Anonymous & Named)
 * - Scope (Global vs Local Function Scope)
 * - PDF Hands-on Exercises:
 *   1. Fibonacci Series for 10 terms (Financial compounding model)
 *   2. Multiplication Table of 8 (Interest rate tier calculator)
 * 
 * Financial Domain Context:
 * - Calculating Loan Compound Interest, Investment Growth Projection, Tax Brackets.
 * 
 * React Connection:
 * - Modern React apps are composed entirely of Functional Components!
 * - Functions are passed as props (Callbacks) between parent and child components.
 * - Understanding closure scope is essential for React Hooks (`useState`, `useEffect`).
 * 
 * Industry Best Practices:
 * - Keep functions PURE: Same inputs should always yield the exact same outputs without side-effects.
 * - Use descriptive function names and guard clauses for early returns.
 * ============================================================================
 */

'use strict';

console.log("\n=== Module 3: JavaScript Functions and Scoping ===");

// ----------------------------------------------------------------------------
// 1. Function Declaration (Hoisted, traditional syntax)
// ----------------------------------------------------------------------------
// Simple financial calculator for Monthly EMI (Equated Monthly Installment)
function calculateSimpleInterest(principal, rate, timeInYears) {
    // Guard clauses for safety
    if (principal <= 0 || rate <= 0 || timeInYears <= 0) {
        return 0;
    }
    const interest = (principal * rate * timeInYears) / 100;
    return interest;
}

const interestEarned = calculateSimpleInterest(100000, 6.5, 3);
console.log(`1. Function Declaration - Simple Interest on $100,000 at 6.5% for 3 yrs: $${interestEarned}`);

// ----------------------------------------------------------------------------
// 2. Function Expressions (Anonymous & Named)
// ----------------------------------------------------------------------------

// Anonymous Function Expression (Stored in variable)
const calculateTax = function (income) {
    if (income <= 250000) return 0;
    if (income <= 500000) return (income - 250000) * 0.05;
    if (income <= 1000000) return 12500 + (income - 500000) * 0.20;
    return 112500 + (income - 1000000) * 0.30;
};

console.log(`2a. Anonymous Function Expression - Income Tax on $750,000: $${calculateTax(750000)}`);

// Named Function Expression (Useful for recursion and stack trace debugging)
const computeFactorialDiscount = function fact(n) {
    if (n <= 1) return 1;
    return n * fact(n - 1);
};
console.log(`2b. Named Function Expression - Factorial Tier (5): ${computeFactorialDiscount(5)}`);

// ----------------------------------------------------------------------------
// 3. Global vs Local Scope Demonstration
// ----------------------------------------------------------------------------
const globalBankCurrency = "USD"; // Global variable - available everywhere

function processAccountTransaction() {
    const localFee = 15.00; // Local variable - scoped only inside this function
    console.log(`\nInside Function: Currency = ${globalBankCurrency}, Transaction Fee = $${localFee}`);
}

processAccountTransaction();
console.log(`Outside Function: Currency = ${globalBankCurrency}`);
// console.log(localFee); // Uncaught ReferenceError: localFee is not defined

// ----------------------------------------------------------------------------
// 4. PDF HANDS-ON EXERCISES
// ----------------------------------------------------------------------------

console.log("\n--- PDF Hands-on 1: Fibonacci Series for 10 numbers ---");
/**
 * Hands-on 1: Create a function which displays the Fibonacci series for 10 numbers:
 * 0, 1, 1, 2, 3, 5, 8, 13, 21, 34
 * Financial Analogy: Used in technical analysis of financial markets (Fibonacci Retracement).
 */
function displayFibonacci(count = 10) {
    const fibSeries = [];
    let n1 = 0, n2 = 1, nextTerm;

    for (let i = 1; i <= count; i++) {
        fibSeries.push(n1);
        nextTerm = n1 + n2;
        n1 = n2;
        n2 = nextTerm;
    }
    console.log(`Fibonacci Series (${count} terms):`, fibSeries.join(", "));
    return fibSeries;
}

displayFibonacci(10);

console.log("\n--- PDF Hands-on 2: Display the Table of 8 ---");
/**
 * Hands-on 2: Create a function which displays the table of 8.
 * Financial Analogy: Fixed interest multiplier matrix for 8% APR investments over 1 to 10 periods.
 */
function displayTableOfEight() {
    console.log("Multiplication Table of 8 (8% Interest Multiplier Grid):");
    const table = [];
    for (let i = 1; i <= 10; i++) {
        const result = 8 * i;
        table.push(`8 x ${i} = ${result}`);
        console.log(`  8 x ${i} = ${result}`);
    }
    return table;
}

displayTableOfEight();

/**
 * ============================================================================
 * REACT IMPORTANCE & BEST PRACTICES
 * ============================================================================
 * 1. React Connection:
 *    - In React, components are functions: `function AccountCard(props) { return <div>{props.balance}</div>; }`
 *    - Event handlers in React components are passed as callback functions:
 *      `<button onClick={handleTransfer}>Transfer Funds</button>`
 * 
 * 2. Industry Best Practices:
 *    - Purity: React expects functional components and helper utilities to be pure functions.
 *    - Declarative parameters: Use default parameter values (e.g. count = 10) to avoid undefined errors.
 * ============================================================================
 */
