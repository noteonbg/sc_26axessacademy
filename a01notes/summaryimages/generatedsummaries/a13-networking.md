# Docker Networking with Simple Core Java (Two Containers)


Folder for this lab:
\01-networking\`

---

## 1. Docker idea in plain words (read this first)

1. A **Dockerfile** is a recipe.
2. An **image** is the packed application created from that recipe.
3. A **container** is a running copy of that image.
4. By default, two containers are like two closed rooms. They cannot easily call each other by name.
5. A **Docker network** is like putting both rooms in the same office corridor.
6. On a network you create yourself, Container A can reach Container B using the **container name** as the hostname.
7. That is the whole networking lesson.

Bank example: one container calculates interest (server). Another container sends account values (client). Both must be on the same Docker network.

---

## 2. What we built

1. **interest-server** container — Java program listens on port `5000`.
2. **interest-client** container — Java program connects to the server, sends `principal,rate,days`, prints interest.
3. Both use only JDK classes. No web framework.

Files:

```text
01-networking/
  server/
    InterestServer.java
    Dockerfile
  client/
    InterestClient.java
    Dockerfile
```

---

## 3. What the Java code does (very simple)

### Server
1. Opens port 5000.
2. Waits for one line of text like `100000,3.5,30`.
3. Calculates simple interest.
4. Sends back one line of text result.

### Client
1. Reads `SERVER_HOST` environment variable (in Docker this will be `interest-server`).
2. Connects to that host on port 5000.
3. Sends `100000,3.5,30` (or values from env).
4. Prints the server response.

---

## 4. Step-by-step: run the networking demo

Open PowerShell or Command Prompt.

### Step 1 — Go to the networking folder

```bash
cd "E:\scproject\dontpostingit\Campus Content 2026\Week 2\docker-java-labs\01-networking"
```

### Step 2 — Build the server image

```bash
docker build -t interest-server:1.0 ./server
```

What this means:
1. Docker reads `server/Dockerfile`.
2. Starts from a Java 17 image.
3. Copies `InterestServer.java`.
4. Compiles it with `javac`.
5. Saves the result as image `interest-server:1.0`.

### Step 3 — Build the client image

```bash
docker build -t interest-client:1.0 ./client
```

### Step 4 — Create a Docker network

```bash
docker network create bank-net
```

What this means: you created a private corridor named `bank-net`.

Check it:

```bash
docker network ls
```

### Step 5 — Start the server container on that network

```bash
docker run -d --name interest-server --network bank-net interest-server:1.0
```

What the flags mean:
1. `-d` — run in background.
2. `--name interest-server` — give it a clear name. This name becomes the hostname.
3. `--network bank-net` — put it on our corridor.

Check it is running:

```bash
docker ps
docker logs interest-server
```

You should see: `InterestServer started on port 5000`

### Step 6 — Run the client container on the same network

```bash
docker run --rm --name interest-client --network bank-net -e SERVER_HOST=interest-server interest-client:1.0
```

What this means:
1. `--rm` — delete client container after it finishes.
2. Same `--network bank-net` — so it can see the server by name.
3. `-e SERVER_HOST=interest-server` — tell Java which hostname to call.

Expected output idea:

```text
Connecting to interest-server:5000
Sending: 100000,3.5,30
Response from server: Principal=100000.0, Rate=3.5, Days=30, Interest=287.6712328767123
```

### Step 7 — Try different values without rebuilding

```bash
docker run --rm --network bank-net -e SERVER_HOST=interest-server -e PRINCIPAL=200000 -e RATE=3.5 -e DAYS=30 interest-client:1.0
```

---

## 5. Prove that the network matters

### Test A — client on a different network (should fail)

```bash
docker network create other-net
docker run --rm --network other-net -e SERVER_HOST=interest-server interest-client:1.0
```

This should fail with connection / unknown host style error, because the client is not in `bank-net`.

### Test B — put client back on bank-net (should work)

```bash
docker run --rm --network bank-net -e SERVER_HOST=interest-server interest-client:1.0
```

This is the key beginner lesson: **same network + container name = they can talk**.

---

## 6. Useful inspection commands

```bash
docker network inspect bank-net
```

Shows which containers are attached and their IPs.

```bash
docker logs interest-server
```

Shows what the server received and replied.

```bash
docker exec -it interest-server sh
```

Optional: go inside the server container. Type `exit` to leave.

---

## 7. Cleanup

```bash
docker rm -f interest-server interest-client
docker network rm bank-net other-net
```

Images can stay for next practice, or remove:

```bash
docker rmi interest-server:1.0 interest-client:1.0
```

---



## 8. Common mistakes

1. Forgetting `--network bank-net` on one of the containers.
2. Typo in container name vs `SERVER_HOST`.
3. Starting client before server.
4. Rebuilding Java code but forgetting `docker build` again.
5. Expecting `localhost` inside the client container to mean the server. Inside a container, `localhost` means itself, not the other container.

---

## 9. One-sentence summary

Create a network, put both containers on it, call the server by its container name — that is Docker networking for beginners.
