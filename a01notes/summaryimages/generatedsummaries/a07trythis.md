

## 1. Project Setup

Create a new directory for your project and navigate into it:

mkdir java-docker-demo
cd java-docker-demo

```

Inside this directory, create two files: `HelloWorld.java` and `Dockerfile`.

---

### File 1: `HelloWorld.java`

This application accepts a name as an argument and appends a greetings message to a log file stored in a mounted directory (`/app/data`).

```java
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloWorld {
    public static void main(String[] args) {
        // Read argument passed from outside, default to "World" if none provided
        String name = (args.length > 0) ? args[0] : "World";
        String message = "Hello, " + name + "! Greeting logged at: " + java.time.LocalDateTime.now();

        System.out.println(message);

        // Write the message to a storage file inside the container
        try (FileWriter fw = new FileWriter("/app/data/log.txt", true);
             PrintWriter out = new PrintWriter(fw)) {
            out.println(message);
            System.out.println("Successfully written to /app/data/log.txt");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}



---

### File 2: `Dockerfile`

This Dockerfile uses a multi-stage approach or a lightweight OpenJDK image to compile and run the Java code.

```dockerfile
# Step 1: Use OpenJDK JDK to compile and run
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy Java source code into container
COPY HelloWorld.java .

# Compile the Java file
RUN javac HelloWorld.java

# Create directory for storage/volume mounting
RUN mkdir -p /app/data

# Default command to run the application
ENTRYPOINT ["java", "HelloWorld"]

```

---

## 2. Step-by-Step Execution Guide

### Step 1: Build the Docker Image

Build the Docker image named `java-hello-app`:

```bash
docker build -t java-hello-app .

```

---

### Step 2: Create a Local Directory for Storage

Create a folder on your host machine to store output logs across container runs:

```bash
mkdir my-host-data

```

---

### Step 3: Run the Container with Arguments & Storage

Run the container, passing:

1. **Argument (`Ramesh`)**: Appended at the end of the `docker run` command.
2. **Storage (`-v`)**: Mounts your local `my-host-data` directory to `/app/data` inside the container.

```bash
docker run --rm -v "$(pwd)/my-host-data:/app/data" java-hello-app Ramesh

```

*Expected Terminal Output:*

```text
Hello, Ramesh! Greeting logged at: 2026-07-30T13:00:00
Successfully written to /app/data/log.txt

```

---

### Step 4: Run Again with a Different Argument

Pass a different argument (e.g., `Suresh`):

```bash
docker run --rm -v "$(pwd)/my-host-data:/app/data" java-hello-app Suresh

```

*Expected Terminal Output:*

```text
Hello, Suresh! Greeting logged at: 2026-07-30T13:01:00
Successfully written to /app/data/log.txt

```

---

### Step 5: Verify Persisted Data

open  my-host-data/log.txt

## Key Takeaways

* **Passing Arguments:** `ENTRYPOINT ["java", "HelloWorld"]` in the Dockerfile lets anything appended to `docker run java-hello-app <arg>` act as command-line arguments to the Java `main` method.
* **Storage / Persistence:** The `-v "$(pwd)/my-host-data:/app/data"` option mounts a directory from your host into the container so that files created in `/app/data` persist even after the container stops and exits.