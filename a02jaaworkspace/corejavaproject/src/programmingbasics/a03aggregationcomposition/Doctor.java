package programmingbasics.a03aggregationcomposition;

public class Doctor {
    private String name;
    private String specialization;

    public Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    public void displayDoctor() {
        System.out.println("Doctor: " + name + ", Specialization: " + specialization);
    }
}
