package ouniverse.kt;

import org.springframework.stereotype.Component;


public class Sim {

    private int simno;


    public Sim()
    {
        System.out.println("Sim object created");
    }

    public int getSimno() {
        return simno;
    }

    public void setSimno(int simno) {
        this.simno = simno;
    }
}
