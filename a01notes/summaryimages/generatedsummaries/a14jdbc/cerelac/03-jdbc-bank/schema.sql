-- Run in pgAdmin on database bankdb before starting JDBC app

DROP TABLE IF EXISTS bank_account;

CREATE TABLE bank_account (
    account_num    VARCHAR(20) PRIMARY KEY,
    account_owner  VARCHAR(100) NOT NULL,
    balance        NUMERIC(12,2) NOT NULL,
    account_type   VARCHAR(20) NOT NULL,   -- DEBIT or CREDIT
    password       VARCHAR(50),            -- for DEBIT
    bonus_point    INT DEFAULT 0,          -- for CREDIT
    account_limit  NUMERIC(12,2)           -- for CREDIT
);

SELECT * FROM bank_account;
