package programmingbasics.a04polymorphism;
//written by Ramesh
 abstract class Vehicle {

    final public String identity(int x)
    {
        return "identify of the vehicle";

    }

    public final  void forwardmove()
    {
        System.out.println("vehicle moved forward");
    }

    public  void reversemove()
    {
        System.out.println("some way of reversing");
    }
}
