# Containerization (Docker) - Detailed Summary

Sources: `Campus_contianerisation_v1.0.0.pdf` (Campus Content, Week 2) and `Campus-ContainersLabs.pdf` (Assignments, Week 2)

This summary follows the PDF **slide by slide**. After every slide you will see a short **Banking example - why it matters** so a new joiner in a bank IT team can connect the idea to real work.

---

## Slide 1 of 47 - Title

1. Title: **Fundamentals of Containerization** (Campus).
2. Banking example - why it matters: this module is the foundation for how bank IT packages overnight interest jobs, EMI helpers and internal tools so DEV, UAT and PROD run the same software.

---

## Slide 2 of 47 - Course Contents

3. Topics covered: Understanding Container & Containerization; Delving deeper into Containers; Container Management Tools; Create and manage Containers; Basics of Container Networking; Managing Storage in Containers.
4. Banking example - why it matters: a bank release is not only "write Java code". It also needs packaging, networking to the rates database, and durable storage for audit logs. This agenda matches that full path.

---

## Slide 3 of 47 - Introduction outline

5. Introduction covers: Containers to the Rescue; What is Container; What is Containerization; Benefits; LxC vs VM vs Docker.
6. Banking example - why it matters: before touching Docker commands, the team must agree why containers exist - usually to stop "works on my laptop, fails on UAT" for interest and EMI programs.

---

## Slide 4 of 47 - The typical SDLC problem

7. Typical problems: compatibility/dependency issues; long setup time; missed or wrong communication; lack of expertise; banter between different environments.
8. Banking example - why it matters: a developer builds an interest batch on Java 17; UAT still has Java 11; month-end posting fails at night. In banking, environment mismatch is not only annoying - it can delay customer interest posting.

---

## Slide 5 of 47 - Containers to the Rescue

9. By bundling the application and its dependencies into containers, build and deploy pipelines become simpler from Dev team to Production.
10. Banking example - why it matters: once InterestBatch is inside a container image, ops does not hand-install JDKs on every server. The same image moves from DEV to UAT to PROD, which is what audit expects.

---

## Slide 6 of 47 - What is a Container?

11. A container is a lightweight unit. All files needed to run it come from an image. This improves portability and consistency. Principle: build once, run anywhere.
12. Multiple apps run on one Container Engine on top of one Host OS, each with its own supporting files/runtime.
13. Banking example - why it matters: the overnight interest container and a small EMI helper can run on the same host without fighting over conflicting library versions, because each carries what it needs.

---

## Slide 7 of 47 - What is Containerization?

14. Containerization is packaging binaries, dependencies, configs, libraries and setup into containers that run smoothly in a target environment.
15. Flow: **Dockerfile â†' build â†' Docker Image â†' run â†' Docker Container**.
16. Banking example - why it matters: the Dockerfile is the written recipe for how the bank builds InterestBatch. Reviewers and auditors can read that recipe in Git instead of trusting a manual server install.

---

## Slide 8 of 47 - Benefits of Using Containers

17. Benefits listed: Portability, Efficiency, Scalability, Consistency, Reliability, Support for DevOps.
18. Banking example - why it matters:
    1. Portability - same interest image on laptop and UAT.
    2. Efficiency - cheaper than full VMs for every small batch.
    3. Scalability - start more EMI helper containers on salary-credit week.
    4. Consistency - UAT and PROD are identical bits.
    5. Reliability - replace a crashed container in seconds.
    6. DevOps - Azure Pipeline can build and ship the image automatically.

---

## Slide 9 of 47 - LxC vs VM vs Docker Containers

19. **VM:** virtualizes hardware via a Hypervisor; each Guest OS is heavy.
20. **LxC:** multi-purpose OS virtualization; shares host resources; uses namespaces/cgroups style isolation.
21. **Docker:** focuses on single-purpose application containers; Docker daemon manages them; shares host kernel.
22. One-line difference: VM = hardware virtualization; Container = operating-environment virtualization. Docker is supported on Windows via Hyper-V.
23. Banking example - why it matters: provisioning a new VM for every small rate-utility job is slow and costly. A Docker container for a Core Java interest class starts in seconds, which matters when finance asks for an urgent recalculation rerun.

---

## Slide 10 of 47 - Container Management (section title)

24. Section break: Container Management.
25. Banking example - why it matters: knowing concepts is not enough; bank ops must manage images and containers safely on shared servers without breaking other teams' jobs.

---

## Slide 11 of 47 - Container Management outline

26. Outline: Docker Architecture; Container Management Platforms; Images and Containers; Working with docker - Common Command List.
27. Banking example - why it matters: incident calls at 2 a.m. need people who know architecture and the basic commands to check whether interest-batch is Up, Exited or missing.

---

## Slide 12 of 47 - Docker Architecture

28. Docker is an open platform for developing, shipping and running applications (Docker Inc). Industry standard for lightweight portable containers. Written in Go, released 2013, free and paid licensing. Supports Windows, Linux, Mac.
29. Components: Docker Daemon, Docker Clients, Docker Host, Docker Registry, Docker Images.
30. Banking example - why it matters: when `docker run` fails, the engineer must know whether the Client, Daemon or Registry access is the problem. In a bank, registry access is often private (ACR), so login/network issues are common.

---

## Slide 13 of 47 - Container Management Platforms

31. **Management tools** (build/create/manage containers): docker, rancher, OpenShift, rkt, podman, Nomad.
32. **Orchestration** (scale/track across hosts): Kubernetes, EKS, AKS, ECS.
33. Banking example - why it matters: a laptop lab uses plain Docker; production bank platforms often use AKS/OpenShift so failed interest pods restart automatically across many nodes.

---

## Slide 14 of 47 - Images and Containers (layer model)

34. Image = logical grouping of layers plus metadata. Layers are immutable. Base layer at bottom, then change layers.
35. Container = image launched into memory; layers unpacked; root filesystem created; writable layer added; process tree and network interface created.
36. Banking example - why it matters: if someone writes a temporary fix inside a running interest container, that fix dies when the container is deleted. Banks must put lasting fixes into a new image build, not into the writable layer.

---

## Slide 15 of 47 - Working with docker - Common Command List

37. Image commands: `docker search`, `pull`, `images`, `history`, `rmi`.
38. Container commands: `run`, `ps`, `stop`, `start`, `restart`, `rm`, `exec`, `commit`.
39. Banking example - why it matters: these are the daily verbs of bank container support - pull the approved tag, run it, check `ps`, read failures, stop for maintenance, start again after the DB window.

---

## Slide 16 of 47 - Lab 1 Docker Basic commands

