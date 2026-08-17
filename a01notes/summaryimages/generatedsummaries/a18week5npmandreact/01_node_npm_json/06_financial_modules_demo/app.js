/**
 * Main Application Script (Consuming Financial Local Module)
 */
'use strict';

// Importing local module
const financialCalc = require('./financialCalculator');

console.log("=================================================");
console.log("   FINANCIAL SERVICES NODE.JS MODULE DEMO");
console.log("=================================================\n");

// 1. Fixed Deposit Interest Calculation Scenario
console.log("--- 1. Fixed Deposit Interest Calculation ---");
const fdResult = financialCalc.calculateSimpleInterest(100000, 7.5, 3);
console.log("Principal Amount     : $" + fdResult.principal);
console.log("Annual Interest Rate : " + fdResult.annualRate + "%");
console.log("Tenure               : " + fdResult.timeYears + " Years");
console.log("Interest Earned      : $" + fdResult.interestAmount);
console.log("Total Maturity Amount: $" + fdResult.totalAmount);

console.log("\n-------------------------------------------------");

// 2. Home Loan EMI Calculation Scenario
console.log("--- 2. Home Loan EMI Calculation ---");
const loanResult = financialCalc.calculateMonthlyEMI(500000, 8.5, 240); // 20 years = 240 months
console.log("Loan Principal       : $" + loanResult.loanAmount);
console.log("Annual Interest Rate : " + loanResult.annualInterestRate + "%");
console.log("Loan Tenure          : " + loanResult.tenureMonths + " Months (20 Years)");
console.log("Monthly EMI Payable  : $" + loanResult.monthlyEMI);
console.log("Total Interest       : $" + loanResult.totalInterest);
console.log("Total Repayment      : $" + loanResult.totalPayment);

console.log("\n=================================================");
