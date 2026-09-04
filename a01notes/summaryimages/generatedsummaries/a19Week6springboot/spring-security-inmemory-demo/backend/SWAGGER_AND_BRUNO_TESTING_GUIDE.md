# Comprehensive Testing Guide: Swagger UI & Bruno API Client
## Testing Spring Security In-Memory Authorization (`f1`, `f2`, `f3`)

This document provides step-by-step instructions for testing the **Spring Security In-Memory Demo** endpoints using **Swagger UI** (in your browser) and **Bruno API Client** (standalone desktop HTTP client).

---

## 1. Quick Testing Reference Matrix

Before testing, review the expected HTTP status codes and response bodies for each endpoint:

| Endpoint | Method | Caller Credentials | Role | Expected Status Code | Expected Response Body |
| :--- | :---: | :--- | :--- | :---: | :--- |
| `/api/f3` | `GET` | *None* (Unauthenticated) | *Public* | **`200 OK`** | `"f3 at work"` |
| `/api/f1` | `GET` | *None* (Unauthenticated) | *None* | **`401 Unauthorized`** | Security Challenge / Empty |
| `/api/f1` | `GET` | `admin` / `admin123` | `ADMIN` | **`200 OK`** | `"f1 at work"` |
| `/api/f1` | `GET` | `user` / `user123` | `USER` | **`403 Forbidden`** | Access Denied |
| `/api/f2` | `GET` | *None* (Unauthenticated) | *None* | **`401 Unauthorized`** | Security Challenge / Empty |
| `/api/f2` | `GET` | `user` / `user123` | `USER` | **`200 OK`** | `"f2 at work"` |
| `/api/f2` | `GET` | `admin` / `admin123` | `ADMIN` | **`403 Forbidden`** | Access Denied |

---

## 2. Testing with Swagger UI (Interactive Browser)

### Step 2.1: Start the Spring Boot Application
Open your terminal and run:
```bash
cd F:\poc\spring-security-inmemory-demo
mvn spring-boot:run
```

### Step 2.2: Open Swagger UI
Open your web browser and navigate to:
👉 `http://localhost:6080/swagger-ui.html`

You will see the Swagger UI page header **"Spring Security In-Memory Demo API"** with a green **"Authorize"** button at the top right.

---

### Step 2.3: Test Public Endpoint `GET /api/f3`
1. Expand the **`GET /api/f3`** endpoint block.
2. Click the **Try it out** button.
3. Click the blue **Execute** button.
4. **Verification**:
   - **Server Response Code**: `200`
   - **Response Body**: `"f3 at work"`
   - *Note*: No login or authorization header was required.

---

### Step 2.4: Test Unauthenticated Access to `GET /api/f1`
1. Expand **`GET /api/f1`**.
2. Click **Try it out** -> Click **Execute** *(Without clicking Authorize)*.
3. **Verification**:
   - **Server Response Code**: `401` (Unauthorized)

---

### Step 2.5: Authorize as Admin (User A) and Test `GET /api/f1`
1. Scroll to the top of the Swagger page and click the green **Authorize** button.
2. In the modal popup under **`basicAuth (http, Basic)`**:
   - **Username**: `admin`
   - **Password**: `admin123`
3. Click **Authorize**, then click **Close**.
4. Expand **`GET /api/f1`** -> Click **Execute**.
5. **Verification**:
   - **Server Response Code**: `200`
   - **Response Body**: `"f1 at work"`

---

### Step 2.6: Test Role Restriction on `GET /api/f2` as Admin
1. While still authenticated as `admin`, expand **`GET /api/f2`**.
2. Click **Try it out** -> Click **Execute**.
3. **Verification**:
   - **Server Response Code**: `403` (Forbidden)
   - *Reason*: `admin` has `ROLE_ADMIN`, but `/api/f2` requires `ROLE_USER`.

---

### Step 2.7: Re-authorize as Normal User (User B) and Test `GET /api/f2`
1. Click the **Authorize** button at top right.
2. Click **Logout**.
3. Enter new credentials:
   - **Username**: `user`
   - **Password**: `user123`
4. Click **Authorize**, then click **Close**.
5. Expand **`GET /api/f2`** -> Click **Execute**.
6. **Verification**:
   - **Server Response Code**: `200`
   - **Response Body**: `"f2 at work"`

---

