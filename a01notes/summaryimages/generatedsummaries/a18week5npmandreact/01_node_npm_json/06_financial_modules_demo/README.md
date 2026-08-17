# Developer Guide: How Node.js Local Modules Work in Financial Applications

This guide explains how to create, export, and import Node.js local modules using a banking and financial domain example, with **`06_financial_modules_demo`** as a standalone reference.

---

## 1. Understanding Node.js Local Modules

1.1 What is a Local Module?  
In Node.js, a local module is a reusable JavaScript file that contains functions, objects, or classes organized in isolation.

1.2 Module Encapsulation and Singletons  
Functions inside a module are private by default. Only functions explicitly attached to `module.exports` can be accessed by other scripts. In Node.js, loaded modules are cached as singletons in memory.

---

## 2. Directory Structure Blueprint

2.1 File Organization:
```
06_financial_modules_demo/
├── financialCalculator.js         # Local Module (Exporting calculation logic)
├── app.js                         # Main Application Script (Importing & running calculation)
└── README.md                      # Developer Documentation
```

---

## 3. Step-by-Step Code Implementations with Syntax Explanations

### 3.1 Module Definition and Export (`financialCalculator.js`)

3.1.1 Step 1: Define Calculation Functions  
Create private helper functions inside `financialCalculator.js`:
```javascript
function calculateSimpleInterest(principal, annualRate, timeYears) {
    const interest = (principal * annualRate * timeYears) / 100;
    const totalAmount = principal + interest;
    return {
        principal,
        annualRate,
        timeYears,
        interestAmount: Number(interest.toFixed(2)),
        totalAmount: Number(totalAmount.toFixed(2))
    };
}
```

3.1.2 Step 2: Export Functions via `module.exports`  
Expose the calculation functions so external scripts can import them:
```javascript
module.exports = {
    calculateSimpleInterest,
    calculateMonthlyEMI
};
```
*Syntax Explanation*: `module.exports` is a special built-in object provided by Node.js. Attaching functions to `module.exports` allows other files to access them.

---

### 3.2 Module Import and Consumption (`app.js`)

3.2.1 Step 1: Import Local Module via `require()`  
Import the module using its relative file path (`./financialCalculator`):
```javascript
const financialCalc = require('./financialCalculator');
```
*Syntax Explanation*: `require('./financialCalculator')` loads the exported functions object into the constant `financialCalc`. The `./` prefix indicates a local file.

3.2.2 Step 2: Execute Financial Calculations  
Call the module functions and output results:
```javascript
const fdResult = financialCalc.calculateSimpleInterest(100000, 7.5, 3);
console.log("Maturity Amount: $" + fdResult.totalAmount);

const loanResult = financialCalc.calculateMonthlyEMI(500000, 8.5, 240);
console.log("Monthly EMI: $" + loanResult.monthlyEMI);
```

---

## 4. How to Run the Financial Module Demo

4.1 Step 1: Open Terminal in Directory  
```bash
cd 06_financial_modules_demo
```

4.2 Step 2: Run Application Script with Node  
```bash
node app.js
```
The terminal will display calculated maturity amounts, interest totals, and monthly EMI values.
