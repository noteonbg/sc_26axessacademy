# 🚀 Swagger & Bruno Guide for `05-banking-jwt-security-demo`

## 📌 Project Details
* **Spring Boot Module**: Stateless JWT Authentication & Bearer Security
* **Server Port**: `8085`

---

## 🌐 1. How to Use Swagger UI with JWT Bearer Token

1. **Start the Application**:
   ```powershell
   cd 05-banking-jwt-security-demo
   mvn spring-boot:run
   ```

2. **Open Swagger UI in Browser**:
   * **Interactive Swagger UI**: [http://localhost:8085/swagger-ui/index.html](http://localhost:8085/swagger-ui/index.html)
   * **OpenAPI v3 JSON Spec**: [http://localhost:8085/v3/api-docs](http://localhost:8085/v3/api-docs)

3. **Step-by-Step JWT Authorization in Swagger UI**:
   * **Step A**: Expand `auth-controller` -> Click `POST /api/v1/auth/login`.
   * **Step B**: Click **Try it out**, enter credentials:
     ```json
     {
       "username": "customer_alice",
       "password": "Pass123!"
     }
     ```
   * **Step C**: Click **Execute**. Copy the returned `token` string from the JSON response.
   * **Step D**: Scroll to top of Swagger UI page -> Click green **Authorize** button.
   * **Step E**: In the `bearerAuth` field, paste your JWT token -> Click **Authorize** -> Click **Close**.
   * **Step F**: Expand `protected-banking-controller` -> Click `GET /api/v1/account/profile` -> Click **Execute** -> Returns `200 OK` with user details!

---

## 🐶 2. How to Test Using Bruno UI

1. **Open Bruno UI App**.
2. Open collection: `bruno-banking-collection`
3. Expand folder **`05-JWT-Security-Demo`**:
   * **Login-JWT**: Send `POST http://localhost:8085/api/v1/auth/login` to obtain JWT token.
   * **Get-Profile-JWT**: Send `GET http://localhost:8085/api/v1/account/profile` with `Authorization: Bearer <jwt-token>` header.
