package programmingbasics.a01start;

import java.util.Scanner;


public class A07HospitalSystem {


   

    public static void main1(String[] args) {
        // Creating patient objects
        Patient p1 = new Patient("Alice Smith", 35, "Diabetes", "P001");
        Patient p2 = new Patient("Bob Johnson", 42, "Hypertension", "P002");

        Patient poc =getSomePatient();
        System.out.println(poc.getDisease());


        // Modify patient details using setters (optional)
        p2.setDisease("Cardiac Arrest");

        // Displaying patient details
        p1.displayInfo();
        p2.displayInfo();
    }

    private static Patient getSomePatient() {

        Patient tempn =
                new Patient("abc",25,"vagueness","23");
        return tempn;



    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter patient ID: ");
        String patientId = scanner.nextLine();

        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  // consume newline

        System.out.print("Enter disease: ");
        String disease = scanner.nextLine();

        Patient patient = new Patient(name, age, disease,patientId);

        System.out.println("\nPatient Details:");
        patient.displayInfo();

        scanner.close();
    }

}
