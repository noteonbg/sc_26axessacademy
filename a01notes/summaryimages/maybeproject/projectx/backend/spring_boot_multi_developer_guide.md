# Multi-Developer Spring Boot Project Folder Structure & Usage Guide

## Executive Summary: Package-by-Feature (Vertical Slice Architecture)

In traditional Spring Boot project setups, code is structured by technical layers (`package com.example.controller`, `package com.example.service`, `package com.example.repository`, `package com.example.entity`). 

### Why Package-by-Layer Fails for 5 Concurrent Developers:
1. **Constant Git Merge Conflicts**: Developer 1 working on Feature 1 and Developer 2 working on Feature 2 modify files in the exact same directories (`/controller`, `/service`, `/repository`) every day.
2. **Poor Domain Isolation**: Feature logic is scattered across the entire repository, making it hard to trace business flows.
3. **High Coupling & Accidental Overwrites**: Developers frequently touch shared repository interfaces or entity classes, accidentally breaking other developers' features.

### The Enterprise Solution: Package-by-Feature
We organize the Spring Boot codebase into **Vertical Feature Slices** (`com.example.backend.features.<feature_name>`).
- Each feature package contains its own controllers, services, repositories, entities, and DTOs.
- Each developer is assigned exclusive ownership of their feature package directory.
- Shared infrastructure (security, global exception handling, database configs, cross-cutting utils) lives in `com.example.backend.common` managed by the Tech Lead.

---

## Developer Ownership & Responsibility Matrix

| Developer | Assigned Feature | Spring Boot Package | Domain Responsibility Examples |
| :--- | :--- | :--- | :--- |
| **Developer 1** | **Feature 1** | `com.example.backend.features.feature1` | User Authentication, JWT Tokens, User Registration, Profile Management |
| **Developer 2** | **Feature 2** | `com.example.backend.features.feature2` | Product Catalog, Search, Categories, Inventory Checking |
| **Developer 3** | **Feature 3** | `com.example.backend.features.feature3` | Shopping Cart, Order Placement, Checkout Processing |
| **Developer 4** | **Feature 4** | `com.example.backend.features.feature4` | Payment Gateway Integration (Stripe/PayPal), Invoicing, Transaction Audit |
| **Developer 5** | **Feature 5** | `com.example.backend.features.feature5` | Admin Dashboard, Sales Metrics, Reporting Engine, Audit Logs |
| **Tech Lead** | **Common Core** | `com.example.backend.common` | Security Config, Global Exceptions, Database Setup, Base Utilities |

---

## Complete Spring Boot Directory & Package Tree

