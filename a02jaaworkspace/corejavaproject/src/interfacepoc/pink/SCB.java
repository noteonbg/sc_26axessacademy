package interfacepoc.pink;

public class SCB implements FourPaymentInterface{

    @Override
    public void makePayment(int from, int amt) {
        System.out.println("made payment using Standard chatertered");

    }
}
