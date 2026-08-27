# Spring Boot Multi-Developer Backend Scaffold

This project demonstrates an enterprise-grade **Package-by-Feature (Vertical Slice Architecture)** setup in Spring Boot for 5 developers working concurrently on 5 separate features.

## Developer Ownership Mapping

| Developer | Assigned Feature Module | Controller Endpoint | Database Table |
| :--- | :--- | :--- | :--- |
| **Developer 1** | `com.example.backend.features.feature1` | `/api/v1/feature1` | `feature1_users` |
| **Developer 2** | `com.example.backend.features.feature2` | `/api/v1/feature2` | `feature2_catalog` |
| **Developer 3** | `com.example.backend.features.feature3` | `/api/v1/feature3` | `feature3_orders` |
| **Developer 4** | `com.example.backend.features.feature4` | `/api/v1/feature4` | `feature4_payments` |
| **Developer 5** | `com.example.backend.features.feature5` | `/api/v1/feature5` | `feature5_analytics` |
| **Tech Lead** | `com.example.backend.common` | Shared Configs & Exception Handling | Shared Infrastructure |

---

## How to Build and Run

### Requirements
- Java 17 or higher
- Maven 3.8+

### Build the Project
```bash
mvn clean compile
```

### Run Unit Tests
```bash
mvn test
```

### Run Spring Boot Application
```bash
mvn spring-boot:run
```

Once launched, access:
- **Application Port**: `http://localhost:8080`
- **H2 Database Console**: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:multidevdb`, Username: `sa`, Password: empty)

---

## REST Endpoints Overview

- **Feature 1 (Developer 1)**:
  - `POST /api/v1/feature1` - Create user/auth profile
  - `GET /api/v1/feature1/{id}` - Fetch user by ID
  - `GET /api/v1/feature1` - Fetch all users
- **Feature 2 (Developer 2)**:
  - `POST /api/v1/feature2` - Create product catalog item
  - `GET /api/v1/feature2/{id}` - Fetch catalog item by ID
  - `GET /api/v1/feature2/search?keyword=...` - Search products
- **Feature 3 (Developer 3)**:
  - `POST /api/v1/feature3` - Place an order (uses Feature 2 service interface)
  - `GET /api/v1/feature3/{id}` - Get order details
- **Feature 4 (Developer 4)**:
  - `POST /api/v1/feature4` - Process payment
  - `GET /api/v1/feature4/order/{orderId}` - Get payment by order ID
- **Feature 5 (Developer 5)**:
  - `POST /api/v1/feature5` - Record analytics metric
  - `GET /api/v1/feature5` - Fetch metrics list
