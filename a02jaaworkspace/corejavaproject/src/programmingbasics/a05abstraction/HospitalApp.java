package programmingbasics.a05abstraction;

public class HospitalApp {

    public static void main(String[] args) {
        Doctor doctor = new Doctor("Dr. Arvind", 45, "Pediatrics");

        doctor.showDetails();
        System.out.println();
        doctor.diagnose();
        doctor.prescribeMedication();
    }
}

