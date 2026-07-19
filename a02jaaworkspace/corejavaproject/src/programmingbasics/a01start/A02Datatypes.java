package programmingbasics.a01start;

public class A02Datatypes {

    public static void main(String[] args) {

        // Primitive Data Types
        byte byteVar = 100;
        short shortVar = 10000;
        int intVar = 100000;
        long longVar = 10000000000L;

        float floatVar = 10.5f;
        double doubleVar = 20.123456;

        char charVar = 'A';
        boolean booleanVar = true;

        // Non-Primitive / Reference Data Types
        String stringVar = "Hello, Java!";
        int[] intArray = {1, 2, 3, 4, 5};
        Integer integerObject = Integer.valueOf(42);
        A02Datatypes obj = new A02Datatypes(); // Instance of class

        // Printing values
        System.out.println("----- Primitive Data Types -----");
        System.out.println("byte: " + byteVar);
        System.out.println("short: " + shortVar);
        System.out.println("int: " + intVar);
        System.out.println("long: " + longVar);
        System.out.println("float: " + floatVar);
        System.out.println("double: " + doubleVar);
        System.out.println("char: " + charVar);
        System.out.println("boolean: " + booleanVar);

        System.out.println("\n----- Reference (Non-Primitive) Data Types -----");
        System.out.println("String: " + stringVar);
        System.out.print("Array: ");
        for (int num : intArray) {
            System.out.print(num + " ");
        }
        System.out.println();
        System.out.println("Wrapper Object (Integer): " + integerObject);
        System.out.println("Class Object: " + obj.toString());
    }
}
