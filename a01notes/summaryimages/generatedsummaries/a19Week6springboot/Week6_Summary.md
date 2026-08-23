# Week 6: Comprehensive Architectural & Technical Summary

This document provides a detailed, simplified, and numbered summary of all concepts covered in **Week 6** (Apache Maven, XML & Data Integration, Spring Boot REST Architecture, Spring Data JPA, and Spring Security). Each concept is explained simply alongside a concrete **Real-Life Enterprise Banking System Example**.

---

## Key Concepts & Real-Life Project Examples

### 1. Automated Project Build & Dependency Management (Apache Maven)
- **Concept**: Maven standardizes project directory structures (`src/main/java`, `src/main/resources`, `pom.xml`), manages external software dependencies, and automates compiling, testing, and packaging code into runnable JAR/WAR files.
- **Real-Life Banking Example**: A banking backend team uses `pom.xml` to declare dependencies like `spring-boot-starter-web`. The CI/CD build pipeline executes `mvn package` to automatically compile Java source files, run unit tests, and produce `banking-service-1.0.jar` for server deployment.

### 2. Data Structuring & Cross-System Data Exchange (XML & Namespaces)
- **Concept**: XML provides self-descriptive, platform-independent data formatting. XML Namespaces (`xmlns:prefix`) resolve element name collisions when combining schemas from different partner systems.
- **Real-Life Banking Example**: When the bank integrates with Amazon to offer shopping discounts, both systems use a `<customer>` tag. XML namespaces differentiate bank customer data (`<scb:customer>`) from merchant customer data (`<amazon:customer>`) within the same integration payload without syntax conflicts.

### 3. RESTful Service Orchestration & Controllers (`@RestController`)
- **Concept**: `@RestController` combines `@Controller` and `@ResponseBody`. It exposes Web API endpoints and automatically serializes Java domain objects into JSON HTTP responses for front-end consumption.
- **Real-Life Banking Example**: When a user opens their mobile app, it sends an HTTP request `GET /api/customers/101`. The `@RestController` processes the request and returns a JSON payload: `{"id": 101, "firstName": "Alice", "email": "alice@example.com"}`.

### 4. Dynamic URL & Payload Data Handling (`@PathVariable` & `@RequestBody`)
- **Concept**: `@PathVariable` extracts dynamic parameters embedded directly in the request URL path, while `@RequestBody` deserializes incoming JSON body payloads into Java domain objects.
- **Real-Life Banking Example**: 
  - `GET /api/customers/{id}` uses `@PathVariable` to extract the ID `101` from `/api/customers/101`.
  - `POST /api/customers` uses `@RequestBody` to parse incoming JSON `{ "firstName": "Bob", "email": "bob@bank.com" }` to register a new user.

### 5. Centralized Global Exception Handling (`@ControllerAdvice` & `@ExceptionHandler`)
- **Concept**: `@ControllerAdvice` provides global error handling across controllers, catching thrown backend exceptions and mapping them to standardized HTTP error responses (e.g., HTTP 404/400).
- **Real-Life Banking Example**: If a user attempts to view a deleted account ID `999`, the backend throws a `CustomerNotFoundException`. The `@ControllerAdvice` handler intercepts this and responds with HTTP status 404 and JSON `{"status": 404, "title": "Record not found"}` instead of crashing or exposing raw stack traces.

### 6. Interactive API Documentation (Swagger / OpenAPI Integration)
- **Concept**: Integrating `springdoc-openapi` automatically generates interactive UI documentation for testing and visualizing REST APIs directly in the browser.
- **Real-Life Banking Example**: Frontend UI developers open `http://localhost:8080/swagger-ui/index.html` to review all available endpoints (`POST /api/customers`, `GET /api/customers/{id}/accounts`), inspect expected request schemas, and trigger live test API calls.

### 7. 3-Tier Enterprise Layered Architecture
- **Concept**: Separates concerns into Web Layer (Controllers/HTTP), Service Layer (Business rules/validation), and Repository/Data Access Layer (Database persistence).
- **Real-Life Banking Example**: 
  - *Web Layer (`CustomerController`)*: Receives HTTP request `POST /api/transfer`.
  - *Service Layer (`CustomerService`)*: Validates account status, checks daily transfer limits, and calculates transaction fees.
  - *Repository Layer (`CustomerRepository`)*: Executes the database queries to update account balances.

### 8. Object-Relational Mapping (ORM) & Java Persistence API (JPA)
- **Concept**: ORM maps Java object classes (`@Entity`) to SQL database tables, enabling database operations without writing repetitive raw SQL queries. JPA is the standard Java specification for ORM.
- **Real-Life Banking Example**: Instead of manually writing `INSERT INTO customers (id, first_name, email) VALUES (...)`, the developer annotates a Java `Customer` class with `@Entity` and saves it directly via `customerRepository.save(customer)`.

