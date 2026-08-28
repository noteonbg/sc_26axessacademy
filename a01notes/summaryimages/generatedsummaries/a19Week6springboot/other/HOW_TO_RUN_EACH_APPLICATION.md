# Guide: How to Run & Demonstrate Each Spring Boot Application

This document provides step-by-step instructions for running, testing, and demonstrating each of the **5 standalone Maven Spring Boot applications** created for the **Week 6** curriculum.

All projects are configured with **Java 21 OpenJDK** and run on **separate HTTP ports** so they can be run side-by-side or individually.

---

## 📊 Summary Port Reference Matrix

| Project Directory | Concept Focus Area | Port | Run Command |
| :--- | :--- | :--- | :--- |
| **`01-banking-controller-demo`** | REST Controller, `@RequestMapping`, `@PathVariable`, `@RequestBody`, `@ControllerAdvice` | **`8081`** | `mvn spring-boot:run` |
| **`02-banking-service-demo`** | Service Layer, `@Service`, Dependency Injection (`@Autowired`), Business Rule Logic | **`8082`** | `mvn spring-boot:run` |
| **`03-banking-jpa-demo`** | Spring Data JPA, `@Entity`, `@OneToMany`, `JpaRepository`, `@Query`, Pagination, `@Transactional` | **`8083`** | `mvn spring-boot:run` |
| **`04-banking-security-demo`** | Spring Security, `SecurityFilterChain`, `BCryptPasswordEncoder`, `UserDetailsService`, Roles | **`8084`** | `mvn spring-boot:run` |
| **`banking-core-app`** | Integrated Banking Microservice (Web + JPA + Security + Swagger) | **`8080`** | `mvn spring-boot:run` |

---

## 1. Project 1: REST Controller Demo (`01-banking-controller-demo`)

### 🎯 Purpose
Demonstrates how the Web Layer handles incoming HTTP requests, extracts dynamic path variables, parses JSON request bodies, handles query parameters, and maps errors using `@ControllerAdvice`.

### 🚀 Step 1: Launch the Application
Open PowerShell or Command Prompt and run:
```bash
cd "F:\scproject\dontpostingit\Campus Content 2026\Week 6\01-banking-controller-demo"
mvn spring-boot:run
```
*App will start on port `8081`.*

### 🧪 Step 2: Test Endpoints via cURL or Postman

#### A. List All Customers (GET)
```bash
curl -X GET http://localhost:8081/api/v1/customers
```

#### B. Fetch Single Customer by ID (@PathVariable)
```bash
curl -X GET http://localhost:8081/api/v1/customers/1
```

#### C. Search by Account Type (@RequestParam)
```bash
curl -X GET "http://localhost:8081/api/v1/customers/search?type=SAVINGS"
```

#### D. Create New Customer (@RequestBody)
```bash
curl -X POST http://localhost:8081/api/v1/customers \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@bank.com",
        "accountType": "SAVINGS",
        "balance": 75000.00
      }'
```

#### E. Test Global Exception Handler (@ControllerAdvice)
Request a non-existent customer ID to trigger HTTP 404 RFC 7807 `ProblemDetail`:
```bash
curl -X GET http://localhost:8081/api/v1/customers/999
```

---

## 2. Project 2: Service Layer Demo (`02-banking-service-demo`)

### 🎯 Purpose
Demonstrates how the `@Service` layer encapsulates core business calculations (quarterly interest computation, transfer validation checks) and receives dependencies via `@Autowired` Constructor Injection.

### 🚀 Step 1: Launch the Application
```bash
cd "F:\scproject\dontpostingit\Campus Content 2026\Week 6\02-banking-service-demo"
mvn spring-boot:run
```
*App will start on port `8082`.*

### 🧪 Step 2: Test Endpoints via cURL or Postman

#### A. Fetch Bank Account Details (GET)
```bash
curl -X GET http://localhost:8082/api/v1/services/accounts/ACC-101
```

#### B. Apply Quarterly Interest Payout (POST)
Triggers `InterestCalculationService` to compute 4% annual interest on savings balance:
```bash
curl -X POST http://localhost:8082/api/v1/services/accounts/ACC-101/apply-interest
```

#### C. Perform Money Transfer with Business Validation Checks (POST)
```bash
curl -X POST "http://localhost:8082/api/v1/services/accounts/transfer?from=ACC-101&to=ACC-102&amount=5000.00"
```

#### D. Test Business Exception Rule (Insufficient Funds)
Attempt to transfer more than available balance:
```bash
curl -X POST "http://localhost:8082/api/v1/services/accounts/transfer?from=ACC-101&to=ACC-102&amount=999999.00"
```

---

## 3. Project 3: Spring Data JPA Demo (`03-banking-jpa-demo`)

