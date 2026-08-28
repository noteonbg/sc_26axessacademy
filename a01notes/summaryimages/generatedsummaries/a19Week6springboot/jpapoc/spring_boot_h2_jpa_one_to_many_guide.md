# Financial Portfolio & Asset Holding Management System
## Complete Step-by-Step Guide: H2 Database Configuration, JPA `@OneToMany` / `@ManyToOne`, Swagger & Bruno Testing

This guide provides an end-to-end implementation of a **Financial Domain application** using **Spring Boot 3**, **H2 Database (Java Configuration)**, **Spring Data JPA**, **Swagger OpenAPI**, and step-by-step testing instructions for **Bruno API Client** without needing pre-existing collections.

> [!NOTE]
> **Domain Focus**: Rather than using *Customer & Account*, this guide models an **Investment Portfolio** (`Portfolio`) and its associated **Financial Asset Holdings** (`Holding`). 
> - **1 Portfolio** has **Many Holdings** (`@OneToMany`)
> - **Many Holdings** belong to **1 Portfolio** (`@ManyToOne`)

---

## 1. Project Dependencies (`pom.xml`)

Add the following dependencies to your `pom.xml` file.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.financial.wealth</groupId>
    <artifactId>portfolio-management-service</artifactId>
    <version>1.0.0</version>
    <name>portfolio-management-service</name>
    <description>Financial Portfolio and Asset Holdings Management Service</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.3</version>
        <relativePath/>
    </parent>

    <properties>
        <java.version>17</java.version>
        <springdoc.version>2.3.0</springdoc.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starter Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Boot Starter Data JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- H2 Database Engine -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok for Boilerplate Reduction -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- OpenAPI / Swagger Documentation -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc.version}</version>
        </dependency>

        <!-- Spring Boot Starter Validation -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <!-- Spring Boot Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## 2. Application Configuration (`application.properties`)

Configure the H2 Database, JPA settings, and Swagger UI in `src/main/resources/application.properties`.

```properties
# Server Configuration
server.port=8080

# Application Name
spring.application.name=portfolio-management-service

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:financialdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console Enablement
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=false

# JPA & Hibernate Settings
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Swagger / OpenAPI Settings
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
```

---

## 3. Java Class for H2 Database Configuration (`H2DatabaseConfig.java`)

Creating a dedicated `@Configuration` class to manage H2 DataSource programmatically, register the H2 Web Console, and initialize seed data for testing.

```java
package com.financial.wealth.config;

import org.h2.tools.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class H2DatabaseConfig {

    /**
     * Explicit DataSource Bean Definition for H2 In-Memory Database.
     */
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:financialdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .username("sa")
                .password("")
                .build();
    }

    /**
     * Optional H2 TCP Server instance to allow external GUI SQL Clients (DBeaver, DataGrip) to connect to in-memory H2.
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnProperty(name = "h2.tcp.enabled", havingValue = "true")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    /**
     * Initializer runner to verify H2 DB Connection on Startup and set up custom DB functions/schema if needed.
     */
    @Bean
    public CommandLineRunner h2Initializer(DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                System.out.println("==================================================");
                System.out.println(" H2 DATABASE INITIALIZED SUCCESSFULLY!");
                System.out.println(" JDBC URL : " + connection.getMetaData().getURL());
                System.out.println(" Username : " + connection.getMetaData().getUserName());
                System.out.println(" Database : " + connection.getMetaData().getDatabaseProductName());
                System.out.println("==================================================");
            } catch (SQLException e) {
                System.err.println("Failed to initialize H2 database: " + e.getMessage());
            }
        };
    }
}
```

---

## 4. Domain Model Entities (`@OneToMany` & `@ManyToOne`)

### 4.1 Parent Entity: `Portfolio.java` (One)

- A **Portfolio** holds metadata (Investor name, Risk Level, Portfolio Type) and has a list of **Holdings**.
- `@OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)` ensures cascading inserts, updates, and deletes.

```java
package com.financial.wealth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "portfolios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String portfolioName;

    @Column(nullable = false, length = 100)
    private String investorName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RiskTolerance riskTolerance; // CONSERVATIVE, MODERATE, AGGRESSIVE

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ONE PORTFOLIO HAS MANY HOLDINGS
    @OneToMany(
        mappedBy = "portfolio", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true, 
        fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Holding> holdings = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Helper methods to manage bidirectional link sync
    public void addHolding(Holding holding) {
        holdings.add(holding);
        holding.setPortfolio(this);
    }

    public void removeHolding(Holding holding) {
        holdings.remove(holding);
        holding.setPortfolio(null);
    }

    public BigDecimal getTotalValue() {
        if (holdings == null || holdings.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return holdings.stream()
                .map(Holding::getCurrentMarketValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
```

