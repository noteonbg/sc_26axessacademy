package functionalprogramming.copypaste;

import java.util.*;

public class CustomerDemo {

    public static void main(String[] args) {

        List<Customer> customers = new ArrayList<>();

        customers.add(new Customer(101, "John", "john@gmail.com", "New York"));
        customers.add(new Customer(102, "Alice", "alice@gmail.com", "London"));
        customers.add(new Customer(103, "Bob", "bob@gmail.com", "New York"));
        customers.add(new Customer(104, "David", "david@gmail.com", "Paris"));
        customers.add(new Customer(105, "Carol", "carol@gmail.com", "London"));

        // Sort by name
        Collections.sort(customers);
        System.out.println("Sorted by Name:");
        customers.forEach(System.out::println);

        // Sort by email
        Collections.sort(customers, new EmailComparator());
        System.out.println("\nSorted by Email:");
        customers.forEach(System.out::println);

        // Group customers by location
        Map<String, List<Customer>> locationGroups = new TreeMap<>();

        for (Customer c : customers) {
            locationGroups
                    .computeIfAbsent(c.getLocation(), k -> new ArrayList<>())
                    .add(c);
        }

        System.out.println("\nCustomers Grouped by Location:");
        for (Map.Entry<String, List<Customer>> entry : locationGroups.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (Customer c : entry.getValue()) {
                System.out.println("   " + c);
            }
        }
    }
}