package ouniverse.kt;

import org.springframework.stereotype.Component;

@Component // this is telling hey sring create the object
public class Mobile
{
    private Sim sim;
    private Battery b;

    public Mobile(Sim s)
    {
        System.out.println("Mobile object created");
        b=new Battery(5000);
    }

    public void call()
    {
        System.out.println("Calling from Mobile");
    }
}
