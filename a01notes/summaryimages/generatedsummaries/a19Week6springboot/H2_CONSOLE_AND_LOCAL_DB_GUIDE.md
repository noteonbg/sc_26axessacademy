# H2 File-Based Local Database & Console Guide

This guide details how H2 local file-based database persistence is configured across the Spring Boot projects located in `F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a19Week6springboot`, how to access the H2 Web Console, and how to connect via command-line console tools.

---

## 1. Overview: Local File-Based Persistence vs In-Memory Mode

By default, H2 can operate in two primary modes:

- **In-Memory Mode (`jdbc:h2:mem:...`)**: Data resides strictly in RAM. All tables and inserted rows are destroyed when the Spring Boot application stops or restarts.
- **File-Based Local Database Mode (`jdbc:h2:file:./data/<dbname>`)**: Data is persisted to files stored inside the project folder (`./data/<dbname>.mv.db`). Tables, schema, and rows survive application restarts.

### Why `AUTO_SERVER=TRUE` is Enabled
When using local file-based H2 databases, opening the database file from multiple applications (e.g., Spring Boot + H2 CLI Shell or external IDE database browser) can trigger a file lock error (`Database already in use`).

Adding `;AUTO_SERVER=TRUE` to the JDBC connection string automatically starts an embedded H2 TCP server when the first process connects. This permits concurrent connections from external SQL clients and consoles without locking issues.

---

## 2. Spring Boot Configuration Matrix

Each Spring Boot module in this repository has been configured with local file-based persistence:

| Project Directory | Port | Database Name | Local JDBC URL | Web Console URL |
| :--- | :--- | :--- | :--- | :--- |
| `03-banking-jpa-demo` | `8083` | `bankingjpadb` | `jdbc:h2:file:./data/bankingjpadb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE` | `http://localhost:8083/h2-console` |
| `04-banking-security-demo` | `8084` | `bankingsecdb` | `jdbc:h2:file:./data/bankingsecdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE` | `http://localhost:8084/h2-console` |
| `banking-core-app` | `8080` | `bankingdb` | `jdbc:h2:file:./data/bankingdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE` | `http://localhost:8080/h2-console` |
| `reactandspring/customer-backend` | `8080` | `bankdb` | `jdbc:h2:file:./data/bankdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE` | `http://localhost:8080/h2-console` |

---

## 3. Standard `application.properties` Syntax

To enable H2 file persistence and Web Console access via Spring Boot, ensure the following properties are in `src/main/resources/application.properties`:

```properties
# Enable H2 Web Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# File-Based Local Database Connection
spring.datasource.url=jdbc:h2:file:./data/bankingdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA & Dialect Settings
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Allow H2 Console Frames (Required if Spring Security is active)
spring.headers.frame-options=sameorigin
```

---

## 4. How to View & Connect via H2 Web Console

### Step-by-Step Connection Instructions:

1. **Start the Target Spring Boot Application**:
   Navigate to the project folder and run:
   ```bash
   mvn spring-boot:run
   ```
2. **Open your Web Browser**:
   Go to the corresponding console URL (e.g. `http://localhost:8080/h2-console` for `banking-core-app` or `http://localhost:8083/h2-console` for `03-banking-jpa-demo`).

3. **Fill in the Login Form**:
   - **Saved Settings**: `Generic H2 (Embedded)`
   - **Setting Name**: `Generic H2 (Embedded)`
   - **Driver Class**: `org.h2.Driver`
   - **JDBC URL**: Ensure it matches the exact local file path configured in `application.properties` (e.g., `jdbc:h2:file:./data/bankingdb` or absolute path `jdbc:h2:file:F:/scproject/.../banking-core-app/data/bankingdb`).
   - **User Name**: `sa`
   - **Password**: *(leave blank / empty)*

4. **Click "Test Connection" and "Connect"**:
   - You will enter the H2 SQL Query editor interface.
   - Expand the left panel tree to view auto-generated tables (e.g., `ACCOUNTS`, `CUSTOMERS`, `TRANSACTIONS`).

---

## 5. How to Connect via Command-Line / Terminal Console

You can also connect to the local file-based database directly using the H2 CLI Shell.

### Option A: Using Maven / H2 Dependency JAR

Locate the `h2-*.jar` in your local Maven repository (`~/.m2/repository/com/h2database/h2/<version>/h2-<version>.jar`) or use Maven runtime:

```bash
# Run H2 Shell tool using java CLI
java -cp C:\Users\<Username>\.m2\repository\com\h2database\h2\2.2.224\h2-2.2.224.jar org.h2.tools.Shell
```

When prompted by the shell:
```text
URL: jdbc:h2:file:./data/bankingdb;AUTO_SERVER=TRUE
Driver: org.h2.Driver
User: sa
Password:
```

### Option B: Executing SQL directly via Shell CLI
```bash
java -cp h2.jar org.h2.tools.Shell -url "jdbc:h2:file:./data/bankingdb;AUTO_SERVER=TRUE" -user "sa" -sql "SELECT * FROM ACCOUNTS;"
```

---

## 6. Spring Security Configuration Note

If Spring Security is included in your project (such as in `04-banking-security-demo` or `banking-core-app`), Security will block `/h2-console` by default. To permit access, the Java `SecurityFilterChain` bean must explicitly allow the endpoint and frame options:

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.disable()) // Disable CSRF for H2 Console & REST APIs
        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // Allow H2 iframe rendering
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/h2-console/**").permitAll() // Permit anonymous access to console
            .anyRequest().authenticated()
        )
        .build();
}
```

---

## 7. Verifying Persistence Locally

Once you insert records (via REST API endpoints or SQL `INSERT` statements):
1. Stop the Spring Boot application.
2. Check the project directory — a `./data` folder will exist containing `<dbname>.mv.db`.
3. Restart the application.
4. Run `SELECT * FROM <tablename>;` in H2 Console or CLI — all previously inserted data remains intact.
