-- =========================================================
-- Week3 DB Assignment - Sample data
-- Run after 01_create_tables.sql
-- =========================================================

-- Customers (India + one overseas for count query)
INSERT INTO customers VALUES
(1, 'Ravi',   'Kumar',  'ravi@bank.com',   '9876543210', 'Mumbai',   '1995-03-12'),
(2, 'Anita',  'Sharma', 'anita@bank.com',  '9876543211', 'Delhi',    '1992-07-20'),
(3, 'John',   'Smith',  'john@bank.com',   '9876543212', 'London',   '1990-01-05'),
(4, 'Sneha',  'Patil',  'sneha@bank.com',  '9876543213', 'Mumbai',   '1998-11-15'),
(5, 'Amit',   'Verma',  'amit@bank.com',   '9876543214', 'Pune',     '1994-05-25'),
(6, 'Priya',  'Nair',   'priya@bank.com',  '9876543215', 'Chennai',  '1996-09-10'),
(7, 'Karan',  'Mehta',  'karan@bank.com',  '9876543216', 'Delhi',    '1993-12-01');

-- Branches
INSERT INTO branch VALUES
(101, 'Fort Branch',      'Mumbai'),
(102, 'Connaught Branch', 'Delhi'),
(103, 'Deccan Branch',    'Pune'),
(104, 'T Nagar Branch',   'Chennai'),
(105, 'Empty Branch',     'Nagpur');   -- no accounts, for query showing 0

-- Accounts (balance kept as text first, for Q1 ALTER)
-- dop includes recent and older dates for "past 3 months" query
INSERT INTO accounts VALUES
(1001, 'saving',  '25000',  CURRENT_DATE - INTERVAL '20 days', 1),
(1002, 'current', '8000',   CURRENT_DATE - INTERVAL '40 days', 1),
(1003, 'saving',  '15000',  CURRENT_DATE - INTERVAL '2 months', 2),
(1004, 'saving',  '5000',   CURRENT_DATE - INTERVAL '1 year', 3),
(1005, 'current', '12000',  CURRENT_DATE - INTERVAL '10 days', 4),
(1006, 'saving',  '9000',   CURRENT_DATE - INTERVAL '6 months', 5),
(1007, 'saving',  '30000',  CURRENT_DATE - INTERVAL '15 days', 6),
(1008, 'current', '7000',   CURRENT_DATE - INTERVAL '25 days', 2),
(1009, 'saving',  '11000',  CURRENT_DATE - INTERVAL '5 days', 7);

-- Loans
-- Customer 1 has 2 loans from different branches (for Q6 and Q12)
INSERT INTO loan VALUES
(501, 'Home',    500000, 1, 1001, 101),
(502, 'Personal',100000, 1, 1002, 102),
(503, 'Car',     300000, 2, 1003, 102),
(504, 'Education',200000, 4, 1005, 101),
(505, 'Home',    450000, 6, 1007, 104);
-- Customers 3,5,7 have NO loan (for Q4)

SELECT 'customers' AS t, COUNT(*) FROM customers
UNION ALL SELECT 'accounts', COUNT(*) FROM accounts
UNION ALL SELECT 'branch', COUNT(*) FROM branch
UNION ALL SELECT 'loan', COUNT(*) FROM loan;
