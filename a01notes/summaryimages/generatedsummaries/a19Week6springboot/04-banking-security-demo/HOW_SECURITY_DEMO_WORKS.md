# 🛡️ How `04-banking-security-demo` Works: Step-by-Step Architecture Guide

This document provides a numbered, step-by-step technical explanation of how Spring Security 6 architecture, HTTP Basic Authentication, and Role-Based Access Control (RBAC) operate in **`04-banking-security-demo`**.

---

## 📌 Numbered Steps: Execution Architecture & Request Lifecycle

### Step 1: Spring Security Servlet Filter Chain Pipeline (`SecurityFilterChain`)
When an HTTP request hits port `8084`, it enters Spring Security's Servlet Filter Chain (`DelegatingFilterProxy`).
* **Configuration Class**: [`BankingSecurityConfig.java`](file:///F:/scproject/sc_26axessacademy/a01notes/summaryimages/generatedsummaries/a19Week6springboot/04-banking-security-demo/src/main/java/com/standardchartered/securitydemo/config/BankingSecurityConfig.java)
* `@EnableWebSecurity` registers the custom `SecurityFilterChain` bean into the Tomcat filter pipeline.

---

### Step 2: Disabling CSRF & Framing Options
```java
.csrf(csrf -> csrf.disable())
.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
```
* **CSRF Disabled**: Cross-Site Request Forgery protection is disabled because REST APIs are stateless and consume JSON/HTTP headers rather than HTML session cookies.
* **Frame Options**: Enables H2 Console to load within HTML `<frame>` / `<iframe>` tags from the same origin (`http://localhost:8084`).

---

### Step 3: URL Pattern Authorization Rules (`authorizeHttpRequests`)
Spring Security evaluates incoming request URI paths sequentially against matching rules:
```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/h2-console/**", "/api/v1/public/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
    .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
    .requestMatchers("/api/v1/teller/**").hasAnyRole("TELLER", "ADMIN")
    .requestMatchers("/api/v1/customer/**").hasAnyRole("CUSTOMER", "TELLER", "ADMIN")
    .anyRequest().authenticated()
)
```
1. **Public Paths (`permitAll()`)**: `/h2-console/**`, `/api/v1/public/**`, `/swagger-ui/**`, and `/v3/api-docs/**` bypass authentication checks.
2. **Role-Restricted Paths**:
   * `/admin/**`: Requires GrantedAuthority `ROLE_ADMIN`.
   * `/teller/**`: Requires GrantedAuthority `ROLE_TELLER` or `ROLE_ADMIN`.
   * `/customer/**`: Requires GrantedAuthority `ROLE_CUSTOMER`, `ROLE_TELLER`, or `ROLE_ADMIN`.
3. **`anyRequest().authenticated()`**: Any unmatched path requires a valid authenticated user.

---

### Step 4: Processing HTTP Basic Authentication Header
When accessing a protected endpoint (e.g., `GET /api/v1/customer/balance`), Spring Security expects an HTTP header:
```text
Authorization: Basic Y3VzdG9tZXJfYWxpY2U6UGFzczEyMyE=
```
* `.httpBasic(Customizer.withDefaults())` activates `BasicAuthenticationFilter`.
* The filter decodes the Base64 string into `username` (`customer_alice`) and `password` (`Pass123!`).

---

### Step 5: Password Encoding & BCrypt Salting
* **Configuration Bean**: `PasswordEncoder passwordEncoder()` in `BankingSecurityConfig.java`.
* Returns `new BCryptPasswordEncoder()`, which hashes passwords using strong random salting.
* Raw passwords are never stored in memory or database; BCrypt matches raw passwords against hashed strings (`$2a$10$...`).

---

### Step 6: User Authentication via `UserDetailsService` & Database Lookup
Spring Security delegates credential verification to `DaoAuthenticationProvider`:
* **Service Class**: [`BankingUserDetailsService.java`](file:///F:/scproject/sc_26axessacademy/a01notes/summaryimages/generatedsummaries/a19Week6springboot/04-banking-security-demo/src/main/java/com/standardchartered/securitydemo/service/BankingUserDetailsService.java)
1. Calls `loadUserByUsername(String username)`.
2. Queries H2 database via `SecurityUserRepository.findByUsername(username)`.
3. Wraps the returned `SecurityUserEntity` into a Spring Security `UserDetails` implementation ([`BankingUserDetails.java`](file:///F:/scproject/sc_26axessacademy/a01notes/summaryimages/generatedsummaries/a19Week6springboot/04-banking-security-demo/src/main/java/com/standardchartered/securitydemo/security/BankingUserDetails.java)).

---

### Step 7: Authority Mapping (`GrantedAuthority`)
Inside `BankingUserDetails.java`, the user's role string (e.g. `"ROLE_CUSTOMER"`) is converted into a GrantedAuthority object:
```java
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(userEntity.getRole()));
}
```

---

### Step 8: SecurityContext Population & Controller Dispatch
1. If BCrypt password check succeeds, an `Authentication` object (`UsernamePasswordAuthenticationToken`) is marked as **authenticated = true**.
2. Spring Security stores this in the current thread's `SecurityContextHolder`:
   ```java
   SecurityContextHolder.getContext().setAuthentication(auth);
   ```
3. Request proceeds to [`SecureBankingEndpointsController.java`](file:///F:/scproject/sc_26axessacademy/a01notes/summaryimages/generatedsummaries/a19Week6springboot/04-banking-security-demo/src/main/java/com/standardchartered/securitydemo/controller/SecureBankingEndpointsController.java), where `SecurityContextHolder` is read to identify the current logged-in user!

---

## 📊 Summary Role Matrix in `04-banking-security-demo`

| Username | Password | Role (`GrantedAuthority`) | Access to `/customer/**` | Access to `/teller/**` | Access to `/admin/**` |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `customer_alice` | `Pass123!` | `ROLE_CUSTOMER` | 🟢 Allowed (`200 OK`) | 🔴 Denied (`403 Forbidden`) | 🔴 Denied (`403 Forbidden`) |
| `teller_bob` | `TellerPass2026!` | `ROLE_TELLER` | 🟢 Allowed (`200 OK`) | 🟢 Allowed (`200 OK`) | 🔴 Denied (`403 Forbidden`) |
| `admin_carol` | `AdminPass2026!` | `ROLE_ADMIN` | 🟢 Allowed (`200 OK`) | 🟢 Allowed (`200 OK`) | 🟢 Allowed (`200 OK`) |
