package programmingbasics.a04polymorphism;

public class Nurse extends Staff {
    @Override
    public void performDuties() {
        System.out.println("Nurse administers medication and monitors patients.");
    }
}
