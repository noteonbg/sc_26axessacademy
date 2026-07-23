package com.campus.finance.demo;

/**
 * CONCEPT 1: FUNCTIONAL INTERFACE
 *
 * A functional interface is an interface with EXACTLY ONE abstract method.
 * We mark it with @FunctionalInterface (optional, but it lets the compiler
 * warn us if we accidentally add a second abstract method).
 *
 * Finance example: a rule that calculates interest on a given amount.
 */
public class Demo1_FunctionalInterface {

    // Our own functional interface: one job, one method.
    @FunctionalInterface
    interface InterestRule {
        double calculate(double principal);
    }

    public static void main(String[] args) {
        double principal = 10000.0;

        // OLD WAY (before Java 8): anonymous class — lots of boilerplate.
        InterestRule savingsOldWay = new InterestRule() {
            @Override
            public double calculate(double amount) {
                return amount * 0.04; // 4% savings interest
            }
        };

        // NEW WAY (Java 8+): a lambda gives the same behaviour in one line.
        InterestRule savingsLambda = amount -> amount * 0.04;
        InterestRule fixedDeposit  = amount -> amount * 0.07; // 7% FD interest

        System.out.println("Interest (anonymous class): " + savingsOldWay.calculate(principal));
        System.out.println("Interest (lambda, savings): " + savingsLambda.calculate(principal));
        System.out.println("Interest (lambda, FD):      " + fixedDeposit.calculate(principal));
    }
}
