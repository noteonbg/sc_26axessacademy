package programmingbasics.a02inheritanceexample;

public class Doctor extends Person {
    String specialization;

    public Doctor(String name, int age, String specialization) {
        super(name, age);  // call superclass constructor
        this.specialization = specialization;
    }
    public String getSpecialization() {
        return specialization;
    }

    @Override
    public void displayInfo() {
        super.displayInfo(); // reuse displayInfo of Person
        System.out.println("Specialization: " + specialization);
    }
}
