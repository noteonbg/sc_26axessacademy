package copypaste;

public class BankingUtils {

    private static final double USD_TO_EUR_RATE = 0.95;
    private static final double EUR_TO_USD_RATE = 1.05;

    // Prevent instantiation
    private BankingUtils() {}

    // Validate a bank account number (e.g., length and numeric check)
    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && accountNumber.matches("\\d{10,16}");
    }

    // Calculate simple interest
    public static double calculateSimpleInterest(double principal, double rate, int timeYears) {
        return (principal * rate * timeYears) / 100.0;
    }

    // Calculate compound interest
    public static double calculateCompoundInterest(double principal, double rate, int timeYears, int nPerYear) {
        return principal * Math.pow((1 + (rate / (nPerYear * 100.0))), nPerYear * timeYears) - principal;
    }

    // Convert USD to EUR
    public static double convertUsdToEur(double amountInUsd) {
        return amountInUsd * USD_TO_EUR_RATE;
    }

    // Convert EUR to USD
    public static double convertEurToUsd(double amountInEur) {
        return amountInEur * EUR_TO_USD_RATE;
    }

    // Generate a masked account number (e.g., show only last 4 digits)
    public static String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) return "****";
        int length = accountNumber.length();
        return "*".repeat(length - 4) + accountNumber.substring(length - 4);
    }

    // Validate IFSC Code (for Indian banks as an example)
    public static boolean isValidIFSC(String ifsc) {
        return ifsc != null && ifsc.matches("^[A-Z]{4}0[A-Z0-9]{6}$");
    }

    // Format currency to 2 decimal places
    public static String formatCurrency(double amount) {
        return String.format("%.2f", amount);
    }

    // Calculate EMI (Equated Monthly Installment)
    public static double calculateEMI(double principal, double annualInterestRate, int tenureMonths) {
        double monthlyRate = annualInterestRate / (12 * 100);
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
                (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }

    // Check if withdrawal is allowed based on balance and minimum balance
    public static boolean canWithdraw(double balance, double amount, double minBalance) {
        return (balance - amount) >= minBalance;
    }
}

