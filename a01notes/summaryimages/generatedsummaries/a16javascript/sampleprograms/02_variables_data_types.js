/**
 * ============================================================================
 * MODULE 2: JAVASCRIPT ESSENTIALS - VARIABLES, DATA TYPES & METHODS
 * ============================================================================
 * Topics Covered:
 * - Declarations: var, let, const
 * - Data Types: primitive (undefined, null, number, string, boolean) & reference (array, object)
 * - String Manipulation & String Methods (toLowerCase, toUpperCase, trim, slice, includes, replace)
 * - JavaScript Numbers, Properties (MAX_VALUE, NaN, EPSILON) & Number Methods (toFixed, toPrecision)
 * - Type Conversions (String to Number, Number to String)
 * 
 * Financial Domain Context:
 * - Formatting interest rates, transaction receipts, bank account numbers, currency calculations.
 * 
 * React Connection:
 * - Primitive values (numbers, strings) compare by value in React dependency arrays (`useEffect`).
 * - Reference values (objects, arrays) compare by reference. Mutating a reference type without
 *   creating a new reference will fail to trigger React re-renders!
 * 
 * Industry Best Practices:
 * - FLOATING POINT BUG IN FINANCE: `0.1 + 0.2 !== 0.3` due to IEEE 754 float binary representation.
 * - Always calculate financial balances in integer units (cents/paise) or use `toFixed()` / BigInt.
 * ============================================================================
 */

'use strict';

console.log("\n=== Module 2: JavaScript Essentials - Variables & Data Types ===");

// ----------------------------------------------------------------------------
// 1. Data Types & typeof Operator (Financial Profile Example)
// ----------------------------------------------------------------------------
let unassignedLimit;                  // undefined
const lastTransactionNote = null;      // null (object type in JS typeof legacy)
const interestRate = 7.5;              // number
const bankName = "Standard Chartered"; // string
const isKYCVerified = true;           // boolean
const accountTags = ["Premium", "NRI"];// array (object)
const customerInfo = { id: 101, name: "Anita Rao" }; // object

console.log("Data Types in Banking System:");
console.log("unassignedLimit:", typeof unassignedLimit, "->", unassignedLimit);
console.log("lastTransactionNote:", typeof lastTransactionNote, "->", lastTransactionNote);
console.log("interestRate:", typeof interestRate, "->", interestRate);
console.log("bankName:", typeof bankName, "->", bankName);
console.log("isKYCVerified:", typeof isKYCVerified, "->", isKYCVerified);
console.log("accountTags:", typeof accountTags, "->", accountTags);
console.log("customerInfo:", typeof customerInfo, "->", customerInfo);

// ----------------------------------------------------------------------------
// 2. Manipulating Strings & String Methods
// ----------------------------------------------------------------------------
console.log("\n--- String Methods in Financial UI ---");
const accountNumber = "  ACC9876543210IN  ";

// Trimming whitespace
const cleanAccNum = accountNumber.trim();
console.log(`Trimmed Acc Number: '${cleanAccNum}' (Length: ${cleanAccNum.length})`);

// Upper / Lower case
console.log(`Lowercase: ${cleanAccNum.toLowerCase()}`);
console.log(`Uppercase: ${cleanAccNum.toUpperCase()}`);

// Substring & Slice (Masking Account Number for Security Best Practice)
const maskedAcc = "XXXX-XXXX-" + cleanAccNum.slice(-4);
console.log(`Masked Account Number: ${maskedAcc}`);

// Searching & Checking (Includes, StartsWith, EndsWith)
console.log(`Is Indian Account? ${cleanAccNum.endsWith("IN")}`);
console.log(`Contains '987'? ${cleanAccNum.includes("987")}`);
console.log(`Index of 'ACC': ${cleanAccNum.indexOf("ACC")}`);

// String Replace
const bankNotice = "Welcome to SCB Bank. SCB provides personal loans.";
const updatedNotice = bankNotice.replace(/SCB/g, "Standard Chartered Bank");
console.log(`Updated Notice: ${updatedNotice}`);

// ----------------------------------------------------------------------------
// 3. JavaScript Numbers, Properties & Methods
// ----------------------------------------------------------------------------
console.log("\n--- Number Properties & Methods ---");
console.log(`Max JS Safe Value: ${Number.MAX_VALUE}`);
console.log(`Min JS Safe Value: ${Number.MIN_VALUE}`);
console.log(`NaN Check (0 / 0): ${isNaN(0 / 0)}`);

// Financial Formatting: toFixed() and toPrecision()
const rawInterest = 1245.6789;
console.log(`Raw Interest: ${rawInterest}`);
console.log(`Formatted Currency (2 decimals): $${rawInterest.toFixed(2)}`); // "1245.68"
console.log(`Precision (5 digits): ${rawInterest.toPrecision(5)}`);       // "1245.7"

// CRITICAL FINANCIAL WARNING: IEEE 754 Floating Point Rounding Issue
const floatSum = 0.1 + 0.2;
console.log(`\nFloating point calculation (0.1 + 0.2): ${floatSum}`);
console.log(`Is 0.1 + 0.2 === 0.3? ${floatSum === 0.3}`); // FALSE!

// Financial Best Practice Fix: Calculate in smallest currency unit (Cents/Paise)
const centsA = 10; // 0.10 USD
const centsB = 20; // 0.20 USD
const totalCents = centsA + centsB;
const totalUSD = (totalCents / 100).toFixed(2);
console.log(`Safe Financial Calculation Result: $${totalUSD}`);

// ----------------------------------------------------------------------------
// 4. Type Conversions (String to Number & Number to String)
// ----------------------------------------------------------------------------
console.log("\n--- Type Conversions ---");
const inputDepositStr = "15000.50";

// Converting String to Number
const numWay1 = Number(inputDepositStr);
const numWay2 = parseFloat(inputDepositStr);
const numWay3 = +inputDepositStr; // unary plus operator

console.log(`String '${inputDepositStr}' converted to Number: ${numWay1} (type: ${typeof numWay1})`);

// Converting Number to String
const accountId = 884920;
const strWay1 = String(accountId);
const strWay2 = accountId.toString();

console.log(`Number ${accountId} converted to String: '${strWay1}' (type: ${typeof strWay1})`);

/**
 * ============================================================================
 * REACT IMPORTANCE SUMMARY
 * ============================================================================
 * - React State Equality: React uses Object.is() for state comparisons.
 *   If you update state with the same primitive number or string, React skips re-renders.
 * - Form Inputs in React: Input values in HTML forms (`<input value={amount} />`) are ALWAYS
 *   strings. You MUST convert strings to Numbers before doing financial math in state handlers!
 * ============================================================================
 */
