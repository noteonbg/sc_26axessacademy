package bank;

/**
 * Small OOP demo for assignment steps 1 to 6 (without database)
 */
public class OOPDemo {
    public static void main(String[] args) {
        System.out.println("=== Step 2: Account object ===");
        Account a1 = new Account("A100", "Ravi", 10000);
        a1.performDeposit(2000);
        a1.performPayment(1500);
        a1.display();

        System.out.println("\n=== Step 5: Debit overload with password ===");
        DebitAccount debit = new DebitAccount("D100", "Anita", 8000, "pass123");
        debit.performPayment(500, "wrong");
        debit.performPayment(500, "pass123");
        debit.performDeposit(1000, "pass123");
        debit.display();

        System.out.println("\n=== Step 4 & 6: Credit override + polymorphism ===");
        Account ref1 = new CreditAccount("C100", "Sneha", 5000, 0, 2000);
        Account ref2 = new DebitAccount("D200", "Amit", 7000, "secret");

        ref1.performPayment(1000);   // Credit overridden method
        ref1.performDeposit(500);
        ref1.display();

        ref2.performPayment(700);    // Debit normal method through parent ref
        ref2.display();
    }
}
