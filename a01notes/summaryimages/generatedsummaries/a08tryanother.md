 example that demonstrates **Networking**, **Logging**, **Arguments**, and **Storage** together.

We will build a simple Java application that acts as an **HTTP Service** listening on a port. When accessed over a custom Docker network, it writes incoming HTTP requests to stdout/stderr (for `docker logs`) and saves request logs to a file volume.

---

## 1. Java Code: `HttpServerApp.java`

Create `HttpServerApp.java`:

```java
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerApp {
    public static void main(String[] args) throws Exception {
        // Read Port from Argument (default to 8080)
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8080;

        // Create an HTTP Server on 0.0.0.0 (all network interfaces)
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);

        // STDOUT / STDERR logs (captured by Docker logging drivers)
        System.out.println("[INFO] Starting Java Server on port " + port + "...");

        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String clientIp = exchange.getRemoteAddress().toString();
                String logMessage = "[REQUEST] Received HTTP GET from " + clientIp;

                // 1. Log to STDOUT for `docker logs`
                System.out.println(logMessage);

                // 2. Persist to file volume storage
                try (FileWriter fw = new FileWriter("/app/data/access.log", true);
                     PrintWriter out = new PrintWriter(fw)) {
                    out.println(logMessage);
                }

                // Send Response
                String response = "Hello from Container Network!";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        server.start();
        System.out.println("[INFO] Server is running and listening for requests...");
    }
}

```

---

## 2. Dockerfile

Create `Dockerfile`:

```dockerfile
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy and compile Java code
COPY HttpServerApp.java .
RUN javac HttpServerApp.java

# Create mount directory for file storage
RUN mkdir -p /app/data

# Default argument: Start server on port 8080
ENTRYPOINT ["java", "HttpServerApp"]
CMD ["8080"]

```

---

## 3. Step-by-Step Execution Guide

### Step 1: Create a Custom Docker Network

Create a bridge network so containers can communicate using container names:

```bash
docker network create my-custom-net

```

---

### Step 2: Build the Docker Image

```bash
docker build -t java-net-app .

```

---

### Step 3: Run the Container with Network, Storage, Ports, and Arguments

Run the container attached to `my-custom-net`, with port forwarding, a volume mount, and an argument specifying port `8080`:

```bash
docker run -d \
  --name java-server \
  --network my-custom-net \
  -p 8080:8080 \
  -v "$(pwd)/my-logs:/app/data" \
  java-net-app 8080

```

* **`-d`**: Runs container in detached (background) mode.
* **`--network my-custom-net`**: Connects container to our custom bridge network.
* **`-p 8080:8080`**: Maps host port 8080 to container port 8080.
* **`-v "$(pwd)/my-logs:/app/data"`**: Mounts host folder `./my-logs` for persistent storage.
* **`8080`**: Passed as an argument to `HttpServerApp.java`.

---

### Step 4: Test Networking Scenarios

#### Scenario A: Access from Host Machine

Send a request from your host terminal using `curl` or open `http://localhost:8080` in your browser:

```bash
curl http://localhost:8080

```

*Output:*

```text
Hello from Container Network!

```

#### Scenario B: Inter-Container Networking via DNS

Run a second container on the **same custom network** and call `java-server` directly by container name rather than IP:

```bash
docker run --rm --network my-custom-net alpine/curl http://java-server:8080

```

*Output:*

```text
Hello from Container Network!

```

---

### Step 5: Viewing Docker Logs

Docker captures everything printed to standard output (`STDOUT`) and standard error (`STDERR`).

1. **View static logs:**
```bash
docker logs java-server

```


*Output:*
```text
[INFO] Starting Java Server on port 8080...
[INFO] Server is running and listening for requests...
[REQUEST] Received HTTP GET from /172.18.0.1:54321
[REQUEST] Received HTTP GET from /172.18.0.3:49152

```


2. **Stream logs in real-time (`-f`):**
```bash
docker logs -f java-server

```


*(Press `Ctrl+C` to exit streaming)*
3. **Tail the last N lines with timestamps:**
```bash
docker logs --tail 2 --timestamps java-server

```



---

### Step 6: Verify Persisted Storage

Check the log file written directly onto the host filesystem inside `./my-logs/`:

```bash
cat my-logs/access.log

```

*Output:*

```text
[REQUEST] Received HTTP GET from /172.18.0.1:54321
[REQUEST] Received HTTP GET from /172.18.0.3:49152

```

---

### Cleanup

Stop and clean up resources when done:

```bash
docker stop java-server
docker rm java-server
docker network rm my-custom-net

```