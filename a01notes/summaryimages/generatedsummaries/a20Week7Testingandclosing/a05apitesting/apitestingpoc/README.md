# Spring Boot REST API Testing Proof of Concept (No Database)

A clean, beginner-friendly Spring Boot project demonstrating **REST API Controller implementation** and **API Testing (Unit & Integration Testing)** in Java 21 without any external database dependencies.

---

## 📌 Project Overview

* **Framework**: Spring Boot 3.3.3
* **Java Version**: 21
* **Database**: **None** (Uses pure in-memory `ConcurrentHashMap` and `AtomicLong`)
* **Testing Frameworks**: JUnit 5, Spring Boot Test (`@WebMvcTest`, `@SpringBootTest`), MockMvc, Mockito, JsonPath
* **API Client**: **Bruno** 🐶 (Open-source, Git-friendly API collection co-located in the repository)

---

## 📁 Project Directory Layout

```
apitestingpoc/
├── pom.xml
├── README.md
├── bruno-collection/                                      <-- Bruno API Testing Collection
│   ├── bruno.json
│   ├── environments/
│   │   └── Local.bru                                      <-- Environment variables (baseUrl)
│   ├── Get-All-Accounts.bru                               <-- GET /api/v1/accounts
│   ├── Get-Account-By-ID.bru                              <-- GET /api/v1/accounts/101
│   ├── Create-Account.bru                                 <-- POST /api/v1/accounts
│   ├── Update-Account.bru                                 <-- PUT /api/v1/accounts/101
│   └── Delete-Account.bru                                 <-- DELETE /api/v1/accounts/101
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           example/
    │           apitestingpoc/
    │               ├── ApiTestingPocApplication.java       <-- Main Spring Boot Entry Point
    │               ├── controller/
    │               │   └── AccountController.java          <-- REST Controller (@RestController)
    │               ├── model/
    │               │   └── Account.java                    <-- Pure Java POJO Model (No DB annotations)
    │               ├── service/
    │               │   └── AccountService.java             <-- In-Memory Service (ConcurrentHashMap)
    │               └── exception/
    │                   ├── AccountNotFoundException.java   <-- Custom Exception for 404
    │                   ├── ErrorResponse.java              <-- Standardized Error JSON Model
    │                   └── GlobalExceptionHandler.java     <-- @RestControllerAdvice Exception Mapper
    └── test/
        └── java/
            └── com/
                example/
                apitestingpoc/
                    ├── controller/
                    │   └── AccountControllerUnitTest.java  <-- Controller Unit Test (@WebMvcTest + MockMvc)
                    └── integration/
                        └── AccountControllerIntegrationTest.java <-- End-to-End Test (@SpringBootTest)
```

---

## 🛠️ Step-by-Step Instructions

### Step 1: Prerequisites
Ensure Java 21+ and Apache Maven 3.9+ are installed on your machine:
```bash
java -version
mvn -version
```

### Step 2: Build and Run All Unit & Integration Tests
Execute the following command inside the `apitestingpoc` folder:
```bash
mvn clean test
```
All unit tests in `AccountControllerUnitTest` and end-to-end integration tests in `AccountControllerIntegrationTest` will run and output `BUILD SUCCESS`.

### Step 3: Run the Application Locally
To start the Spring Boot application server locally:
```bash
mvn spring-boot:run
```
The server will start on port `8080` (e.g. `http://localhost:8080/api/v1/accounts`).

---

## 🐶 Step 4: Testing APIs with Bruno

### Method A: Bruno GUI Client
1. Download and install [Bruno](https://www.usebruno.com/).
2. Open Bruno, click **Open Collection**, and select the `bruno-collection` folder in this project.
3. Select the **Local** environment from the top-right environment selector.
4. Select any request (e.g., `Get All Accounts` or `Create Bank Account`) and click **Send**.
5. Check the **Tests / Assertions** tab to see automated response validations (`res.status == 200`, `res.body.id isDefined`).

### Method B: Bruno CLI (Automated Test Execution)
Run the Bruno collection directly from your terminal:
```bash
npx @usebruno/cli run bruno-collection --env Local
```

---

## 🌐 REST API Endpoints Overview

| HTTP Method | URI Endpoint | Description | Expected HTTP Status |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/accounts` | Retrieve all accounts | `200 OK` |
| `GET` | `/api/v1/accounts/{id}` | Retrieve account by ID | `200 OK` (or `404 Not Found`) |
| `POST` | `/api/v1/accounts` | Create a new bank account | `201 Created` (or `400 Bad Request`) |
| `PUT` | `/api/v1/accounts/{id}` | Update an existing account | `200 OK` (or `404 Not Found`) |
| `DELETE` | `/api/v1/accounts/{id}` | Delete an account by ID | `204 No Content` (or `404 Not Found`) |

### Sample JSON Request Payload (`POST /api/v1/accounts`)
```json
{
  "accountNumber": "ACC1003",
  "accountHolderName": "Charlie Brown",
  "balance": 5000.00,
  "accountType": "SAVINGS"
}
```

### Sample JSON Response Payload (`201 Created`)
```json
{
  "id": 101,
  "accountNumber": "ACC1003",
  "accountHolderName": "Charlie Brown",
  "balance": 5000.00,
  "accountType": "SAVINGS"
}
```

### Sample Error JSON Response Payload (`404 Not Found`)
```json
{
  "timestamp": "2026-09-02T20:30:00.123456",
  "status": 404,
  "error": "Resource Not Found",
  "message": "Account not found with ID: 999"
}
```

---

## 💡 Syntax & Key Concepts Explained

### 1. Spring REST Controller Annotations
* `@RestController`: Combines `@Controller` and `@ResponseBody`. Tells Spring to serialize return values directly into HTTP response bodies as JSON.
* `@RequestMapping("/api/v1/accounts")`: Sets base URL path for all endpoints in the controller.
* `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: Specialized shortcut annotations mapping HTTP methods (GET, POST, PUT, DELETE) to specific handler methods.
* `@PathVariable`: Extracts dynamic values from URI path templates (e.g. `/api/v1/accounts/{id}`).
* `@RequestBody`: Binds incoming HTTP JSON body directly to a Java object instance.
* `@RestControllerAdvice`: Intercepts exceptions thrown across controllers and formats them into custom HTTP response JSON structures.

### 2. Spring API Testing Annotations
* `@WebMvcTest(AccountController.class)`:
  - Unit testing annotation for Spring Web MVC controllers.
  - Loads ONLY web layer components. Does NOT load full application context or start a server.
  - Runs in milliseconds.
* `@SpringBootTest`:
  - Integration testing annotation that loads the FULL Spring Application Context.
  - Tests end-to-end interaction between Controller, Service, and In-Memory Data Store.
* `@MockBean`:
  - Injects a Mockito mock of `AccountService` into the Spring container for `@WebMvcTest`.
* `MockMvc`:
  - Provides a powerful API for performing simulated HTTP requests (`get()`, `post()`, `put()`, `delete()`) and verifying responses (`andExpect(status().isOk())`, `andExpect(jsonPath("$.field").value(...))`).