```
springboot-multi-dev-backend/
├── pom.xml                                           # Maven Build & Dependency File
├── README.md                                         # Quickstart & Developer Onboarding Guide
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── example/
    │   │           └── backend/
    │   │               ├── BackendApplication.java   # Spring Boot Main Entrypoint (@SpringBootApplication)
    │   │               │
    │   │               ├── common/                   # SHARED INFRASTRUCTURE (Tech Lead Owned)
    │   │               │   ├── config/               # Security, CORS, OpenAPI/Swagger Configs
    │   │               │   │   └── SecurityConfig.java
    │   │               │   ├── exception/            # Centralized Error Handling
    │   │               │   │   ├── ApiException.java
    │   │               │   │   ├── ErrorResponseDTO.java
    │   │               │   │   └── GlobalExceptionHandler.java
    │   │               │   └── util/                 # Cross-Cutting Helper Utilities
    │   │               │       └── DateTimeUtils.java
    │   │               │
    │   │               └── features/                 # VERTICAL FEATURE SLICES (Dev 1 - Dev 5)
    │   │                   │
    │   │                   ├── feature1/             # [Developer 1] Feature 1 Module (Auth/User)
    │   │                   │   ├── controller/
    │   │                   │   │   └── Feature1Controller.java
    │   │                   │   ├── service/
    │   │                   │   │   ├── Feature1Service.java
    │   │                   │   │   └── impl/
    │   │                   │   │       └── Feature1ServiceImpl.java
    │   │                   │   ├── repository/
    │   │                   │   │   └── Feature1Repository.java
    │   │                   │   ├── entity/
    │   │                   │   │   └── Feature1Entity.java
    │   │                   │   └── dto/
    │   │                   │       ├── Feature1RequestDTO.java
    │   │                   │       └── Feature1ResponseDTO.java
    │   │                   │
    │   │                   ├── feature2/             # [Developer 2] Feature 2 Module (Catalog)
    │   │                   │   ├── controller/
    │   │                   │   │   └── Feature2Controller.java
    │   │                   │   ├── service/
    │   │                   │   │   ├── Feature2Service.java
    │   │                   │   │   └── impl/
    │   │                   │   │       └── Feature2ServiceImpl.java
    │   │                   │   ├── repository/
    │   │                   │   │   └── Feature2Repository.java
    │   │                   │   ├── entity/
    │   │                   │   │   └── Feature2Entity.java
    │   │                   │   └── dto/
    │   │                   │       ├── Feature2RequestDTO.java
    │   │                   │       └── Feature2ResponseDTO.java
    │   │                   │
    │   │                   ├── feature3/             # [Developer 3] Feature 3 Module (Cart/Orders)
    │   │                   │   ├── controller/
    │   │                   │   │   └── Feature3Controller.java
    │   │                   │   ├── service/
    │   │                   │   │   ├── Feature3Service.java
    │   │                   │   │   └── impl/
    │   │                   │   │       └── Feature3ServiceImpl.java
    │   │                   │   ├── repository/
    │   │                   │   │   └── Feature3Repository.java
    │   │                   │   ├── entity/
    │   │                   │   │   └── Feature3Entity.java
    │   │                   │   └── dto/
    │   │                   │       ├── Feature3RequestDTO.java
    │   │                   │       └── Feature3ResponseDTO.java
    │   │                   │
    │   │                   ├── feature4/             # [Developer 4] Feature 4 Module (Payments)
    │   │                   │   ├── controller/
    │   │                   │   │   └── Feature4Controller.java
    │   │                   │   ├── service/
    │   │                   │   │   ├── Feature4Service.java
    │   │                   │   │   └── impl/
    │   │                   │   │       └── Feature4ServiceImpl.java
    │   │                   │   ├── repository/
    │   │                   │   │   └── Feature4Repository.java
    │   │                   │   ├── entity/
    │   │                   │   │   └── Feature4Entity.java
    │   │                   │   └── dto/
    │   │                   │       ├── Feature4RequestDTO.java
    │   │                   │       └── Feature4ResponseDTO.java
    │   │                   │
    │   │                   └── feature5/             # [Developer 5] Feature 5 Module (Analytics)
    │   │                       ├── controller/
    │   │                       │   └── Feature5Controller.java
    │   │                       ├── service/
    │   │                       │   ├── Feature5Service.java
    │   │                       │   └── impl/
    │   │                       │       └── Feature5ServiceImpl.java
    │   │                       ├── repository/
    │   │                       │   └── Feature5Repository.java
    │   │                       ├── entity/
    │   │                       │   └── Feature5Entity.java
    │   │                       └── dto/
    │   │                           ├── Feature5RequestDTO.java
    │   │                           └── Feature5ResponseDTO.java
    │   │
    │   └── resources/
    │       ├── application.yml                       # Application Configuration Properties
    │       └── db/
    │           └── migration/                        # Database Migration SQL Scripts (Flyway/Liquibase)
    │               ├── V1_1__dev1_feature1_schema.sql
    │               ├── V1_2__dev2_feature2_schema.sql
    │               ├── V1_3__dev3_feature3_schema.sql
    │               ├── V1_4__dev4_feature4_schema.sql
    │               └── V1_5__dev5_feature5_schema.sql
    │
    └── test/
        └── java/
            └── com/
                └── example/
                    └── backend/
                        ├── BackendApplicationTests.java
                        └── features/                 # Modular Unit & Integration Tests per Feature
                            ├── feature1/
                            │   └── Feature1ControllerTest.java
                            ├── feature2/
                            ├── feature3/
                            ├── feature4/
                            └── feature5/
```

