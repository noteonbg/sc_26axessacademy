package functionalprogramming.copypaste;

import java.util.*;


//Suresh Code
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
       // customers.forEach(System.out::println);  // Method REference

        // Sort by email
        customers.sort(Comparator.comparing(Customer::getEmail));
        System.out.println("\nSorted by Email:");
       // customers.forEach(System.out::println);

        customers.stream()
                .map(Customer::getLocation)
                .distinct()
                .forEach(location -> {
                    System.out.println(location + ":");
                    customers.stream()
                            .filter(c -> c.getLocation().equals(location))
                            .forEach(System.out::println);
                });
    }
}