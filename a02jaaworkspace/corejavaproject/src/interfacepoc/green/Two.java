package interfacepoc.green;

import interfacepoc.pink.FourPaymentInterface;
import interfacepoc.pink.HDFCPayment3;
import interfacepoc.pink.SCB;

public class Two {

    public static void main(String[] args) {

        FourPaymentInterface pi =new SCB();
        pi.makePayment(23,24);



    }
}
