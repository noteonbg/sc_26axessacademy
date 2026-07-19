package programmingbasics.a01start;

public class A03TypeCastingDemo {

    public static void main(String[] args) {

        // Widening Casting (Automatic) - small to large
        int intVal = 100;
        long longVal = intVal;        // int to long
        float floatVal = longVal;     // long to float

        System.out.println("Widening Typecasting:");
        System.out.println("int value: " + intVal);
        System.out.println("long value: " + longVal);
        System.out.println("float value: " + floatVal);

        // Narrowing Casting (Manual) - large to small
        double doubleVal = 99.99;
        int narrowedInt = (int) doubleVal; // double to int
        byte narrowedByte = (byte) narrowedInt; // int to byte

        System.out.println("\nNarrowing Typecasting:");
        System.out.println("double value: " + doubleVal);
        System.out.println("narrowed to int: " + narrowedInt);
        System.out.println("narrowed to byte: " + narrowedByte);
    }
}
