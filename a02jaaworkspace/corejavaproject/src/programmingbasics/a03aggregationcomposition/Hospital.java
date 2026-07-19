package programmingbasics.a03aggregationcomposition;

public class Hospital {
    private String hospitalName;
    private Doctor doctor; // Aggregated object

    public Hospital(String hospitalName, Doctor doctor) {
        this.hospitalName = hospitalName;
        this.doctor = doctor;
    }

    public void displayHospital() {
        System.out.println("Hospital: " + hospitalName);
        doctor.displayDoctor(); // using aggregated object
    }
}