40. Objective: pull an image, launch containers, interact with containers. Reference: Lab Document Section A.
41. Banking example - why it matters: Lab 1 skills are exactly what you use to validate a new UAT host can pull `interest-batch:2.3` and keep a container running before month-end.

---

## Slide 17 of 47 - Environment Variables outline

42. Outline: Environment Variables in Docker; Why Use Them; Practical Applications; Working with Environment variables.
43. Banking example - why it matters: banks must not bake PROD database passwords into images. Environment variables are the simple first pattern for separating secrets and config from code.

---

## Slide 18 of 47 - Environment Variables in Docker

44. Environment variables pass configuration at runtime as key-value pairs, without changing application code. Useful when DEV, test and production differ.
45. Banking example - why it matters: one InterestBatch image can use `RATE=3.5` in PROD and `RATE=3.0` in an old replay environment without rebuilding Java.

---

## Slide 19 of 47 - Why Use Environment Variables in Docker?

46. Reasons: Configuration Flexibility; Portability and modularity; Separation of concerns (12-factor style).
47. Banking example - why it matters: compliance likes separation of concerns - application code is reviewed once; environment-specific values are controlled by ops/secrets management per environment.

---

## Slide 20 of 47 - Practical Applications of Environment Variables

48. Uses: Database Connections; Feature Flags; API Keys and Credentials; Configuration Settings; Dynamic Hostnames and Ports.
49. Banking example - why it matters:
    1. DB URL points InterestBatch to UAT or PROD rates DB.
    2. Feature flag can disable a new senior-citizen bonus in PROD if unsafe.
    3. API tokens for internal rate services stay out of Git.
    4. `LOG_LEVEL=DEBUG` in DEV and `ERROR` in PROD.
    5. In Kubernetes, DB hostname is injected dynamically.

---

## Slide 21 of 47 - Working with Environment variables

50. Pass with `docker run ... -e ENV_NAME=value`.
51. Inspect logs with `docker logs`.
52. Linking containers is legacy; prefer docker network.
53. Move files with `docker cp`.
54. Banking example - why it matters: during a failed overnight run, ops checks `docker logs interest-batch` and `docker exec ... env` to confirm the rate and DB host were set correctly before blaming the Java code.

---

## Slide 22 of 47 - Lab 2 Running containerized apps

55. Objective: pass environment values and files to containers; inspect containers. Reference: Lab Document Section B.
56. Banking example - why it matters: this lab mirrors starting a rates MySQL/Postgres container with credentials via `-e`, then verifying with logs - the same habit used in bank UAT bring-ups.

---

## Slide 23 of 47 - Container Lifecycle & Image management outline

57. Outline: Building your own image using Dockerfile; Dockerfile Commands; Docker build; Container Life Cycle.
58. Banking example - why it matters: banks need a controlled life cycle - build, run, stop, replace, rollback - because financial jobs cannot depend on a snowflake server.

---

## Slide 24 of 47 - Building your own image using Dockerfile

59. Dockerfile is a text file of ordered commands to build an image. Default name `Dockerfile`; other names need `-f`.
60. Sample idea from deck: FROM ubuntu, RUN install tools, COPY app, ENTRYPOINT.
61. Banking example - why it matters: a Core Java `InterestCalculator` Dockerfile is reviewable in a Pull Request. That review is part of secure banking delivery.

---

## Slide 25 of 47 - Dockerfile Commands

62. Key instructions: FROM, ADD, COPY, RUN, WORKDIR, EXPOSE, CMD, ENTRYPOINT, ARG, LABEL, USER, ONBUILD, HEALTHCHECK.
63. Banking example - why it matters:
    1. FROM approved Java base image only.
    2. COPY the JAR/source, do not download unknown binaries at build from the internet if policy forbids it.
    3. USER non-root reduces blast radius if the container is compromised.
    4. HEALTHCHECK helps orchestration restart a hung batch sidecar or API.

---

## Slide 26 of 47 - Sample Dockerfile

64. Deck samples include a Java JRE image copying a JAR and exposing a port, and a non-root USER example.
65. Banking example - why it matters: the Java sample is closest to bank batch/API packaging - start from JRE/JDK, copy artifact, set entrypoint to `java ...`. Non-root USER matches bank security hardening baselines.

---

## Slide 27 of 47 - Container Life Cycle

66. Flow: Dockerfile/build â†' image; run â†' container; stop/start/restart; rm; exec; commit; push/pull via registry/Docker Hub.
67. Banking example - why it matters: rollback in a bank is often "stop bad tag, run previous tag". Understanding life-cycle commands makes that rollback minutes, not hours.

---

## Slide 28 of 47 - Lab 3 Creating Images using Dockerfile

68. Objective: build images using Dockerfile; containerize an application. Reference: Lab Document Section C.
69. Banking example - why it matters: after this lab you can package a simple Core Java interest class the same way the bank packages real batch JARs.

---

## Slide 29 of 47 - Container Registry outline

70. Outline: Container Registry and Repositories; Docker Hub; Docker Tags.
71. Banking example - why it matters: without a registry, each server has a different copy of "the build". Banks need one trusted store of versioned images.

---

## Slide 30 of 47 - Container Registry and Repositories

72. Registry stores images/packages, often privately. Organised into repositories with multiple versions.
73. Popular options: Docker Hub, ECR, ACR, Quay, Artifactory, GCR, Harbor.
74. Push/pull manually or via pipelines with authentication. Images can be signed and verified.
75. Banking example - why it matters: banks typically use private ACR/Artifactory/Harbor. Image signing helps prove production did not run a tampered interest-calculation image.

---

## Slide 31 of 47 - Docker Hub

76. Features: Repositories; Teams & Organizations; Official Images; Verified Publisher Images; Builds; Webhooks.
77. Banking example - why it matters: Docker Hub is fine for learning and public base images. Real bank application images stay private. Still, Official base images from Hub/Mirrored internal caches are often the approved starting point.

---

## Slide 32 of 47 - Docker Tags & Repositories

78. A tag is an alias to an image ID for versions. Valid ASCII naming rules apply.
79. Examples: `docker tag 0e5574283393 fedora/httpd:version1.0` and tagging by name.
80. If tag omitted, defaults toward `latest`.
81. Banking example - why it matters: production must run `interest-batch:2026.07.1`, never `latest`. Auditors ask which exact version posted interest on 1 August.

---

## Slide 33 of 47 - Demo: Registering to Docker Hub

82. Demo flow: hub.docker.com â†' create repository.
83. Banking example - why it matters: the demo teaches registry login/push habits. In the bank you will do the same against ACR with corporate credentials or pipeline service principals.

