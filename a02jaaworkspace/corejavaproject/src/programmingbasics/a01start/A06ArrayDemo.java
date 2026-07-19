package programmingbasics.a01start;

public class A06ArrayDemo {

    public static void main(String[] args) {

        // 1. Declaring and initializing a one-dimensional array
        int[] numbers = {10, 20, 30, 40, 50};

        System.out.println("----- One-Dimensional Array -----");
        System.out.println("Length of array: " + numbers.length);
        System.out.println("Elements in array:");
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("numbers[" + i + "] = " + numbers[i]);
        }

        // 2. Using enhanced for loop
        System.out.println("\nUsing enhanced for loop:");
        for (int num : numbers) {
            System.out.println(num);
        }

        // 3. Declaring and initializing a two-dimensional array (matrix)
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("\n----- Two-Dimensional Array -----");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println(); // new line after each row
        }

        // 4. Array of Strings
        String[] fruits = {"Apple", "Banana", "Cherry"};

        System.out.println("\n----- Array of Strings -----");
        for (int i = 0; i < fruits.length; i++) {
            System.out.println("fruits[" + i + "] = " + fruits[i]);
        }
    }
}

