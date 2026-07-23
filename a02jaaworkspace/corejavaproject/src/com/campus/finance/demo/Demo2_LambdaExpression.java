package com.campus.finance.demo;

/**
 * CONCEPT 2: LAMBDA EXPRESSION
 *
 * A lambda is an "anonymous function" (a function with no name).
 * Syntax:  (parameters) -> { body }
 * For a single expression you can drop the braces and the 'return' keyword.
 *
 * Finance examples below: zero, one, and two parameter lambdas.
 */
public class Demo2_LambdaExpression {

    // Small functional interfaces so we have something for the lambdas to fill.
    @FunctionalInterface
    interface NoInput {
        String get();
    }

    @FunctionalInterface
    interface OneInput {
        double apply(double amount);
    }

    @FunctionalInterface
    interface TwoInputs {
        double apply(double a, double b);
    }

    public static void main(String[] args) {
        // Zero parameters: supply today's currency.
        NoInput currency = () -> "INR";

        // One parameter: 10% bonus on a salary (single line, no braces, no 'return').
        OneInput bonus = salary -> salary * 0.10;

        // Two parameters: add two amounts (e.g. two instalments).
        TwoInputs total = (a, b) -> a + b;

        System.out.println("Currency          : " + currency.get());
        System.out.println("Bonus on 50000    : " + bonus.apply(50000));
        System.out.println("Total of 1200+800 : " + total.apply(1200, 800));
    }
}
