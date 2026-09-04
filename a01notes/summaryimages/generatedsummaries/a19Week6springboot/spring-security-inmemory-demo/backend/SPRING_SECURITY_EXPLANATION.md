# Deep Dive Explanation: Spring Security In-Memory Architecture, User Detection & Role Authorization

This document provides a comprehensive, step-by-step technical explanation of the **Spring Security In-Memory Demo** project. It explains **what** was done, **why** it was done, **how Spring Security detects users**, **how roles are mapped**, and **how access decision evaluation works for endpoints `f1`, `f2`, and `f3`**.

---

## 1. Architectural Summary & Component Map

The application consists of 4 main layers working together:

```
                  +--------------------------------------------------+
                  |               Incoming HTTP Request              |
                  +--------------------------------------------------+
                                           |
                                           v
                  +--------------------------------------------------+
                  |            Spring Security Filter Chain          |
                  |  1. BasicAuthenticationFilter (User Detection)   |
                  |  2. AuthorizationFilter     (Role Evaluation)    |
                  +--------------------------------------------------+
                                  /        |        \
                                 /         |         \
                    /api/f1 (ADMIN)  /api/f2 (USER)  /api/f3 (Public)
                               /           |           \
                              v            v            v
                       +-------------+------------+------------+
                       |   f1()      |    f2()    |    f3()    |
                       |  "f1 at     |   "f2 at   |   "f3 at   |
                       |   work"     |    work"   |   work"    |
                       +-------------+------------+------------+
```

---

## 2. How Users Are Defined and Detected

### 2.1 In-Memory User Definition (`UserDetailsService`)

In a database-backed application, Spring Security queries a database table (like `users` and `roles`). For this demo, **no database is used**. Instead, users are defined in-memory using Spring Security's `InMemoryUserDetailsManager` bean in `SecurityConfig.java`:

```java
@Bean
public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    UserDetails adminUser = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin123"))
            .roles("ADMIN")      // Maps internally to authority "ROLE_ADMIN"
            .build();

    UserDetails normalUser = User.builder()
            .username("user")
            .password(passwordEncoder.encode("user123"))
            .roles("USER")       // Maps internally to authority "ROLE_USER"
            .build();

    return new InMemoryUserDetailsManager(adminUser, normalUser);
}
```

### Why Password Encoding Is Mandatory
Spring Security 5+ and 6+ enforce password encoding by default. If raw plain-text passwords were used without a `PasswordEncoder`, Spring Security would throw an `java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"`. 

