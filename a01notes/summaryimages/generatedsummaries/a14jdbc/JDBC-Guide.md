# JDBC — Fresh Hire Guide

Source deck: `JDBC.pdf` (Java with Postgres)  
Audience: new college hires joining a bank / financial IT team

HTML can collect customer data. Database can store it.  
**JDBC is the bridge** that lets Java applications read and write banking data safely.

---

## How to use this file

1. Read one topic.
2. Relate it to Bank Customer / Account Management.
3. Practice syntax in a small Java class.
4. Follow industry best practices — this is where SQL injection and connection leaks happen if you are careless.

---

# Topic 1. Why JDBC exists

## Simple explanation
Java cannot talk to PostgreSQL by magic.  
**JDBC (Java Database Connectivity)** is the standard API for that conversation.

## Finance example
A branch officer uses a Java menu:
1. Add customer
2. List customers
3. Find customer
4. Delete customer draft record

Each option runs SQL in PostgreSQL through JDBC.

## Industry best practice
1. Application code should not open random DB tools manually for daily business actions.
2. Same service logic should work in UAT and PROD with different connection config.
3. Keep SQL and credentials out of chat messages and screenshots.

---

# Topic 2. Bank use case first (from your deck)

## Simple explanation
Course use case: **Bank Customer Management System**

Customer fields:
1. customer id
2. name
3. mail id
4. contact
5. account type (Savings/Current)

Menu:
1. Add customer
2. List all
3. Find match
4. Delete
5. Exit

Validations before save:
1. name = alphabets
2. email valid
3. contact = 10 digits
4. account type = Savings or Current

## Finance example
This is similar to a simplified customer-master maintenance screen used by ops teams, before full CRM systems.

## Industry best practice
1. Validate in Java **and** enforce constraints in DB.
2. Auto-generate ids; do not let users invent customer ids casually.
3. Prefer soft status changes over hard delete for real customers.

---

# Topic 3. JDBC architecture pieces

## Simple explanation
Main pieces you will touch:
1. **Driver** — translator for PostgreSQL
2. **Connection** — live session to DB
3. **Statement / PreparedStatement** — SQL carrier
4. **ResultSet** — rows returned by SELECT

## Finance example
Add Customer flow:
Java values → PreparedStatement INSERT → PostgreSQL `customer` table  
List Customers flow:
PostgreSQL SELECT → ResultSet → print in console/UI

## Industry best practice
1. Use Type 4 JDBC driver (pure Java), standard today.
2. One logical business action should use clear connection handling.
3. Close resources always (Connection/Statement/ResultSet).

---

# Topic 4. JDBC steps (memorize this)

## Simple explanation
1. Load/register driver
2. Create connection
3. Create statement
4. Execute SQL
5. Close resources

## Finance example
Every “Save Customer” button eventually does these five steps.

## Syntax

```java
Class.forName("org.postgresql.Driver");

Connection con = DriverManager.getConnection(
    "jdbc:postgresql://localhost:5432/bankdb",
    "postgres",
    "YOUR_PASSWORD"
);

PreparedStatement ps = con.prepareStatement(
    "INSERT INTO customer(name, mailid, contact, account_type) VALUES (?, ?, ?, ?)"
);
ps.setString(1, "Ravi Kumar");
ps.setString(2, "ravi@bank.com");
ps.setString(3, "9876543210");
ps.setString(4, "Savings");
ps.executeUpdate();

ps.close();
con.close();
```

## Industry best practice
1. Prefer try-with-resources so close happens automatically.
2. Never leave connections open in loops.
3. Connection pool is used in real servers (HikariCP etc.); learn plain JDBC first.

---

# Topic 5. Connection URL (very practical)

## Simple explanation
URL tells Java where Postgres is.

```text
jdbc:postgresql://HOST:PORT/DATABASE
```

Local fresher setup:

```text
jdbc:postgresql://localhost:5432/bankdb
```

## Finance example
1. DEV laptop → `localhost/bankdb`
2. UAT server → `uat-pg.bank.local/customerdb`
3. PROD → locked-down host, credentials from secret store

Same code, different config.

## Industry best practice
1. Do not hardcode prod passwords in source code.
2. Use environment variables or secure config for real projects.
3. Separate DB users: app user should not be superuser.

---

# Topic 6. Statement vs PreparedStatement

## Simple explanation
1. `Statement` — plain SQL string
2. `PreparedStatement` — SQL with `?` placeholders

## Finance example
Bad (unsafe) pattern:

```java
String sql = "SELECT * FROM customer WHERE name = '" + userInput + "'";
```

If user types clever text, this can become SQL injection.

Good:

```java
PreparedStatement ps = con.prepareStatement(
  "SELECT * FROM customer WHERE name = ?"
);
ps.setString(1, userInput);
ResultSet rs = ps.executeQuery();
```

## Industry best practice
1. **Always prefer PreparedStatement for user input.**
2. This is one of the first secure-coding expectations in bank projects.
3. Statement is okay for static SQL with no user input, but PreparedStatement is a safer habit.

---

