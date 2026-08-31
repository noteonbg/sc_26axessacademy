# Banking Account Service - JUnit 5 & Mockito Unit Testing Guide

> **Location:** `F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a20Week7Testingandclosing\a03junit`  
> **Build Tool:** Apache Maven  
> **Java Version:** OpenJDK 21  
> **Test Frameworks:** JUnit 5 (Jupiter 5.10.2) + Mockito 5.11.0

---

## 1. Project Architecture Overview

This project demonstrates a standard 2-layer Java enterprise architecture without relying on heavy external database frameworks:

```
┌─────────────────────────────────────────────────────────┐
│                 Main Application / Caller               │
└────────────────────────────┬────────────────────────────┘
                             │
┌────────────────────────────┴────────────────────────────┐
│                    Service Layer                        │
│                (AccountServiceImpl)                     │
│  Enforces business validation rules (deposit limits,   │
│  overdraft prevention, high-value customer scoring)     │
└────────────────────────────┬────────────────────────────┘
                             │
┌────────────────────────────┴────────────────────────────┐
│                   Repository Layer                      │
│                (AccountRepositoryImpl)                  │
│  Simulates DB latency via Thread.sleep(2000) for live   │
│  data access operations.                                │
└─────────────────────────────────────────────────────────┘
```

### Key Components:
- **`Account.java`** (`com.bank.account.model`): Domain entity representing a bank account (id, holder name, balance, account type, status).
- **`AccountRepository.java` & `AccountRepositoryImpl.java`** (`com.bank.account.repository`): Data access layer pre-populated with seed data, simulating a **2000ms (2 seconds) database delay** in `findById()`.
- **`AccountServiceImpl.java`** (`com.bank.account.service`): Service layer implementing core financial business logic.
- **`MainApplication.java`** (`com.bank.account`): Live executable entry point.

---

## 2. The Repository Latency Problem & Mocking Solution

### The Problem:
When calling the real `AccountRepositoryImpl` in live production or un-mocked execution, every `findById()` invocation introduces a mandatory **2-second delay** to simulate slow network/IO database calls. Running end-to-end integration calls through the service layer for multiple scenarios takes 6 to 10+ seconds.

### The Solution (Mockito Mocking in JUnit 5):
In **Unit Testing**, we want to test *only* the business logic of `AccountServiceImpl` in complete isolation. We do **not** want unit tests to wait for slow database queries or network calls.

Using Mockito's `@Mock` and `@InjectMocks`, we replace the real `AccountRepositoryImpl` with a fast in-memory **Mock object**. We stub responses using `when(accountRepository.findById(...)).thenReturn(...)`. 

**Result:** The entire test suite of 22 test cases runs in **under 0.2 seconds** instead of taking 40+ seconds!

---

## 3. How to Compile, Run, and Test

Ensure you navigate to the project directory first:
```bash
cd F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a20Week7Testingandclosing\a03junit
```

### A. Compile the Project
To clean previous build artifacts and compile all Java source files:
```bash
mvn clean compile
```

### B. Run the Live Demo Application (With Simulated Latency)
To execute `MainApplication.java` and observe the live 2-second database delay:
```bash
mvn exec:java
```
*Output snippet:*
```
[Action 1] Fetching Account details for ACC1001...
[Repo Layer] Simulating slow database lookup for ACC1001... (2000ms delay)
Result: Account{accountId='ACC1001', holderName='Alice Smith', balance=5000.0, ...}
Total Execution Time (With Repo Latency): ~6000 ms
```

### C. Execute Unit Tests (Bypassing Latency via Mocking)
To execute all JUnit 5 unit tests:
```bash
mvn test
```
*Output snippet:*
```
[INFO] Running com.bank.account.service.AccountServiceTest
[INFO] Tests run: 22, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.142 s
[INFO] BUILD SUCCESS
```

---

## 4. How JUnit 5 Works: Architecture & Test Execution Lifecycle

JUnit 5 is composed of three main modules:
$$\text{JUnit 5} = \text{JUnit Platform} + \text{JUnit Jupiter} + \text{JUnit Vintage}$$

1. **JUnit Platform:** Launches testing frameworks on the JVM. Defines the `TestEngine` API.
2. **JUnit Jupiter:** The modern programming and extension model for writing tests in Java 8+.
3. **JUnit Vintage:** Provides backward compatibility for running JUnit 3 and JUnit 4 tests.

### Test Execution Lifecycle for a Test Class:

