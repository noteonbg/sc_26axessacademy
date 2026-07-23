package com.campus.finance.demo;

import com.campus.finance.model.Account;
import com.campus.finance.model.SampleData;

import java.util.List;
import java.util.Optional;

/**
 * CONCEPT 6: OPTIONAL CLASS
 *
 * Optional<T> is a box that either holds a value or is empty.
 * It helps us avoid the dreaded NullPointerException by making
 * "there might be no value" visible and forcing us to handle it.
 *
 * Finance example: look up an account by id — it may or may not exist.
 */
public class Demo6_Optional {

    // Returns an Optional instead of possibly returning null.
    static Optional<Account> findAccountById(List<Account> accounts, int id) {
        return accounts.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
    }

    public static void main(String[] args) {
        List<Account> accounts = SampleData.accounts();

        // Case 1: the account exists.
        Optional<Account> found = findAccountById(accounts, 101);
        found.ifPresent(a -> System.out.println("Found: " + a.getOwnerName()));

        // Case 2: the account does NOT exist — no crash, we give a safe default.
        Optional<Account> missing = findAccountById(accounts, 999);
        String name = missing
                .map(Account::getOwnerName)
                .orElse("No such customer");
        System.out.println("Lookup for id 999: " + name);

        // orElseGet lets us compute a fallback only when needed.
        double balance = findAccountById(accounts, 999)
                .map(Account::getBalance)
                .orElseGet(() -> 0.0);
        System.out.println("Balance for id 999 (safe): " + balance);

        // Compare with the OLD, risky style (kept commented so students see the danger):
        // Account a = null;
        // System.out.println(a.getOwnerName()); // <-- NullPointerException!
    }
}
