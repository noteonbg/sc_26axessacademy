# Complete Step-by-Step Full-Stack Guide: Spring Boot & React Calculator Application

This guide provides **ultra-detailed, step-by-step instructions** to build a Spring Boot and React application from scratch. Every single step explains **WHAT** we are doing and **WHY** we are doing it.

---

## Overview & Architectural Design

### What are we building?
We are building a full-stack Web Application consisting of:
1. **Spring Boot Backend**: A RESTful API that accepts calculator inputs (two numbers and an operation symbol) wrapped inside a **single JSON object** via HTTP POST, validates the inputs, performs calculation, and returns a JSON response object.
2. **React Frontend**: A web user interface that collects user input and sends it to the Spring Boot backend using Axios.

### Why are we structuring it this way?
1. **Why a Single Object in POST request body (`@RequestBody`)?**
   Sending data as a structured JSON payload in the POST request body ensures the payload remains clean, readable, type-safe, and easy to extend.
2. **Why use `ResponseEntity` as the Controller return type?**
   `ResponseEntity` allows full control over HTTP status codes. We can dynamically return `HTTP 200 OK` on successful calculation or `HTTP 400 Bad Request` when validation rules are violated.
3. **Why place Axios API calls in a separate JS file (`calculatorService.js`) instead of inside the React Component?**
   - **Separation of Concerns**: UI components should focus purely on rendering views and managing local form state.
   - **Reusability & Maintainability**: Placing network logic in a dedicated service file isolates API endpoints, base URLs, and error handling into a single, clean module.

---

## SECTION 1: Spring Boot Backend (Step-by-Step from Scratch)

### Step 1.1: Create Project Folder Structure

**WHAT WE ARE DOING:**
Creating the exact folder hierarchy for Java packages.

**WHY WE ARE DOING IT:**
Java classes require package names (e.g., `package com.example.calculator.dto;`) that strictly correspond to their physical folder paths on disk.

1. Open your terminal or file explorer.
2. Create a root folder named `calculator-backend`.
3. Inside `calculator-backend`, create the standard Maven directory path:
   `src/main/java/com/example/calculator`
4. Inside `com/example/calculator`, create two sub-folders:
   - `dto` *(Data Transfer Objects)*
   - `controller` *(REST API Controllers)*

**Directory Tree to Verify:**
```text
calculator-backend/
├── pom.xml                        <-- Maven Build & Dependency Configuration
└── src/
    └── main/
        └── java/
            └── com/
                └── example/
                    └── calculator/
                        ├── dto/
                        └── controller/
```

---

### Step 1.2: Verify Maven Dependencies in `pom.xml`

**WHAT WE ARE DOING:**
Checking and verifying that the required Maven dependencies are present in your `pom.xml` file in the root of `calculator-backend`.

**WHY WE ARE DOING IT:**
`pom.xml` is Maven's Project Object Model file. It defines project metadata, Java version (Java 17), and declares all required dependencies (`spring-boot-starter-web` for REST API endpoints and embedded Tomcat server, and `spring-boot-starter-test`).

1. Open `pom.xml` located in your project root directory (`calculator-backend/`).
2. Check that the `<dependencies>` section includes `spring-boot-starter-web` (and `spring-boot-starter-test`). If missing, ensure your `pom.xml` matches the configuration below:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!-- Spring Boot Parent Starter: Provides default dependency versions and plugin management -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.3</version>
        <relativePath/>
    </parent>

    <groupId>com.example</groupId>
    <artifactId>calculator-backend</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>calculator-backend</name>
    <description>Spring Boot Calculator Backend</description>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <!-- WHAT THIS DOES: Enables REST API development, Jackson JSON serialization, & Tomcat embedded server -->
        <!-- WHY NEEDED: Essential for @RestController, @PostMapping, ResponseEntity, and JSON payload handling -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- WHAT THIS DOES: Provides JUnit 5 and Spring Boot Test utilities -->
        <!-- WHY NEEDED: Enables unit and integration testing of endpoints -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Maven plugin to package the Spring Boot app into an executable JAR file -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

