# Week 6: Financial Domain Demonstrable Examples

This document presents small, self-contained, demonstrable examples in the **financial domain** (Retail Banking, Wire Transfers, Account Management, and Payment Security) for every topic covered across the **Week 6** curriculum PDFs:
1. `MAVEN.pdf`
2. `XML.pdf`
3. `Spring Boot.pdf`
4. `SpringSecurity_framework.pdf`

---

# Section 1: Apache Maven Examples (`MAVEN.pdf`)

### 1. Standard Maven Project Directory Structure
- **Topic**: Maven standard directory layout (`src/main/java`, `src/main/resources`, `src/test/java`, `pom.xml`).
- **Financial Scenario**: Standard directory structure for an enterprise Banking Payment Microservice (`payment-service`).
- **Demonstrable Layout**:
```text
payment-service/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── standardchartered/
    │   │           └── banking/
    │   │               ├── PaymentServiceApplication.java
    │   │               ├── controller/
    │   │               │   └── PaymentController.java
    │   │               └── model/
    │   │                   └── Transaction.java
    │   └── resources/
    │       ├── application.properties
    │       └── banner.txt
    └── test/
        ├── java/
        │   └── com/
        │       └── standardchartered/
        │           └── banking/
        │               └── PaymentServiceApplicationTests.java
        └── resources/
            └── application-test.properties
```

---

### 2. Maven Archetype Generation (`mvn archetype:generate`)
- **Topic**: Creating a new Maven project using archetype parameters (`groupId`, `artifactId`).
- **Financial Scenario**: Initializing a Fund Transfer microservice for cross-border wire transfers.
- **Demonstrable CLI Command**:
```bash
mvn archetype:generate \
  -DgroupId=com.standardchartered.banking \
  -DartifactId=fund-transfer-service \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false
```
- **Generated `pom.xml` Metadata Header**:
```xml
<groupId>com.standardchartered.banking</groupId>
<artifactId>fund-transfer-service</artifactId>
<version>1.0.0-SNAPSHOT</version>
<packaging>jar</packaging>
<name>Fund Transfer Microservice</name>
```

---

### 3. Build Lifecycle & Packaging (`mvn package`)
- **Topic**: Compiling, testing, and packaging code into an executable artifact.
- **Financial Scenario**: Automated CI/CD compilation and JAR packaging of the `wire-transfer-service` for deployment.
- **Demonstrable CLI Execution**:
```bash
# Clean previous builds, compile Java sources, execute JUnit tests, and package JAR
mvn clean package
```
- **Output Artifact**: `target/wire-transfer-service-1.0.0.jar`

---

### 4. Maven Repository Resolution (Local vs Central vs Corporate Remote)
- **Topic**: Sequence of resolving dependencies across local (`.m2`), Maven Central, and private remote corporate repositories.
- **Financial Scenario**: Configuring Maven to download external financial standards (`javamoney`) and secure corporate banking libraries from an internal Nexus repository.
- **Demonstrable `pom.xml` Snippet**:
```xml
<project>
    <!-- Corporate Remote Banking Repository -->
    <repositories>
        <repository>
            <id>scb-nexus-repo</id>
            <name>Standard Chartered Internal Repo</name>
            <url>https://nexus.internal.bank.com/repository/maven-releases/</url>
        </repository>
    </repositories>

    <dependencies>
        <!-- Java Money and Currency API Dependency -->
        <dependency>
            <groupId>org.javamoney</groupId>
            <artifactId>moneta</artifactId>
            <version>1.4.2</version>
            <type>pom</type>
        </dependency>
    </dependencies>
</project>
```

---

# Section 2: XML & Data Integration Examples (`XML.pdf`)

### 1. Data Structuring & Self-Descriptive Tags
- **Topic**: Platform-independent data structuring using custom hierarchical XML elements.
- **Financial Scenario**: Core Banking System exporting a Customer Account Statement payload.
- **Demonstrable XML (`bank_customer_statement.xml`)**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<statement>
    <bankName>Standard Chartered Bank</bankName>
    <customer>
        <customerId>SCB902341</customerId>
        <name>Sarah Jenkins</name>
        <accountType>SAVINGS</accountType>
        <accountNumber>ACC-8849-201</accountNumber>
        <balance currency="USD">145000.75</balance>
    </customer>
