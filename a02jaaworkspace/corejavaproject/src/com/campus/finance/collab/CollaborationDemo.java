package com.campus.finance.collab;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Functional Programming — all concepts demonstrated on ONE List<Account>.
 *
 * THE STORY (why collaboration is the theme):
 * A bank runs a nightly "Customer Insights" job over the same List<Account>.
 * It is built by a cross-functional team, and each person contributes ONE small
 * function. Because these are plain functions (functional programming), each
 * person can write, test and own their piece independently, and the pieces then
 * COMPOSE into shared pipelines. That is the real-world value of FP for a team.
 *
 * Cast of contributors:
 *   - Priya  (Risk team)        -> a Predicate that flags high-value accounts
 *   - Arjun  (Regional team)    -> a Predicate that flags Mumbai accounts
 *   - Neha   (Rewards team)     -> a Function that computes a bonus
 *   - Sam    (Reporting team)   -> a Consumer that prints an account nicely
 *   - Devi   (Audit team)       -> a Supplier that produces an audit id
 *   - Ravi   (Support team)     -> an Optional-based safe lookup
 */
public class CollaborationDemo {

    public static void main(String[] args) {
        // The ONE shared data source used for every concept below.
        List<Account> accounts = bankPortfolio();

        section("Shared data: the List<Account> everyone works on");
        accounts.forEach(System.out::println);

        // ------------------------------------------------------------------
        section("1. FUNCTIONAL INTERFACE + 2. LAMBDA  (Segmentation team)");
        // The team agreed on the AccountClassifier contract (Concept 1).
        // Here a teammate supplies the behaviour with a LAMBDA (Concept 2).
        AccountClassifier tierClassifier =
                a -> a.getBalance() >= 100000 ? "PRIORITY" : "STANDARD";


        for (Account a : accounts) {
            System.out.println("Account#" + a.getId() + " -> " + tierClassifier.classify(a));
        }

        // ------------------------------------------------------------------
        section("3. BUILT-IN FUNCTIONAL INTERFACES  (one per team member)");

        // Priya (Risk): Predicate = a test that returns true/false.
        Predicate<Account> highValue = a -> a.getBalance() >= 100000;
        // Arjun (Regional): another Predicate, written independently.
        Predicate<Account> inMumbai = a -> a.getCity().equals("Mumbai");
        // Neha (Rewards): Function = transform an Account into a bonus amount.
        Function<Account, Double> bonus = a -> a.getBalance() * 0.10;
        // Sam (Reporting): Consumer = do something (print), return nothing.
        Consumer<Account> printer =
                a -> System.out.println("   report: Account#" + a.getId() + " RM=" + a.getRelationshipManager());
        // Devi (Audit): Supplier = produce a value from nothing.
        Supplier<String> auditId = () -> "AUDIT-" + System.nanoTime();

        System.out.println("Priya's high-value test on Account#101: " + highValue.test(accounts.get(0)));
        System.out.println("Neha's bonus for Account#101          : " + bonus.apply(accounts.get(0)));
        System.out.print("Sam's report line                     :\n");
        printer.accept(accounts.get(0));
        System.out.println("Devi's audit id                       : " + auditId.get());

        // ------------------------------------------------------------------
        section("COLLABORATION HIGHLIGHT: composing two people's functions");
        // Priya's rule AND Arjun's rule combined WITHOUT either rewriting the other.
        Predicate<Account> highValueInMumbai = highValue.and(inMumbai);
        System.out.println("High-value AND Mumbai accounts:");
        accounts.stream()
                .filter(highValueInMumbai)
                .forEach(a -> System.out.println("   " + a));

        // Neha's bonus THEN formatted by another teammate (Function.andThen).
        Function<Account, String> bonusLabel =
                bonus.andThen(amount -> "bonus = " + amount);
        System.out.println("Composed bonus label for Account#102: " + bonusLabel.apply(accounts.get(1)));

        // ------------------------------------------------------------------
        section("4. METHOD REFERENCE  (shorthand for simple lambdas)");
        System.out.println("Relationship managers (Account::getRelationshipManager):");
        accounts.stream()
                .map(Account::getRelationshipManager)     // instance method of a type
                .distinct()
                .forEach(System.out::println);            // instance method of an object

        // ------------------------------------------------------------------
        section("5. STREAM API on the List<Account>  (filter / map / reduce / sorted / collect)");

        // filter (Priya's rule) + map (owner-facing message)
        System.out.println("filter + map -> greetings for high-value customers:");
        accounts.stream()
                .filter(highValue)
                .map(a -> "Hi owners of Account#" + a.getId() + " " + a.getOwners())
                .forEach(System.out::println);

        // reduce -> single summary number (total money the bank holds)
        double totalBalance = accounts.stream()
                .map(Account::getBalance)
                .reduce(0.0, Double::sum);
        System.out.println("reduce -> total balance across all accounts = " + totalBalance);

        // sorted -> order the portfolio by balance (descending)
        System.out.println("sorted -> accounts by balance (highest first):");
        accounts.stream()
                .sorted((a, b) -> Double.compare(b.getBalance(), a.getBalance()))
                .forEach(a -> System.out.println("   Account#" + a.getId() + " = " + a.getBalance()));

        // collect + groupingBy -> which employee handles which accounts (collaboration view)
        System.out.println("collect -> accounts grouped by Relationship Manager:");
        accounts.stream()
                .collect(Collectors.groupingBy(
                        Account::getRelationshipManager,
                        Collectors.mapping(Account::getId, Collectors.toList())))
                .forEach((rm, ids) -> System.out.println("   " + rm + " manages accounts " + ids));

        // "multiple people": every DISTINCT person who owns any account (flatMap over the List)
        System.out.println("flatMap -> all distinct customers (people) in the portfolio:");
        accounts.stream()
                .flatMap(a -> a.getOwners().stream())
                .distinct()
                .sorted()
                .forEach(name -> System.out.println("   " + name));

        // ------------------------------------------------------------------
        section("6. OPTIONAL  (Support team: safe lookup, no NullPointerException)");
        System.out.println("Lookup existing Account#103 : "
                + findById(accounts, 103).map(Account::getCity).orElse("NOT FOUND"));
        System.out.println("Lookup missing  Account#999 : "
                + findById(accounts, 999).map(Account::getCity).orElse("NOT FOUND"));

        section("Done - every concept ran on the SAME List<Account>, built by the whole team.");
    }

    /** CONCEPT 6 helper: returns Optional instead of possibly-null (safe for teammates to call). */
    private static Optional<Account> findById(List<Account> accounts, int id) {
        return accounts.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
    }

    /** The single portfolio (List<Account>) that the whole demo operates on. */
    private static List<Account> bankPortfolio() {
        return Arrays.asList(
                new Account(101, "SAVINGS", "Mumbai",    150000.0, Arrays.asList("Asha Rao", "Vijay Rao"), "Meena"),
                new Account(102, "CURRENT", "Delhi",     250000.0, Arrays.asList("Ravi Kumar"),            "Karan"),
                new Account(103, "SAVINGS", "Mumbai",     48000.0, Arrays.asList("Meera Nair"),            "Meena"),
                new Account(104, "SAVINGS", "Bangalore",  92000.0, Arrays.asList("John Mathew", "Asha Rao"), "Karan"),
                new Account(105, "CURRENT", "Mumbai",    300000.0, Arrays.asList("Sara Khan", "Imran Khan"), "Meena")
        );
    }

    private static void section(String title) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("  " + title);
        System.out.println("==================================================");
    }
}
