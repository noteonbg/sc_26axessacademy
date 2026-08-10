/**
 * ============================================================================
 * MODULE 7: ADVANCED JAVASCRIPT (ES6+) & REACT ARCHITECTURE
 * ============================================================================
 * Topics Covered:
 * - let & const keywords (block scoping)
 * - Arrow functions (concise syntax & lexical 'this' binding)
 * - ES6 Classes & Objects (constructor, methods, instantiation)
 * - Destructuring (Array & Object unpacking)
 * - The Spread Operator (...) (cloning, merging objects/arrays)
 * - ES Modules concept (import & export)
 * 
 * Financial Domain Context:
 * - Corporate Banking Employee & Customer Objects, Portfolio state updates,
 *   ledger merging without mutating original financial records.
 * ============================================================================
 */

'use strict';

console.log("\n=== Module 7: Advanced JavaScript (ES6+) ===");

// ----------------------------------------------------------------------------
// 1. let & const Block Scoping
// ----------------------------------------------------------------------------
function demonstrateBlockScope() {
    const minReserveRatio = 0.04; // Read-only block scoped constant
    let bankVaultStatus = "SECURE";

    if (true) {
        // block scope level 1
        const minReserveRatio = 0.10; // Shadows outer variable inside this block
        let bankVaultStatus = "AUDITING";
        console.log(`Inside block: reserveRatio = ${minReserveRatio}, vaultStatus = ${bankVaultStatus}`);
    }

    console.log(`Outside block: reserveRatio = ${minReserveRatio}, vaultStatus = ${bankVaultStatus}`);
}
demonstrateBlockScope();

// ----------------------------------------------------------------------------
// 2. Arrow Functions (Implicit vs Explicit Return)
// ----------------------------------------------------------------------------
console.log("\n--- Arrow Functions ---");

// Standard function expression
const calcInterestOld = function (principal, rate) {
    return (principal * rate) / 100;
};

// ES6 Arrow function equivalent (explicit return)
const calcInterestArrow = (principal, rate) => {
    return (principal * rate) / 100;
};

// Concise single-line arrow function (implicit return)
const calcTaxStandard = income => income * 0.15;

console.log(`Standard Calc Interest: $${calcInterestOld(10000, 5)}`);
console.log(`Arrow Calc Interest:    $${calcInterestArrow(10000, 5)}`);
console.log(`Implicit Return Tax:    $${calcTaxStandard(50000)}`);

// ----------------------------------------------------------------------------
// 3. ES6 Classes & Object Instantiation
// ----------------------------------------------------------------------------
console.log("\n--- ES6 Class & Objects ---");

class BankCustomerAccount {
    constructor(accountNumber, customerName, initialBalance = 0) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.balance = initialBalance;
    }

    deposit(amount) {
        if (amount <= 0) throw new Error("Deposit amount must be positive.");
        this.balance += amount;
        console.log(`[${this.accountNumber}] Deposited $${amount}. New Balance: $${this.balance}`);
        return this.balance;
    }

    withdraw(amount) {
        if (amount > this.balance) {
            console.log(`[${this.accountNumber}] Insufficient Funds for $${amount} withdrawal!`);
            return false;
        }
        this.balance -= amount;
        console.log(`[${this.accountNumber}] Withdrew $${amount}. Remaining Balance: $${this.balance}`);
        return true;
    }

    getAccountDetails() {
        return `Account #${this.accountNumber} | Holder: ${this.customerName} | Balance: $${this.balance}`;
    }
}

// Instantiating Class
const custAcc1 = new BankCustomerAccount("ACC-7749", "Anita Sharma", 25000);
console.log(custAcc1.getAccountDetails());
custAcc1.deposit(5000);
custAcc1.withdraw(8000);
custAcc1.withdraw(50000); // Attempt overdraft

// ----------------------------------------------------------------------------
// 4. Destructuring (Object & Array Unpacking)
// ----------------------------------------------------------------------------
console.log("\n--- Object & Array Destructuring ---");

const financialProfile = {
    accNo: "IND998811",
    holder: {
        firstName: "Rajesh",
        lastName: "Kumar"
    },
    metrics: {
        creditScore: 785,
        tier: "Platinum"
    }
};

// Object Destructuring with nested values & default fallback
const { accNo, metrics: { creditScore, tier }, branch = "Mumbai Main" } = financialProfile;
console.log(`Destructured -> Acc: ${accNo}, Score: ${creditScore}, Tier: ${tier}, Branch: ${branch}`);

// Array Destructuring
const currencyRates = [1.00, 0.85, 0.78, 83.25];
const [usd, eur, gbp, inr] = currencyRates;
console.log(`Array Destructured Rates -> USD: ${usd}, EUR: ${eur}, GBP: ${gbp}, INR: ${inr}`);

// ----------------------------------------------------------------------------
// 5. The Spread Operator (...) for Immutability
// ----------------------------------------------------------------------------
console.log("\n--- The Spread Operator (...) ---");

// Array Merging & Cloning
const Q1Transactions = [150, 300, 450];
const Q2Transactions = [200, 600];

// Creating a brand new combined array without mutating originals
const annualTransactions = [...Q1Transactions, ...Q2Transactions, 1000];
console.log("Spread Merged Transactions:", annualTransactions);

// Object Cloning & Immutable State Update (React Core Pattern!)
const originalAccount = { id: 201, name: "Priya", balance: 5000, status: "Active" };

// Creating updated account object immutably
const updatedAccount = {
    ...originalAccount,
    balance: originalAccount.balance + 1500, // Override balance
    lastUpdated: new Date().toISOString()
};

console.log("Original Object (Unchanged):", originalAccount);
console.log("Updated Object (New Reference):", updatedAccount);

/**
 * ============================================================================
 * REACT IMPORTANCE SUMMARY
 * ============================================================================
 * 1. Prop Destructuring in React Components:
 *    `const AccountCard = ({ customerName, balance, tier }) => <div>...</div>;`
 * 
 * 2. Immutable State Update via Spread Operator:
 *    `setUserState(prev => ({ ...prev, balance: prev.balance + depositAmount }));`
 * 
 * 3. Modern Component Syntax:
 *    React functional components use ES6 Arrow functions:
 *    `const BankDashboard = () => { return <main>...</main>; };`
 * ============================================================================
 */
