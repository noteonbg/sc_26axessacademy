/**
 * ============================================================================
 * MODULE 4: JAVASCRIPT MULTIPLE VALUES - ARRAYS, STRINGS & OBJECTS
 * ============================================================================
 * Topics Covered:
 * - Arrays (Homogenous & Heterogenous) & Array Iteration
 * - Array Mutating Methods: push, pop, shift, unshift, reverse, sort
 * - Array Iteration & Modern Methods: forEach, find, filter, map, reduce
 * - PDF Hands-on: Convert ["sTandarD", "CharTered", "banK"] to Uppercase
 * - Objects: Literal declaration, properties, methods, 'this' keyword
 * 
 * Financial Domain Context:
 * - Bank statement itemization, filtering ledger transactions, portfolio sum, customer profile object.
 * 
 * React Connection:
 * - **STATE IMMUTABILITY**: React state updates MUST NOT mutate original arrays/objects directly.
 *   Mutating methods like push/sort won't trigger re-renders. Non-mutating methods like `.map()`
 *   and `.filter()` produce new array references, causing React to render UI changes.
 * - **JSX LIST RENDERING**: React renders arrays of elements using `.map()`, requiring unique `key` props.
 * ============================================================================
 */

'use strict';

console.log("\n=== Module 4: Multiple Values - Arrays, Strings & Objects ===");

// ----------------------------------------------------------------------------
// 1. Array Fundamentals (Heterogenous Banking Array)
// ----------------------------------------------------------------------------
const customerAccount = ["John Doe", "ACC9988", 45000.75, true]; // Heterogenous elements
console.log("Account Details Array:", customerAccount);
console.log(`Account Name: ${customerAccount[0]}, Balance: $${customerAccount[2]}`);

// ----------------------------------------------------------------------------
// 2. Mutating Array Methods vs Non-Mutating Methods
// ----------------------------------------------------------------------------
console.log("\n--- Array Mutating Methods ---");
const transactionQueue = [100, 250, 50];

// Adding & removing elements
transactionQueue.push(500);   // add to end
console.log("After push(500):", transactionQueue);

transactionQueue.unshift(20); // add to start
console.log("After unshift(20):", transactionQueue);

const lastTx = transactionQueue.pop(); // remove from end
console.log(`Popped last tx: $${lastTx}, Queue:`, transactionQueue);

const firstTx = transactionQueue.shift(); // remove from start
console.log(`Shifted first tx: $${firstTx}, Queue:`, transactionQueue);

// Reversing & Sorting
const rates = [24, 27, 20, 12, 28];
rates.reverse();
console.log("Reversed Rates:", rates);

rates.sort((a, b) => a - b);
console.log("Sorted Rates (Numeric):", rates);

// ----------------------------------------------------------------------------
// 3. Functional Array Methods (forEach, find, filter, map, reduce)
// ----------------------------------------------------------------------------
console.log("\n--- Functional Array Methods (React Ready) ---");

const bankLedger = [
    { id: 1, type: "DEPOSIT", amount: 1500, status: "PENDING" },
    { id: 2, type: "WITHDRAWAL", amount: 200, status: "COMPLETED" },
    { id: 3, type: "WITHDRAWAL", amount: 450, status: "PENDING" },
    { id: 4, type: "DEPOSIT", amount: 3000, status: "COMPLETED" }
];

// forEach() - Iteration
console.log("Ledger Summary (forEach):");
bankLedger.forEach((tx, idx) => {
    console.log(`  [Tx #${idx + 1}] ID: ${tx.id} | ${tx.type} | Amount: $${tx.amount} | ${tx.status}`);
});






// find() - Returns first matching element
const pendingTx = bankLedger.find(tx => tx.status === "PENDING");
console.log("Found Pending Tx:", pendingTx);




// filter() - Creates a new array passing condition (Crucial for React state filtering)
const completedDeposits = bankLedger.filter(tx => tx.type === "DEPOSIT" && tx.status === "COMPLETED");
console.log("Filtered Completed Deposits:", completedDeposits);

// map() - Transforms elements into a new array (Crucial for React JSX UI lists)
const transactionSummaries = bankLedger.map(tx => `Tx #${tx.id}: ${tx.type} of $${tx.amount}`);
console.log("Mapped Summaries:", transactionSummaries);

// reduce() - Aggregate total balance
const totalNetBalance = bankLedger.reduce((acc, tx) => {
    return tx.type === "DEPOSIT" ? acc + tx.amount : acc - tx.amount;
}, 0);
console.log(`Calculated Total Net Balance: $${totalNetBalance}`);

// ----------------------------------------------------------------------------
// 4. PDF HANDS-ON EXERCISE
// ----------------------------------------------------------------------------
console.log("\n--- PDF Hands-on: Convert Array Strings to Uppercase ---");
/**
 * PDF Requirement:
 * Create an array of Strings which contains values like ["sTandarD", "CharTered", "banK"]
 * then replace the array values with corresponding Uppercase values only:
 * ["STANDARD", "CHARTERED", "BANK"]
 */
const inputBankNames = ["sTandarD", "CharTered", "banK"];
console.log("Original Array:", inputBankNames);

// Using .map() to return a clean uppercase array (Best Practice)
const uppercaseBankNames = inputBankNames.map(name => name.toUpperCase());
console.log("Transformed Uppercase Array:", uppercaseBankNames);

// ----------------------------------------------------------------------------
// 5. JavaScript Objects & 'this' Context
// ----------------------------------------------------------------------------
console.log("\n--- Objects & Methods ---");
const bankCustomer = {
    firstName: "Bob",
    lastName: "Smith",
    age: 32,
    accountType: "Savings",
    balance: 15400.00,
    
    // Method using 'this' keyword
    getAccountSummary: function() {
        return `${this.firstName} ${this.lastName} (${this.accountType} Acc) - Balance: $${this.balance.toFixed(2)}`;
    },

    deposit: function(amount) {
        this.balance += amount;
        return `Deposited $${amount}. New Balance: $${this.balance}`;
    }
};

console.log("Customer Summary:", bankCustomer.getAccountSummary());
console.log(bankCustomer.deposit(2500));
console.log("Updated Balance:", bankCustomer.balance);

/**
 * ============================================================================
 * REACT IMPORTANCE & BEST PRACTICES SUMMARY
 * ============================================================================
 * 1. Why map() & filter() are React Essentials:
 *    - In React, rendering a dynamic transaction list in UI looks like:
 *      {transactions.map(tx => <TransactionCard key={tx.id} data={tx} />)}
 *    - Key props allow React's Virtual DOM diffing engine to track item movements efficiently.
 * 
 * 2. Immutability Principle:
 *    - Direct mutation (e.g. `customer.balance = 500`) does NOT alert React to re-render.
 *    - Always use immutable patterns: `setCustomer(prev => ({ ...prev, balance: prev.balance + 2500 }))`.
 * ============================================================================
 */