</statement>
```

---

### 2. XML Attributes vs XML Elements
- **Topic**: Representing metadata via attributes vs domain data via child elements.
- **Financial Scenario**: Credit Card Transaction payload showing both representation approaches.
- **Demonstrable XML Comparison**:
```xml
<!-- Approach A: Attribute-Focused (Metadata heavy) -->
<transaction id="TXN99482" type="DEBIT" status="COMPLETED" currency="USD" amount="250.00">
    <merchant>Amazon Retail</merchant>
</transaction>

<!-- Approach B: Element-Focused (Data heavy - Recommended for core data) -->
<transaction>
    <id>TXN99482</id>
    <type>DEBIT</type>
    <status>COMPLETED</status>
    <amount>
        <currency>USD</currency>
        <value>250.00</value>
    </amount>
    <merchant>Amazon Retail</merchant>
</transaction>
```

---

### 3. XML Syntax Rules & Well-Formedness
- **Topic**: Rules for well-formed XML (Single root element, case sensitivity, proper nesting, quoted attributes).
- **Financial Scenario**: ISO 20022 Financial Wire Transfer XML payload.
- **Demonstrable Valid XML**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<wireTransfer>
    <transferId>TRX-55421</transferId>
    <senderAccount>ACC-101-SAVINGS</senderAccount>
    <receiverAccount>ACC-302-CHECKING</receiverAccount>
    <amount currency="EUR">5000.00</amount>
</wireTransfer>
```
- **Syntax Error Breakdown**:
```xml
<!-- INVALID XML: Mismatched tag case and missing root closing tag -->
<wireTransfer>
    <Amount currency="EUR">5000.00</amount>  <!-- Syntax Error: Case sensitivity mismatch (<Amount> vs </amount>) -->
<!-- Syntax Error: Missing closing </wireTransfer> tag -->
```

---

### 4. XML Namespaces (`xmlns:prefix`)
- **Topic**: Resolving element name collisions when integrating XML schemas from disparate applications.
- **Financial Scenario**: Merging Bank Customer profile schema (`scb:customer`) with E-commerce Merchant order schema (`amazon:customer`) in a discount partnership payload.
- **Demonstrable XML (`partner_discount_integration.xml`)**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<integrationPayload 
    xmlns:scb="http://www.sc.com/banking/customers" 
    xmlns:amazon="http://www.amazon.com/shopping/orders">

    <!-- Core Bank Customer Profile -->
    <scb:customer>
        <scb:customerId>SCB132</scb:customerId>
        <scb:firstname>Sandra</scb:firstname>
        <scb:lastname>Rogers</scb:lastname>
        <scb:accountType>Savings</scb:accountType>
        <scb:balance>100000.00</scb:balance>
    </scb:customer>

    <!-- Merchant Partner Order Info -->
    <amazon:customer>
        <amazon:customerId>SCB132</amazon:customerId>
        <amazon:items>Smart Refrigerator</amazon:items>
        <amazon:amount>50000.00</amazon:amount>
    </amazon:customer>

</integrationPayload>
```

---

# Section 3: Spring Boot REST & Data Architecture Examples (`Spring Boot.pdf`)

### 1. Spring Boot Starters & Dependency Management (`pom.xml`)
- **Topic**: Bill of Materials (BOM) and starter dependencies (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-actuator`).
- **Financial Scenario**: Maven configuration for a Retail Banking Microservice backend.
- **Demonstrable `pom.xml`**:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
    </parent>

    <groupId>com.standardchartered.banking</groupId>
    <artifactId>retail-banking-service</artifactId>
    <version>1.0.0-SNAPSHOT</version>

    <dependencies>
        <!-- REST API Web Starter (Embedded Tomcat + Jackson) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- ORM & Database Access Starter -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <!-- PostgreSQL Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
        </dependency>
        <!-- Actuator for Health Metrics -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>
