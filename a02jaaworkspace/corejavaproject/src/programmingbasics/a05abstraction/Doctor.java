package programmingbasics.a05abstraction;

public class Doctor extends MedicalProfessional implements Duties {
    private String specialization;

    public Doctor(String name, int age, String specialization) {
        super(name, age);
        this.specialization = specialization;
    }

    @Override
    public void showDetails() {
        System.out.println("Doctor Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Specialization: " + specialization);
    }

    @Override
    public void diagnose() {
        System.out.println("Diagnosing the patient based on symptoms.");
    }

    @Override
    public void prescribeMedication() {
        System.out.println("Prescribing suitable medication.");
    }
}
