package programmingbasics.a03aggregationcomposition;

public class Patient {
    private String name;
    private int age;
    private MedicalRecord record; // Composed object

    public Patient(String name, int age, String recordId, String diagnosis) {
        this.name = name;
        this.age = age;
        // Composition: creating the object internally
        this.record = new MedicalRecord(recordId, diagnosis);
    }

    public void displayPatient() {
        System.out.println("Patient: " + name + ", Age: " + age);
        record.displayRecord(); // using composed object
    }
}
