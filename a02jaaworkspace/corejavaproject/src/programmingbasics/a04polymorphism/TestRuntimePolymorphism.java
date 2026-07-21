package programmingbasics.a04polymorphism;

public class TestRuntimePolymorphism {

    public static void main(String[] args) {

        Vehicle v =null;
        int x = (int)(Math.random()*20);
        // to tell I dont know what kidn of vehicle i am getting.
        if( x < 5)
            v =new ElectricTW();
        else if( x < 12)
            v =new PetrolTW();
        else
            v =new AeroPlane();

        v.reversemove();  //this line is called as runtime polymorphism

        //why this is polymorphic, i tis a non final function in java..


    }
}
