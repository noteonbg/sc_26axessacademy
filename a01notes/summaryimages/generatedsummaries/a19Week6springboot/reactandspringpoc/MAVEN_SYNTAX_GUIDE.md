# Comprehensive Maven Syntax & Commands Guide

This document provides a detailed, line-by-line explanation of all **Maven XML syntax** in `pom.xml` and all **Maven CLI commands** used in building and running Spring Boot applications (including `mvn spring-boot:run`).

---

## Table of Contents
1. [What is Maven?](#1-what-is-maven)
2. [Line-by-Line Explanation of pom.xml Syntax](#2-line-by-line-explanation-of-pomxml-syntax)
3. [Maven Dependency Scopes Explained](#3-maven-dependency-scopes-explained)
4. [Maven Build Lifecycle Phases](#4-maven-build-lifecycle-phases)
5. [Explanation of Maven CLI Commands](#5-explanation-of-maven-cli-commands)
   - [`mvn spring-boot:run`](#mvn-spring-bootrun)
   - [`mvn clean`](#mvn-clean)
   - [`mvn compile`](#mvn-compile)
   - [`mvn test-compile`](#mvn-test-compile)
   - [`mvn test`](#mvn-test)
   - [`mvn package`](#mvn-package)
   - [`mvn clean package`](#mvn-clean-package)

---

## 1. What is Maven?

**Apache Maven** is a software project management and build automation tool used primarily for Java projects. It handles:
1. **Dependency Management**: Automatically downloads required JAR files (Spring Boot, PostgreSQL, Validation) from Maven Central Repository (`https://repo.maven.apache.org/maven2`).
2. **Standard Folder Layout**: Organizes code in `src/main/java`, resources in `src/main/resources`, and tests in `src/test/java`.
3. **Build Automation**: Compiles Java code, runs unit tests, packages code into executable JAR files, and runs the application.

---

## 2. Line-by-Line Explanation of `pom.xml` Syntax

Here is the exact `pom.xml` structure used in our Spring Boot applications, with every line explained:

```xml
<?xml version="1.0" encoding="UTF-8"?>
```
- **Explanation**: XML declaration tag. Specifies that this is an XML document using version `1.0` and character encoding `UTF-8`.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
```
- **`<project>`**: The root XML element of every Maven `pom.xml` file.
- **`xmlns`**: Specifies the XML namespace for Maven POM version 4.0.0.
- **`xmlns:xsi` & `xsi:schemaLocation`**: Defines the location of the Maven XML schema definition (XSD) file used to validate the syntax of `pom.xml`.

```xml
    <modelVersion>4.0.0</modelVersion>
```
- **`<modelVersion>`**: Declares the version of the POM model structure. Must be `4.0.0` for Maven 2 and Maven 3.

```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.5</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
```
- **`<parent>`**: Inherits default configuration settings from Spring Boot's official parent project.
- **`spring-boot-starter-parent`**: Provides pre-configured dependency version management (dependency management BOM), compiler plugin configurations, resource filtering, and plugin setups.
- **`version 3.2.5`**: Ensures all Spring Boot libraries (Web, JPA, Validation) use compatible versions without manual version specification.
- **`<relativePath/>`**: Tells Maven to fetch the parent POM from the remote Maven Central repository rather than a local relative path.

```xml
    <groupId>com.example</groupId>
    <artifactId>rectangle-backend</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>rectangle-backend</name>
    <description>Rectangle PA Calculation Spring Boot Backend API</description>
```
- **`<groupId>`**: The unique domain/organization identifier (`com.example`).
- **`<artifactId>`**: The unique name of the project module (`rectangle-backend`).
- **`<version>`**: The current project version (`0.0.1-SNAPSHOT`).
- **`<name>` & `<description>`**: Human-readable name and short description.

```xml
    <properties>
        <java.version>17</java.version>
    </properties>
```
- **`<properties>`**: Defines configuration key-value properties.
- **`<java.version>17</java.version>`**: Instructs the Maven Compiler Plugin to compile Java source code targeting JDK 17 (or 21).

```xml
    <dependencies>
```
- **`<dependencies>`**: Container tag listing all external libraries (JAR files) required by this project.

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```
- **`spring-boot-starter-web`**: Starter for building web applications and RESTful APIs using Spring MVC. Automatically imports embedded Tomcat Web Server and Jackson JSON serializer.

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
```
- **`spring-boot-starter-validation`**: Imports Hibernate Validator and Jakarta Bean Validation annotations (`@NotNull`, `@Min`).

```xml
    </dependencies>
```
- Closes the list of project dependencies.

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```
- **`<build>`**: Encloses build configuration and plugin definitions.
- **`spring-boot-maven-plugin`**: Essential Spring Boot Maven plugin that:
  1. Packages the project into a runnable **fat/uber executable JAR** containing all dependencies and embedded Tomcat server.
  2. Enables the `mvn spring-boot:run` command.

---

## 3. Maven Dependency Scopes Explained

In `<dependency>`, the `<scope>` tag defines when a dependency is included:
- **`compile` (default)**: Required for compiling, testing, and running the app.
- **`runtime`**: Not required for compiling source code, but required during application execution (e.g. database JDBC drivers like `postgresql` or `h2`).
- **`provided`**: Required for compilation, but provided by application container at runtime.
- **`test`**: Only needed when compiling and running unit tests.

---

## 4. Maven Build Lifecycle Phases

Maven executes builds through standard sequential phases:
1. **`validate`**: Validates project structure and pom.xml correctness.
2. **`compile`**: Compiles Java source files (`.java` $\rightarrow$ `.class`) into `target/classes/`.
3. **`test-compile`**: Compiles test source files into `target/test-classes/`.
4. **`test`**: Runs unit tests using JUnit/TestNG.
5. **`package`**: Bundles compiled code into `.jar` or `.war` in `target/`.
6. **`verify`**: Runs integration test checks.
7. **`install`**: Installs generated `.jar` into local Maven cache (`~/.m2/repository`).
8. **`deploy`**: Copies final artifact to remote Nexus/Artifactory repository.

---

## 5. Explanation of Maven CLI Commands

Here is what each Maven command does:

### `mvn spring-boot:run`
- **What it does**: Executes the `run` goal provided by `spring-boot-maven-plugin`.
- **Internal Action**:
  1. Compiles Java source files (`src/main/java`).
  2. Copies application configuration (`src/main/resources/application.properties`).
  3. Boots up the Spring ApplicationContext container.
  4. Starts the embedded Tomcat web server on port `8080`.
  5. Keeps the process running to serve incoming HTTP requests from React.
- **When to use**: During development when you want to launch the Spring Boot API directly from terminal without manually packaging a JAR file.

---

### `mvn clean`
- **What it does**: Deletes the `target/` output folder.
- **Internal Action**: Removes all previously compiled `.class` files, generated artifacts, and cached build outputs.
- **When to use**: Before doing a fresh build to ensure obsolete classes or old build artifacts are completely removed.

---

### `mvn compile`
- **What it does**: Compiles the main Java source code (`src/main/java`).
- **Internal Action**: Runs `javac` compiler and places compiled `.class` files in `target/classes/`.
- **When to use**: To check if your Java source code compiles without syntax or type errors.

---

### `mvn test-compile`
- **What it does**: Compiles both main source code AND test source code (`src/test/java`).
- **Internal Action**: Places compiled test bytecode in `target/test-classes/`.
- **When to use**: To verify test code syntax without actually executing the tests.

---

### `mvn test`
- **What it does**: Compiles source code and runs all automated unit tests.
- **Internal Action**: Executes test suites using the Maven Surefire plugin and generates test reports in `target/surefire-reports/`.
- **When to use**: In CI/CD pipelines or before packaging to ensure all unit tests pass.

---

### `mvn package`
- **What it does**: Compiles code, runs tests, and packages the application.
- **Internal Action**: Creates an executable `rectangle-backend-0.0.1-SNAPSHOT.jar` inside `target/` folder containing all classes, resources, and bundled libraries.
- **When to use**: When preparing your Spring Boot application for production deployment.

---

### `mvn clean package`
- **What it does**: Combines `mvn clean` and `mvn package`.
- **Internal Action**: Completely deletes `target/`, recompiles all Java source code from scratch, runs tests, and creates a fresh production JAR file.
- **When to use**: Best practice command for generating clean release builds.