---

## Slide 34 of 47 - Lab 4 Uploading Images to Docker Hub

84. Objective: tag and upload the image created earlier. Reference: Lab Document Section D.
85. Banking example - why it matters: this is the promotion path - build once, push a version, let UAT/PROD pull the same digest.

---

## Slide 35 of 47 - Container Networking (section title)

86. Section break: Container Networking.
87. Banking example - why it matters: interest jobs must reach the rates database, but the database must not be open to the whole internet. Networking design is a security control.

---

## Slide 36 of 47 - Container Networking outline

88. Outline: Container Networking Types; Bridge; Networking Commands.
89. Banking example - why it matters: choosing bridge vs host vs none decides whether a batch is isolated, shareable, or dangerously exposed.

---

## Slide 37 of 47 - Container Networking Types

90. **None:** no network; strong isolation.
91. **Bridge:** same-host communication; default `docker0`; not directly reachable from outside without publish.
92. **Host:** shares host network; fast but weaker isolation.
93. **Overlay:** across hosts; used by orchestrators.
94. **Underlay:** exposes host interfaces; simpler/more efficient than bridge in some designs.
95. Banking example - why it matters: an offline interest recalculation can use `none` if it only needs local files. Online services use bridge/overlay with only the front door published.

---

## Slide 38 of 47 - Container Networking - Bridge

96. Bridge points: container IP; port mapping; bind to specific IP; auto host port; same daemon; user-defined bridges; linked containers can share env vars (legacy pattern).
97. Publish example: `docker run -d -p 8080:8080 tomcat`.
98. Banking example - why it matters: publish only the customer-facing port. Keep `rates-db` unpublished on a user-defined bridge so only `interest-batch` can talk to it by name.

---

## Slide 39 of 47 - Container Networking Commands

99. Commands: `docker network ls`; `docker inspect c1 | grep IPAddress`; `docker network inspect bridge`.
100. Banking example - why it matters: when InterestBatch cannot connect to DB, these commands show whether both containers are on the same network and what IPs/names exist.

---

## Slide 40 of 47 - Lab 5 Docker networking

101. Objective: connect application and database on a user-defined bridge and access the app. Reference: Lab Document Section E.
102. Banking example - why it matters: this is the standard bank pattern - app + DB on private network, only app port exposed to testers or load balancers.

---

## Slide 41 of 47 - Container Storage (section title)

103. Section break: Container Storage.
104. Banking example - why it matters: financial output files and audit logs must survive container replacement. Storage design is a regulatory need, not an optional extra.

---

## Slide 42 of 47 - Container Storage outline

105. Outline: Sharing Data between Containers; Volume vs Bind Mount.
106. Banking example - why it matters: overnight jobs need a clear answer to "where is today's account extract and where do posted-interest files go?"

---

## Slide 43 of 47 - Sharing Data between Containers

107. Containers are ephemeral; data may need to persist after deletion.
108. `docker cp` moves files either way.
109. Persistence options: Data Volumes; Bind mount; Tempfs.
110. Banking example - why it matters: if posted-interest files lived only inside the container, deleting the container would destroy evidence needed for audit and customer disputes.

---

## Slide 44 of 47 - Volumes, Bind Mounts, tmpfs

111. Volumes live under Docker-managed host storage (`/var/lib/docker/volumes/`) and can mount into many containers.
112. Bind mounts map any host directory.
113. tmpfs is memory-only and temporary.
114. Banking example - why it matters: use volumes for batch input/output and DB data; use bind mounts for developer source on a laptop; use tmpfs for short-lived secrets that should not touch disk.

---

## Slide 45 of 47 - Volume vs Bind Mount

115. Volumes: share among containers; decouple host paths; easier migrate/backup between Docker hosts.
116. Bind Mount: share specific host files/folders; host filesystem stays as-is.
117. Banking example - why it matters: production prefers volumes managed by the platform. Bind-mounting random server folders creates hidden dependencies that fail when the host is rebuilt.

---

## Slide 46 of 47 - Lab 6 Docker Volumes

118. Objective: send input to a containerized app and capture output via volumes. Reference: Lab Document Section F.
119. Banking example - why it matters: this lab is the overnight batch story - read account extract from input volume, write interest result to output volume, delete container, keep the files.

---

## Slide 47 of 47 - Thank You

120. Closing slide: Thank You / Happy Learning.
121. Banking example - why it matters: after this course, a new bank IT joiner should be able to package a Core Java job, configure it safely, network it privately, persist its files, and promote a versioned image through environments.

---

## Quick reminder of the core flow for bank IT

122. Write Core Java â†' describe it in a Dockerfile â†' build an image â†' tag a version â†' push to private registry â†' run with `-e` config â†' put app and DB on a private network â†' mount volumes for input/output/audit â†' replace containers freely without losing financial data.

---

# Part 17. Solved Examples (from the Lab document)

## Example A - Basic image and container commands (Lab Section A)

148. **Problem:** Pull an Ubuntu image, run a container, install Python inside it, and save it as a new image.
148a. Banking example - why it matters: these are the same first checks used when validating a new bank UAT Docker host before an interest-batch release.
149. **Step 1 - check the installation:**
     ```bash
     docker info
     # Output: Containers: 0 , Images: 0 , Server Version: 24.0.5 , Storage Driver: overlay2 ...

     docker version
     # Output: Client version and Server (Engine) version
     ```
150. **Step 2 - list local images and search the hub:**
     ```bash
     docker images
     # Output: REPOSITORY   TAG   IMAGE ID   CREATED   SIZE      (empty at first)

     docker search ubuntu
     # Output: NAME     DESCRIPTION                 STARS   OFFICIAL
     #         ubuntu   Ubuntu is a Debian-based..  16000   [OK]
     ```
151. **Step 3 - pull and verify:**
     ```bash
     docker pull ubuntu:latest
     # Output: latest: Pulling from library/ubuntu
     #         a1b2c3d4: Pull complete
     #         Status: Downloaded newer image for ubuntu:latest

     docker images
     # Output: ubuntu   latest   e4c58958181a   2 weeks ago   77.8MB
     ```
152. **Step 4 - create and inspect a container:**
     ```bash
     docker run -itd --name myubuntu e4c58958181a
     # Output: 9f2a7c1b4d5e6f708192a3b4c5d6e7f8

     docker ps -a
     # Output: CONTAINER ID  IMAGE   COMMAND  STATUS        NAMES
     #         9f2a7c1b4d5e  ubuntu  "/bin/bash"  Up 5 seconds  myubuntu
     ```
     Here `-i` and `-t` keep an interactive terminal alive so the container does not exit immediately, and `-d` runs it in the background.
