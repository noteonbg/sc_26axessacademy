/**
 * ============================================================================
 * WEEK 5 - MODULE 2: GUIDING PRINCIPLES OF REST & IDEMPOTENCY
 * ============================================================================
 * Topics Covered:
 * - REST Guiding Principles: Client-Server, Statelessness, Cacheable, Uniform Interface, Layered System
 * - Idempotency in REST APIs:
 *   - Idempotent Methods: GET, PUT, DELETE, HEAD, OPTIONS (Multiple identical requests = same state)
 *   - Non-Idempotent Method: POST (Multiple identical requests = creates multiple resources)
 * - Content Negotiation & CORS (Cross-Origin Resource Sharing)
 * ============================================================================
 */

'use strict';

console.log("=== Week 5: REST Architectural Principles & Idempotency ===");

// 1. Idempotency Demonstration Simulator
const mockDatabase = {
    accounts: [
        { id: "ACC-101", balance: 5000 },
        { id: "ACC-102", balance: 12000 }
    ]
};

console.log("Initial Database State:", JSON.stringify(mockDatabase.accounts));

// IDEMPOTENT OPERATION (PUT: Replaces account state explicitly)
function idempotentPutUpdate(accountId, newBalance) {
    const acc = mockDatabase.accounts.find(a => a.id === accountId);
    if (acc) {
        acc.balance = newBalance; // Running 1 time or 10 times sets balance to exact same value!
    }
}

// NON-IDEMPOTENT OPERATION (POST: Appends new transaction record each call)
function nonIdempotentPostCreate(newAccId, initialBalance) {
    const newAcc = { id: newAccId, balance: initialBalance };
    mockDatabase.accounts.push(newAcc); // Running 10 times creates 10 duplicate records!
}

// Testing PUT (Idempotent)
console.log("\n1. Testing PUT (Idempotent Method): Setting ACC-101 balance to $7,500...");
idempotentPutUpdate("ACC-101", 7500);
idempotentPutUpdate("ACC-101", 7500); // 2nd identical call
idempotentPutUpdate("ACC-101", 7500); // 3rd identical call
console.log("-> State after 3 identical PUT calls:", JSON.stringify(mockDatabase.accounts[0]));
console.log("-> RESULT: ACC-101 balance remained $7,500. PUT IS IDEMPOTENT.");

// Testing POST (Non-Idempotent)
console.log("\n2. Testing POST (Non-Idempotent Method): Invoking POST 3 times...");
nonIdempotentPostCreate("ACC-103", 2000);
nonIdempotentPostCreate("ACC-103", 2000); // 2nd call creates duplicate!
console.log("-> State after 2 POST calls:", JSON.stringify(mockDatabase.accounts));
console.log("-> RESULT: 2 new distinct account entries created. POST IS NOT IDEMPOTENT.");

// 2. Content Negotiation Explanation
console.log("\n--- Content Negotiation & CORS Summary ---");
console.log("• Content Negotiation Header: 'Accept: application/json' (Client tells server preferred representation format).");
console.log("• CORS (Cross-Origin Resource Sharing): Browser security feature using 'Access-Control-Allow-Origin' header to permit domain-a.com to fetch data from api.domain-b.com.");
