# 🚀 Bruno UI & Swagger OpenAPI Guide: Testing Spring Boot Projects

This document provides step-by-step instructions on how to access **Swagger UI** for interactive API documentation across all Spring Boot projects in this repository, and how to use the **Bruno UI Tool** (Desktop Application) for API testing and automation.

---

## 📌 Document Overview & Relative Folder Locations

* **Document File Path**: `BRUNO_UI_AND_SWAGGER_GUIDE.md`
* **Pre-Built Bruno Collection Path**: `bruno-banking-collection/`

---

## 🌐 1. Swagger / OpenAPI Configuration & Endpoints Matrix

All Spring Boot microservices in this folder are enabled with **SpringDoc OpenAPI 3 (`springdoc-openapi-starter-webmvc-ui:2.5.0`)**. When you run any project, Swagger UI is automatically hosted on embedded Tomcat.

### 📊 Spring Boot Projects Summary & Swagger URLs

| Project Name | Project Type | Server Port | Swagger UI Endpoint | Raw OpenAPI v3 JSON Spec |
| :--- | :--- | :--- | :--- | :--- |
| **`banking-core-app`** | Integrated Core Application | `8080` | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) | `http://localhost:8080/v3/api-docs` |
| **`01-banking-controller-demo`** | Topic 1: REST Controllers & Exceptions | `8081` | [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) | `http://localhost:8081/v3/api-docs` |
| **`02-banking-service-demo`** | Topic 2: Business Logic & DI | `8082` | [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) | `http://localhost:8082/v3/api-docs` |
| **`03-banking-jpa-demo`** | Topic 3: Spring Data JPA & DB | `8083` | [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html) | `http://localhost:8083/v3/api-docs` |
| **`04-banking-security-demo`** | Topic 4: Spring Security & Auth | `8084` | [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html) | `http://localhost:8084/v3/api-docs` |
| **`05-banking-jwt-security-demo`** | Topic 5: Stateless JWT Auth & Bearer Security | `8085` | [http://localhost:8085/swagger-ui/index.html](http://localhost:8085/swagger-ui/index.html) | `http://localhost:8085/v3/api-docs` |
| **`06-banking-qualifier-beans-demo`** | Topic 6: Ambiguous Beans & @Qualifier Demo | `8086` | [http://localhost:8086/swagger-ui/index.html](http://localhost:8086/swagger-ui/index.html) | `http://localhost:8086/v3/api-docs` |
| **`reactandspring/customer-backend`** | Customer Management Fullstack | `8080` | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) | `http://localhost:8080/v3/api-docs` |
| **`reactandspringpoc/rectangle-backend`** | Rectangle PA Calculation | `8080` | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) | `http://localhost:8080/v3/api-docs` |
| **`00-maven-core-demo`** | Pure Maven Java CLI (Non-Web) | N/A | *Not Applicable (Core Java Console App)* | *N/A* |

---

## 🐶 2. What is Bruno UI Tool?

**Bruno** is an open-source, lightweight, fast, Git-friendly API client (a modern alternative to Postman or Insomnia).

### Key Advantages of Bruno:
1. **Plain Text Format (`.bru`)**: Requests are stored directly in your codebase as human-readable `.bru` text files.
2. **Git Version Control**: Collections can be checked into Git alongside your Java code.
3. **No Account Required / Privacy**: Runs 100% locally on your machine without mandatory cloud accounts or token leakage.
4. **Fast UI**: Instant startup time with zero bloat.

---

## 🛠️ 3. How to Install Bruno UI

Choose any of the following installation methods:

