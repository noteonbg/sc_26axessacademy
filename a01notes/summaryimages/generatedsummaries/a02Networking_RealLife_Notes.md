# Basic Networking — Real-Life Notes for a Bank Software Engineer

Source: `Campus Content 2026/Week 1/Basic Networking .pdf`

You will build and support applications that talk to databases, payment gateways, message
queues and partner banks — all over a network. You will rarely *configure* routers or firewalls
yourself (dedicated network/infra teams do that), but **you must understand these concepts to design
apps correctly and to debug them when something breaks at 2 AM during a production incident.**

This document is a numbered walkthrough. For each concept you get: **what it is → why it matters in
real bank life → a concrete example**. Where the concept is something you can actually touch from a
Linux server (which is where bank apps run), there is a **Linux scenario + solution** using real
commands you will type in PuTTY.

> One habit to build from day one: in a bank, everything is logged, monitored and audited. When you
> run a network command on a production server, know *why* you are running it and *what* a healthy
> result looks like.

---

## 1. What a Computer Network Is (and why a bank is basically a giant network)

1. A computer network is a group of devices (servers, laptops, printers, ATMs) connected by cables,
   fibre or wireless links so they can share resources and exchange data.

**Real-life implication:** A bank is a network of networks — branch offices, ATMs, data centres,
cloud regions, and connections to external parties like Visa/Mastercard, SWIFT, and regulators. Your
application is never alone; it is one node that must reach many others reliably and securely.

**Example:** When a customer taps "Pay" in the mobile app, the request travels across the internet to
the bank's gateway, through several internal networks, to the core banking server, then out to the
card network — crossing a dozen network devices in under a second.

---

## 2. IP Address — the identity of every device

2. An IP (Internet Protocol) address is a unique address identifying a device on a network. It does
   two jobs: **identification** (who is this device) and **location addressing** (where is it, so
   packets can be routed to it). It has a **network part** and a **host part**.

**Real-life implication:** Every server your app runs on, every database it connects to, and every
API it calls has an IP address. When you write a config file (`database.host = 10.20.30.40`) you are
using an IP. Firewall rules, load balancers and monitoring are all built around IP addresses.

**Example:** Your dev server is `10.15.4.22`; the test Oracle DB is `10.15.9.5`. If your app can't
connect, the first question is always "can this IP even reach that IP?".

### Linux scenario & solution — "Which server am I on, and what's its IP?"

You SSH into a server and need to confirm its address before raising a firewall request.

```bash
ip addr show          # modern command: lists all interfaces and their IP addresses
ip -brief addr        # compact one-line-per-interface view
hostname -I           # quickest way to print just this host's IP address(es)
ifconfig              # older command, still on many boxes (may need net-tools)
```
Look for the line like `inet 10.15.4.22/24` under `eth0` — that `10.15.4.22` is your server's IP and
`/24` is its subnet size (see point 6).

---

## 3. IPv4 vs IPv6 — two formats of address

3. **IPv4** is a 32-bit address written as four numbers 0–255 (e.g. `192.168.1.10`) — about 4 billion
   addresses, which the world has run out of. **IPv6** is a 128-bit hexadecimal address in 8 groups
   separated by colons (e.g. `FDEC:BA98::600:BDFF:4:FFFF`), giving a practically unlimited supply,
   plus a simpler header, better security and better mobile support. Leading zeros can be dropped and
   one run of all-zero groups can be shortened to `::` (only once).

**Real-life implication:** Most internal bank systems still run IPv4, but you will increasingly meet
IPv6 in cloud and mobile. Your code and config must not assume an address is always four dotted
numbers — parsing/validation should handle both.

**Example:** A logging function that assumes IPv4 (`split on "."`) will break when it receives an IPv6
client address like `2001:db8::1` from a mobile customer.

---

## 4. IP Address Classes (A, B, C, D, E)

4. Classful addressing splits IPv4 into ranges: **Class A** (1.0.0.0–126.0.0.0, huge networks),
   **Class B** (128.0.0.0–191.255.0.0, medium), **Class C** (192.0.0.0–223.255.255.0, small networks),
   **Class D** (224.x–239.x, multicast) and **Class E** (240.x–255.x, experimental/reserved).

