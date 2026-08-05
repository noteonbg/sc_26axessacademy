package bank;

/**
 * DebitAccount extends Account
 * Extra field: password
 * Method OVERLOAD with password checking
 */
public class DebitAccount extends Account {
    String password;

    public DebitAccount(String accountNum, String accountOwner, double balance, String password) {
        super(accountNum, accountOwner, balance);
        this.password = password;
    }

    // Overloaded payment with password
    public void performPayment(double amount, String inputPassword) {
        if (!password.equals(inputPassword)) {
            System.out.println("Wrong password. Payment failed.");
            return;
        }
        performPayment(amount);
    }

    // Overloaded deposit with password
    public void performDeposit(double amount, String inputPassword) {
        if (!password.equals(inputPassword)) {
            System.out.println("Wrong password. Deposit failed.");
            return;
        }
        performDeposit(amount);
    }

    public String getPassword() { return password; }

    @Override
    public void display() {
        System.out.println("Debit Account: " + accountNum
                + " | Owner: " + accountOwner
                + " | Balance: " + balance);
    }
}
