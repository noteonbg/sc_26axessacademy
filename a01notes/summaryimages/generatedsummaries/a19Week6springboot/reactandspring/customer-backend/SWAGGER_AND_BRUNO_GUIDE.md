# 🚀 Swagger & Bruno Guide for `reactandspring/customer-backend`

## 📌 Project Details
* **Spring Boot Module**: Customer Management Spring Boot Backend API (for React Frontend integration)
* **Server Port**: `8080`

---

## 🌐 1. How to Open & View Swagger UI

1. **Start the Application**:
   ```powershell
   cd reactandspring/customer-backend
   mvn spring-boot:run
   ```

2. **Open in Browser**:
   * **Interactive Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
   * **OpenAPI v3 JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

3. **What You Will See in Swagger**:
   * CRUD endpoints for Customer entity management (`GET /api/customers`, `POST /api/customers`, `DELETE /api/customers/{id}`).

---

## 🐶 2. How to Test Using Bruno UI

1. **Launch Bruno UI**.
2. Click **Import Collection** on Bruno home screen -> Choose **OpenAPI V3 Spec**.
3. Select **URL** -> Enter `http://localhost:8080/v3/api-docs` -> Click **Import**.
4. Test generated requests:
   * **Get All Customers**: Send `GET http://localhost:8080/api/customers`.
   * **Add Customer**: Send `POST http://localhost:8080/api/customers` with JSON payload:
     ```json
     {
       "name": "John Doe",
       "email": "john.doe@example.com"
     }
     ```