153. **Step 5 - stop and start:**
     ```bash
     docker stop myubuntu     # Output: myubuntu
     docker ps                # Output: (empty - it is no longer running)
     docker ps -a             # Output: STATUS = Exited (0) 10 seconds ago
     docker start myubuntu    # Output: myubuntu
     ```
154. **Step 6 - get inside the container and install Python:**
     ```bash
     docker exec -it myubuntu sh
     # you now get the # prompt, inside the container

     # apt update -y
     # apt install -y python3
     # exit
     ```
155. **Step 7 - save the customised container as a new image:**
     ```bash
     docker commit myubuntu ubuntu-python
     # Output: sha256:7b3c9d1e5f8a2b4c6d7e8f90a1b2c3d4e5f6a7b8

     docker images
     # Output: ubuntu-python   latest   7b3c9d1e5f8a   5 seconds ago   142MB
     #         ubuntu          latest   e4c58958181a   2 weeks ago     77.8MB
     ```
156. **Step 8 - verify the new image works:**
     ```bash
     docker run -itd --name mypython ubuntu-python
     docker exec -it mypython python3 --version
     # Output: Python 3.10.12
     ```
157. **Step 9 - try to delete the base image and observe:**
     ```bash
     docker rmi ubuntu
     # Output: Error response from daemon: conflict: unable to remove repository
     #         reference "ubuntu" (must force) - container 9f2a7c1b4d5e is using it
     ```
158. **Explanation of the error:** Docker protects images that are still referenced by containers. You must first remove the containers (`docker rm -f myubuntu`) and then remove the image. This is the layer model at work - the new `ubuntu-python` image is built *on top of* the ubuntu layers, so those layers cannot simply disappear.

## Example B - Running containerized applications with environment variables (Lab Section B)

159. **Problem:** Run a web application and a MySQL database as containers, pass configuration through environment variables, and copy a file into a container.
159a. Banking example - why it matters: bank UAT databases and rate services are commonly started with `-e` credentials, then verified with `docker logs` before any Java batch is pointed at them.
160. **Step 1 - run a web application and reach it:**
     ```bash
     docker container run -d -p 80:80 tutum/hello-world
     # Output: 4c8e1a9b2d3f...

     docker ps
     # Output: CONTAINER ID  IMAGE               PORTS                NAMES
     #         4c8e1a9b2d3f  tutum/hello-world   0.0.0.0:80->80/tcp   eager_bose

     curl http://localhost:80
     # Output: <html>... Hello world! ... </html>
     ```
     `-p 80:80` means "host port 80 forwards to container port 80". Without it, the application would run but be unreachable.
161. **Step 2 - check the logs:**
     ```bash
     docker logs 4c8e1a9b2d3f
     # Output: [Sun Jul 26 10:20:11 2026] apache2 -D FOREGROUND
     #         10.0.0.1 - - "GET / HTTP/1.1" 200
     ```
162. **Step 3 - run MySQL with environment variables:**
     ```bash
     docker run --name db \
       -e MYSQL_ROOT_PASSWORD=docker \
       -e MYSQL_DATABASE=docker \
       -e MYSQL_USER=docker \
       -e MYSQL_PASSWORD=docker \
       -d mysql:5.6
     # Output: 2f7d4a1c8b9e...
     ```
     The image contains no database and no user. The four `-e` variables tell it, at start-up, to create a database named `docker` and a user named `docker`. The same image with different values would create a completely different setup - this is the whole point of environment variables.
163. **Step 4 - print the variables inside the container:**
     ```bash
     docker exec -it db env
     # Output: PATH=/usr/local/sbin:...
     #         MYSQL_ROOT_PASSWORD=docker
     #         MYSQL_DATABASE=docker
     #         MYSQL_USER=docker
     #         MYSQL_PASSWORD=docker
     #         MYSQL_VERSION=5.6.51
     ```
164. **Step 5 - log in to MySQL and create a table:**
     ```bash
     docker exec -it db mysql -u docker -p
     # Enter password: docker

     mysql> use docker;
     # Output: Database changed

     mysql> show tables;
     # Output: Empty set

     mysql> CREATE TABLE Persons (
         ->   PersonID int,
         ->   LastName varchar(255),
         ->   FirstName varchar(255),
         ->   Address varchar(255),
         ->   City varchar(255)
         -> );
     # Output: Query OK, 0 rows affected

     mysql> show tables;
     # Output: +------------------+
     #         | Tables_in_docker |
     #         +------------------+
     #         | Persons          |
     #         +------------------+
     ```
165. **Important observation:** if you now run `docker rm -f db`, this table is gone forever, because it lives in the container's writable layer. That is exactly the problem volumes solve.
166. **Step 6 - copy a file into the container:**
     ```bash
     touch README.txt
     echo "This is a help document" > README.txt

     docker cp README.txt db:/

     docker exec -it db ls /
     # Output: bin  boot  dev  etc  home  README.txt  root  usr  var
     ```

## Example C - Creating images using a Dockerfile (Lab Section C)

167. **Problem 1:** Build an Ubuntu image that already has git and curl installed.
167a. Banking example - why it matters: a reviewed Dockerfile is how the bank proves InterestBatch was built the approved way, not by hand on a server.
168. **Solution - create a file named `Dockerfile`:**
     ```dockerfile
     FROM ubuntu:latest
     MAINTAINER author
     RUN apt-get update -y && apt-get install -y git && apt-get install -y curl
     ```
169. **Build it:**
     ```bash
     docker build -t myubuntu-git .
     # Output: [+] Building 42.3s
     #         => [1/2] FROM docker.io/library/ubuntu:latest
     #         => [2/2] RUN apt-get update -y && apt-get install -y git ...
     #         => exporting to image
     #         => naming to docker.io/library/myubuntu-git
     ```
     The `-t` gives the image a name and the `.` tells Docker the build context is the current folder.
170. **Verify:**
     ```bash
     docker container run -itd --name c1 myubuntu-git
     docker exec -it c1 git version
     # Output: git version 2.34.1
     ```
171. **Note on the RUN line:** the three commands are joined with `&&` on purpose. Each `RUN` creates a new layer, so combining them keeps the image smaller. Also, `apt-get update` must be in the *same* RUN as the install, otherwise a cached update layer can cause the install to fail later.
172. **Problem 2:** Containerize a simple Node.js application.
173. **Create `index.js`:**
     ```javascript
     var http = require('http');
     var server = http.createServer(function(request, response) {
         response.statusCode = 200;
         response.setHeader('Content-Type', 'text/plain');
         response.end('Welcome to the World !');
     });
     server.listen(3000, function() {
         console.log('Server running on port 3000');
     });
     ```