We configure `BCryptPasswordEncoder` as a bean:
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```
When `adminUser` is created, `"admin123"` is hashed using BCrypt (`$2a$10$...`). When a user sends HTTP requests, Spring Security hashes the incoming password and compares it against the stored BCrypt hash.

---

### 2.2 How User Authentication Works at Runtime (HTTP Basic Auth)

When a client sends a request with HTTP Basic Authentication, the browser or API client sends an `Authorization` HTTP header:

```http
Authorization: Basic YWRtaW46YWRtaW4xMjM=
```
*(Where `YWRtaW46YWRtaW4xMjM=` is the Base64 encoding of `admin:admin123`)*

#### Step-by-Step Runtime User Detection Flow:
1. **`BasicAuthenticationFilter` Intercepts Request**:
   - Inspects the request header for `Authorization: Basic ...`.
   - Decodes Base64 to extract raw credentials: `username = "admin"`, `password = "admin123"`.
2. **Delegates to `AuthenticationManager` / `DaoAuthenticationProvider`**:
   - Calls `userDetailsService.loadUserByUsername("admin")`.
   - `InMemoryUserDetailsManager` looks up `"admin"` in memory and retrieves the `UserDetails` object.
3. **Password Validation**:
   - Compares the incoming password `"admin123"` against the BCrypt hashed password using `passwordEncoder.matches(...)`.
4. **Populates `SecurityContextHolder`**:
   - Upon successful verification, Spring Security creates a `UsernamePasswordAuthenticationToken` containing:
     - **Principal**: `admin`
     - **Credentials**: `[PROTECTED]`
     - **Authorities**: `[ROLE_ADMIN]`
   - This token is placed in `SecurityContextHolder.getContext().setAuthentication(...)`.

---

## 3. How Roles Are Detected and Prefixed

In Spring Security, there is a key distinction between **Roles** and **GrantedAuthorities**:

- **GrantedAuthority**: Fine-grained permission strings (e.g., `READ_PORTFOLIO`, `WRITE_TRANSACTION`, `ROLE_ADMIN`).
- **Role**: High-level grouping of authorities. 

When you use `.roles("ADMIN")` in the `User` builder:
```java
User.builder().username("admin").roles("ADMIN").build();
```
Spring Security **automatically prepends `ROLE_`** to the string. Thus:
- `.roles("ADMIN")` $\rightarrow$ Granted Authority: **`ROLE_ADMIN`**
- `.roles("USER")`  $\rightarrow$ Granted Authority: **`ROLE_USER`**

When configuring authorization rules:
- `hasRole("ADMIN")` automatically checks for authority `ROLE_ADMIN`.
- `hasAuthority("ROLE_ADMIN")` is identical in behavior to `hasRole("ADMIN")`.

---

## 4. How Access Control Is Determined for `f1`, `f2`, and `f3`

Authorization rules are defined in `SecurityConfig.java` using `SecurityFilterChain`:

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            // 1. PUBLIC ACCESS RULE
            .requestMatchers("/api/f3").permitAll()
            
            // 2. SWAGGER PUBLIC ACCESS RULE
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            
            // 3. ADMIN ROLE ACCESS RULE
            .requestMatchers("/api/f1").hasRole("ADMIN")
            
            // 4. USER ROLE ACCESS RULE
            .requestMatchers("/api/f2").hasRole("USER")
            
            // 5. CATCH-ALL RULE
            .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults());

    return http.build();
}
```

Here is the exact evaluation logic executed by Spring Security's `AuthorizationFilter` for each endpoint:

---

### 4.1 Access Decision for `f3` (`GET /api/f3`)

- **Rule**: `.requestMatchers("/api/f3").permitAll()`
- **Evaluation Mechanism**:
  1. `AuthorizationFilter` matches incoming request path `/api/f3`.
  2. Rule is `permitAll()`.
  3. Spring Security **bypasses authentication checks** entirely for this path.
  4. The request immediately reaches `DemoController.f3()`.
- **Result**: Returns HTTP `200 OK` with string `"f3 at work"` for **all callers** (unauthenticated anonymous users, `user`, and `admin`).

---

### 4.2 Access Decision for `f1` (`GET /api/f1`)

- **Rule**: `.requestMatchers("/api/f1").hasRole("ADMIN")`
- **Evaluation Matrix**:

| Caller Credentials | Authentication Status | Granted Authorities | Access Decision | HTTP Response Code |
| :--- | :--- | :--- | :--- | :--- |
| **Unauthenticated (No Header)** | Anonymous | `[ROLE_ANONYMOUS]` | **Denied** (Unauthenticated) | **`401 Unauthorized`** |
| **User B (`user` / `user123`)** | Authenticated | `[ROLE_USER]` | **Denied** (Role Mismatch) | **`403 Forbidden`** |
| **User A (`admin` / `admin123`)** | Authenticated | `[ROLE_ADMIN]` | **GRANTED** (Role Match) | **`200 OK` ("f1 at work")** |

- **Step-by-Step Security Evaluation Process**:
  1. Request to `/api/f1` arrives.
  2. If no `Authorization` header is provided $\rightarrow$ `BasicAuthenticationFilter` leaves user as `AnonymousAuthenticationToken`. `AuthorizationFilter` sees requirement `hasRole("ADMIN")` is missing $\rightarrow$ Returns **`401 Unauthorized`**.
  3. If User B (`user`) logs in $\rightarrow$ `SecurityContext` has authority `ROLE_USER`. `AuthorizationFilter` checks if `ROLE_USER` contains `ROLE_ADMIN` $\rightarrow$ Evaluation fails $\rightarrow$ Returns **`403 Forbidden`**.
  4. If User A (`admin`) logs in $\rightarrow$ `SecurityContext` has authority `ROLE_ADMIN`. `AuthorizationFilter` checks if `ROLE_ADMIN` contains `ROLE_ADMIN` $\rightarrow$ Evaluation passes $\rightarrow$ Request proceeds to `DemoController.f1()` returning **`200 OK: f1 at work`**.

