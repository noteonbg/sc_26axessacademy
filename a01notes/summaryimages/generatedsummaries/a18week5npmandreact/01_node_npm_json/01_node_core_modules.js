/**
 * ============================================================================
 * WEEK 5 - MODULE 1: NODE.JS CORE MODULES IN FINANCIAL SYSTEMS
 * ============================================================================
 * Topics Covered:
 * - Overview of Node.js (V8 runtime outside browser, server-side scripting)
 * - Types of Modules: Core Modules (http, fs, path, url, querystring, util)
 * 
 * Financial Domain Context:
 * - Creating an HTTP Core Banking Microservice & File Audit Logger.
 * 
 * Industry Best Practices:
 * - Use asynchronous non-blocking file I/O (`fs.promises` or `fs.writeFile`) to prevent event-loop blocking.
 * - Always set appropriate HTTP response headers (`Content-Type: application/json`).
 * ============================================================================
 */

'use strict';

const http = require('http');
const fs = require('fs');
const path = require('path');
const url = require('url');
const util = require('util');

console.log("=== Week 5: Node.js Core Modules Demo ===");

// 1. Path Module: Safely constructing file system paths
const auditLogPath = path.join(__dirname, 'bank_audit_ledger.log');
console.log(`Audit Log File Location: ${auditLogPath}`);

// 2. FS (File System) Module: Asynchronous File I/O
function logBankingTransaction(txDetails) {
    const logEntry = `[${new Date().toISOString()}] ${JSON.stringify(txDetails)}\n`;
    
    // Append to file asynchronously
    fs.appendFile(auditLogPath, logEntry, 'utf8', (err) => {
        if (err) {
            console.error("Failed to write to audit log:", err);
        } else {
            console.log(`✓ Audit Log Appended: ${txDetails.txId}`);
        }
    });
}

// Log initial dummy transaction
logBankingTransaction({ txId: "TX-1001", account: "ACC-88301", amount: 1500.00, type: "DEPOSIT" });

// 3. HTTP Module: Lightweight Core Banking Server
const PORT = 3005;
const server = http.createServer((req, res) => {
    // Parse URL & Query String
    const parsedUrl = url.parse(req.url, true);
    const pathname = parsedUrl.pathname;

    // CORS & JSON Response Headers
    res.setHeader('Content-Type', 'application/json');
    res.setHeader('Access-Control-Allow-Origin', '*');

    if (pathname === '/api/health') {
        res.writeHead(200);
        res.end(JSON.stringify({ status: "UP", service: "Core Banking Node Microservice", time: new Date() }));
    } else if (pathname === '/api/balance') {
        const accNo = parsedUrl.query.account || "ACC-DEFAULT";
        res.writeHead(200);
        res.end(JSON.stringify({
            accountNumber: accNo,
            currency: "USD",
            availableBalance: 48500.75,
            ledgerBalance: 50000.00
        }));
    } else {
        res.writeHead(404);
        res.end(JSON.stringify({ error: "Endpoint Not Found", path: pathname }));
    }
});

// Start Server on PORT 3005
server.listen(PORT, () => {
    console.log(`\n🚀 Core Banking Node Server running at http://localhost:${PORT}/`);
    console.log(`   Test Endpoint: http://localhost:${PORT}/api/balance?account=ACC-9988`);
    
    // Auto-close server after 3 seconds for CLI run testing convenience
    setTimeout(() => {
        server.close(() => {
            console.log("\n[Server Closed] Node.js Core Modules Test Completed.");
        });
    }, 3000);
});
