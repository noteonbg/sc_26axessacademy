/**
 * ============================================================================
 * MODULE 8: ASYNCHRONOUS JAVASCRIPT - PROMISES, FETCH API & ASYNC/AWAIT
 * ============================================================================
 * Topics Covered:
 * - Promises (Creating with new Promise, resolve, reject, .then(), .catch())
 * - Fetch API (HTTP network calls, response parsing with .json())
 * - Async / Await (Synchronous-style asynchronous code, try...catch error handling)
 * 
 * Financial Domain Context:
 * - Asynchronous Core Banking API calls for Credit Verification, Real-Time Forex Rates,
 *   and Fraud Detection processing.
 * 
 * React Connection:
 * - In React, network API calls are placed inside the `useEffect` lifecycle hook.
 * - Components must handle 3 asynchronous states: Loading (`isLoading`), Success (`data`),
 *   and Failure (`error`).
 * ============================================================================
 */

'use strict';

console.log("\n=== Module 8: Asynchronous JavaScript (Promises, Fetch & Async/Await) ===");

// ----------------------------------------------------------------------------
// 1. Creating & Handling Promises (Bank Credit Authorization Simulation)
// ----------------------------------------------------------------------------

// Ramesh
function checkCreditApproval(creditScore) {
    return new Promise((x, y) => {
        //Logic inside the function is written by Ramesh.. full fill the promise..
        console.log(`[Credit System] Verifying score (${creditScore})... Please wait...`);
        
        setTimeout(() => {
            if (creditScore >= 700) {
                x({
                    status: "APPROVED",
                    score: creditScore,
                    maxLoanAmount: 500000,
                    interestRate: "6.8%"
                });
            } else {
                y({
                    status: "REJECTED",
                    score: creditScore,
                    reason: "Credit score below required threshold (700)."
                });
            }
        }, 1500); // 1.5 second delay
    });

}


// Consuming Promise using .then() and .catch()
console.log("\n--- Promise Consumption (.then / .catch) ---");

//sursh has to use the code
checkCreditApproval(750)
    .then(result => {
        console.log("PROMISE RESOLVED:", result);
    })
    .catch(error => {
        console.log("PROMISE REJECTED:", error);
    });

// ----------------------------------------------------------------------------
// 2. Async / Await Syntax with try...catch Error Handling
// ----------------------------------------------------------------------------
console.log("\n--- Async / Await Demonstration ---");

async function processLoanApplication(applicantName, score) {
    console.log(`\nStarting loan application evaluation for '${applicantName}'...`);
    
    try {
        // 'await' pauses execution until the promise resolves or rejects
        const approval = await checkCreditApproval(score);
        console.log(` SUCCESS: Loan approved for ${applicantName}!`);
        console.log(` Max Loan: $${approval.maxLoanAmount} | APR: ${approval.interestRate}`);
        return approval;
    } catch (err) {
        console.log(` DECLINED: Loan application failed for ${applicantName}.`);
        console.log(` Reason: ${err.reason}`);
        return null;
    } finally {
        console.log(`[System Log] Evaluation completed for ${applicantName}.`);
    }
}

// Invoking async function
processLoanApplication("David Miller", 720);
processLoanApplication("Sarah Jenkins", 640);

// ----------------------------------------------------------------------------
// 3. The Fetch API Simulation (Network Call & JSON parsing)
// ----------------------------------------------------------------------------
console.log("\n--- Fetch API Pattern in Financial Web Apps ---");

/**
 * Standard Fetch API Example Syntax:
 * fetch('https://api.exchangerate-api.com/v4/latest/USD')
 *   .then(response => {
 *      if (!response.ok) throw new Error("HTTP Error " + response.status);
 *      return response.json();
 *   })
 *   .then(data => console.log("Rates:", data))
 *   .catch(err => console.error("Fetch Failed:", err));
 */

// Simulated Mock Fetch for offline Node execution safety
function mockFetchForexRates(currencyBase = "USD") {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            const mockApiResponse = {
                ok: true,
                status: 200,
                json: async () => ({
                    base: currencyBase,
                    date: new Date().toISOString().split('T')[0],
                    rates: { EUR: 0.92, GBP: 0.79, INR: 83.15, JPY: 155.40 }
                })
            };
            resolve(mockApiResponse);
        }, 1000);
    });
}

// Fetching rates using Modern Async/Await pattern
async function fetchLatestForexRates() {
    try {
        console.log("[Fetch API] Requesting foreign exchange rates...");
        const response = await mockFetchForexRates("USD");
        
        if (!response.ok) {
            throw new Error(`Server returned HTTP status ${response.status}`);
        }
        
        const data = await response.json();
        console.log("✓ Forex API Response Received:");
        console.log(`  Base: ${data.base} | Date: ${data.date}`);
        console.log(`  USD -> EUR: ${data.rates.EUR}`);
        console.log(`  USD -> INR: ₹${data.rates.INR}`);
    } catch (error) {
        console.error("Fetch API Error:", error.message);
    }
}

fetchLatestForexRates();

/**
 * ============================================================================
 * REACT IMPORTANCE SUMMARY: useEffect & Data Fetching
 * ============================================================================
 * In React components, network fetching is executed inside useEffect:
 * 
 * useEffect(() => {
 *   let isMounted = true; // prevents state updates on unmounted component
 *   
 *   async function loadData() {
 *     setIsLoading(true);
 *     try {
 *       const res = await fetch('/api/balance');
 *       const result = await res.json();
 *       if (isMounted) setBalanceData(result);
 *     } catch (err) {
 *       if (isMounted) setError(err.message);
 *     } finally {
 *       if (isMounted) setIsLoading(false);
 *     }
 *   }
 *   
 *   loadData();
 *   return () => { isMounted = false; }; // Cleanup
 * }, []);
 * ============================================================================
 */
