# Apache JMeter Load Testing POC (`jmeterpoc`) — Java Spring Boot Edition

> **Standard Chartered Axess Academy — Week 7 Performance Testing Hands-on Exercise**  
> **Backend Stack**: Java 21, Spring Boot 3.3.3, Spring MVC REST API, Maven  
> **Target Scenario**: Load test endpoint `http://localhost:3000/sc_employees` with 50 concurrent virtual users, verifying HTTP 200/201 response status codes, latency thresholds, and Summary Report listener.

---

## 1. Project Architecture (100% Java / Spring Boot)

This POC implements an enterprise-grade performance testing architecture built entirely in **Java & Spring Boot**:

```
+-----------------------------------------------------------------------------------------+
|                    SPRING BOOT & APACHE JMETER LOAD TESTING ARCHITECTURE                |
|                                                                                         |
|   [JMeter Test Plan / Java Load Runner]                                                 |
|   • 50 Virtual Users (Threads)                                                          |
|   • Ramp-up: 5 seconds                                                                  |
|   • Iterations: 2 Loops (Total = 200 Requests)                                          |
|   • Dataset: test_employees.csv                                                         |
|         │                                                                               |
|         ├───> GET  http://localhost:3000/sc_employees ──> Assertion: HTTP 200 OK        |
|         └───> POST http://localhost:3000/sc_employees ──> Assertion: HTTP 201 CREATED   |
|         │                                                 Assertion: Duration < 2000ms  |
|         ▼                                                                               |
|   [Spring Boot REST Application (com.sc.jmeterpoc)]                                     |
|   • Port 3000 (server.port=3000)                                                        |
|   • Embedded Apache Tomcat with 200 max worker threads                                  |
|   • EmployeeController (@RestController) + EmployeeService                              |
|   • Thread-safe ConcurrentHashMap data store                                            |
|   • Spring Boot Actuator for health & live performance metrics                          |
+-----------------------------------------------------------------------------------------+
```

---

## 2. File & Directory Layout

```
jmeterpoc/
├── pom.xml                                    <- Maven configuration (Spring Boot 3.3.3, Java 21)
├── src/
│   ├── main/
│   │   ├── java/com/sc/jmeterpoc/
│   │   │   ├── ScEmployeeApplication.java     <- Spring Boot @SpringBootApplication main entrypoint
│   │   │   ├── controller/
│   │   │   │   └── EmployeeController.java    <- @RestController handling GET (200) & POST (201)
│   │   │   ├── model/
│   │   │   │   └── Employee.java              <- Employee domain entity
│   │   │   └── service/
│   │   │       └── EmployeeService.java       <- Business logic & thread-safe repository
│   │   └── resources/
│   │       └── application.properties         <- Port 3000, Tomcat thread pool config
│   └── test/
│       └── java/com/sc/jmeterpoc/
│           └── JavaJMeterLoadTestRunner.java  <- Pure Java 21 multi-threaded load test simulation
├── sc_employees_load_test.jmx                 <- Official Apache JMeter Test Plan (50 users)
├── test_employees.csv                         <- Parameterized CSV data for load testing
├── run_jmeter.bat                             <- Non-GUI JMeter execution batch script
├── run_jmeter.ps1                             <- Non-GUI JMeter execution PowerShell script
└── README.md                                  <- Technical setup and execution documentation
```

---

## 3. Quick Start Guide

### Step 1: Start the Spring Boot REST API
Open a terminal in `jmeterpoc/` and run:
```bash
mvn spring-boot:run
```
The application will compile and launch on `http://localhost:3000`.

**Verify Health Endpoints in Browser or Terminal**:
- Health: `http://localhost:3000/actuator/health`
- Employee API: `http://localhost:3000/sc_employees`
- Live Load Stats: `http://localhost:3000/sc_employees/stats`

---

### Step 2: Run the Performance Tests

#### Option A: Using the Pure Java Concurrency Runner
In a second terminal, compile and run the Java test simulation:
```bash
mvn test-compile
java -cp "target\test-classes;target\classes" com.sc.jmeterpoc.JavaJMeterLoadTestRunner
```
This spawns 50 concurrent virtual user threads in Java, ramps them up over 5 seconds, sends 200 HTTP requests, verifies HTTP 200/201 assertions and duration limits, and prints a formatted JMeter Summary Report table in the terminal!

#### Option B: Using the Apache JMeter GUI
1. Open Apache JMeter (`F:\software\apache-jmeter-5.6.3\bin\jmeter.bat`).
2. Go to **File > Open** and select `sc_employees_load_test.jmx`.
3. Review the configured tree nodes:
   - **50 Concurrent Users - Load Test** (Thread Group: 50 threads, ramp-up = 5s, loops = 2)
   - **GET /sc_employees** (HTTP Sampler + Assertion: HTTP 200 OK)
   - **POST /sc_employees** (HTTP Sampler + Assertion: HTTP 201 Created)
   - **Summary Report** (Listener)
   - **View Results Tree** (Listener)
4. Click the green **Start (Play)** button.
5. Select **Summary Report** in the left navigation tree to view real-time statistics.

#### Option C: Non-GUI / CLI Execution with Interactive HTML Report
Run the provided batch or PowerShell runner:
```bash
.\run_jmeter.bat
# or
powershell -ExecutionPolicy Bypass -File .\run_jmeter.ps1
```
Or execute the direct JMeter command:
```bash
F:\software\apache-jmeter-5.6.3\bin\jmeter.bat -n -t sc_employees_load_test.jmx -l results.jtl -e -o html_report
```
Open `html_report/index.html` in any web browser to view interactive charts for:
- Response times over time
- Throughput (Transactions Per Second)
- Latency percentiles (90th, 95th, 99th)
- Response code distribution

---

## 4. Key Spring Boot & JMeter Concepts Demonstrated

### 1. Spring Boot REST Controllers & HTTP Status Codes
In `EmployeeController.java`:
- `@GetMapping`: Returns `ResponseEntity.ok(...)` with HTTP 200 OK.
- `@PostMapping`: Returns `ResponseEntity.status(HttpStatus.CREATED).body(...)` with explicit HTTP 201 Created status.

### 2. High-Concurrency Tomcat Configuration
In `application.properties`:
- `server.tomcat.threads.max=200`: Configures Tomcat's worker thread pool to handle dozens of concurrent connections without queuing or dropping sockets.
- `server.tomcat.accept-count=100`: Queue capacity for incoming connection requests when all worker threads are active.

### 3. JMeter Test Plan Structure
- **Thread Group**: Simulates 50 concurrent employees/clients logging in simultaneously.
- **CSV Data Set Config**: Reads dynamic records from `test_employees.csv` to inject real employee attributes (`name`, `department`, `role`, `salary`) into POST payloads.
- **Response Assertions**: Verifies that status codes strictly equal 200 (for reads) and 201 (for writes).
- **Duration Assertion**: Verifies that 100% of transactions complete in under 2,000 ms.
