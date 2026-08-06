# High-Value Transaction Processing - Complete Student Practice Guide

Welcome to the **High-Value Transaction Processing** practice exercise! This lab is designed to help you practice Java Lambda expressions, Streams, and Method References 
using a financial domain model.

---

## 1. The Domain Model (`Transaction.java`)

Here is the domain model you will be working with. Study the fields and getter methods available.

```java
import java.util.Objects;

public class Transaction {
    private String transactionId;
    private String type; // "BUY", "SELL", "TRANSFER"
    private double amount;
    private String status; // "PENDING", "APPROVED", "REJECTED"

    public Transaction(String transactionId, String type, double amount, String status) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.status = status;
    }

    public String getTransactionId() { return transactionId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("TxID: %s | Type: %s | Amount: $%.2f | Status: %s", 
                transactionId, type, amount, status);
    }
}
```

---

## 2. Your Tasks

Implement the missing logic inside the `TransactionProcessor` class below. You will use **Streams**, **Lambda expressions**, and **Method References** to complete each requirement.

### Task 1: Filter & Collect (Lambda Practice)
Filter all transactions that have the status `"APPROVED"` **and** an amount greater than `$1,000.00`. Collect them into a `List<Transaction>`.

### Task 2: Transform (Method Reference Practice)
Extract a list containing only the `transactionId` strings (`List<String>`) from all transactions using a method reference (`Transaction::getTransactionId`).

### Task 3: Aggregate (Stream Terminal Operations)
Calculate the total monetary value (`double`) of all `"BUY"` transactions using primitive streams (`mapToDouble` and `sum`).

### Task 4: Sort (Comparator Practice)
Sort a copy of the original list of transactions by `amount` in **descending order** using `Comparator.comparing` and a method reference (`Transaction::getAmount`).

---

## 3. Starter Code (`TransactionProcessor.java`)

Copy and paste this template into your IDE and fill in the missing code where indicated by `// TODO`.

import java.util.*;
import java.util.stream.Collectors;

public class TransactionProcessor {

    public static void main(String[] args) {
        // Sample dataset
        List<Transaction> transactions = Arrays.asList(
            new Transaction("TX101", "BUY", 1500.50, "APPROVED"),
            new Transaction("TX102", "SELL", 450.00, "PENDING"),
            new Transaction("TX103", "BUY", 12000.00, "APPROVED"),
            new Transaction("TX104", "TRANSFER", 2500.00, "REJECTED"),
            new Transaction("TX105", "BUY", 800.00, "APPROVED"),
            new Transaction("TX106", "SELL", 5300.00, "APPROVED")
        );

        System.out.println("=== ALL TRANSACTIONS ===");
        transactions.forEach(System.out::println);

        // ==========================================
        // TASK 1: Filter & Collect
        // ==========================================
        // TODO: Filter transactions where status == "APPROVED" and amount > 1000.0
        List<Transaction> highValueApproved = null; // Replace with stream code
        
        System.out.println("\n=== TASK 1: High Value Approved Transactions ===");
        if (highValueApproved != null) {
            highValueApproved.forEach(System.out::println);
        }

        // ==========================================
        // TASK 2: Transform (Method Reference)
        // ==========================================
        // TODO: Extract all transaction IDs into a List<String> using Transaction::getTransactionId
        List<String> transactionIds = null; // Replace with stream code

        System.out.println("\n=== TASK 2: Transaction IDs ===");
        System.out.println(transactionIds);

        // ==========================================
        // TASK 3: Aggregate (Numeric Stream Sum)
        // ==========================================
        // TODO: Calculate the total sum of amounts for all "BUY" transactions
        double totalBuyAmount = 0.0; // Replace with stream code

        System.out.println("\n=== TASK 3: Total Buy Amount ===");
        System.out.printf("$%.2f\n", totalBuyAmount);

        // ==========================================
        // TASK 4: Sort (Comparator & Method Reference)
        // ==========================================
        // TODO: Create a mutable sorted copy of transactions by amount descending
        List<Transaction> sortedByAmountDesc = null; // Replace with sorting code

        System.out.println("\n=== TASK 4: Sorted by Amount (Descending) ===");
        if (sortedByAmountDesc != null) {
            sortedByAmountDesc.forEach(System.out::println);
        }
    }
}

---

## 4. Solutions (Try and prove you understood this theory )


### Task 1 Solution:
List<Transaction> highValueApproved = transactions.stream()
    .filter(tx -> "APPROVED".equals(tx.getStatus()) && tx.getAmount() > 1000.0)
    .collect(Collectors.toList());


### Task 2 Solution:
List<String> transactionIds = transactions.stream()
    .map(Transaction::getTransactionId)
    .collect(Collectors.toList());

### Task 3 Solution:
double totalBuyAmount = transactions.stream()
    .filter(tx -> "BUY".equals(tx.getType()))
    .mapToDouble(Transaction::getAmount)
    .sum();

### Task 4 Solution:
List<Transaction> sortedByAmountDesc = new ArrayList<>(transactions);
sortedByAmountDesc.sort(Comparator.comparing(Transaction::getAmount).reversed());