174. **Create `Dockerfile_node`:**
     ```dockerfile
     FROM node
     WORKDIR /app
     COPY index.js .
     EXPOSE 3000
     CMD node index.js
     ```
175. **Line by line:** `FROM node` starts from an image that already has Node.js installed. `WORKDIR /app` creates and moves into `/app`. `COPY index.js .` copies the file into `/app`. `EXPOSE 3000` documents the port. `CMD node index.js` is the command that runs when the container starts.
176. **Build and test:**
     ```bash
     docker build -t myimage -f Dockerfile_node .
     # Output: => naming to docker.io/library/myimage

     docker run -d -p 3000:3000 --name nodec myimage
     curl http://localhost:3000
     # Output: Welcome to the World !
     ```
177. Note the `-f Dockerfile_node`, needed because the file is not called plain `Dockerfile`.

## Example D - Pushing an image to Docker Hub (Lab Section D)

178. **Problem:** Upload the image built in Section C to your own Docker Hub repository.
178a. Banking example - why it matters: pushing a versioned tag is how the same tested interest image is promoted from build to UAT to PROD through a private registry.
179. **Solution:**
     ```bash
     # 1. Register at hub.docker.com and create a repository, say "myapp"

     docker login
     # Username: ravikumar
     # Password: ********
     # Output: Login Succeeded

     docker images
     # Output: myimage   latest   8d3f1a2b7c9e   3 minutes ago   1.1GB

     docker tag 8d3f1a2b7c9e ravikumar/myapp:1.0
     # (no output on success)

     docker images
     # Output: myimage           latest  8d3f1a2b7c9e  3 minutes ago  1.1GB
     #         ravikumar/myapp   1.0     8d3f1a2b7c9e  3 minutes ago  1.1GB

     docker push ravikumar/myapp:1.0
     # Output: The push refers to repository [docker.io/ravikumar/myapp]
     #         5f2c8a1b: Pushed
     #         1.0: digest: sha256:9a8b7c6d... size: 1573
     ```
180. **Explanation:** notice that both names share the **same image ID** `8d3f1a2b7c9e`. A tag is only an alias - no copy of the image was made. The push works only because the tag is in the form `<dockerhub-username>/<repository>:<version>`; without that prefix Docker would not know where to send it.
181. Any teammate can now run `docker pull ravikumar/myapp:1.0` on any machine and get the identical application.

## Example E - Container networking (Lab Section E)

182. **Problem:** Make two containers - a service and a database - talk to each other by placing them in the same network.
182a. Banking example - why it matters: InterestBatch should reach `rates-db` by name on a private network, while the database port stays unpublished to the outside world.
183. **Step 1 - inspect the existing networks:**
     ```bash
     docker network ls
     # Output: NETWORK ID     NAME     DRIVER   SCOPE
     #         b33f1a2c9d4e   bridge   bridge   local
     #         7c2d8e1f5a6b   host     host     local
     #         2a9b4c7d1e8f   none     null     local

     docker network inspect b33
     # Output: shows subnet 172.17.0.0/16, gateway 172.17.0.1,
     #         and the list of containers attached with their IPs
     ```
     Note that only the first few characters of the network ID are needed.
184. **Step 2 - create a user-defined bridge and attach both containers:**
     ```bash
     docker network create myapp-net
     # Output: 5e8f2a1b9c3d...

     docker run -d --name pgdb --network myapp-net \
       -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=appdb postgres

     docker run -d --name springapp --network myapp-net -p 8080:8080 \
       -e DB_HOST=pgdb -e DB_NAME=appdb myspringapp:1.0
     ```
185. **Step 3 - verify:**
     ```bash
     docker network inspect myapp-net
     # Output: Containers: pgdb (172.18.0.2), springapp (172.18.0.3)

     curl http://localhost:8080/accounts
     # Output: [{"id":1,"name":"Test"}]
     ```
186. **The key point:** the Spring application connects to the database using the hostname `pgdb`, which is simply the container name. On a **user-defined** bridge, Docker provides automatic name resolution between containers. On the **default** bridge this does not work and you would have to find and hardcode the IP address, which changes every restart.
187. **The second key point:** only `springapp` has `-p 8080:8080`. The database has no published port, so it is reachable by the application but not from outside the host. This is a basic and important security practice.

## Example F - Volumes (Lab Section F)

188. **Problem:** Run a Node application that reads its input from one volume and writes its output to another, so the data survives the container.
188a. Banking example - why it matters: overnight account extracts and posted-interest result files must remain on volumes after the batch container is deleted, for operations reruns and audit.
189. **Step 1 - get the code and build the image:**
     ```bash
     git clone https://github.com/slathas24/ContainersLab.git
     cd ContainersLab/nodeapp/
     ls -l
     # Output: Dockerfile  index.js  package.json  users.json

     cat Dockerfile

     docker build -t nodeapp:1.0 .
     docker images
     # Output: nodeapp   1.0   6b2e9f3a1c8d   10 seconds ago   950MB
     ```
190. **Step 2 - edit the input file** (`nano users.json`) and add your username to it.
191. **Step 3 - create the two volumes:**
     ```bash
     docker volume create input
     # Output: input
     docker volume create output
     # Output: output

     ls -l /var/lib/docker/volumes/
     # Output: drwx-----x  input
     #         drwx-----x  output

     ls -l /var/lib/docker/volumes/input/_data/
     # Output: (empty - nothing stored yet)
     ```
192. **Step 4 - put the input data into the volume:**
     ```bash
     cp users.json /var/lib/docker/volumes/input/_data/

     ls -l /var/lib/docker/volumes/input/_data/
     # Output: -rw-r--r--  users.json
     ```
193. **Step 5 - run the container with both volumes mounted:**
     ```bash
     docker run -d --name n1 -p 3000:3000 \
       --mount src=input,target=/app/input \
       --mount src=output,target=/app/output \
       nodeapp:1.0

     docker ps
     # Output: CONTAINER ID  IMAGE        PORTS                    NAMES
     #         a7f3c9d2e5b1  nodeapp:1.0  0.0.0.0:3000->3000/tcp   n1
     ```
194. **Step 6 - trigger the application and check the output:**
     ```bash
     curl http://localhost:3000
     # Output: Hello <your username>

     cat /var/lib/docker/volumes/output/_data/user.log
     # Output: 2026-07-26T10:45:12  request served for <your username>
     ```
