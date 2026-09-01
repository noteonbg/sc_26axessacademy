# JUnit 5 & Mockito Unit Testing Proof of Concept (POC)

This project in `unittestpoc` demonstrates a simple, clean **JUnit 5 unit test with Mockito** testing a **Service Layer** that depends on a **Repository Layer** without requiring any database usage.

---

## 🎯 Key Concepts Explained

### 1. Repository Interface (`BookRepository.java`)
Defines 2 contract methods without any database or backend code:
- `findById(int id)`: Fetches a book title by ID.
- `save(String title)`: Saves a new book title.

### 2. Service Layer (`BookService.java`)
Contains the business logic and uses **Constructor Injection** to receive `BookRepository`:
- `getBookById(int id)`: Calls `repository.findById(id)` and returns `"Book Not Found"` if null.
- `addBook(String title)`: Validates that title is not blank, then calls `repository.save(title)`.

### 3. Unit Test Class (`BookServiceTest.java`)
Uses JUnit 5 and Mockito to test `BookService` in complete isolation:
- `@ExtendWith(MockitoExtension.class)`: Integrates Mockito with JUnit 5.
- `@Mock`: Creates a fake/mock object of `BookRepository`.
- `@InjectMocks`: Creates an instance of `BookService` and injects the mocked repository.
- `when(...).thenReturn(...)`: Defines fake behavior for repository calls (stubbing).
- `verify(...)`: Verifies that repository methods were called with expected arguments.

---

## 🛠️ Prerequisites

- **Java JDK**: Version 17 or higher (`java -version`)
- **Apache Maven**: Version 3.6+ (`mvn -v`)

---

## 📖 Explanation of Maven Keywords & Commands

Below is a detailed breakdown of what each Maven command and keyword means:

| Maven Command / Flag | What It Means | What It Does Under the Hood |
| :--- | :--- | :--- |
| `mvn` | **Maven CLI** | Invokes the Apache Maven build automation tool executable. |
| `clean` | **Clean Lifecycle Phase** | Deletes the `target/` output folder, removing all previously compiled `.class` files and build artifacts so you start fresh. |
| `compile` | **Compile Phase** | Compiles main Java source code into `.class` bytecode files inside `target/classes/`. |
| `test` | **Test Lifecycle Phase** | Compiles main and test code, then executes all unit tests using Maven Surefire plugin. |
| `package` | **Package Phase** | Runs all unit tests first, and if they pass, packages compiled code into a JAR file in `target/`. |
| `-Dtest=BookServiceTest` | **System Property Flag** | `-D` passes a property to Maven. `test=BookServiceTest` instructs Maven Surefire to run **only** the `BookServiceTest` class instead of all tests. |

---

## 🚀 Maven Execution Commands

Navigate to the `unittestpoc` folder:

```bash
cd unittestpoc
```

### 1. Compile the Project
Deletes old builds and compiles main source files:
```bash
mvn clean compile
```

### 2. Run All Unit Tests
Compiles main + test files, then runs all JUnit tests:
```bash
mvn test
```

### 3. Run a Specific Test Class
Runs only `BookServiceTest`:
```bash
mvn test -Dtest=BookServiceTest
```

### 4. Build and Package Project
Cleans, runs tests, and packages into a `.jar` file:
```bash
mvn clean package
```

---

## ✅ Expected Test Output

When running `mvn test`, you should see a successful output similar to:

```text
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.example.demo.service.BookServiceTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.8 s -- in com.example.demo.service.BookServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```
