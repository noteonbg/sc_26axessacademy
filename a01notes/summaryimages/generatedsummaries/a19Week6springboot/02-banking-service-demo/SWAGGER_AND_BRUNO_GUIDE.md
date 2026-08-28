# 🚀 Swagger & Bruno Guide for `02-banking-service-demo`

## 📌 Project Details
* **Spring Boot Module**: Service Layer & Business Calculations Focus
* **Server Port**: `8082`

---

## 🌐 1. How to Open & View Swagger UI

1. **Start the Application**:
   ```powershell
   cd 02-banking-service-demo
   mvn spring-boot:run
   ```

2. **Open in Browser**:
   * **Interactive Swagger UI**: [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html)
   * **OpenAPI v3 JSON Spec**: [http://localhost:8082/v3/api-docs](http://localhost:8082/v3/api-docs)

3. **What You Will See in Swagger**:
   * All endpoints for account management, interest calculations, and fund transfers.
   * Parameter schemas for calculation inputs and response objects.

---

## 🐶 2. How to Test Using Bruno UI

1. **Open Bruno UI App**.
2. Click **Open Collection** -> Select:
   `bruno-banking-collection`
3. Set Environment to **`Local-Environment`**.
4. Expand the folder **`02-Service-Demo`**:
   * Click **Get-Account** -> Click **Send** (`GET http://localhost:8082/api/v1/services/accounts/ACC-101`).
   * Click **Apply-Interest** -> Click **Send** (Calculates 4% quarterly interest credit).
   * Click **Transfer-Money** -> Click **Send** (Executes business validation and transfer).
