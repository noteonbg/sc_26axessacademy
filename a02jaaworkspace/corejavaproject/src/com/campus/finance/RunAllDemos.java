package com.campus.finance;

import com.campus.finance.demo.Demo1_FunctionalInterface;
import com.campus.finance.demo.Demo2_LambdaExpression;
import com.campus.finance.demo.Demo3_BuiltInInterfaces;
import com.campus.finance.demo.Demo4_MethodReference;
import com.campus.finance.demo.Demo5_StreamApi;
import com.campus.finance.demo.Demo6_Optional;

/**
 * Runs every demo one after another so students can see all six concepts
 * in a single run. You can also run each Demo file on its own.
 */
public class RunAllDemos {

    public static void main(String[] args) {
        banner("1. FUNCTIONAL INTERFACE");
        Demo1_FunctionalInterface.main(args);

        banner("2. LAMBDA EXPRESSION");
        Demo2_LambdaExpression.main(args);

        banner("3. BUILT-IN FUNCTIONAL INTERFACES");
        Demo3_BuiltInInterfaces.main(args);

        banner("4. METHOD REFERENCE");
        Demo4_MethodReference.main(args);

        banner("5. STREAM API");
        Demo5_StreamApi.main(args);

        banner("6. OPTIONAL CLASS");
        Demo6_Optional.main(args);
    }

    private static void banner(String title) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("  " + title);
        System.out.println("==================================================");
    }
}
