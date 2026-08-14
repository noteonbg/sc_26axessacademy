# Cybersecurity 101: Student Essential Guide for Financial Services

This summary document translates core cybersecurity principles into real-world banking and financial engineering requirements. Every developer, analyst, or engineer working in a financial institution (e.g., Standard Chartered, J.P. Morgan, HSBC) must understand these baseline concepts to protect financial systems, customer wealth, and institutional trust.

---

## 📌 1. Why Cybersecurity is Job #1 in Financial Services

In financial engineering, **code equals money**. A software bug in a retail store might cause a temporary cart error, but a bug in a bank can result in millions of dollars lost in seconds, regulatory fines, or complete loss of banking license.

- **Ransomware Impact**: In 2023 alone, ransomware attacks caused over **$1.1 Billion** in losses globally.
- **Data Exfiltration & Dark Web**: Stolen banking credentials, credit card details, and PII are traded daily on dark web marketplaces.

---

## 🏛️ 2. Core Security Foundations (The Banking Realities)

### A. The CIA Triad in Banking
1. **Confidentiality**: Ensuring sensitive banking data is accessible *only* to authorized users.
   - *Real Banking Example*: A customer viewing their net worth on mobile banking should be the *only* person able to see that data. Encrypted TLS/HTTPS prevents ISP or Wi-Fi eavesdroppers from sniffing account numbers.
2. **Integrity**: Guaranteeing data has not been altered, tampered with, or corrupted.
   - *Real Banking Example*: When transferring $100 to a friend, an attacker cannot intercept the packet and change the recipient's account number or alter the amount to $10,000.
3. **Availability**: Ensuring banking services and payment networks remain operational 24/7.
   - *Real Banking Example*: Payment gateways, ATM networks, and stock trading platforms must remain operational during market peak hours. A DDoS attack taking down trading servers during market hours causes catastrophic financial loss.

---

## 📘 3. Specialized Non-Technical Security Guides

1. **Ramesh & Suresh Encryption Story (Public/Private Keys)**:
   👉 **[`06_asymmetric_encryption_ramesh_suresh_guide.md`](file:///f:/poc/cybersecurity/06_asymmetric_encryption_ramesh_suresh_guide.md)** (Clear, simple storytelling guide explaining Public/Private Key pairs, secret messaging, digital signatures, and HTTPS hybrid encryption using Ramesh & Suresh).

2. **Practical Banking Security Mechanisms**:
   👉 **[`04_practical_banking_security_mechanisms.md`](file:///f:/poc/cybersecurity/04_practical_banking_security_mechanisms.md)** (12-step operational guide covering Input Validation, Parameterized Queries, Salted Hashing, MFA/OTP, Rate Limiting, Session Timeouts, RBAC, TLS, Digital Signatures, and Anomaly Detection).

3. **Spring Boot & React Security Blueprint**:
   👉 **[`05_spring_boot_react_security_architecture_guide.md`](file:///f:/poc/cybersecurity/05_spring_boot_react_security_architecture_guide.md)** (Architectural mental model, trust boundary definitions, and step-by-step security blueprint for developers building Spring Boot backend APIs + React frontend SPAs).

---

## 🧪 4. Hands-On Practical Security Labs for Students

To convert theory into practical skills, students should complete these 3 hands-on practical labs included in this directory:

1. **[`01_sql_injection_defense.js`](file:///f:/poc/cybersecurity/01_sql_injection_defense.js)**: Demonstrates SQL Injection vulnerability vs Parameterized Prepared Statement protection.
2. **[`02_password_hashing.js`](file:///f:/poc/cybersecurity/02_password_hashing.js)**: Salted password hashing with PBKDF2 SHA-512 to defeat Rainbow Table attacks.
3. **[`03_digital_signatures.js`](file:///f:/poc/cybersecurity/03_digital_signatures.js)**: RSA 2048-bit key signing & SHA-256 integrity verification for wire transfers.

### 🚀 Running the Practical Security Labs:
```bash
cd f:\poc\cybersecurity

node 01_sql_injection_defense.js
node 02_password_hashing.js
node 03_digital_signatures.js
```
