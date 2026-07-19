package programmingbasics.a01start;

public class a05DecisionMakingDemo {

    public static void main(String[] args) {
        int number = 75;

        // 1. if statement
        if (number > 0) {
            System.out.println("The number is positive.");
        }

        // 2. if-else statement
        if (number % 2 == 0) {
            System.out.println("The number is even.");
        } else {
            System.out.println("The number is odd.");
        }

        // 3. if-else-if ladder
        if (number < 50) {
            System.out.println("The number is less than 50.");
        } else if (number == 50) {
            System.out.println("The number is exactly 50.");
        } else {
            System.out.println("The number is greater than 50.");
        }

        // 4. switch statement
        int day = 1;
        System.out.println("\nUsing switch statement:");
        switch (day) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            default:
                System.out.println("Other day");
        }
    }
}
