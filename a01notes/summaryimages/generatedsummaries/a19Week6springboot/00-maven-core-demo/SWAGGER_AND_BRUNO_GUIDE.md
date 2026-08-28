# ℹ️ Swagger & Bruno Guide for `00-maven-core-demo`

## 📌 Project Overview
This project is a **pure Maven Java CLI application** demonstrating core Maven concepts such as build lifecycles, resource filtering (`${environment.name}`), and active profiles (`dev` vs `prod`).

---

## 🌐 Swagger UI Status
* **Swagger UI / OpenAPI**: **Not Applicable**
* **Reason**: This project does not contain Spring Boot Web, embedded Tomcat, or REST Controllers. It runs as a Java console process.

---

## 🐶 Bruno UI Status
* **Bruno API Testing**: **Not Applicable**
* **Reason**: There are no HTTP REST endpoints listening on a network port to test via Bruno.

---

## 🚀 How to Run & Test This Project
To test this project, execute Maven goals via terminal:

```powershell
# Run with default 'dev' profile
mvn clean compile exec:java

# Run with 'prod' profile
mvn clean compile exec:java -Pprod
```
