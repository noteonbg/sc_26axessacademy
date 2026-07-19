package programmingbasics.a04polymorphism;

public class Doctor extends Staff {
    @Override
    public void performDuties() {
        System.out.println("Doctor diagnoses and treats patients.");
    }
}
