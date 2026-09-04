# How to Run the Customer Management Application

This document provides a step-by-step guide on how to set up, build, run, and test the **Customer Management System**, which consists of a **Spring Boot REST Backend** (configured with embedded **H2 in-memory database**) and a **React Frontend** (using Axios).

---

## 📋 Prerequisites

Before running the projects, make sure your computer has the following tools installed:

1. **Java Development Kit (JDK 17 or higher)**
   - Verify by running: `java -version`
2. **Apache Maven (3.8+)**
   - Verify by running: `mvn -v`
3. **Node.js (v18 or higher) and npm**
   - Verify by running: `node -v` and `npm -v`

---

## 🗄️ Embedded H2 Database Setup

No external database installation or setup is required! The application uses an embedded **H2 in-memory database** (`jdbc:h2:mem:bankdb`).

- **Database URL**: `jdbc:h2:mem:bankdb`
- **Driver**: `org.h2.Driver`
- **Username**: `sa`
- **Password**: *(empty)*
- **H2 Console**: Accessible in browser at `http://localhost:4500/h2-console` when backend is running.

---

## 📂 Project Directory Structure

```
reactandspring
├── docs/
│   ├── HOW_TO_RUN.md              <-- This guide
│   ├── SPRING_BOOT_SYNTAX_GUIDE.md<-- Spring Boot code & syntax guide
│   └── REACT_AXIOS_SYNTAX_GUIDE.md<-- React & Axios code & syntax guide
├── customer-backend/              <-- Spring Boot Maven REST API (H2 Embedded DB, Port 4500)
│   ├── pom.xml
│   └── src/
└── customer-frontend/             <-- React Single Page Application (Port 4800)
    ├── .env
    ├── package.json
    ├── public/
    └── src/
```

---

## 🚀 Step-by-Step Execution Guide

### Step 1: Start the Spring Boot Backend

1. Open a terminal window.
2. Navigate to the `customer-backend` folder:
   ```bash
   cd customer-backend
   ```
3. Build the project using Maven:
   ```bash
   mvn clean package
   ```
4. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
5. **Expected Output**:
   - Spring Boot connects to H2 database at `jdbc:h2:file:./data/bankdb`.
   - Hibernate automatically creates/updates the `customers` table (`ddl-auto=update`).
   - Initial seed data (Alice, Bob, Charlie, Diana) is automatically inserted if the table is empty.
   - Tomcat web server starts on **`http://localhost:4500`**.

---

### Step 2: Start the React Frontend

1. Open a **new / separate** terminal window.
2. Navigate to the `customer-frontend` folder:
   ```bash
   cd customer-frontend
   ```
3. Install required npm packages (first time only):
   ```bash
   npm install
   ```
4. Start the React development server:
   ```bash
   npm start
   ```
5. **Expected Output**:
   - React application will open in browser at **`http://localhost:4800`**.
   - Contacts `http://localhost:4500/api/customers` via Axios and renders customer directory.

---

## 🧪 How to Use the Application

### 1. Select / View All Customers
- On opening `http://localhost:4800`, the table displays all existing customers fetched via Spring Boot:
  - **Customer ID** (e.g. `#1`)
  - **Name** (e.g. `Alice Johnson`)
  - **Email** (e.g. `alice@example.com`)
  - **Location** (e.g. `📍 New York`)

### 2. Update a Customer (Rule: Only Email & Location Can Be Updated)
1. Click the **✏️ Edit** button on any customer row.
2. An Edit Modal will appear:
   - **Customer ID** is **disabled / read-only**.
   - **Customer Name** is **disabled / read-only**.
   - **Email Address** is editable.
   - **Location** is editable.
3. Modify email or location (e.g. location to `Chicago`).
4. Click **Save Changes**.
5. A success notification banner appears, and PostgreSQL is updated instantly.
