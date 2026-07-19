package programmingbasics.a01start;

public class A04Operators {

    public static void main(String[] args) {

        int a = 10, b = 5;
        boolean x = true, y = false;

        // 1. Arithmetic Operators
        System.out.println("----- Arithmetic Operators -----");
        System.out.println("a + b = " + (a + b));
        System.out.println("a - b = " + (a - b));
        System.out.println("a * b = " + (a * b));
        System.out.println("a / b = " + (a / b));
        System.out.println("a % b = " + (a % b));

        // 2. Relational Operators
        System.out.println("\n----- Relational Operators -----");
        System.out.println("a == b: " + (a == b));
        System.out.println("a != b: " + (a != b));
        System.out.println("a > b: " + (a > b));
        System.out.println("a < b: " + (a < b));
        System.out.println("a >= b: " + (a >= b));
        System.out.println("a <= b: " + (a <= b));

        // 3. Logical Operators
        System.out.println("\n----- Logical Operators -----");
        System.out.println("x && y: " + (x && y));
        System.out.println("x || y: " + (x || y));
        System.out.println("!x: " + (!x));

        // 4. Bitwise Operators
        System.out.println("\n----- Bitwise Operators -----");
        System.out.println("a & b: " + (a & b));  // 10 & 5 = 0
        System.out.println("a | b: " + (a | b));  // 10 | 5 = 15
        System.out.println("a ^ b: " + (a ^ b));  // 10 ^ 5 = 15
        System.out.println("~a: " + (~a));        // bitwise complement
        System.out.println("a << 1: " + (a << 1)); // left shift
        System.out.println("a >> 1: " + (a >> 1)); // right shift
        System.out.println("a >>> 1: " + (a >>> 1)); // unsigned right shift

        // 5. Assignment Operators
        System.out.println("\n----- Assignment Operators -----");
        int c = 20;
        c += 5;
        System.out.println("c += 5: " + c);
        c -= 3;
        System.out.println("c -= 3: " + c);
        c *= 2;
        System.out.println("c *= 2: " + c);
        c /= 4;
        System.out.println("c /= 4: " + c);
        c %= 3;
        System.out.println("c %= 3: " + c);

        // 6. Unary Operators
        System.out.println("\n----- Unary Operators -----");
        int d = 5;
        System.out.println("d = " + d);
        System.out.println("++d = " + (++d));  // pre-increment
        System.out.println("d++ = " + (d++));  // post-increment
        System.out.println("After d++: " + d);
        System.out.println("--d = " + (--d));  // pre-decrement
        System.out.println("d-- = " + (d--));  // post-decrement
        System.out.println("After d--: " + d);
        System.out.println("-d = " + (-d));
        System.out.println("+d = " + (+d));

        // 7. Ternary Operator
        System.out.println("\n----- Ternary Operator -----");
        int max = (a > b) ? a : b;
        System.out.println("Max of a and b: " + max);

        // 8. instanceof Operator
        System.out.println("\n----- instanceof Operator -----");
        String str = "Java";
        System.out.println("str instanceof String: " + (str instanceof String));

        // 9. Type Cast Operator
        System.out.println("\n----- Type Cast Operator -----");
        double pi = 3.14159;
        int intPi = (int) pi;
        System.out.println("Double value: " + pi);
        System.out.println("Casted to int: " + intPi);
    }
}