## 3. Testing with Bruno API Client (Step-by-Step Manual Setup)

Bruno is an open-source, offline desktop API client. Follow these steps to manually set up and test all requests without needing to import any pre-existing collections.

---

### Step 3.1: Create a Collection in Bruno
1. Open the **Bruno** application.
2. Click **Create Collection** on the home screen.
3. Enter Details:
   - **Name**: `Spring-Security-Inmemory-API`
   - **Location**: Select any folder on your computer.
4. Click **Create**.

---

### Step 3.2: Request 1 — Public Access (`GET /api/f3`)
1. In your collection, click **+ Add Request** (or right-click collection $\rightarrow$ *New Request*).
2. Configure Request:
   - **Request Type**: `HTTP`
   - **Name**: `1. Public Endpoint f3`
   - **Method**: `GET`
   - **URL**: `http://localhost:6080/api/f3`
3. Click **Create**.
4. Go to the **Auth** tab and ensure it is set to **No Auth** (or *Inherit*).
5. Click the blue **Send** button (or press `Ctrl + Enter`).
6. **Expected Result**:
   - **Status**: `200 OK`
   - **Response**: `"f3 at work"`

---

### Step 3.3: Request 2 — Admin Calling `f1` (`GET /api/f1`)
1. Click **+ Add Request**.
   - **Name**: `2. Admin calling f1`
   - **Method**: `GET`
   - **URL**: `http://localhost:6080/api/f1`
2. Go to the **Auth** tab:
   - Select **Basic Auth** from the dropdown.
   - **Username**: `admin`
   - **Password**: `admin123`
3. Click **Send**.
4. **Expected Result**:
   - **Status**: `200 OK`
   - **Response**: `"f1 at work"`

---

### Step 3.4: Request 3 — Normal User Calling `f1` (Forbidden Verification)
1. Click **+ Add Request**.
   - **Name**: `3. User calling f1 (Forbidden)`
   - **Method**: `GET`
   - **URL**: `http://localhost:6080/api/f1`
2. Go to the **Auth** tab:
   - Select **Basic Auth**.
   - **Username**: `user`
   - **Password**: `user123`
3. Click **Send**.
4. **Expected Result**:
   - **Status**: `403 Forbidden`
   - **Response**: Access Denied (user lacks `ROLE_ADMIN`).

---

### Step 3.5: Request 4 — Normal User Calling `f2` (`GET /api/f2`)
1. Click **+ Add Request**.
   - **Name**: `4. User calling f2`
   - **Method**: `GET`
   - **URL**: `http://localhost:6080/api/f2`
2. Go to the **Auth** tab:
   - Select **Basic Auth**.
   - **Username**: `user`
   - **Password**: `user123`
3. Click **Send**.
4. **Expected Result**:
   - **Status**: `200 OK`
   - **Response**: `"f2 at work"`

---

### Step 3.6: Request 5 — Admin Calling `f2` (Forbidden Verification)
1. Click **+ Add Request**.
   - **Name**: `5. Admin calling f2 (Forbidden)`
   - **Method**: `GET`
   - **URL**: `http://localhost:6080/api/f2`
2. Go to the **Auth** tab:
   - Select **Basic Auth**.
   - **Username**: `admin`
   - **Password**: `admin123`
3. Click **Send**.
4. **Expected Result**:
   - **Status**: `403 Forbidden`
   - **Response**: Access Denied (admin lacks `ROLE_USER`).

---

## 4. cURL Command Quick Reference

You can also run these exact test cases directly from your terminal or command prompt using `curl`:

```bash
# 1. Test Public Endpoint f3 (Unauthenticated) -> Returns 200 OK "f3 at work"
curl -i http://localhost:6080/api/f3

# 2. Test f1 with Admin Credentials -> Returns 200 OK "f1 at work"
curl -i -u admin:admin123 http://localhost:6080/api/f1

# 3. Test f1 with Normal User Credentials -> Returns 403 Forbidden
curl -i -u user:user123 http://localhost:6080/api/f1

# 4. Test f2 with Normal User Credentials -> Returns 200 OK "f2 at work"
curl -i -u user:user123 http://localhost:6080/api/f2

# 5. Test f2 with Admin Credentials -> Returns 403 Forbidden
curl -i -u admin:admin123 http://localhost:6080/api/f2
```
