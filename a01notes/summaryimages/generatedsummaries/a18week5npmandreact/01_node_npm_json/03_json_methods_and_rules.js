/**
 * ============================================================================
 * WEEK 5 - MODULE 1: JSON SYNTAX, DATA TYPES & METHODS
 * ============================================================================
 * Topics Covered:
 * - JSON (JavaScript Object Notation) rules & structure
 * - Allowed Types: string, number, object, array, boolean, null
 * - Disallowed Types: function, date, undefined
 * - Methods: JSON.parse() and JSON.stringify()
 * ============================================================================
 */

'use strict';

console.log("=== Week 5: JSON Syntax, Rules & Methods ===");

// 1. Valid Banking JSON Object (Double quotes for keys and strings)
const rawJsonString = `{
    "accountNumber": "ACC-884920",
    "customerName": "Anita Rao",
    "isKYCApproved": true,
    "creditLimit": 25000,
    "secondaryCardholders": ["Rahul Rao", "Priya Rao"],
    "linkedLoans": null
}`;

console.log("1. Raw JSON String (received over network):");
console.log(rawJsonString);

// 2. Parsing JSON string into JavaScript Object (JSON.parse)
const parsedCustomerObj = JSON.parse(rawJsonString);
console.log("\n2. Parsed JS Object (JSON.parse):");
console.log(`   Customer: ${parsedCustomerObj.customerName}`);
console.log(`   Limit: $${parsedCustomerObj.creditLimit}`);
console.log(`   Cardholders Array: ${parsedCustomerObj.secondaryCardholders.join(", ")}`);

// 3. Modifying Object & Stringifying back to JSON (JSON.stringify)
parsedCustomerObj.creditLimit += 5000;
parsedCustomerObj.lastUpdated = "2026-08-07";

const updatedJsonString = JSON.stringify(parsedCustomerObj, null, 2);
console.log("\n3. Serialized JSON String (JSON.stringify with formatting):");
console.log(updatedJsonString);

// 4. Demonstrating Disallowed Data Types in JSON
const jsObjWithForbiddenTypes = {
    bankName: "Standard Chartered",
    createdDate: new Date(),                     // Converts to ISO string on stringify
    calculateTax: function() { return 100; },    // Omitted completely!
    unassignedVar: undefined                      // Omitted completely!
};

console.log("\n4. Handling Disallowed Data Types during JSON.stringify:");
console.log("   Original JS Object containing function and undefined:", jsObjWithForbiddenTypes);
console.log("   Stringified Output (Functions & undefined are stripped out):");
console.log(JSON.stringify(jsObjWithForbiddenTypes));
