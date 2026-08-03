# Database Fundamentals — Fresh Hire Guide

Source deck: `Database_Fundamentals.pdf`  
Audience: new college hires joining a bank / financial IT team

Talk like this is your first week on a payments or retail-banking project. Keep concepts small. Always ask: **what customer money does this protect?**

---

## How to use this file

1. Read one topic at a time.
2. Look at the finance example.
3. Copy the syntax into pgAdmin and try it on database `bankdb`.
4. Note the industry best practice — interviewers and leads care about this.

---

# Topic 1. What is a Database and DBMS?

## Simple explanation
A **database** is an organized place to store data permanently.  
A **DBMS** (Database Management System) is the software that helps you store, find, update and protect that data.

Think of database = bank vault of records.  
DBMS = the secure system + staff process that controls who can open which locker.

## Finance example
Customer name, account number, balance and loan details cannot live only in Excel on someone’s laptop. They live in a database so every branch sees the same truth.

## Industry best practice
1. Never treat production customer data casually.
2. Prefer controlled databases over shared spreadsheets for anything linked to money.
3. In banks, access is logged. Assume someone can audit what you queried.

---

# Topic 2. RDBMS terms (Schema, Table, Row, Column)

## Simple explanation
1. **Table** = one Excel-like sheet (Customers, Accounts)
2. **Row** = one customer or one account
3. **Column** = one field (name, balance)
4. **Schema** = the structure/design of tables in a database

## Finance example

| customer_id | first_name | city | phone |
|-------------|------------|------|-------|
| 101 | Ravi | Mumbai | 98765... |
| 102 | Anita | Delhi | 98111... |

Row 101 is one customer. Column `city` is one attribute.

## Syntax

```sql
CREATE TABLE customers (
  customer_id INT PRIMARY KEY,
  first_name  VARCHAR(50),
  last_name   VARCHAR(50),
  city        VARCHAR(50),
  phone       VARCHAR(15)
);
```

## Industry best practice
1. Use clear names: `customer_id`, not `c1`.
2. Keep one meaning per column.
3. Document money columns with scale, e.g. `NUMERIC(12,2)` for rupees and paise.

---

# Topic 3. ER Diagram (Entity Relationship)

## Simple explanation
Before writing SQL, draw **what things exist** and **how they connect**.  
That drawing is an ER diagram.

1. Entity = Customer, Account, Loan
2. Relationship = Customer **owns** Account, Customer **takes** Loan

## Finance example
1. One customer can have many accounts.
2. One customer can have many loans.
3. One loan belongs to one customer and one branch.

## Syntax mindset (design first)

```text
CUSTOMER 1 ----< ACCOUNT
CUSTOMER 1 ----< LOAN
BRANCH   1 ----< LOAN
```

## Industry best practice
1. Never jump straight into CREATE TABLE on a banking feature.
2. Get the relationship right first. Wrong ER = wrong balances later.
3. Ask business: “Can one customer have multiple savings accounts?” before coding.

---

# Topic 4. Normalization (1NF, 2NF, 3NF)

## Simple explanation
Normalization means **store each fact once**, so updates do not break data.

1. **1NF:** no multi-values in one cell  
2. **2NF:** no partial dependency on part of a key  
3. **3NF:** no derived/transitive fields that can be calculated or depend on non-key columns

## Finance example
Bad design:

| customer_id | phones | branch_name | branch_city |
|-------------|--------|-------------|-------------|
| 101 | 98.., 99.. | Fort, MG Road | Mumbai, Mumbai |

Problems:
1. Two phones in one cell (breaks 1NF)
2. Branch city repeated everywhere

Better:
1. `customer`
2. `customer_phone`
3. `branch`
4. `account` with `branch_id`

## Syntax

```sql
-- Better phone design (1NF)
CREATE TABLE customer_phone (
  customer_id INT,
  phone VARCHAR(15),
  PRIMARY KEY (customer_id, phone)
);
```

## Industry best practice
1. Do not store `annual_interest` if you can calculate it from rate and principal unless reporting needs a snapshot.
2. Duplicate customer address in 10 tables causes failed KYC updates.
3. Banks prefer clean master data (Customer Hub) referenced by other systems.

---

# Topic 5. Indexing

## Simple explanation
An **index** is like the index of a passbook register. It helps the database find rows faster.

1. **Clustered index:** sorts the actual table data (often primary key)
2. **Non-clustered index:** separate lookup structure pointing to rows

## Finance example
Tellers search by `account_number` thousands of times a day. Index `account_number`.  
Do not index every column. Too many indexes slow down inserts during salary-credit night batches.

## Syntax

```sql
CREATE INDEX idx_account_number ON accounts(account_id);
CREATE INDEX idx_customer_city ON customers(city);
```