### 9. JPA Entity Lifecycle & Persistence Context
- **Concept**: Entities transition between states: **Transient** (new in-memory object), **Managed** (tracked in `EntityManager` persistence context), **Detached**, and **Removed**. The persistence context acts as an in-memory cache that automatically synchronizes changes to the DB upon transaction commit.
- **Real-Life Banking Example**: When updating a customer's phone number, calling `repository.findById(101)` returns a **Managed** entity. Simply modifying `customer.setPhone("555-0199")` inside an active transaction automatically triggers an SQL `UPDATE` statement when the transaction completes.

### 10. Data Access Repositories & Custom Queries (`JpaRepository` & `@Query`)
- **Concept**: `JpaRepository` provides built-in CRUD operations out of the box. Custom business queries can be created using JPQL (operating on Java entity fields) or Native SQL.
- **Real-Life Banking Example**: Simple lookups use `findByEmail(email)`. For custom reporting, `@Query("SELECT c FROM Customer c WHERE c.accountBalance > :minAmount")` executes a JPQL query to retrieve all high-net-worth clients for wealth management campaigns.

### 11. Entity Associations & Cascading (`@OneToMany`, `@ManyToOne`, `CascadeType`)
- **Concept**: Maps relational foreign keys between entities (e.g., One Customer to Many Accounts). Cascading (`CascadeType.ALL`) propagates operations (insert, update, delete) from parent entities to child entities.
- **Real-Life Banking Example**: A `Customer` entity has a `@OneToMany(cascade = CascadeType.ALL)` relationship with `Account` entities. Creating a customer with both a Savings Account and Checking Account automatically inserts all associated account records into the PostgreSQL database when the customer record is saved.

### 12. Database Pagination & Sorting (`Pageable` & `PageRequest`)
- **Concept**: Splits large database query result sets into smaller pages with sorting parameters to optimize database execution speed and UI responsiveness.
- **Real-Life Banking Example**: On an online banking statement page with thousands of transactions, `PageRequest.of(0, 10, Sort.by("transactionDate").descending())` retrieves only the 10 most recent transactions for page 1 display.

### 13. ACID Transactions & Boundary Management (`@Transactional`)
- **Concept**: Groups database operations into an atomic unit of work adhering to ACID properties (Atomicity, Consistency, Isolation, Durability). If any operation fails, all previous changes in the transaction are rolled back.
- **Real-Life Banking Example**: When transferring `$500` from Account A to Account B, the system debits Account A and credits Account B. If crediting Account B fails mid-operation, `@Transactional` automatically rolls back the `$500` debit from Account A so funds are preserved.

### 14. Authentication vs. Authorization
- **Concept**: **Authentication** verifies *who* the user is (identity check), whereas **Authorization** verifies *what* permissions/roles the user has to access specific endpoints or actions.
- **Real-Life Banking Example**: 
  - *Authentication*: Logging into online banking using username `alice@example.com` and password `Password123`.
  - *Authorization*: Alice (assigned `ROLE_CUSTOMER`) can view her own balances (`GET /api/customers/101/accounts`), but receives HTTP 403 Forbidden when attempting to access `GET /api/admin/all-users`.

### 15. Security Filter Chain & Password Hashing (`SecurityFilterChain` & `BCryptPasswordEncoder`)
- **Concept**: `SecurityFilterChain` defines security rules intercepting incoming requests. `BCryptPasswordEncoder` securely hashes passwords prior to database storage or verification.
- **Real-Life Banking Example**: `SecurityFilterChain` enforces that `/api/customer/**` requires `ADMIN` role and `/api/account/**` requires `USER` role. User passwords like `"SecretPass123"` are encrypted into `$2a$10$e7...` using `BCryptPasswordEncoder` to protect credentials against data breaches.

### 16. Database-Driven Security & User Details (`UserDetailsService` & `UserDetails`)
- **Concept**: Implementing `UserDetailsService` and `UserDetails` fetches user accounts, credentials, and granted authorities dynamically from PostgreSQL database tables during login instead of using hardcoded in-memory users.
- **Real-Life Banking Example**: When a bank teller logs in, Spring Security invokes `CustomerService.loadUserByUsername(email)`. This executes `findByEmail()` against the database, wraps the customer record into a `UserDetails` object containing granted roles (`ROLE_TELLER`), and validates the session credentials.

---
*Generated based on materials in `F:\scproject\dontpostingit\Campus Content 2026\Week 6` (MAVEN.pdf, XML.pdf, Spring Boot.pdf, SpringSecurity_framework.pdf).*
