package interfacepoc.pink;


//2
public class SBIPayment3 implements FourPaymentInterface {
    @Override
    public void makePayment(int from, int amt) {

        System.out.println("sbi payment at work");

    }
}
