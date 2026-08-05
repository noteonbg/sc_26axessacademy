package bank;

/**
 * CreditAccount extends Account
 * Extra fields: bonusPoint, limit
 * Method OVERRIDE with limit check and bonus points
 */
public class CreditAccount extends Account {
    int bonusPoint;
    double limit;

    public CreditAccount(String accountNum, String accountOwner, double balance, int bonusPoint, double limit) {
        super(accountNum, accountOwner, balance);
        this.bonusPoint = bonusPoint;
        this.limit = limit;
    }

    @Override
    public void performPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        // cannot go below -limit
        if (balance - amount < -limit) {
            System.out.println("Payment failed. Credit limit exceeded.");
            return;
        }
        balance = balance - amount;
        bonusPoint = bonusPoint + (int)(amount / 100); // 1 point per 100 spent
        System.out.println("Credit payment successful. Balance: " + balance + " | Bonus: " + bonusPoint);
    }

    @Override
    public void performDeposit(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        balance = balance + amount;
        bonusPoint = bonusPoint + (int)(amount / 200); // smaller bonus on deposit
        System.out.println("Credit deposit successful. Balance: " + balance + " | Bonus: " + bonusPoint);
    }

    public int getBonusPoint() { return bonusPoint; }
    public double getLimit() { return limit; }
    public void setBonusPoint(int bonusPoint) { this.bonusPoint = bonusPoint; }

    @Override
    public void display() {
        System.out.println("Credit Account: " + accountNum
                + " | Owner: " + accountOwner
                + " | Balance: " + balance
                + " | Bonus: " + bonusPoint
                + " | Limit: " + limit);
    }
}
