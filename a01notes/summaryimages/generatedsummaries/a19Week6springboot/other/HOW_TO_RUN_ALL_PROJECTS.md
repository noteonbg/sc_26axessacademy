# Week 6 Spring Boot Projects — Complete Execution & How-to-Run Guide

This document provides a comprehensive, step-by-step guide to run and test all **Spring Boot Banking & Demo Projects** in the Week 6 curriculum. All database-driven projects are configured to use an **embedded H2 in-memory database** (`jdbc:h2:mem:...`), eliminating the need for any external database installation (such as PostgreSQL).

---

## 🛠️ Prerequisites & System Requirements

Before running any project, verify that your computer has the following tools installed:

| Tool | Recommended Version | Verification Command |
| :--- | :--- | :--- |
| **Java JDK** | **Java 17** or **Java 21** | `java -version` |
| **Apache Maven** | **3.8.x** or higher | `mvn -v` |
| **Node.js & npm** | **Node v18+** / **npm 9+** | `node -v` && `npm -v` |

---

## 🗄️ Database Architecture: Embedded H2 Database

All Spring Boot database applications in this repository use **H2 Embedded In-Memory Mode**. 
- **Zero Configuration**: Database schema and initial seed data are created in memory when the Spring Boot application boots up.
- **No External Setup**: No PostgreSQL server, MySQL server, or `CREATE DATABASE` queries are needed.
- **H2 Console**: Accessible via web browser for visual SQL inspection.

### Active H2 Database Summary

| Project | Port | H2 JDBC URL | Driver | Username / Password | H2 Console Path |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **`banking-core-app`** | `8080` | `jdbc:h2:mem:bankingdb` | `org.h2.Driver` | `sa` / *(blank)* | `http://localhost:8080/h2-console` |
| **`03-banking-jpa-demo`** | `8083` | `jdbc:h2:mem:bankingjpadb` | `org.h2.Driver` | `sa` / *(blank)* | `http://localhost:8083/h2-console` |
| **`04-banking-security-demo`** | `8084` | `jdbc:h2:mem:bankingsecdb` | `org.h2.Driver` | `sa` / *(blank)* | `http://localhost:8084/h2-console` |
| **`customer-backend`** (`reactandspring`) | `8080` | `jdbc:h2:mem:bankdb` | `org.h2.Driver` | `sa` / *(blank)* | `http://localhost:8080/h2-console` |

---

## 🚀 Step-by-Step How to Run Each Project

---

### Project 1: `banking-core-app` (Full Core Banking System)
*Location*: `a19Week6springboot/banking-core-app`

1. **Navigate to Directory**:
   ```bash
   cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\banking-core-app
   ```
2. **Build and Package**:
   ```bash
   mvn clean package
   ```
3. **Run Application**:
   ```bash
   mvn spring-boot:run
   ```
4. **Test & Access**:
   - **Swagger Interactive API Documentation**: Open `http://localhost:8080/swagger-ui/index.html`
   - **H2 Console**: Open `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:bankingdb`, User: `sa`, Password: leave empty).
   - **Sample API Test (GET Customers)**:
     ```bash
     curl -X GET http://localhost:8080/api/v1/customers -u teller_joe:TellerPass2026!
     ```

---

### Project 2: `01-banking-controller-demo` (REST Controllers & Exception Handling)
*Location*: `a19Week6springboot/01-banking-controller-demo`

1. **Navigate to Directory**:
   ```bash
   cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\01-banking-controller-demo
   ```
2. **Build and Run**:
   ```bash
   mvn clean spring-boot:run
   ```
3. **Test & Access**:
   - **Port**: `8081`
   - **Fetch All Customers**: `GET http://localhost:8081/api/v1/customers`
   - **Fetch Single Customer**: `GET http://localhost:8081/api/v1/customers/101`

---

### Project 3: `02-banking-service-demo` (Service Layer & Business Rules)
*Location*: `a19Week6springboot/02-banking-service-demo`