# Topic 7. executeQuery vs executeUpdate

## Simple explanation
1. `executeQuery` — SELECT → returns ResultSet
2. `executeUpdate` — INSERT/UPDATE/DELETE → returns row count

## Finance example

```java
// List customers
ResultSet rs = ps.executeQuery();

// Insert / update balance / delete draft
int rows = ps.executeUpdate();
```

## Industry best practice
1. Check returned row count for updates/deletes.
2. If UPDATE returns 0, account id may be wrong — show clear message.
3. Log business reference (account/customer id), not full SQL with secrets.

---

# Topic 8. ResultSet

## Simple explanation
ResultSet is a cursor over result rows.

## Finance example
Print all customers for branch ops screen.

## Syntax

```java
ResultSet rs = ps.executeQuery();
while (rs.next()) {
    int id = rs.getInt("customerid");
    String name = rs.getString("name");
    String mail = rs.getString("mailid");
    System.out.println(id + " | " + name + " | " + mail);
}
```

## Industry best practice
1. Read by column name when learning (`getString("name")`) for clarity.
2. Do not keep ResultSet open while doing slow network calls.
3. Mask sensitive fields in console logs.

---

# Topic 9. Transactions (COMMIT / ROLLBACK mindset)

## Simple explanation
A transaction groups SQL steps that must all succeed together.

## Finance example
Fund transfer style logic:
1. Debit account A
2. Credit account B

If step 2 fails, roll back step 1. Never leave money disappearing.

## Syntax

```java
Connection con = DBConnection.getConnection();
try {
    con.setAutoCommit(false);

    // 1) debit
    // 2) credit

    con.commit();
} catch (Exception e) {
    con.rollback();
} finally {
    con.setAutoCommit(true);
    con.close();
}
```

## Industry best practice
1. Any multi-step money movement needs a transaction.
2. Keep transactions short.
3. Know your isolation requirements before touching ledger-like tables.

---

# Topic 10. CRUD mapping for bank menu

## Simple explanation
CRUD = Create, Read, Update, Delete

| Menu action | SQL | JDBC method |
|-------------|-----|-------------|
| Add customer | INSERT | executeUpdate |
| List all | SELECT | executeQuery |
| Find match | SELECT ... WHERE | executeQuery |
| Delete | DELETE | executeUpdate |
| Update contact | UPDATE | executeUpdate |

## Finance example syntax

```sql
INSERT INTO customer(name, mailid, contact, account_type)
VALUES (?, ?, ?, ?);

SELECT * FROM customer;

SELECT * FROM customer WHERE customerid = ?;

DELETE FROM customer WHERE customerid = ?;
```

## Industry best practice
1. Create DAO/repository classes (`CustomerDAO`) instead of putting SQL in `main`.
2. Keep menu/UI separate from database code.
3. Write small test data scripts for UAT demos.

---

# Topic 11. Driver types (enough for freshers)

## Simple explanation
Older books mention Type 1 to Type 4.  
Today you almost always use **Type 4** PostgreSQL driver JAR.

## Finance example
Your project `lib/postgresql-42.x.x.jar` is the Type 4 driver that talks to bankdb.

## Industry best practice
1. Use approved driver versions from company repository when available.
2. Do not download random unsigned jars on prod build agents.
3. Keep driver version documented in README.

---

# Topic 12. Connection checklist for freshers

## Simple explanation
If JDBC fails, check these in order:

1. PostgreSQL service running?
2. Database `bankdb` exists?
3. Username/password correct?
4. URL host/port correct?
5. Driver JAR on classpath?
6. Table created (`schema.sql` run)?

## Finance example
UAT release failed because app still pointed to `localhost` instead of UAT host. Same code, wrong config.

## Industry best practice
1. Externalize config early.
2. Fail fast with clear error: “DB connection failed” + reason.
3. Never print password in exception messages.

---

# End-to-end mini banking story

1. HTML form captures customer details (Week 3 HTML topic).
2. Java validates name/email/mobile/account type.
3. JDBC PreparedStatement inserts into PostgreSQL.
4. Ops user lists/finds customer through menu.
5. Constraints + transactions protect data quality.
6. Later, same DAO can serve an API for net banking.

That is classroom JDBC becoming enterprise banking work.

---

# Fresh hire checklist before you say “JDBC done”

1. Am I using PreparedStatement for input values?
2. Am I closing Connection/Statement/ResultSet?
3. Is password not hardcoded for shared/prod use?
4. Did I validate before insert?
5. Did I test add/list/find/delete on `bankdb`?
6. Can I explain my SQL to a senior in business words?

---

# Tiny cheat sheet

```java
// Connect
Connection con = DriverManager.getConnection(url, user, pass);

// Write
PreparedStatement ps = con.prepareStatement("INSERT ... VALUES (?, ?)");
ps.setString(1, value1);
ps.executeUpdate();

// Read
PreparedStatement ps2 = con.prepareStatement("SELECT ... WHERE id = ?");
ps2.setInt(1, id);
ResultSet rs = ps2.executeQuery();
while (rs.next()) { ... }
```
