package programmingbasics.a06staticandfinal;

public class HospitalInfo {

    // Static variable - shared by all instances
    static String hospitalName = "Cure Hospital";

    // Final variable - constant value
    final String registrationNumber = "REG-2025-HOSP";

    // Static method - can be called without object
    public static void displayHospitalName() {
        System.out.println("Hospital Name: " + hospitalName);
    }

    // Final method - cannot be overridden in subclasses
    public final void showRegistrationInfo() {
        System.out.println("Hospital Registration Number: " + registrationNumber);
    }
}
