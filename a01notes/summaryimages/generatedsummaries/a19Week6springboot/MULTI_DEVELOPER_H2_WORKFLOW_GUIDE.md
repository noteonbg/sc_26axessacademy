# Multi-Developer H2 Database & Feature Branch Rebase Workflow Guide

This guide provides numbered, step-by-step instructions specifically tailored for teams working on **local feature branches** using **`git fetch origin`** and **`git rebase origin/main`** with **H2 Database** in Spring Boot.

---

##  Pro Tips for Seeding Database Tables with Values

When developers create new tables or need initial test data, here are the top 4 techniques to seed H2 database tables with default values in Spring Boot:

---

### Method 1: SQL Scripts via `data.sql` / `import.sql`

If developers prefer raw SQL scripts, Spring Boot can automatically execute an SQL file on startup.

1. **Create `src/main/resources/data.sql`**:
   ```sql
   INSERT INTO employees (name, email, department, salary, designation) 
   VALUES ('Alice Johnson', 'alice@sc.com', 'IT', 85000.00, 'Developer');

   INSERT INTO employees (name, email, department, salary, designation) 
   VALUES ('Bob Smith', 'bob@sc.com', 'Finance', 92000.00, 'Analyst');
   ```

2. **Configure `application.properties`**:
   ```properties
   # Ensure Hibernate creates tables BEFORE data.sql executes!
   spring.jpa.defer-datasource-initialization=true
   spring.sql.init.mode=always
   ```

* ** Pro Tip**: Always set `spring.jpa.defer-datasource-initialization=true`. Without this setting, Spring Boot tries to execute `data.sql` before Hibernate creates the tables, causing a `Table "EMPLOYEES" not found` error!

---

### Method 2: Interactive Seeding via H2 Web Console

For quick one-off testing without modifying code:

1. Open H2 Console in browser: `http://localhost:8083/h2-console`
2. Run SQL statements directly in the query window:
   ```sql
   INSERT INTO EMPLOYEES (name, email, department, salary, designation) 
   VALUES ('Carol Williams', 'carol@sc.com', 'HR', 68000.00, 'HR Specialist');
   ```

---


---

### Method 3: Programmatic Seeding via `CommandLineRunner` (Recommended for Dev Teams)

This is the cleanest pattern because it is type-safe, refactoring-friendly, and tracked in Git.

1. **Create a `@Component` implementing `CommandLineRunner`**:
   ```java
   package com.standardchartered.jpademo.config;

   import com.standardchartered.jpademo.entity.Employee;
   import com.standardchartered.jpademo.repository.EmployeeRepository;
   import org.springframework.boot.CommandLineRunner;
   import org.springframework.stereotype.Component;
   import java.math.BigDecimal;
   import java.util.List;

   @Component
   public class EmployeeDataInitializer implements CommandLineRunner {

       private final EmployeeRepository repository;

       public EmployeeDataInitializer(EmployeeRepository repository) {
           this.repository = repository;
       }

       @Override
       public void run(String... args) {
           // Pro Tip: Idempotent check prevents duplicate rows on restart!
           if (repository.count() == 0) {
               Employee e1 = new Employee("Alice Johnson", "alice@sc.com", "IT", new BigDecimal("85000"), "Developer");
               Employee e2 = new Employee("Bob Smith", "bob@sc.com", "Finance", new BigDecimal("92000"), "Analyst");
               repository.saveAll(List.of(e1, e2));
           }
       }
   }
   ```

* **💡 Pro Tip**: Always check `repository.count() == 0` before saving so restarting the application doesn't insert duplicate records or throw unique key violation exceptions!

#
## 🔍 When Hibernate WILL Create Tables vs When It WILL NOT Create Tables

### 🟢 Conditions WHEN Hibernate WILL Create Tables Automatically:

1. **Class Has `@Entity` Annotation**:
   * The Java class MUST be annotated with `@jakarta.persistence.Entity`.

2. **Entity is Inside Spring Component Scan Path**:
   * The class resides in the main package or a sub-package of `@SpringBootApplication`.

3. **Primary Key `@Id` Is Defined**:
   * The `@Entity` class contains at least one field marked as Primary Key (`@Id`).

4. **`spring.jpa.hibernate.ddl-auto` Property Configuration**:
   * **`update`**: Checks H2 database. If table doesn't exist, executes `CREATE TABLE`. If table exists, executes `ALTER TABLE` for new fields.
   * **`create`**: Drops and recreates all tables on startup.

---

### 🔴 Conditions WHEN Hibernate WILL NOT Create Tables:

1. **Missing `@Entity` Annotation**: Non-entity classes (DTOs, POJOs, Controllers) are ignored.
2. **Table Already Exists (under `ddl-auto=update`)**: Skips `CREATE TABLE`; only adds new columns via `ALTER TABLE`.
3. **`spring.jpa.hibernate.ddl-auto` Set to `none` or `validate`**: No table creation DDL executed.
4. **Annotated with `@MappedSuperclass` or `@Transient`**: Standalone tables are not created.

---

## 🌐 Concrete Example: 5 Developers Adding Tables A through G

### 📋 Team Table Creation Matrix:
* **Developer 1**: Creates **Table A** (`TableA.java`)
* **Developer 2**: Creates **Table B** (`TableB.java`) & **Table C** (`TableC.java`)
* **Developer 3**: Creates **Table D** (`TableD.java`)
* **Developer 4**: Creates **Table E** (`TableE.java`)
* **Developer 5**: Creates **Table F** (`TableF.java`) & **Table G** (`TableG.java`)

---

### ⏳ Step-by-Step Chronological Merge Timeline:

1. **Dev 1** merges `TableA.java` to `origin/main`.
2. **Dev 2** runs `git fetch origin` && `git rebase origin/main` -> Launches app -> Hibernate auto-creates **Tables A, B, C**. Dev 2 merges.
3. **Dev 3** rebases -> Launches app -> H2 auto-creates **Table D** alongside A, B, C. Dev 3 merges.
4. **Dev 4** rebases -> Launches app -> H2 auto-creates **Table E** alongside A, B, C, D. Dev 4 merges.
5. **Dev 5** rebases -> Launches app -> H2 auto-creates **Tables F & G** alongside A, B, C, D, E. Dev 5 merges.

---

## 📌 Numbered Steps: Feature Branch Rebase & H2 Integration Workflow

### Step 1: Ensure Local H2 Database Files are Git-Ignored (`.gitignore`)
```gitignore
/data/
*.mv.db
*.trace.db
```

### Step 2: Developer 1 Merges Code to Main (`origin/main`)
### Step 3: Developer 2, 3, 4, 5 Rebase Feature Branches onto `origin/main`
```bash
git status
git add .
git commit -m "WIP feature commit"
git fetch origin
git rebase origin/main
```

### Step 4: Rebuild & Restart Spring Boot Application
```bash
mvn clean compile
mvn spring-boot:run
```

### Step 5: Verify Updated H2 Schema & Seed Data in H2 Console
Open `http://localhost:8083/h2-console`.

### Step 6: Handling Breaking Schema Changes (Hard Reset)
```powershell
Remove-Item -Recurse -Force ./data
mvn spring-boot:run
```