---

### 4.2 Enum: `RiskTolerance.java`

```java
package com.financial.wealth.entity;

public enum RiskTolerance {
    CONSERVATIVE,
    MODERATE,
    AGGRESSIVE
}
```

---

### 4.3 Child Entity: `Holding.java` (Many)

- Each **Holding** represents an asset (e.g. Stock `AAPL`, ETF `VOO`, Bond `US10Y`) owned inside a portfolio.
- `@ManyToOne(fetch = FetchType.LAZY)` points back to the owning `Portfolio`.

```java
package com.financial.wealth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "holdings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String tickerSymbol; // e.g., AAPL, NVDA, VOO

    @Column(nullable = false, length = 50)
    private String assetClass; // EQUITY, FIXED_INCOME, CRYPTO, COMMODITY

    @Column(nullable = false, precision = 15, scale = 4)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal purchasePrice;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal currentPrice;

    @Column(name = "purchased_at", nullable = false)
    private LocalDateTime purchasedAt;

    // MANY HOLDINGS BELONG TO ONE PORTFOLIO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    public BigDecimal getCurrentMarketValue() {
        if (quantity == null || currentPrice == null) {
            return BigDecimal.ZERO;
        }
        return quantity.multiply(currentPrice);
    }
}
```

---

## 5. Data Transfer Objects (DTOs)

To prevent infinite JSON recursion and separate API contracts from DB entities, define clean DTOs.

### 5.1 Request DTOs

```java
package com.financial.wealth.dto;

import com.financial.wealth.entity.RiskTolerance;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioRequestDTO {

    @NotBlank(message = "Portfolio name is required")
    private String portfolioName;

    @NotBlank(message = "Investor name is required")
    private String investorName;

    @NotNull(message = "Risk tolerance is required")
    private RiskTolerance riskTolerance;

    private List<HoldingRequestDTO> holdings;
}
```

```java
package com.financial.wealth.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoldingRequestDTO {

    @NotBlank(message = "Ticker symbol is required")
    @Size(min = 1, max = 10)
    private String tickerSymbol;

    @NotBlank(message = "Asset class is required")
    private String assetClass;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private BigDecimal quantity;

    @NotNull(message = "Purchase price is required")
    @Positive(message = "Purchase price must be positive")
    private BigDecimal purchasePrice;

    @NotNull(message = "Current price is required")
    @Positive(message = "Current price must be positive")
    private BigDecimal currentPrice;
}
```

---

### 5.2 Response DTOs

```java
package com.financial.wealth.dto;

import com.financial.wealth.entity.RiskTolerance;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioResponseDTO {
    private Long id;
    private String portfolioName;
    private String investorName;
    private RiskTolerance riskTolerance;
    private BigDecimal totalPortfolioValue;
    private LocalDateTime createdAt;
    private List<HoldingResponseDTO> holdings;
}
```

```java
package com.financial.wealth.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoldingResponseDTO {
    private Long id;
    private String tickerSymbol;
    private String assetClass;
    private BigDecimal quantity;
    private BigDecimal purchasePrice;
    private BigDecimal currentPrice;
    private BigDecimal marketValue;
    private LocalDateTime purchasedAt;
}
```

---

## 6. Spring Data JPA Repositories

### 6.1 `PortfolioRepository.java`

```java
package com.financial.wealth.repository;

import com.financial.wealth.entity.Portfolio;
import com.financial.wealth.entity.RiskTolerance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findByRiskTolerance(RiskTolerance riskTolerance);

    List<Portfolio> findByInvestorNameContainingIgnoreCase(String investorName);

    // Fetch portfolio along with holdings in a single query (solves N+1 problem)
    @Query("SELECT p FROM Portfolio p LEFT JOIN FETCH p.holdings WHERE p.id = :id")
    Optional<Portfolio> findByIdWithHoldings(Long id);
}
```

### 6.2 `HoldingRepository.java`

```java
package com.financial.wealth.repository;

import com.financial.wealth.entity.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {

    List<Holding> findByTickerSymbol(String tickerSymbol);

    List<Holding> findByPortfolioId(Long portfolioId);
}
```

---

## 7. Business Service Layer

### 7.1 Interface: `PortfolioService.java`

