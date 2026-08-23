# Standard Chartered Core Banking System (Week 6 Topic-Wise Reference Guide)

Welcome to the **Core Banking System** reference applications and comprehensive guide based on **Week 6** curriculum topics (Apache Maven, XML Data Integration, Spring Boot REST Architecture, Spring Data JPA, and Spring Security).

All projects have been created using **Java 21 OpenJDK** and **Apache Maven 3.9+**, featuring **line-by-line syntax commentary** inside the source code to demonstrate each Spring Boot concept one by one.

---

## 📂 Topic-Wise Project Directory

Each Spring Boot concept is built as a **separate, standalone executable Maven project** so you can demonstrate and study them individually:

```text
F:\scproject\dontpostingit\Campus Content 2026\Week 6\
├── 01-banking-controller-demo/    # Focus: @RestController, Mappings, @PathVariable, @RequestBody, @ControllerAdvice
├── 02-banking-service-demo/       # Focus: @Service, Dependency Injection (@Autowired), Business Rule Calculations
├── 03-banking-jpa-demo/          # Focus: @Entity, @Table, @OneToMany, JpaRepository, JPQL/Native @Query, @Transactional
├── 04-banking-security-demo/     # Focus: SecurityFilterChain, BCryptPasswordEncoder, UserDetailsService DB security
└── banking-core-app/              # Complete integrated banking application (untouched)
```

---

## 🎯 Individual Topic-Wise Projects Overview

### Project 1: `01-banking-controller-demo` (Port 8081)
* **Location**: `F:\scproject\dontpostingit\Campus Content 2026\Week 6\01-banking-controller-demo`
* **Focus Area**: Web Layer, REST Controllers, Request Mappings, and Global Error Contracts.
* **Key Syntax Demonstrated**:
  * `@RestController` & `@RequestMapping("/api/v1/customers")`
  * `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
  * `@PathVariable("id")` (Dynamic URL lookup)
  * `@RequestBody` (JSON body deserialization)
  * `@RequestParam` (Query string parameters)
  * `@ControllerAdvice` & `@ExceptionHandler` (RFC 7807 `ProblemDetail` error payload)
* **Run Command**:
  ```bash
  cd "F:\scproject\dontpostingit\Campus Content 2026\Week 6\01-banking-controller-demo"
  mvn spring-boot:run
  ```

---

### Project 2: `02-banking-service-demo` (Port 8082)
* **Location**: `F:\scproject\dontpostingit\Campus Content 2026\Week 6\02-banking-service-demo`
* **Focus Area**: Business Layer, IoC Container, Dependency Injection (Constructor vs Field Injection), and Rule Checks.
* **Key Syntax Demonstrated**:
  * `@Service` (Spring Bean registration for business logic)
  * `@Autowired` (Constructor Injection pattern recommended for immutability and unit testing)
  * Separation of Concerns (Decoupling HTTP request processing from quarterly interest calculations and balance validations)
* **Run Command**:
  ```bash
  cd "F:\scproject\dontpostingit\Campus Content 2026\Week 6\02-banking-service-demo"
  mvn spring-boot:run
  ```

---

### Project 3: `03-banking-jpa-demo` (Port 8083)
* **Location**: `F:\scproject\dontpostingit\Campus Content 2026\Week 6\03-banking-jpa-demo`
* **Focus Area**: Data Persistence, Object-Relational Mapping (ORM), Query Execution, and Transactions.
* **Key Syntax Demonstrated**:
  * `@Entity` & `@Table(name = "customers")`
  * `@Id` & `@GeneratedValue(strategy = GenerationType.IDENTITY)`
  * `@Column(name = "first_name", nullable = false)`
  * `@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)`
  * `@ManyToOne(fetch = FetchType.LAZY)` & `@JoinColumn(name = "customer_id")`
  * `JpaRepository<Entity, Long>` & Derived finder methods (`findByEmail`)
  * Custom `@Query` (JPQL `SELECT c FROM BankCustomerJpaEntity c` vs Native SQL `SELECT * FROM customers`)
  * `PageRequest.of(page, size, Sort.by("id").descending())` database pagination
  * `@Transactional` ACID boundaries and automatic dirty checking on managed entities
* **Run Command**:
  ```bash
  cd "F:\scproject\dontpostingit\Campus Content 2026\Week 6\03-banking-jpa-demo"
  mvn spring-boot:run
  ```

---

### Project 4: `04-banking-security-demo` (Port 8084)
* **Location**: `F:\scproject\dontpostingit\Campus Content 2026\Week 6\04-banking-security-demo`
* **Focus Area**: API Security, Authentication, Role-Based Access Control (RBAC), and Password Encoding.
* **Key Syntax Demonstrated**:
  * `@EnableWebSecurity` & `@Configuration`
  * `SecurityFilterChain` bean definition using Spring Security 6+ Lambda DSL syntax
  * `requestMatchers("/api/v1/admin/**").hasRole("ADMIN")` & `hasAnyRole("TELLER", "ADMIN")`
  * `BCryptPasswordEncoder` password hashing and verification
  * Custom `UserDetailsService` and `UserDetails` implementation loading user roles dynamically from database tables
* **Run Command**:
  ```bash
  cd "F:\scproject\dontpostingit\Campus Content 2026\Week 6\04-banking-security-demo"
  mvn spring-boot:run
  ```

---

## 🛠️ Software Requirements

* **Java JDK**: OpenJDK 21 LTS (`java -version` -> `21.0.11`)
* **Apache Maven**: Version 3.9+ (`mvn -version`)
* **PostgreSQL / H2**: Pre-configured with H2 in-memory DB for instant execution, with PostgreSQL configuration included.