**Real-life implication:** Classes are largely a legacy/exam concept now (CIDR replaced them — point
5), but you still hear "it's a Class C" as shorthand for "a small /24 network of 254 hosts", and
multicast (Class D) matters for some market-data feeds.

**Example:** A trading system receiving live market prices may subscribe to a **multicast** (Class D)
address so one feed reaches many servers at once, instead of sending a separate copy to each.

---

## 5. Classless Addressing (CIDR) — how addresses really work today

5. CIDR (Classless Inter-Domain Routing) drops rigid classes and uses a **network prefix** length,
   e.g. `192.168.12.0/24`, where `/24` means the first 24 bits are the network and the rest are hosts.
   This allows right-sized blocks and efficient use of scarce IPv4 space.

**Real-life implication:** Firewall requests and cloud security groups are written in CIDR. When infra
asks "which CIDR range should we allow?", you need to answer with something like `10.15.4.0/24`, not a
vague "our servers".

**Example:** To let your whole app cluster reach a database, you request a firewall rule allowing
`10.15.4.0/24` (all 254 app servers) to `10.15.9.5` on port 1521, rather than adding servers one by one.

---


Here is the step-by-step breakdown of what that sentence means in practice.

---

### 1. The Scenario

Imagine you are running a web application:

* You have a **database** sitting at a specific, single IP address: `10.15.9.5`.
* The database uses **Oracle Database** (or a similar service), which listens for traffic on **port 1521**.
* You have an **application cluster** containing **254 app servers** (like web servers or microservices) that need to query this database. Their IP addresses span the range from `10.15.4.1` to `10.15.4.254`.

### 2. What `10.15.4.0/24` Represents

Instead of writing out every single IP address (`10.15.4.1`, `10.15.4.2`, `10.15.4.3` ... `10.15.4.254`), CIDR notation allows you to summarize all 254 usable host addresses into **one single expression**: `10.15.4.0/24`.

* **`10.15.4.0`**: The base network address.
* **`/24`**: Indicates that the first 24 bits are fixed for the network. This leaves 8 bits for host addresses ($32 - 24 = 8$ bits).
* **$2^8 = 256$ total addresses**:
* `10.15.4.0` (Network address)
* `10.15.4.1` through `10.15.4.254` (Usable IP addresses for your 254 app servers)
* `10.15.4.255` (Broadcast address)



### 3. Comparing the Two Approaches

#### Without CIDR (The Hard Way)

If you had to set up firewall rules without CIDR or subnet groups, you would have to enter **254 separate firewall lines**:

```text
ALLOW 10.15.4.1   --> 10.15.9.5:1521
ALLOW 10.15.4.2   --> 10.15.9.5:1521
ALLOW 10.15.4.3   --> 10.15.9.5:1521
... [250 more rules] ...
ALLOW 10.15.4.254 --> 10.15.9.5:1521

```

> **Why this hurts:**
> * **Tedious & Error-Prone:** High risk of typos or skipping an IP.
> * **Maintenance Nightmare:** If you auto-scale or spin up a new app server inside that range, it won't be able to talk to the database until someone manually updates the firewall.
> 
> 

---

#### With CIDR (The Smart Way)

By leveraging CIDR, you create **one single firewall rule**:

```text
Source:      10.15.4.0/24   (Entire App Cluster Subnet)
Destination: 10.15.9.5      (Database)
Port:        1521           (Database Listener Port)
Action:      ALLOW

```

---

### Summary Table

| Metric | Individual IPs | CIDR Notation (`10.15.4.0/24`) |
| --- | --- | --- |
| **Firewall Rules** | 254 rules | **1 rule** |
| **Operational Effort** | Very High | **Minimal** |
| **Auto-scaling Friendly** | No (must add IPs manually) | **Yes** (new IPs in the range work automatically) |
| **Human Errors** | High risk | **Low risk** |

## 6. Subnets & Subnet Masks — dividing a big network into safe zones

6. Subnetting divides a large network into smaller sub-networks. A **subnet mask** (e.g.
   `255.255.255.0`) tells a device which part of an IP is network and which is host, so a router can
   decide whether a destination is "local" or "must be sent to the gateway".

