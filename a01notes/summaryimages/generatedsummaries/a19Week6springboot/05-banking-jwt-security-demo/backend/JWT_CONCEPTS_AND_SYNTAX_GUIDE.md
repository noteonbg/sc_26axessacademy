# 🔑 Master Guide: JWT Concepts & Java Implementation Syntax

This guide explains **stateless authentication using JSON Web Tokens (JWT)** in Spring Boot. It follows a strict **"Concept First, Syntax Next"** approach so you master the theory before diving into Java code line by line.

---

# 🧠 PART 1: THE CONCEPTS (Understand the Theory First)

## 1. What is a JSON Web Token (JWT)?
A **JSON Web Token (JWT)** is an open standard (RFC 7519) that defines a compact and self-contained format for securely transmitting information between parties as a digitally signed JSON object.

### Why Stateless JWT vs Stateful HTTP Sessions?
* **Stateful Sessions (Traditional)**: Server creates a session in memory, generates a Session ID (`JSESSIONID`), and stores it in a server lookup table.
  * *Drawback*: Scalability bottleneck. If you run 10 microservices, every service must share session storage (Redis/Memcached).
* **Stateless JWT (Modern Microservices)**: Server stores **ZERO** session state. When user logs in, server issues a digitally signed JWT token. The client sends this token in every HTTP request header (`Authorization: Bearer <token>`). Any server instance can instantly verify the token using a shared secret key without querying a database!

---

## 2. Anatomy of a JWT Token
A JWT token is a single string composed of three distinct parts separated by dots (`.`):

```text
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdXN0b21lcl9hbGljZSIsInJvbGVzIjoiW1JPTEVfQ1VTVE9NRVJdIiwiaWF0IjoxNzE2ODg4MDAwLCJleHAiOjE3MTY5NzQ0MDB9.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

| Token Component | Formatted Content | Purpose |
| :--- | :--- | :--- |
| **1. Header** (Red) | `{"alg": "HS256", "typ": "JWT"}` | Specifies hashing algorithm (HMAC SHA-256) and token type. Base64Url encoded. |
| **2. Payload** (Green) | `{"sub": "customer_alice", "roles": "[ROLE_CUSTOMER]", "exp": 1716974400}` | Contains **Claims** (Subject/Username, Roles, IssuedAt, Expiration timestamp). Base64Url encoded. |
| **3. Signature** (Blue) | `HMACSHA256(Header + "." + Payload, SecretKey)` | Digitally signs header + payload using a secret key held only by the backend server. Prevents tampering! |

---

## 3. JWT Authentication Lifecycle Flow

```text
+-------------------+                  +---------------------+                  +-----------------------+
|   Client / UI     |                  | AuthController      |                  | JwtAuthenticationFilter|
+-------------------+                  +---------------------+                  +-----------------------+
          |                                       |                                         |
          | --- 1. POST /api/v1/auth/login -----> |                                         |
          |    (username & password)             |                                         |
          |                                       | -- 2. Verify Credentials against DB     |
          |                                       | -- 3. Sign JWT with Secret Key          |
          | <--- 4. Return JWT Token ------------- |                                         |
          |    {"token": "eyJhbG...",             |                                         |
          |     "type": "Bearer"}                 |                                         |
          |                                                                                 |
          |                                                                                 |
          | --- 5. GET /api/v1/account/profile --------------------------------------------> |
          |    Header: "Authorization: Bearer eyJhbG..."                                   |
          |                                                                                 | -- 6. Strip "Bearer "
          |                                                                                 | -- 7. Validate Signature
          |                                                                                 | -- 8. Extract Username & Roles
          |                                                                                 | -- 9. Set SecurityContext
          | <--- 10. Return HTTP 200 Protected Resource Data ----------------------------- |
