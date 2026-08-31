# Standard Chartered Banking JWT Security Demo: Spring Boot Backend + React Frontend

> **Location:** `F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\05-banking-jwt-security-demo`  
> **Backend:** Java 21, Spring Boot 3.2.5, Spring Security 6, JJWT (io.jsonwebtoken 0.12.5), H2 In-Memory DB, Maven  
> **Frontend:** Pure Create React App (CRA), `react-scripts`, Axios HTTP Client  

---

## Executive Summary & Architecture Overview

This repository demonstrates an end-to-end full-stack banking integration between a **Spring Boot 3 Backend** utilizing **Stateless JWT (JSON Web Token) Bearer Authentication** and a **React Frontend Application** communicating via a **Decoupled Axios API Service**.

```
05-banking-jwt-security-demo/
│
├── README.md                             <-- Master guide for running full-stack app
│
├── backend/                              <-- SPRING BOOT JWT BACKEND
│   ├── pom.xml                           <-- Maven configuration & JJWT dependencies
│   ├── JWT_CONCEPTS_AND_SYNTAX_GUIDE.md  <-- JWT concepts & architecture guide
│   ├── SWAGGER_AND_BRUNO_GUIDE.md        <-- Swagger UI & Bruno API testing guide
│   └── src/main/java/com/standardchartered/jwtdemo/
│       ├── config/
│       │   └── JwtSecurityConfig.java    <-- SecurityFilterChain, Stateless Policy & CORS Config
│       ├── controller/
│       │   ├── AuthController.java       <-- Login (/login) & Register (/register)
│       │   └── ProtectedBankingController.java <-- Protected banking endpoints
│       ├── dto/                          <-- LoginRequest, JwtResponse, RegisterRequest
│       ├── entity/                       <-- JwtUserEntity (H2 DB User storage)
│       ├── repository/                   <-- JwtUserRepository
│       ├── security/                     <-- JwtAuthenticationFilter & JwtUserDetails
│       └── service/                      <-- JwtService (HMAC Token Generation & Validation)
│
└── frontend/                             <-- PURE CREATE REACT APP (CRA) FRONTEND
    ├── package.json                      <-- React, react-scripts & Axios dependencies
    ├── public/
    │   └── index.html                    <-- Public HTML template container (#root)
    └── src/
        ├── index.js                      <-- React DOM rendering entry point
        ├── index.css                     <-- Global dark-theme styling
        ├── App.js                        <-- Main React UI Component with full in-code documentation
        ├── App.css                       <-- Component styling
        └── api/
            └── apiService.js             <-- SEPARATE JS module for Axios JWT API calls
```

---

## 1. JWT Authentication Mechanism (Backend)

The Spring Boot backend uses **Stateless Session Management** (`SessionCreationPolicy.STATELESS`), meaning **no HTTP JSESSIONID cookies** are created or stored on the server.

### Authentication Flow:
1. **User Login:** Client sends credentials `POST /api/v1/auth/login`.
2. **Token Generation:** `JwtService.java` signs a cryptographic JWT token using HMAC-SHA256 containing subject username, issued timestamp, expiration (24h), and user roles (`ROLE_CUSTOMER` or `ROLE_ADMIN`).
3. **Protected Requests:** Client attaches header `Authorization: Bearer <jwt_token>` on all requests to protected banking endpoints.
4. **Filter Interception:** `JwtAuthenticationFilter.java` intercepts the request, validates the cryptographic signature, extracts claims, and populates Spring Security's `SecurityContextHolder`.

---

## 2. REST Endpoints & Access Control Matrix

| Endpoint | HTTP Method | Allowed Roles | Description |
| :--- | :---: | :---: | :--- |
| `/api/v1/auth/login` | POST | `PermitAll` (Public) | Authenticates credentials and returns JWT Bearer token payload. |
| `/api/v1/auth/register` | POST | `PermitAll` (Public) | Registers new user entity into database (`ROLE_CUSTOMER` or `ROLE_ADMIN`). |
| `/api/v1/account/profile` | GET | `CUSTOMER`, `ADMIN` | Returns active customer profile details. |
| `/api/v1/account/transactions` | GET | `CUSTOMER`, `ADMIN` | Returns recent banking ledger transactions. |
| `/api/v1/admin/system-status` | GET | `ADMIN` Only | Returns microservice health status (Restricted to Administrators). |

---

## 3. CORS Configuration (`backend/JwtSecurityConfig.java`)

To enable the React frontend running on `http://localhost:3000` to send cross-origin requests with `Authorization: Bearer` headers:

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(List.of("http://localhost:*", "http://127.0.0.1:*"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

---

## 4. Decoupled Axios API Service Layer (`frontend/src/api/apiService.js`)

All Axios network calls and JWT Bearer header formatting are isolated into `frontend/src/api/apiService.js`.

### Injecting JWT Bearer Header in Axios:
```javascript
import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  timeout: 5000,
});

export const fetchAccountProfile = async (token) => {
  try {
    const response = await apiClient.get('/account/profile', {
      headers: {
        Authorization: `Bearer ${token}`, // Formats RFC 6750 Bearer Header
      },
    });
    return { success: true, status: response.status, data: response.data };
  } catch (error) {
    return handleAxiosError(error);
  }
};
```

---

## 5. How to Run Backend and Frontend

### Step 1: Run Spring Boot Backend

Open terminal #1:
```bash
cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\05-banking-jwt-security-demo\backend

# Compile and start Spring Boot backend
mvn spring-boot:run
```
*Backend runs on `http://localhost:8080`*

---

### Step 2: Run React Frontend

Open terminal #2:
```bash
cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\05-banking-jwt-security-demo\frontend

# Install npm dependencies (first time only)
npm install

# Start Create React App dev server
npm start
```
*Frontend runs on `http://localhost:3000`*

---

## 6. Security Authorization Test Matrix

| Credentials / Token Used | Action Button Clicked | Resulting HTTP Status | Expected Outcome |
| :--- | :--- | :---: | :--- |
| **john_doe** (`ROLE_CUSTOMER`) | `Get Account Profile` | `200 OK` | ✅ Success (Profile retrieved) |
| **john_doe** (`ROLE_CUSTOMER`) | `Get Recent Transactions` | `200 OK` | ✅ Success (Transactions retrieved) |
| **john_doe** (`ROLE_CUSTOMER`) | `Get System Status (ADMIN)` | `403 Forbidden` | ❌ Access Denied (Customer lacks ADMIN role) |
| **admin** (`ROLE_ADMIN`) | `Get Account Profile` | `200 OK` | ✅ Success (Admin has access) |
| **admin** (`ROLE_ADMIN`) | `Get System Status (ADMIN)` | `200 OK` | ✅ Success (System status retrieved) |
| **No Token / Corrupted Token** | Any Protected Endpoint | `401 Unauthorized` | ❌ Authentication Failed (JWT Invalid) |