---

## Detailed Breakdown of Sub-Package Layers Per Feature

Each feature directory (`features/featureX/`) follows a clean 5-layer internal architecture:

### 1. `controller/` (HTTP Endpoint Layer)
- **Role**: Exposes REST API endpoints (`@RestController`, `@RequestMapping`). Handles HTTP requests, path variables, query parameters, payload validation (`@Valid`), and maps HTTP status codes (`200 OK`, `201 Created`, `400 Bad Request`).
- **Rule**: Controllers must **never** contain business logic or direct database repository calls. They delegate immediately to the feature's Service interface.
- **Example File**: `Feature1Controller.java` -> `@PostMapping("/api/v1/feature1")`

### 2. `service/` & `service/impl/` (Business Logic Layer)
- **Role**: Contains domain rules, transactional boundaries (`@Transactional`), validations, workflow steps, and coordinate repository calls.
- **Best Practice**: Define an interface (`Feature1Service`) and an implementation class (`Feature1ServiceImpl`). This allows easy mock testing and loose coupling.
- **Rule**: All business validation errors throw custom domain exceptions (e.g. `ApiException`) caught by `GlobalExceptionHandler`.

### 3. `repository/` (Database Data Access Layer)
- **Role**: Spring Data JPA interface extending `JpaRepository<Feature1Entity, Long>`. Defines custom queries (`@Query`, derived query methods like `findByCode(String code)`).
- **Rule**: Repositories must **never** be exposed directly to Controllers.

### 4. `entity/` (Database Schema Entity Layer)
- **Role**: JPA `@Entity` representing database tables. Maps columns, primary keys (`@Id`, `@GeneratedValue`), and indexes.
- **Rule**: Entities must **never** be returned directly in REST API responses! Always map Entities to DTOs before responding.

### 5. `dto/` (Data Transfer Object Layer)
- **Role**: Request and Response contracts for the REST API.
  - `Feature1RequestDTO.java`: Incoming payload with `@NotNull`, `@NotBlank`, `@Size` validation annotations.
  - `Feature1ResponseDTO.java`: Outgoing JSON response payload.
- **Benefit**: Protects internal database schema changes from breaking the REST API contract used by frontend clients.

---

## How Spring Boot Interacts with This Folder Structure

### 1. Spring Component Scanning
`BackendApplication.java` is annotated with `@SpringBootApplication` and resides in package `com.example.backend`.
Because Spring Boot recursively scans all sub-packages underneath `com.example.backend`, it automatically discovers:
- `@RestController` beans inside `com.example.backend.features.feature1.controller`, `feature2.controller`, etc.
- `@Service` beans inside `service.impl` sub-packages.
- `@Repository` interfaces for Spring Data JPA.
- `@Configuration` and `@RestControllerAdvice` inside `com.example.backend.common`.

No manual package scanning configuration is needed!

### 2. Centralized Exception Handling
All developers share a single global exception handler located in `common/exception/GlobalExceptionHandler.java`:
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponseDTO> handleApiException(ApiException ex) { ... }
}
```
If **Developer 3** throws an `ApiException("Item out of stock", HttpStatus.BAD_REQUEST)` in `Feature3ServiceImpl`, Spring Boot automatically captures it and formats a unified JSON error payload:
```json
{
  "timestamp": "2026-08-26T21:07:27Z",
  "status": 400,
  "error": "BAD_REQUEST",
  "message": "Item out of stock",
  "path": "/api/v1/feature3"
}
```

### 3. Centralized Spring Security & Universal CORS Configuration
The Tech Lead manages `common/config/SecurityConfig.java`. It enables Spring Security for the entire application while providing a universal CORS policy allowing any origin URL during development:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().permitAll()
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*")); // Allows ANY URL / Origin
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("*"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```