---

### Step 1.3: Create Request DTO (`CalculateRequest.java`)

**WHAT WE ARE DOING:**
Creating a Java class named `CalculateRequest.java` inside the `dto` folder.

**WHY WE ARE DOING IT:**
The requirement specifies that the controller must accept two numbers and a symbol as **one single object** in POST mode. Spring Boot uses Jackson to automatically deserialize incoming request JSON into this object.

1. Navigate to: `src/main/java/com/example/calculator/dto/`
2. Create a new file named: `CalculateRequest.java`
3. Paste the code below:

```java
package com.example.calculator.dto;

/**
 * WHAT THIS FILE DOES:
 * Encapsulates the incoming request JSON object.
 * 
 * WHY WE CREATED THIS:
 * Fulfills requirement to pass two numbers and symbol as ONE single object.
 */
public class CalculateRequest {
    private double num1;
    private double num2;
    private String symbol;

    // Default constructor required by Spring Jackson JSON mapper
    public CalculateRequest() {}

    // Parameterized constructor
    public CalculateRequest(double num1, double num2, String symbol) {
        this.num1 = num1;
        this.num2 = num2;
        this.symbol = symbol;
    }

    // Getters and Setters allow Spring to populate and read JSON properties
    public double getNum1() {
        return num1;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public double getNum2() {
        return num2;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
```

---

### Step 1.4: Create Response DTO (`CalculateResponse.java`)

**WHAT WE ARE DOING:**
Creating a Java class named `CalculateResponse.java` inside the `dto` folder.

**WHY WE ARE DOING IT:**
The requirement states the backend must return a result object containing a status `message` string and the numeric `result`.

1. Navigate to: `src/main/java/com/example/calculator/dto/`
2. Create a new file named: `CalculateResponse.java`
3. Paste the code below:

```java
package com.example.calculator.dto;

/**
 * WHAT THIS FILE DOES:
 * Encapsulates the outgoing response JSON payload.
 * 
 * WHY WE CREATED THIS:
 * Returns a standardized structure containing both a descriptive status message
 * and the calculated numeric result (or null if an error occurs).
 */
public class CalculateResponse {
    private String message;
    private Double result; // Double wrapper class allows returning null when 4xx error occurs

    public CalculateResponse() {}

    public CalculateResponse(String message, Double result) {
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
```

---

### Step 1.5: Create REST Controller (`CalculatorController.java`)

**WHAT WE ARE DOING:**
Creating `CalculatorController.java` inside the `controller` folder.

**WHY WE ARE DOING IT:**
This class exposes the HTTP POST endpoint `/api/calculate`. It validates whether the symbol is `+` or `-`, checks if both numbers are zero, returns `HTTP 400 Bad Request` for invalid requests, and returns `HTTP 200 OK` with the calculation result for valid requests using `ResponseEntity`.

1. Navigate to: `src/main/java/com/example/calculator/controller/`
2. Create a new file named: `CalculatorController.java`
3. Paste the code below:

```java
package com.example.calculator.controller;

import com.example.calculator.dto.CalculateRequest;
import com.example.calculator.dto.CalculateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * WHAT THIS FILE DOES:
 * REST Controller providing POST endpoint /api/calculate.
 * 
 * WHY WE USE ResponseEntity:
 * Allows us to explicitly return HTTP 200 (OK) for valid operations and HTTP 400 (Bad Request)
 * for invalid input conditions.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // WHY: Prevents CORS security block when React calls Spring
public class CalculatorController {

    @PostMapping("/calculate")
    public ResponseEntity<CalculateResponse> calculate(@RequestBody CalculateRequest request) {

        // --- VALIDATION RULE 1: Symbol must be '+' or '-' ---
        // WHY: Requirement states valid symbols can only be '+' or '-'.
        if (request.getSymbol() == null || 
           (!request.getSymbol().equals("+") && !request.getSymbol().equals("-"))) {
            
            // Return 4xx HTTP error status (400 Bad Request)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CalculateResponse("Invalid operation symbol! Only '+' and '-' are allowed.", null));
        }

        // --- VALIDATION RULE 2: Both numbers cannot be zero ---
        // WHY: Requirement states if both numbers are zero, return a 4xx error code.
        if (request.getNum1() == 0 && request.getNum2() == 0) {
            
            // Return 4xx HTTP error status (400 Bad Request)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CalculateResponse("Invalid calculation! Both numbers cannot be zero.", null));
        }

        // --- PERFORM CALCULATION ---
        double result;
        if ("+".equals(request.getSymbol())) {
            result = request.getNum1() + request.getNum2();
        } else {
            result = request.getNum1() - request.getNum2();
        }

        // --- RETURN HTTP 200 OK WITH SUCCESS OBJECT ---
        // WHY: Successful calculations must return HTTP 200 status code with result object.
        return ResponseEntity.ok(new CalculateResponse("Calculation performed successfully", result));
    }
}
```

---

### Step 1.6: Create Main Application Class (`CalculatorApplication.java`)

**WHAT WE ARE DOING:**
Creating the entry point class for Spring Boot.

**WHY WE ARE DOING IT:**
Launches the embedded Tomcat server on port 8080.

1. Navigate to: `src/main/java/com/example/calculator/`
2. Create a file named: `CalculatorApplication.java`
3. Paste the code below:

```java
package com.example.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }
}
```

---

## SECTION 2: React Frontend (Step-by-Step from Scratch)

### Step 2.1: Initialize React App & Folders

**WHAT WE ARE DOING:**
Creating the React project and organizing the `src` folder structure.

**WHY WE ARE DOING IT:**
Separates network API services from UI components cleanly.

1. Open your terminal in your workspace directory.
2. Run command to create React app:
   `npx create-react-app calculator-frontend`
3. Change into project directory:
   `cd calculator-frontend`
4. Install `axios` package:
   `npm install axios`
5. Inside `src/`, create two sub-folders:
   - `src/services`
   - `src/components`

**Directory Tree to Verify:**
```text
calculator-frontend/
├── package.json                   <-- NPM Project Dependencies & Scripts Configuration
└── src/
    ├── services/
    │   └── calculatorService.js   <-- Separate JS file for Axios call
    ├── components/
    │   └── Calculator.jsx         <-- React UI Component file
    ├── App.js
    └── index.js
```

---

### Step 2.2: Verify Node Package Dependencies in `package.json`

**WHAT WE ARE DOING:**
Checking and verifying that the required NPM package dependencies are present in your `package.json` file in the root of `calculator-frontend`.

**WHY WE ARE DOING IT:**
`package.json` defines all JavaScript libraries required by the React application. It includes `react`, `react-dom`, `react-scripts`, and `axios` (the HTTP library required for communicating with the Spring Boot backend).

1. Open `package.json` located in your project root directory (`calculator-frontend/`).
2. Check that `"dependencies"` includes `axios` (along with `react`, `react-dom`, and `react-scripts`). If any are missing, add them or run `npm install axios`, ensuring your `package.json` matches the configuration below:

```json
{
  "name": "calculator-frontend",
  "version": "0.1.0",
  "private": true,
  "dependencies": {
    "axios": "^1.6.8",
    "react": "^18.2.0",
    "react-dom": "^18.2.0",
    "react-scripts": "5.0.1",
    "web-vitals": "^2.1.4"
  },
  "scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build",
    "test": "react-scripts test",
    "eject": "react-scripts eject"
  },
  "eslintConfig": {
    "extends": [
      "react-app",
      "react-app/jest"
    ]
  },
  "browserslist": {
    "production": [
      ">0.2%",
      "not dead",
      "not op_mini all"
    ],
    "development": [
      "last 1 chrome version",
      "last 1 firefox version",
      "last 1 safari version"
    ]
  }
}
```

