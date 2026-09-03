# Performance Testing Fundamentals & Test Automation Frameworks: A Practical Guide

> **Source Material**: Week 7 Campus Content — Standard Chartered Axess Academy  
> **Modules Covered**:
> 1. *Performance Testing Fundamentals* (Slide Deck 1)
> 2. *Test Automation Frameworks* (Slide Deck 2)
> 
> **Includes Hands-on POC**: Apache JMeter Load Testing Suite (`jmeterpoc`)

---

## Table of Contents
1. [Part 1: Performance Testing Fundamentals](#part-1-performance-testing-fundamentals)
   - [1.1 What is Performance Testing?](#11-what-is-performance-testing)
   - [1.2 Why Performance Testing Matters (Business & Technical Value)](#12-why-performance-testing-matters)
   - [1.3 The 7 Core Types of Performance Testing (with Real-World Examples)](#13-the-7-core-types-of-performance-testing)
   - [1.4 Key Performance Metrics Explained](#14-key-performance-metrics-explained)
   - [1.5 Non-Functional Requirements (NFRs) in Practice](#15-non-functional-requirements-nfrs-in-practice)
   - [1.6 The 4-Phase Performance Testing Process](#16-the-4-phase-performance-testing-process)
   - [1.7 Test Environment Considerations & Parity](#17-test-environment-considerations--parity)
   - [1.8 Common Performance Bottlenecks](#18-common-performance-bottlenecks)
   - [1.9 Performance Testing Tools Landscape](#19-performance-testing-tools-landscape)
   - [1.10 Performance Testing Best Practices](#110-performance-testing-best-practices)
2. [Part 2: Test Automation Frameworks](#part-2-test-automation-frameworks)
   - [2.1 Module 1: Page Object Model (POM) Pattern](#21-module-1-page-object-model-pom-pattern)
   - [2.2 Module 2: Data-Driven Testing (DDT) Framework with Apache POI](#22-module-2-data-driven-testing-ddt-framework-with-apache-poi)
   - [2.3 Module 3: TestNG Automation Framework & Annotations](#23-module-3-testng-automation-framework--annotations)
   - [2.4 Module 4: Keyword-Driven Framework (KDF)](#24-module-4-keyword-driven-framework-kdf)
3. [Part 3: JMeter Hands-on POC (`jmeterpoc`)](#part-3-jmeter-hands-on-poc-jmeterpoc)

---

# Part 1: Performance Testing Fundamentals

## 1.1 What is Performance Testing?

### Simple Definition
Functional testing checks **"Does the application work?"** (e.g., Can a user log in and transfer $100?).  
**Performance testing** checks **"How well does it work under pressure?"** (e.g., How fast does the transfer complete when 10,000 customers transfer money at the exact same second?).

It evaluates four critical pillars of system health:
1. **Responsiveness (Speed)**: How quickly does the system return results to the user?
2. **Stability**: Does the application stay healthy or crash when usage peaks?
3. **Reliability**: Does the system return correct answers consistently over extended time without degradation?
4. **Scalability**: Can the system handle higher workloads smoothly if additional servers or CPU cores are added?

```
+-------------------------------------------------------------------------+
|                       SOFTWARE TESTING SPECTRUM                         |
+------------------------------------+------------------------------------+
|         Functional Testing         |        Performance Testing         |
|   "Does it do the right thing?"    |      "Does it do it fast & reliably?"|
+------------------------------------+------------------------------------+
| • Button clicks                    | • Response time under load         |
| • Form validations                 | • Maximum throughput (TPS)         |
| • Business calculation correctness | • Hardware resource utilization    |
| • Negative test cases              | • Stability during traffic spikes  |
+------------------------------------+------------------------------------+
```

### Real-World Example
* **Banking Scenario**: You build a Mobile Banking app. When 1 person logs in, the dashboard loads in 0.5 seconds. When 5,000 employees try to check their salary balance on the 30th of the month, the server's CPU hits 100% and response times climb to 45 seconds or throw `HTTP 504 Gateway Timeouts`. Performance testing detects this bottleneck before customers ever encounter it.

---

## 1.2 Why Performance Testing Matters

Performance is not just a technical preference; it directly impacts company revenue, legal compliance, and customer trust.

```
                           POOR PERFORMANCE
                                  │
         ┌────────────────────────┴────────────────────────┐
         ▼                                                 ▼
  [Business Impact]                               [Technical Benefits of Testing]
  • Lost Revenue & Cart Abandonment               • Early Bug & Bottleneck Discovery
  • Outages During Peak Events                    • Optimized Cloud & Server Costs
  • Brand & Reputation Damage                     • Assured High-Availability & SLA
  • Regulatory Fines & Compliance Breaches        • Smooth User Experience (UX)
```

| Dimension | Risk / Consequence | Real-World Example |
| :--- | :--- | :--- |
| **Business Impact** | **Lost Revenue & Customers** | Amazon found that every 100ms of added latency cost them 1% in sales. If a mobile banking app freezes during payment, the customer switches to another card or app. |
| **Business Impact** | **Peak Usage Crashes** | A stock trading platform crashing on market open or budget day leads to multi-million dollar customer trading losses. |
| **Business Impact** | **Damaged Brand** | Customers tweet screenshots of banking app downtime (`#BankDown`), causing lasting brand damage. |
| **Business Impact** | **Regulatory Compliance** | Financial regulators (e.g., PRA/FCA, RBI, MAS, SEC) impose heavy fines on banks when core payment settlement systems suffer prolonged outages. |
| **Technical Benefit** | **Early Defect Identification** | Fixing a database deadlock during development costs $500; fixing it after a production crash costs $500,000 in downtime and emergency engineering. |
| **Technical Benefit** | **Resource Optimization** | Tuning code lets a bank run on 10 optimized cloud containers instead of paying for 50 oversized virtual machines. |

---

## 1.3 The 7 Core Types of Performance Testing

The slides detail 7 distinct types of performance tests. Here is a clear breakdown with practical analogies and examples:

```
                               PERFORMANCE TESTING TYPES
    ┌──────────────┬──────────────┬──────────────┬──────────────┬──────────────┬──────────────┬──────────────┐
    ▼              ▼              ▼              ▼              ▼              ▼              ▼              ▼
[1. Load]     [2. Stress]    [3. Volume]    [4. Spike]   [5. Soak/Endur][6. Scalability][7. Capacity]
 Expected       Breaking        Database        Sudden        Long-Term      Adding More     Maximum Safe
 Normal Load     Point           Size           Surge         Stability       Resources         Ceiling
```

### 1. Load Testing
* **Goal**: Validate how the system behaves under expected normal and peak business conditions.
* **Analogy**: Testing an elevator with its rated capacity of 10 people to ensure smooth ascent.
* **Example**: Simulating 500 concurrent customers using online banking between 10:00 AM and 11:00 AM on a regular Tuesday to verify page load stays under 1.5 seconds.

### 2. Stress Testing
* **Goal**: Push the system beyond its designed limits until it breaks, to observe how it degrades and whether it recovers gracefully (fail-safe).
* **Analogy**: Adding 25 people into a 10-person elevator to see if the safety alarm triggers or the cables snap.
* **Example**: Sending 10,000 requests per second to a login API designed for 2,000. Does the server gracefully return `HTTP 429 Too Many Requests` or `HTTP 503 Service Unavailable`, or does the operating system crash the database?

### 3. Volume Testing (Flood Testing)
* **Goal**: Check system responsiveness when the database is populated with an enormous volume of historical data.
* **Analogy**: Testing how fast a librarian finds a book when the library has 100 books vs. 5,000,000 books.
* **Example**: Testing an `Account Statement Search` API when the database table contains 50 million historical transaction records versus only 1,000 dummy records.

### 4. Spike Testing
* **Goal**: Test system resilience against sudden, instant surges of massive traffic followed by an immediate return to normal.
* **Analogy**: 1,000 people suddenly rushing through a stadium gate all at once when doors open.
* **Example**: A flash sale starting at 12:00:00 PM, or millions of users checking live cricket scores during the final over of a World Cup match.

### 5. Endurance / Soak Testing
* **Goal**: Run the system under sustained normal-to-high load over an extended time (12 hours, 24 hours, or over a weekend) to identify memory leaks and slow degradation.
* **Analogy**: Driving a car continuously on the highway for 48 hours without turning off the engine.
* **Example**: Running a continuous load of 200 transactions/sec for 48 hours. If memory usage climbs steadily from 2 GB to 32 GB without being garbage collected, a **memory leak** is uncovered.

### 6. Scalability Testing
* **Goal**: Measure if the application scales proportionally when hardware resources (CPU, RAM) or additional server nodes (horizontal scaling) are added.
* **Analogy**: Checking if doubling the number of cashiers at a supermarket cuts customer checkout wait times in half.
* **Example**: Running 1,000 users on 1 server node (Latency = 2s). Adding a 2nd server node and re-running 2,000 users: does latency stay at 2s?

### 7. Capacity Testing
* **Goal**: Determine the absolute maximum number of users or transactions a specific architecture can support while still satisfying agreed service level agreements (SLAs).
* **Analogy**: Determining the exact maximum seating capacity of a restaurant before service drops below acceptable quality.
* **Example**: Incrementally adding 100 users every 5 minutes until response times exceed 2 seconds. If it happens at 3,200 users, the system's capacity is **3,200 concurrent users**.

---

## 1.4 Key Performance Metrics Explained

Performance engineers measure four fundamental metrics:

```
+---------------------------------------------------------------------------------+
|                           4 KEY PERFORMANCE METRICS                             |
+----------------------+--------------------------+-------------------------------+
| Metric               | Unit                     | Real-World Target             |
+----------------------+--------------------------+-------------------------------+
| 1. Response Time     | Milliseconds (ms) / Sec  | < 200ms for APIs, < 2s for UI |
| 2. Throughput        | TPS / Requests per sec   | e.g. 500 TPS to 5,000 TPS     |
| 3. Error Rate        | Percentage (%)           | Must be < 1% (ideal is 0.0%)  |
| 4. Resource Usage    | Percentage (%)           | CPU < 80%, RAM < 75%          |
+----------------------+--------------------------+-------------------------------+
```

### Detailed Breakdown
1. **Response Time**: The total round-trip elapsed time from when a user hits "Submit" until the client receives the last byte of the response.
   * *Example*: User clicks "Confirm Payment" -> 350 ms later the receipt appears.
2. **Throughput (TPS - Transactions Per Second)**: The number of completed business transactions the server can process per unit of time.
   * *Example*: A Visa payment switch processing 4,500 approved transactions every second.
3. **Resource Utilization**: Hardware consumption on application servers, database instances, and network pipes.
   * *Example*: CPU hovering at 65%, RAM at 55%, disk I/O at 20%. If CPU hits 100%, incoming requests queue up and time out.
4. **Error Rate**: Percentage of requests that fail or return HTTP 4xx / 5xx status codes out of total requests sent.
   * *Formula*: `Error Rate = (Failed Requests / Total Requests) * 100%`. Must remain `< 1%`.

---

## 1.5 Non-Functional Requirements (NFRs) in Practice

Non-Functional Requirements (NFRs) are the measurable quality criteria defined by business analysts and architects.

```
                    NON-FUNCTIONAL REQUIREMENTS (NFRs)
                                     │
           ┌─────────────────────────┴─────────────────────────┐
           ▼                                                   ▼
[Performance Criteria]                               [Availability Criteria]
• Response Time: < 2.0 seconds                       • System Uptime: 99.9% (Three Nines)
• Throughput: Minimum 1,000 TPS                      • RTO: Recovery Time Objective < 15 min
• Concurrency: 5,000 Simultaneous Users              • RPO: Recovery Point Objective < 1 min
• Resource Ceiling: CPU & Memory < 80%
```

### SLA Targets & Examples
* **Response Time Threshold**: "The Fund Transfer API must respond in less than 2.0 seconds for 95% of requests (95th percentile)."
* **Throughput Requirement**: "The payment gateway must process at least 1,000 TPS during month-end payroll settlement."
* **Concurrent User Capacity**: "The system must support 5,000 active concurrent users with zero session dropouts."
* **Uptime (Availability)**: 99.9% uptime permits no more than 8.76 hours of total unplanned downtime across an entire year.
* **RTO (Recovery Time Objective)**: If a server fails, the backup cluster must take over within 15 minutes.
* **RPO (Recovery Point Objective)**: In a disaster, no more than 1 minute of financial transaction data may be lost.

---

## 1.6 The 4-Phase Performance Testing Process

Performance testing is an iterative engineering lifecycle:

```
┌─────────────────┐       ┌─────────────────┐       ┌─────────────────┐       ┌─────────────────┐
│   1. PLANNING   │ ────> │   2. DESIGN     │ ────> │  3. EXECUTION   │ ────> │   4. ANALYSIS   │
│ • Define Scope  │       │ • Create Scripts│       │ • Run Scenarios │       │ • Compare NFRs  │
│ • Identify NFRs │       │ • Prepare Data  │       │ • Monitor APM   │       │ • Find Bottlenecks
│ • Choose Tools  │       │ • Setup Env     │       │ • Collect Logs  │       │ • Tuning Advice │
└─────────────────┘       └─────────────────┘       └─────────────────┘       └─────────────────┘
```

1. **Planning Phase**:
   * Identify business-critical user journeys (e.g. Login, Search Accounts, Transfer Funds, View Statement).
   * Agree on specific NFR numbers with product owners.
   * Select test types (Load, Spike, Endurance) and testing tools (JMeter, Gatling).
2. **Design Phase**:
   * Author test scripts with realistic delays (Think Time / Timers) and parameterized test data.
   * Generate test data (e.g., 50,000 pre-existing dummy bank accounts in test DB).
   * Verify test environment readiness.
3. **Execution Phase**:
   * Run baseline tests (1 user), then ramp up to targeted concurrency (e.g. 50, 500, 5000 users).
   * Continuously monitor CPU, memory, database query locks, and network latency.
4. **Analysis Phase**:
   * Compare observed response times and throughput against the NFR baseline.
   * Identify root-cause bottlenecks (e.g., missing database index or thread contention).
   * Share tuning recommendations with the development team and re-test.

---

## 1.7 Test Environment Considerations & Parity

A common mistake in performance testing is running tests against an under-powered developer laptop and trying to guess production behavior. **Environment Parity** is essential.

### Key Factors for Parity
* **Production-like Configuration**: Same OS version, JVM version, connection pool limits, thread limits, and caching policies.
* **Hardware Parity**: Equivalent CPU cores, RAM, and SSD storage speed. If testing on a 50% scale environment, the scale factor must be mathematically modeled.
* **Network Parity**: Same bandwidth limits, latency, SSL termination layers, and firewall configurations.
* **Data Volume Parity**: The test database must contain realistic data volume (e.g. 5 million records, not 50 records) so database query plans match production execution plans.

```
+--------------------------------------------------------------------+
|               FULL STACK ARCHITECTURE UNDER TEST                  |
|                                                                    |
|  [Clients / JMeter]                                                |
|         │ (Simulated 50-5000 Users over HTTPS)                     |
|         ▼                                                          |
|  [Load Balancer / Reverse Proxy] (NGINX / F5 / AWS ALB)            |
|         │                                                          |
|         ├───> [App Server Node 1] ──┐                              |
|         ├───> [App Server Node 2] ──┼───> [Distributed Cache/Redis]|
|         └───> [App Server Node 3] ──┤                              |
|                                     ▼                              |
|                          [Relational Database]                     |
|                           (PostgreSQL / Oracle)                    |
+--------------------------------------------------------------------+
```

---

## 1.8 Common Performance Bottlenecks

Bottlenecks can occur at any tier of the system:

```
                               PERFORMANCE BOTTLENECKS
                                          │
       ┌──────────────────────────────────┼──────────────────────────────────┐
       ▼                                  ▼                                  ▼
[Application Level]             [Infrastructure Level]             [External Dependencies]
• Inefficient algorithms (O(N²)) • CPU / Memory exhaustion          • Slow third-party APIs
• Unindexed slow SQL queries     • Network bandwidth saturated      • Rate limits / 429 throttling
• Memory leaks / excessive GC    • Database connection exhaustion   • Cross-region network latency
• Missing or poor caching        • Misconfigured load balancer      • Unresponsive payment gateway
```

| Layer | Common Cause | Concrete Example | Fix |
| :--- | :--- | :--- | :--- |
| **Application** | Missing Database Index | `SELECT * FROM txns WHERE user_id = '123'` performs a full table scan across 10M rows, taking 4.2 seconds. | Add composite index `idx_txns_user_id (user_id)`. Query drops to 3ms. |
| **Application** | Memory Leak | Objects stored in a static `HashMap` are never cleared; garbage collection pauses the JVM for 10 seconds. | Use bounded caches like Caffeine with LRU eviction. |
| **Infrastructure** | Connection Pool Starvation | Database connection pool is capped at 10 connections, while 200 threads compete for them. | Increase pool size to 50 and use HikariCP. |
| **External** | Third-party Payment Gateway Delay | The core app is fast (20ms), but the credit card partner API takes 3,500ms to respond. | Implement asynchronous processing, webhooks, or circuit breakers (Resilience4j). |

---

## 1.9 Performance Testing Tools Landscape

```
+-------------------+---------------------------------------------+------------------------------------+
| Category          | Tools                                       | Best Suited For                    |
+-------------------+---------------------------------------------+------------------------------------+
| **Open-Source**   | **Apache JMeter**                           | Web apps, REST APIs, JDBC, JMS     |
|                   | **Gatling**                                 | High-throughput async/Scala tests  |
|                   | **k6** (Grafana)                            | Developer-centric JS tests in CI   |
+-------------------+---------------------------------------------+------------------------------------+
| **Commercial**    | **Micro Focus LoadRunner**                  | Large enterprise ERP/Citrix systems|
|                   | **Tricentis NeoLoad**                       | Continuous automated load tests    |
|                   | **BlazeMeter**                              | Scalable cloud execution of JMeter |
+-------------------+---------------------------------------------+------------------------------------+
| **Monitoring/APM**| **Dynatrace, AppDynamics, Prometheus/Grafana| Profiling CPU, memory, traces, SQL |
+-------------------+---------------------------------------------+------------------------------------+
```

---

## 1.10 Performance Testing Best Practices

1. **Shift-Left Performance**: Do not wait until the week before production deployment. Test individual API endpoints as soon as they are implemented.
2. **Use Realistic Scenarios & Think Time**: Real users do not click buttons every 0 milliseconds. Introduce realistic user think time (e.g. 1–3 seconds pause between actions).
3. **Include Positive and Negative Tests**: Test invalid payloads and authentication failures under load to ensure error handlers do not leak memory or thread pools.
4. **Automate in CI/CD**: Run lightweight performance smoke tests (e.g. 20 concurrent users for 2 minutes) on every pull request to catch regressions early.
5. **Establish Baselines**: Maintain historical performance run results. If build #102 responds in 120ms and build #103 responds in 450ms, investigate immediately.

---

# Part 2: Test Automation Frameworks

## 2.1 Module 1: Page Object Model (POM) Pattern

### What is the Page Object Model?
The **Page Object Model (POM)** is a design pattern in test automation where each web page (or distinct UI component) is represented as a separate class in code.
* The **Page Class** holds all the web element locators (`By.id`, `By.xpath`) and the user action methods (`clickSubmit()`, `enterUsername()`).
* The **Test Class** contains only test flow logic, assertions, and test annotations (`@Test`).

```
                              PAGE OBJECT MODEL ARCHITECTURE

           [Pages Package]                                    [Tests Package]
    ┌─────────────────────────────┐                    ┌─────────────────────────────┐
    │    LoginPage.java           │                    │     LoginTest.java          │
    │ ─────────────────────────── │                    │ ─────────────────────────── │
    │ • Locators:                 │ <───────────────── │ • @Test testValidLogin()    │
    │   txt_user, txt_pass, btn_go│    Calls Page      │ • Calls page methods        │
    │ • Methods:                  │      Methods       │ • Asserts expected outcomes │
    │   setUsername(), clickGo()  │                    └──────────────┬──────────────┘
    └─────────────────────────────┘                                   │
                                                                      ▼
                                                       ┌─────────────────────────────┐
                                                       │     TestBase.java           │
                                                       │ ─────────────────────────── │
                                                       │ • @BeforeMethod: initDriver │
                                                       │ • @AfterMethod: quitDriver  │
                                                       └─────────────────────────────┘
```

### Why Use Page Object Model?
1. **Prevents Code Duplication**: If the Login button is used in 20 test cases, you declare its locator only once in `LoginPage.java`.
2. **Easy Maintenance**: If developers change the button ID from `login-btn` to `submit-btn`, you update 1 line in `LoginPage.java` instead of fixing 20 test scripts.
3. **Clean, Readable Test Code**: Test scripts read like human-readable user journeys rather than technical DOM manipulation code.

### Concrete Example: Google Home Page Search

#### 1. The Page Class (`GoogleHomePage.java`)
```java
package pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleHomePage {
    private WebDriver driver;

    // 1. Locators stored as private fields (Encapsulation)
    private By searchBox = By.name("q");
    private By searchBtn = By.name("btnK");

    // 2. Constructor to receive the WebDriver instance
    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
    }

    // 3. User Action Methods
    public void setSearchQuery(String query) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(query);
    }

    public void clickSearchButton() {
        driver.findElement(searchBtn).submit();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
```

#### 2. The Test Class (`TestGoogleHomePage.java`)
```java
package pom.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pom.pages.GoogleHomePage;

public class TestGoogleHomePage {
    private WebDriver driver;
    private GoogleHomePage homePage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com");
        homePage = new GoogleHomePage(driver);
    }

    @Test
    public void validateGoogleSearch() {
        // Step 1: Verify title
        String title = homePage.getPageTitle();
        Assert.assertTrue(title.contains("Google"), "Title verification failed!");

        // Step 2: Type search text
        homePage.setSearchQuery("Standard Chartered Axess Academy");

        // Step 3: Click search
        homePage.clickSearchButton();

        // Step 4: Verify search results page title
        Assert.assertTrue(driver.getTitle().contains("Axess Academy"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
```

---

## 2.2 Module 2: Data-Driven Testing (DDT) Framework with Apache POI

### What is a Data-Driven Framework?
In a **Data-Driven Framework**, test logic is completely separated from test data.
Instead of hardcoding inputs inside test methods, input data (e.g., usernames, passwords, loan amounts) is stored in an external file like an **Excel sheet (`.xlsx`)**, **CSV**, **JSON**, or database table. The test runs in a loop for each row of data.

```
+---------------------+
|   TestData.xlsx     |
+---------------------+
| Name        | City  |
+-------------+-------+
| RohitSharma | Nagpur| ──────┐
| Virat Kohli | Delhi | ──────┼───> [Apache POI Library] ───> [Selenium Test Script]
| MS Dhoni    | Ranchi| ──────┘       (XSSFWorkbook)               (Executes 3 times)
+---------------------+
```

### Apache POI Library Explained
Apache POI is the standard Java open-source library for reading and writing Microsoft Office documents.
* **HSSF (Horrible Spreadsheet Format)**: Handles legacy Excel `.xls` format (Excel 97–2003).
* **XSSF (XML Spreadsheet Format)**: Handles modern Excel `.xlsx` format (Excel 2007+ OpenXML).

### Code Example 1: Reading Data from Excel (`TestData.xlsx`)
```java
package excelExportAndFileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadExcelFile {

    public void readExcel(String filePath, String fileName, String sheetName) throws IOException {
        File file = new File(filePath + File.separator + fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = null;

        // Check file extension to choose XSSF or HSSF
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        if (fileExtensionName.equals(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream); // Modern Excel
        } else if (fileExtensionName.equals(".xls")) {
            workbook = new HSSFWorkbook(inputStream); // Legacy Excel
        }

        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        // Loop over all rows
        for (int i = 0; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            // Loop over all cells in row
            for (int j = 0; j < row.getLastCellNum(); j++) {
                System.out.print(row.getCell(j).getStringCellValue() + " | ");
            }
            System.out.println();
        }
        workbook.close();
        inputStream.close();
    }

    public static void main(String[] args) throws IOException {
        ReadExcelFile obj = new ReadExcelFile();
        obj.readExcel(".", "TestData.xlsx", "Data");
    }
}
```

### Code Example 2: Writing Results Back into Excel
```java
package excelExportAndFileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelFile {

    public void writeExcel(String filePath, String fileName, String sheetName, String[] dataToWrite) throws IOException {
        File file = new File(filePath + File.separator + fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        // Create a new row at the end of the sheet
        Row newRow = sheet.createRow(rowCount + 1);

        // Fill cells with new data
        for (int j = 0; j < dataToWrite.length; j++) {
            Cell cell = newRow.createCell(j);
            cell.setCellValue(dataToWrite[j]);
        }

        inputStream.close();

        // Write output back to disk
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
        System.out.println("Data successfully written to Excel!");
    }

    public static void main(String[] args) throws IOException {
        WriteExcelFile writer = new WriteExcelFile();
        String[] rowData = {"Jasprit Bumrah", "Gujarat"};
        writer.writeExcel(".", "TestData.xlsx", "Data", rowData);
    }
}
```

---

## 2.3 Module 3: TestNG Automation Framework & Annotations

### What is TestNG?
**TestNG** (**Next Generation**) is an enterprise test automation framework inspired by JUnit and NUnit.
It was designed specifically to overcome JUnit's limitations by adding:
1. Flexible test configuration through annotations.
2. Built-in HTML reporting (`test-output/index.html`).
3. Support for parallel execution across multiple threads.
4. Parameterization and Data Providers (`@DataProvider`).
5. Re-running failed tests easily (`testng-failed.xml`).

```
+----------------------------------------------------------------------+
|                     WHY USE TestNG WITH SELENIUM?                    |
+----------------------------------------------------------------------+
| Standard Selenium WebDriver has NO built-in assertion or reporting   |
| engine!                                                              |
| • TestNG provides Assert.assertEquals()                              |
| • TestNG manages browser lifecycle (@BeforeMethod / @AfterMethod)     |
| • TestNG produces colorful HTML reports & execution logs automatically|
| • TestNG enables executing failed tests independently                |
+----------------------------------------------------------------------+
```

### Complete TestNG Execution Lifecycle & Annotations Hierarchy

TestNG executes annotations in a strict hierarchical order:

```
@BeforeSuite        (Runs once before any test in the XML suite)
  └── @BeforeTest   (Runs before any test class inside the <test> tag)
        └── @BeforeClass  (Runs once before the first method of current class)
              └── @BeforeMethod (Runs before EACH @Test method)
                    └── @Test 1 (Actual test case execution)
              └── @AfterMethod  (Runs after EACH @Test method)
              └── @BeforeMethod
                    └── @Test 2 (Actual test case execution)
              └── @AfterMethod
        └── @AfterClass   (Runs once after all methods of current class finish)
  └── @AfterTest    (Runs after all test classes in <test> tag finish)
@AfterSuite         (Runs once after all tests in the entire suite finish)
```

| Annotation | Description | Use Case in Selenium Automation |
| :--- | :--- | :--- |
| `@Test` | Marks a Java method as an automated test case. | Testing login, search, payment, etc. |
| `@BeforeSuite` | Runs once before the entire suite executes. | Setting up database connections, reading global configs. |
| `@AfterSuite` | Runs once after all tests in the suite complete. | Closing DB connections, generating consolidated reports. |
| `@BeforeTest` | Runs before any test class defined within the `<test>` tag in `testng.xml`. | Setting environment parameters (e.g. Chrome vs Firefox). |
| `@AfterTest` | Runs after all test classes inside `<test>` tag have finished. | Flushing extent reports, cleanup. |
| `@BeforeClass` | Runs once before the first test method in the current class. | Initializing page objects or base URLs. |
| `@AfterClass` | Runs once after all test methods in the current class finish. | Resetting class-level variables. |
| `@BeforeMethod`| Runs **before every single** `@Test` method. | Launching a fresh browser window and navigating to URL. |
| `@AfterMethod` | Runs **after every single** `@Test` method. | Taking screenshots on failure and closing browser (`driver.quit()`). |

### Practical Code Example: TestNG Test Class
```java
package firsttestngpackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FirstTestNGFile {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1, description = "Verifies homepage title matches expected string")
    public void verifyHomepageTitle() {
        driver.get("https://www.google.com");
        String actualTitle = driver.getTitle();
        String expectedTitle = "Google";
        Assert.assertEquals(actualTitle, expectedTitle, "Homepage title does not match!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
```

### Viewing TestNG HTML Reports
When you run a TestNG test in Eclipse or IntelliJ or Maven (`mvn test`):
1. TestNG creates a `test-output/` folder in your project root.
2. Refresh the project in the IDE.
3. Open `test-output/index.html` in any browser to view:
   * Total tests run, passed, failed, and skipped.
   * Execution time per test method.
   * Stack traces and assertion messages for any failures.

---

## 2.4 Module 4: Keyword-Driven Framework (KDF)

### What is a Keyword-Driven Framework?
A **Keyword-Driven Framework (KDF)** (also known as Table-Driven Testing) is an automation architecture where test steps are expressed as simple, human-readable keywords (e.g., `openBrowser`, `enterText`, `clickButton`, `verifyText`) in an external table (like an Excel sheet).

It separates **Test Design** (what to test) from **Test Development** (how the automation code runs):
* **Non-technical testers or business analysts** write the test steps in Excel using predefined keywords.
* **Technical automation engineers** write the underlying Java/Selenium code that maps each keyword to browser actions.

```
+-------------------------------------------------------------------------------+
|                       KEYWORD-DRIVEN ARCHITECTURE                             |
|                                                                               |
|  [Excel Test Sheet]                                                           |
|  Step 1: openBrowser | Chrome                                                 |
|  Step 2: navigate    | https://bank.com                                       |
|  Step 3: enterText   | txtUsername | admin                                    |
|  Step 4: click       | btnLogin                                               |
|         │                                                                     |
|         ▼                                                                     |
|  [Driver Script (Execution Engine)]                                           |
|         │ Reads keyword and dispatches action                                 |
|         ▼                                                                     |
|  [Function Library (Java Methods)] <───> [Object Repository (locators.prop)]  |
|  • openBrowser(param)                    • txtUsername = id:username          |
|  • enterText(locator, value)             • btnLogin    = id:submit-btn        |
|         │                                                                     |
|         ▼                                                                     |
|  [Application Under Test (AUT) in Web Browser]                                |
+-------------------------------------------------------------------------------+
```

### The 6 Essential Components of KDF
1. **Excel Sheet**: Stores the test case steps, action keywords, target object names, and input values.
2. **Object Repository**: A `.properties` or `.json` file that stores element locators in key-value pairs (e.g., `txt_username = id:user_login`).
3. **Function Library**: Java class containing reusable Selenium methods (e.g., `clickElement()`, `typeText()`, `selectDropdown()`).
4. **Test Datasheet**: Stores the actual variable data to be inputted into forms.
5. **Test Script**: The logical test definition tying together keywords and data.
6. **Driver Script (Execution Engine)**: The core brain that reads the Excel sheet line-by-line, looks up locators from the Object Repository, invokes the corresponding Java function, and logs pass/fail results.

### Concrete Example: How a Keyword Driver Works in Java
```java
// Simplified execution engine switch
public void executeKeyword(String keyword, String objectName, String testData) {
    By locator = ObjectRepository.getLocator(objectName);

    switch (keyword.toLowerCase()) {
        case "openbrowser":
            ActionKeywords.openBrowser(testData);
            break;
        case "navigate":
            ActionKeywords.navigate(testData);
            break;
        case "entertext":
            ActionKeywords.enterText(locator, testData);
            break;
        case "click":
            ActionKeywords.click(locator);
            break;
        case "verifytext":
            ActionKeywords.verifyText(locator, testData);
            break;
        case "closebrowser":
            ActionKeywords.closeBrowser();
            break;
        default:
            throw new IllegalArgumentException("Unknown keyword: " + keyword);
    }
}
```

### Advantages vs. Disadvantages of Keyword-Driven Testing

| Advantages | Disadvantages |
| :--- | :--- |
| **No Programming Knowledge Needed for Testers**: Manual testers and business analysts can build automated test cases in Excel. | **High Initial Setup Cost**: Writing the initial driver script, reflection engine, and function libraries requires expert Java developers. |
| **High Code Reusability**: An `enterText` method is written once and reused across hundreds of tests. | **Keyword Maintenance Overhead**: Over time, teams create redundant or conflicting keywords, causing framework clutter. |
| **Maintenance Independence**: If an object locator changes, update the Object Repository without modifying test case sheets. | **Complex Debugging**: When an Excel row fails, tracing the issue through the driver reflection engine is harder than debugging direct code. |

---

# Part 3: JMeter Hands-on POC (`jmeterpoc`) — Java Spring Boot Edition

The hands-on exercise from Slide 14 specifies:
> **Handson 1**: Write a load test using JMeter to hit the endpoint `http://localhost:3000/sc_employees` with 50 users. Add response assertions for HTTP 200/201 and a Summary Report listener.

We have built a full enterprise Java Spring Boot project located in:
`F:\scproject\sc_26axessacademy\a01notes\summaryimages\generatedsummaries\a20Week7Testingandclosing\a06performancetestingandautomated\jmeterpoc`

### Project Structure (100% Java & Spring Boot)
```
jmeterpoc/
├── pom.xml                                    <- Maven POM (Spring Boot 3.3.3, Java 21)
├── src/
│   ├── main/
│   │   ├── java/com/sc/jmeterpoc/
│   │   │   ├── ScEmployeeApplication.java     <- @SpringBootApplication main entrypoint
│   │   │   ├── controller/
│   │   │   │   └── EmployeeController.java    <- @RestController for /sc_employees (200 & 201)
│   │   │   ├── model/
│   │   │   │   └── Employee.java              <- Domain entity model
│   │   │   └── service/
│   │   │       └── EmployeeService.java       <- Thread-safe business logic & repository
│   │   └── resources/
│   │       └── application.properties         <- Port 3000 & Tomcat thread pool configuration
│   └── test/
│       └── java/com/sc/jmeterpoc/
│           └── JavaJMeterLoadTestRunner.java  <- Multi-threaded Java 21 load test simulation
├── sc_employees_load_test.jmx                 <- Official Apache JMeter XML Test Plan (50 users)
├── test_employees.csv                         <- Parameterized CSV dataset for JMeter
├── run_jmeter.bat                             <- Non-GUI JMeter runner batch script
├── run_jmeter.ps1                             <- Non-GUI JMeter runner PowerShell script
└── README.md                                  <- Setup & execution guide
```

### 1. Spring Boot REST Controller Code (`EmployeeController.java`)
```java
package com.sc.jmeterpoc.controller;

import com.sc.jmeterpoc.model.Employee;
import com.sc.jmeterpoc.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/sc_employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Handson 1: GET http://localhost:3000/sc_employees -> HTTP 200 OK
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEmployees() {
        List<Employee> list = employeeService.getAllEmployees();
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", "success");
        resp.put("count", list.size());
        resp.put("data", list);
        return ResponseEntity.ok(resp);
    }

    // Handson 1: POST http://localhost:3000/sc_employees -> HTTP 201 CREATED
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody Employee employee) {
        Employee created = employeeService.createEmployee(employee);
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", "created");
        resp.put("message", "Employee record created successfully");
        resp.put("data", created);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
}
```

### 2. Spring Boot Performance Tuning (`application.properties`)
```properties
# Listen on Port 3000 as required by slide 14
server.port=3000

# Embedded Tomcat Worker Thread Pool (Optimized for high-concurrency JMeter load testing)
server.tomcat.threads.max=200
server.tomcat.threads.min-spare=20
server.tomcat.accept-count=100
server.tomcat.connection-timeout=20000

# Actuator Health & Metrics
management.endpoints.web.exposure.include=health,info,metrics
```

### 3. How to Run with Maven & Java
```bash
# Terminal 1: Run the Spring Boot application
mvn spring-boot:run

# Terminal 2: Run the Java multi-threaded load test simulation (50 concurrent users)
mvn test-compile
java -cp "target\test-classes;target\classes" com.sc.jmeterpoc.JavaJMeterLoadTestRunner

# Terminal 2 (Alternative): Run official Apache JMeter in non-GUI CLI mode
.\run_jmeter.bat
```

---

## 3.1 Verified Execution Results (Spring Boot + Apache JMeter 5.6.3)

### 1. Official Apache JMeter 5.6.3 Run Against Spring Boot
Executed with `F:\software\apache-jmeter-5.6.3\bin\jmeter.bat`:

```
Creating summariser <summary>
Created the tree successfully using sc_employees_load_test.jmx
Starting standalone test @ 2026 Sep 3 16:25:56 IST
summary +    132 in 00:00:04 =   36.0/s Avg:    65 Min:    26 Max:   130 Err:     0 (0.00%)
summary +     68 in 00:00:02 =   39.6/s Avg:    60 Min:    28 Max:   115 Err:     0 (0.00%)
summary =    200 in 00:00:05 =   37.1/s Avg:    63 Min:    26 Max:   130 Err:     0 (0.00%)
... end of run
```

### 2. JMeter Statistics Dashboard Metrics (`html_report/statistics.json`)
| Transaction Label | Samples | Failures | Error % | Average (ms) | Min (ms) | Max (ms) | 90th % (ms) | 95th % (ms) | Throughput (TPS) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **GET /sc_employees** | 100 | 0 | 0.00% | 58.60 ms | 26.0 ms | 130.0 ms | 82.90 ms | 92.70 ms | 21.35 /sec |
| **POST /sc_employees**| 100 | 0 | 0.00% | 69.06 ms | 36.0 ms | 118.0 ms | 99.80 ms | 103.95 ms | 21.63 /sec |
| **TOTAL** | **200** | **0** | **0.00%** | **63.83 ms** | **26.0 ms** | **130.0 ms** | **92.90 ms** | **102.95 ms** | **42.08 /sec** |

### 3. Verification Checklist:
* [x] **50 Users Concurrency**: Thread group executed 50 threads smoothly across a 5s ramp-up.
* [x] **Response Assertions**: Validated HTTP 200 for all GET requests and HTTP 201 for all POST requests (0 errors).
* [x] **Duration Assertion**: Max latency of 130ms is well under the 2,000ms threshold (100% compliant).
* [x] **Listeners Generated**: Summary report CSV (`results_summary.csv`), JTL execution logs (`results.jtl`), and HTML visual dashboard (`html_report/index.html`).

