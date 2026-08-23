# Core Banking Application: Execution, API Access & Debugging Guide

This guide provides step-by-step instructions for running the **Java 21 OpenJDK Maven Core Banking Microservice** (`banking-core-app`), accessing live REST and XML endpoints, and troubleshooting/debugging common issues.

---

## 📋 Table of Contents
1. [Prerequisites & Environment Setup](#1-prerequisites--environment-setup)
2. [How to Run the Application](#2-how-to-run-the-application)
3. [Accessing Live Endpoints & Swagger UI](#3-accessing-live-endpoints--swagger-ui)
4. [Authentication & Authorization Credentials](#4-authentication--authorization-credentials)
5. [cURL & Postman API Request Examples](#5-curl--postman-api-request-examples)
6. [Comprehensive Troubleshooting & Debugging Guide](#6-comprehensive-troubleshooting--debugging-guide)
   - [Port 8080 Conflict](#issue-1-port-8080-already-in-use)
   - [Java JDK 21 Mismatch](#issue-2-java-jdk-21-version-mismatch)
   - [Database Connection Failure (PostgreSQL vs H2)](#issue-3-database-connection-failure)
   - [401 Unauthorized / 403 Forbidden Security Errors](#issue-4-http-401-unauthorized--403-forbidden)
   - [IDE Breakpoint Debugging (IntelliJ, Eclipse, VS Code)](#issue-5-ide-debugging-setup)
   - [Enabling Verbose & Security Debug Logs](#issue-6-enabling-verbose-debug-logs)

---

## 1. Prerequisites & Environment Setup

Ensure the following software tools are installed on your machine:
* **Java Development Kit (JDK)**: OpenJDK 21 LTS (`java -version` should show `21.0.11` or higher).
* **Apache Maven**: Version 3.8+ or 3.9+ (`mvn -version`).
* **IDE**: IntelliJ IDEA, Eclipse, or VS Code with Java Extension Pack.
* **API Client**: Postman, cURL, or Web Browser.

---

## 2. How to Run the Application

Navigate to the project root directory in your terminal:
```bash
cd "F:\scproject\dontpostingit\Campus Content 2026\Week 6\banking-core-app"
```

### Option A: Run via Maven Plugin (Recommended for Development)
```bash
mvn spring-boot:run
```

### Option B: Build Package and Run Executable JAR
```bash
# Compile and create executable JAR
mvn clean package

# Run the packaged JAR file
java -jar target/banking-core-app-1.0.0-SNAPSHOT.jar
```

### Option C: Run from IDE
1. Open the project folder `banking-core-app` in IntelliJ IDEA or Eclipse.
2. Locate `src/main/java/com/standardchartered/banking/BankingApplication.java`.
3. Right-click `BankingApplication.java` and select **Run 'BankingApplication'** (or **Debug 'BankingApplication'**).

---

## 3. Accessing Live Endpoints & Swagger UI

Once launched, the embedded Tomcat server starts on port `8080`.

| Feature / Interface | Live Access URL | Description |
| :--- | :--- | :--- |
| **Interactive Swagger UI** | `http://localhost:8080/swagger-ui/index.html` | Visual OpenAPI 3.0 documentation to test endpoints directly in the browser. |
| **OpenAPI Raw Spec** | `http://localhost:8080/v3/api-docs` | JSON specification of all REST endpoints. |
| **H2 In-Memory DB Console** | `http://localhost:8080/h2-console` | Browser database GUI (`JDBC URL`: `jdbc:h2:mem:bankingdb`, Username: `sa`, Password: *blank*). |
| **XML Integration Endpoint** | `http://localhost:8080/api/v1/xml/statement` | Returns XML statement formatted with `scb:` and `amazon:` namespaces. |

---

## 4. Authentication & Authorization Credentials

The application uses **Spring Security** with **BCrypt** password encoding. Seeded test accounts:

| Username / Email | Password | Role / Authority | Permitted Endpoints |
| :--- | :--- | :--- | :--- |
| `admin_sarah@bank.com` | `AdminPass2026!` | `ROLE_ADMIN` | All endpoints (`/customers/**`, `/transfers/**`, `/xml/**`) |
| `teller_joe@bank.com` | `TellerPass2026!` | `ROLE_TELLER` | Customer lookups, Account queries, Fund transfers (`/transfers/**`) |
| `sandra.rogers@bank.com` | `Pass123!` | `ROLE_CUSTOMER` | Customer profile lookups (`/customers/**`) |

---

## 5. cURL & Postman API Request Examples

### 1. List All Bank Customers (GET)
```bash
curl -X GET http://localhost:8080/api/v1/customers \
  -u admin_sarah@bank.com:AdminPass2026!
```

### 2. Fetch Customers with Pagination & Sorting (GET)
```bash
curl -X GET "http://localhost:8080/api/v1/customers/paged?page=0&size=5&sortBy=firstName" \
  -u admin_sarah@bank.com:AdminPass2026!
```

### 3. Fetch Single Customer Details (GET)
```bash
curl -X GET http://localhost:8080/api/v1/customers/1 \
  -u teller_joe@bank.com:TellerPass2026!
```

### 4. Fetch Customer's Accounts (1:N Association) (GET)
```bash
curl -X GET http://localhost:8080/api/v1/customers/1/accounts \
  -u teller_joe@bank.com:TellerPass2026!
```

### 5. Create a New Customer (POST)
```bash
curl -X POST http://localhost:8080/api/v1/customers \
  -u admin_sarah@bank.com:AdminPass2026! \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Michael",
        "lastName": "Scott",
        "email": "michael.scott@bank.com",
        "phone": "555-0199",
        "status": "ACTIVE"
      }'
```

### 6. Execute Atomic Fund Transfer (POST)
```bash
curl -X POST http://localhost:8080/api/v1/transfers \
  -u teller_joe@bank.com:TellerPass2026! \
  -H "Content-Type: application/json" \
  -d '{
        "sourceAccountId": 1,
        "targetAccountId": 2,
        "amount": 500.00
      }'
```

### 7. Fetch XML Statement with Namespaces (GET)
```bash
curl -X GET http://localhost:8080/api/v1/xml/statement
```

---

## 6. Comprehensive Troubleshooting & Debugging Guide

### Issue 1: Port 8080 Already in Use
**Symptom**: Application fails to start with exception:
`WebServerException: Port 8080 was already in use.`

**Root Cause**: Another application (Tomcat, Docker, or previous Java instance) is occupying port 8080.

**Fix Option A: Terminate the Process on Port 8080 (Windows)**
```powershell
# 1. Find process ID (PID) using port 8080
netstat -ano | findstr :8080

# 2. Kill process by PID (replace <PID> with number from step 1)
taskkill /F /PID <PID>
```

**Fix Option B: Change Application Port in `application.properties`**
Edit `src/main/resources/application.properties`:
```properties
server.port=8081
```

---

### Issue 2: Java JDK 21 Version Mismatch
**Symptom**: `UnsupportedClassVersionError: ... has been compiled by a more recent version of the Java Runtime (class file version 65.0)`.

**Root Cause**: Maven or system terminal is configured to an older Java version (e.g. JDK 8 or JDK 11) while `pom.xml` targets Java 21.

**Fix**:
1. Check configured Java version:
   ```bash
   java -version
   mvn -version
   ```
2. Ensure your `JAVA_HOME` environment variable points to OpenJDK 21:
   ```cmd
   set JAVA_HOME=F:\software\java-21-openjdk-21.0.11.0.10-1.win.jdk.x86_64
   set PATH=%JAVA_HOME%\bin;%PATH%
   ```

---

### Issue 3: Database Connection Failure
**Symptom**: `PSQLException: Connection to localhost:5432 refused` or `Cannot create PoolableConnectionFactory`.

**Root Cause**: Application is set to connect to PostgreSQL, but PostgreSQL server is not running or credentials are wrong.

**Fix**:
1. By default, `application.properties` uses **H2 In-Memory Database** which requires zero setup and works instantly.
2. To use **PostgreSQL**, make sure PostgreSQL service is running and update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/CoreBankingDB
   spring.datasource.username=postgres
   spring.datasource.password=SecureBankPass2026!
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   ```

---

### Issue 4: HTTP 401 Unauthorized / 403 Forbidden
**Symptom**: Requests return `401 Unauthorized` or `403 Forbidden` response.

**Root Cause**: Missing or invalid HTTP Basic Authentication header, or user role lacks permission for target URL path.

**Fix**:
1. Verify credentials match the table in Section 4.
2. In Postman: Go to **Authorization** tab -> Type: **Basic Auth** -> Enter Username & Password.
3. For `/api/v1/transfers`, ensure user has `ROLE_TELLER` or `ROLE_ADMIN` (`teller_joe@bank.com` or `admin_sarah@bank.com`).

---

### Issue 5: IDE Debugging Setup

#### Setting Breakpoints & Running Debug Mode in IntelliJ IDEA / Eclipse
1. Open any class file (e.g., `FundTransferService.java` or `CustomerController.java`).
2. Click the gutter next to a line of code (e.g., inside `executeTransfer(...)`) to place a red breakpoint.
3. Right-click `BankingApplication.java` -> Select **Debug 'BankingApplication'**.
4. Trigger the endpoint via Postman or cURL. The IDE execution will pause at your breakpoint, allowing you to inspect variable values (`source`, `target`, `transferAmount`).

#### Remote JPDA Debugging Command (Terminal)
If running outside an IDE, pass JPDA remote debug flags:
```bash
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target/banking-core-app-1.0.0-SNAPSHOT.jar
```
Then attach your IDE debugger to `localhost:5005`.

---

### Issue 6: Enabling Verbose & Security Debug Logs

To inspect SQL statements, transaction boundaries, or security filter evaluation in detail, add the following lines to `src/main/resources/application.properties`:

```properties
# Enable SQL statement logging with parameter values
spring.jpa.show-sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# Enable Spring Security Filter Chain debugging logs
logging.level.org.springframework.security=DEBUG

# Enable Banking Application package debug logs
logging.level.com.standardchartered.banking=DEBUG
```
