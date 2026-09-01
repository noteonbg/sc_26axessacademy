# How to Run Unit and Integration Tests

This document provides step-by-step instructions on how to execute unit tests and integration tests for both the **Spring Boot Backend** (`rectangle-backend`) and **React Frontend** (`rectangle-frontend`) in `reactandspringpoc`.

---

## 1. Running Spring Boot Backend Tests (`rectangle-backend`)

### Prerequisites
- Java 17+ or Java 21 Installed
- Apache Maven installed (`mvn`)

### Steps to Execute Tests:

1. **Navigate to the Backend Directory:**
   ```bash
   cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\reactandspringpoc\rectangle-backend
   ```

2. **Run All Unit & Integration Tests:**
   ```bash
   mvn test
   ```

3. **Expected Test Execution Summary:**
   - `RectangleServiceImplTest`: Evaluates math logic ($A = L \times B$, $P = 2(L+B)$) and `ZeroDimensionsException`.
   - `RectangleControllerIntegrationTest`: Evaluates full HTTP POST `/api/rectangle/calculate` REST API request-response flow with `MockMvc`.

4. **Verify Output:**
   Look for `BUILD SUCCESS` with:
   ```text
   [INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
   [INFO] BUILD SUCCESS
   ```

---

## 2. Running React Frontend Tests (`rectangle-frontend`)

### Prerequisites
- Node.js (v18+) and npm installed

### Steps to Execute Tests:

1. **Navigate to the Frontend Directory:**
   ```bash
   cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot\reactandspringpoc\rectangle-frontend
   ```

2. **Install Dependencies (if not already installed):**
   ```bash
   npm install
   ```

3. **Run All Unit & Integration Tests (Non-Interactive CI Mode):**
   ```bash
   npm test -- --watchAll=false
   ```

4. **Expected Test Execution Summary:**
   - `App.test.js`: Unit tests verifying initial input field state and controlled component changes.
   - `App.integration.test.js`: Integration tests verifying mocked Axios API calls, calculation result rendering, and exception banner handling.

5. **Verify Output:**
   Look for:
   ```text
   PASS src/App.test.js
   PASS src/App.integration.test.js

   Test Suites: 2 passed, 2 total
   Tests:       5 passed, 5 total
   ```
