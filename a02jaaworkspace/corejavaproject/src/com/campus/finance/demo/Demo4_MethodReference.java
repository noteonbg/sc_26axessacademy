package com.campus.finance.demo;

import com.campus.finance.model.Account;
import com.campus.finance.model.SampleData;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * CONCEPT 4: METHOD REFERENCE
 *
 * When a lambda does nothing but call one existing method, you can replace it
 * with a shorter "method reference" using the :: operator.
 *
 * The four types (all shown below):
 *   1. Static method              ->  ClassName::staticMethod
 *   2. Instance method of an object ->  object::instanceMethod
 *   3. Instance method of a type   ->  ClassName::instanceMethod
 *   4. Constructor                 ->  ClassName::new
 */
public class Demo4_MethodReference {

    // A plain static helper we can point a method reference at.
    static double toBonus(double salary) {
        return salary * 0.10;
    }

    public static void main(String[] args) {
        List<Account> accounts = SampleData.accounts();

        // 1. Reference to a STATIC method.
        //    Lambda version:  salary -> Demo4_MethodReference.toBonus(salary)
        Function<Double, Double> bonus = Demo4_MethodReference::toBonus;
        System.out.println("Bonus on 50000        : " + bonus.apply(50000.0));

        // 2. Reference to an instance method of a PARTICULAR object (System.out).
        //    Lambda version:  a -> System.out.println(a)
        System.out.println("--- printing all accounts (System.out::println) ---");
        accounts.forEach(System.out::println);

        // 3. Reference to an instance method of an ARBITRARY object of a type.
        //    Lambda version:  a -> a.getOwnerName()
        System.out.println("--- owner names (Account::getOwnerName) ---");
        accounts.stream()
                .map(Account::getOwnerName)
                .forEach(System.out::println);

        // 4. Reference to a CONSTRUCTOR.
        //    Lambda version:  () -> new StringBuilder()
        Supplier<StringBuilder> makeBuilder = StringBuilder::new;
        System.out.println("New builder created   : " + (makeBuilder.get() != null));
    }
}
