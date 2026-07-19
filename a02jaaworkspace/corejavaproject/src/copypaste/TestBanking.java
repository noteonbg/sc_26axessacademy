package copypaste;

public class TestBanking {

    // I want jvm to run this class, jvm opinion
    public static void main(String[] args) {

            double interest=BankingUtils.calculateCompoundInterest(2300,2.3,2,1);

        System.out.println(interest);
    }
}