```java
package com.financial.wealth.service;

import com.financial.wealth.dto.*;

import java.util.List;

public interface PortfolioService {
    PortfolioResponseDTO createPortfolio(PortfolioRequestDTO requestDTO);
    List<PortfolioResponseDTO> getAllPortfolios();
    PortfolioResponseDTO getPortfolioById(Long id);
    PortfolioResponseDTO addHoldingToPortfolio(Long portfolioId, HoldingRequestDTO holdingRequestDTO);
    void deletePortfolio(Long id);
}
```

---

### 7.2 Implementation: `PortfolioServiceImpl.java`

```java
package com.financial.wealth.service;

import com.financial.wealth.dto.*;
import com.financial.wealth.entity.Holding;
import com.financial.wealth.entity.Portfolio;
import com.financial.wealth.repository.PortfolioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Override
    public PortfolioResponseDTO createPortfolio(PortfolioRequestDTO requestDTO) {
        Portfolio portfolio = Portfolio.builder()
                .portfolioName(requestDTO.getPortfolioName())
                .investorName(requestDTO.getInvestorName())
                .riskTolerance(requestDTO.getRiskTolerance())
                .build();

        if (requestDTO.getHoldings() != null && !requestDTO.getHoldings().isEmpty()) {
            for (HoldingRequestDTO hDto : requestDTO.getHoldings()) {
                Holding holding = Holding.builder()
                        .tickerSymbol(hDto.getTickerSymbol().toUpperCase())
                        .assetClass(hDto.getAssetClass())
                        .quantity(hDto.getQuantity())
                        .purchasePrice(hDto.getPurchasePrice())
                        .currentPrice(hDto.getCurrentPrice())
                        .purchasedAt(LocalDateTime.now())
                        .build();
                portfolio.addHolding(holding);
            }
        }

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        return mapToPortfolioResponse(savedPortfolio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PortfolioResponseDTO> getAllPortfolios() {
        return portfolioRepository.findAll().stream()
                .map(this::mapToPortfolioResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PortfolioResponseDTO getPortfolioById(Long id) {
        Portfolio portfolio = portfolioRepository.findByIdWithHoldings(id)
                .orElseThrow(() -> new EntityNotFoundException("Portfolio not found with ID: " + id));
        return mapToPortfolioResponse(portfolio);
    }

    @Override
    public PortfolioResponseDTO addHoldingToPortfolio(Long portfolioId, HoldingRequestDTO holdingDTO) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new EntityNotFoundException("Portfolio not found with ID: " + portfolioId));

        Holding holding = Holding.builder()
                .tickerSymbol(holdingDTO.getTickerSymbol().toUpperCase())
                .assetClass(holdingDTO.getAssetClass())
                .quantity(holdingDTO.getQuantity())
                .purchasePrice(holdingDTO.getPurchasePrice())
                .currentPrice(holdingDTO.getCurrentPrice())
                .purchasedAt(LocalDateTime.now())
                .build();

        portfolio.addHolding(holding);
        Portfolio updatedPortfolio = portfolioRepository.save(portfolio);
        return mapToPortfolioResponse(updatedPortfolio);
    }

    @Override
    public void deletePortfolio(Long id) {
        if (!portfolioRepository.existsById(id)) {
            throw new EntityNotFoundException("Portfolio not found with ID: " + id);
        }
        // Cascades to holdings automatically via CascadeType.ALL
        portfolioRepository.deleteById(id);
    }

    // Mapper Helpers
    private PortfolioResponseDTO mapToPortfolioResponse(Portfolio portfolio) {
        List<HoldingResponseDTO> holdingResponses = portfolio.getHoldings().stream()
                .map(this::mapToHoldingResponse)
                .collect(Collectors.toList());

        return PortfolioResponseDTO.builder()
                .id(portfolio.getId())
                .portfolioName(portfolio.getPortfolioName())
                .investorName(portfolio.getInvestorName())
                .riskTolerance(portfolio.getRiskTolerance())
                .totalPortfolioValue(portfolio.getTotalValue())
                .createdAt(portfolio.getCreatedAt())
                .holdings(holdingResponses)
                .build();
    }

    private HoldingResponseDTO mapToHoldingResponse(Holding holding) {
        return HoldingResponseDTO.builder()
                .id(holding.getId())
                .tickerSymbol(holding.getTickerSymbol())
                .assetClass(holding.getAssetClass())
                .quantity(holding.getQuantity())
                .purchasePrice(holding.getPurchasePrice())
                .currentPrice(holding.getCurrentPrice())
                .marketValue(holding.getCurrentMarketValue())
                .purchasedAt(holding.getPurchasedAt())
                .build();
    }
}
```

