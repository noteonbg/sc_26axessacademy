1. **Container 1 (`producer-app`)**: Runs a Java program that generates log entries or data reports and writes them directly into the shared volume every few seconds.
2. **Container 2 (`consumer-app`)**: Runs an Alpine image (a simple lightweight monitor/log reader) that reads and processes that same file live from the shared volume.

---

## Step 1: Create the Shared Volume

First, create a single named volume in your terminal:

```cmd
docker volume create shared-data

```

---

## Step 2: Set Up Container 1 (Data Producer in Java)

### 1. `Writer.java`

This Java program writes a new timestamped log entry to `/app/shared/log.txt` every 2 seconds.

```java
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Writer {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("[Producer] Started writing to shared volume...");

        int count = 1;
        while (true) {
            try (FileWriter fw = new FileWriter("/app/shared/log.txt", true);
                 PrintWriter out = new PrintWriter(fw)) {
                
                String logLine = "Log entry #" + count + " generated at " + LocalDateTime.now();
                out.println(logLine);
                System.out.println("[Producer Wrote]: " + logLine);
                
                count++;
            } catch (IOException e) {
                System.err.println("[Producer Error]: " + e.getMessage());
            }

            // Pause for 2 seconds
            Thread.sleep(2000);
        }
    }
}

```

### 2. `Dockerfile` for Producer

```dockerfile
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
RUN mkdir -p /app/shared
COPY Writer.java .
RUN javac Writer.java
ENTRYPOINT ["java", "Writer"]

```

### 3. Build the Producer Image

```cmd
docker build -t writer-image .

```

---

## Step 3: Set Up Container 2 (Data Consumer / Reader)

For Container 2, we don't even need a custom Dockerfile! We can use a lightweight standard **`alpine`** image that continuously monitors the shared file.

---

## Step 4: Run Both Containers Together

### 1. Start Container 1 (`writer-image`) in Detached Mode

Mount the `shared-data` volume to `/app/shared` inside the Java container:

```cmd
docker run -d --name producer -v shared-data:/app/shared writer-image

```

### 2. Start Container 2 (`alpine`) to Read the Data Live

Mount the **same** `shared-data` volume to `/data` inside the Alpine container and continuously output new lines using `tail -f`:

#### Command Prompt (`cmd.exe`):

```cmd
docker run --rm -v shared-data:/data alpine sh -c "tail -f /data/log.txt"

```

#### PowerShell:

```powershell
docker run --rm -v shared-data:/data alpine sh -c "tail -f /data/log.txt"

```

---

## Expected Output

In your terminal, you will see the `alpine` container outputting the logs generated live by the Java container:

```text
Log entry #1 generated at 2026-07-31T12:58:12.123456
Log entry #2 generated at 2026-07-31T12:58:14.125678
Log entry #3 generated at 2026-07-31T12:58:16.127890
...

```

---

## Summary Diagram

```text
+-----------------------+                    +-----------------------+
|  Container 1 (Java)   |                    |  Container 2 (Alpine) |
|     producer-app      |                    |      consumer-app     |
|                       |                    |                       |
| Writes to:            |                    | Reads from:           |
| /app/shared/log.txt   |                    | /data/log.txt         |
+-----------+-----------+                    +-----------+-----------+
            |                                            |
            +--------------------+-----------------------+
                                 |
                        [ Shared Volume ]
                          "shared-data"

```

---

## Cleanup

When you are finished practicing:

```cmd
docker stop producer
docker rm producer
docker volume rm shared-data

```