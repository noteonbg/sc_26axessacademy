## 1. Create the Java Application (`Processor.java`)

This program reads text from `/app/input/data.txt`, converts the text to uppercase, and writes it to `/app/output/result.txt`.

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Processor {
    public static void main(String[] args) {
        Path inputPath = Paths.get("/app/input/data.txt");
        Path outputPath = Paths.get("/app/output/result.txt");

        System.out.println("[INFO] Processing files from input volume...");

        try {
            // Check if input file exists in the volume
            if (!Files.exists(inputPath)) {
                System.err.println("[ERROR] Input file not found at " + inputPath);
                System.exit(1);
            }

            // Read contents, convert to UPPERCASE
            String content = Files.readString(inputPath);
            String processedContent = content.toUpperCase();

            // Write output to the destination volume
            Files.writeString(outputPath, processedContent);
            System.out.println("[SUCCESS] Processed content written to " + outputPath);

        } catch (IOException e) {
            System.err.println("[EXCEPT] Error handling volume files: " + e.getMessage());
            System.exit(2);
        }
    }
}


---

## 2. Create the `Dockerfile`

```dockerfile
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Create directories where the volumes will be mounted inside the container
RUN mkdir -p /app/input /app/output

# Copy and compile the Java code
COPY Processor.java .
RUN javac Processor.java

ENTRYPOINT ["java", "Processor"]

```

---

## 3. Step-by-Step Execution Guide

### Step 1: Create the Named Volumes

Create both Docker named volumes in your terminal:

```bash
docker volume create input
docker volume create output

```

You can verify they exist by listing volumes:

```bash
docker volume ls

```

---

### Step 2: Build the Docker Image

```bash
docker build -t volume-processor-demo .

```

---

### Step 3: Populate the `input` Volume with Initial Data

Because named volumes are managed by Docker on the host system, the easiest way to place a sample `data.txt` file into the `input` volume is by running a quick helper container:

```bash
docker run --rm -v input:/app/input alpine sh -c 'echo "hello from docker input volume" > /app/input/data.txt'


---

### Step 4: Run the Java Container with Both Volumes Mounted

Mount the named volume `input` to `/app/input` and `output` to `/app/output`:

```bash
docker run --rm \
  -v input:/app/input \
  -v output:/app/output \
  volume-processor-demo

```

*Expected Terminal Output:*

```text
[INFO] Processing files from input volume...
[SUCCESS] Processed content written to /app/output/result.txt

```

---

### Step 5: Read the Processed Data from the `output` Volume

Inspect the resulting file written inside the `output` volume using a temporary helper container:

```bash
docker run --rm -v output:/app/output alpine cat /app/output/result.txt

```

*Expected Output:*

```text
HELLO FROM DOCKER INPUT VOLUME

```

---

## Key Takeaway: How Volume Mounts Work Here

| Command Flag | Named Volume | Destination inside Container | Role |
| --- | --- | --- | --- |
| **`-v input:/app/input`** | `input` | `/app/input` | Read-only input source for data |
| **`-v output:/app/output`** | `output` | `/app/output` | Persistent output storage for results |

Data written to the `output` volume persists even after the container finishes execution and exits.

