package programmingbasics.a06staticandfinal;

public class HospitalApp {
    public static void main(String[] args) {
        // Static method call without creating object
        HospitalInfo.displayHospitalName();

        // Create doctor object
        Doctor doc = new Doctor("Dr. Ramesh", "Neurology");

        doc.displayDoctorInfo();

        // Final method call
        doc.showRegistrationInfo();
    }
}