**Real-life implication:** Banks subnet heavily for **security segmentation** — the DMZ (internet-
facing) subnet, the application subnet, the database subnet, and the highly-restricted "PCI" subnet
for card data are separated so a breach in one zone can't freely spread to another. Your app and its
database often live in different subnets, which is exactly why firewall rules are needed between them.

**Example:** App servers in `10.15.4.0/24` cannot talk to DB servers in `10.15.9.0/24` unless a rule
explicitly permits it — this containment is a deliberate security control, not a bug.

### Linux scenario & solution — "Is this address on my subnet or does it need the gateway?"

```bash
ip route              # shows your routing table, including the "default" gateway
ip route get 10.15.9.5   # tells you exactly which route/interface a given IP will use
```
If `ip route get` shows the destination going via your `default` gateway, it is off-subnet and traffic
will cross a router (and probably a firewall) — a strong hint about where a connection could be blocked.

---

## 7. Public vs Private, Static vs Dynamic addressing

7. **Public IPs** are globally unique, assigned by IANA/ISPs, and reachable on the internet.
   **Private IPs** (e.g. `10.0.0.0/8`, `192.168.0.0/16`) are used inside an organisation and are not
   routable on the internet. **Static IPs** don't change (used for servers). **Dynamic IPs** are handed
   out automatically by a **DHCP** server and can change (used for laptops/desktops).

**Real-life implication:** Bank servers almost always have **static private IPs** so firewall rules and
DNS records stay stable. Your laptop gets a **dynamic** IP via DHCP when you connect to the office
network. Anything customer-facing sits behind a small number of tightly controlled **public** IPs.

**Example:** The payments API server keeps the static private IP `10.15.4.22` for years; your laptop
might be `10.200.5.33` today and `10.200.5.180` tomorrow — which is why firewall rules are never based
on your laptop's IP.

---

## 8. Nodes — anything that participates in the network

8. A node is any connection point that can send, receive, create or store data — a server, printer,
   switch, modem, ATM — each identified uniquely (usually by IP).

**Real-life implication:** In monitoring dashboards, every "node" is something that can fail. Thinking
in nodes helps you reason about single points of failure: if one node handles all traffic, its failure
is an outage.

**Example:** An ATM is a node; if that node loses its network link, customers at that machine can't
withdraw cash even though the core banking system is perfectly healthy.

---

## 9. Routing & Routers — choosing the best path between networks

