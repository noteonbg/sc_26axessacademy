package functionalprogramming;

import java.util.ArrayList;
import java.util.List;

//Suresh f3 is main
public class Main {
    public static void main(String[] args) {

        //ArrayList class is writtne by Rames and its function sort is written Ramesh

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", 70000));
        employees.add(new Employee("Bob", 50000));
        employees.add(new Employee("Charlie", 60000));

        // 1. Sort by Salary (Ascending)
        employees.sort((e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()));

        //sort function is RAmesh function, Suresh is calling it
        // in its input you will see suresh has done three things.
        //1. defined a functioon
        //2  created an object
        //3 passed it

        //employees.sort(fate function written by suresh;

        //

        // 2. Sort by Name (Alphabetical)
        employees.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
    }
}