1. **Navigate to Directory**:
   ```bash
   cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\02-banking-service-demo
   ```
2. **Build and Run**:
   ```bash
   mvn clean spring-boot:run
   ```
3. **Test & Access**:
   - **Port**: `8082`
   - **Fetch Account Balance**: `GET http://localhost:8082/api/v1/accounts/ACC1001`
   - **Execute Transfer**: `POST http://localhost:8082/api/v1/transfers`

---

### Project 4: `03-banking-jpa-demo` (Spring Data JPA ORM & Custom Queries)
*Location*: `a19Week6springboot/03-banking-jpa-demo`

1. **Navigate to Directory**:
   ```bash
   cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\03-banking-jpa-demo
   ```
2. **Build and Run**:
   ```bash
   mvn clean spring-boot:run
   ```
3. **Test & Access**:
   - **Port**: `8083`
   - **H2 Console**: `http://localhost:8083/h2-console` (JDBC URL: `jdbc:h2:mem:bankingjpadb`)
   - **Fetch All Accounts**: `GET http://localhost:8083/api/v1/jpa/accounts`

---

### Project 5: `04-banking-security-demo` (Spring Security Architecture & Hashing)
*Location*: `a19Week6springboot/04-banking-security-demo`

1. **Navigate to Directory**:
   ```bash
   cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\04-banking-security-demo
   ```
2. **Build and Run**:
   ```bash
   mvn clean spring-boot:run
   ```
3. **Test & Access**:
   - **Port**: `8084`
   - **H2 Console**: `http://localhost:8084/h2-console` (JDBC URL: `jdbc:h2:mem:bankingsecdb`)
   - **Test Authenticated Route**: `GET http://localhost:8084/api/v1/secure/profile` (Basic Auth: `user` / `password`)

---

### Project 6: `reactandspring` (Customer Management Spring Boot + React App)
*Location*: `a19Week6springboot/reactandspring`

1. **Start Backend (Terminal 1)**:
   ```bash
   cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\reactandspring\customer-backend
   mvn clean spring-boot:run
   ```
   - *Runs on*: `http://localhost:8080` (H2 DB: `jdbc:h2:mem:bankdb`)

2. **Start Frontend (Terminal 2)**:
   ```bash
   cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\reactandspring\customer-frontend
   npm install
   npm start
   ```
   - *Opens browser at*: `http://localhost:3000`

---

### Project 7: `00-maven-core-demo` (Apache Maven Core Concepts & Lifecycle)
*Location*: `a19Week6springboot/00-maven-core-demo`

1. **Navigate & Execute**:
   ```bash
   cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\00-maven-core-demo
   mvn clean package
   java -jar target/maven-core-demo-1.0.0.jar
   ```

---

## 📋 Summary Checklist

| Project | Build Command | Default Port | Database Type | Status |
| :--- | :--- | :--- | :--- | :--- |
| `00-maven-core-demo` | `mvn clean package` | N/A | None | ✅ Verified |
| `01-banking-controller-demo` | `mvn spring-boot:run` | `8081` | In-memory POJO | ✅ Verified |
| `02-banking-service-demo` | `mvn spring-boot:run` | `8082` | In-memory POJO | ✅ Verified |
| `03-banking-jpa-demo` | `mvn spring-boot:run` | `8083` | **H2 Embedded** | ✅ Verified |
| `04-banking-security-demo` | `mvn spring-boot:run` | `8084` | **H2 Embedded** | ✅ Verified |
| `banking-core-app` | `mvn spring-boot:run` | `8080` | **H2 Embedded** | ✅ Verified |
| `reactandspring/customer-backend` | `mvn spring-boot:run` | `8080` | **H2 Embedded** | ✅ Verified |
| `reactandspringpoc/rectangle-backend` | `mvn spring-boot:run` | `8080` | None | ✅ Verified |
