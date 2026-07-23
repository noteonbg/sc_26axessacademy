package com.campus.finance.model;

import java.util.Arrays;
import java.util.List;

/**
 * One place that hands out ready-made sample data so every demo can reuse it.
 */
public class SampleData {

    public static List<Account> accounts() {
        return Arrays.asList(
                new Account(101, "Asha Rao",     "Mumbai",    "SAVINGS", 15000.0),
                new Account(102, "Ravi Kumar",   "Delhi",     "CURRENT", 250000.0),
                new Account(103, "Meera Nair",   "Mumbai",    "SAVINGS", 800.0),
                new Account(104, "John Mathew",  "Bangalore", "SAVINGS", 42000.0),
                new Account(105, "Sara Khan",    "Delhi",     "CURRENT", 120000.0),
                new Account(106, "Asha Rao",     "Mumbai",    "SAVINGS", 15000.0) // duplicate customer name
        );
    }

    public static List<Transaction> transactions() {
        return Arrays.asList(
                new Transaction(101, "CREDIT", "SALARY",   50000.0),
                new Transaction(101, "DEBIT",  "SHOPPING",  3200.0),
                new Transaction(102, "DEBIT",  "FUEL",      4500.0),
                new Transaction(102, "CREDIT", "SALARY",   90000.0),
                new Transaction(103, "DEBIT",  "SHOPPING",  1500.0),
                new Transaction(104, "CREDIT", "SALARY",   60000.0),
                new Transaction(104, "DEBIT",  "FUEL",      2000.0)
        );
    }
}