---

### 4.3 Access Decision for `f2` (`GET /api/f2`)

- **Rule**: `.requestMatchers("/api/f2").hasRole("USER")`
- **Evaluation Matrix**:

| Caller Credentials | Authentication Status | Granted Authorities | Access Decision | HTTP Response Code |
| :--- | :--- | :--- | :--- | :--- |
| **Unauthenticated (No Header)** | Anonymous | `[ROLE_ANONYMOUS]` | **Denied** (Unauthenticated) | **`401 Unauthorized`** |
| **User A (`admin` / `admin123`)** | Authenticated | `[ROLE_ADMIN]` | **Denied** (Role Mismatch) | **`403 Forbidden`** |
| **User B (`user` / `user123`)** | Authenticated | `[ROLE_USER]` | **GRANTED** (Role Match) | **`200 OK` ("f2 at work")** |

- **Step-by-Step Security Evaluation Process**:
  1. Request to `/api/f2` arrives.
  2. If User A (`admin`) calls `/api/f2` $\rightarrow$ Granted authority is `ROLE_ADMIN`. Requirement is `ROLE_USER`. Since User A does not have `ROLE_USER`, Spring Security blocks the request and returns **`403 Forbidden`**.
  3. If User B (`user`) calls `/api/f2` $\rightarrow$ Granted authority is `ROLE_USER`. Requirement is `ROLE_USER`. Evaluation passes $\rightarrow$ Returns **`200 OK: f2 at work`**.

---

## 5. How Swagger OpenAPI Integration Works

To enable interactive API testing directly within Swagger UI (`http://localhost:6080/swagger-ui.html`):

1. **Permit Swagger Endpoint Access**:
   ```java
   .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
   ```
   *Why*: Allows the Swagger HTML/JS bundle and OpenAPI JSON definition to load in the browser without prompting for credentials when opening the page.

2. **Register Basic Security Scheme (`OpenApiConfig.java`)**:
   ```java
   @SecurityScheme(
       name = "basicAuth",
       type = SecuritySchemeType.HTTP,
       scheme = "basic"
   )
   ```
   *Why*: Instructs Swagger UI to display the **"Authorize"** lock button at the top of the documentation page, allowing you to enter username/password (`admin`/`admin123` or `user`/`user123`) and attach HTTP Basic Auth headers to outgoing test requests automatically.

3. **Annotate Restricted Controller Methods (`DemoController.java`)**:
   ```java
   @Operation(
       summary = "Function f1 (ADMIN only)",
       security = @SecurityRequirement(name = "basicAuth")
   )
   ```
   *Why*: Connects the endpoint in Swagger UI to the `basicAuth` security scheme so Swagger displays a lock icon next to `/api/f1` and `/api/f2`.

---

## 6. Summary Verification Checklist

| Aspect | Detail | Verified Outcome |
| :--- | :--- | :---: |
| **User Storage** | `InMemoryUserDetailsManager` | Hardcoded in RAM, zero DB overhead |
| **Password Encoder** | `BCryptPasswordEncoder` | Securely hashes passwords before comparison |
| **Role Detection** | `.roles("ADMIN")` $\rightarrow$ `ROLE_ADMIN` | Automatically prefixed and checked by `AuthorizationFilter` |
| **Endpoint `f1`** | `/api/f1` | Accessible ONLY by `admin` (Returns 200 OK: `"f1 at work"`) |
| **Endpoint `f2`** | `/api/f2` | Accessible ONLY by `user` (Returns 200 OK: `"f2 at work"`) |
| **Endpoint `f3`** | `/api/f3` | Accessible by ANYONE (Returns 200 OK: `"f3 at work"`) |
| **Swagger UI** | Enabled at `/swagger-ui.html` | Public page loading with Basic Auth lock controls |