195. **The whole point of the exercise:** now run `docker rm -f n1` and then look at the log file again - it is still there. The container was destroyed but the data lives on in the volume, on the host. Start a new container with the same mounts and it continues from where the old one left off.
196. Note the two directions: `input` is data going *into* the application, and `output` is data coming *out of* it. Both are ordinary folders on the host under `/var/lib/docker/volumes/<name>/_data/`, which makes them easy to back up.

---

# Part 18. Real-life example in the Finance domain (kept simple)

197. **Scenario:** A bank has a small service called **EMI Calculator API**. A customer enters the loan amount, the interest rate and the number of months, and the service returns the monthly EMI. It is used by the bank's website and by the mobile app.
198. **The problem before containers.** The developer builds it with Java 17 on his laptop and it works. The test server has Java 11, so the application refuses to start. The UAT server has Java 17 but an old version of the PostgreSQL driver, so the loan rates cannot be read from the database. Every deployment needs a two-page installation document, and every environment ends up slightly different. A release that should take one hour takes two days.
199. **The solution - one Dockerfile:**
     ```dockerfile
     FROM azul/zulu-openjdk-alpine:17-jre
     WORKDIR /app
     COPY target/emi-calculator.jar .
     EXPOSE 8080
     ENTRYPOINT ["java", "-jar", "./emi-calculator.jar"]
     ```
200. **Build it once:**
     ```bash
     docker build -t bank/emi-calculator:1.0 .
     ```
     The Java runtime, the application and all its libraries are now inside one image. Nothing needs to be installed on any server except Docker itself.
201. **The same image runs in every environment; only the configuration changes.** This is where environment variables earn their place:
     ```bash
     # Development
     docker run -d -p 8080:8080 --name emi-dev \
       -e DB_HOST=dev-db -e RATE_SOURCE=static -e LOG_LEVEL=DEBUG \
       bank/emi-calculator:1.0

     # Production
     docker run -d -p 8080:8080 --name emi-prod \
       -e DB_HOST=prod-db -e RATE_SOURCE=live -e LOG_LEVEL=ERROR \
       bank/emi-calculator:1.0
     ```
     The image ID is identical in both cases. This matters in banking, because the auditor can be shown that the software tested in UAT is bit-for-bit the same software running in production.
202. **The rates database runs as a second container** on a user-defined bridge:
     ```bash
     docker network create bank-net
     docker run -d --name rates-db --network bank-net -e POSTGRES_PASSWORD=**** postgres
     docker run -d --name emi-api --network bank-net -p 8080:8080 -e DB_HOST=rates-db bank/emi-calculator:1.0
     ```
     The API connects to the database simply as `rates-db`. The database has **no published port**, so no one outside the host can reach the customer rate data directly - only through the API. That is a real security control, not just a convenience.
203. **A volume protects the data.** The transaction log and the database files are put on a volume, so if the container is deleted or upgraded, the records survive:
     ```bash
     docker volume create emi-audit-logs
     docker run -d --name emi-api --mount src=emi-audit-logs,target=/app/logs ... bank/emi-calculator:1.0
     ```
     Banking regulations require these logs to be retained. Without a volume, they would vanish the moment the container was replaced.
204. **Handling the month-end rush.** On the 1st of the month, loan enquiries triple. The operations team simply starts four more containers from the same image behind a load balancer, and stops them the next day. Each one starts in about two seconds. With virtual machines this would mean provisioning four new servers.
205. **Versioning and rollback.** Every release is tagged and pushed to the bank's private registry (Azure Container Registry, not the public Docker Hub, because the image is bank property):
     ```bash
     docker tag bank/emi-calculator:1.0 bankacr.azurecr.io/emi-calculator:2026.07.1
     docker push bankacr.azurecr.io/emi-calculator:2026.07.1
     ```
     If version `2026.07.1` turns out to compute EMI wrongly for 15-year loans, the fix is to stop it and start `2026.06.4` again. Rollback takes seconds, not a night of reinstallation.
206. **How it joins up with Git and Azure Pipelines** (from the other summary): a developer merges his change into `develop`, the pipeline builds the JAR, builds the Docker image, tags it with the build number, pushes it to the registry and deploys it to the test environment. Nobody touches a server by hand, and every image can be traced back to the exact commit and Work Item that produced it.

---

# Part 19. Command quick reference

| # | Command | What it does |
|---|---|---|
| 1 | `docker info` / `docker version` | Details of the Docker installation |
| 2 | `docker search <image>` | Search Docker Hub |
| 3 | `docker pull <image>:<tag>` | Download an image |
| 4 | `docker images` | List local images |
| 5 | `docker history <image>` | Show the layers of an image |
| 6 | `docker rmi <image>` | Delete a local image |
| 7 | `docker run -itd --name <n> -p 8080:80 <image>` | Create and start a container |
| 8 | `docker run -e KEY=value <image>` | Pass an environment variable |
| 9 | `docker ps` / `docker ps -a` | List running / all containers |
| 10 | `docker stop` / `start` / `restart <id>` | Control a container's state |
| 11 | `docker rm -f <id>` | Delete a container |
| 12 | `docker exec -it <id> sh` | Open a shell inside a running container |
| 13 | `docker exec -it <id> env` | Print the container's environment variables |
| 14 | `docker logs <id>` | View the container's console output |
| 15 | `docker commit <container> <newimage>` | Save a container as a new image |
| 16 | `docker cp <file> <container>:/path` | Copy a file into a container |
| 17 | `docker build -t <name> [-f <file>] .` | Build an image from a Dockerfile |
| 18 | `docker login` | Authenticate to Docker Hub |
| 19 | `docker tag <imageid> <repo>/<app>:<tag>` | Give an image a registry name and version |
| 20 | `docker push <repo>/<app>:<tag>` | Upload the image to the registry |
| 21 | `docker network ls` | List networks |
| 22 | `docker network create <name>` | Create a user-defined bridge |
| 23 | `docker network inspect <name>` | See subnet, gateway and attached containers |
| 24 | `docker inspect <c> \| grep IPAddress` | Find a container's IP address |
| 25 | `docker volume create <name>` | Create a volume |
| 26 | `docker run --mount src=<vol>,target=<path>` | Attach a volume to a container |
| 27 | `docker run --mount type=bind,source=/hostdir,target=/app1` | Attach a bind mount |
| 28 | `ls /var/lib/docker/volumes/<name>/_data/` | Look at a volume's data on the host |

---

# Part 20. Commonly used Docker commands - a daily guide for new developers

This section is written for someone who is new to Docker. Each command is explained in plain words, followed by a small example you can actually type. Read it in order - the examples build on each other.

