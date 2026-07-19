package programmingbasics.a04polymorphism;

public class Pharmacy {

    // Method to dispense medicine by name
    public void dispenseMedicine(String medicineName) {
        System.out.println("Dispensing medicine: " + medicineName);
    }

    // Overloaded method to dispense with quantity
    public void dispenseMedicine(String medicineName, int quantity) {
        System.out.println("Dispensing " + quantity + " units of " + medicineName);
    }

    // Overloaded method to dispense with dosage info
    public void dispenseMedicine(String medicineName, int quantity, String dosage) {
        System.out.println("Dispensing " + quantity + " units of " + medicineName + " with dosage " + dosage);
    }

    public static void main(String[] args) {
        Pharmacy pharmacy = new Pharmacy();

        pharmacy.dispenseMedicine("Paracetamol");
        pharmacy.dispenseMedicine("Amoxicillin", 10);
        pharmacy.dispenseMedicine("Ibuprofen", 5, "200mg");
    }
}
