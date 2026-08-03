-- =========================================================
-- Week3 DB Assignment - Query solutions (Q1 to Q15)
-- Run after create + insert scripts
-- =========================================================

-- Q1. Change account_balance type from string to number
ALTER TABLE accounts
ALTER COLUMN account_balance TYPE NUMERIC(12,2)
USING account_balance::NUMERIC(12,2);

-- Check
SELECT account_id, account_balance FROM accounts;


-- Q2. customer id, firstname, account_balance
-- sorted by year of DOB, then firstname
SELECT c.customer_id,
       c.first_name,
       a.account_balance,
       c.dob
FROM customers c
JOIN accounts a ON c.customer_id = a.customer_id
ORDER BY EXTRACT(YEAR FROM c.dob), c.first_name;


-- Q3. Number of customers from India (alias Cust_Count)
-- Sample data uses Indian cities + London; count Indian cities
SELECT COUNT(*) AS Cust_Count
FROM customers
WHERE city IN ('Mumbai', 'Delhi', 'Pune', 'Chennai');


-- Q4. Customers who do not have loan
SELECT c.*
FROM customers c
LEFT JOIN loan l ON c.customer_id = l.customer_id
WHERE l.loan_id IS NULL;

-- Alternative using NOT IN
SELECT *
FROM customers
WHERE customer_id NOT IN (SELECT customer_id FROM loan);


-- Q5. City name and count of branches in that city
SELECT city, COUNT(*) AS branch_count
FROM branch
GROUP BY city
ORDER BY city;


-- Q6. Customer details who has highest number of loans
SELECT c.*
FROM customers c
JOIN loan l ON c.customer_id = l.customer_id
GROUP BY c.customer_id, c.first_name, c.last_name, c.email, c.phone, c.city, c.dob
HAVING COUNT(l.loan_id) = (
    SELECT MAX(cnt) FROM (
        SELECT COUNT(*) AS cnt
        FROM loan
        GROUP BY customer_id
    ) t
);


-- Q7. Customers who opened account in past 3 months
SELECT DISTINCT c.*
FROM customers c
JOIN accounts a ON c.customer_id = a.customer_id
WHERE a.dop >= CURRENT_DATE - INTERVAL '3 months';


-- Q8. account id, firstname, lastname for saving accounts
SELECT a.account_id, c.first_name, c.last_name
FROM accounts a
JOIN customers c ON a.customer_id = c.customer_id
WHERE LOWER(a.account_type) = 'saving';


-- Q9. customer id, firstname, branch id, loan amount for people with loans
SELECT c.customer_id, c.first_name, l.branch_id, l.loan_amount
FROM customers c
JOIN loan l ON c.customer_id = l.customer_id;


-- Q10. customer number, customer name, account number where balance < 10000
SELECT c.customer_id,
       c.first_name || ' ' || c.last_name AS customer_name,
       a.account_id
FROM customers c
JOIN accounts a ON c.customer_id = a.customer_id
WHERE a.account_balance < 10000;


-- Q11. customer number, firstname, customer city, branch city
-- where customer city and branch city are different
SELECT DISTINCT c.customer_id,
       c.first_name,
       c.city AS customer_city,
       b.city AS branch_city
FROM customers c
JOIN accounts a ON c.customer_id = a.customer_id
JOIN loan l ON c.customer_id = l.customer_id
JOIN branch b ON l.branch_id = b.branch_id
WHERE c.city <> b.city;


-- Q12. customer number, firstname, lastname, account
-- who has taken loan from more than 1 bank/branch
SELECT c.customer_id, c.first_name, c.last_name, a.account_id
FROM customers c
JOIN accounts a ON c.customer_id = a.customer_id
WHERE c.customer_id IN (
    SELECT customer_id
    FROM loan
    GROUP BY customer_id
    HAVING COUNT(DISTINCT branch_id) > 1
);


-- Q13. citywise, branchwise count of accounts
-- if branch has no records, show 0
-- Using loans linked to branch; Empty Branch should show 0
SELECT b.city,
       b.branch_name,
       COUNT(l.loan_id) AS account_count
FROM branch b
LEFT JOIN loan l ON b.branch_id = l.branch_id
GROUP BY b.city, b.branch_name
ORDER BY b.city, b.branch_name;


-- Q14. firstname of customers who have more than 1 account
SELECT c.first_name
FROM customers c
JOIN accounts a ON c.customer_id = a.customer_id
GROUP BY c.customer_id, c.first_name
HAVING COUNT(a.account_id) > 1;


-- Q15. branch name, branch city where we have the maximum customers
-- Interpreting as branch linked to most loan customers
SELECT b.branch_name, b.city
FROM branch b
JOIN loan l ON b.branch_id = l.branch_id
GROUP BY b.branch_id, b.branch_name, b.city
HAVING COUNT(DISTINCT l.customer_id) = (
    SELECT MAX(cnt) FROM (
        SELECT COUNT(DISTINCT customer_id) AS cnt
        FROM loan
        GROUP BY branch_id
    ) t
);