## 20.1 First, the mental model

209. Remember these three words and you will not get confused:
    - **Dockerfile** = the recipe (a text file you write).
    - **Image** = the packed dish (read-only, you store and share it).
    - **Container** = the dish being served (a running copy of the image).
210. Most commands fall into one of four groups: commands about **images**, commands about **containers**, commands about **networks**, and commands about **volumes**.
211. A very useful habit: `docker images` tells you what you *have*, and `docker ps -a` tells you what is *running or stopped*. When you are lost, run these two first.

## 20.2 Checking your setup

212. **`docker version`** - shows the Docker client and engine versions. Use it to confirm Docker is installed and the engine is actually running.
    ```bash
    docker version
    # Output: Client: Version 24.0.5
    #         Server: Docker Engine - Community  Version 24.0.5
    ```
    If you see "Cannot connect to the Docker daemon", Docker Desktop is not started.
213. **`docker info`** - shows a fuller picture: how many containers and images you have, the storage driver and the disk in use.
    ```bash
    docker info
    # Output: Containers: 3 (Running: 1, Stopped: 2)
    #         Images: 5
    #         Server Version: 24.0.5
    ```

## 20.3 Getting an image

214. **`docker search <name>`** - searches Docker Hub from the command line.
    ```bash
    docker search nginx
    # Output: NAME    DESCRIPTION                     STARS   OFFICIAL
    #         nginx   Official build of Nginx         19000   [OK]
    ```
    Always prefer images marked `[OK]` (official) or from a verified publisher.
215. **`docker pull <image>:<tag>`** - downloads an image to your machine. If you skip the tag, Docker assumes `latest`.
    ```bash
    docker pull nginx:1.25
    # Output: 1.25: Pulling from library/nginx
    #         Status: Downloaded newer image for nginx:1.25
    ```
216. **`docker images`** - lists the images you already have locally.
    ```bash
    docker images
    # Output: REPOSITORY  TAG    IMAGE ID       CREATED       SIZE
    #         nginx       1.25   a8758716bb6a   2 weeks ago   187MB
    ```
217. **`docker history <image>`** - shows the layers inside an image and the instruction that created each one. Useful when you want to know why an image is so big.
    ```bash
    docker history nginx:1.25
    ```

## 20.4 Running a container (the most important command)

218. **`docker run`** - creates a container from an image and starts it. This one command has a few flags you will use constantly:
    - `-d` â†' run in the background (detached). Without it, your terminal stays stuck showing the logs.
    - `--name <name>` â†' give it a readable name, instead of a random one like `eager_bose`.
    - `-p <hostport>:<containerport>` â†' publish a port so you can reach the app from your browser.
    - `-e KEY=value` â†' pass a configuration value.
    - `-it` â†' give an interactive terminal (used with shells, not with web apps).
219. **Small example - run a web server and open it:**
    ```bash
    docker run -d --name web -p 8080:80 nginx:1.25
    # Output: 7f3c9d2e5b1a...

    curl http://localhost:8080
    # Output: <html><h1>Welcome to nginx!</h1></html>
    ```
    Read `-p 8080:80` as "port 8080 on **my machine** goes to port 80 **inside the container**". Change the left number if 8080 is already busy.
220. **Small example - pass configuration with `-e`:**
    ```bash
    docker run -d --name db -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=bankdb mysql:5.6
    ```
    The same image becomes a different setup just by changing these values. No rebuild needed.

## 20.5 Looking at what is running

221. **`docker ps`** - lists **running** containers only.
    ```bash
    docker ps
    # Output: CONTAINER ID  IMAGE       STATUS         PORTS                  NAMES
    #         7f3c9d2e5b1a  nginx:1.25  Up 2 minutes   0.0.0.0:8080->80/tcp   web
    ```
222. **`docker ps -a`** - lists **all** containers, including the stopped ones. If your container "disappeared", it is almost always here with status `Exited`.
    ```bash
    docker ps -a
    # Output: STATUS = Exited (0) 5 minutes ago
    ```
223. **`docker logs <name>`** - prints whatever the application inside wrote to the console. **This is the first command to run when something does not work.**
    ```bash
    docker logs web
    # Output: 172.17.0.1 - - "GET / HTTP/1.1" 200

    docker logs -f web       # -f follows the log live, like tail -f
    ```
224. **`docker inspect <name>`** - dumps every detail of a container in JSON: its IP address, mounts, environment variables and network.
    ```bash
    docker inspect web | grep IPAddress
    # Output: "IPAddress": "172.17.0.2"
    ```

## 20.6 Going inside a running container

225. **`docker exec -it <name> <command>`** - runs a command inside a container that is already running. This is how you "log in" and look around.
    ```bash
    docker exec -it web sh
    # you now get a # prompt inside the container
    # ls
    # exit
    ```
226. You can also run a single command without opening a shell:
    ```bash
    docker exec -it db env              # print the environment variables
    docker exec -it web nginx -v        # check the nginx version inside
    ```
227. **Important for beginners:** `docker exec` only works on a **running** container. If the container has exited, start it first with `docker start`.

## 20.7 Starting, stopping and removing

228. **`docker stop <name>`** - stops a running container politely. The container and its data stay on disk.
    ```bash
    docker stop web
    ```
229. **`docker start <name>`** - starts a stopped container again, with everything as you left it.
    ```bash
    docker start web
    ```
230. **`docker restart <name>`** - stop and start in one step. Useful after changing a mounted config file.
231. **`docker rm <name>`** - deletes a container permanently. Add `-f` to delete one that is still running.
    ```bash
    docker rm -f web
    # Output: web
    ```
232. **`docker rmi <image>`** - deletes an image. It will refuse if any container still uses it - remove those containers first.
    ```bash
    docker rmi nginx:1.25
    # Output: Error response from daemon: conflict: unable to remove ...
    #         (a container is still using this image)
    ```
233. **Small cleanup example** - free up space when your machine is full:
    ```bash
    docker ps -a                  # see what is lying around
    docker rm -f $(docker ps -aq) # remove all containers (careful!)
    docker system prune           # remove unused images, networks and cache
    ```

## 20.8 Building your own image

234. **`docker build -t <name>:<tag> .`** - reads the `Dockerfile` in the current folder and produces an image. The `-t` names it, and the `.` means "build using this folder".
235. **Small end-to-end example.** Create a file called `Dockerfile`:
    ```dockerfile
    FROM node:18
    WORKDIR /app
    COPY index.js .
    EXPOSE 3000
    CMD node index.js
    ```
    Then:
    ```bash
    docker build -t myapp:1.0 .
    # Output: => naming to docker.io/library/myapp:1.0

    docker run -d --name myapp-c -p 3000:3000 myapp:1.0
    curl http://localhost:3000
    # Output: Welcome to the World !
    ```