## Industry best practice
1. Index columns used in WHERE / JOIN frequently.
2. Measure before adding random indexes.
3. On large banking tables, bad indexes can make month-end jobs miss SLA.

---

# Topic 6. PostgreSQL basics

## Simple explanation
PostgreSQL is the open-source database used in this course.  
You connect with pgAdmin, create a database, and run SQL in Query Tool.

## Finance example
Many banks and fintechs use Postgres (or similar RDBMS) for customer master, account master and case-management style apps in non-mainframe layers.

## Syntax

```sql
CREATE DATABASE bankdb;

-- then connect to bankdb and run:
SELECT current_database(), current_user;
```

## Industry best practice
1. Dev/UAT/Prod must be separate databases.
2. Never practice DELETE/UPDATE on production.
3. Keep connection secrets out of chat/email.

---

# Topic 7. SQL command groups

## Simple explanation
1. **DDL** — define structure (CREATE, ALTER, DROP)
2. **DML** — change data (INSERT, UPDATE, DELETE)
3. **DQL** — read data (SELECT)
4. **DCL** — permissions (GRANT, REVOKE)
5. **TCL** — transactions (COMMIT, ROLLBACK)

## Finance example
1. DDL: create `loan` table
2. DML: insert a new home loan
3. DQL: show all overdue loans
4. DCL: only loan-ops role can update status
5. TCL: if EMI posting fails halfway, ROLLBACK

## Industry best practice
1. Treat DDL in production as a controlled release change.
2. Wrap money updates in transactions.
3. Prefer least privilege: app user should not be superuser.

---

# Topic 8. Constraints

## Simple explanation
Constraints are rules that protect data quality.

1. `NOT NULL` — must have value
2. `UNIQUE` — no duplicates
3. `PRIMARY KEY` — unique row identity
4. `FOREIGN KEY` — must refer to a valid parent row
5. `CHECK` — custom rule

## Finance example
Account balance should not be negative for a basic savings product.  
Every account must belong to a real customer.

## Syntax

```sql
CREATE TABLE accounts (
  account_id      INT PRIMARY KEY,
  customer_id     INT NOT NULL REFERENCES customers(customer_id),
  account_type    VARCHAR(20) NOT NULL,
  account_balance NUMERIC(12,2) CHECK (account_balance >= 0),
  branch_id       INT REFERENCES branch(branch_id)
);
```

## Industry best practice
1. Do not rely only on UI validation. Enforce critical rules in DB too.
2. Foreign keys prevent orphan accounts after customer deletion mistakes.
3. For banking amounts, use `NUMERIC`, not `FLOAT`.

---

# Topic 9. DDL (CREATE / ALTER / DROP / TRUNCATE)

## Simple explanation
DDL changes structure.

1. `CREATE` makes table
2. `ALTER` changes table
3. `DROP` deletes table
4. `TRUNCATE` removes all rows fast

## Finance example
Business says: “Add KYC status column to customer.” Use ALTER, do not recreate table in production casually.

## Syntax

```sql
CREATE TABLE branch (
  branch_id INT PRIMARY KEY,
  branch_name VARCHAR(50),
  city VARCHAR(50)
);

ALTER TABLE customers ADD COLUMN kyc_status VARCHAR(20);

ALTER TABLE accounts
ALTER COLUMN account_balance TYPE NUMERIC(12,2)
USING account_balance::NUMERIC(12,2);

TRUNCATE TABLE temp_interest_calc;
-- DROP TABLE temp_interest_calc;
```

## Industry best practice
1. Prefer additive ALTER with backfill plan.
2. `DROP/TRUNCATE` in prod needs approval.
3. Keep DDL scripts in Git, reviewed like application code.

---

# Topic 10. DML (INSERT / UPDATE / DELETE)

## Simple explanation
DML changes rows.

## Finance example
1. INSERT: open new savings account
2. UPDATE: correct customer mobile after KYC
3. DELETE: remove a draft/test account in UAT (rarely hard-delete customers in prod)

## Syntax

```sql
INSERT INTO customers (customer_id, first_name, last_name, city, phone, dob)
VALUES (201, 'Ravi', 'Kumar', 'Mumbai', '9876543210', '1995-03-12');

UPDATE customers
SET phone = '9999988888'
WHERE customer_id = 201;

DELETE FROM accounts
WHERE account_id = 9999;
```

## Industry best practice
1. Always use WHERE on UPDATE/DELETE. Forgetting WHERE can wipe a table.
2. In prod banking systems, prefer soft delete / status change over hard DELETE for customers.
3. Test UPDATE on one id first, then batch.

---

# Topic 11. DQL / SELECT basics

## Simple explanation
SELECT reads data. This is what support teams and reports use most.

## Finance example
Show all Mumbai customers with active savings accounts, sorted by balance.

