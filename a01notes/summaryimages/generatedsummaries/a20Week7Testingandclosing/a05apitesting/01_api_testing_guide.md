# API Testing - Comprehensive Talk Summary & Guide

> **Target Audience**: Software Engineers, QA Testers, and Technology Trainees  
> **Topic**: Fundamentals of API Testing, Architecture, Strategies, and Practical Examples  
> **Source Material**: Week 7 Campus Content - Standard Chartered Axess Academy  

---

## 1. What is an API? (Application Programming Interface)

### High-Level Definition
An **API (Application Programming Interface)** allows two software systems to communicate with each other. It exposes business capabilities over standard network protocols (like HTTP) without revealing internal codebase details or database structures.

### The Restaurant Waiter Metaphor 🍽️
Think of an API like a **waiter** in a restaurant:
* **Customer (Client/App)**: You want to order food. You look at the menu (API documentation/specification).
* **Waiter (API)**: Takes your request, carries it to the kitchen, and brings back your food (response).
* **Kitchen (Backend System / Server)**: Prepares the food (executes business logic) without you needing to know how the stove works.

```
+------------------+         Request (Order)         +--------------------+         Request         +--------------------+
|  Client (User)   |  ---------------------------->  |     API Waiter     |  -------------------->  |   Server/Kitchen   |
| (Mobile/Web App) |  <----------------------------  | (Business Logic)   |  <--------------------  |  (Database/Logic)  |
+------------------+        Response (Dish)          +--------------------+        Data Response    +--------------------+
```

---

## 2. Web Services & Architecture Styles

A **Web Service** is any API exposed over the internet. Common web service architectural styles include:

| Architectural Style | Description | Data Format | Common Use Cases |
| :--- | :--- | :--- | :--- |
| **REST** (Representational State Transfer) | Resource-oriented, lightweight, stateless | JSON, XML, Plain Text | Mobile apps, Web APIs, Microservices |
| **SOAP** (Simple Object Access Protocol) | Protocol-driven, strict XML contracts (WSDL) | XML | Legacy Banking, Financial Gateways, Enterprise Security |
| **GraphQL** | Query-based, client requests exact fields | JSON | Flexible client-driven frontends |
| **RPC / gRPC** | Procedure-driven, high-performance binary transport | Protocol Buffers / JSON | Microservice-to-microservice communication |

### SOAP vs. REST Quick Comparison Table

| Feature | SOAP | REST |
| :--- | :--- | :--- |
| **Definition** | Protocol (Simple Object Access Protocol) | Architectural Style (Representational State Transfer) |
| **Focus** | Function/Operation-driven (`getUserDetails`) | Data/Resource-driven (`/api/v1/users/10`) |
| **Data Format** | Strict XML only | JSON, XML, HTML, Plain Text (JSON is industry standard) |
| **Contract** | WSDL (Web Services Description Language) | OpenAPI Specification / Swagger |
| **Transport** | HTTP, SMTP, JMS | HTTP / HTTPS |
| **Bandwidth** | Heavy payload overhead | Lightweight payload |

---

## 3. Anatomy of a REST API Request & Resource Naming

A REST request consists of **four primary components**:

1. **The Endpoint (URL)**: The unique address of the resource.
   - Example: `https://api.bank.com/v1/accounts`
2. **The HTTP Method (Action)**: Indicates the CRUD operation to perform.
   - `GET`: Read resource(s)
   - `POST`: Create a new resource
   - `PUT`: Update/replace an existing resource
   - `PATCH`: Partially update a resource
   - `DELETE`: Remove a resource
3. **HTTP Headers**: Metadata passed with the request.
   - `Content-Type: application/json`
   - `Authorization: Bearer <token>`
   - `Accept: application/json`
4. **Request Body (Payload)**: JSON or XML data sent with `POST`, `PUT`, or `PATCH`.

### REST Resource Naming Rules
* Use **nouns** (plural) instead of verbs: `/api/v1/accounts` (Good) vs `/api/v1/getAccounts` (Bad).
* Hierarchy reflects relationship: `/api/v1/customers/42/accounts` (Accounts for customer 42).

```
URL Structure:
https://api.bank.com : 8080 / api / v1 / accounts ? type=SAVINGS
|------- Host ------| |-Port-| |--- Resource Path ---| |-- Query Params --|
```

---

## 4. What is API Testing?

### Definition
API Testing directly tests the **Business Logic Layer** of an application's architecture. Unlike GUI testing which tests visual elements (buttons, layout, CSS), API testing validates data exchanges, response statuses, security, performance, and failure modes directly at the API endpoint level.

```
+------------------------------------+
|  Presentation Layer (GUI Testing)  |  --> Focuses on UI look & feel, visual layout
+------------------------------------+
|  Business Layer (API Testing)      |  --> Focuses on rules, validation, status codes, data integrity
+------------------------------------+
|  Database Layer (Data Storage)     |  --> Focuses on persistence and storage
+------------------------------------+
```

### Why API Testing is Crucial
1. **Early Bug Detection**: APIs can be tested before the GUI is built.
2. **Execution Speed**: API tests run in milliseconds compared to slow browser UI automation.
3. **Core Business Logic Validation**: Guarantees core financial and business rules are enforced regardless of which client (iOS, Android, Web) calls the API.
4. **Cost Reduction**: Automation at the API layer is faster to write and easier to maintain.

---

## 5. API Testing Checklist: What to Test?

When testing an API endpoint, verify these 8 key aspects:

1. **HTTP Status Codes**: Verify correct code (e.g., `200 OK`, `201 Created`, `400 Bad Request`, `401 Unauthorized`, `404 Not Found`, `500 Server Error`).
2. **Response Data Accuracy**: Ensure fields match expected values.
3. **Schema Validation**: Confirm JSON response matches defined structure and data types (String, Number, Boolean).
4. **Error Handling & Messages**: Verify meaningful error messages when bad data is sent.
5. **Authorization & Authentication**: Reject requests with missing or invalid tokens (`401` / `403`).
6. **Edge Cases & Input Validation**: Test boundary values, nulls, empty strings, and malformed payloads.
7. **Performance & Timeout**: Confirm response times meet SLAs (e.g. < 200ms).
8. **HTTP Method Restrictions**: Ensure unsupported methods return `405 Method Not Allowed`.

---

## 6. Functional Test Conditions & Examples for Your Talk

Here are practical test scenarios to present during a technical talk:

### Example 1: Valid Resource Creation (`POST /api/v1/accounts`)
* **Request**: Valid JSON payload.
* **Expected Result**: HTTP `201 Created`, `Location` header present, returned JSON contains generated `id`.

### Example 2: Invalid Input Validation (`POST /api/v1/accounts`)
* **Request**: Missing mandatory field `accountNumber` or negative initial balance `-500`.
* **Expected Result**: HTTP `400 Bad Request`, response JSON contains field-level error messages (`"balance cannot be negative"`).

### Example 3: Non-Existent Resource Retrieval (`GET /api/v1/accounts/9999`)
* **Request**: Request ID that does not exist in backend.
* **Expected Result**: HTTP `404 Not Found`, structured error response (`"Account with ID 9999 not found"`).

### Example 4: Authentication Security Check (`GET /api/v1/accounts`)
* **Request**: No Authorization header provided.
* **Expected Result**: HTTP `401 Unauthorized`.

---

## 7. Common API Testing Tools

* **Bruno 🐶**: Fast, open-source, local-first API client that saves collections directly as plain text `.bru` files inside your Git repository.
* **REST-assured**: Java library for automated REST API testing in BDD syntax (`given().when().then()`).
* **Spring Boot MockMvc**: In-memory Spring controller unit testing framework without starting a real HTTP server.
* **Apache JMeter / Gatling**: API performance, load, and stress testing.
* **SoapUI / Insomnia**: Additional tools for SOAP and REST web service testing.

---

## 8. API Testing with Bruno 🐶 (Git-Friendly & Open Source)

### Why Bruno?
Unlike traditional API clients that store collections in proprietary clouds, **Bruno** stores API requests directly in your project folder as human-readable, plain-text `.bru` files. This allows you to track API request collections directly in **Git**.

### Sample `.bru` Request Files

#### 1. `Get-All-Accounts.bru` (GET Request with Assertions)
```text
meta {
  name: Get All Accounts
  type: http
  seq: 1
}

get {
  url: {{baseUrl}}/api/v1/accounts
  body: none
  auth: none
}

assert {
  res.status: eq 200
  res.body[0].accountNumber: isString
}
```

#### 2. `Create-Account.bru` (POST Request with JSON Body)
```text
meta {
  name: Create Bank Account
  type: http
  seq: 2
}

post {
  url: {{baseUrl}}/api/v1/accounts
  body: json
  auth: none
}

headers {
  Content-Type: application/json
}

body:json {
  {
    "accountNumber": "ACC5001",
    "accountHolderName": "Diana Prince",
    "balance": 8500.00,
    "accountType": "SAVINGS"
  }
}

assert {
  res.status: eq 201
  res.body.id: isDefined
  res.body.accountHolderName: eq Diana Prince
}
```

#### 3. Bruno CLI (`bru run`)
You can run all tests in a Bruno collection from your terminal or CI/CD pipeline using the Bruno CLI:
```bash
npx @usebruno/cli run --env Local
```

---

## 9. Presentation Speaker Talking Points & Q&A Prep 🎙️

### Key Speaker Talking Points
1. **"Test the Contract First"**: Before writing code, verify the OpenAPI/Swagger specification with stakeholders.
2. **"Shift Left with API Testing"**: Explain how API testing enables QA to start testing weeks before UI screens are designed.
3. **"Git-Driven API Collections with Bruno"**: Emphasize how Bruno keeps API collections co-located in the code repository as `.bru` files, avoiding cloud sync lock-in.
4. **"Fail Fast, Fail Clear"**: Emphasize that an API should return explicit `4xx` error status codes with descriptive JSON payloads rather than silent `500` server crashes.

### Sample Audience Q&A Answers
* **Q: Why do we need API testing if we already have UI Selenium/Playwright tests?**  
  *A: UI tests are slow, brittle, and depend on browser rendering. API tests run 10x faster, cover edge cases that are difficult to trigger in the UI, and validate the actual backend business logic directly.*

* **Q: Why use Bruno instead of legacy API clients?**  
  *A: Bruno is open-source, fast, and saves collections directly as `.bru` text files inside your codebase repository. You review API changes via standard Git Pull Requests.*

* **Q: What is the difference between `@WebMvcTest` and `@SpringBootTest` in Spring Boot API testing?**  
  *A: `@WebMvcTest` is a targeted unit test that only loads the Web Layer (controllers, mappers) and mocks dependencies like services using `@MockBean`. `@SpringBootTest` loads the entire application context for full end-to-end integration testing.*