236. If your file is not named exactly `Dockerfile`, point to it with `-f`:
    ```bash
    docker build -t myapp:1.0 -f Dockerfile_node .
    ```
237. **`docker commit <container> <newimage>`** - saves a running container's current state as a new image. Handy for experiments, but **not** the right way to build production images - always prefer a Dockerfile, because that is repeatable and reviewable.

## 20.9 Moving files in and out

238. **`docker cp`** - copies files between your machine and a container, in either direction.
    ```bash
    docker cp config.properties myapp-c:/app/     # host  -> container
    docker cp myapp-c:/app/logs/app.log ./        # container -> host
    ```
239. Small example of checking it worked:
    ```bash
    docker cp README.txt db:/
    docker exec -it db ls /
    # Output: bin  boot  etc  README.txt  usr  var
    ```

## 20.10 Sharing your image through a registry

240. **`docker login`** - signs you in to Docker Hub (or your company registry).
    ```bash
    docker login
    # Username: ravikumar
    # Output: Login Succeeded
    ```
241. **`docker tag <imageid|name> <repo>/<app>:<tag>`** - gives the image the name the registry expects. A tag is only an alias; no copy is made.
    ```bash
    docker tag myapp:1.0 ravikumar/myapp:1.0
    ```
242. **`docker push <repo>/<app>:<tag>`** - uploads it.
    ```bash
    docker push ravikumar/myapp:1.0
    # Output: 1.0: digest: sha256:9a8b7c6d... size: 1573
    ```
243. Now anyone, on any machine, can run your exact application:
    ```bash
    docker pull ravikumar/myapp:1.0
    docker run -d -p 3000:3000 ravikumar/myapp:1.0
    ```

## 20.11 Letting two containers talk to each other

244. **`docker network ls`** - lists the networks that exist.
    ```bash
    docker network ls
    # Output: bridge, host, none
    ```
245. **`docker network create <name>`** - creates your own bridge network. Do this whenever two containers need to talk.
246. **Small example - an app talking to a database:**
    ```bash
    docker network create bank-net

    docker run -d --name rates-db --network bank-net \
      -e POSTGRES_PASSWORD=secret postgres

    docker run -d --name emi-api --network bank-net -p 8080:8080 \
      -e DB_HOST=rates-db myapp:1.0
    ```
247. **Why this works:** on a network you created yourself, containers can find each other **by container name**. The app simply connects to the host `rates-db`. On the default bridge this does not work and you would have to hunt for IP addresses that change on every restart.
248. Notice that the database has **no `-p` flag**. It is reachable by the app but not from outside the machine - a simple and important security habit.
249. **`docker network inspect <name>`** - confirms which containers are attached and what IPs they got.

## 20.12 Keeping data after the container is gone

250. **`docker volume create <name>`** - creates a storage area managed by Docker that outlives containers.
251. **`--mount src=<volume>,target=<path>`** - attaches it when you run a container.
252. **Small example showing why it matters:**
    ```bash
    docker volume create appdata

    docker run -d --name c1 --mount src=appdata,target=/data alpine \
      sh -c "echo 'transaction 001' > /data/ledger.txt && sleep 3600"

    docker rm -f c1                 # destroy the container completely

    docker run --rm --mount src=appdata,target=/data alpine cat /data/ledger.txt
    # Output: transaction 001       <-- the data survived
    ```
253. Without the volume, that file would have vanished the moment `c1` was deleted, because it lived in the container's thin writable layer.
254. **`docker volume ls`** lists volumes, and **`docker volume rm <name>`** deletes one. Deleting a volume deletes the data for good, so be careful.
255. Use a **bind mount** instead when you want a real folder from your own machine inside the container - very common while developing, so your code edits appear instantly:
    ```bash
    docker run -d -p 3000:3000 --mount type=bind,source=/home/ravi/code,target=/app myapp:1.0
    ```

## 20.13 A complete beginner walkthrough (all the common commands in one flow)

256. Follow these ten steps once and you will have used almost every command that matters:
    ```bash
    # 1. Confirm Docker is running
    docker version

    # 2. Get an image
    docker pull nginx:1.25
    docker images

    # 3. Run it
    docker run -d --name web -p 8080:80 nginx:1.25

    # 4. Confirm it is up and reachable
    docker ps
    curl http://localhost:8080

    # 5. Look at its logs
    docker logs web

    # 6. Go inside it
    docker exec -it web sh
    # ls ; exit

    # 7. Copy a file in
    echo "hello" > note.txt
    docker cp note.txt web:/usr/share/nginx/html/

    # 8. Stop and start it
    docker stop web
    docker ps -a          # status shows Exited
    docker start web

    # 9. Clean up
    docker rm -f web
    docker rmi nginx:1.25

    # 10. Check nothing is left
    docker ps -a
    docker images
    ```

## 20.14 Best practices for new developers

257. **Always name your containers** with `--name`. Random names are hard to work with and hard to script.
258. **Always tag your images with a real version** such as `myapp:1.4`. Never deploy `latest` to production - you can never be sure what is actually running.
259. **One application per container.** Do not put the app and the database in the same container. Run two containers and put them on a network.
260. **Read the logs before guessing.** `docker logs <name>` answers most "why is it not working" questions in seconds.
261. **Never put passwords or keys inside the Dockerfile.** They get baked into the image and anyone who pulls it can read them. Pass them with `-e` at runtime, or use your platform's secrets store.
262. **Keep images small.** Start from a slim or alpine base image, and combine `apt-get update` with the install in the same `RUN` line so you do not create extra layers or hit stale cache.
263. **Assume containers can be destroyed at any moment.** Anything you must keep - a database, logs, uploaded files - belongs on a **volume**, not inside the container.
264. **Do not publish ports you do not need.** Only the front-facing service needs `-p`. Databases and internal services should stay unpublished on a private network.
265. **Clean up regularly.** Stopped containers and unused images quietly fill your disk. `docker system prune` recovers the space, but check `docker ps -a` first so you do not delete something you still need.
266. **Build with a Dockerfile, not with `docker commit`.** A Dockerfile can be reviewed, committed to Git and rebuilt identically by anyone. A committed container is a one-off that nobody can reproduce.
267. **One sentence summary for every new developer:** pull or build an image, run it with `-d --name -p`, check it with `docker ps` and `docker logs`, go inside with `docker exec -it`, keep important data on a volume, connect related containers with your own network, and never hardcode secrets into an image.

