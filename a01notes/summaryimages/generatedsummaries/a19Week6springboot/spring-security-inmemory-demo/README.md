# Spring Security In-Memory Auth Demo: Spring Boot Backend + React Frontend

> **Location:** `F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\spring-security-inmemory-demo`  
> **Backend:** Java 21, Spring Boot 3.2.5, Spring Security 6, Maven  
> **Frontend:** Pure Create React App (CRA), `react-scripts`, Axios HTTP Client  

---

## Executive Summary & Directory Architecture

This repository demonstrates an end-to-end full-stack integration between a **Spring Boot Backend** utilizing **Spring Security In-Memory Authentication** and a **React Frontend Application** communicating via **Axios** using **HTTP Basic Authentication**.

```
spring-security-inmemory-demo/
│
├── README.md                             <-- Master guide for running full-stack app
│
├── backend/                              <-- SPRING BOOT BACKEND
│   ├── pom.xml                           <-- Maven configuration & dependencies
│   ├── SPRING_SECURITY_EXPLANATION.md    <-- Spring Security concepts & architecture guide
│   ├── SWAGGER_AND_BRUNO_TESTING_GUIDE.md<-- Swagger UI & Bruno API testing guide
│   └── src/main/java/com/example/security/
│       ├── config/
│       │   └── SecurityConfig.java       <-- SecurityFilterChain, In-Memory Users & CORS Config
│       └── controller/
│           └── DemoController.java       <-- Endpoints /api/f1, /api/f2, /api/f3
│
└── frontend/                             <-- PURE CREATE REACT APP (CRA) FRONTEND
    ├── package.json                      <-- React, react-scripts & Axios dependencies
    ├── public/
    │   └── index.html                    <-- Public HTML template container (#root)
    └── src/
        ├── index.js                      <-- React DOM rendering entry point
        ├── index.css                     <-- Global CSS styles
        ├── App.js                        <-- Main React UI Component
        ├── App.css                       <-- Component styling
        └── api/
            └── apiService.js             <-- SEPARATE JS module for Axios HTTP calls
```

---

## 1. Spring Security In-Memory Configuration (Backend)

The Spring Boot backend defines two hardcoded in-memory users and three REST endpoints protected by role-based access rules.

### In-Memory Users:
1. **User A (Admin):** Username = `admin`, Password = `admin123`, Role = `ADMIN`
2. **User B (Normal User):** Username = `user`, Password = `user123`, Role = `USER`

### REST Endpoints & Access Policy:

| Endpoint | HTTP Method | Role Requirement | Description |
| :--- | :---: | :---: | :--- |
| `/api/f1` | GET | `ROLE_ADMIN` | Accessible **ONLY** by User A (`admin`). Returns `"f1 at work"`. |
| `/api/f2` | GET | `ROLE_USER` | Accessible **ONLY** by User B (`user`). Returns `"f2 at work"`. |
| `/api/f3` | GET | `PermitAll` (Public) | Accessible by **ANYONE** without authentication. Returns `"f3 at work"`. |

---

## 2. CORS (Cross-Origin Resource Sharing) Setup

When a browser running the React app on `http://localhost:6100` calls Spring Boot on `http://localhost:6080`, the browser executes a **CORS preflight OPTIONS request**.

In `backend/src/main/java/com/example/security/config/SecurityConfig.java`, CORS is explicitly enabled:

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

## 3. Separate Axios API Service Layer (`frontend/src/api/apiService.js`)

Per architectural requirements, all HTTP network requests and Basic Authentication headers are decoupled into a dedicated file: `frontend/src/api/apiService.js`.

### How HTTP Basic Auth Works in Axios:
HTTP Basic Authentication requires an `Authorization` header containing Base64 encoded credentials: `Basic <base64(username:password)>`.

Axios provides a built-in `auth` configuration option that automatically handles Base64 encoding:

```javascript
import axios from 'axios';

const apiClient = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL || 'http://localhost:6080/api',
  timeout: 5000,
});

export const fetchFunctionF1 = async (username, password) => {
  try {
    const response = await apiClient.get('/f1', {
      auth: {
        username: username,
        password: password,
      },
    });
    return { success: true, status: response.status, data: response.data };
  } catch (error) {
    return handleAxiosError(error);
  }
};
```

---

## 4. React Component Structure & Syntax Explanation (`frontend/src/App.js`)

The React UI (`App.js`) follows Create React App conventions and features interactive preset profile selection, form inputs, dynamic endpoint testing, and response status visualization.

### Key Syntax Concepts Explained:

1. **`import React, { useState } from 'react';`**
   * Imports the core React library and the `useState` Hook for state management.
2. **`const [username, setUsername] = useState('admin');`**
   * Declares a state variable `username` initialized to `'admin'`, and a function `setUsername` to update it when the user types or clicks a preset button.
3. **`async / await` in Handlers:**
   * Handlers like `handleCallF1` pause execution until the asynchronous Axios promise returns, keeping the UI responsive.
4. **Conditional Rendering (`{apiResult && (...)}`):**
   * Renders the response panel only after an API request has been completed.
5. **Class Names & Dynamic CSS (`className={...}`):**
   * Dynamically applies success (`badge-ok`) or error (`badge-fail`) styling depending on HTTP status codes (`200 OK` vs `401 / 403`).

---

## 5. How to Run Backend and Frontend

### Step 1: Run the Spring Boot Backend

Open terminal #1:
```bash
cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\spring-security-inmemory-demo\backend

# Compile and launch Spring Boot application
mvn spring-boot:run
```
*Backend will start on port 6080:* `http://localhost:6080`

---

### Step 2: Run the Create React App Frontend

Open terminal #2:
```bash
cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\spring-security-inmemory-demo\frontend

# Install npm dependencies (first time only)
npm install

# Start Create React App development server
npm start
```
*Frontend will start on port 6100:* `http://localhost:6100`

---

## 6. Expected Security Test Results Matrix

| Credentials Entered | Action Button Clicked | Resulting HTTP Status | Expected Outcome |
| :--- | :--- | :---: | :--- |
| **admin / admin123** (`ROLE_ADMIN`) | `Call /api/f1 (ADMIN)` | `200 OK` | ✅ Success ("f1 at work") |
| **admin / admin123** (`ROLE_ADMIN`) | `Call /api/f2 (USER)` | `403 Forbidden` | ❌ Access Denied (Admin lacks USER role) |
| **admin / admin123** (`ROLE_ADMIN`) | `Call /api/f3 (Public)` | `200 OK` | ✅ Success ("f3 at work") |
| **user / user123** (`ROLE_USER`) | `Call /api/f1 (ADMIN)` | `403 Forbidden` | ❌ Access Denied (User lacks ADMIN role) |
| **user / user123** (`ROLE_USER`) | `Call /api/f2 (USER)` | `200 OK` | ✅ Success ("f2 at work") |
| **user / user123** (`ROLE_USER`) | `Call /api/f3 (Public)` | `200 OK` | ✅ Success ("f3 at work") |
| **Empty / Wrong Password** | `Call /api/f1` or `f2` | `401 Unauthorized` | ❌ Authentication Failed |
| **Empty / Wrong Password** | `Call /api/f3 (Public)` | `200 OK` | ✅ Success (Public endpoint) |
