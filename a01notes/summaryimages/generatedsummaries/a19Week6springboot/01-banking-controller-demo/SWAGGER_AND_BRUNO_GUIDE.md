# 🚀 Swagger & Bruno Guide for `01-banking-controller-demo`

## 📌 Project Details
* **Spring Boot Module**: REST Controller & Exception Handler Focus
* **Server Port**: `8081`

---

## 🌐 1. How to Open & View Swagger UI

1. **Start the Application**:
   ```powershell
   cd 01-banking-controller-demo
   mvn spring-boot:run
   ```

2. **Open in Browser**:
   * **Interactive Swagger UI**: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
   * **OpenAPI v3 JSON Spec**: [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)

3. **What You Will See in Swagger**:
   * All endpoints defined in `CustomerController`:
     * `GET /api/v1/customers`
     * `GET /api/v1/customers/{id}`
     * `POST /api/v1/customers`
     * `GET /api/v1/customers/error-demo`
   * Click **Try it out** -> **Execute** on any endpoint to test directly in the browser!

---

## 🐶 2. How to Test Using Bruno UI

1. **Open Bruno UI App**.
2. Click **Open Collection** -> Browse to:
   `bruno-banking-collection`
3. Select **`Local-Environment`** in the top-right corner.
4. Expand the folder **`01-Controller-Demo`**:
   * Click **Get-All-Customers** -> Click **Send** (`GET http://localhost:8081/api/v1/customers`).
   * Click **Get-Customer-By-ID** -> Click **Send** (`GET http://localhost:8081/api/v1/customers/101`).
   * Click **Create-Customer** -> Inspect request body JSON -> Click **Send**.
   * Click **Test-Exception-Handler** -> Click **Send** (Inspect `404 Not Found` RFC 7807 `ProblemDetail` payload).
