# Spring Boot Code & Syntax Guide (Line-by-Line Explanation)

This document provides a line-by-line explanation of every line of code, annotation, keyword, and syntax used in the **`customer-backend`** Spring Boot Maven application.

---

## Table of Contents
1. [pom.xml (Maven Configuration)](#1-pomxml-maven-configuration)
2. [application.properties](#2-applicationproperties)
3. [CustomerBackendApplication.java (Main Class)](#3-customerbackendapplicationjava)
4. [Customer.java (JPA Entity Model)](#4-customerjava-jpa-entity)
5. [CustomerResponseDto.java (Data Transfer Object)](#5-customerresponsedtojava)
6. [UpdateCustomerRequestDto.java (Validation DTO)](#6-updatecustomerrequestdtojava)
7. [ApiResponse.java (Generic API Response Wrapper)](#7-apiresponsejava)
8. [CustomerNotFoundException.java (Custom Exception)](#8-customernotfoundexceptionjava)
9. [ErrorResponse.java (Structured Error DTO)](#9-errorresponsejava)
10. [GlobalExceptionHandler.java (@RestControllerAdvice)](#10-globalexceptionhandlerjava)
11. [CustomerRepository.java (Spring Data JPA)](#11-customerrepositoryjava)
12. [CustomerService.java (Interface)](#12-customerservicejava)
13. [CustomerServiceImpl.java (Service Implementation)](#13-customerserviceimpljava)
14. [CustomerController.java (REST Controller & ResponseEntity)](#14-customercontrollerjava)

---

## 1. `pom.xml` (Maven Configuration)

```xml
<?xml version="1.0" encoding="UTF-8"?>
```
- **Explanation**: XML declaration specifying XML version (`1.0`) and character encoding (`UTF-8`).

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" ...>
```
- `<project>`: Root element of every Maven `pom.xml` file.
- `xmlns`: Defines the XML namespace for Maven POM version 4.0.0.

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.5</version>
    <relativePath/>
</parent>
```
- `<parent>`: Inherits default configurations, dependency versions, and plugin setups from Spring Boot Starter Parent `3.2.5`.

```xml
<groupId>com.example</groupId>
<artifactId>customer-backend</artifactId>
<version>0.0.1-SNAPSHOT</version>
<name>customer-backend</name>
```
- `<groupId>`: Unique identifier for the organization/project domain (`com.example`).
- `<artifactId>`: Name of the project artifact / build output jar (`customer-backend`).
- `<version>`: Project version (`0.0.1-SNAPSHOT` indicates a development build).

```xml
<properties>
    <java.version>17</java.version>
</properties>
```
- `<properties>`: Sets Java target version to Java 17 for the compiler.

```xml
<dependencies>
```
- Contains all external libraries needed by the project.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
- **`spring-boot-starter-web`**: Includes Jackson (JSON parser), Embedded Tomcat Server, and Spring MVC framework for building REST APIs.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
- **`spring-boot-starter-data-jpa`**: Connects Spring to relational databases using Hibernate ORM (Object-Relational Mapping).

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```
- **`spring-boot-starter-validation`**: Enables Bean Validation annotations like `@NotBlank` and `@Email`.

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```
- **`h2`**: Lightweight in-memory database used for development without needing a separate database server. `runtime` scope means it's only included when running the app.

---

## 2. `application.properties`

```properties
server.port=8080
```
- Configures Tomcat to listen on HTTP port `8080`.

```properties
spring.datasource.url=jdbc:h2:mem:customerdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```
- Configures H2 database connection: in-memory database named `customerdb`, default driver `org.h2.Driver`, user `sa`, empty password.

```properties
spring.h2.console.enabled=true
```
- Enables the web-based H2 database console UI at `http://localhost:8080/h2-console`.

```properties
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
- `H2Dialect`: Tells Hibernate to generate SQL specific to H2.
- `ddl-auto=update`: Automatically creates or updates database table schemas based on Java JPA `@Entity` annotations.
- `show-sql=true`: Prints executed SQL queries in terminal logs for debugging.

---

## 3. `CustomerBackendApplication.java`

```java
package com.example.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
```
- `package`: Declares package namespace.
- `import`: Imports Spring Boot framework classes.

```java
@SpringBootApplication
public class CustomerBackendApplication {
```
- **`@SpringBootApplication`**: Convenience annotation combining:
  1. `@Configuration`: Marks class as a source of bean definitions.
  2. `@EnableAutoConfiguration`: Enables Spring Boot's automatic setup based on classpath dependencies.
  3. `@ComponentScan`: Tells Spring to scan package `com.example.customer` and sub-packages for `@RestController`, `@Service`, `@Repository`, and `@Component` annotations.

```java
    public static void main(String[] args) {
        SpringApplication.run(CustomerBackendApplication.class, args);
    }
}
```
- `public static void main`: Standard Java entry point.
- `SpringApplication.run()`: Boots up Spring container, initializes Tomcat server on port 8080, and loads application beans.

---

## 4. `Customer.java` (JPA Entity)

```java
package com.example.customer.model;

import jakarta.persistence.*;
```
- Imports Jakarta Persistence API annotations for JPA mapping.

```java
@Entity
@Table(name = "customers")
public class Customer {
```
- **`@Entity`**: Marks this class as a database table entity managed by JPA/Hibernate.
- **`@Table(name = "customers")`**: Maps this Java class to database table named `customers`.

```java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;
```
- **`@Id`**: Marks `customerId` as primary key.
- **`@GeneratedValue(strategy = GenerationType.IDENTITY)`**: Auto-increments ID using database column auto-increment.
- **`@Column(name = "customer_id")`**: Maps to table column `customer_id`.

```java
    @Column(name = "name", nullable = false, updatable = false)
    private String name;
```
- **`nullable = false`**: Name column cannot be NULL.
- **`updatable = false`**: **Crucial Rule!** Database-level enforcement that prevents `name` column from being modified during SQL `UPDATE` operations.

```java
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "location", nullable = false)
    private String location;
```
- Defines editable columns `email` and `location`.

```java
    public Customer() {} // Default no-arg constructor required by JPA
    public Customer(String name, String email, String location) { ... } // Parameterized constructors
    // Getters and Setters ...
```
- standard constructors, getters, setters, and `toString()` implementation.

---

## 5. `CustomerResponseDto.java`

```java
package com.example.customer.dto;

public class CustomerResponseDto {
    private Long customerId;
    private String name;
    private String email;
    private String location;
    // constructors, getters, setters
}
```
- **DTO (Data Transfer Object)**: Decouples the internal database entity (`Customer`) from the public API JSON response returned to the client.

---

## 6. `UpdateCustomerRequestDto.java`

```java
package com.example.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateCustomerRequestDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Location is required")
    private String location;
    // constructors, getters, setters
}
```
- **Syntax Purpose**: **Strict Enforcement of Business Rule!**
  - Notice that `customerId` and `name` are **completely absent** from this DTO.
  - Clients can ONLY send `email` and `location` in the JSON request body.
- **`@NotBlank`**: Validation rule ensuring string is neither null nor empty whitespace.
- **`@Email`**: Validation rule ensuring string matches valid email format (e.g. `user@domain.com`).

---

## 7. `ApiResponse.java`

```java
package com.example.customer.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
```
- **Generic Wrapper `<T>`**: Accepts any data type (`List<CustomerResponseDto>` or single `CustomerResponseDto`) to standardize all REST responses.
- `success`: Boolean flag (`true`/`false`).
- `timestamp`: Automatically initialized to `LocalDateTime.now()` when created.

---

## 8. `CustomerNotFoundException.java`

```java
package com.example.customer.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id) {
        super("Customer with ID " + id + " was not found");
    }
}
```
- **`extends RuntimeException`**: Custom unchecked exception thrown when a customer lookup by ID fails. Passes error message to superclass constructor.

---

## 9. `ErrorResponse.java`

```java
package com.example.customer.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> validationErrors;
```
- Standardized payload format sent to client when an exception or validation error occurs. Contains HTTP status code, error message, and a map of validation field errors.

---

## 10. `GlobalExceptionHandler.java`

```java
package com.example.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
```
- Imports Spring Exception handling classes.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
```
- **`@RestControllerAdvice`**: Global interceptor that intercepts exceptions thrown by any `@RestController` across the application and formats the output into JSON responses.

```java
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
```
- **`@ExceptionHandler(CustomerNotFoundException.class)`**: Triggers whenever `CustomerNotFoundException` is thrown.
- **`ResponseEntity<>(error, HttpStatus.NOT_FOUND)`**: Wraps `ErrorResponse` payload with HTTP Status code **`404 NOT FOUND`**.

```java
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Input validation failed",
                errors
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
```
- **`MethodArgumentNotValidException`**: Automatically thrown when DTO `@Valid` validation fails. Extracts field errors (e.g. `email`: `"Please provide a valid email address"`) and returns HTTP Status **`400 BAD REQUEST`**.

---

## 11. `CustomerRepository.java`

```java
package com.example.customer.repository;

import com.example.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
```
- **`@Repository`**: Indicates Spring Data repository component.
- **`extends JpaRepository<Customer, Long>`**: Provides pre-built database CRUD methods (`findAll()`, `findById()`, `save()`, `deleteById()`) out of the box without writing any SQL queries manually!

---

## 12. `CustomerService.java`

```java
package com.example.customer.service;

import com.example.customer.dto.CustomerResponseDto;
import com.example.customer.dto.UpdateCustomerRequestDto;
import java.util.List;

public interface CustomerService {
    List<CustomerResponseDto> getAllCustomers();
    CustomerResponseDto getCustomerById(Long customerId);
    CustomerResponseDto updateCustomer(Long customerId, UpdateCustomerRequestDto updateDto);
}
```
- Defines service abstraction contract to ensure loose coupling between Controller and Service implementation.

---

## 13. `CustomerServiceImpl.java`

```java
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
```
- **`@Service`**: Registers class as Spring service bean.
- **Constructor Injection**: Spring automatically injects `CustomerRepository` instance into constructor (Best Practice).

```java
    @PostConstruct
    public void initDatabase() { ... }
```
- **`@PostConstruct`**: Runs automatically after bean initialization to insert seed customers into H2 in-memory DB.

```java
    @Override
    @Transactional
    public CustomerResponseDto updateCustomer(Long customerId, UpdateCustomerRequestDto updateDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        // Strict Enforcement: ONLY email and location can be updated!
        customer.setEmail(updateDto.getEmail());
        customer.setLocation(updateDto.getLocation());

        Customer updatedCustomer = customerRepository.save(customer);
        return convertToResponseDto(updatedCustomer);
    }
```
- **`@Transactional`**: Manages database transaction boundary.
- **`.orElseThrow(...)`**: If customer ID doesn't exist, throws `CustomerNotFoundException`.
- **Updating Fields**: Only `email` and `location` setters are called. `customerId` and `name` remain untouched.

---

## 14. `CustomerController.java`

```java
package com.example.customer.controller;

import ...;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
```
- **`@RestController`**: Marks class as RESTful controller where return values are serialized directly into JSON HTTP responses.
- **`@RequestMapping("/api/customers")`**: Base URL route for all endpoints in this controller.
- **`@CrossOrigin(origins = "*")`**: Enables Cross-Origin Resource Sharing (CORS) so React frontend at `http://localhost:3000` can communicate with backend at `http://localhost:8080`.

```java
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> getAllCustomers() {
        List<CustomerResponseDto> customers = customerService.getAllCustomers();
        ApiResponse<List<CustomerResponseDto>> response = new ApiResponse<>(
                true,
                "Customers retrieved successfully",
                customers
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
```
- **`@GetMapping`**: Handles `GET /api/customers` requests.
- **`ResponseEntity<T>`**: Wraps response payload AND explicit HTTP status code **`HttpStatus.OK`** (HTTP 200).

```java
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> updateCustomer(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateCustomerRequestDto updateDto) {

        CustomerResponseDto updatedCustomer = customerService.updateCustomer(id, updateDto);
        ApiResponse<CustomerResponseDto> response = new ApiResponse<>(
                true,
                "Customer email and location updated successfully",
                updatedCustomer
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
```
- **`@PutMapping("/{id}")`**: Handles `PUT /api/customers/{id}` update requests.
- **`@PathVariable("id") Long id`**: Extracts ID from URL path (e.g. `/api/customers/1`).
- **`@Valid`**: Triggers Spring validation rules on `UpdateCustomerRequestDto` (`@NotBlank`, `@Email`). If invalid, throws `MethodArgumentNotValidException` which is caught by `@RestControllerAdvice`.
- **`@RequestBody`**: Binds incoming JSON payload to Java object `updateDto`.
- **`ResponseEntity<...>`**: Returns updated customer object wrapped in `ApiResponse` with `HttpStatus.OK` (200).