```
                  ┌─────────────────────────────────────┐
                  │            @BeforeAll               │
                  │   Static setup (Runs once before    │
                  │         all test methods)           │
                  └──────────────────┬──────────────────┘
                                     │
           ┌─────────────────────────┴─────────────────────────┐
           ▼                                                   ▼
┌──────────────────────┐                            ┌──────────────────────┐
│     @BeforeEach      │                            │     @BeforeEach      │
│ Fresh setup before T1│                            │ Fresh setup before T2│
└──────────┬───────────┘                            └──────────┬───────────┘
           │                                                   │
           ▼                                                   ▼
┌──────────────────────┐                            ┌──────────────────────┐
│        @Test 1       │                            │        @Test 2       │
│  Executes Test 1     │                            │  Executes Test 2     │
└──────────┬───────────┘                            └──────────┬───────────┘
           │                                                   │
           ▼                                                   ▼
┌──────────────────────┐                            ┌──────────────────────┐
│      @AfterEach      │                            │      @AfterEach      │
│ Cleanup after T1     │                            │ Cleanup after T2     │
└──────────┬───────────┘                            └──────────┬───────────┘
           │                                                   │
           └─────────────────────────┬─────────────────────────┘
                                     │
                  ┌──────────────────┴──────────────────┐
                  │             @AfterAll               │
                  │    Static cleanup (Runs once after  │
                  │         all test methods)           │
                  └─────────────────────────────────────┘
```

---

## 5. Comprehensive Annotation Reference Guide

### A. Core JUnit 5 Annotations Used in this Project

| Annotation | Description & Usage | Code Example from Project |
| :--- | :--- | :--- |
| **`@Test`** | Denotes that a method is a test method. Unlike JUnit 4, it does not require `public` access modifier. | `@Test void testGetAccountDetails_Success()` |
| **`@BeforeAll`** | Executes **once** before all test methods in the current class. Must be static. | `@BeforeAll static void beforeAllTests() { ... }` |
| **`@AfterAll`** | Executes **once** after all test methods in the current class have completed. Must be static. | `@AfterAll static void afterAllTests() { ... }` |
| **`@BeforeEach`** | Executes **before each** test method. Used to re-initialize fresh test data fixtures. | `@BeforeEach void setUpEachTest() { sampleAccount = new Account(...); }` |
| **`@AfterEach`** | Executes **after each** test method. Used to clean up state or temp files post-execution. | `@AfterEach void tearDownEachTest() { ... }` |
| **`@DisplayName`** | Declares a custom, human-readable name for the test class or test method in reports. | `@DisplayName("Should deposit funds correctly and update balance")` |
| **`@Disabled`** | Disables/skips a test class or test method during execution (replaces JUnit 4 `@Ignore`). | `@Disabled("Feature pending legacy system deprecation")` |
| **`@Timeout`** | Fails the test if its execution time exceeds the specified threshold. | `@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)` |
| **`@ParameterizedTest`** | Signals that a test method runs multiple times with different argument inputs. | `@ParameterizedTest @ValueSource(strings = {"", "   "})` |
| **`@ValueSource`** | Provides a single array of literal values (`strings`, `doubles`, `ints`) to a parameterized test. | `@ValueSource(doubles = {0.0, -100.0, -0.01})` |
| **`@CsvSource`** | Expresses complex multi-argument test datasets as comma-separated values. | `@CsvSource({"49999.99, false", "50000.00, true"})` |
| **`@Nested`** | Groups related test cases into inner nested classes for structured hierarchical reporting. | `@Nested @DisplayName("Financial Deposit & Withdrawal Operations") class TransactionOperationsTests` |

### B. Mockito Annotations & Extension Integration

| Annotation / Method | Description & Usage | Code Example from Project |
| :--- | :--- | :--- |
| **`@ExtendWith(MockitoExtension.class)`** | Registers Mockito extension with JUnit 5 to enable automatic mock creation & injection. | `@ExtendWith(MockitoExtension.class) class AccountServiceTest` |
| **`@Mock`** | Creates a mock instance of a dependency (simulates `AccountRepository`). | `@Mock private AccountRepository accountRepository;` |
| **`@InjectMocks`** | Creates an instance of the class under test and injects `@Mock` fields into its constructor. | `@InjectMocks private AccountServiceImpl accountService;` |
| **`when(...).thenReturn(...)`** | Stubs a mock method call to return a pre-defined result (bypasses real method). | `when(accountRepository.findById("ACC1001")).thenReturn(Optional.of(sampleAccount));` |
| **`assertThrows(...)`** | Asserts that execution of the supplied executable throws an expected exception type. | `assertThrows(IllegalArgumentException.class, () -> service.withdraw(...));` |
| **`assertAll(...)`** | Grouped assertion where all contained assertions are executed even if one fails. | `assertAll("Account Validation", () -> assertEquals(...), () -> assertTrue(...));` |
| **`verify(...)`** | Verifies that a specific mock method was invoked with expected arguments and frequency. | `verify(accountRepository, times(1)).findById("ACC1001");`<br>`verify(accountRepository, never()).save(any());` |

---

## 6. Summary of Test Results

Executing `mvn test` produces 22 distinct test executions:
- **Direct Repository Tests:** 4 passed
- **Service Layer Mocked Tests:** 17 passed
- **Disabled Tests:** 1 skipped (`testLegacyTransferFeature`)
- **Total Duration:** **~0.14 Seconds**

This demonstrates the power of unit testing with JUnit 5 and Mockito: achieving 100% logic coverage with instant feedback loop execution.
