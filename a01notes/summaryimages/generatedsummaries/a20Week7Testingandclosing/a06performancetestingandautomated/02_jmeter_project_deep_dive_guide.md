# Apache JMeter Project Deep-Dive & Architecture Guide (`jmeterpoc`)

> **Document Purpose**: Complete code and configuration walkthrough for the **`jmeterpoc`** project created for **Week 7: Performance Testing Fundamentals (Handson 1)** at Standard Chartered Axess Academy.  
> **Target Audience**: Java & Spring Boot Developers, QA Engineers, and System Architects.  
> **Tech Stack**: Java 21 LTS, Spring Boot 3.3.3, Embedded Apache Tomcat, Apache JMeter 5.6.3.

---

## Table of Contents
1. [Project Overview & Objective](#1-project-overview--objective)
2. [End-to-End Architecture Diagram](#2-end-to-end-architecture-diagram)
3. [Spring Boot Backend — Code & Configuration Breakdown](#3-spring-boot-backend--code--configuration-breakdown)
   - [3.1 `pom.xml` (Maven Dependencies)](#31-pomxml-maven-dependencies)
   - [3.2 `application.properties` (Tomcat Thread Pool & Port 3000)](#32-applicationproperties-tomcat-thread-pool--port-3000)
   - [3.3 `ScEmployeeApplication.java` (Application Bootstrap)](#33-scemployeeapplicationjava-application-bootstrap)
   - [3.4 `Employee.java` (Domain Entity & JSON Mapping)](#34-employeejava-domain-entity--json-mapping)
   - [3.5 `EmployeeService.java` (Thread-Safe Business Logic)](#35-employeeservicejava-thread-safe-business-logic)
   - [3.6 `EmployeeController.java` (REST Controller & Status Codes)](#36-employeecontrollerjava-rest-controller--status-codes)
4. [Apache JMeter Test Plan — Component & XML Breakdown (`sc_employees_load_test.jmx`)](#4-apache-jmeter-test-plan--component--xml-breakdown)
   - [4.1 Test Plan Root & Variables](#41-test-plan-root--variables)
   - [4.2 HTTP Request Defaults](#42-http-request-defaults)
   - [4.3 HTTP Header Manager](#43-http-header-manager)
   - [4.4 CSV Data Set Config (`test_employees.csv`)](#44-csv-data-set-config)
   - [4.5 Thread Group (50 Concurrent Users)](#45-thread-group-50-concurrent-users)
   - [4.6 HTTP Samplers (GET & POST)](#46-http-samplers-get--post)
   - [4.7 Assertions (HTTP 200, HTTP 201, Duration < 2000ms)](#47-assertions-http-200-http-201-duration--2000ms)
   - [4.8 Listeners (Summary Report, View Results Tree, Aggregate Report)](#48-listeners)
5. [Pure Java Load Test Simulation (`JavaJMeterLoadTestRunner.java`)](#5-pure-java-load-test-simulation)
6. [Step-by-Step Execution Guide](#6-step-by-step-execution-guide)
7. [How to Read and Interpret Performance Metrics](#7-how-to-read-and-interpret-performance-metrics)
8. [Troubleshooting & Common Questions](#8-troubleshooting--common-questions)

---

## 1. Project Overview & Objective

### The Business Scenario (Handson 1 from Slide 14)
> **Requirement**: Write a load test using JMeter to hit the endpoint `http://localhost:3000/sc_employees` with 50 concurrent users. Add response assertions for HTTP 200/201 and a Summary Report listener.

In banking and enterprise financial systems, APIs must sustain high transaction volumes during critical events (such as month-end payroll settlement or stock market openings). This project provides:
1. A **production-grade Spring Boot 3.3 REST API** that listens on port `3000`, implements `/sc_employees`, and handles high-concurrency requests safely.
2. A **fully configured Apache JMeter Test Plan (`.jmx`)** implementing 50 concurrent users, parameterization, response code assertions, latency assertions, and listeners.
3. A **pure Java multi-threaded test runner** allowing Java developers to execute and debug the 50-user load test inside any Java IDE or terminal without opening the JMeter GUI.

---

## 2. End-to-End Architecture Diagram

```
+-----------------------------------------------------------------------------------------------+
|                                      TEST EXECUTION TIER                                      |
|                                                                                               |
|   [Option 1: Apache JMeter GUI / CLI]            [Option 2: Java Load Test Runner]             |
|   • F:\software\apache-jmeter-5.6.3               • JavaJMeterLoadTestRunner.java (Java 21)   |
|   • sc_employees_load_test.jmx                    • 50 Concurrent Worker Threads              |
|   • Parameterized from test_employees.csv         • Native java.net.http.HttpClient           |
|                                                                                               |
|               │                                                 │                             |
|               │ (50 Concurrent HTTP/1.1 Connections)            │                             |
|               └────────────────────────┬────────────────────────┘                             |
|                                        │                                                      |
+----------------------------------------┼------------------------------------------------------+
                                         ▼
+-----------------------------------------------------------------------------------------------+
|                              APPLICATION TIER (SPRING BOOT 3.3)                               |
|                                                                                               |
|  [Embedded Apache Tomcat Web Server (Port 3000)]                                              |
|  • Worker Thread Pool: Max = 200 threads, Min-Spare = 20 threads                              |
|  • Connection Queue: Accept Count = 100                                                       |
|                                                                                               |
|  [EmployeeController (@RestController)]                                                       |
|  • GET  /sc_employees       ──> Returns HTTP 200 OK with List<Employee>                       |
|  • POST /sc_employees       ──> Returns HTTP 201 CREATED with new Employee                    |
|  • GET  /sc_employees/{id}  ──> Returns HTTP 200 OK (or 404 NOT FOUND)                        |
|  • GET  /sc_employees/stats ──> Returns Live Throughput (TPS) & Counters                      |
|                                                                                               |
|  [EmployeeService (Business & Persistence Layer)]                                             |
|  • Thread-Safe Storage: ConcurrentHashMap<Long, Employee>                                     |
|  • Thread-Safe IDs    : AtomicLong idSequence                                                 |
|  • Latency Simulator  : ThreadLocalRandom delay (25ms - 80ms)                                 |
+-----------------------------------------------------------------------------------------------+
```

---

## 3. Spring Boot Backend — Code & Configuration Breakdown

All backend code resides in:  
`F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a20Week7Testingandclosing\a06performancetestingandautomated\jmeterpoc`

### 3.1 `pom.xml` (Maven Dependencies)

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.3.3</version>
</parent>
```
* **Why it was created**: The parent POM manages standard dependency versions, Java 21 compiler compliance, and build plugin bindings so we never face JAR version conflicts.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
* **Why it was created**: Bundles three essential capabilities:
  1. **Embedded Tomcat 10.1**: Eliminates the need for external application server installation (WAR deployment).
  2. **Spring MVC**: Provides `@RestController`, `@GetMapping`, and `@PostMapping` annotations.
  3. **Jackson JSON Engine**: Automatically converts Java POJOs into JSON strings and vice versa.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
* **Why it was created**: Exposes production-ready health and monitoring endpoints (`/actuator/health`, `/actuator/metrics`) to verify JVM memory and active thread counts during performance testing.

---

### 3.2 `application.properties` (Tomcat Thread Pool & Port 3000)

Located at: `src/main/resources/application.properties`

```properties
# 1. SERVER PORT
server.port=3000
```
* **Why it was created**: Slide 14 explicitly commands the load test to hit `http://localhost:3000/sc_employees`. Default Spring Boot port is 8080, so setting `server.port=3000` aligns exactly with the training requirement.

```properties
# 2. EMBEDDED TOMCAT THREAD POOL CONFIGURATION
server.tomcat.threads.max=200
server.tomcat.threads.min-spare=20
server.tomcat.accept-count=100
server.tomcat.connection-timeout=20000
```
* **Why it was created**: Under a 50-user load test:
  * `server.tomcat.threads.max=200`: Ensures Tomcat can dedicate an independent worker thread to every one of the 50 concurrent incoming JMeter connections without dropping requests.
  * `server.tomcat.threads.min-spare=20`: Keeps 20 worker threads pre-warmed in memory so the first burst of requests does not suffer thread-creation latency.
  * `server.tomcat.accept-count=100`: Buffer queue capacity for OS TCP connection backlog.

---

### 3.3 `ScEmployeeApplication.java` (Application Bootstrap)

Located at: `src/main/java/com/sc/jmeterpoc/ScEmployeeApplication.java`

```java
@SpringBootApplication
public class ScEmployeeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScEmployeeApplication.class, args);
    }
}
```
* **Why it was created**: Serves as the starting point of the JVM runtime.
* **What `@SpringBootApplication` does**:
  1. `@Configuration`: Marks the class as a configuration source for beans.
  2. `@EnableAutoConfiguration`: Automatically configures the web environment, Tomcat, and Jackson.
  3. `@ComponentScan`: Recursively scans package `com.sc.jmeterpoc` for `@RestController` and `@Service` classes.

---

### 3.4 `Employee.java` (Domain Entity & JSON Mapping)

Located at: `src/main/java/com/sc/jmeterpoc/model/Employee.java`

```java
public class Employee implements Serializable {
    private Long id;
    private String name;
    private String department;
    private String role;
    private Double salary;

    public Employee() {} // Required by Jackson JSON deserializer
    public Employee(Long id, String name, String department, String role, Double salary) { ... }
    // Getters and Setters...
}
```
* **Why it was created**: Defines the data model exchanged over HTTP.
* **Why the No-Args Constructor exists**: Jackson JSON library needs an empty constructor to create a blank instance before calling setter methods when parsing incoming POST JSON bodies.

---

### 3.5 `EmployeeService.java` (Thread-Safe Business Logic)

Located at: `src/main/java/com/sc/jmeterpoc/service/EmployeeService.java`

```java
@Service
public class EmployeeService {
    private final Map<Long, Employee> employeeRepository = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);
```
* **Why `ConcurrentHashMap` was chosen**: In a single-threaded unit test, a regular `HashMap` works. But when **50 JMeter threads** write to the map simultaneously, a normal `HashMap` corrupts its internal linked buckets and throws `ConcurrentModificationException`. `ConcurrentHashMap` utilizes lock-striping for thread-safe concurrent reads and writes.
* **Why `AtomicLong` was chosen**: If two threads increment a regular `long id = 0` via `id++` simultaneously, they will generate duplicate IDs due to race conditions. `AtomicLong.incrementAndGet()` relies on hardware CPU CAS (Compare-And-Swap) instructions for atomic uniqueness.

```java
private void simulateDatabaseDelay(int minMs, int maxMs) {
    try {
        int delay = ThreadLocalRandom.current().nextInt(minMs, maxMs + 1);
        Thread.sleep(delay);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
```
* **Why simulated latency exists**: In real banking systems, a database query takes 25ms–80ms across the network. If our mock responded in 0ms, JMeter would measure synthetic memory speeds rather than realistic web transaction latency.

---

### 3.6 `EmployeeController.java` (REST Controller & Status Codes)

Located at: `src/main/java/com/sc/jmeterpoc/controller/EmployeeController.java`

```java
@RestController
@RequestMapping(value = "/sc_employees", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class EmployeeController {
```
* **Why `@RestController`**: Instructs Spring to automatically write method return values directly into the HTTP response body as JSON.

#### The GET Handler (HTTP 200 OK):
```java
@GetMapping
public ResponseEntity<Map<String, Object>> getAllEmployees() {
    List<Employee> list = employeeService.getAllEmployees();
    Map<String, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("count", list.size());
    response.put("data", list);
    return ResponseEntity.ok(response); // HTTP 200 OK
}
```
* **Handson 1 Verification**: JMeter asserts that every GET request receives **HTTP status 200** and that the body contains the string `"success"`.

#### The POST Handler (HTTP 201 CREATED):
```java
@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody Employee employee) {
    Employee created = employeeService.createEmployee(employee);
    Map<String, Object> response = new HashMap<>();
    response.put("status", "created");
    response.put("message", "Employee record created successfully");
    response.put("data", created);
    return ResponseEntity.status(HttpStatus.CREATED).body(response); // HTTP 201 CREATED
}
```
* **Handson 1 Verification**: By standard HTTP REST specification, newly created resources return status code `201 Created`. JMeter's POST sampler contains an assertion verifying status code **201**.

#### The Live Stats Handler:
```java
@GetMapping("/stats")
public ResponseEntity<Map<String, Object>> getPerformanceStats() { ... }
```
* **Why it was created**: Allows monitoring live throughput (TPS) directly from the application's perspective.

---

## 4. Apache JMeter Test Plan — Component & XML Breakdown

The test plan is stored in:  
[`sc_employees_load_test.jmx`](file:///F:/scproject/sc_26axessacademy/a01notes/summaryimages/generatedsummaries/a20Week7Testingandclosing/a06performancetestingandautomated/jmeterpoc/sc_employees_load_test.jmx)

JMeter `.jmx` files are standard XML documents that configure JMeter's Abstract Syntax Tree (AST) of test execution components. Here is what each element does:

### 4.1 Test Plan Root & Variables
```xml
<TestPlan guiclass="TestPlanGui" testclass="TestPlan" testname="SC Employees Performance Test Plan">
  <elementProp name="TestPlan.user_defined_variables" ...>
    <elementProp name="HOST"><stringProp name="Argument.value">localhost</stringProp></elementProp>
    <elementProp name="PORT"><stringProp name="Argument.value">3000</stringProp></elementProp>
  </elementProp>
</TestPlan>
```
* **Why it exists**: The parent node of all execution elements. We declare `HOST` and `PORT` as User Defined Variables so if we later run tests against staging (e.g. `api-staging.bank.com:443`), we only change one variable instead of editing 50 samplers.

---

### 4.2 HTTP Request Defaults
```xml
<ConfigTestElement guiclass="HttpDefaultsGui" testclass="ConfigTestElement" testname="HTTP Request Defaults">
  <stringProp name="HTTPSampler.domain">${HOST}</stringProp>
  <stringProp name="HTTPSampler.port">${PORT}</stringProp>
  <stringProp name="HTTPSampler.protocol">http</stringProp>
  <stringProp name="HTTPSampler.connect_timeout">5000</stringProp>
  <stringProp name="HTTPSampler.response_timeout">10000</stringProp>
</ConfigTestElement>
```
* **Why it exists**: Defines base connection properties for all samplers. Every HTTP sampler inherits `localhost:3000` automatically.
* `connect_timeout`: 5,000ms (fails fast if server is completely down).
* `response_timeout`: 10,000ms (aborts if a request hangs indefinitely).

---

### 4.3 HTTP Header Manager
```xml
<HeaderManager guiclass="HeaderPanel" testclass="HeaderManager" testname="HTTP Header Manager">
  <collectionProp name="HeaderManager.headers">
    <elementProp name="Content-Type"><stringProp name="Header.value">application/json</stringProp></elementProp>
    <elementProp name="Accept"><stringProp name="Header.value">application/json</stringProp></elementProp>
  </collectionProp>
</HeaderManager>
```
* **Why it exists**: Tells Spring Boot that incoming request bodies are formatted as JSON and that the client expects JSON in response.

---

### 4.4 CSV Data Set Config
```xml
<CSVDataSet guiclass="TestBeanGUI" testclass="CSVDataSet" testname="Employee CSV Test Data">
  <stringProp name="filename">test_employees.csv</stringProp>
  <stringProp name="variableNames">emp_name,department,role,salary</stringProp>
  <boolProp name="ignoreFirstLine">true</boolProp>
</CSVDataSet>
```
* **Why it exists**: Implements **Data-Driven Performance Testing**.
* Instead of posting 100 identical dummy records, JMeter reads rows from `test_employees.csv` and injects dynamic employee attributes (`${emp_name}`, `${department}`, `${role}`, `${salary}`) into the POST body.

---

### 4.5 Thread Group (50 Concurrent Users)
```xml
<ThreadGroup guiclass="ThreadGroupGui" testclass="ThreadGroup" testname="50 Concurrent Users - Load Test">
  <stringProp name="ThreadGroup.num_threads">50</stringProp>
  <stringProp name="ThreadGroup.ramp_time">5</stringProp>
  <stringProp name="LoopController.loops">2</stringProp>
</ThreadGroup>
```
* **`num_threads = 50`**: Creates 50 independent Java threads, simulating 50 simultaneous employees or mobile clients.
* **`ramp_time = 5`**: Ramp-up duration of 5 seconds. This means $50 \div 5 = 10\text{ threads/second}$. Threads are spawned gradually to prevent artificially spiking the server at second zero.
* **`loops = 2`**: Each thread runs the scenario twice, yielding:
  $$\text{Total Requests} = 50\text{ users} \times 2\text{ samplers (GET + POST)} \times 2\text{ loops} = 200\text{ requests}$$

---

### 4.6 HTTP Samplers (GET & POST)

#### Sampler 1: `GET /sc_employees`
```xml
<HTTPSamplerProxy guiclass="HttpTestSampleGui" testclass="HTTPSamplerProxy" testname="GET /sc_employees">
  <stringProp name="HTTPSampler.path">/sc_employees</stringProp>
  <stringProp name="HTTPSampler.method">GET</stringProp>
</HTTPSamplerProxy>
```
* Issues an HTTP GET request to `http://localhost:3000/sc_employees` to test read throughput.

#### Sampler 2: `POST /sc_employees`
```xml
<HTTPSamplerProxy guiclass="HttpTestSampleGui" testclass="HTTPSamplerProxy" testname="POST /sc_employees">
  <boolProp name="HTTPSampler.postBodyRaw">true</boolProp>
  <stringProp name="HTTPSampler.path">/sc_employees</stringProp>
  <stringProp name="HTTPSampler.method">POST</stringProp>
  <stringProp name="Argument.value">{
    "name": "${emp_name}",
    "department": "${department}",
    "role": "${role}",
    "salary": ${salary}
  }</stringProp>
</HTTPSamplerProxy>
```
* Issues an HTTP POST request sending dynamic JSON payload to test resource creation under concurrency.

---

### 4.7 Assertions (HTTP 200, HTTP 201, Duration < 2000ms)

#### Response Code Assertion on GET:
```xml
<ResponseAssertion guiclass="AssertionGui" testclass="ResponseAssertion" testname="Assert HTTP 200 OK">
  <stringProp name="Assertion.test_field">Assertion.response_code</stringProp>
  <intProp name="Assertion.test_type">8</intProp> <!-- 8 means Equals -->
  <stringProp name="Asserion.test_strings">200</stringProp>
</ResponseAssertion>
```
* Verifies that the server returned HTTP 200. If the server returned 404, 500, or timed out, JMeter marks the sample as a failure.

#### Response Code Assertion on POST:
```xml
<ResponseAssertion guiclass="AssertionGui" testclass="ResponseAssertion" testname="Assert HTTP 201 Created">
  <stringProp name="Assertion.test_field">Assertion.response_code</stringProp>
  <intProp name="Assertion.test_type">8</intProp>
  <stringProp name="Asserion.test_strings">201</stringProp>
</ResponseAssertion>
```
* Verifies that newly created resources returned HTTP 201 Created.

#### Duration Assertion (SLA / NFR Validation):
```xml
<DurationAssertion guiclass="DurationAssertionGui" testclass="DurationAssertion" testname="Assert Response Time &lt; 2000ms">
  <stringProp name="DurationAssertion.duration">2000</stringProp>
</DurationAssertion>
```
* Verifies that every transaction finishes in under 2 seconds ($<2000\text{ ms}$), enforcing the Non-Functional Requirement (NFR).

---

### 4.8 Listeners
1. **Summary Report (`SummaryReport`)**: Consolidates statistics (Samples, Average, Min, Max, Error %, TPS) and outputs to `results_summary.csv`.
2. **View Results Tree (`ViewResultsFullVisualizer`)**: Displays individual request and response headers and body content for debugging.
3. **Aggregate Report (`StatVisualizer`)**: Calculates 90th, 95th, and 99th percentile latencies.

---

## 5. Pure Java Load Test Simulation (`JavaJMeterLoadTestRunner.java`)

Located at: `src/test/java/com/sc/jmeterpoc/JavaJMeterLoadTestRunner.java`

### Why This Class Was Created:
Not every developer has Apache JMeter installed on their local machine, and running tests inside automated Java CI/CD pipelines is often easier via native Java.

### How It Works Under the Hood:
```java
// Allocates a thread pool with 50 worker threads
ExecutorService threadPool = Executors.newFixedThreadPool(50);

// Spawns 50 Callable tasks representing virtual users
for (int userId = 0; userId < 50; userId++) {
    tasks.add(() -> simulateVirtualUser(client, uId));
}

// Executes all 50 threads concurrently
List<Future<List<SampleMetric>>> futures = threadPool.invokeAll(tasks);
```
* Uses Java 21's `java.net.http.HttpClient` with HTTP/1.1 keep-alive connections.
* Computes mathematical percentiles ($90^{\text{th}}$ and $95^{\text{th}}$) and standard deviation across all samples.
* Prints a console table formatted identically to JMeter's Summary Report.

---

## 6. Step-by-Step Execution Guide

### Step 1: Open Terminal & Start Spring Boot
```bash
cd "F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a20Week7Testingandclosing\a06performancetestingandautomated\jmeterpoc"
mvn spring-boot:run
```
Expected output:
```
Tomcat started on port 3000 (http) with context path '/'
Started ScEmployeeApplication in 4.93 seconds
```

---

### Step 2: Run the Performance Tests

#### Method 1: Using the Batch Runner (Apache JMeter Non-GUI CLI)
Double-click `run_jmeter.bat` or run in PowerShell:
```powershell
.\run_jmeter.bat
```
This automatically invokes:
```powershell
F:\software\apache-jmeter-5.6.3\bin\jmeter.bat -n -t sc_employees_load_test.jmx -l results.jtl -e -o html_report
```
* `-n`: Non-GUI mode (essential for performance testing; GUI mode consumes excessive RAM).
* `-t`: Path to JMX test plan.
* `-l`: Log output file (`results.jtl`).
* `-e -o`: Generates interactive HTML dashboard in `html_report/`.

Open `html_report/index.html` in your browser to inspect interactive performance charts.

---

#### Method 2: Using the Java Concurrency Runner
In a second terminal:
```bash
mvn test-compile
java -cp "target\test-classes;target\classes" com.sc.jmeterpoc.JavaJMeterLoadTestRunner
```

---

#### Method 3: Using the Apache JMeter GUI
1. Open Apache JMeter (`F:\software\apache-jmeter-5.6.3\bin\jmeter.bat`).
2. Go to **File > Open**, select `sc_employees_load_test.jmx`.
3. Click the green **Start** button.
4. Click **Summary Report** to see real-time throughput and response time metrics.

---

## 7. How to Read and Interpret Performance Metrics

When you run the load test, JMeter outputs a Summary Report like this:

```
================================================================================================================
                 JAVA SPRING BOOT JMETER LOAD TEST SUMMARY REPORT (50 USERS)
================================================================================================================
Label                  | # Samples |  Average |    Min |    Max | Std. Dev. | Error % |   Throughput |  90th % |  95th %
----------------------------------------------------------------------------------------------------------------
GET /sc_employees      |       100 |   52.8ms |   32ms |  101ms |    12.7ms |   0.00% |     19.2/sec |  69.0ms |  71.0ms
POST /sc_employees     |       100 |   68.4ms |   42ms |  166ms |    17.5ms |   0.00% |     19.2/sec |  87.0ms |  89.0ms
----------------------------------------------------------------------------------------------------------------
TOTAL                  |       200 |   60.6ms |   32ms |  166ms |    17.2ms |   0.00% |     38.3/sec |  83.0ms |  87.0ms
================================================================================================================
```

### Metrics Definition & Analysis:
1. **# Samples (200)**: Total number of requests executed ($100\text{ GET} + 100\text{ POST}$).
2. **Average (60.6ms)**: Arithmetic mean response time across all 200 requests.
3. **Min (32ms) & Max (166ms)**: Fastest and slowest requests observed.
4. **Standard Deviation (17.2ms)**: Measure of latency variability. A low standard deviation means consistent performance; a high standard deviation indicates erratic response spikes.
5. **Error % (0.00%)**: Percentage of requests that failed assertions or returned error codes. In banking systems, Error % must strictly remain $< 1.0\%$.
6. **Throughput (38.3/sec)**: Transactions Per Second (TPS) processed by the application under test.
7. **$90^{\text{th}}$ Percentile (83.0ms)**: 90% of all users experienced a response time of 83.0ms or faster. Only 10% took longer.
8. **$95^{\text{th}}$ Percentile (87.0ms)**: 95% of requests completed within 87.0ms, well below the 2,000ms NFR requirement!

---

## 8. Troubleshooting & Common Questions

### Q1: What if port 3000 is already in use?
Check what process is holding port 3000 in PowerShell:
```powershell
Get-NetTCPConnection -LocalPort 3000
```
To stop the holding process:
```powershell
Stop-Process -Id <PID> -Force
```

### Q2: Why should JMeter tests NOT be run in GUI mode for real load tests?
The JMeter GUI renders Java Swing graphic components for every incoming sample. Under high load (500+ users), the JMeter GUI JVM runs out of memory and lags, creating artificial client-side bottlenecks. **Always run load tests in Non-GUI CLI mode (`-n -t ... -l ...`)**.

### Q3: Why did we set `ramp_time = 5` instead of `0`?
If 50 threads hit a server at millisecond zero, you are performing a **Spike Test**, which causes artificial thread pool saturation before connections are established. A ramp-up period of 5 seconds simulates human users logging in gradually over time.
