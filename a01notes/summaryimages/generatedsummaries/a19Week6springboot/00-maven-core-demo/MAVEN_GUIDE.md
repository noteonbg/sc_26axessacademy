# Master Guide to Apache Maven Concepts

Welcome to the **Apache Maven Concepts Guide**. This document provides an exhaustive, practical explanation of all fundamental and advanced Maven concepts demonstrated in the `maven-core-demo` project.

---

## 1. What is Apache Maven?

**Apache Maven** is a software project management and comprehension tool primarily used for Java projects. Based on the concept of a **Project Object Model (POM)**, Maven manages project builds, reporting, dependencies, and documentation from a central piece of configuration (`pom.xml`).

### Key Benefits of Maven:
- **Convention over Configuration**: Standardized folder structure across all Java projects.
- **Automated Dependency Management**: Downloads libraries and transitive dependencies automatically from remote repositories (Maven Central).
- **Consistent Build Lifecycle**: Standard set of commands (`compile`, `test`, `package`, `clean`, `install`).
- **Extensible Plugin Architecture**: Build steps are executed by plugins.

---

## 2. Standard Directory Layout

Maven enforces a strict standard directory structure. This ensures any developer can navigate any Maven project instantly.

```
maven-core-demo/
├── pom.xml                             # Project Object Model configuration
├── MAVEN_GUIDE.md                      # Documentation guide
└── src/
    ├── main/                           # Application source files
    │   ├── java/                       # Java source code (.java)
    │   │   └── com/example/...
    │   └── resources/                  # Resource files (config, properties, XMLs)
    │       ├── application.properties
    │       └── logback.xml
    └── test/                           # Test source files
        ├── java/                       # Unit and integration tests (.java)
        │   └── com/example/...
        └── resources/                  # Test resources
            └── test-config.properties
```

---

## 3. The Project Object Model (`pom.xml`)

The `pom.xml` file is the heart of any Maven project. Below is a breakdown of the key XML sections:

### 3.1 GAV Coordinates (Group, Artifact, Version)
GAV uniquely identifies a project across the entire Java ecosystem.

```xml
<groupId>com.example</groupId>
<artifactId>maven-core-demo</artifactId>
<version>1.0.0</version>
<packaging>jar</packaging>
```

| Element | Description | Example |
| :--- | :--- | :--- |
| `groupId` | Domain/Organization namespace (reverse domain format) | `com.example` |
| `artifactId` | The unique name of the project module | `maven-core-demo` |
| `version` | Current release or snapshot version | `1.0.0` or `1.0.0-SNAPSHOT` |
| `packaging` | Target output format (`jar`, `war`, `ear`, `pom`) | `jar` |

> **SNAPSHOT vs Release**: Versions ending in `-SNAPSHOT` represent work-in-progress development builds that are re-downloaded during builds when dependencies update.

---

### 3.2 Properties
Properties are variables used throughout `pom.xml` to maintain consistency and prevent duplication.

```xml
<properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <junit.version>5.11.4</junit.version>
</properties>
```

---

### 3.3 Dependencies & Scopes

Dependencies declare external libraries required by your project. Maven resolves these from Maven Central and downloads them to your local repository (`~/.m2/repository`).

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>${junit.version}</version>
    <scope>test</scope>
</dependency>
```

#### Dependency Scopes Explained:

| Scope | Compiled Code | Test Code | Runtime | Packaged JAR/WAR | Typical Use Case |
| :--- | :---: | :---: | :---: | :---: | :--- |
| `compile` *(default)* | Yes | Yes | Yes | Included | Utility libraries (e.g., `commons-lang3`, `slf4j-api`) |
| `provided` | Yes | Yes | No | Excluded | App server APIs (e.g., `servlet-api`, `lombok`) |
| `runtime` | No | Yes | Yes | Included | Implementation drivers (e.g., `logback-classic`, `mysql-connector`) |
| `test` | No | Yes | No | Excluded | Testing frameworks (e.g., `junit-jupiter`, `mockito`) |
| `system` | Yes | Yes | Yes | Excluded | Explicit local JAR files via system path |
| `import` | N/A | N/A | N/A | N/A | Dependency BOM imports inside `<dependencyManagement>` |

---

## 4. Build Lifecycles & Phases

Maven builds are structured into **3 Built-in Lifecycles**. Each lifecycle consists of ordered **Phases**. When you execute a phase, Maven sequentially executes all preceding phases in that lifecycle.

### 4.1 The `default` Lifecycle (Main Build Process)

1. `validate`: Validates that the project structure is correct and necessary information is available.
2. `compile`: Compiles main Java source code (`src/main/java`) into `target/classes`.
3. `test-compile`: Compiles test source code (`src/test/java`) into `target/test-classes`.
4. `test`: Runs unit tests using a testing framework (Surefire plugin).
5. `package`: Packs compiled code into its distribution format (e.g., `.jar` file in `target/`).
6. `verify`: Runs integration tests and checks quality criteria.
7. `install`: Installs the package into the local Maven repository (`~/.m2/repository`).
8. `deploy`: Copies final package to remote repository for sharing with other developers.

### 4.2 The `clean` Lifecycle
Handles project cleanup.
- `pre-clean`: Executes processes before actual project cleaning.
- `clean`: Removes all files generated by previous builds (deletes `target/` folder).
- `post-clean`: Executes processes after project cleaning.

---

## 5. Plugins and Goals

In Maven, **Plugins do all the actual work**. A **Goal** is a specific task bound to a lifecycle phase.

| Plugin | Goal | Phase Bound | Purpose |
| :--- | :--- | :--- | :--- |
| `maven-clean-plugin` | `clean` | `clean` | Deletes `target/` directory |
| `maven-compiler-plugin` | `compile` | `compile` | Compiles `.java` to `.class` |
| `maven-surefire-plugin` | `test` | `test` | Runs JUnit 5 test classes |
| `maven-jar-plugin` | `jar` | `package` | Packages classes & manifest into JAR |
| `exec-maven-plugin` | `java` | Manual CLI | Executes main Java application |

---

## 6. Build Profiles & Resource Filtering (Practical Example)

### What are Maven Profiles?
Build profiles allow you to customize build configurations, properties, or dependencies for different target environments (such as `dev`, `test`, `prod`) without modifying code.

### How Resource Filtering Works:
When resource filtering is enabled in `pom.xml`:
```xml
<resources>
    <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
    </resource>