---

## 8. REST Controller (`PortfolioController.java`)

Complete Controller with Swagger / OpenAPI Annotations.

```java
package com.financial.wealth.controller;

import com.financial.wealth.dto.*;
import com.financial.wealth.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
@Tag(name = "Portfolio Management", description = "Endpoints for managing financial portfolios and asset holdings")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    @Operation(summary = "Create a new portfolio", description = "Creates a new investment portfolio along with optional initial holdings.")
    @ApiResponse(responseCode = "201", description = "Portfolio successfully created")
    public ResponseEntity<PortfolioResponseDTO> createPortfolio(@Valid @RequestBody PortfolioRequestDTO requestDTO) {
        PortfolioResponseDTO response = portfolioService.createPortfolio(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all portfolios", description = "Retrieves a list of all portfolios with summary valuations.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved portfolios")
    public ResponseEntity<List<PortfolioResponseDTO>> getAllPortfolios() {
        return ResponseEntity.ok(portfolioService.getAllPortfolios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get portfolio by ID", description = "Retrieves detailed information and holdings for a specific portfolio.")
    @ApiResponse(responseCode = "200", description = "Portfolio found")
    @ApiResponse(responseCode = "404", description = "Portfolio not found")
    public ResponseEntity<PortfolioResponseDTO> getPortfolioById(@PathVariable Long id) {
        return ResponseEntity.ok(portfolioService.getPortfolioById(id));
    }

    @PostMapping("/{id}/holdings")
    @Operation(summary = "Add holding to portfolio", description = "Adds a new asset holding (stock/etf/bond) to an existing portfolio.")
    @ApiResponse(responseCode = "200", description = "Holding successfully added")
    @ApiResponse(responseCode = "404", description = "Portfolio not found")
    public ResponseEntity<PortfolioResponseDTO> addHolding(
            @PathVariable Long id,
            @Valid @RequestBody HoldingRequestDTO holdingRequestDTO) {
        return ResponseEntity.ok(portfolioService.addHoldingToPortfolio(id, holdingRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete portfolio", description = "Deletes a portfolio and all associated holdings (Cascading delete).")
    @ApiResponse(responseCode = "204", description = "Portfolio successfully deleted")
    @ApiResponse(responseCode = "404", description = "Portfolio not found")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 9. How to Test with Swagger UI & H2 Console

### 9.1 Accessing H2 Console
1. Start your Spring Boot application (`mvn spring-boot:run`).
2. Open your web browser and navigate to:
   `http://localhost:8080/h2-console`
3. Enter the following details:
   - **JDBC URL**: `jdbc:h2:mem:financialdb`
   - **User Name**: `sa`
   - **Password**: *(leave blank)*
4. Click **Connect**. You will see the `PORTFOLIOS` and `HOLDINGS` tables generated automatically by JPA.

### 9.2 Accessing Swagger UI
1. Navigate to:
   `http://localhost:8080/swagger-ui.html`
2. You will see the interactive Swagger Documentation listing all `/api/v1/portfolios` endpoints.
3. Click on **POST /api/v1/portfolios** -> **Try it out** -> Paste JSON -> **Execute**.

---

## 10. How to Test with Bruno API Client (Step-by-Step without importing collections)

Follow these steps to manually set up and execute request tests in **Bruno**:

### Step 10.1: Create a New Request Collection in Bruno
1. Open the **Bruno** application.
2. Click **Create Collection**.
3. Name: `Financial-Portfolio-API`.
4. Location: Select a local folder.
5. Click **Create**.

---

### Step 10.2: Configure Environment Variables (Optional but recommended)
1. Click the **Environments** gear icon in the top right.
2. Click **Add Environment** -> Name it `Local-Dev`.
3. Add a Variable:
   - **Name**: `baseUrl`
   - **Value**: `http://localhost:8080`
4. Click **Save** and select `Local-Dev` as the active environment.

---

### Step 10.3: Request 1 - Create Portfolio with Holdings (`POST`)
1. Click **+ Add Request** inside your collection.
2. **Type**: `HTTP`
3. **Name**: `Create Growth Portfolio`
4. **Method**: `POST`
5. **URL**: `{{baseUrl}}/api/v1/portfolios` (or `http://localhost:8080/api/v1/portfolios`)
6. Go to the **Body** tab -> Select **JSON**.
7. Paste the following JSON payload:

```json
{
  "portfolioName": "Tech Growth Fund",
  "investorName": "Jane Doe",
  "riskTolerance": "AGGRESSIVE",
  "holdings": [
    {
      "tickerSymbol": "AAPL",
      "assetClass": "EQUITY",
      "quantity": 50.0,
      "purchasePrice": 175.50,
      "currentPrice": 190.25
    },
    {
      "tickerSymbol": "NVDA",
      "assetClass": "EQUITY",
      "quantity": 20.0,
      "purchasePrice": 450.00,
      "currentPrice": 720.00
    }
  ]
}
```
8. Click **Send** (or `Ctrl + Enter`).
9. **Expected Output**: HTTP `201 Created` returning the generated Portfolio (ID: `1`) and total valuation calculation.

---

### Step 10.4: Request 2 - Fetch All Portfolios (`GET`)
1. Click **+ Add Request**.
2. **Name**: `Get All Portfolios`
3. **Method**: `GET`
4. **URL**: `http://localhost:8080/api/v1/portfolios`
5. Click **Send**.
6. **Expected Output**: HTTP `200 OK` with an array of portfolios.

---

### Step 10.5: Request 3 - Add New Asset Holding to Portfolio (`POST`)
1. Click **+ Add Request**.
2. **Name**: `Add ETF Holding to Portfolio 1`
3. **Method**: `POST`
4. **URL**: `http://localhost:8080/api/v1/portfolios/1/holdings`
5. Go to **Body** -> **JSON** tab:

```json
{
  "tickerSymbol": "VOO",
  "assetClass": "ETF",
  "quantity": 15.0,
  "purchasePrice": 410.00,
  "currentPrice": 460.50
}
```
6. Click **Send**.
7. **Expected Output**: HTTP `200 OK` returning Portfolio 1 with the newly added `VOO` holding and recalculated portfolio value.

---

### Step 10.6: Request 4 - Fetch Portfolio by ID (`GET`)
1. Click **+ Add Request**.
2. **Name**: `Get Portfolio 1 Details`
3. **Method**: `GET`
4. **URL**: `http://localhost:8080/api/v1/portfolios/1`
5. Click **Send**.
6. **Expected Output**: HTTP `200 OK` showing complete details and all 3 holdings (`AAPL`, `NVDA`, `VOO`).

---

### Step 10.7: Request 5 - Delete Portfolio & Verify Cascade (`DELETE`)
1. Click **+ Add Request**.
2. **Name**: `Delete Portfolio 1`
3. **Method**: `DELETE`
4. **URL**: `http://localhost:8080/api/v1/portfolios/1`
5. Click **Send**.
6. **Expected Output**: HTTP `204 No Content`.
7. Check the H2 Console (`http://localhost:8080/h2-console`) and run:
   ```sql
   SELECT * FROM PORTFOLIOS WHERE ID = 1;
   SELECT * FROM HOLDINGS WHERE PORTFOLIO_ID = 1;
   ```
   Both queries will return 0 rows, confirming proper JPA **cascading delete** operation.

---

## 11. Complete Verification & Review Checklist

| Step / Feature | Verification Criteria | Status |
| :--- | :--- | :---: |
| **Domain Selection** | Financial domain model (`Portfolio` & `Holding`) used instead of Customer/Account | ✅ |
| **Dependencies** | Complete `pom.xml` with Spring Data JPA, H2, Spring Web, Lombok, and OpenAPI Swagger | ✅ |
| **Properties Format** | Standard `application.properties` key-value pairs used (no YAML) | ✅ |
| **H2 Config Class** | Dedicated Java Configuration class `H2DatabaseConfig.java` provided | ✅ |
| **JPA `@OneToMany`** | Configured in `Portfolio.java` with `cascade = CascadeType.ALL` and `orphanRemoval = true` | ✅ |
| **JPA `@ManyToOne`** | Configured in `Holding.java` with `@JoinColumn(name = "portfolio_id")` | ✅ |
| **DTO Pattern** | Request/Response DTOs created to prevent circular JSON serialization issues | ✅ |
| **Repository Layer** | Spring Data JPA interfaces created for `Portfolio` and `Holding` | ✅ |
| **Service Layer** | Business logic & transaction boundaries properly handled | ✅ |
| **REST Controller** | `PortfolioController.java` with CRUD endpoints and Swagger `@Operation` annotations | ✅ |
| **Swagger Testing** | Clear instructions and endpoint URL (`/swagger-ui.html`) provided | ✅ |
| **Bruno API Testing** | Step-by-step manual setup instructions without relying on pre-imported collections | ✅ |
