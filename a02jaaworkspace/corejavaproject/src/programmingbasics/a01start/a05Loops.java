package programmingbasics.a01start;

public class a05Loops {

    public static void main(String[] args) {

        // 1. for loop
        System.out.println("----- for loop -----");
        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }

        // 2. while loop
        System.out.println("\n----- while loop -----");
        int j = 1;
        while (j <= 5) {
            System.out.println("j = " + j);
            j++;
        }

        // 3. do-while loop
        System.out.println("\n----- do-while loop -----");
        int k = 1;
        do {
            System.out.println("k = " + k);
            k++;
        } while (k <= 5);

        // 4. Enhanced for loop (for-each)
        System.out.println("\n----- enhanced for loop (for-each) -----");
        int[] numbers = {10, 20, 30, 40, 50};
        for (int num : numbers) {
            System.out.println("num = " + num);
        }
    }
}
