package programmingbasics.a08exceptionhandling;

// File: MedicalBilling.java
import java.util.Scanner;

public class MedicalBilling {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // User input
            System.out.print("Enter total billing amount: ");
            double totalAmount = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter number of patients: ");
            int numberOfPatients = Integer.parseInt(scanner.nextLine());

            // Custom validation
            if (numberOfPatients <= 0) {
                throw new InvalidPatientCountException("Patient count must be greater than 0.");
            }

            // Calculation
            double avgBill = totalAmount / numberOfPatients;
            System.out.println("Average bill per patient: ₹" + avgBill);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numeric values only.");
        } catch (InvalidPatientCountException e) {
            System.out.println("Custom Error: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Error: Cannot divide by zero.");
        } finally {
            System.out.println("Billing process completed. Thank you.");
            scanner.close();
        }
    }
}

