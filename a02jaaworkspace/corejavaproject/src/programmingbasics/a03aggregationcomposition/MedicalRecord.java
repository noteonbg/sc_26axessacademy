package programmingbasics.a03aggregationcomposition;

public class MedicalRecord {
    private String recordId;
    private String diagnosis;

    public MedicalRecord(String recordId, String diagnosis) {
        this.recordId = recordId;
        this.diagnosis = diagnosis;
    }

    public void displayRecord() {
        System.out.println("Record ID: " + recordId + ", Diagnosis: " + diagnosis);
    }
}