---

### Step 2.3: Create Separate Axios Service (`src/services/calculatorService.js`)

**WHAT WE ARE DOING:**
Creating `calculatorService.js` inside `src/services/`.

**WHY WE ARE DOING IT:**
The prompt explicitly states: *"ensure call is made using axios and done in a separate js and not in the component code"*. Isolating HTTP calls makes the code reusable and clean.

1. Navigate to: `src/services/`
2. Create file named: `calculatorService.js`
3. Paste the code below:

```javascript
import axios from 'axios';

// Base URL of the Spring Boot REST API
const API_BASE_URL = 'http://localhost:8080/api';

/**
 * WHAT THIS FUNCTION DOES:
 * Sends an HTTP POST request to Spring Boot via Axios.
 * 
 * WHY IT IS IN A SEPARATE JS FILE:
 * Strictly separates network communication logic from UI component code.
 * 
 * @param {Object} calculateData Object containing { num1, num2, symbol }
 * @returns {Promise<Object>} Response payload containing { message, result }
 */
export const calculateNumbers = async (calculateData) => {
  try {
    // Send POST request with single object payload
    const response = await axios.post(`${API_BASE_URL}/calculate`, calculateData);
    
    // Return backend response object on 200 OK
    return response.data;
  } catch (error) {
    // Catch 4xx error responses returned from Spring Boot ResponseEntity
    if (error.response && error.response.data) {
      // Throw backend error response payload to be caught by UI component
      throw error.response.data;
    }
    
    // Fallback for network connection failure
    throw { message: 'Network error or Spring Boot backend is offline.', result: null };
  }
};
```

---

### Step 2.4: Create React UI Component (`src/components/Calculator.jsx`)

**WHAT WE ARE DOING:**
Creating `Calculator.jsx` inside `src/components/`.

**WHY WE ARE DOING IT:**
Provides an interactive HTML form for user input, imports `calculateNumbers` from `calculatorService.js` to execute the POST call, and displays returned results or 4xx error messages.

1. Navigate to: `src/components/`
2. Create file named: `Calculator.jsx`
3. Paste the code below:

```jsx
import React, { useState } from 'react';
// IMPORTING THE SEPARATE JS SERVICE MODULE:
// WHY: We delegate the Axios POST call to calculatorService.js instead of writing inline axios calls here.
import { calculateNumbers } from '../services/calculatorService';

const Calculator = () => {
  // Input State
  const [num1, setNum1] = useState(0);
  const [num2, setNum2] = useState(0);
  const [symbol, setSymbol] = useState('+');

  // Response & Status State
  const [responseMsg, setResponseMsg] = useState('');
  const [result, setResult] = useState(null);
  const [isError, setIsError] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Clear previous results
    setResponseMsg('');
    setResult(null);
    setIsError(false);

    // Prepare single payload object
    const payload = {
      num1: Number(num1),
      num2: Number(num2),
      symbol: symbol
    };

    try {
      // WHAT WE ARE DOING: Invoking API call function imported from calculatorService.js
      // WHY: Keeps UI code clean and avoids inline Axios calls.
      const data = await calculateNumbers(payload);
      
      // Update state for HTTP 200 OK response
      setResponseMsg(data.message);
      setResult(data.result);
      setIsError(false);
    } catch (err) {
      // Update state for HTTP 4xx Bad Request error
      setResponseMsg(err.message || 'An error occurred during calculation.');
      setResult(null);
      setIsError(true);
    }
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.heading}>Spring Boot & React Calculator</h2>

      <form onSubmit={handleSubmit}>
        <div style={styles.fieldGroup}>
          <label style={styles.label}>Number 1:</label>
          <input
            type="number"
            value={num1}
            onChange={(e) => setNum1(e.target.value)}
            style={styles.input}
            required
          />
        </div>

        <div style={styles.fieldGroup}>
          <label style={styles.label}>Operation Symbol:</label>
          <select 
            value={symbol} 
            onChange={(e) => setSymbol(e.target.value)}
            style={styles.select}
          >
            <option value="+">+ (Addition)</option>
            <option value="-">- (Subtraction)</option>
            <option value="*">* (Test Invalid Symbol)</option>
            <option value="/">/ (Test Invalid Symbol)</option>
          </select>
        </div>

        <div style={styles.fieldGroup}>
          <label style={styles.label}>Number 2:</label>
          <input
            type="number"
            value={num2}
            onChange={(e) => setNum2(e.target.value)}
            style={styles.input}
            required
          />
        </div>

        <button type="submit" style={styles.button}>
          Calculate
        </button>
      </form>

      {/* Response Display Box */}
      {responseMsg && (
        <div style={{
          ...styles.responseBox,
          backgroundColor: isError ? '#f8d7da' : '#d4edda',
          color: isError ? '#721c24' : '#155724',
          borderColor: isError ? '#f5c6cb' : '#c3e6cb'
        }}>
          <p><strong>HTTP Status:</strong> {isError ? '400 Bad Request' : '200 OK'}</p>
          <p><strong>Message:</strong> {responseMsg}</p>
          {result !== null && <p><strong>Result:</strong> {result}</p>}
        </div>
      )}
    </div>
  );
};

// Styling rules for clean UI layout
const styles = {
  container: {
    maxWidth: '400px',
    margin: '40px auto',
    padding: '24px',
    borderRadius: '8px',
    border: '1px solid #ddd',
    boxShadow: '0 4px 6px rgba(0,0,0,0.1)',
    fontFamily: 'Arial, sans-serif'
  },
  heading: { textAlign: 'center', marginBottom: '20px' },
  fieldGroup: { marginBottom: '14px' },
  label: { display: 'block', marginBottom: '6px', fontWeight: 'bold' },
  input: { width: '100%', padding: '8px', boxSizing: 'border-box', borderRadius: '4px', border: '1px solid #ccc' },
  select: { width: '100%', padding: '8px', boxSizing: 'border-box', borderRadius: '4px', border: '1px solid #ccc' },
  button: { width: '100%', padding: '10px', backgroundColor: '#007bff', color: '#fff', border: 'none', borderRadius: '4px', fontSize: '16px', cursor: 'pointer' },
  responseBox: { marginTop: '20px', padding: '12px', borderRadius: '6px', border: '1px solid' }
};

export default Calculator;
```

---

### Step 2.5: Mount Component in `App.js`

**WHAT WE ARE DOING:**
Updating `src/App.js` to render `<Calculator />`.

**WHY WE ARE DOING IT:**
`App.js` is the root component executed by React on startup.

1. Open `src/App.js`.
2. Replace content with:

```jsx
import React from 'react';
import Calculator from './components/Calculator';

function App() {
  return (
    <div>
      <Calculator />
    </div>
  );
}

export default App;
```

---

## SECTION 3: Step-by-Step Testing & Verification

| # | Scenario | Form Data Input | Expected HTTP Status Code | Expected Response Payload |
|---|---|---|---|---|
| 1 | **Valid Addition** | `num1=10`, `num2=5`, `symbol="+"` | **`200 OK`** | `{"message": "Calculation performed successfully", "result": 15.0}` |
| 2 | **Valid Subtraction** | `num1=20`, `num2=8`, `symbol="-"` | **`200 OK`** | `{"message": "Calculation performed successfully", "result": 12.0}` |
| 3 | **Both Numbers Zero** | `num1=0`, `num2=0`, `symbol="+"` | **`400 Bad Request`** | `{"message": "Invalid calculation! Both numbers cannot be zero.", "result": null}` |
| 4 | **Invalid Symbol** | `num1=5`, `num2=3`, `symbol="*"` | **`400 Bad Request`** | `{"message": "Invalid operation symbol! Only '+' and '-' are allowed.", "result": null}` |
