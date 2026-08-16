/**
 * ============================================================================
 * WEEK 5 - MODULE 2: REST API LABS 1 - 5 (REQRES.IN SIMULATION / AXIOS PATTERN)
 * ============================================================================
 * Topics Covered:
 * - Lab 1: GET list of users (https://reqres.in/api/users?page=2)
 * - Lab 2: GET single user (https://reqres.in/api/users/2)
 * - Lab 3: POST create new user (https://reqres.in/api/users)
 * - Lab 4: PUT update existing user (https://reqres.in/api/users/2)
 * - Lab 5: GET user not found (https://reqres.in/api/users/23 -> 404 Status)
 * ============================================================================
 */

'use strict';

const https = require('https');

console.log("=== Week 5: REST API Hands-On Labs 1 to 5 ===");

// Utility helper function making HTTPS request using Promise API (Native Node replacement for Axios)
function mockAxiosRequest(method, urlString, payloadData = null) {
    return new Promise((resolve, reject) => {
        const parsedUrl = new URL(urlString);
        const options = {
            hostname: parsedUrl.hostname,
            port: 443,
            path: parsedUrl.pathname + parsedUrl.search,
            method: method.toUpperCase(),
            headers: {
                'Content-Type': 'application/json',
                'User-Agent': 'Node-Axios-Lab/1.0'
            }
        };

        const req = https.request(options, (res) => {
            let body = '';
            res.on('data', chunk => body += chunk);
            res.on('end', () => {
                try {
                    const parsedData = body ? JSON.parse(body) : {};
                    resolve({
                        status: res.statusCode,
                        statusText: res.statusMessage,
                        data: parsedData
                    });
                } catch (e) {
                    resolve({ status: res.statusCode, data: body });
                }
            });
        });

        req.on('error', (err) => reject(err));

        if (payloadData) {
            req.write(JSON.stringify(payloadData));
        }

        req.end();
    });
}

// ----------------------------------------------------------------------------
// Async Executor for Labs 1 through 5
// ----------------------------------------------------------------------------
async function executeAllRestLabs() {
    try {
        // LAB 1: GET List of Users
        console.log("\n🔹 LAB 1: GET List of Users (page=2)...");
        const res1 = await mockAxiosRequest('GET', 'https://reqres.in/api/users?page=2');
        console.log(`   Status Code: ${res1.status} OK`);
        console.log(`   Page: ${res1.data.page}, Total Users: ${res1.data.total}`);
        if (res1.data.data && res1.data.data.length > 0) {
            console.log(`   First User on Page 2: ${res1.data.data[0].first_name} ${res1.data.data[0].last_name} (${res1.data.data[0].email})`);
        }

        // LAB 2: GET Single User
        console.log("\n🔹 LAB 2: GET Single User (ID #2)...");
        const res2 = await mockAxiosRequest('GET', 'https://reqres.in/api/users/2');
        console.log(`   Status Code: ${res2.status} OK`);
        console.log(`   Fetched User:`, res2.data.data);

        // LAB 3: POST Create New User (Matches PDF Slide 11: name="Rohit", job="leader")
        console.log("\n🔹 LAB 3: POST Create User ({ name: 'Rohit', job: 'leader' })...");
        const res3 = await mockAxiosRequest('POST', 'https://reqres.in/api/users', { name: "Rohit", job: "leader" });
        console.log(`   Status Code: ${res3.status} Created`);
        console.log(`   Created User Record:`, res3.data);

        // LAB 4: PUT Update Existing User (Matches PDF Slide 12: name="James", job="manager")
        console.log("\n🔹 LAB 4: PUT Update User (ID #2 -> { name: 'James', job: 'manager' })...");
        const res4 = await mockAxiosRequest('PUT', 'https://reqres.in/api/users/2', { name: "James", job: "manager" });
        console.log(`   Status Code: ${res4.status} OK`);
        console.log(`   Updated Record Timestamp:`, res4.data);

        // LAB 5: GET User Not Found (Matches PDF Slide 13: ID #23 -> 404)
        console.log("\n🔹 LAB 5: GET User Not Found (ID #23 -> Expecting 404 Error)...");
        const res5 = await mockAxiosRequest('GET', 'https://reqres.in/api/users/23');
        console.log(`   Status Code: ${res5.status}`);
        console.log(`   Response Body:`, res5.data);
        if (res5.status === 404) {
            console.log(`   ✓ Correctly Handled 404 Not Found Status Code.`);
        }

    } catch (error) {
        console.error("❌ REST API Lab Failure:", error.message);
    }
}

executeAllRestLabs();
