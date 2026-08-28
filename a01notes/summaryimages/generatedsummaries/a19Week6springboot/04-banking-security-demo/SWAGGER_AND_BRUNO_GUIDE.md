# 🚀 Swagger & Bruno Guide for `04-banking-security-demo`

## 📌 Project Details
* **Spring Boot Module**: Spring Security Architecture & Role-Based Authorization Focus
* **Server Port**: `8084`

---

## 🌐 1. How to Open & View Swagger UI

1. **Start the Application**:
   ```powershell
   cd 04-banking-security-demo
   mvn spring-boot:run
   ```

2. **Open in Browser**:
   * **Interactive Swagger UI**: [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html) *(Publicly permitted in BankingSecurityConfig)*
   * **OpenAPI v3 JSON Spec**: [http://localhost:8084/v3/api-docs](http://localhost:8084/v3/api-docs)

3. **What You Will See in Swagger**:
   * Public, Customer, Teller, and Admin endpoints.
   * Note: Executing secured endpoints inside Swagger UI requires clicking **Authorize** and entering Basic Auth credentials (e.g. `admin_carol` / `AdminPass2026!`).

---

## 🐶 2. How to Test Using Bruno UI & Basic Authentication

1. **Open Bruno UI App**.
2. Open collection: `bruno-banking-collection`
3. Select **`Local-Environment`**.
4. Expand the folder **`04-Security-Demo`**:

### Credentials Matrix for Testing:
| Bruno Request Name | Target Endpoint | Required Role | Credentials (Username / Password) | Expected HTTP Status |
| :--- | :--- | :--- | :--- | :--- |
| **Public-Info** | `GET /api/v1/public/info` | Unauthenticated | None | `200 OK` |
| **Customer-Balance-Auth** | `GET /api/v1/customer/balance` | `ROLE_CUSTOMER` | `customer_alice` / `Pass123!` | `200 OK` |
| **Teller-Summary-Auth** | `GET /api/v1/teller/daily-summary` | `ROLE_TELLER` | `teller_bob` / `TellerPass2026!` | `200 OK` |
| **Admin-Audit-Logs-Auth** | `GET /api/v1/admin/audit-logs` | `ROLE_ADMIN` | `admin_carol` / `AdminPass2026!` | `200 OK` |

5. Test authorization failure:
   * Select `Admin-Audit-Logs-Auth` -> Change username in `Auth` tab to `customer_alice` -> Click **Send** -> Result: `403 Forbidden`.
