# How to Connect to PostgreSQL (for freshers)

Use this before running the Database SQL assignment or the JDBC project.

---

## 1. What you need

1. PostgreSQL installed on your laptop
2. pgAdmin (usually installed with PostgreSQL)
3. A database name, username and password

Default local values used in these solutions:

| Item | Value |
|------|--------|
| Host | `localhost` |
| Port | `5432` |
| Database | `bankdb` |
| Username | `postgres` |
| Password | the password you set during PostgreSQL install |

---

## 2. Install PostgreSQL (if not already installed)

1. Download PostgreSQL from: https://www.postgresql.org/download/windows/
2. Run the installer.
3. Remember the password you set for user `postgres`.
4. Keep port as `5432`.
5. Finish install and open **pgAdmin 4**.

---

## 3. Create database `bankdb` using pgAdmin

1. Open **pgAdmin 4**.
2. Enter your master password if asked.
3. In the left tree: **Servers → PostgreSQL → Databases**.
4. Right click **Databases → Create → Database**.
5. Database name: `bankdb`
6. Owner: `postgres`
7. Click **Save**.

---

## 4. Open Query Tool and run SQL files

1. Expand **Databases → bankdb**.
2. Click **Tools → Query Tool**.
3. Open file `01_create_tables.sql` from folder `01-database`.
4. Click Execute (play button) or press F5.
5. Then open and run `02_insert_sample_data.sql`.
6. Then open and run queries from `03_queries_solutions.sql` one by one.

---

## 5. Test connection with a simple query

In Query Tool for `bankdb`, run:

```sql
SELECT current_database(), current_user;
```

If this prints `bankdb` and `postgres`, you are connected.

---

## 6. Connect from Java JDBC (used in 03-jdbc-bank)

Java connection string:

```text
jdbc:postgresql://localhost:5432/bankdb
```

Example in code:

```java
String url = "jdbc:postgresql://localhost:5432/bankdb";
String user = "postgres";
String password = "YOUR_PASSWORD_HERE";

Connection con = DriverManager.getConnection(url, user, password);
```

You also need the PostgreSQL JDBC driver JAR in the `lib` folder.  
Download from: https://jdbc.postgresql.org/download/

Suggested file name:
`postgresql-42.7.4.jar` (any recent 42.x is fine)

Put it here:
`Assignments\Week3\03-jdbc-bank\lib\postgresql-42.7.4.jar`

---

## 7. Common connection errors (simple fixes)

1. **Connection refused / port issue**
   - PostgreSQL service is not running.
   - On Windows: Services → start `postgresql-x64-...`

2. **password authentication failed**
   - Wrong password for user `postgres`.
   - Reset/reinstall or use the correct password in code and pgAdmin.

3. **database "bankdb" does not exist**
   - Create `bankdb` again in pgAdmin.

4. **ClassNotFoundException: org.postgresql.Driver**
   - JDBC JAR missing from classpath/`lib` folder.

5. **FATAL: role "postgres" does not exist**
   - Use the username created during install, or create role `postgres`.

---

## 8. Quick checklist before class demo

1. PostgreSQL service is running
2. Database `bankdb` exists
3. You can run `SELECT 1;` in pgAdmin Query Tool
4. For JDBC: driver JAR is in `lib` and password in `DBConnection.java` is updated
