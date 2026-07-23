package com.campus.finance.demo;

import com.campus.finance.model.Account;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * CONCEPT 3: BUILT-IN FUNCTIONAL INTERFACES (java.util.function)
 *
 * Java already ships the four most useful functional interfaces, so most of
 * the time you do NOT need to write your own:
 *
 *   Function<T,R>  -> takes a T, returns an R          (transform)
 *   Predicate<T>   -> takes a T, returns true/false    (test)
 *   Consumer<T>    -> takes a T, returns nothing       (do something / print)
 *   Supplier<T>    -> takes nothing, returns a T       (produce a value)
 */
public class Demo3_BuiltInInterfaces {

    public static void main(String[] args) {
        Account account = new Account(101, "Asha Rao", "Mumbai", "SAVINGS", 15000.0);

        // Function: turn a balance into its 10% bonus.
        Function<Double, Double> bonus = balance -> balance * 0.10;

        // Predicate: is this a high-value account (balance >= 1,00,000)?
        Predicate<Account> isHighValue = a -> a.getBalance() >= 100000;

        // Consumer: print an account in a friendly way (returns nothing).
        Consumer<Account> printer = a -> System.out.println("Account owner: " + a.getOwnerName());

        // Supplier: produce a fresh account number (takes no input).
        Supplier<String> accountNumberGenerator = () -> "AC-" + System.currentTimeMillis();

        System.out.println("Bonus on balance   : " + bonus.apply(account.getBalance()));
        System.out.println("Is high value?     : " + isHighValue.test(account));
        printer.accept(account);
        System.out.println("New account number : " + accountNumberGenerator.get());
    }
}
