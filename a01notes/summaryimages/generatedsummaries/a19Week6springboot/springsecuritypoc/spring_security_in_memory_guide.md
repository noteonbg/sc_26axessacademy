# Basic Spring Security In-Memory Demo Guide
## Role-Based Access Control (`f1`, `f2`, `f3`) with Swagger UI & Bruno Testing

This document provides a complete step-by-step implementation of a **Spring Boot 3** project demonstrating **Basic Spring Security with In-Memory Users** (no database required).

---

## Key Requirements & Endpoint Security Matrix

| Function | Endpoint | Security Access Rule | Required Credentials | Output (HTTP 200) |
| :--- | :--- | :--- | :--- | :--- |
| **`f1`** | `/api/f1` | Restricted to **User A (ADMIN)** | `admin` / `admin123` | `"f1 at work"` |
| **`f2`** | `/api/f2` | Restricted to **User B (USER)** | `user` / `user123` | `"f2 at work"` |
| **`f3`** | `/api/f3` | **Public** (Unauthenticated) | None (Public) | `"f3 at work"` |

---

## 1. Project Folder Structure

```
spring-security-inmemory-demo/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── example/
    │   │           └── security/
    │   │               ├── SpringSecurityDemoApplication.java
    │   │               ├── config/
    │   │               │   ├── SecurityConfig.java
    │   │               │   └── OpenApiConfig.java
    │   │               └── controller/
    │   │                   └── DemoController.java
    │   └── resources/
    │       └── application.properties
    └── test/
        └── java/
            └── com/
                └── example/
                    └── security/
                        └── DemoControllerTest.java
```

---

## 2. Dependencies (`pom.xml`)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>spring-security-inmemory-demo</artifactId>
    <version>1.0.0</version>
    <name>spring-security-inmemory-demo</name>
    <description>Basic Spring Security In-Memory Demo with Role-Based Access Control and Swagger UI</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.3</version>
        <relativePath/>
    </parent>

    <properties>
        <java.version>17</java.version>
        <springdoc.version>2.3.0</springdoc.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starter Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Boot Starter Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!-- Springdoc OpenAPI Swagger UI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc.version}</version>
        </dependency>

        <!-- Spring Boot Starter Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- Spring Security Test -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## 3. Configuration (`application.properties`)

```properties
# Server Port
server.port=8080

# Application Name
spring.application.name=spring-security-inmemory-demo

# Swagger OpenAPI Path Settings
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
```

---

## 4. Main Application Class (`SpringSecurityDemoApplication.java`)

```java
package com.example.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }
}
```

---

## 5. Security Configuration (`SecurityConfig.java`)

Configures in-memory users (`InMemoryUserDetailsManager`) and defines role-based endpoint access rules (`SecurityFilterChain`).

```java
package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * In-Memory User Details Manager defining hardcoded users without database requirement.
     * - User A (Admin): username 'admin', password 'admin123', Role 'ADMIN'
     * - User B (Normal User): username 'user', password 'user123', Role 'USER'
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails normalUser = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(adminUser, normalUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security Filter Chain defining authorization rules and HTTP Basic Auth.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Allow public access to endpoint f3
                .requestMatchers("/api/f3").permitAll()
                
                // Allow public access to Swagger UI and OpenAPI docs
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
                ).permitAll()
                
                // Restrict /api/f1 to ADMIN role only (User A)
                .requestMatchers("/api/f1").hasRole("ADMIN")
                
                // Restrict /api/f2 to USER role only (Normal User B)
                .requestMatchers("/api/f2").hasRole("USER")
                
                // All other endpoints require authentication
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
```

---

## 6. Swagger OpenAPI Configuration (`OpenApiConfig.java`)

Enables HTTP Basic Authentication in Swagger UI so you can test protected endpoints directly inside your browser.

```java
package com.example.security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Spring Security In-Memory Demo API",
        version = "1.0.0",
        description = "Demo API illustrating Basic Authentication with In-Memory User Details and Role-Based Access Control"
    )
)
@SecurityScheme(
    name = "basicAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
public class OpenApiConfig {
}
```

---

## 7. REST Controller (`DemoController.java`)

```java
package com.example.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Demo Controller", description = "Endpoints demonstrating Basic Spring Security role-based access")
public class DemoController {

    /**
     * Endpoint f1: Accessible ONLY by ADMIN role (User A).
     */
    @GetMapping("/f1")
    @Operation(
        summary = "Function f1 (ADMIN only)",
        description = "Requires HTTP Basic Auth with ADMIN role (User A). Returns 200 OK and 'f1 at work'.",
        security = @SecurityRequirement(name = "basicAuth")
    )
    public ResponseEntity<String> f1() {
        return ResponseEntity.ok("f1 at work");
    }

    /**
     * Endpoint f2: Accessible ONLY by USER role (Normal User).
     */
    @GetMapping("/f2")
    @Operation(
        summary = "Function f2 (USER only)",
        description = "Requires HTTP Basic Auth with USER role (Normal User). Returns 200 OK and 'f2 at work'.",
        security = @SecurityRequirement(name = "basicAuth")
    )
    public ResponseEntity<String> f2() {
        return ResponseEntity.ok("f2 at work");
    }

    /**
     * Endpoint f3: Accessible by ANYONE (Public / Unauthenticated).
     */
    @GetMapping("/f3")
    @Operation(
        summary = "Function f3 (Public)",
        description = "Publicly accessible by anyone without authentication. Returns 200 OK and 'f3 at work'."
    )
    public ResponseEntity<String> f3() {
        return ResponseEntity.ok("f3 at work");
    }
}
```