</project>
```

---

### 2. Main Application Entry Point (`@SpringBootApplication`)
- **Topic**: Main application class, component scanning, auto-configuration, and starting embedded Tomcat.
- **Financial Scenario**: Entry point for `RetailBankingApplication`.
- **Demonstrable Code (`RetailBankingApplication.java`)**:
```java
package com.standardchartered.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RetailBankingApplication {
    public static void main(String[] args) {
        // Starts Spring ApplicationContext, performs classpath scan, initializes Embedded Tomcat on port 8080
        SpringApplication.run(RetailBankingApplication.class, args);
    }
}
```

---

### 3. In-Memory Domain Model & Service Layer (POJO CRUD without DB)
- **Topic**: Simple 2-tier REST architecture maintaining domain entities in `ArrayList`.
- **Financial Scenario**: In-memory `BankCustomer` POJO and `InMemBankCustomerService`.
- **Demonstrable Code (`InMemBankCustomerService.java`)**:
```java
package com.standardchartered.banking.service;

import com.standardchartered.banking.model.BankCustomer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InMemBankCustomerService {
    private final List<BankCustomer> customerList = new ArrayList<>();

    public InMemBankCustomerService() {
        customerList.add(new BankCustomer(1L, "Jeff", "Bezos", "jeff@amazon.com", "555-0101", "ACTIVE", "SAVINGS"));
        customerList.add(new BankCustomer(2L, "Jack", "Ma", "jack@alibaba.com", "555-0102", "ACTIVE", "CURRENT"));
    }

    public List<BankCustomer> getAllCustomers() {
        return customerList;
    }

    public BankCustomer getCustomerById(Long id) {
        return customerList.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public BankCustomer addCustomer(BankCustomer customer) {
        customerList.add(customer);
        return customer;
    }
}
```

---

### 4. REST Controllers & HTTP Mappings (`@RestController`, `@GetMapping`, `@PostMapping`)
- **Topic**: Exposing RESTful HTTP endpoints returning serialized JSON domain objects.
- **Financial Scenario**: `BankCustomerController` exposing CRUD APIs for customer management.
- **Demonstrable Code (`BankCustomerController.java`)**:
```java
package com.standardchartered.banking.controller;

import com.standardchartered.banking.model.BankCustomer;
import com.standardchartered.banking.service.InMemBankCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class BankCustomerController {

    @Autowired
    private InMemBankCustomerService customerService;

    // GET /api/v1/customers -> Returns JSON array of all bank customers
    @GetMapping
    public List<BankCustomer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // POST /api/v1/customers -> Creates a new customer
    @PostMapping
    public BankCustomer createCustomer(@RequestBody BankCustomer customer) {
        return customerService.addCustomer(customer);
    }
}
```

---

### 5. Dynamic Routing & Payload Deserialization (`@PathVariable`, `@RequestBody`)
- **Topic**: Capturing URL path variables and deserializing HTTP JSON body content into DTO objects.
- **Financial Scenario**: `FundTransferController` processing dynamic transfer requests.
- **Demonstrable Code (`FundTransferController.java`)**:
```java
package com.standardchartered.banking.controller;

import com.standardchartered.banking.dto.TransferRequestDTO;
import com.standardchartered.banking.model.TransferReceipt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfers")
public class FundTransferController {

    // GET /api/v1/transfers/TXN-90214 -> Dynamic URL parameter lookup
    @GetMapping("/{transferRef}")
    public String getTransferDetails(@PathVariable("transferRef") String transferRef) {
        return "Receipt details for transaction reference: " + transferRef;
    }

    // POST /api/v1/transfers -> Deserializes JSON payload into TransferRequestDTO
    @PostMapping
    public String executeTransfer(@RequestBody TransferRequestDTO request) {
        return "Transferred $" + request.getAmount() + " from Account " 
                + request.getSourceAccount() + " to Account " + request.getTargetAccount();
    }
}
```

---

### 6. Global Exception Handling (`@ControllerAdvice`, `@ExceptionHandler`)
- **Topic**: Centralized exception handling intercepting backend exceptions and returning standard HTTP error contracts (`ProblemDetail`).
- **Financial Scenario**: Intercepting `InsufficientBalanceException` during payment processing.
- **Demonstrable Code (`GlobalFinancialExceptionHandler.java`)**:
```java
package com.standardchartered.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class GlobalFinancialExceptionHandler {

    @ExceptionHandler(InsufficientBalanceException.class)
    public ProblemDetail handleInsufficientBalance(InsufficientBalanceException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, 
                ex.getMessage()
        );
        problem.setTitle("Transaction Declined");
        problem.setType(URI.create("https://bank.com/errors/insufficient-funds"));
        return problem; // Returns HTTP 400 with standardized JSON payload
    }
}
```

---

### 7. Swagger / OpenAPI Integration (`springdoc-openapi-starter-webmvc-ui`)
- **Topic**: Auto-generating interactive Swagger API documentation UI.
- **Financial Scenario**: Swagger configuration and OpenAPI annotations for Bank Account APIs.
- **Demonstrable Code (`OpenApiConfig.java`)**:
```java
package com.standardchartered.banking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customBankingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Standard Chartered Core Banking API")
                        .version("1.0.0")
                        .description("Interactive REST documentation for Customers, Accounts, and Wire Transfers"));
    }
}
```
*Access UI at: `http://localhost:8080/swagger-ui/index.html`*

