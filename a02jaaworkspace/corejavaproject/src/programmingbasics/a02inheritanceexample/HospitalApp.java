package programmingbasics.a02inheritanceexample;

public class HospitalApp {

    public static void main(String[] args) {
        Doctor doc = new Doctor("Dr. Emily", 40, "Cardiology");
        doc.setMobileNumber(44);
        doc.displayInfo();
        doc.addEmil("abc@def.com");
    }
}