---

## 8. Integration Tests (`DemoControllerTest.java`)

```java
package com.example.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("f3 should be accessible by anyone (unauthenticated)")
    public void f3_shouldBeAccessibleByAnyone() throws Exception {
        mockMvc.perform(get("/api/f3"))
                .andExpect(status().isOk())
                .andExpect(content().string("f3 at work"));
    }

    @Test
    @DisplayName("f1 should return 401 Unauthorized when unauthenticated")
    public void f1_shouldReturn401_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/f1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("f2 should return 401 Unauthorized when unauthenticated")
    public void f2_shouldReturn401_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/f2"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("f1 should return 200 OK when called by ADMIN user A")
    public void f1_shouldReturn200_forAdminUser() throws Exception {
        mockMvc.perform(get("/api/f1").with(httpBasic("admin", "admin123")))
                .andExpect(status().isOk())
                .andExpect(content().string("f1 at work"));
    }

    @Test
    @DisplayName("f1 should return 403 Forbidden when called by normal USER")
    public void f1_shouldReturn403_forNormalUser() throws Exception {
        mockMvc.perform(get("/api/f1").with(httpBasic("user", "user123")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("f2 should return 200 OK when called by normal USER")
    public void f2_shouldReturn200_forNormalUser() throws Exception {
        mockMvc.perform(get("/api/f2").with(httpBasic("user", "user123")))
                .andExpect(status().isOk())
                .andExpect(content().string("f2 at work"));
    }

    @Test
    @DisplayName("f2 should return 403 Forbidden when called by ADMIN user")
    public void f2_shouldReturn403_forAdminUser() throws Exception {
        mockMvc.perform(get("/api/f2").with(httpBasic("admin", "admin123")))
                .andExpect(status().isForbidden());
    }
}
```

---

## 9. How to Test with Swagger UI

1. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
2. Open your browser and navigate to:
   `http://localhost:8080/swagger-ui.html`
3. Notice the green **Authorize** button at the top right of the Swagger UI page.
4. **Testing `/api/f3`**:
   - Expand `GET /api/f3` -> Click **Try it out** -> **Execute**.
   - Output: `200 OK` with response body `"f3 at work"` (no login required).
5. **Testing `/api/f1` with Admin Credentials**:
   - Click **Authorize**.
   - Enter `Username: admin` and `Password: admin123`. Click **Authorize** -> **Close**.
   - Expand `GET /api/f1` -> Click **Try it out** -> **Execute**.
   - Output: `200 OK` with response body `"f1 at work"`.
6. **Testing `/api/f2` with Normal User Credentials**:
   - Click **Authorize** -> **Logout** -> Enter `Username: user` and `Password: user123`.
   - Expand `GET /api/f2` -> Click **Try it out** -> **Execute**.
   - Output: `200 OK` with response body `"f2 at work"`.

---

## 10. How to Test with Bruno API Client (Step-by-Step)

### Step 10.1: Test Public Endpoint `/api/f3`
1. Open **Bruno** -> Create a new `HTTP` request.
2. Method: `GET` | URL: `http://localhost:8080/api/f3`
3. Auth tab: `Inherit` or `No Auth`.
4. Click **Send** -> Response: `200 OK` with `"f3 at work"`.

### Step 10.2: Test `/api/f1` as Admin User A
1. Method: `GET` | URL: `http://localhost:8080/api/f1`
2. Go to **Auth** tab -> Select **Basic Auth**.
3. **Username**: `admin`
4. **Password**: `admin123`
5. Click **Send** -> Response: `200 OK` with `"f1 at work"`.

### Step 10.3: Test `/api/f1` as Normal User (Verify Access Denied)
1. In the same request or new request for `/api/f1`:
2. Go to **Auth** tab -> Select **Basic Auth**.
3. **Username**: `user`
4. **Password**: `user123`
5. Click **Send** -> Response: `403 Forbidden`.

### Step 10.4: Test `/api/f2` as Normal User
1. Method: `GET` | URL: `http://localhost:8080/api/f2`
2. Go to **Auth** tab -> Select **Basic Auth**.
3. **Username**: `user`
4. **Password**: `user123`
5. Click **Send** -> Response: `200 OK` with `"f2 at work"`.

---

## 11. Verification Checklist

| Requirement | Implementation Details | Status |
| :--- | :--- | :---: |
| **No Database** | In-memory `InMemoryUserDetailsManager` used | ✅ |
| **User A (Admin)** | `username: admin`, `password: admin123`, `ROLE_ADMIN` | ✅ |
| **User B (Normal User)** | `username: user`, `password: user123`, `ROLE_USER` | ✅ |
| **Endpoint `f1`** | `/api/f1` returns 200 OK `"f1 at work"` (ADMIN only) | ✅ |
| **Endpoint `f2`** | `/api/f2` returns 200 OK `"f2 at work"` (USER only) | ✅ |
| **Endpoint `f3`** | `/api/f3` returns 200 OK `"f3 at work"` (Public / Anyone) | ✅ |
| **Swagger UI Config** | `OpenApiConfig.java` + HTTP Basic Auth enabled in Swagger UI | ✅ |
| **Automated Tests** | 7 MockMvc test cases passing cleanly | ✅ |
