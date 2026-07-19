package programmingbasics.a08exceptionhandling.doubt;

import java.io.IOException;

public class RuntimeNOnRuntime {

    public static void main(String[] args) {

        try
        {
            //there is no line here.. which rsults in numberformatexception

        }catch(Exception e)
        {
            System.out.println("freak");
        }

    }

}
