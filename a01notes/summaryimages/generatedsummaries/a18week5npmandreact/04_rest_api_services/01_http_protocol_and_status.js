/**
 * ============================================================================
 * WEEK 5 - MODULE 2: HTTP PROTOCOL & STATUS CODES SIMULATOR
 * ============================================================================
 * Topics Covered:
 * - HTTP Request Structure (Request Line, Headers, Message Body)
 * - HTTP Response Structure (Status Line, Headers, Message Body)
 * - HTTP Methods: GET, POST, PUT, DELETE, PATCH, OPTIONS, HEAD
 * - HTTP Status Codes:
 *   - 200 OK, 201 Created
 *   - 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 405 Method Not Allowed
 *   - 500 Internal Server Error, 503 Service Unavailable, 504 Gateway Timeout
 * ============================================================================
 */

'use strict';

console.log("=== Week 5: HTTP Protocol & Status Codes Simulator ===");

// Banking HTTP Request Constructor Simulator
function buildHttpRequest(method, path, headers = {}, body = null) {
    return {
        requestLine: `${method.toUpperCase()} ${path} HTTP/1.1`,
        headers: {
            Host: "api.standardchartered.com",
            Accept: "application/json",
            "Content-Type": "application/json",
            "User-Agent": "SCB-MobileApp/v4.2",
            ...headers
        },
        body: body ? JSON.stringify(body) : ""
    };
}

// Banking HTTP Server Response Router Simulator
function simulateBankServerResponse(httpRequest) {
    console.log(`\n------------------------------------------------------------`);
    console.log(`📥 INCOMING REQUEST: ${httpRequest.requestLine}`);
    console.log(`   Headers:`, httpRequest.headers);
    if (httpRequest.body) console.log(`   Payload Body:`, httpRequest.body);

    const [method, path] = httpRequest.requestLine.split(" ");
    
    // Auth Check
    const authHeader = httpRequest.headers["Authorization"];

    if (!authHeader) {
        return {
            statusLine: "HTTP/1.1 401 Unauthorized",
            code: 401,
            body: { error: "Authentication credentials missing. Bearer token required." }
        };
    }

    if (authHeader === "Bearer EXPIRED_TOKEN") {
        return {
            statusLine: "HTTP/1.1 403 Forbidden",
            code: 403,
            body: { error: "Token expired or insufficient permissions for resource." }
        };
    }

    // Path Routing & Methods
    if (path === "/api/v1/accounts" && method === "GET") {
        return {
            statusLine: "HTTP/1.1 200 OK",
            code: 200,
            body: [
                { id: "ACC-101", type: "SAVINGS", balance: 15400.00 },
                { id: "ACC-102", type: "CURRENT", balance: 82000.50 }
            ]
        };
    }

    if (path === "/api/v1/accounts" && method === "POST") {
        const payload = httpRequest.body ? JSON.parse(httpRequest.body) : {};
        if (!payload.customerName || !payload.initialDeposit) {
            return {
                statusLine: "HTTP/1.1 400 Bad Request",
                code: 400,
                body: { error: "Validation Failed: customerName and initialDeposit are required." }
            };
        }

        return {
            statusLine: "HTTP/1.1 201 Created",
            code: 201,
            body: {
                message: "Bank Account Created Successfully",
                accountNumber: "ACC-" + Math.floor(100000 + Math.random() * 900000),
                status: "ACTIVE"
            }
        };
    }

    if (path.startsWith("/api/v1/accounts/")) {
        const accId = path.split("/").pop();
        if (accId === "999") {
            return {
                statusLine: "HTTP/1.1 404 Not Found",
                code: 404,
                body: { error: `Account #${accId} does not exist in bank database.` }
            };
        }
    }

    return {
        statusLine: "HTTP/1.1 500 Internal Server Error",
        code: 500,
        body: { error: "Unhandled internal server error occurred." }
    };
}

// Running Test Scenarios
const testToken = "Bearer VALID_SCB_JWT_TOKEN";

// Scenario 1: 200 OK (GET Accounts)
const req1 = buildHttpRequest("GET", "/api/v1/accounts", { Authorization: testToken });
console.log("Response:", simulateBankServerResponse(req1));

// Scenario 2: 201 Created (POST New Account)
const req2 = buildHttpRequest("POST", "/api/v1/accounts", { Authorization: testToken }, { customerName: "Rajesh Kumar", initialDeposit: 5000 });
console.log("Response:", simulateBankServerResponse(req2));

// Scenario 3: 400 Bad Request (Missing payload)
const req3 = buildHttpRequest("POST", "/api/v1/accounts", { Authorization: testToken }, {});
console.log("Response:", simulateBankServerResponse(req3));

// Scenario 4: 401 Unauthorized (No auth header)
const req4 = buildHttpRequest("GET", "/api/v1/accounts");
console.log("Response:", simulateBankServerResponse(req4));

// Scenario 5: 404 Not Found (Non-existent Account)
const req5 = buildHttpRequest("GET", "/api/v1/accounts/999", { Authorization: testToken });
console.log("Response:", simulateBankServerResponse(req5));
