/**
 * ============================================================================
 * WEEK 5 - MODULE 1: LOCAL MODULE DESIGN (ES MODULES & COMMONJS)
 * ============================================================================
 * Topics Covered:
 * - Creating and consuming local user-defined modules.
 * - Singletons in Node modules (Modules are cached after initial require/import).
 * ============================================================================
 */

'use strict';

console.log("=== Week 5: Local Modules & Singleton Design ===");

// 1. Local Banking Utilities Module Definition
const BankingCalculatorModule = (function () {
    // Private variable inside closure scope
    let totalCalculationsPerformed = 0;

    return {
        calculateCompoundInterest: function (principal, annualRatePercent, years, compoundingFrequency = 12) {
            totalCalculationsPerformed++;
            const r = annualRatePercent / 100;
            const n = compoundingFrequency;
            const amount = principal * Math.pow(1 + r / n, n * years);
            return amount;
        },

        formatCurrency: function (amount, currencyCode = 'USD') {
            return new Intl.NumberFormat('en-US', {
                style: 'currency',
                currency: currencyCode
            }).format(amount);
        },

        getUsageMetrics: function () {
            return `Calculations Executed: ${totalCalculationsPerformed}`;
        }
    };
})();

// Demonstrating Module Export / Import consumption
const principal = 100000;
const rate = 7.5;
const years = 5;

const futureValue = BankingCalculatorModule.calculateCompoundInterest(principal, rate, years);
const formattedFV = BankingCalculatorModule.formatCurrency(futureValue, 'USD');

console.log(`Initial Investment: $${principal.toLocaleString()}`);
console.log(`Annual Interest Rate: ${rate}% over ${years} years`);
console.log(`Projected Compound Value: ${formattedFV}`);
console.log(`Module Metric: ${BankingCalculatorModule.getUsageMetrics()}`);
