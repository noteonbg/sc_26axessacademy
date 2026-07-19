package programmingbasics.a04polymorphism;

public class Hospital {
    public static void main(String[] args) {
        Staff staff1 = null;

        int x  =(int)(Math.random()*10);
        if( x < 5)
             staff1 =new Nurse();
        else
            staff1 =new Doctor();


        System.out.println("=== Run-Time Polymorphism ===");
        staff1.performDuties();

        }

}

