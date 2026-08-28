# 🚀 Swagger & Bruno Guide for `reactandspringpoc/rectangle-backend`

## 📌 Project Details
* **Spring Boot Module**: Rectangle Perimeter & Area Calculation API
* **Server Port**: `8080`

---

## 🌐 1. How to Open & View Swagger UI

1. **Start the Application**:
   ```powershell
   cd reactandspringpoc/rectangle-backend
   mvn spring-boot:run
   ```

2. **Open in Browser**:
   * **Interactive Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
   * **OpenAPI v3 JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

3. **What You Will See in Swagger**:
   * Calculation REST endpoints accepting length & breadth parameters and returning calculated area and perimeter.

---

## 🐶 2. How to Test Using Bruno UI

1. **Launch Bruno UI**.
2. Click **Import Collection** on Bruno home screen -> Choose **OpenAPI V3 Spec**.
3. Select **URL** -> Enter `http://localhost:8080/v3/api-docs` -> Click **Import**.
4. Test Rectangle Calculation endpoint:
   * Send `POST http://localhost:8080/api/rectangle/calculate` (or configured calculation path) with payload:
     ```json
     {
       "length": 10.5,
       "breadth": 5.0
     }
     ```
   * Verify calculated JSON output response (`area: 52.5`, `perimeter: 31.0`).
