# JDBC Bank Account Management System — Solution

Source assignment: `JDBCProject.pdf`

This solution covers:
1. OOP Account / DebitAccount / CreditAccount
2. Polymorphism demo
3. PostgreSQL table + JDBC CRUD
4. Console menu for add / display / payment / deposit / delete

---

## Folder structure

```text
03-jdbc-bank/
  schema.sql
  run.bat
  README.md
  lib/                 <-- put postgresql jar here
  src/bank/
    Account.java
    DebitAccount.java
    CreditAccount.java
    DBConnection.java
    AccountDAO.java
    OOPDemo.java
    App.java
```

---

## Step 0 — Connect PostgreSQL first

Read this file carefully first:

`..\HOW-TO-CONNECT-POSTGRES.md`

You need:
1. PostgreSQL running
2. Database named `bankdb`
3. Username `postgres`
4. Your real password

---

## Step 1 — Create table in PostgreSQL

1. Open pgAdmin.
2. Connect to `bankdb`.
3. Open Query Tool.
4. Run `schema.sql` from this folder.

Expected table: `bank_account`

---

## Step 2 — Download PostgreSQL JDBC driver

1. Go to: https://jdbc.postgresql.org/download/
2. Download a jar like `postgresql-42.7.4.jar`
3. Copy it into:
   `03-jdbc-bank\lib\postgresql-42.7.4.jar`

If `lib` is empty, Java cannot connect.

---

## Step 3 — Set your password in code

Open `src\bank\DBConnection.java` and change:

```java
private static final String PASSWORD = "postgres";
```

to your real PostgreSQL password.

Connection used:

```text
jdbc:postgresql://localhost:5432/bankdb
user = postgres
```

---

## Step 4 — Compile and run

### Option A: use run.bat

1. Open Command Prompt.
2. Go to this folder:

```bat
cd /d E:\scproject\dontpostingit\Assignments\Week3\03-jdbc-bank
```

3. Run:

```bat
run.bat
```

### Option B: manual commands

```bat
cd /d E:\scproject\dontpostingit\Assignments\Week3\03-jdbc-bank
mkdir out
javac -cp "lib/*" -d out src\bank\*.java
java -cp "out;lib/*" bank.OOPDemo
java -cp "out;lib/*" bank.App
```

---

## Menu options (assignment points 8–14)

```text
1. Add Account
2. Display All Accounts
3. Display Account by Id
4. Perform Payment
5. Perform Deposit
6. Delete Account
7. Exit
```

### Add Account example
1. Choose type `DEBIT` or `CREDIT`
2. Enter account number, owner, balance
3. For DEBIT enter password
4. For CREDIT enter limit

### Payment / Deposit
1. System loads account from DB
2. If DEBIT → asks password (overload)
3. If CREDIT → uses overridden limit/bonus logic
4. New balance is saved back to PostgreSQL

---

## How assignment points map to code

| Point | Topic | Where |
|------|------|------|
| 1 | Account class | `Account.java` |
| 2 | Object usage | `OOPDemo.java` |
| 3 | Inheritance | `DebitAccount`, `CreditAccount` |
| 4 | Override | `CreditAccount.performPayment/Deposit` |
| 5 | Overload | `DebitAccount.performPayment/Deposit(amount, password)` |
| 6 | Polymorphism | parent ref in `OOPDemo` and menu payment logic |
| 7 | JDBC CRUD | `AccountDAO.java` |
| 8–14 | Menu actions | `App.java` |

---

## Common fresher mistakes

1. Forgot to change password in `DBConnection.java`
2. Forgot JDBC jar in `lib`
3. Did not run `schema.sql`
4. PostgreSQL service not started
5. Compiling from wrong folder

---

## Quick test after app starts

1. Add DEBIT account `D001`, owner `Ravi`, balance `10000`, password `1234`
2. Display all accounts
3. Perform payment `500` with password `1234`
4. Display account by id `D001`
5. Delete if needed