---

### 8. ORM & Spring Data JPA Mapping (`@Entity`, `@Table`, `JpaRepository`)
- **Topic**: Mapping Java object classes to relational DB tables without writing SQL queries.
- **Financial Scenario**: `AccountEntity` mapped to `bank_accounts` table and `AccountRepository`.
- **Demonstrable Code (`AccountEntity.java` & `AccountRepository.java`)**:
```java
package com.standardchartered.banking.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bank_accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_balance")
    private BigDecimal accountBalance;

    // Getters and Setters omitted for brevity
}

// Spring Data JPA Repository interface
package com.standardchartered.banking.repository;

import com.standardchartered.banking.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
```

---

### 9. JPA Entity Lifecycle & Persistence Context
- **Topic**: Entity states (Transient, Managed, Detached, Removed) and dirty checking in persistence context.
- **Financial Scenario**: Modifying credit limits on a managed customer entity.
- **Demonstrable Code (`CustomerManagementService.java`)**:
```java
package com.standardchartered.banking.service;

import com.standardchartered.banking.entity.CustomerEntity;
import com.standardchartered.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CustomerManagementService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public void updateCustomerCreditLimit(Long customerId, BigDecimal newLimit) {
        // 1. Transient -> Managed state when fetched via Repository/EntityManager
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // 2. Modifying Managed Entity fields automatically triggers an SQL UPDATE upon transaction commit
        customer.setCreditLimit(newLimit); // Dirty checking automatically syncs change to Postgres
    }
}
```

---

### 10. Database Connection Properties (`application.properties`)
- **Topic**: Datasource connection settings, Hikari pool configuration, and JPA dialect settings.
- **Financial Scenario**: PostgreSQL connection properties for core banking database.
- **Demonstrable `application.properties`**:
```properties
# PostgreSQL Database Connection Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/CoreBankingDB
spring.datasource.username=postgres
spring.datasource.password=SecureBankPass2026!
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA / Hibernate Properties
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

---

### 11. Entity Associations & Cascading (`@OneToMany`, `@ManyToOne`, `CascadeType.ALL`)
- **Topic**: Relational Foreign Key mapping between parent and child entities with cascading operations.
- **Financial Scenario**: `CustomerEntity` (One) holding multiple `AccountEntity` records (Many).
- **Demonstrable Code (`CustomerEntity.java`)**:
```java
package com.standardchartered.banking.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String email;

    // One Customer to Many Accounts relationship with cascading persist/delete
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountEntity> accounts = new ArrayList<>();

    public void addAccount(AccountEntity account) {
        accounts.add(account);
        account.setCustomer(this);
    }
}
```

---

### 12. Custom Queries with JPQL & Native SQL (`@Query`)
- **Topic**: Writing custom database queries using JPQL (Operating on entity object fields) and Native SQL.
- **Financial Scenario**: Finding high-net-worth customers and branch transaction aggregates.
- **Demonstrable Code (`AccountRepository.java`)**:
```java
package com.standardchartered.banking.service;

import com.standardchartered.banking.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    // 1. JPQL Query (Operates on Java Entity fields)
    @Query("SELECT a FROM AccountEntity a WHERE a.accountBalance > :minBalance")
    List<AccountEntity> findHighNetWorthAccounts(@Param("minBalance") BigDecimal minBalance);

    // 2. Native SQL Query (Operates directly on SQL database table columns)
    @Query(value = "SELECT * FROM bank_accounts WHERE account_branch = :branchName", nativeQuery = true)
    List<AccountEntity> findAccountsByBranchNative(@Param("branchName") String branchName);
}
```

---

### 13. Database Pagination & Sorting (`Pageable`, `PageRequest`, `Sort`)
- **Topic**: Querying large data result sets in paginated chunks with sorting.
- **Financial Scenario**: Paging through bank transaction statements (`GET /transactions?page=0&size=10&sort=timestamp,desc`).
- **Demonstrable Code (`TransactionStatementService.java`)**:
```java
package com.standardchartered.banking.service;

