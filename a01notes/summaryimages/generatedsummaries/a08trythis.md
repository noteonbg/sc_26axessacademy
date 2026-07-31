

### Step 1: Create `App.java`

Save the following code in a file named `App.java`:

```java
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

public class App {
    private static boolean isHealthy = true;

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);

        System.out.println("[INFO] Server starting on port " + port + "...");

        // Endpoint 1: Standard Home Page
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                if (!isHealthy) {
                    System.err.println("[ERROR] Request failed: Server simulated a crash!");
                    String response = "500 Internal Server Error: Application Crashed!";
                    exchange.sendResponseHeaders(500, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    return;
                }

                System.out.println("[INFO] Received GET request on /");
                String response = "Hello! Container is running fine.";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        // Endpoint 2: Simulate Crash
        server.createContext("/crash", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                System.err.println("[CRITICAL] Crash endpoint hit! Exiting application...");
                String response = "Triggering application exit...";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

                // Force exit with non-zero exit code to simulate container crash
                System.exit(1);
            }
        });

        server.start();
        System.out.println("[INFO] Server started successfully. Ready for requests.");
    }
}


### Step 2: Create `Dockerfile`

In the same directory, create a file named `Dockerfile`:

```dockerfile
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy source and compile
COPY App.java .
RUN javac App.java

EXPOSE 8080

ENTRYPOINT ["java", "App"]



### Step 3: Commands to Build, Run, and Practice Debugging

#### 1. Build the Docker Image

```bash
docker build -t java-crash-demo .

```

#### 2. Run the Container

```bash
docker run -d -p 8080:8080 --name my-java-app java-crash-demo

```

#### 3. Verify it is running

```bash
docker ps

```

#### 4. Test normal endpoint

```bash
curl http://localhost:8080

```

*Output:* `Hello! Container is running fine.`

Check logs:

```bash
docker logs my-java-app

```

#### 5. Trigger the Crash!

Call the `/crash` endpoint to force the Java application to exit:

```bash
curl http://localhost:8080/crash

```

#### 6. Inspect the Crashed State & Logs

Now check running vs. stopped containers to practice debugging:

```bash
# Check running containers (it won't show up here anymore)
docker ps

# Check ALL containers to see the exited status & exit code
docker ps -a

# View why it crashed from stdout/stderr logs
docker logs my-java-app