> **Future Authorization Note**: Exact role-based authorization rules (e.g. `@PreAuthorize("hasRole('ADMIN')")` or JWT filter chains) will be plugged into `SecurityConfig.java` and individual feature controllers as development progresses.

---

## Guidelines for Inter-Feature Communication (Cross-Developer Calls)

What happens when **Developer 3 (Orders)** needs information from **Developer 2 (Catalog)**?

### Option A: Inject Service Interfaces (Recommended for Direct Calls)
Developer 3 injects Developer 2's public `Feature2Service` interface into `Feature3ServiceImpl`:
```java
@Service
@RequiredArgsConstructor
public class Feature3ServiceImpl implements Feature3Service {
    private final Feature2Service feature2Service; // Call Developer 2's service
    
    @Override
    public Feature3ResponseDTO processOrder(...) {
        Feature2ResponseDTO product = feature2Service.getFeature2ById(productId);
        // Process order using product details
    }
}
```
> **STRICT RULE**: Developer 3 must **NEVER** directly access Developer 2's `Feature2Repository` or `Feature2Entity`! Communication must always pass through the public Service boundary or DTO contracts.

### Option B: Spring Application Events (Decoupled Async Calls)
If Developer 3 wants to notify Developer 5 (Analytics) when an order occurs without depending on Developer 5's code:
1. Dev 3 publishes a `Feature3CompletedEvent` using Spring's `ApplicationEventPublisher`.
2. Dev 5 writes an `@EventListener` or `@TransactionalEventListener` in `Feature5ServiceImpl` to consume the event.

---

## Database Migration Rules for 5 Developers (Flyway Naming Strategy)

When 5 developers create database tables concurrently, version conflicts occur if everyone names their SQL files `V1__init.sql`.

### Standardized Naming Convention:
All SQL files reside under `src/main/resources/db/migration/` using developer feature prefixes:

- Developer 1: `V1_1__dev1_feature1_schema.sql`
- Developer 2: `V1_2__dev2_feature2_schema.sql`
- Developer 3: `V1_3__dev3_feature3_schema.sql`
- Developer 4: `V1_4__dev4_feature4_schema.sql`
- Developer 5: `V1_5__dev5_feature5_schema.sql`

If Developer 1 needs to add a new column later: `V1_6__dev1_feature1_add_status.sql`.

---

## Developer Workflow & Git Branching Strategy

### 1. Git Branch Naming
- Developer 1: `feature/dev1-feature1-auth`
- Developer 2: `feature/dev2-feature2-catalog`
- Developer 3: `feature/dev3-feature3-cart`
- Developer 4: `feature/dev4-feature4-payments`
- Developer 5: `feature/dev5-feature5-analytics`

### 2. Pull Request Rules
1. **Scoped Changes**: A developer must only touch files inside `features/featureX/` and their respective migration script.
2. **Common Code Modifying Approval**: If a developer needs to touch `common/` (e.g. `SecurityConfig.java`), the Tech Lead **must** review and approve the Pull Request.
3. **Unit Tests**: Every PR must include passing unit tests inside `src/test/java/.../features/featureX/`.

---

## Summary Checklist for Developers

- [x] Create all new code inside your assigned feature package `com.example.backend.features.featureX`.
- [x] Never import another feature's Repository or Entity directly; use their Service interface or DTOs.
- [x] Map `@Entity` objects to `ResponseDTO` objects in the Service layer before returning to Controller.
- [x] Validate incoming DTO payloads using `@Valid` and standard Jakarta Validation annotations (`@NotNull`, `@NotBlank`).
- [x] Prefix database migration SQL scripts with your developer ID (`V1_X__devX_...`).
- [x] Write isolated controller and service unit tests in your matching `src/test/java/.../features/featureX` directory.