</resources>
```
Maven scans files inside `src/main/resources/` (such as `application.properties`) and replaces placeholders (e.g., `${environment.name}`) with property values from the active profile!

### Profile Definitions in `pom.xml`:
```xml
<profiles>
    <!-- 1. Development Profile (Active by Default) -->
    <profile>
        <id>dev</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <environment.name>DEVELOPMENT (H2 In-Memory DB: jdbc:h2:mem:devdb)</environment.name>
            <app.log.level>DEBUG</app.log.level>
        </properties>
    </profile>

    <!-- 2. Production Profile -->
    <profile>
        <id>prod</id>
        <properties>
            <environment.name>PRODUCTION (PostgreSQL DB: jdbc:postgresql://prod-db:5432/bankdb)</environment.name>
            <app.log.level>WARN</app.log.level>
        </properties>
    </profile>
</profiles>
```

### Running Profiles via Command Line:

1. **Run with Default (`dev`) Profile**:
   ```bash
   mvn compile exec:java
   ```
   **Output**:
   `>>> ACTIVE MAVEN PROFILE / ENVIRONMENT: DEVELOPMENT (H2 In-Memory DB: jdbc:h2:mem:devdb)`

2. **Run with `prod` Profile**:
   ```bash
   mvn compile exec:java -Pprod
   ```
   **Output**:
   `>>> ACTIVE MAVEN PROFILE / ENVIRONMENT: PRODUCTION (PostgreSQL DB: jdbc:postgresql://prod-db:5432/bankdb)`

---

## 7. Step-by-Step CLI Execution Guide

### Command 1: Clean the Target Directory
Deletes the output directory (`target/`).
```bash
mvn clean
```

### Command 2: Compile Main Source Code
Compiles all Java code under `src/main/java`.
```bash
mvn compile
```

### Command 3: Run Unit Tests
Compiles test classes and executes JUnit 5 tests.
```bash
mvn test
```

### Command 4: Package Application into Executable JAR
Runs compilation, tests, and creates `maven-core-demo-1.0.0.jar` inside `target/`.
```bash
mvn package
```

### Command 5: Run Application with Default (Dev) Profile
Executes `com.example.App` directly using Maven.
```bash
mvn exec:java
```

### Command 6: Run Application with Prod Profile
Activates the `prod` profile during build/execution.
```bash
mvn exec:java -Pprod
```

### Command 7: Inspect Dependency Tree
Visualizes all direct and transitive dependencies.
```bash
mvn dependency:tree
```

---

## 8. Summary Table of Core Commands

| Goal/Command | Description | Lifecycle Phase | Output / Profile |
| :--- | :--- | :--- | :--- |
| `mvn clean` | Deletes generated build output | `clean` | Removes `target/` |
| `mvn compile` | Compiles main source files | `compile` | `target/classes/` |
| `mvn test` | Executes unit tests | `test` | `target/surefire-reports/` |
| `mvn package` | Builds distributable JAR | `package` | `target/*.jar` |
| `mvn exec:java` | Runs main class with dev profile | Exec goal | Dev Profile Config |
| `mvn exec:java -Pprod` | Runs main class with prod profile | Exec goal | Prod Profile Config |
| `mvn dependency:tree` | Displays dependency hierarchy | Plugin goal | Console output |

