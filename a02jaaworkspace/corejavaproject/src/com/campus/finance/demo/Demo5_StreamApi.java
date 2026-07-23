package com.campus.finance.demo;

import com.campus.finance.model.Account;
import com.campus.finance.model.SampleData;
import com.campus.finance.model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CONCEPT 5: STREAM API  (filter, map, reduce, sorted, collect)
 *
 * A stream is a pipeline over a collection:
 *   source  ->  intermediate ops (filter/map/sorted)  ->  terminal op (collect/reduce/forEach)
 *
 * A stream does NOT change the original list; it produces a new result.
 * Below we answer real questions a bank might ask, mirroring the PDF use case.
 */
public class Demo5_StreamApi {

    public static void main(String[] args) {
        List<Account> accounts = SampleData.accounts();
        List<Transaction> transactions = SampleData.transactions();

        // filter(): only high-value accounts (balance >= 1,00,000).
        System.out.println("--- High value accounts (filter) ---");
        accounts.stream()
                .filter(a -> a.getBalance() >= 100000)
                .forEach(a -> System.out.println(a.getOwnerName() + " : " + a.getBalance()));

        // map(): greet every customer as "Hi <name>".
        System.out.println("\n--- Greetings (map) ---");
        accounts.stream()
                .map(a -> "Hi " + a.getOwnerName())
                .forEach(System.out::println);

        // map() + reduce(): total money the bank holds.
        System.out.println("\n--- Total balance (map + reduce) ---");
        double totalBalance = accounts.stream()
                .map(Account::getBalance)
                .reduce(0.0, Double::sum);
        System.out.println("Total balance across all accounts = " + totalBalance);

        // Unique customers from Mumbai, sorted by name (PDF use case).
        System.out.println("\n--- Unique Mumbai customers, sorted (filter + map + distinct + sorted) ---");
        accounts.stream()
                .filter(a -> a.getCity().equals("Mumbai"))
                .map(Account::getOwnerName)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        // Bonus = balance * 0.1 for each account (map).
        System.out.println("\n--- Bonus per customer (map) ---");
        accounts.stream()
                .map(a -> a.getOwnerName() + " -> bonus " + (a.getBalance() * 0.1))
                .forEach(System.out::println);

        // Group account owners by city (collect to a Map) — "who is in each location".
        System.out.println("\n--- Customers grouped by city (collect) ---");
        accounts.stream()
                .collect(Collectors.groupingBy(
                        Account::getCity,
                        Collectors.mapping(Account::getOwnerName, Collectors.toList())))
                .forEach((city, owners) -> System.out.println(city + " : " + owners));

        // reduce() on transactions: total money spent (DEBIT only).
        System.out.println("\n--- Total DEBIT amount (filter + map + reduce) ---");
        double totalSpent = transactions.stream()
                .filter(t -> t.getType().equals("DEBIT"))
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
        System.out.println("Total spent (all debits) = " + totalSpent);
    }
}
