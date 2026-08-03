# Database Assignment Solution (DB.pdf)

## What this folder solves

Create bank tables (Customers, Accounts, Branch, Loan), insert sample data, and solve queries 1 to 15.

## Files

1. `01_create_tables.sql`
2. `02_insert_sample_data.sql`
3. `03_queries_solutions.sql`

## Before you start

Read first: `..\HOW-TO-CONNECT-POSTGRES.md`

## Steps

1. Open pgAdmin and connect to PostgreSQL.
2. Create database `bankdb` if it does not exist.
3. Open Query Tool on `bankdb`.
4. Run `01_create_tables.sql`.
5. Run `02_insert_sample_data.sql`.
6. Run each query in `03_queries_solutions.sql` and check the result.

## Important note for freshers

1. In the PDF, `account_balance` starts as string, then Q1 converts it to number. That is why create script uses `VARCHAR` first.
2. PDF loan table did not list `loan_amount`, but questions ask for loan amount, so solution adds `loan_amount`.
3. Q12 “more than 1 bank” is treated as loans from more than one branch.

## Quick verify

```sql
SELECT COUNT(*) FROM customers;
SELECT COUNT(*) FROM accounts;
SELECT COUNT(*) FROM branch;
SELECT COUNT(*) FROM loan;
```