### 🎯 Purpose
Demonstrates ORM entity mappings (`@Entity`, `@Table`, `@OneToMany`, `@ManyToOne`), `JpaRepository` methods, JPQL vs Native SQL `@Query`, `Pageable` database pagination, and `@Transactional` dirty checking.

### 🚀 Step 1: Launch the Application
```bash
cd "F:\scproject\dontpostingit\Campus Content 2026\Week 6\03-banking-jpa-demo"
mvn spring-boot:run
```
*App will start on port `8083`.*

### 🧪 Step 2: Test Endpoints & Database Console

#### A. H2 Database Web Console
* Open Browser: `http://localhost:8083/h2-console`
* JDBC URL: `jdbc:h2:mem:bankingjpadb`
* Username: `sa`, Password: *blank*

#### B. Fetch All JPA Customers with Accounts (GET)
```bash
curl -X GET http://localhost:8083/api/v1/jpa/customers
```

#### C. Database Pagination & Sorting (GET)
```bash
curl -X GET "http://localhost:8083/api/v1/jpa/customers/paged?page=0&size=2&sortBy=id"
```

#### D. Automatic Dirty Checking via @Transactional (PUT)
Updates customer email in DB without calling repository `save()`:
```bash
curl -X PUT "http://localhost:8083/api/v1/jpa/customers/1/email?email=sandra.updated@bank.com"
```

#### E. Transactional Fund Transfer (POST)
```bash
curl -X POST "http://localhost:8083/api/v1/jpa/customers/transfer?sourceId=1&targetId=2&amount=2500.00"
```

---

## 4. Project 4: Spring Security Demo (`04-banking-security-demo`)

### 🎯 Purpose
Demonstrates Spring Security architecture, `SecurityFilterChain` path authorization (`hasRole`), BCrypt password hashing, and loading security credentials dynamically from database tables using `UserDetailsService`.

### 🚀 Step 1: Launch the Application
```bash
cd "F:\scproject\dontpostingit\Campus Content 2026\Week 6\04-banking-security-demo"
mvn spring-boot:run
```
*App will start on port `8084`.*

### 🧪 Step 2: Test Security Role Matrix

#### A. Public Endpoint (No Authentication Required)
```bash
curl -X GET http://localhost:8084/api/v1/public/info
```

#### B. Customer Endpoint (Requires Customer, Teller, or Admin Role)
```bash
# Allowed for customer_alice
curl -X GET http://localhost:8084/api/v1/customer/balance \
  -u customer_alice:Pass123!
```

#### C. Teller Endpoint (Requires Teller or Admin Role)
```bash
# Denied (403 Forbidden) for customer_alice:
curl -X GET http://localhost:8084/api/v1/teller/daily-summary \
  -u customer_alice:Pass123!

# Allowed (200 OK) for teller_bob:
curl -X GET http://localhost:8084/api/v1/teller/daily-summary \
  -u teller_bob:TellerPass2026!
```

#### D. Admin Endpoint (Requires Admin Role Only)
```bash
# Denied (403 Forbidden) for teller_bob:
curl -X GET http://localhost:8084/api/v1/admin/audit-logs \
  -u teller_bob:TellerPass2026!

# Allowed (200 OK) for admin_carol:
curl -X GET http://localhost:8084/api/v1/admin/audit-logs \
  -u admin_carol:AdminPass2026!
```

---

## 5. Main Integrated Project: `banking-core-app`

### 🎯 Purpose
Complete integrated microservice combining Web REST Controllers, Services, Spring Data JPA, Spring Security, XML Statements, and Swagger UI.

### 🚀 Step 1: Launch the Application
```bash
cd "F:\scproject\dontpostingit\Campus Content 2026\Week 6\banking-core-app"
mvn spring-boot:run
```
*App will start on port `8080`.*

### 🧪 Step 2: Access Live Interfaces
* **Interactive Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
* **XML Statement Payload**: `http://localhost:8080/api/v1/xml/statement`
* **H2 Database GUI**: `http://localhost:8080/h2-console` (`JDBC URL`: `jdbc:h2:mem:bankingdb`)

---

## 🖥️ Running Projects from IDE (IntelliJ IDEA / Eclipse / VS Code)

1. Open the target project directory (e.g. `01-banking-controller-demo`) in your IDE.
2. Locate the Application entry class:
   - `01-banking-controller-demo` -> `ControllerDemoApplication.java`
   - `02-banking-service-demo` -> `ServiceDemoApplication.java`
   - `03-banking-jpa-demo` -> `JpaDemoApplication.java`
   - `04-banking-security-demo` -> `SecurityDemoApplication.java`
   - `banking-core-app` -> `BankingApplication.java`
3. Right-click the class file and click **Run** (or **Debug**).
