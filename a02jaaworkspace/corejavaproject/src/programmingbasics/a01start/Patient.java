package programmingbasics.a01start;

// we taught the system hey look My projct needs a data type called Patient, here is its content
//here are its operations.

public class Patient {

    // Private instance variables  content
    private String name;  //non local variable
    private int age;
    private String disease;
    private String patientId;



    //operations


    public Patient(String patientId, String disease) {
        this.patientId = patientId;
        this.disease = disease;
    }

    // Constructor using 'this' keyword
    public Patient(String name, int age, String disease, String patientId) {
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.patientId = patientId;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDisease() {
        return disease;
    }

    public String getPatientId() {
        return patientId;
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
        System.out.println("Patient ID: " + getPatientId());
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Disease: " + getDisease());
        System.out.println("-----------------------------");
    }





}
