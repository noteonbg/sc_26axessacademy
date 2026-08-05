package bank;

/**
 * Base Account class
 * Fields: accountNum, accountOwner, balance
 * Methods: performPayment (withdraw), performDeposit
 */
public class Account {
    String accountNum;
    String accountOwner;
    double balance;

    public Account(String accountNum, String accountOwner, double balance) {
        this.accountNum = accountNum;
        this.accountOwner = accountOwner;
        this.balance = balance;
    }

    // withdraw money
    public void performPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        if (amount > balance) {
            System.out.println("Not enough balance.");
            return;
        }
        balance = balance - amount;
        System.out.println("Payment successful. New balance: " + balance);
    }

    // add money
    public void performDeposit(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        balance = balance + amount;
        System.out.println("Deposit successful. New balance: " + balance);
    }

    public void display() {
        System.out.println("Account: " + accountNum
                + " | Owner: " + accountOwner
                + " | Balance: " + balance);
    }

    public String getAccountNum() { return accountNum; }
    public String getAccountOwner() { return accountOwner; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
