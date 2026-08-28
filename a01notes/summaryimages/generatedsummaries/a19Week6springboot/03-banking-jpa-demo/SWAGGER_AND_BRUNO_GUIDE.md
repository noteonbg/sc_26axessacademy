# 🚀 Swagger, H2 Console & Bruno Guide for `03-banking-jpa-demo`

## 📌 Project Details
* **Spring Boot Module**: Spring Data JPA, Single-Table Employee CRUD, Pagination & Transactions Focus
* **Server Port**: `8083`
* **H2 Console Guide**: `H2_CONSOLE_GUIDE.md`

---

## 🌐 1. How to Open & View Swagger UI

1. **Start the Application**:
   ```powershell
   cd 03-banking-jpa-demo
   mvn spring-boot:run
   ```

2. **Open in Browser**:
   * **Interactive Swagger UI**: [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html)
   * **OpenAPI v3 JSON Spec**: [http://localhost:8083/v3/api-docs](http://localhost:8083/v3/api-docs)

3. **What You Will See in Swagger**:
   * **Single-Table Employee CRUD Endpoints (`/api/v1/jpa/employees`)**:
     * `GET /api/v1/jpa/employees`
     * `GET /api/v1/jpa/employees/{id}`
     * `POST /api/v1/jpa/employees`
     * `PUT /api/v1/jpa/employees/{id}`
     * `DELETE /api/v1/jpa/employees/{id}`
     * `GET /api/v1/jpa/employees/department/{department}`
   * Customer & Account JPA endpoints demonstrating pagination, dirty checking, and transfers.

---

## 🗄️ 2. How to Access the H2 Database Console

1. Open browser to **[http://localhost:8083/h2-console](http://localhost:8083/h2-console)**
2. Connection settings:
   * **Driver Class**: `org.h2.Driver`
   * **JDBC URL**: `jdbc:h2:file:./data/bankingjpadb;AUTO_SERVER=TRUE`
   * **User Name**: `sa`
   * **Password**: *(leave empty)*
3. Click **Connect**. View tables: `EMPLOYEES`, `CUSTOMERS`, `ACCOUNTS`.
4. See full step-by-step SQL queries in `H2_CONSOLE_GUIDE.md`.

---

## 🐶 3. How to Test Using Bruno UI

1. Launch Bruno UI and open collection:
   `bruno-banking-collection`
2. Select **`Local-Environment`** in top-right dropdown.
3. Expand **`03-JPA-Demo`** folder:
   * **Get-All-Employees**: `GET http://localhost:8083/api/v1/jpa/employees`
   * **Get-Employee-By-ID**: `GET http://localhost:8083/api/v1/jpa/employees/1`
   * **Create-Employee**: `POST http://localhost:8083/api/v1/jpa/employees`
   * **Update-Employee**: `PUT http://localhost:8083/api/v1/jpa/employees/1`
   * **Delete-Employee**: `DELETE http://localhost:8083/api/v1/jpa/employees/3`
   * **Get-Paged-Customers**: `GET http://localhost:8083/api/v1/jpa/customers?page=0&size=5`
