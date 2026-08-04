# Step-by-Step Guide: Building a Layered Customer Management Application in IntelliJ IDEA

This tutorial is written specifically for beginners who only know how to write a basic "Hello World" in Java. By following these numbered steps, you will build a professional **Customer Management Application** from scratch using **IntelliJ IDEA**.

---

## Table of Contents
1. [What We Are Building](#1-what-we-are-building)
2. [Step 1: Setting Up a New Java Project in IntelliJ IDEA](#step-1-setting-up-a-new-java-project-in-intellij-idea)
3. [Step 2: Creating the Package Structure](#step-2-creating-the-package-structure)
4. [Step 3: Creating Layer 1 — The Model (`Customer.java`)](#step-3-creating-layer-1--the-model-customerjava)
5. [Step 4: Creating Custom Exception Classes](#step-4-creating-custom-exception-classes)
6. [Step 5: Creating Layer 2 — The Repository Interface (`CustomerRepository.java`)](#step-5-creating-layer-2--the-repository-interface-customerrepositoryjava)
7. [Step 6: Creating Layer 3 — Repository Implementations (`ArrayList` & `HashMap`)](#step-6-creating-layer-3--repository-implementations-arraylist--hashmap)
8. [Step 7: Creating Layer 4 — The Service Layer (`CustomerService.java`)](#step-7-creating-layer-4--the-service-layer-customerservicejava)
9. [Step 8: Creating Layer 5 — The Main Application (`MainApp.java`)](#step-8-creating-layer-5--the-main-application-mainappjava)
10. [Step 9: Running the Application in IntelliJ IDEA](#step-9-running-the-application-in-intellij-idea)
11. [Step 10: Beginner Java Syntax & Keyword Cheat Sheet](#step-10-beginner-java-syntax--keyword-cheat-sheet)

---

## 1. What We Are Building

We will build an application to manage **Customers** with 4 fields:
- `customerId` (Integer, e.g. `101`) — **Fixed Identifier** (cannot be updated after creation).
- `name` (String, e.g. `"John Doe"`) — **Updateable**.
- `email` (String, e.g. `"john@example.com"`) — **Updateable**.
- `location` (String, e.g. `"Chicago"`) — **Updateable**.

### The 4-Layer Architecture
We will separate our code into 4 clean layers:
1. **Model**: Defines what a `Customer` looks like.
2. **Repository**: Handles storing, reading, updating, and deleting customer data (first with `ArrayList`, then with `HashMap`).
3. **Service**: Contains business rules (e.g., preventing duplicate IDs, making sure only `name`, `email`, and `location` can be updated).
4. **App**: Runs the application in IntelliJ.

---

## Step 1: Setting Up a New Java Project in IntelliJ IDEA

1. Open **IntelliJ IDEA**.
2. Click **New Project** (or go to **File ➔ New ➔ Project**).
3. Fill in the project details:
   - **Name**: `CustomerManagementSystem`
   - **Language**: `Java`
   - **Build system**: `IntelliJ` (or `Maven`)
   - **JDK**: Select Java 17 or Java 21 (any JDK version 11 or higher works fine).
4. Click **Create**.
5. Look at the **Project Tool Window** on the left side of IntelliJ. You will see a folder named `src`. This is where all your Java source code files will live.

---

## Step 2: Creating the Package Structure

Packages are like folders inside Java that keep your code organized. We need 6 packages.

1. On the left panel in IntelliJ, **Right-click** on the `src` folder.
2. Select **New ➔ Package**.
3. Type: `com.example.customer.model` and press **Enter**.
4. Repeat this step to create all 6 packages:
   - `com.example.customer.model`
   - `com.example.customer.exception`
   - `com.example.customer.repository`
   - `com.example.customer.repository.impl`
   - `com.example.customer.service`
   - `com.example.customer.app`

---

## Step 3: Creating Layer 1 — The Model (`Customer.java`)

1. Right-click on package `com.example.customer.model`.
2. Select **New ➔ Java Class**.
3. Name it: `Customer` and press **Enter**.
4. Paste the following complete code into `Customer.java`:

```java
package com.example.customer.model;

import java.util.Objects;

/**
 * Model class representing a Customer.
 * customerId is immutable (cannot be changed after creation).
 * name, email, and location can be updated.
 */
public class Customer {

    // Member variables (attributes of a Customer)
    private final int customerId; // 'final' means this value cannot be changed once set
    private String name;
    private String email;
    private String location;

    // Constructor: Used to create a new Customer object
    public Customer(int customerId, String name, String email, String location) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.location = location;
    }

    // Getter for customerId (Notice: NO setter is provided, keeping customerId immutable)
    public int getCustomerId() {
        return customerId;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Compares two Customer objects by customerId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId == customer.customerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    // Converts Customer object to a human-readable text string for printing
    @Override
    public String toString() {
        return String.format("Customer [customerId=%d, name='%s', email='%s', location='%s']",
                customerId, name, email, location);
    }
}
```

---

## Step 4: Creating Custom Exception Classes

When an error happens (e.g. searching for a customer that doesn't exist), Java throws an Exception. We will create two custom exceptions.

### 4.1 `CustomerNotFoundException.java`
1. Right-click on package `com.example.customer.exception`.
2. Select **New ➔ Java Class**, name it `CustomerNotFoundException`.
3. Paste the following code:

```java
package com.example.customer.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
```

### 4.2 `DuplicateCustomerException.java`
1. Right-click on package `com.example.customer.exception`.
2. Select **New ➔ Java Class**, name it `DuplicateCustomerException`.
3. Paste the following code:

```java
package com.example.customer.exception;

public class DuplicateCustomerException extends RuntimeException {
    public DuplicateCustomerException(String message) {
        super(message);
    }
}
```

---

## Step 5: Creating Layer 2 — The Repository Interface (`CustomerRepository.java`)

An `interface` is a blueprint of methods that storage classes must implement.

1. Right-click on package `com.example.customer.repository`.
2. Select **New ➔ Java Class**.
3. Select **Interface** from the dropdown menu in IntelliJ, name it `CustomerRepository`.
4. Paste the following code:

```java
package com.example.customer.repository;

import com.example.customer.model.Customer;
import java.util.List;
import java.util.Optional;

/**
 * Storage contract interface for Customer data.
 */
public interface CustomerRepository {

    // Adds a customer to storage
    void save(Customer customer);

    // Finds a customer by ID (returns Optional in case customer is not found)
    Optional<Customer> findById(int customerId);

    // Retrieves all customers currently stored
    List<Customer> findAll();

    // Updates name, email, and location of an existing customer by ID
    boolean update(int customerId, String newName, String newEmail, String newLocation);

    // Deletes a customer by ID
    boolean deleteById(int customerId);

    // Checks if a customer ID already exists
    boolean existsById(int customerId);
}
```

---

## Step 6: Creating Layer 3 — Repository Implementations (`ArrayList` & `HashMap`)

Now we implement the interface using two different data structures.

### 6.1 `CustomerArrayListRepository.java`
1. Right-click package `com.example.customer.repository.impl`.
2. Select **New ➔ Java Class**, name it `CustomerArrayListRepository`.
3. Paste the following code:

```java
package com.example.customer.repository.impl;

import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Storage implementation using an ArrayList.
 */
public class CustomerArrayListRepository implements CustomerRepository {

    // Internal list to store customers in memory
    private final List<Customer> customerList = new ArrayList<>();

    @Override
    public void save(Customer customer) {
        customerList.add(customer);
    }

    @Override
    public Optional<Customer> findById(int customerId) {
        return customerList.stream()
                .filter(c -> c.getCustomerId() == customerId)
                .findFirst();
    }

    @Override
    public List<Customer> findAll() {
        // Returns a copy of the list
        return new ArrayList<>(customerList);
    }

    @Override
    public boolean update(int customerId, String newName, String newEmail, String newLocation) {
        Optional<Customer> customerOpt = findById(customerId);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            // ONLY update name, email, and location. customerId remains untouched!
            customer.setName(newName);
            customer.setEmail(newEmail);
            customer.setLocation(newLocation);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int customerId) {
        return customerList.removeIf(c -> c.getCustomerId() == customerId);
    }

    @Override
    public boolean existsById(int customerId) {
        return customerList.stream().anyMatch(c -> c.getCustomerId() == customerId);
    }
}
```

### 6.2 `CustomerHashMapRepository.java`
1. Right-click package `com.example.customer.repository.impl`.
2. Select **New ➔ Java Class**, name it `CustomerHashMapRepository`.
3. Paste the following code:

```java
package com.example.customer.repository.impl;

import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Storage implementation using a HashMap (Key: customerId, Value: Customer).
 */
public class CustomerHashMapRepository implements CustomerRepository {

    // Internal HashMap to store customers (Key = customerId)
    private final Map<Integer, Customer> customerMap = new HashMap<>();

    @Override
    public void save(Customer customer) {
        customerMap.put(customer.getCustomerId(), customer);
    }

    @Override
    public Optional<Customer> findById(int customerId) {
        return Optional.ofNullable(customerMap.get(customerId));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public boolean update(int customerId, String newName, String newEmail, String newLocation) {
        Customer customer = customerMap.get(customerId);
        if (customer != null) {
            // ONLY update name, email, and location
            customer.setName(newName);
            customer.setEmail(newEmail);
            customer.setLocation(newLocation);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int customerId) {
        return customerMap.remove(customerId) != null;
    }

    @Override
    public boolean existsById(int customerId) {
        return customerMap.containsKey(customerId);
    }
}
```

---

## Step 7: Creating Layer 4 — The Service Layer (`CustomerService.java`)

This layer handles business logic. It uses **Dependency Injection** so it can work with either `ArrayList` or `HashMap` repositories!

1. Right-click package `com.example.customer.service`.
2. Select **New ➔ Java Class**, name it `CustomerService`.
3. Paste the following code:

```java
package com.example.customer.service;

import com.example.customer.exception.DuplicateCustomerException;
import com.example.customer.exception.CustomerNotFoundException;
import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;

import java.util.List;

/**
 * Service Layer enforcing business rules.
 */
public class CustomerService {

    // Reference to the repository interface
    private final CustomerRepository repository;

    // Constructor Injection: Accepts ANY CustomerRepository (ArrayList or HashMap)
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Business Rule 1: Check for duplicate customerId before adding
    public void addCustomer(int customerId, String name, String email, String location) {
        if (repository.existsById(customerId)) {
            throw new DuplicateCustomerException("Customer ID " + customerId + " already exists!");
        }
        Customer customer = new Customer(customerId, name, email, location);
        repository.save(customer);
    }

    // Business Rule 2: Get all customers
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    // Business Rule 3: Find by ID or throw error
    public Customer getCustomerById(int customerId) {
        return repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer ID " + customerId + " not found!"));
    }

    // Business Rule 4: Update customer (only name, email, location)
    public void updateCustomer(int customerId, String newName, String newEmail, String newLocation) {
        boolean success = repository.update(customerId, newName, newEmail, newLocation);
        if (!success) {
            throw new CustomerNotFoundException("Cannot update. Customer ID " + customerId + " does not exist!");
        }
    }

    // Business Rule 5: Delete customer
    public void deleteCustomer(int customerId) {
        boolean success = repository.deleteById(customerId);
        if (!success) {
            throw new CustomerNotFoundException("Cannot delete. Customer ID " + customerId + " does not exist!");
        }
    }
}
```

---

## Step 8: Creating Layer 5 — The Main Application (`MainApp.java`)

This is where the program executes.

1. Right-click package `com.example.customer.app`.
2. Select **New ➔ Java Class**, name it `MainApp`.
3. Paste the following code:

```java
package com.example.customer.app;

import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.repository.impl.CustomerArrayListRepository;
import com.example.customer.repository.impl.CustomerHashMapRepository;
import com.example.customer.service.CustomerService;

import java.util.List;

public class MainApp {

    // Main entry point for Java execution
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   CUSTOMER MANAGEMENT SYSTEM DEMO");
        System.out.println("=================================================\n");

        // ---------------------------------------------------------------------
        // PART 1: USING ARRAYLIST STORAGE
        // ---------------------------------------------------------------------
        System.out.println(">>> 1. RUNNING WITH ARRAYLIST STORAGE <<<\n");
        CustomerRepository arrayListRepo = new CustomerArrayListRepository();
        CustomerService arrayListService = new CustomerService(arrayListRepo);

        runDemo(arrayListService, "ArrayList");

        // ---------------------------------------------------------------------
        // PART 2: SWITCHING STORAGE TO HASHMAP
        // ---------------------------------------------------------------------
        System.out.println("\n>>> 2. SWITCHING STORAGE TO HASHMAP <<<\n");
        CustomerRepository hashMapRepo = new CustomerHashMapRepository();
        CustomerService hashMapService = new CustomerService(hashMapRepo);

        runDemo(hashMapService, "HashMap");
    }

    private static void runDemo(CustomerService service, String storageName) {
        // 1. CREATE / INSERT
        System.out.println("[" + storageName + "] Adding Customers...");
        service.addCustomer(501, "Emma Watson", "emma@example.com", "London");
        service.addCustomer(502, "Liam Neeson", "liam@example.com", "Belfast");
        service.addCustomer(503, "Sophia Loren", "sophia@example.com", "Rome");
        System.out.println("Successfully added 3 customers.");

        // 2. VIEW ALL
        System.out.println("\n[" + storageName + "] Viewing All Customers:");
        printList(service.getAllCustomers());

        // 3. UPDATE (name, email, location ONLY)
        System.out.println("\n[" + storageName + "] Updating Customer 502 (name, email, location)...");
        System.out.println("Before: " + service.getCustomerById(502));
        service.updateCustomer(502, "Liam Neeson OBE", "liam.neeson@hollywood.com", "Dublin");
        System.out.println("After:  " + service.getCustomerById(502));

        // 4. DELETE
        System.out.println("\n[" + storageName + "] Deleting Customer 501...");
        service.deleteCustomer(501);
        System.out.println("Customer 501 deleted.");

        // 5. VIEW ALL AFTER DELETE
        System.out.println("\n[" + storageName + "] Viewing All Customers After Deletion:");
        printList(service.getAllCustomers());
    }

    private static void printList(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("  No customers found.");
        } else {
            customers.forEach(c -> System.out.println("  -> " + c));
        }
    }
}
```

---

## Step 9: Running the Application in IntelliJ IDEA

1. Open `MainApp.java` in IntelliJ IDEA.
2. Look at line 12: `public static void main(String[] args)`.
3. You will see a **Green Play Arrow (▶)** next to the line number or near the class name.
4. Click the **Green Play Arrow (▶)** and select **Run 'MainApp.main()'**.
5. Look at the **Run Window** at the bottom of IntelliJ IDEA. You should see the complete output:

```text
=================================================
   CUSTOMER MANAGEMENT SYSTEM DEMO
=================================================

>>> 1. RUNNING WITH ARRAYLIST STORAGE <<<

[ArrayList] Adding Customers...
Successfully added 3 customers.

[ArrayList] Viewing All Customers:
  -> Customer [customerId=501, name='Emma Watson', email='emma@example.com', location='London']
  -> Customer [customerId=502, name='Liam Neeson', email='liam@example.com', location='Belfast']
  -> Customer [customerId=503, name='Sophia Loren', email='sophia@example.com', location='Rome']

[ArrayList] Updating Customer 502 (name, email, location)...
Before: Customer [customerId=502, name='Liam Neeson', email='liam@example.com', location='Belfast']
After:  Customer [customerId=502, name='Liam Neeson OBE', email='liam.neeson@hollywood.com', location='Dublin']

[ArrayList] Deleting Customer 501...
Customer 501 deleted.

[ArrayList] Viewing All Customers After Deletion:
  -> Customer [customerId=502, name='Liam Neeson OBE', email='liam.neeson@hollywood.com', location='Dublin']
  -> Customer [customerId=503, name='Sophia Loren', email='sophia@example.com', location='Rome']

>>> 2. SWITCHING STORAGE TO HASHMAP <<<

... (Identical execution output using HashMap storage!)
```

---

## Step 10: Beginner Java Syntax & Keyword Cheat Sheet

Here is a simple explanation of every Java keyword used in this project:

| Keyword | Simple Beginner Explanation |
| :--- | :--- |
| `package` | Folder location of your file. Keeps code organized. |
| `import` | Brings in existing Java utility tools (like `ArrayList` or `List`). |
| `public` | Anyone can access this class or method. |
| `private` | Only code inside this specific class can read or change this variable. Protects data. |
| `class` | The blueprint used to create objects. |
| `interface` | A contract listing methods that implementing classes must promise to build. |
| `implements` | Tells Java that a class promises to fulfill an `interface` contract. |
| `extends` | Tells Java that a class inherits from a parent class (e.g. `extends RuntimeException`). |
| `final` | Makes a variable unchangeable once set. Used on `customerId` so it cannot be mutated! |
| `static` | Belongs to the class itself, not an individual instance. Used on `main()`. |
| `this` | Refers to the current object instance being used. |
| `new` | Creates a new instance of an object in memory (e.g. `new Customer(...)`). |
| `void` | Means the function does not return any value back. |
| `return` | Sends a value back from a function. |
| `List` / `ArrayList` | A dynamic resizable array that stores items sequentially in order. |
| `Map` / `HashMap` | A dictionary-like structure that stores Key-Value pairs (`Key = customerId`, `Value = Customer`). |
