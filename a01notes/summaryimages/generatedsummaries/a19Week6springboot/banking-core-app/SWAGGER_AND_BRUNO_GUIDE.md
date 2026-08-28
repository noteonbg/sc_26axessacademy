# 🚀 Swagger & Bruno Guide for `banking-core-app`

## 📌 Project Details
* **Spring Boot Module**: Integrated Financial Core Banking Application
* **Server Port**: `8080`

---

## 🌐 1. How to Open & View Swagger UI

1. **Start the Application**:
   ```powershell
   cd banking-core-app
   mvn spring-boot:run
   ```

2. **Open in Browser**:
   * **Interactive Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
   * **OpenAPI v3 JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

3. **What You Will See in Swagger**:
   * Complete REST endpoints for Customer Management, Account Transfers, and XML Financial Statement generation.

---

## 🐶 2. How to Test Using Bruno UI

1. **Open Bruno UI App**.
2. Open collection: `bruno-banking-collection`
3. Select **`Local-Environment`**.
4. Expand the folder **`Integrated-Core-App`**:
   * Click **XML-Statement** -> Click **Send** (`GET http://localhost:8080/api/v1/xml/statement`).
   * Inspect response headers: `Content-Type: application/xml`.
   * Verify XML payload structure and banking XML namespaces.