import com.standardchartered.banking.entity.TransactionEntity;
import com.standardchartered.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TransactionStatementService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Page<TransactionEntity> getStatementPage(int pageNo, int pageSize) {
        // Page 0, 10 records per page, sorted by transactionDate descending
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("transactionDate").descending());
        return transactionRepository.findAll(pageable);
    }
}
```

---

### 14. ACID Transactions & Transaction Management (`@Transactional`)
- **Topic**: Atomic units of work guaranteeing that all operations succeed or all changes roll back.
- **Financial Scenario**: Account-to-Account Fund Transfer Service.
- **Demonstrable Code (`FundTransferService.java`)**:
```java
package com.standardchartered.banking.service;

import com.standardchartered.banking.entity.AccountEntity;
import com.standardchartered.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class FundTransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional // Guarantees Atomicity & Consistency: If credit fails, debit rolls back automatically
    public void transferFunds(Long sourceAccId, Long targetAccId, BigDecimal amount) {
        AccountEntity source = accountRepository.findById(sourceAccId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        AccountEntity target = accountRepository.findById(targetAccId)
                .orElseThrow(() -> new RuntimeException("Target account not found"));

        if (source.getAccountBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance for transfer");
        }

        source.setAccountBalance(source.getAccountBalance().subtract(amount));
        target.setAccountBalance(target.getAccountBalance().add(amount));

        accountRepository.save(source);
        accountRepository.save(target);
    }
}
```

---

# Section 4: API Security with Spring Security Examples (`SpringSecurity_framework.pdf`)

### 1. Authentication vs Authorization Principles in Banking
- **Topic**: Authentication (verifying who the user is) vs Authorization (checking user permissions & roles).
- **Financial Security Matrix**:

| Security Concept | Definition | Financial System Real-World Example |
| :--- | :--- | :--- |
| **Authentication** | Verifying user credentials/identity | Customer logs into online banking using email `sarah@bank.com` and password `SecretPass123!`. |
| **Authorization** | Verifying permissions to perform actions | Customer `sarah` (`ROLE_CUSTOMER`) can view her balance (`GET /api/v1/accounts/1`), but receives `HTTP 403 Forbidden` when attempting to access `GET /api/v1/admin/audit-logs`. |
| **Principal** | The authenticated user identity | `SecurityContextHolder.getContext().getAuthentication().getPrincipal()` representing `sarah`. |
| **Authority / Role** | Specific privilege granted to user | `ROLE_TELLER`, `ROLE_ADMIN`, `ROLE_CUSTOMER`. |

---

### 2. Spring Security Architecture & Filter Chain Workflow
- **Topic**: Request flow through `SecurityFilterChain`, `AuthenticationManager`, `AuthenticationProvider`, and `UserDetailsService`.
- **Financial Request Lifecycle**:
```text
Client Request (POST /api/v1/transfers)
       │
       ▼
[SecurityFilterChain] ──> Intercepts HTTP request
       │
       ▼
[AuthenticationManager] ──> Delegates to correct provider
       │
       ▼
[DaoAuthenticationProvider] ──> Fetches user details & compares encoded password
       ├──> [UserDetailsService] (Loads user from DB)
       └──> [BCryptPasswordEncoder] (Verifies password hash)
       │
       ▼
