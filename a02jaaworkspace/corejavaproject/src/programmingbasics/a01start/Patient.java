package programmingbasics.a01start;
public class Patient {

    // Private instance variables
    private String name;
    private int age;
    private String disease;
    private String patientId;



    // Constructor using 'this' keyword
    public Patient(String name, int age, String disease, String patientId) {
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.patientId = patientId;
    }

    // Getter methods
    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getDisease() {
        return this.disease;
    }

    public String getPatientId() {
        return this.patientId;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    // Method to display patient details
    public void displayInfo() {
        System.out.println("Patient ID: " + this.getPatientId());
        System.out.println("Name: " + this.getName());
        System.out.println("Age: " + this.getAge());
        System.out.println("Disease: " + this.getDisease());
        System.out.println("-----------------------------");
    }





}
