# How to Run the Rectangle PA Calculator POC

This POC demonstrates passing a **`Rectangle` object** (`length`, `breadth`) from React to Spring Boot, which calculates and returns a **`PA` (Perimeter & Area) object**, with validation and custom exception handling when both length and breadth are zero.

---

## 📋 Prerequisites & Verification Commands

Before running the projects, make sure your computer has the required tools installed. Run the following verification commands in terminal:

1. **Check Java JDK Version**:
   ```bash
   java -version
   ```
   - **What this command does**: Prints installed Java Runtime Environment (JRE) and JDK version. Ensures Java 17 or Java 21 is installed.

2. **Check Apache Maven Version**:
   ```bash
   mvn -v
   ```
   - **What this command does**: Prints installed Apache Maven build tool version, Java home path, and OS details. Ensures Maven 3.8+ is ready.

3. **Check Node.js and npm Versions**:
   ```bash
   node -v
   npm -v
   ```
   - **What `node -v` does**: Displays installed Node.js runtime version.
   - **What `npm -v` does**: Displays installed Node Package Manager (npm) version.

---

## 🚀 Step-by-Step Execution Guide & Command Explanations

### Step 1: Start the Spring Boot Backend

1. **Navigate to Backend Folder**:
   ```bash
   cd reactandspringpoc\rectangle-backend
   ```
   - **Command Explanation**: `cd` (Change Directory) changes working directory to `rectangle-backend` where `pom.xml` lives.

2. **Run Spring Boot Application**:
   ```bash
   mvn spring-boot:run
   ```
   - **Command Explanation**: Invokes `run` goal from `spring-boot-maven-plugin`. Compiles Java source files (`src/main/java`), copies `application.properties`, boots Spring container, and starts embedded Tomcat server on port `8080`.

---

### Step 2: Start the React Frontend

1. **Navigate to Frontend Folder**:
   ```bash
   cd f:\poc\reactandspringpoc\rectangle-frontend
   ```
   - **Command Explanation**: `cd` changes working directory to `rectangle-frontend` where `package.json` lives.

2. **Install Node Dependencies** (first time only):
   ```bash
   npm install
   ```
   - **Command Explanation**: Downloads required npm packages (`react`, `react-dom`, `axios`, `react-scripts`) from npm registry and installs them into `node_modules/` directory.

3. **Start React Development Server**:
   ```bash
   npm start
   ```
   - **Command Explanation**: Executes `react-scripts start` command. Compiles React components, starts Webpack dev server on `http://localhost:3000`, and opens application in default browser.

---

## 🧪 Summary Table of Terminal Commands Used

| Command | Purpose & Internal Action |
| :--- | :--- |
| `cd <path>` | Changes current working directory to target folder. |
| `java -version` | Verifies JDK installation and version. |
| `mvn -v` | Verifies Apache Maven build tool installation. |
| `node -v` | Verifies Node.js JavaScript runtime version. |
| `npm -v` | Verifies Node Package Manager version. |
| `mvn spring-boot:run` | Boots Spring ApplicationContext and starts embedded Tomcat server on port 8080. |
| `npm install` | Downloads and installs JavaScript dependencies into `node_modules/`. |
| `npm start` | Launches Webpack dev server on port 3000 to serve React application. |
