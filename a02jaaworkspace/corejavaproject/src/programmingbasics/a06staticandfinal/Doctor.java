package programmingbasics.a06staticandfinal;

public class Doctor extends HospitalInfo {
    private String name;
    private String specialization;

    public Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    public void displayDoctorInfo() {
        System.out.println("Doctor Name: " + name);
        System.out.println("Specialization: " + specialization);
    }

    // Can't override showRegistrationInfo() because it's final
}
