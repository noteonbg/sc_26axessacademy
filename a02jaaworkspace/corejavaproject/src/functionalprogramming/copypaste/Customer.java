package functionalprogramming.copypaste;

import java.util.*;

// Customer class
class Customer implements Comparable<Customer> {
    private int customerId;
    private String name;
    private String email;
    private String location;

    public Customer(int customerId, String name, String email, String location) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.location = location;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    // Default sorting by name
    @Override
    public int compareTo(Customer other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}