* **Official Installer (Windows/macOS/Linux)**:
  Download the installer from [https://www.usebruno.com/downloads](https://www.usebruno.com/downloads)
* **Windows Package Manager (winget)**:
  ```powershell
  winget install usebruno
  ```
* **macOS (Homebrew)**:
  ```bash
  brew install bruno
  ```
* **Node.js CLI Runner (Optional)**:
  ```bash
  npm install -g @usebruno/cli
  ```

---

## 🖥️ 4. Basic Step-by-Step Instructions: How to Open & Use Bruno UI

### Step 1: Launch Bruno UI
Open the **Bruno** application from your Start Menu or desktop shortcut.

### Step 2: Open the Pre-built Banking Collection
1. On the Bruno home screen, click **"Open Collection"**.
2. Browse to the folder:
   `bruno-banking-collection`
3. Click **Select Folder** (or **Open**).
4. The sidebar will populate with pre-configured requests organized by topic:
   * 📁 `01-Controller-Demo`
   * 📁 `02-Service-Demo`
   * 📁 `03-JPA-Demo`
   * 📁 `04-Security-Demo`
   * 📁 `05-JWT-Security-Demo`
   * 📁 `Integrated-Core-App`

---

### Step 3: Activate the Environment (`Local-Environment`)
1. Look at the top-right corner of the Bruno UI window (it will say **"No Environment"** by default).
2. Click the Environment dropdown menu.
3. Select **`Local-Environment`**.
4. This binds environment variables to their respective microservice ports:
   * `{{core_app_url}}` = `http://localhost:8080`
   * `{{controller_demo_url}}` = `http://localhost:8081`
   * `{{service_demo_url}}` = `http://localhost:8082`
   * `{{jpa_demo_url}}` = `http://localhost:8083`
   * `{{security_demo_url}}` = `http://localhost:8084`
   * `{{jwt_demo_url}}` = `http://localhost:8085`

---

### Step 4: Run a Spring Boot Application
Before sending requests in Bruno, start the target Spring Boot application in your terminal or IDE:

```powershell
# Example: Start 01-banking-controller-demo (Port 8081)
cd 01-banking-controller-demo
mvn spring-boot:run
```

---

### Step 5: Execute Requests & View Responses
1. In Bruno's left sidebar, expand `01-Controller-Demo`.
2. Click **Get-All-Customers**.
3. Notice the HTTP method **GET** and target URL `{{controller_demo_url}}/api/v1/customers`.
4. Click the blue **Send** button (or press `Ctrl + Enter`).
5. Inspect the response in the right pane:
   * **Status Code**: `200 OK`
   * **Headers**: `Content-Type: application/json`
   * **Body**: Array of customer JSON objects.

---

## ⚡ 5. Creating a New Request in Bruno UI

If you want to test new endpoints manually:

1. Right-click any collection folder in the sidebar -> Select **New Request**.
2. Fill in request details:
   * **Name**: e.g., `Create New Account`
   * **Type**: `HTTP`
   * **Method**: `POST`
   * **URL**: `http://localhost:8080/api/v1/accounts`
3. Click **Create**.
4. Navigate to tabs in the center panel:
   * **Body**: Select `JSON` and enter request payload:
     ```json
     {
       "accountNumber": "ACC-9999",
       "accountType": "SAVINGS",
       "balance": 1500.00
     }
     ```
   * **Auth**: Select `Basic Auth` or `Bearer Token` for secured endpoints.
   * **Headers**: Add custom headers if required (e.g. `Accept: application/xml`).
5. Click **Send** to test!

---

## 📥 6. Importing Swagger OpenAPI Spec directly into Bruno UI

Bruno UI allows importing Swagger / OpenAPI definitions directly to generate full collections automatically!

1. Make sure your Spring Boot application is running (e.g. on port `8080`).
2. In Bruno UI, click **Import Collection** on the home screen (or main menu).
3. Choose **OpenAPI V3 Spec**.
4. Input Source:
   * **URL**: Enter `http://localhost:8080/v3/api-docs`
   * Or download the spec file and select **File**.
5. Click **Import**.
6. Bruno will instantly build a structured collection of all endpoints defined in your Spring Boot Controllers!

---

## 🛡️ 7. Testing Security & Role Matrix in Bruno UI

For `04-banking-security-demo` (Port 8084), test role-based permissions in Bruno UI under `04-Security-Demo`:

| Request Name | Endpoint | Auth Credentials | Expected Result |
| :--- | :--- | :--- | :--- |
| **Public Info** | `GET /api/v1/public/info` | None | `200 OK` |
| **Customer Balance** | `GET /api/v1/customer/balance` | Basic Auth: `customer_alice` / `Pass123!` | `200 OK` |
| **Teller Summary** | `GET /api/v1/teller/daily-summary` | Basic Auth: `teller_bob` / `TellerPass2026!` | `200 OK` |
| **Admin Audit Logs** | `GET /api/v1/admin/audit-logs` | Basic Auth: `admin_carol` / `AdminPass2026!` | `200 OK` |
| **Unauthorized Test** | `GET /api/v1/admin/audit-logs` | Basic Auth: `customer_alice` / `Pass123!` | `403 Forbidden` |

---

## 💡 Summary of Files & Paths

* **Swagger / OpenAPI Documentation Guide**: `BRUNO_UI_AND_SWAGGER_GUIDE.md`
* **Bruno API Collection**: `bruno-banking-collection/`