```

---

# 💻 PART 2: THE SYNTAX (Code Implementation & What Each Line Does)

Below is the code breakdown for each component in **`05-banking-jwt-security-demo`**.

---

## Component 1: `application.properties` (JWT Secret & Expiration Configuration)

### 📄 Configuration Code:
```properties
app.jwt.secret=StandardCharteredSecretKeyForJWTAuthentication2026SuperSecureKeyWith256BitsLength!
app.jwt.expiration-ms=86400000
```

### 🔍 What the Syntax Does:
* **`app.jwt.secret`**: Defines a 256-bit (32+ character) secret string used by HMAC-SHA256 algorithm to sign and verify tokens.
* **`app.jwt.expiration-ms`**: Sets token lifespan to `86,400,000` ms (24 hours). After 24 hours, token expires and client must re-authenticate.

---

## Component 2: `JwtService.java` (Generating & Validating JWT Tokens)

### 📄 Key Code Snippets:

#### A. Generating a Signed JWT Token:
```java
public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", userDetails.getAuthorities().toString());

    return Jwts.builder()
            .claims(claims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(getSigningKey())
            .compact();
}
```

### 🔍 What the Syntax Does:
* **`Jwts.builder()`**: Initiates JJWT fluent builder pattern.
* **`.claims(claims)`**: Inserts custom data payload (e.g. user roles).
* **`.subject(...)`**: Sets `sub` claim to username (`customer_alice`).
* **`.issuedAt(...)` & `.expiration(...)`**: Attaches timestamps for token validity window.
* **`.signWith(getSigningKey())`**: Computes HMAC-SHA256 signature using secret key.
* **`.compact()`**: Serializes header, payload, and signature into Base64Url string `xxx.yyy.zzz`.

#### B. Extracting Claims & Validating Token Signature:
```java
private Claims extractAllClaims(String token) {
    return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
}
```

### 🔍 What the Syntax Does:
* **`Jwts.parser().verifyWith(getSigningKey())`**: Re-computes signature using server's secret key and compares it against token's signature. If token was tampered with, throws `SignatureException`.
* **`.parseSignedClaims(token)`**: Decodes Base64Url payload and returns `Claims` object.

---

## Component 3: `JwtAuthenticationFilter.java` (Intercepting Every Request)

### 📄 Key Code Snippet:
```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwtToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
```

### 🔍 What the Syntax Does:
1. **`extends OncePerRequestFilter`**: Guarantees filter executes exactly once per incoming HTTP request.
2. **`request.getHeader("Authorization")`**: Fetches the HTTP header string.
3. **`authHeader.startsWith("Bearer ")`**: Verifies header starts with standard OAuth2 / JWT prefix `"Bearer "`.
4. **`authHeader.substring(7)`**: Removes `"Bearer "` (7 chars) to isolate raw JWT string `xxx.yyy.zzz`.
5. **`SecurityContextHolder.getContext().getAuthentication() == null`**: Checks if request is unauthenticated.
6. **`SecurityContextHolder.getContext().setAuthentication(authToken)`**: Marks user as authenticated for current thread request execution.

---

## Component 4: `JwtSecurityConfig.java` (Stateless Security Filter Chain & Swagger)

### 📄 Key Code Snippet:
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
}
```

### 🔍 What the Syntax Does:
* **`SessionCreationPolicy.STATELESS`**: Tells Spring Security **NEVER** to create an HTTP Session cookie (`JSESSIONID`).
* **`addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)`**: Plugs `JwtAuthenticationFilter` ahead of standard username/password filter so JWT is verified first.

---

## Component 5: `AuthController.java` (Issuing JWT Tokens)

### 📄 Key Code Snippet:
```java
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
    );

    UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
    String jwtToken = jwtService.generateToken(userDetails);

    return ResponseEntity.ok(new JwtResponse(jwtToken, userDetails.getUsername(), role));
}
```

### 🔍 What the Syntax Does:
1. **`authenticationManager.authenticate(...)`**: Verifies raw username and password against database BCrypt hash. If invalid, throws `BadCredentialsException` (`401 Unauthorized`).
2. **`jwtService.generateToken(userDetails)`**: Generates signed JWT string upon successful authentication.
3. **`ResponseEntity.ok(new JwtResponse(...))`**: Returns JSON response containing JWT string to client.
