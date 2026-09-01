# JUnit 5 & Mockito Unit Testing Proof of Concept (POC)

This project in `unittestpoc` demonstrates the fundamental difference between running an application with a **Real Repository** (which is slow and simulates database execution) vs running **Unit Tests with Mockito** (which mocks out the repository for fast, isolated testing).

---

## 🎯 Key Educational Lessons

1. **`BookRepositoryImpl.java` (Real Concrete Implementation)**:
   - Contains real-world behavior with `System.out.println(">>> [REAL REPOSITORY]...")` logs.
   - Includes artificial slowness (`Thread.sleep(2000)`) simulating slow database/network queries.

2. **`MainApp.java` (Standalone Main Application)**:
   - Contains `public static void main(String[] args)`.
   - Instantiates `new BookRepositoryImpl()`.
   - Takes **~4.0 seconds** to run and prints `[REAL REPOSITORY]` console logs.

3. **`BookServiceTest.java` (JUnit 5 + Mockito Test)**:
   - Uses `@Mock` to create a fake repository object.
   - **`BookRepositoryImpl` is NEVER instantiated or called!**
   - No `[REAL REPOSITORY]` logs appear.
   - Executes **INSTANTLY (~11 ms)** without database latency.

---

## 🚀 How to Run the Java Program & Unit Tests Using Maven

First, open your terminal and navigate to the `unittestpoc` folder:

```bash
cd unittestpoc
```

### 1. Run the Main Java Application using Maven
To execute the Java program (`MainApp.java`) with Maven:

```bash
mvn compile exec:java
```

> **How it works:**
> - `compile`: Compiles `MainApp.java`, `BookService.java`, and `BookRepositoryImpl.java` into `.class` files in `target/classes/`.
> - `exec:java`: Uses Maven's `exec-maven-plugin` configured in `pom.xml` to launch `com.example.demo.MainApp.main()`.

**Expected Output:**
```text
=========================================================================
   RUNNING MAIN APPLICATION (USING REAL REPOSITORY WITH DATABASE DELAYS)
=========================================================================

Calling bookService.getBookById(1)...
>>> [REAL REPOSITORY] Connecting to Database to query book by ID: 1...
>>> [REAL REPOSITORY] Database query completed successfully!
--> Result: Clean Code

Calling bookService.addBook('Effective Java')...
>>> [REAL REPOSITORY] Executing SQL INSERT INTO books VALUES ('Effective Java')...
>>> [REAL REPOSITORY] Database transaction committed successfully!
--> Result Saved: true

=========================================================================
 MAIN APPLICATION COMPLETED IN: 4.044 SECONDS
 Notice the slowness and [REAL REPOSITORY] sysout messages above!
=========================================================================
```

---

### 2. Run the Unit Tests using Maven
To execute the JUnit 5 + Mockito unit tests:

```bash
mvn test
```

> **How it works:**
> - Compiles both main and test source files.
> - Runs `BookServiceTest.java` using Maven Surefire plugin.
> - Mockito intercepts repository calls so **NO 2-second sleep delays or `[REAL REPOSITORY]` logs occur**.

**Expected Output:**
```text
-------------------------------------------------------------------------
 [UNIT TEST] Running testGetBookById() using Mockito Mock...
 Notice: NO '[REAL REPOSITORY]' logs and NO 2-second delay!
-------------------------------------------------------------------------
>>> SUCCESS: Test completed in ONLY 11 ms! (Fast because of Mocking)

[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

### 3. Run a Specific Test Class using Maven
To run only `BookServiceTest`:

```bash
mvn test -Dtest=BookServiceTest
```

---

## 📖 Key Maven Commands Summary

| Maven Command | Purpose | What It Does |
| :--- | :--- | :--- |
| `mvn compile exec:java` | **Run Java App** | Compiles the project and runs `MainApp.main()` via `exec-maven-plugin`. |
| `mvn test` | **Run Unit Tests** | Compiles main & test code and executes all JUnit tests using Mockito mocks. |
| `mvn clean` | **Clean Build** | Deletes the `target/` directory to start a fresh build. |
| `mvn clean package` | **Build JAR** | Runs tests and packages the application into a `.jar` file in `target/`. |