9. Routing directs traffic between different networks/subnets. A **router** is a Layer-3 device that
   picks the best path and forwards packets toward their destination. Routing can be **static** (fixed,
   manually set), **dynamic** (routers learn paths automatically) or **default** (a catch-all "send
   anything I don't recognise here").

**Real-life implication:** When your app in one data centre calls a service in another, routers decide
the path. "It works from server A but not server B" is often a **routing** difference between the two
servers.

**Example:** During a data-centre failover, dynamic routing automatically redirects traffic to the
backup site — ideally so smoothly that customers never notice.

### Linux scenario & solution — "Where does the traffic actually go / where does it stop?"

Your app can't reach a partner service. You want to see the path and where it dies.

```bash
ip route                       # confirm you even have a route (and a default gateway)
traceroute partner-api.bank.com    # shows each router hop toward the destination
tracepath partner-api.bank.com     # similar, no root needed on many systems
```
If `traceroute` shows hops progressing then stopping with `* * *` at a certain point, the break is
around that hop — evidence to hand to the network team instead of vaguely saying "it's slow".

---

## 10. Switching & Switches — moving frames inside one network

10. Switching forwards a packet from one port to the correct destination port **within a network**.
    The PDF covers three classic techniques: **circuit switching** (a dedicated path for the whole
    call, like old telephone lines), **message switching** (whole message stored-and-forwarded at each
    node) and **packet switching** (message broken into packets routed independently — this is how the
    internet and IP work).

**Real-life implication:** Switches connect servers within a rack/data centre. You won't configure
them, but understanding packet switching explains why data can arrive out of order and why TCP
(point 22) has to reassemble it — which affects how you design reliable messaging.

**Example:** A large file transfer between two servers is chopped into thousands of packets that may
take slightly different internal paths and arrive out of order; TCP puts them back together correctly.

---

## 11. Ports — many services on one server

11. A port is a number that identifies a specific application/service on a device. If the IP address is
    the building, the port is the room number. Servers listen on well-known ports so clients know where
    to knock.

**Real-life implication:** Every service you build or call lives on a port. You must know the common
ones: **80** (HTTP), **443** (HTTPS), **22** (SSH), **21** (FTP), **25/587/465** (SMTP mail), **1521**
(Oracle DB), **3306** (MySQL), **8080** (app servers). "Connection refused" almost always means
nothing is listening on that port, or a firewall blocks it.

**Example:** Your Spring Boot app listens on `8080`; the load balancer forwards public `443` traffic to
your `8080`. If you start two apps on `8080` on the same server, the second fails with "port already in
use".

### Linux scenario & solution — "Is my service actually listening? Is the remote port open?"

The app won't start, or a downstream call fails. Check ports from both ends.

```bash
# On YOUR server: is anything listening on the port you expect?
ss -tlnp                       # modern: lists listening TCP ports + the process
netstat -tlnp                  # older equivalent
ss -tlnp | grep 8080           # narrow to your app's port

# Toward a REMOTE server: is its port reachable from here?
nc -zv 10.15.9.5 1521          # netcat: "open"/"succeeded" means port is reachable
telnet 10.15.9.5 1521          # old-school check; a blank screen = connected, refused = blocked
curl -v telnet://10.15.9.5:1521    # another quick reachability check
```
If `ss` shows your app listening but a client still fails, the problem is between them (firewall/route),
not your app. If `nc -zv` says "Connection refused", the DB isn't listening; if it just hangs/times
out, a firewall is silently dropping the traffic.

---

## 12. The OSI Model — the 7-layer mental map for debugging

12. The OSI (Open Systems Interconnection) model is a 7-layer framework: **7 Application** (services to
    apps: email, web), **6 Presentation** (encryption, compression, translation), **5 Session**
    (set up/maintain/tear down conversations), **4 Transport** (reliable delivery, segmentation — TCP/
    UDP), **3 Network** (routing, IP addresses), **2 Data Link** (Ethernet/Wi-Fi, MAC, error detection),
    **1 Physical** (cables, signals).

**Real-life implication:** OSI is the shared language for troubleshooting. Senior engineers ask "which
layer is the problem?" — a cable fault is Layer 1, an IP/routing issue is Layer 3, a TLS certificate
error is around Layer 6, an application bug is Layer 7. Naming the layer focuses the whole incident
bridge on the right team.

**Example:** "Users get a certificate error" → that's a presentation/application-layer (L6/L7) issue,
so you don't waste time asking the cabling team to check Layer 1.

---

## 13. The TCP/IP Model — the 4-layer model the internet actually uses

13. The practical model has 4 layers: **Application** (HTTP, FTP, SMTP, Telnet), **Transport** (TCP and
    UDP), **Internet** (IP — addressing, routing, fragmentation), and **Link** (Ethernet, Wi-Fi,
    Bluetooth). It maps onto OSI but is what real protocols are built on.

**Real-life implication:** This is the model you'll use daily because your app config literally names
these pieces: an HTTP endpoint (application), over TCP (transport), to an IP (internet).

**Example:** A REST call to `https://api.bank.com/accounts` = HTTP (application) over TLS, carried by
TCP (transport), to the server's IP (internet), across Ethernet (link) — four layers in one line of code.

---

## 14. TCP vs UDP — reliable vs fast

14. **TCP** is connection-oriented and reliable: it splits a message into packets, guarantees ordered
    delivery, checks for errors and retransmits lost packets. **UDP** is connectionless with no delivery
    guarantee and minimal overhead — faster, used for real-time streams and broadcasts.

**Real-life implication:** Anything involving money uses **TCP** — you cannot "lose a packet" in a
transaction. **UDP** shows up in things like live video, VoIP calls, some market-data feeds, and
DNS lookups, where speed matters more than perfect delivery.

**Example:** A funds transfer uses TCP so no instruction is ever silently dropped; a video call with a
customer support agent uses UDP, so a lost frame just causes a tiny glitch rather than freezing.

---

## 15. Gateways — the door between different networks

15. A gateway connects two or more *dissimilar* networks and is usually the exit/entry point of a
    network (often a computer/router with multiple network cards), using packet switching to pass data
    between them. Your "default gateway" is where your server sends anything not on its own subnet.

**Real-life implication:** If your server's default gateway is misconfigured or down, the server can
talk to its own subnet but nothing beyond it — a classic "the app works locally but can't reach
anything external" symptom.

**Example:** A payment gateway (a specialised gateway) is the controlled door through which all card
transactions leave the bank toward the card networks.

### Linux scenario & solution — "Can I reach my own gateway and the outside world?"

```bash
ip route | grep default        # find your default gateway IP (e.g. 10.15.4.1)
ping -c 4 10.15.4.1            # can you reach the gateway itself? (4 pings then stop)
ping -c 4 8.8.8.8             # can you reach an external IP at all?
```
If you can ping the gateway but not beyond it, the problem is upstream (routing/firewall), not your
server. `-c 4` limits ping to 4 packets so it doesn't run forever — important on a shared server.

---

## 16. NAT (Network Address Translation) — sharing and hiding addresses

16. NAT rewrites the IP address in packet headers as they pass through a router, mapping many private
    inside addresses to one (or few) outside addresses. It conserves scarce public IPs and hides
    internal addressing from the outside world.

**Real-life implication:** Thousands of bank servers with private `10.x` addresses reach the internet
through a handful of NAT'd public IPs. This is why an external partner sees all your outbound calls
coming from one IP — and why you give partners that **single NAT IP** to allow-list, not each server's
private IP.

**Example:** When your batch job calls an external credit-scoring API, the partner's firewall sees your
NAT gateway's public IP (say `203.0.113.10`), not the job's private `10.15.4.22`.

---

## 17. Firewalls — the security guard on the traffic

17. A firewall is a security device that monitors and controls incoming/outgoing traffic against
    predefined rules. It can do packet filtering, stateful inspection, NAT, VPN termination,
    application-level filtering, intrusion prevention (IPS), and logging.

**Real-life implication:** Firewalls are **the** most common reason a new bank app "can't connect".
Nothing talks to anything new until a firewall rule is explicitly requested and approved. As a
developer you will regularly raise firewall requests specifying *source IP/CIDR → destination IP →
port → protocol*, and you must justify each one for the security review.

**Example:** Your new service needs to reach the Oracle DB, so you raise a rule: allow
`10.15.4.0/24 → 10.15.9.5 : 1521 (TCP)`. Until it's approved, your connection attempts just time out.

### Linux scenario & solution — "Is it a firewall or is the service down?"

```bash
nc -zv 10.15.9.5 1521          # reachable → not a firewall; refused/timeout → likely firewall or service down
ping -c 4 10.15.9.5           # note: many firewalls block ping (ICMP) even when the app port is open,
                               #        so a failed ping alone does NOT prove the host is unreachable
ss -tnp                        # see your existing established connections to confirm what already works
```
Distinguish the two symptoms: **"Connection refused"** = you reached the host but nothing is listening
(service down / wrong port); **"timed out"** = traffic is being dropped silently (classic firewall block).

---

## 18. VPN (Virtual Private Network) — a secure tunnel over the public internet

18. A VPN creates an **encrypted tunnel** over the public internet so a remote device can securely join
    a private network. It hides your real IP behind the VPN's IP and protects data in transit. It became
    essential as employees started working remotely.

**Real-life implication:** As a bank employee you almost certainly connect to internal systems through
the corporate VPN when working from home. Many internal servers are only reachable *after* you're on the
VPN — so "I can't reach the dev server from home" is very often "I forgot to connect the VPN".

**Example:** From home you launch the VPN client, which places your laptop virtually inside the bank
network; only then can PuTTY reach `10.15.4.22`.

---

## 19. Proxies and Reverse Proxies — intermediaries in the traffic flow

19. A **proxy server** sits between clients and servers and forwards requests. A **forward proxy** acts
    on behalf of *clients* (providing anonymity, security, caching, content filtering, load balancing on
    outbound traffic). A **reverse proxy** sits in front of *servers* and is the entry point for incoming
    requests (load balancing, caching, content routing, and SSL/TLS termination).

**Real-life implication:** Bank outbound internet access usually goes through a **forward proxy** — so
your server's HTTP calls must be configured with proxy settings or they'll fail with timeouts.
Customer traffic hits a **reverse proxy** (like an F5/Nginx load balancer) that spreads load across
your app instances and handles HTTPS certificates so your app doesn't have to.

**Example:** Your batch job downloading a file from the internet fails until you set
`export https_proxy=http://proxy.bank.com:8080`; meanwhile the mobile app's HTTPS request lands on a
reverse proxy that forwards it to whichever of your 10 app servers is least busy.

### Linux scenario & solution — "My server can't download from the internet"

```bash
curl -v https://updates.vendor.com          # -v shows exactly where it fails (DNS? connect? TLS?)
echo $http_proxy $https_proxy               # is a proxy configured in this shell?
export https_proxy=http://proxy.bank.com:8080   # set the corporate proxy, then retry
curl -v https://updates.vendor.com
```
On a bank server, a **direct** internet call usually fails by design; it must go through the proxy.

---

## 20. DNS (Domain Name System) — the phone book of the network

20. DNS translates human-readable names (`api.bank.com`) into IP addresses. It is a distributed,
    hierarchical database of name servers acting as the internet's directory.

**Real-life implication:** Config files use hostnames, not IPs, precisely so that infra can change the
underlying server without you editing code. But this means **DNS problems look like application
problems**: if a name resolves to the wrong (or old) IP, your app connects to the wrong server or
fails entirely. "Works with IP but not with hostname" = a DNS issue.

**Example:** After a failover, `db.bank.com` is repointed from the primary to the standby database via
DNS; your app picks up the new IP on its next lookup without a code change — provided DNS caching
isn't holding the old value.

### Linux scenario & solution — "Is the name resolving to the right address?"

```bash
nslookup api.bank.com          # simple name-to-IP lookup
dig api.bank.com +short        # cleaner output; just the resolved IP(s)
host api.bank.com              # another quick resolver
cat /etc/resolv.conf          # which DNS servers is this box using?
cat /etc/hosts                # local overrides that beat DNS (a common "why is it going there?!" cause)
```
If `dig` returns the wrong IP, the fix is a DNS/`/etc/hosts` issue, not your code. Always check
`/etc/hosts` — a stale manual entry there silently overrides real DNS and confuses everyone.

---

## 21. CDN (Content Delivery Network) — serving content closer to users

21. A CDN is a geographically distributed set of "edge" servers that cache copies of content close to
    users, improving performance and availability by cutting the distance (and latency) between user
    and content.

**Real-life implication:** The bank's public website, mobile app assets (images, JS, CSS) and marketing
pages are served via a CDN so a customer in Singapore and one in London both get fast load times. As a
developer, remember that **CDN caching can serve stale content** — after a release you may need to
"purge the CDN" for users to see the new version.

**Example:** You deploy a new logo, but customers still see the old one for hours because the CDN edge
servers cached it — until the cache expires or is purged.

---

## 22. HTTP / HTTPS — the language of web and APIs

22. HTTP (HyperText Transfer Protocol) is a stateless, client-server, request/response protocol for the
    web. Requests use **methods**: **GET** (read), **POST** (submit/create), **PUT** (replace),
    **DELETE** (remove), **HEAD** (headers only). A **URL** has method, host, optional port and path.
    Responses carry **status codes**. **HTTPS** is HTTP secured with TLS encryption.

**Real-life implication:** Almost every service you build in a modern bank is an HTTP/HTTPS REST API.
You must know the methods and status-code families cold: **2xx** success, **3xx** redirect, **4xx**
client error (e.g. 401 unauthorised, 403 forbidden, 404 not found), **5xx** server error (500, 503).
"Stateless" is why sessions/tokens exist — the server doesn't remember you between calls. All external
banking traffic must be **HTTPS**, never plain HTTP.

**Example:** `GET /accounts/123/balance` returns `200 OK` with the balance; a bad token returns
`401`; if your service is overloaded it returns `503`, telling the caller to back off and retry.

### Linux scenario & solution — "Is the API healthy from this server?"

```bash
curl -i https://api.bank.com/health         # -i shows the status line + headers (look for 200 OK)
curl -v https://api.bank.com/health         # -v shows the full TLS handshake + request/response
curl -w "time_total=%{time_total}\n" -o /dev/null -s https://api.bank.com/health   # measure latency
wget -qO- https://api.bank.com/health       # alternative if curl isn't installed
```
Reading the status code and headers tells you instantly whether it's your code, auth (401/403), or the
server (5xx) — and the `time_total` output turns "it feels slow" into an actual number.

---

## 23. FTP / SFTP — moving files between systems

23. FTP (File Transfer Protocol) is a client-server protocol for transferring files. Plain FTP is
    insecure (credentials/data in clear text); banks use **SFTP** (over SSH) or **FTPS** instead.

**Real-life implication:** Banks move enormous numbers of files — end-of-day reports, reconciliation
files, regulatory submissions, partner data exchanges — and **SFTP is the workhorse** for this. You
will write or support jobs that pick up, transform and drop files on SFTP servers.

**Example:** Every night a batch job SFTPs the day's transaction file to a clearing house; if the file
is late or malformed, downstream settlement breaks — a real, high-visibility incident.

### Linux scenario & solution — "Send/fetch a file securely to another server"

```bash
scp report.csv user@10.15.9.9:/incoming/       # secure copy a file TO a remote server (over SSH)
scp user@10.15.9.9:/outgoing/ack.csv .         # copy a file FROM a remote server to here
sftp user@10.15.9.9                            # interactive secure file transfer session
# inside sftp:  put report.csv   |   get ack.csv   |   ls   |   bye
```
Prefer `scp`/`sftp` over plain `ftp` — in a bank, transferring files over unencrypted FTP is a
security violation.

---

## 24. SSH (Secure Shell) — secure remote access, and how you log in

24. SSH is a cryptographic protocol for securely accessing and managing remote systems, guaranteeing
    confidentiality and integrity. It uses a **key pair**: a **public key** (shareable, placed on the
    server) and a **private key** (kept secret on your machine). Default port **22**.

**Real-life implication:** This is *how you actually connect to bank servers* — PuTTY is an SSH client.
Understanding key pairs matters because banks favour **key-based** login over passwords: you generate a
key pair, the infra team puts your public key on the servers, and your protected private key proves who
you are. Never share or email a private key.

**Example:** You `ssh` into `10.15.4.22` to check application logs; because your public key is installed
there, you're let in without a password, and the session is fully encrypted.

### Linux scenario & solution — "Set up and use SSH the professional way"

```bash
ssh-keygen -t ed25519                         # generate a modern key pair (protect it with a passphrase)
ssh-copy-id user@10.15.4.22                    # install YOUR public key on the server (if allowed)
ssh user@10.15.4.22                            # log in securely
ssh -v user@10.15.4.22                         # verbose: debug why a login is failing (key? port? host?)
```
On Windows, PuTTY (with PuTTYgen for keys and Pageant to hold them) does the same thing with a GUI.

---

## 25. SMTP — how systems send email

25. SMTP (Simple Mail Transfer Protocol) sends/relays outgoing email between mail servers. It's a
    simple text-based, client-server protocol on port **25** (or **587** for submission, **465** for
    SMTPS), supports authentication and STARTTLS encryption, and uses an envelope + headers (sender,
    recipient, subject).

**Real-life implication:** Applications send email constantly — transaction alerts, OTPs, statements,
system failure notifications to support teams. Your app will be configured with an SMTP server host,
port and (usually) credentials. If alerts stop arriving, SMTP config or connectivity is a prime
suspect.

**Example:** When a large transfer completes, your service connects to the SMTP relay and sends the
customer a confirmation email; if the SMTP port is blocked, the transfer still succeeds but the
customer never gets notified — a subtle bug worth checking for.

### Linux scenario & solution — "Can this server reach the mail relay?"

```bash
nc -zv smtp.bank.com 587       # is the SMTP submission port reachable from here?
telnet smtp.bank.com 25        # a 220 greeting means the mail server is responding
```
A quick port check separates "my email code is wrong" from "this server can't even reach the mail relay".

---

## 26. SNMP — monitoring the health of network devices

26. SNMP (Simple Network Management Protocol) monitors and manages network devices (routers, switches,
    servers, printers). **Agents** on devices collect data into a **MIB** (a structured database);
    **managers** query them with operations like **GET** (read) and **SET** (change), while agents send
    **TRAP** notifications when something goes wrong. Versions v1/v2c/v3 (v3 adds real security).

**Real-life implication:** You won't usually write SNMP code, but the bank's monitoring platform uses it
to watch infrastructure, and **SNMP traps often trigger the alerts that page you** during an incident.
Knowing this explains where "CPU high on server X" alerts come from.

**Example:** A switch's CPU crosses a threshold; its SNMP agent fires a TRAP to the monitoring system,
which raises an alert — and that's the notification that wakes up the on-call engineer.

---

## 27. Your Everyday Linux Networking Toolkit (consolidated cheat-sheet)

27. These are the commands from the scenarios above, gathered as a quick reference. As a bank software
    engineer, 90% of your network debugging on a server uses just these.

| Question you're answering | Command(s) |
|---|---|
| What's my IP / interfaces? | `ip addr`, `ip -brief addr`, `hostname -I` |
| What's my gateway / routes? | `ip route`, `ip route get <ip>` |
| Can I reach a host at all? | `ping -c 4 <host>` (ICMP may be blocked) |
| Where does the path break? | `traceroute <host>`, `tracepath <host>` |
| Is a remote port open? | `nc -zv <host> <port>`, `telnet <host> <port>` |
| What's listening on my box? | `ss -tlnp`, `netstat -tlnp` |
| What connections exist now? | `ss -tnp`, `netstat -tnp` |
| Does a name resolve correctly? | `dig <name> +short`, `nslookup <name>`, `host <name>` |
| Which DNS / host overrides? | `cat /etc/resolv.conf`, `cat /etc/hosts` |
| Is an API/web endpoint healthy? | `curl -i <url>`, `curl -v <url>`, `wget -qO- <url>` |
| Copy files securely | `scp`, `sftp` |
| Log into a server securely | `ssh user@host`, `ssh -v user@host` |

### A realistic end-to-end incident (ties it all together)

**Ticket:** "The new payments service on `app01` (`10.15.4.22`) can't reach the Oracle DB
`db.bank.com`. Customers are getting errors. Investigate."

```bash
# 1. Confirm which server and IP you're on
hostname -I

# 2. Does the DB name resolve, and to what IP?
dig db.bank.com +short            # -> say it returns 10.15.9.5

# 3. Do you even have a route to that subnet?
ip route get 10.15.9.5            # goes via default gateway = it's off-subnet (firewall likely in path)

# 4. Is the DB port reachable?
nc -zv 10.15.9.5 1521             # "timed out"  -> firewall is dropping it
                                  # "refused"    -> DB isn't listening / wrong port

# 5. Confirm your own app isn't the problem (is it listening where it should?)
ss -tlnp | grep 8080

# 6. Sanity-check what already works from this box
ss -tnp                            # existing established connections
```
**Diagnosis path:** name resolves fine → route exists → but `nc` **times out** → this is a **firewall
block between the app subnet `10.15.4.0/24` and the DB `10.15.9.5:1521`**. **Solution:** raise a
firewall request for `10.15.4.0/24 → 10.15.9.5 : 1521 (TCP)`, and in the meantime you've saved hours by
proving it's *not* an application bug. This is exactly the kind of crisp, evidence-based handoff that
makes a new engineer look competent on an incident bridge.

---

## 28. Golden rules to carry into the bank

28. Keep these principles in mind, because in a regulated bank the "why" matters as much as the "how":

1. **Prefer names over IPs** in config, but know how to fall back to IPs when debugging DNS.
2. **Always encrypt in transit** — HTTPS, SFTP, SSH; never plain HTTP/FTP/Telnet for real data.
3. **"Connection refused" ≠ "timed out"** — the first is a down/wrong service, the second is usually a
   firewall. This single distinction resolves a huge share of incidents.
4. **New connectivity needs a firewall rule** — plan for it early, specify source CIDR → dest IP → port.
5. **Segment awareness** — your app and DB are usually in different subnets by design; that's security,
   not an accident.
6. **Ping failing doesn't mean the host is down** — ICMP is often blocked; test the actual port with `nc`.
7. **Gather evidence before escalating** — run the commands, capture the output, then hand a clear,
   layer-specific summary to the right team.
8. **Be careful on production** — use bounded commands (`ping -c 4`), stay in your own space, and never
   run anything you don't understand on a live banking server.
```
