# Software Testing & Fundamentals: Financial Institution Practical Guide

> **Domain Context:** Standard Chartered / Banking & Financial Services Quality Engineering  
> **Target Audience:** Test Engineers, Business Analysts, System Architects, and Software Developers in Banking Systems  
> **Source Material:** Standard Chartered Axess Academy - Week 7 Testing & Closing (Module 01 - 06)

---

## Executive Summary & Overview

Software in financial institutions powers critical infrastructure: core banking ledgers, real-time payment gateways (SWIFT, FAST, FedWire), credit decisioning engines, wealth management platforms, and Anti-Money Laundering (AML) compliance scanners. A single defect in a banking system can lead to multi-million-dollar financial losses, severe regulatory penalties (e.g., PRA, FCA, MAS, Fed), or reputational damage.

This document translates fundamental software testing concepts into practical, real-life financial engineering scenarios and workflows.

---

## Table of Contents

1. [Module 01: Testing Overview & Fundamentals](#module-01-testing-overview--fundamentals)
   - 1.1 Classifications of Software Testing
   - 1.2 Testing vs. Debugging
   - 1.3 Testing's Contribution to Financial Success
   - 1.4 Quality Assurance (QA) vs. Quality Control (QC)
   - 1.5 Error, Defect (Fault/Bug), and Failure
   - 1.6 Root Causes of Defects in Banking Systems
   - 1.7 The 7-Phase Financial Test Process
   - 1.8 Psychology & Mindsets: Developer vs. Tester
   - 1.9 PDCA Cycle and the Workbench Model
   - 1.10 Why We Test & Limitations of Testing
2. [Module 02: Testing Levels in Banking Architectures](#module-02-testing-levels-in-banking-architectures)
   - 2.1 Component / Unit Testing
   - 2.2 Integration Testing & Approaches
   - 2.3 System Testing
   - 2.4 User Acceptance Testing (UAT)
3. [Module 03: Testing Types in Financial Systems](#module-03-testing-types-in-financial-systems)
   - 3.1 Functional Testing (Black-Box)
   - 3.2 Non-Functional Testing (NFRs in Banking)
4. [Module 04: Test Scenario & Test Case Preparation](#module-04-test-scenario--test-case-preparation)
   - 4.1 Test Scenarios vs. Test Cases
   - 4.2 Test Case Characteristics & Formats
   - 4.3 Banking Decision Matrix Example
5. [Module 05: Test Execution & Governance](#module-05-test-execution--governance)
   - 5.1 Objectives & Execution Flow
   - 5.2 Roles & Responsibility Matrix (RACI)
6. [Module 06: Defect Management & Life Cycle](#module-06-defect-management--life-cycle)
   - 6.1 Origins of Banking Defects
   - 6.2 The Defect Discovery Process
   - 6.3 Severity vs. Priority in Financial Context
   - 6.4 End-to-End Defect Lifecycle

---

## Module 01: Testing Overview & Fundamentals

### 1.1 Classifications of Software Testing

Software testing is broadly categorized into **Static Testing** and **Dynamic Testing**.

```
                           ┌─────────────────────────┐
                           │    Software Testing     │
                           └────────────┬────────────┘
                                        │
           ┌────────────────────────────┴────────────────────────────┐
           ▼                                                         ▼
┌─────────────────────┐                                   ┌─────────────────────┐
│   Static Testing    │                                   │   Dynamic Testing   │
│ (Reviews & Analysis)│                                   │ (Levels of Testing) │
└──────────┬──────────┘                                   └──────────┬──────────┘
           │                                                         │
           ├─ Informal Review                                        ├─ Unit Testing
           ├─ Walkthrough                                            ├─ Integration Testing
           ├─ Technical Review                                       ├─ System Testing
           └─ Inspection                                             └─ UAT & Non-Functional
```

#### Real-Life Financial Scenarios:

* **Static Testing (Reviews & Static Analysis):**
  * *Example:* Reviewing the **Business Requirement Document (BRD)** for a new Interest Calculation Service before a single line of code is written. Analysts check if compound interest formulas handle leap years, daylight savings time changes, and negative interest rates correctly according to central bank directives.
  * *Types of Reviews:*
    * **Informal Review:** A developer shares a draft diagram of a payment gateway API interface with a senior peer on Teams.
    * **Walkthrough:** A Business Analyst (BA) walks developers and testers through the user stories for a new Mortgage Origination Workflow.
    * **Technical Review:** Enterprise Security Architects and Senior Lead Developers evaluate an OAuth 2.0 Mutual TLS (mTLS) implementation for Open Banking APIs.
    * **Inspection:** A formal, strict inspection of a C++ core ledger memory management module with defined roles (Moderator, Author, Inspector, Scribe) and checklist criteria.

* **Dynamic Testing:**
  * *Example:* Executing automated scripts against a staging core banking environment by submitting \$500 wire transfer requests and verifying database balances, audit logging, and debit/credit ledger entries.

---

### 1.2 Testing vs. Debugging

| Dimension | Testing | Debugging |
| :--- | :--- | :--- |
| **Primary Goal** | Demonstrate failure or find defects by executing the application against expected behavior. | Locate the root cause of a defect in the code/architecture and fix it. |
| **Primary Owner** | QA Engineers, Test Automation Engineers, Business Testers | Software Developers, Systems Engineers |
| **Financial Example** | A tester attempts to transfer \$10,000 from a savings account with only \$2,000 balance. The system incorrectly allows the transfer, resulting in a **negative balance failure**. The tester logs the defect. | The developer inspects Java application logs, attaches a debugger to the microservice, traces `AccountService.java`, and finds that the overdraft validation `if (balance < amount)` condition was commented out. The developer fixes the code. |

---

### 1.3 Testing's Contribution to Financial Success

Early and continuous testing directly prevents financial loss, regulatory fines, and system outages:

1. **Requirements Review:** Catching an ambiguous rule in an international trade finance BRD (e.g., "apply FX rate at transaction time" vs. "apply FX rate at settlement time") during story refinement prevents multi-million dollar reconciliation discrepancies later.
2. **System Design Collaboration:** Testers working with solution architects ensure high availability (HA) and active-active failover mechanisms are built into the payment gateway design.
3. **Developer Collaboration:** QA pairing with developers to write unit and contract tests for microservices (e.g., Pact testing between Mobile App and Account Microservice).
4. **Validation Prior to Release:** Comprehensive regression suites prevent outages like transaction duplicate debits during peak shopping holidays.

---

### 1.4 Quality Assurance (QA) vs. Quality Control (QC)

* **Quality Assurance (Process-Focused - Defect Prevention):**
  * Establishing governance, coding standards, peer-review mandates, security compliance policies (PCI-DSS, SOC 2), and CI/CD quality gates.
  * *Example:* Defining a process where no financial transaction code can be merged into `main` without 85%+ unit test coverage and two senior approvals.
* **Quality Control (Product-Focused - Defect Detection):**
  * Executing actual tests on the built software product to verify it matches requirements.
  * *Example:* Executing 500 test cases on the credit card statement generation module prior to deployment to verify calculations and PDF formatting.

---

### 1.5 Error, Defect (Fault/Bug), and Failure

Understanding the distinction is vital when logging financial incidents:

```
[Human Error]  ──(creates)──>  [Defect in Code]  ──(executed in prod)──>  [System Failure]
```

* **Error (Mistake):** A human action that produces an incorrect result.
  * *Example:* A developer misreads the regulatory guidelines for daily transfer limits, thinking the limit is \$50,000 instead of \$10,000.
* **Fault / Defect / Bug:** The manifestation of the human error within software code or documentation.
  * *Example:* Hardcoded rule in `TransferValidator.java`: `public static final double DAILY_LIMIT = 50000.00;`.
* **Failure:** Deviation of the software from its expected delivery or service during execution.
  * *Example:* A customer successfully transfers \$45,000 in a single day through mobile banking, violating central bank compliance mandates and triggering a regulatory audit.

---

### 1.6 Root Causes of Defects in Banking Systems

```
                              ┌──────────────────────────────────┐
                              │ Causes of Software Defects       │
                              └────────────────┬─────────────────┘
                                               │
   ┌───────────────────────┬───────────────────┼───────────────────┬───────────────────────┐
   ▼                       ▼                   ▼                   ▼                       ▼
Time Pressure       Human Error          Inexperience       Miscommunication      System Complexity
(Regulatory Cutoff) (Tired Developer)   (New Banking API)   (BA to Developer)   (Mainframe + Microservice)
```

1. **Time Pressure:** Strict regulatory enforcement dates (e.g., ISO 20022 payment format migration deadline forced by the central bank).
2. **Human Error Prone Nature:** Developers working late-night shifts during production release windows.
3. **Inexperienced Project Participants:** Hiring engineers unfamiliar with domain-specific protocols like SWIFT MT/MX messages or FIX protocol for trading systems.
4. **Miscommunication:** Business analysts using vague banking jargon that developers misinterpret.
5. **Architectural Complexity:** Modern frontend React apps connecting via API Gateways to legacy IBM Mainframe COBOL backend ledgers.
6. **Interface Misunderstandings:** Differing expectations between internal core banking services and third-party credit bureaus (e.g., Experian/Equifax API response field formats).

---

### 1.7 The 7-Phase Financial Test Process

```
┌──────────────┐     ┌─────────────────────┐     ┌──────────────┐     ┌──────────────┐
│  1. Test     │ ──> │ 2. Monitoring &     │ ──> │  3. Test     │ ──> │  4. Test     │
│   Planning   │     │      Control        │     │   Analysis   │     │    Design    │
└──────────────┘     └─────────────────────┘     └──────────────┘     └──────────────┘
                                                                             │
┌──────────────┐     ┌─────────────────────┐     ┌──────────────┐            │
│  7. Test     │ <── │ 6. Test Execution & │ <── │  5. Test     │ <──────────┘
│  Completion  │     │ Defect Management   │     │ Implementation│
└──────────────┘     └─────────────────────┘     └──────────────┘
```

1. **Test Planning:** Defining test strategy, scope, resources, risk analysis, and test environments for the retail banking release.
2. **Test Monitoring and Control:** Tracking test execution metrics (e.g., 85% pass rate achieved, 15 critical defects blocking release) and adjusting timelines.
3. **Test Analysis:** Analyzing user stories to identify test conditions (e.g., "What conditions trigger an automated loan rejection?").
4. **Test Design:** Creating high-level test scenarios and detailed test cases with test data (mock account numbers, test credit card numbers).
5. **Test Implementation:** Setting up test environments, creating automated test scripts (Selenium/Playwright/REST Assured), and preparing synthetic banking test data.
6. **Test Execution:** Running manual and automated scripts, logging actual vs. expected results, and filing defect tickets.
7. **Test Completion:** Reviewing exit criteria, archiving test assets, generating test summary reports for risk & compliance sign-off.

---

### 1.8 Psychology & Mindsets: Developer vs. Tester

Successful financial software delivery requires balancing two distinct mindsets:

* **Confirmation Bias in Developers:**
  * Developers build systems to *work*. They naturally assume positive flows ("The happy path works; funds transfer successfully"). They have an inherent bias toward proving their solution is correct.
* **Professional Pessimism in Testers:**
  * Testers look for how systems *fail*. They operate with curiosity, attention to detail, and constructive skepticism ("What if the network drops while the debit succeeds but the credit fails? Does the ledger balance?").

#### Communication Guidelines for Testers:
1. **Collaborate, don't battle:** Position testing as a shared goal to protect the bank's reputation.
2. **Neutral, fact-focused defect logging:** State *"System allowed transfer exceeding balance by \$5,000 on Account #1234"* instead of *"Developer forgot balance check again"*.
3. **Understand developer constraints:** Acknowledge complex legacy mainframe integration hurdles.

---

### 1.9 PDCA Cycle and the Workbench Model

Processes ensure repeatable quality across software releases.

```
       ┌────────────────────────┐
       │   P - PLAN             │
       │   Devise test strategy │
       └───────────┬────────────┘
                   │
  ┌────────────────┴────────────────┐
  ▼                                 ▼
┌────────────────────────┐        ┌────────────────────────┐
│   A - ACT              │        │   D - DO               │
│   Refine testing based │        │   Execute tests &      │
│   on defect metrics    │        │   log defects          │
└────────────────────────┘        └────────────────────────┘
  ▲                                 │
  └────────────────┬────────────────┘
                   │
       ┌───────────┴────────────┐
       │   C - CHECK            │
       │   Evaluate test results│
       │   against exit criteria│
       └───────────┴────────────┘
```

* **PDCA (Plan-Do-Check-Act):** Continuous improvement cycle applied to testing sprints.
* **Workbench Model:** A standard mechanism to process work inputs through quality checkpoints:
  * *Input:* Business Requirements & User Stories.
  * *Work Activity:* Developing test cases and code.
  * *Quality Check (Workbench Standard):* Code reviews, static analysis, test execution.
  * *Output:* Production-ready financial application build.

---

### 1.10 Why We Test & Limitations of Testing

#### Why We Test:
* Build stakeholder & customer confidence in the digital banking platform.
* Identify operational weaknesses before fraudsters exploit them.
* Validate regulatory compliance (GDPR, PCI-DSS, PSD2 Open Banking).

#### Limitations of Software Testing:
1. **Testing cannot be exhaustive:** You cannot test every permutation of customer balance, transaction type, currency pair, and timestamp. Risk-based testing must prioritize high-value flows.
2. **Selective testing cannot catch all bugs:** Edge cases may survive to production.
3. **Testing itself can contain bugs:** Flaky automated test scripts or incorrect test data setup can yield false positives/negatives.
4. **Testing does not prevent defects by itself:** Testing *detects* defects; early reviews and proper architecture *prevent* them.

---

## Module 02: Testing Levels in Banking Architectures

Modern financial architectures require structured testing across four primary levels:

```
┌─────────────────────────────────────────────────────────────────┐
│                 User Acceptance Testing (UAT)                   │
│        (Business Users / Operations / Compliance Teams)          │
└────────────────────────────────┬────────────────────────────────┘
                                 │
┌────────────────────────────────┴────────────────────────────────┐
│                         System Testing                          │
│               (End-to-End Functional & Non-Functional)          │
└────────────────────────────────┬────────────────────────────────┘
                                 │
┌────────────────────────────────┴────────────────────────────────┐
│                       Integration Testing  ( attempt this)                     │
│              (APIs, Microservices, Mainframe Middleware)        │
└────────────────────────────────┬────────────────────────────────┘
                                 │
┌────────────────────────────────┴────────────────────────────────┐
│                     Component / Unit Testing    (attemp this)                │
│                (Isolated Classes, Functions, Methods)           │
└─────────────────────────────────────────────────────────────────┘
```

### 2.1 Component / Unit Testing

* **Definition:** Testing individual software components/units in isolation, typically performed by developers using frameworks like JUnit, NUnit, or PyTest.
* **Financial Example:** Testing a standalone Java function calculating daily compound interest on a fixed deposit account:

```java
@Test
void testCalculateInterest_StandardSavingsAccount() {
    InterestCalculator calc = new InterestCalculator();
    double principal = 10000.00;
    double annualRate = 0.05; // 5%
    int days = 30;
    
    double expectedInterest = 41.09; // (10000 * 0.05 * 30) / 365
    double actualInterest = calc.compute(principal, annualRate, days);
    
    assertEquals(expectedInterest, actualInterest, 0.01);
}
```

* **Stubs & Drivers:**
  * If the interest calculator relies on a live Database to pull customer interest tiers, a **Mock/Stub** is used to return dummy rates without connecting to the real DB.

---

### 2.2 Integration Testing & Approaches

Testing the interfaces and interaction between integrated components/microservices.

#### Integration Approaches in Banking:

1. **Big Bang Integration:**
   * All microservices (Account, Transfer, Notification, Ledger, Audit) are combined at once and tested together.
   * *Risk:* Extremely high. If a fund transfer fails, isolating whether the cause lies in the API gateway, payment service, database, or notification queue is difficult.
2. **Incremental Integration:**
   * **Top-Down:** Start from the Mobile Banking UI down through API Gateways to backend services, using stubs for unbuilt backend databases.
   * **Bottom-Up:** Start from the Core Database & Mainframe connectors up to the API layer, using drivers to simulate UI calls.
   * **Mixed / Hybrid (Sandwich):** Combining top-down for frontend modules and bottom-up for core transaction ledgers.

#### Financial Example:
Testing the integration between the **Payment Service** and the **Anti-Money Laundering (AML) Sanction Screening Service**. QA verifies that when the Payment Service sends a transaction payload, the AML service receives the payload, queries the OFAC sanctions database, and returns `APPROVED` or `FLAGGED_SUSPICIOUS` within 200ms.

---

### 2.3 System Testing

Testing the overall behavior of the complete, fully integrated financial system based on specified business requirements and technical specifications.

* **Key Focus Areas:**
  * End-to-end business workflows.
  * System resource utilization (CPU, memory, database connection pool limits).
  * Operating system and infrastructure compatibility (Cloud AWS/Azure vs On-Prem Mainframe).

#### Financial Example:
Testing a full **Loan Approval & Disbursement Journey**:
1. Applicant fills out loan application on retail Web Portal.
2. System calls Credit Bureau API to fetch credit score.
3. Automated Decision Engine evaluates score & income.
4. Core Banking ledger opens new loan account.
5. Treasury service transfers loan funds to customer checking account.
6. SMS & Email engine dispatches approval notification with repayment schedule.

---

### 2.4 User Acceptance Testing (UAT)

Determines whether the application satisfies business acceptance criteria and empowers business leadership to decide on deployment readiness.

* **Primary Objective:** Validate fitness for business purpose and evaluate operational risks, **not** primarily to find low-level technical bugs.
* **Environment:** Executed in a dedicated UAT environment pre-loaded with production-like anonymized data.
* **Participants:** Business Operations Officers, Branch Managers, Treasury Traders, Compliance Officers.

#### Key Questions Answered by UAT Sign-Off:
* *"Does the system meet regulatory compliance requirements for trade reporting?"*
* *"Can branch staff process customer deposit requests without operational bottlenecks?"*
* *"Is the residual business risk acceptable for launch?"*

---

## Module 03: Testing Types in Financial Systems

```
                              ┌──────────────────────────────────┐
                              │          Testing Types           │
                              └────────────────┬─────────────────┘
                                               │
           ┌───────────────────────────────────┴───────────────────────────────────┐
           ▼                                                                       ▼
┌───────────────────────────────────────┐                               ┌───────────────────────────────────────┐
│          Functional Testing           │                               │        Non-Functional Testing         │
│          ("What the system does")     │                               │      ("How well the system performs")  │
├───────────────────────────────────────┤                               ├───────────────────────────────────────┤
│ • Account Balance Query               │                               │ • Performance & Load Testing          │
│ • Funds Transfer                      │                               │ • Security & Penetration Testing      │
│ • Bill Payment                        │                               │ • High Availability & Failover        │
│ • Interest Calculation                │                               │ • Usability & Accessibility           │
└───────────────────────────────────────┘                               └───────────────────────────────────────┘
```

### 3.1 Functional Testing (Black-Box Testing)

Validates specific functional behavior against functional requirements, user stories, and use cases without inspecting underlying source code.

* **Financial Use Case Examples:**
  * **Daily ATM Withdrawal Limit:** Verifying that a customer cannot withdraw more than \$1,000 cash in a single calendar day across multiple ATM channels.
  * **Overdraft Prevention:** Verifying that a debit card purchase of \$250 is declined if the available account balance is \$200 and overdraft protection is disabled.
  * **Currency Conversion:** Verifying that converting \$1,000 USD to EUR applies the real-time spot FX rate plus the bank's 1.5% margin accurately.

---

### 3.2 Non-Functional Testing (NFRs in Banking)

Validates quality characteristics, performance metrics, and operational readiness.

| Non-Functional Type | Financial Definition | Real-Life Banking Example |
| :--- | :--- | :--- |
| **Performance / Load** | Evaluates responsiveness and stability under extreme transaction volumes. | Simulating 20,000 concurrent users accessing the Mobile Banking App at 9:00 AM on payday to ensure API response times stay below 1.5 seconds. |
| **Security / Pen Testing** | Verifies defense against unauthorized access, data breaches, and exploits. | Testing API endpoints against OWASP Top 10 vulnerabilities (SQL Injection, Broken Object Level Authorization) to prevent hackers from viewing other customers' account balances. |
| **Reliability & Availability** | Ensures continuous operation without unplanned outages. | Testing active-passive data center failover. If Primary Data Center A goes down, Data Center B takes over core ledger transactions within 30 seconds with 0 data loss (Zero RPO). |
| **Interoperability** | Measures ability to exchange data with external banking networks. | Validating mTLS authentication and JSON message exchange between the bank and Visa/Mastercard credit card clearing networks. |
| **Usability & Accessibility** | Evaluates ease of use and compliance with accessibility standards (WCAG). | Ensuring color contrast, screen reader compatibility, and font size scaling work for visually impaired users opening a bank account online. |
| **Recovery / Disaster Recovery** | Verifies system recovery capability following hardware or network crashes. | Pulling power from a primary database node during peak batch processing and verifying that transaction logs roll back gracefully without leaving pending transfers in an inconsistent state. |

---

## Module 04: Test Scenario & Test Case Preparation

### 4.1 Test Scenarios vs. Test Cases

* **Test Scenario:** A high-level statement covering a end-to-end functionality to be tested. It answers *what* to test.
* **Test Case:** A detailed set of steps, prerequisites, inputs, execution conditions, and expected results. It answers *how* to test.

```
[Test Scenario]
  └─ "Verify Customer Cross-Border Wire Transfer via Mobile App"
        │
        ├─ [Test Case 1]: Valid SWIFT Transfer within daily limit (Pass Flow)
        ├─ [Test Case 2]: Transfer exceeding daily limit (Rejection Flow)
        ├─ [Test Case 3]: Transfer to sanctioned beneficiary country (AML Hold Flow)
        └─ [Test Case 4]: Transfer with insufficient funds (Validation Error Flow)
```

---

### 4.2 Test Case Characteristics & Formats

A robust financial test case must demonstrate:
1. **Coverage:** Ensures all business rules and edge cases are validated.
2. **Modularity:** Avoids redundant testing across multiple test cases.
3. **Traceability:** Linked directly to Jira User Stories or Regulatory Requirement IDs (e.g., Requirement `REQ-PAY-042`).

#### Step-by-Step Action Format Example:

**Test Case ID:** `TC-PAY-1042`  
**Requirement Reference:** `REQ-PAY-042` (International Fund Transfer)  
**Title:** Verify International SWIFT Transfer Rejection on Insufficient Funds  

| Step # | Action Instruction | Test Data / Input | Expected Result | Pass / Fail |
| :---: | :--- | :--- | :--- | :---: |
| **1** | Log into Mobile Banking App | Username: `test_user_01`<br>Password: `Pass@1234` | Dashboard loaded; Available Balance displays **\$150.00 USD**. | Pass |
| **2** | Navigate to 'International Transfers' screen | N/A | International Transfer input form displayed. | Pass |
| **3** | Enter Beneficiary Details & Amount | Beneficiary: `Global Corp`<br>IBAN: `DE89370400440532013000`<br>SWIFT: `DBEKDEFF`<br>Amount: **\$500.00 USD** | Amount entered; fee estimation calculated as \$15.00 USD. | Pass |
| **4** | Tap 'Submit Transfer' and authorize with OTP | OTP: `654321` | Transaction rejected with error message: *"Insufficient available funds for this transfer."* Account balance remains \$150.00 USD. No SWIFT payload generated. | Pass |

---

### 4.3 Banking Decision Matrix Example

When testing complex financial rules (e.g., Credit Card Approval), a **Decision Table Matrix** format is preferred:

| Condition / Input Rule | Case 1 | Case 2 | Case 3 | Case 4 |
| :--- | :---: | :---: | :---: | :---: |
| **Credit Score > 750** | Yes | Yes | No | No |
| **Annual Income > \$60,000** | Yes | No | Yes | No |
| **Existing Default History?** | No | No | No | Yes |
| **Expected Outcome** | **Instant Approval (Platinum Card)** | **Standard Approval (Gold Card)** | **Manual Underwriter Review** | **Instant Decline** |

---

## Module 05: Test Execution & Governance

### 5.1 Objectives & Execution Flow

Test Execution takes place after Test Planning, Test Design, and Test Environment Setup are completed.

```
                                  ┌───────────────────────────┐
                                  │   Test Execution Phase    │
                                  └─────────────┬─────────────┘
                                                │
           ┌────────────────────────────────────┴────────────────────────────────────┐
           ▼                                                                         ▼
┌──────────────────────────────────────┐                                  ┌──────────────────────────────────────┐
│       Expected vs. Actual Match      │                                  │        Defect Identification         │
├──────────────────────────────────────┤                                  ├──────────────────────────────────────┤
│ Confirm system behaves as expected   │                                  │ Log misconfigurations, DB errors,    │
│ (e.g., Balance updates accurately).  │                                  │ and API failures for dev resolution. │
└──────────────────────────────────────┘                                  └──────────────────────────────────────┘
```

#### Primary Tester Responsibilities During Execution:
1. Execute test scripts according to the planned test schedule.
2. Record actual results and capture evidentiary artifacts (screen captures, API payloads, DB logs).
3. Log defects immediately in tracking tools (Jira / Azure DevOps) with clear steps to reproduce.
4. Collaborate with developers during bug triage to expedite defect fixes.
5. Retest fixed defects and execute regression suites to verify no side effects were introduced.

---

### 5.2 Roles & Responsibility Matrix (RACI)

Different organizational roles take ownership at different testing levels:

| Responsible Group | Unit Testing | Integration Testing | System Testing | User Acceptance Testing (UAT) |
| :--- | :---: | :---: | :---: | :---: |
| **Software Developers** | **Lead (X)** | **Shared (X)** | Support | - |
| **QA / Test Engineers** | - | **Shared (X)** | **Lead (X)** | Support |
| **Business Analysts / End Users** | - | - | Support | **Lead (X)** |

---

## Module 06: Defect Management & Life Cycle

### 6.1 Origins of Banking Defects

The majority of software defects originate in the **Requirements Definition Phase** and the **System Architecture Design Phase**.

```
┌─────────────────────────────────────────────────────────┐
│ Requirements Definition (Ambiguous Specs, Vague Rules)  │ ──> ~55% of Defects
└────────────────────────────┬────────────────────────────┘
                             │
┌────────────────────────────┴────────────────────────────┐
│ System & Software Design (Flawed Architecture/DB Schema)│ ──> ~25% of Defects
└────────────────────────────┬────────────────────────────┘
                             │
┌────────────────────────────┴────────────────────────────┐
│ Coding & Implementation (Logic Errors, Syntax Mistakes) │ ──> ~15% of Defects
└────────────────────────────┬────────────────────────────┘
                             │
┌────────────────────────────┴────────────────────────────┐
│ Environment, Deployment & Configuration Issues          │ ──> ~5% of Defects
└─────────────────────────────────────────────────────────┘
```

#### Why Banking Requirements Suffer Ambiguity:
* Complex domain logic spanning multiple country-specific regulations.
* Ambiguous natural language.
  * *Example Requirement:* "Charge a foreign transaction fee of 2% on cross-border payments."
  * *Unclear Details:* Is the 2% fee calculated on the original foreign currency amount or the converted base currency amount? Is the fee subject to tax? Does a minimum fee of \$1 apply?

---

### 6.2 The Defect Discovery Process

Defect discovery flows through four key steps:

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│  1. Find     │ ──> │  2. Record   │ ──> │  3. Report   │ ──> │ 4. Acknowledge│
│    Defect    │     │    Defect    │     │    Defect    │     │    Defect    │
└──────────────┘     └──────────────┘     └──────────────┘     └──────────────┘
```

1. **Find Defect:** Identified via static reviews (BRD inspection), dynamic testing (script execution), or operational failures in staging/production.
2. **Record Defect:** Logging exact details, steps to reproduce, environment state, and log extracts.
3. **Report Defect:** Assigning the defect to the appropriate development team lead via Jira.
4. **Acknowledge Defect:** Developers review and accept responsibility for investigating and fixing the issue.

---

### 6.3 Severity vs. Priority in Financial Context

* **Severity:** Represents the technical impact of the defect on system functionality.
* **Priority:** Represents the business urgency of fixing the defect based on commercial or operational deadlines.

```
                  ┌──────────────────────────────────────────────────────────────────────────┐
                  │                             SEVERITY (Impact)                            │
                  │                   HIGH                                   LOW             │
┌─────────────────┼───────────────────────────────────────┬──────────────────────────────────┤
│           HIGH  │ • Core Banking Balance Calculation    │ • Bank Logo misaligned on Login  │
│                 │   Error crediting \$1M to accounts    │   page on day of Public PR Launch│
│ PRIORITY        │   (Fix Immediately)                   │   (Fix Urgently for Brand Image) │
│ (Business       ├───────────────────────────────────────┼──────────────────────────────────┤
│  Urgency)  LOW  │ • EOD Batch Job crash for accounts    │ • Typo in footer link of Privacy │
│                 │   closed 10+ years ago (Rare Edge)   │   Policy page                    │
│                 │   (Fix in Next Scheduled Sprint)      │   (Fix in Backlog Low Priority)  │
└─────────────────┴───────────────────────────────────────┴──────────────────────────────────┘
```

#### Real-Life Banking Quadrant Examples:

1. **High Severity / High Priority:**
   * *Issue:* The core transaction processing engine crashes whenever a customer attempts a bill payment, causing transaction timeouts and potential double-debits.
   * *Impact:* System unuseable, customer money at risk. Must be hot-fixed immediately.
2. **High Severity / Low Priority:**
   * *Issue:* Interest calculation yields an arithmetic overflow crash when processing accounts with balances exceeding \$10 Billion.
   * *Impact:* High technical severity (system crash), but low priority because no retail bank customer holds \$10 Billion in a standard savings account. Can be scheduled for a future sprint.
3. **Low Severity / High Priority:**
   * *Issue:* The bank's main corporate brand logo appears distorted or broken on the mobile app home screen right before a major national marketing launch.
   * *Impact:* Zero impact on backend money transfers (low technical severity), but extreme priority for corporate marketing and public relations.
4. **Low Severity / Low Priority:**
   * *Issue:* A minor grammatical typo in the FAQ section ("Withdrawl" instead of "Withdrawal").
   * *Impact:* Minimal business impact. Add to general maintenance backlog.

---

### 6.4 End-to-End Defect Lifecycle

```
 ┌──────────┐      Assign      ┌──────────┐     Fix Code      ┌────────────┐
 │   NEW    │ ───────────────> │  OPEN    │ ────────────────> │   FIXED    │
 └──────────┘                  └──────────┘                   └─────┬──────┘
       │                             │                              │
       │ Duplicate /                 │ Cannot                       │ Retest
       │ Invalid                     │ Reproduce                    │ Verification
       ▼                             ▼                              ▼
 ┌──────────┐                  ┌──────────┐                   ┌────────────┐
 │ REJECTED │                  │ DEFERRED │                   │ RETESTING  │
 └──────────┘                  └──────────┘                   └─────┬──────┘
                                                                    │
                                                 ┌──────────────────┴──────────────────┐
                                                 ▼                                     ▼
                                          [Retest Pass]                         [Retest Fail]
                                                 │                                     │
                                                 ▼                                     ▼
                                          ┌────────────┐                        ┌────────────┐
                                          │   CLOSED   │                        │ RE-OPENED  │
                                          └────────────┘                        └────────────┘
```

#### Step-by-Step Status Flow in Banking Jira Board:
1. **New:** QA logs defect `DEF-8901` - *"Wire transfer API fails with HTTP 500 when transaction memo contains special characters (&, %, #)"*.
2. **Open:** Lead Developer reviews log files, confirms defect, and assigns it to Backend Engineer.
3. **Fixed:** Developer sanitizes input strings in `WireTransferController.java`, updates unit tests, and deploys fix to Staging.
4. **Retesting:** QA Engineer re-runs test cases using payloads with special characters.
5. **Closed:** Retest passes cleanly. Verification evidence is attached to `DEF-8901` and status is updated to Closed.
6. **Re-Opened (Alternative Path):** If the retest fails or breaks another parameter (e.g., standard text transfers fail), QA transitions status back to Re-Opened with updated failure logs.

---

## Conclusion & Summary Checklist for Financial QA Engineers

To ensure high-quality software delivery in banking environment:

- [x] **Shift Left:** Engage testers early during BRD static reviews and story refinements to prevent defects before coding begins.
- [x] **Enforce Clear Definitions:** Differentiate between human errors, code defects, and runtime failures in incident reports.
- [x] **Balance Mindsets:** Combine developer innovation with tester professional pessimism.
- [x] **Tier Testing Levels:** Rigorously execute Unit, Integration, System, and UAT levels with appropriate stubs, drivers, and real-world environments.
- [x] **Cover Functional & Non-Functional:** Validate not just *what* the system does (transfers, balances), but *how well* it does it (security, scalability, resilience).
- [x] **Maintain Traceability:** Map every test case directly back to business requirements and regulatory compliance mandates.
- [x] **Apply Severity & Priority:** Categorize defects objectively based on technical risk and business impact to streamline remediation.

---
*Document compiled for Standard Chartered Axess Academy - Week 7 Testing & Closing.*
