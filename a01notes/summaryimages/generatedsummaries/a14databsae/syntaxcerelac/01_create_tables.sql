-- =========================================================
-- Week3 DB Assignment - Create tables
-- Run this first in pgAdmin Query Tool on database: bankdb
-- =========================================================

-- Clean old tables if you want to re-run from scratch
DROP TABLE IF EXISTS loan CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS branch CASCADE;
DROP TABLE IF EXISTS customers CASCADE;

-- 1) Customers
CREATE TABLE customers (
    customer_id   INT PRIMARY KEY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    email         VARCHAR(100),
    phone         VARCHAR(15),
    city          VARCHAR(50),
    dob           DATE
);

-- 2) Accounts
-- account_balance starts as STRING (VARCHAR) because Q1 asks to change it to number
CREATE TABLE accounts (
    account_id      INT PRIMARY KEY,
    account_type    VARCHAR(20),
    account_balance VARCHAR(30),
    dop             DATE,              -- date of opening
    customer_id     INT REFERENCES customers(customer_id)
);

-- 3) Branch
CREATE TABLE branch (
    branch_id    INT PRIMARY KEY,
    branch_name  VARCHAR(50),
    city         VARCHAR(50)
);

-- 4) Loan
-- loan_amount added because questions ask for loan amount / highest loans
CREATE TABLE loan (
    loan_id     INT PRIMARY KEY,
    loan_type   VARCHAR(30),
    loan_amount NUMERIC(12,2),
    customer_id INT REFERENCES customers(customer_id),
    account_id  INT REFERENCES accounts(account_id),
    branch_id   INT REFERENCES branch(branch_id)
);

-- Check tables
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
ORDER BY table_name;
