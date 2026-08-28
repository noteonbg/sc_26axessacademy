# 🚀 Swagger & Bruno Guide for `06-banking-qualifier-beans-demo`

## 📌 Project Details
* **Spring Boot Module**: Ambiguous Beans Resolution & `@Qualifier` Demo
* **Server Port**: `8086`

---

## 🌐 1. How to Open & View Swagger UI

1. **Start the Application**:
   ```powershell
   cd 06-banking-qualifier-beans-demo
   mvn spring-boot:run
   ```

2. **Open in Browser**:
   * **Interactive Swagger UI**: [http://localhost:8086/swagger-ui/index.html](http://localhost:8086/swagger-ui/index.html)
   * **OpenAPI v3 JSON Spec**: [http://localhost:8086/v3/api-docs](http://localhost:8086/v3/api-docs)

3. **What You Will See in Swagger**:
   * **Payment Gateway Controller Endpoints (`/api/v1/payments`)**:
     * `POST /api/v1/payments/primary`: Demonstrates `@Primary` default bean (`CreditCardPaymentGateway`).
     * `POST /api/v1/payments/qualifier/paypal`: Demonstrates `@Qualifier("payPalGateway")`.
     * `POST /api/v1/payments/custom-qualifier/upi`: Demonstrates custom `@Upi` qualifier annotation.
     * `POST /api/v1/payments/dynamic/{gatewayType}`: Demonstrates dynamic lookup via `Map<String, PaymentGatewayService>`.
     * `GET /api/v1/payments/all-gateways`: Returns list of all registered gateway bean names in Spring container.

---

## 🐶 2. How to Test Using Bruno UI

1. **Launch Bruno UI App**.
2. Open collection: `bruno-banking-collection`
3. Select **`Local-Environment`** in the top-right corner.
4. Expand folder **`06-Qualifier-Beans-Demo`**:
   * **Test-Primary-Gateway**: Send `POST http://localhost:8086/api/v1/payments/primary?amount=150.00`.
   * **Test-Qualifier-PayPal**: Send `POST http://localhost:8086/api/v1/payments/qualifier/paypal?amount=299.99`.
   * **Test-Custom-Qualifier-UPI**: Send `POST http://localhost:8086/api/v1/payments/custom-qualifier/upi?amount=500.00`.
   * **Test-Dynamic-Gateway-Lookup**: Send `POST http://localhost:8086/api/v1/payments/dynamic/cryptoGateway?amount=1000.00`.
   * **Get-All-Registered-Gateways**: Send `GET http://localhost:8086/api/v1/payments/all-gateways`.
