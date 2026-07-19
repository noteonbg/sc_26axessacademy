package programmingbasics.a01start;

public class A08variablescopedemo {

    // Instance variable
    String patientName;

    // Static variable
    static String hospitalName = "City Hospital";

    // Constructor to set instance variable
    public A08variablescopedemo(String name) {
        this.patientName = name;
    }

    // Method to show variables
    public void showDetails() {
        // Local variable
        int roomNumber = 101;

        System.out.println("Hospital Name (static): " + hospitalName);
        System.out.println("Patient Name (instance): " + patientName);
        System.out.println("Room Number (local): " + roomNumber);
    }

    // Main method
    public static void main(String[] args) {
        A08variablescopedemo p1 = new A08variablescopedemo("Alice");
        A08variablescopedemo p2 = new A08variablescopedemo("Bob");

        p1.showDetails();
        p2.showDetails();

        // Accessing static variable directly via class
        System.out.println("Accessing static variable: " + A08variablescopedemo.hospitalName);
    }
}

