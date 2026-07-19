package programmingbasics.a03aggregationcomposition;

public class HospitalApp {
    public static void main(String[] args) {

        // Aggregation Example
        Doctor doc = new Doctor("Dr. Ramesh", "Cardiology");
        Hospital hospital = new Hospital("City Hospital", doc);
        System.out.println("=== Aggregation Example ===");
        hospital.displayHospital();

        // Composition Example
        Patient patient = new Patient("Mahesh", 30, "R001", "Hypertension");
        System.out.println("\n=== Composition Example ===");
        patient.displayPatient();
    }
}