## Syntax

```sql
SELECT customer_id, first_name, city
FROM customers
WHERE city = 'Mumbai'
ORDER BY first_name;

SELECT account_id, account_balance AS balance
FROM accounts
WHERE account_type = 'saving';

SELECT DISTINCT city FROM customers;
```

## Industry best practice
1. Do not `SELECT *` in application code.
2. Select only needed columns for performance and privacy.
3. For customer support screens, mask PAN/Aadhaar/phone when possible.

---

# Topic 12. Operators, LIKE, NULL

## Simple explanation
Filter rows with conditions.

## Finance example
Find customers whose name starts with `Ra`, or accounts with unknown email.

## Syntax

```sql
SELECT * FROM accounts
WHERE account_balance > 10000
  AND account_type = 'saving';

SELECT * FROM customers
WHERE first_name LIKE 'Ra%';

SELECT * FROM customers
WHERE email IS NULL;
```

## Industry best practice
1. `= NULL` is wrong. Use `IS NULL`.
2. Leading wildcard `LIKE '%abc'` can be slow on big tables.
3. Be careful with PII filters in shared logs.

---

# Topic 13. Aggregates

## Simple explanation
Aggregates summarize many rows into one value: `COUNT`, `SUM`, `AVG`, `MIN`, `MAX`.

## Finance example
Total deposits in a branch, average loan amount, highest account balance.

## Syntax

```sql
SELECT COUNT(*) AS cust_count FROM customers;
SELECT SUM(account_balance) AS total_balance FROM accounts;
SELECT AVG(loan_amount) AS avg_loan FROM loan;
SELECT MAX(account_balance) AS max_balance FROM accounts;
```

## Industry best practice
1. For money, know whether you need live balance or end-of-day snapshot.
2. Reconcile aggregate reports with source systems before publishing to finance.
3. Alias columns clearly for business users (`Cust_Count`, `Total_Balance`).

---

# Topic 14. GROUP BY and HAVING

## Simple explanation
1. `GROUP BY` makes groups
2. `HAVING` filters groups (after aggregation)

WHERE filters rows first. HAVING filters groups later.

## Finance example
Count customers by city. Show only cities with more than 100 customers.

## Syntax

```sql
SELECT city, COUNT(*) AS cust_count
FROM customers
GROUP BY city
HAVING COUNT(*) > 1
ORDER BY cust_count DESC;
```

## Industry best practice
1. Every non-aggregated selected column must be in GROUP BY.
2. Use this for branch MIS reports, not for updating balances.
3. Validate report totals with a second independent query.

---

# Topic 15. Subquery

## Simple explanation
A query inside another query.

## Finance example
Find customers who hold the maximum number of loans, or accounts above average balance.

## Syntax

```sql
SELECT *
FROM customers
WHERE customer_id IN (
  SELECT customer_id
  FROM loan
  GROUP BY customer_id
  HAVING COUNT(*) = (
    SELECT MAX(cnt) FROM (
      SELECT COUNT(*) AS cnt FROM loan GROUP BY customer_id
    ) t
  )
);
```

## Industry best practice
1. Start with readable subqueries; optimize later if slow.
2. For complex bank reports, CTEs (`WITH` clause) are often clearer for team review.
3. Never copy subquery results into Excel as “source of truth”.

---

# Topic 16. Joins

## Simple explanation
Join combines tables using related keys.

1. INNER JOIN — only matching rows
2. LEFT JOIN — all left + matching right
3. RIGHT JOIN — all right + matching left
4. FULL JOIN — all from both

## Finance example
Show customer name with account balance and branch city.

## Syntax

```sql
SELECT c.first_name, a.account_id, a.account_balance, b.branch_name
FROM customers c
INNER JOIN accounts a ON c.customer_id = a.customer_id
LEFT JOIN loan l ON c.customer_id = l.customer_id
LEFT JOIN branch b ON l.branch_id = b.branch_id;
```

## Industry best practice
1. Know your grain: one customer can return many account rows.
2. Wrong join can duplicate loan amounts and scare finance teams.
3. Always verify counts before and after join in UAT.

---

# End-to-end mini banking story

1. Design ER: Customer, Account, Loan, Branch
2. Create normalized tables with constraints
3. Index account_id and customer_id
4. Insert account opening data
5. Query branch balances with JOIN + GROUP BY
6. If a batch fails mid-way, ROLLBACK

That is how classroom SQL becomes real bank IT work.

---

# Fresh hire checklist before you say “done”

1. Did I use NUMERIC for money?
2. Did I add WHERE to UPDATE/DELETE?
3. Did I avoid SELECT * in app code?
4. Did I test on `bankdb` UAT-like data, not prod?
5. Can I explain my join in business words to a BA?
