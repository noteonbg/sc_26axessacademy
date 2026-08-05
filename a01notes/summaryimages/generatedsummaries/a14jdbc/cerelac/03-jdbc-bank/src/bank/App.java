package bank;

import java.util.List;
import java.util.Scanner;

/**
 * Menu-driven Bank Account Management System with JDBC
 */
public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountDAO dao = new AccountDAO();

        while (true) {
            System.out.println("\n===== Bank Account Menu =====");
            System.out.println("1. Add Account");
            System.out.println("2. Display All Accounts");
            System.out.println("3. Display Account by Id");
            System.out.println("4. Perform Payment");
            System.out.println("5. Perform Deposit");
            System.out.println("6. Delete Account");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    addAccount(sc, dao);
                    break;
                case 2:
                    List<Account> all = dao.getAllAccounts();
                    if (all.isEmpty()) {
                        System.out.println("No accounts found.");
                    } else {
                        for (Account a : all) {
                            a.display();
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter Account Id: ");
                    Account one = dao.getAccountById(sc.nextLine());
                    if (one == null) {
                        System.out.println("Account not found.");
                    } else {
                        one.display();
                    }
                    break;
                case 4:
                    doPayment(sc, dao);
                    break;
                case 5:
                    doDeposit(sc, dao);
                    break;
                case 6:
                    System.out.print("Enter Account Id to delete: ");
                    boolean deleted = dao.deleteAccount(sc.nextLine());
                    System.out.println(deleted ? "Deleted." : "Delete failed.");
                    break;
                case 7:
                    System.out.println("Thank you!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addAccount(Scanner sc, AccountDAO dao) {
        System.out.print("Account Type (DEBIT/CREDIT): ");
        String type = sc.nextLine().trim().toUpperCase();

        System.out.print("Account Number: ");
        String num = sc.nextLine();
        System.out.print("Account Owner: ");
        String owner = sc.nextLine();
        System.out.print("Opening Balance: ");
        double bal = Double.parseDouble(sc.nextLine());

        Account account;
        if ("DEBIT".equals(type)) {
            System.out.print("Password: ");
            String pass = sc.nextLine();
            account = new DebitAccount(num, owner, bal, pass);
        } else if ("CREDIT".equals(type)) {
            System.out.print("Credit Limit: ");
            double limit = Double.parseDouble(sc.nextLine());
            account = new CreditAccount(num, owner, bal, 0, limit);
        } else {
            System.out.println("Unknown type.");
            return;
        }

        boolean ok = dao.addAccount(account, type);
        System.out.println(ok ? "Account added." : "Add failed.");
    }

    private static void doPayment(Scanner sc, AccountDAO dao) {
        System.out.print("Account Id: ");
        Account account = dao.getAccountById(sc.nextLine());
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        if (account instanceof DebitAccount) {
            System.out.print("Password: ");
            String pass = sc.nextLine();
            ((DebitAccount) account).performPayment(amount, pass);
        } else {
            account.performPayment(amount);
        }

        dao.updateAccount(account);
        account.display();
    }

    private static void doDeposit(Scanner sc, AccountDAO dao) {
        System.out.print("Account Id: ");
        Account account = dao.getAccountById(sc.nextLine());
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        if (account instanceof DebitAccount) {
            System.out.print("Password: ");
            String pass = sc.nextLine();
            ((DebitAccount) account).performDeposit(amount, pass);
        } else {
            account.performDeposit(amount);
        }

        dao.updateAccount(account);
        account.display();
    }
}