[SecurityContextHolder] ──> Stores authenticated Principal & GrantedAuthorities
```

---

### 3. In-Memory Authentication (`InMemoryUserDetailsManager`)
- **Topic**: non-persistent in-memory user configuration for development & prototyping.
- **Financial Scenario**: Setting up hardcoded Teller and Customer test accounts.
- **Demonstrable Code (`InMemorySecurityConfig.java`)**:
```java
package com.standardchartered.banking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class InMemorySecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails teller = User.withUsername("teller_joe")
                .password(encoder.encode("TellerPass2026!"))
                .roles("TELLER")
                .build();

        UserDetails admin = User.withUsername("admin_sarah")
                .password(encoder.encode("AdminPass2026!"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(teller, admin);
    }
}
```

---

### 4. URL Path Authorization & Security Filter Chain Rules
- **Topic**: Restricting REST API endpoints based on Granted Authorities using `SecurityFilterChain`.
- **Financial Scenario**: Protecting `/api/v1/admin/**` (Admin role) vs `/api/v1/customer/**` (Customer role).
- **Demonstrable Code (`BankSecurityFilterChainConfig.java`)**:
```java
package com.standardchartered.banking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BankSecurityFilterChainConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Disabled for REST APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/public/**").permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/teller/**").hasAnyRole("TELLER", "ADMIN")
                        .requestMatchers("/api/v1/customer/**").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // Enable HTTP Basic Auth for REST calls
                .build();
    }
}
```

---

### 5. Password Hashing (`BCryptPasswordEncoder`)
- **Topic**: Encoding passwords securely using BCrypt hashing prior to storage.
- **Financial Scenario**: Utility hashing raw banking credentials into secure salt-and-hash strings.
- **Demonstrable Code (`PasswordHashingDemo.java`)**:
```java
package com.standardchartered.banking.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashingDemo {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "CustomerSecret2026!";

        // Encrypt plain text password
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println("Encoded Password: " + hashedPassword);
        // Output: $2a$10$wK1m4.q5P4J3R7x5z8A...

        // Verify matches during login
        boolean matches = encoder.matches(rawPassword, hashedPassword);
        System.out.println("Password Match Status: " + matches); // true
    }
}
```

---

### 6. Database-Driven Security (`UserDetailsService` & `UserDetails`)
- **Topic**: Loading user accounts and granted roles dynamically from PostgreSQL database tables during login.
- **Financial Scenario**: Custom `UserDetailsService` loading `BankUserEntity` by email and converting to `UserDetails`.
- **Demonstrable Code (`CustomBankUserDetailsService.java` & `BankUserDetails.java`)**:
```java
// 1. Custom UserDetails implementation wrapping BankUser entity
package com.standardchartered.banking.security;

import com.standardchartered.banking.entity.BankUserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class BankUserDetails implements UserDetails {
    private final BankUserEntity user;

    public BankUserDetails(BankUserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole())); // e.g., "ROLE_CUSTOMER"
    }

    @Override
    public String getPassword() { return user.getPassword(); }

    @Override
    public String getUsername() { return user.getEmail(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return user.isActive(); }
}

// 2. Custom UserDetailsService implementation loading user from PostgreSQL DB
package com.standardchartered.banking.service;

import com.standardchartered.banking.entity.BankUserEntity;
import com.standardchartered.banking.repository.BankUserRepository;
import com.standardchartered.banking.security.BankUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomBankUserDetailsService implements UserDetailsService {

    @Autowired
    private BankUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BankUserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
        
        return new BankUserDetails(user);
    }
}
```

---

## Summary Matrix

| PDF Document | Main Domain Topics Covered | Financial Domain Use Cases |
| :--- | :--- | :--- |
| **`MAVEN.pdf`** | Directory Layout, `mvn archetype`, `mvn package`, Repositories | `payment-service` folder tree, `fund-transfer-service` archetype, JAR packaging, Nexus repo config |
| **`XML.pdf`** | Structuring, Attributes vs Elements, Well-Formedness, Namespaces | Customer statements, Credit card transaction payloads, ISO 20022 wire transfer XML, E-commerce partner integration (`scb:` vs `amazon:`) |
| **`Spring Boot.pdf`** | Starters, `@SpringBootApplication`, REST Controllers, `@PathVariable`, Exception Handling, Swagger, JPA Entities, `@Query`, Pagination, `@Transactional` | Retail banking microservice, In-memory customer CRUD, Transfer DTO deserialization, `InsufficientBalanceException` handling, OpenAPI config, High-net-worth accounts JPQL, Paginated statements, Atomic fund transfers |
| **`SpringSecurity_framework.pdf`** | Authentication vs Authorization, Security Architecture, In-Memory Security, Path Authorization, BCrypt, `UserDetailsService` DB security | Teller vs Customer security matrix, Security filter chain workflow, Hardcoded test credentials, Path-based access rules (`/admin/**`), Password hashing demo, Database-backed user login |
