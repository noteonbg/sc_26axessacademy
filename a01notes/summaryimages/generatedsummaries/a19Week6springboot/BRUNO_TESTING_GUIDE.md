# Testing Spring Boot Microservices with Bruno API Client

This guide explains how to use **Bruno**—the open-source, Git-friendly API client—to test and demonstrate all Week 6 Spring Boot applications.

---

## 🐶 What is Bruno and Why Use It?

**Bruno** (`https://www.usebruno.com/`) is a modern API client alternative to Postman and Insomnia with a major architectural advantage:

* **Git-Friendly Plain Text (`.bru`)**: Bruno stores API requests as plain text `.bru` files directly inside your source code repository rather than hiding them in proprietary cloud syncs.
* **Offline & Secure**: No forced cloud account creation or data leaks of sensitive banking tokens.
* **Built-In Environment Management**: Easily switch base URLs between `localhost:8080`, `localhost:8081`, `localhost:8082`, `localhost:8083`, and `localhost:8084`.

---

## 📂 Pre-Built Bruno Collection Location

We have pre-created a ready-to-use Bruno API collection for all Week 6 microservices:

**Collection Directory**: **[`F:\scproject\dontpostingit\Campus Content 2026\Week 6\bruno-banking-collection`](file:///F:/scproject/dontpostingit/Campus%20Content%202026/Week%206/bruno-banking-collection)**

### Collection Folder Layout:
```text
bruno-banking-collection/
├── bruno.json                                   # Collection manifest
├── environments/
│   └── Local-Environment.bru                    # Port variables (8080 - 8084)
├── 01-Controller-Demo/
│   ├── Get-All-Customers.bru                    # GET /api/v1/customers (Port 8081)
│   ├── Get-Customer-By-ID.bru                   # GET @PathVariable Demo
│   ├── Create-Customer.bru                      # POST @RequestBody Demo
│   └── Test-Exception-Handler.bru               # GET 404 @ControllerAdvice ProblemDetail
├── 02-Service-Demo/
│   ├── Get-Account.bru                          # GET /api/v1/services/accounts/ACC-101 (Port 8082)
│   ├── Apply-Interest.bru                       # POST Quarterly 4% Interest Calculation
│   └── Transfer-Money.bru                       # POST Validation & Transfer Logic
├── 03-JPA-Demo/
│   ├── Get-Paged-Customers.bru                  # GET Paginated JPA Customers (Port 8083)
│   ├── Update-Email-Dirty-Check.bru             # PUT @Transactional Automatic Dirty Check
│   └── Transactional-Transfer.bru               # POST Atomic Fund Transfer
├── 04-Security-Demo/
│   ├── Public-Info.bru                          # GET /public/info (No Auth - Port 8084)
│   ├── Customer-Balance-Auth.bru                # GET Basic Auth (customer_alice)
│   ├── Teller-Summary-Auth.bru                  # GET Basic Auth (teller_bob)
│   └── Admin-Audit-Logs-Auth.bru                # GET Basic Auth (admin_carol)
└── Integrated-Core-App/
    └── XML-Statement.bru                        # GET XML Statement with Namespaces (Port 8080)
```

---

## 🚀 How to Open and Run the Collection in Bruno

### Step 1: Install Bruno (GUI or CLI)
* **Download Bruno GUI**: Download the free app from [usebruno.com/downloads](https://www.usebruno.com/downloads).
* **Or Install via Package Managers**:
  * Windows (Winget): `winget install usebruno`
  * macOS (Homebrew): `brew install bruno`
  * npm (CLI runner): `npm install -g @usebruno/cli`

---

### Step 2: Open the Collection in Bruno GUI
1. Launch the **Bruno** desktop application.
2. Click **Open Collection** on the home screen.
3. Browse to the folder:
   `F:\scproject\dontpostingit\Campus Content 2026\Week 6\bruno-banking-collection`
4. Select the folder and click **Open**.

---

### Step 3: Select the Local Environment
1. In the upper-right corner of Bruno, click the Environment dropdown menu (defaults to *No Environment*).
2. Select **`Local-Environment`**.
3. This populates base URL variables:
   * `{{core_app_url}}` -> `http://localhost:8080`
   * `{{controller_demo_url}}` -> `http://localhost:8081`
   * `{{service_demo_url}}` -> `http://localhost:8082`
   * `{{jpa_demo_url}}` -> `http://localhost:8083`
   * `{{security_demo_url}}` -> `http://localhost:8084`

---

### Step 4: Execute & Demonstrate Requests

#### 1. Demonstrating REST Controllers (`01-Controller-Demo`)
* Start application on port 8081: `cd 01-banking-controller-demo && mvn spring-boot:run`
* In Bruno, click `01-Controller-Demo` -> **Get All Customers** -> Click **Send** (Returns HTTP 200 JSON array).
* Click **Test @ControllerAdvice Exception Handler** -> Click **Send** (Returns HTTP 404 RFC 7807 `ProblemDetail` payload).

#### 2. Demonstrating Service Layer & Calculations (`02-Service-Demo`)
* Start application on port 8082: `cd 02-banking-service-demo && mvn spring-boot:run`
* In Bruno, click `02-Service-Demo` -> **Apply Quarterly Interest** -> Click **Send** (Calculates 4% interest payout on savings balance).

#### 3. Demonstrating Spring Data JPA & Transactions (`03-JPA-Demo`)
* Start application on port 8083: `cd 03-banking-jpa-demo && mvn spring-boot:run`
* In Bruno, click `03-JPA-Demo` -> **Get Paginated JPA Customers** -> Click **Send** (Returns paginated database results).
* Click **Update Email (@Transactional Dirty Checking)** -> Click **Send** (Updates DB automatically via dirty checking).

#### 4. Demonstrating Spring Security & Role Matrix (`04-Security-Demo`)
* Start application on port 8084: `cd 04-banking-security-demo && mvn spring-boot:run`
* In Bruno, test endpoint permissions:
  * **Customer Balance**: Basic Auth credentials `customer_alice` / `Pass123!` (200 OK).
  * **Teller Summary**: Basic Auth credentials `teller_bob` / `TellerPass2026!` (200 OK).
  * **Admin Audit Logs**: Basic Auth credentials `admin_carol` / `AdminPass2026!` (200 OK).

---

## 💻 Running Bruno Tests via CLI (Automated CI/CD)

Bruno includes a command-line runner `@usebruno/cli` to execute tests in CI/CD terminal pipelines:

```bash
# Run the entire Bruno banking collection from terminal
bru run "F:\scproject\dontpostingit\Campus Content 2026\Week 6\bruno-banking-collection" --env Local-Environment
```

---

## 📝 `.bru` Plain Text File Syntax Syntax Explanation

Below is an example of what a plain text `.bru` file looks like inside the repository (`04-Security-Demo/Teller-Summary-Auth.bru`):

```text
meta {
  name: Teller Daily Summary (Basic Auth - Teller Role)
  type: http
  seq: 3
}

get {
  url: {{security_demo_url}}/api/v1/teller/daily-summary
  body: none
  auth: basic
}

auth:basic {
  username: teller_bob
  password: TellerPass2026!
}
```

* **`meta` block**: Specifies the human-readable request name and sequence in Bruno GUI.
* **`get` block**: Specifies HTTP method, target URL template, and auth mode.
* **`auth:basic` block**: Encapsulates basic authentication credentials.
