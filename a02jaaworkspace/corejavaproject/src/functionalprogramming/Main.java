package functionalprogramming;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", 70000));
        employees.add(new Employee("Bob", 50000));
        employees.add(new Employee("Charlie", 60000));

        // 1. Sort by Salary (Ascending)
        employees.sort((e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()));

        // 2. Sort by Name (Alphabetical)
        employees.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
    }
}