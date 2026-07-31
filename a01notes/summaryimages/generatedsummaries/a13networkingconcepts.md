

## Overview of Docker Network Drivers

| Networking Mode | Scope | How Container X and Y Talk to Each Other | Typical Use Case |
| --- | --- | --- | --- |
| **Default Bridge** | Single Host | Via private internal IP addresses only (no automatic name resolution) | Quick local testing |
| **User-Defined Bridge** | Single Host | Via container names (`http://Y:8000`) using automatic DNS | Multi-container apps on one machine |
| **Host** | Single Host | Via `localhost` using different port numbers | High performance / zero latency |
| **Container-to-Container** | Single Host | Via `localhost` sharing the exact same IP and port space | Helper / sidecar applications |
| **Overlay** | Multi-Host | Encrypted tunnel across physical host machines using container names | Swarm clusters or multi-server setups |
| **Macvlan** | Local LAN | Directly via physical router IP assignments as standard network devices | Legacy apps requiring real physical IPs |
| **None** | Isolated | They cannot communicate (network stack disabled) | Air-gapped or offline processing |

---

## 1. Default Bridge Network (Default Mode)

When you launch Container X and Container Y on the same machine without specifying a network, Docker places both inside a default virtual switch called `bridge`.

* **How communication happens:**
* Docker assigns each container a private internal IP address (e.g., Container X gets `172.17.0.2`, Container Y gets `172.17.0.3`).
* Container X can reach Container Y by sending requests directly to `172.17.0.3`.


* **Key limitation:** There is no built-in DNS service on the default bridge. Container X cannot find Container Y using the name `Y` — it must use the specific IP address.

---

## 2. User-Defined Custom Bridge Network (Recommended for Single Host)

You create a custom network (e.g., `my-app-net`) and attach both Container X and Container Y to it.

* **How communication happens:**
* Docker provides an automatic embedded DNS server for custom bridge networks.
* Container X simply sends a request to `http://Y:8080`.
* Docker automatically resolves `Y` to Container Y's internal IP behind the scenes.


* **Isolation advantage:** If Container Z is on the default network, it cannot reach Container X or Y on `my-app-net`.

---

## 3. Host Network Mode

In Host mode, Docker removes network isolation between the containers and the physical host system.

* **How communication happens:**
* Container X and Container Y do not get individual container IP addresses; they share the host machine’s actual IP address (`192.168.1.50`).
* If Container Y listens on port `8080`, Container X contacts Container Y using `http://localhost:8080` or `[http://192.168.1.50:8080](http://192.168.1.50:8080)`.


* **Trade-off:** No port mapping is required, which gives maximum network speed, but both containers cannot listen on the same port at the same time.

---

## 4. Shared Container Network (`container:X` Mode)

Container Y is configured to attach directly to Container X's existing network stack.

* **How communication happens:**
* Container X and Container Y share the exact same network identity, IP address, and MAC address.
* Container Y can reach a service on Container X directly via `http://localhost:<port>`.


* **Use Case:** Commonly used for "sidecar" containers (e.g., Container Y acts as a logging or security agent that monitors Container X).

---

## 5. Overlay Network (Multi-Host)

Used when Container X is running on **Server A** and Container Y is running on **Server B** across a local network or cloud environment.

* **How communication happens:**
* Docker creates a virtual, encrypted tunnel (VXLAN) linking Server A and Server B.
* To Container X, Container Y appears to be on the exact same local network switch, even though they are on physically separate servers.
* Container X reaches Container Y using its service name (`Y`) via Docker's internal routing mesh.



---

## 6. Macvlan Network

Macvlan assigns a unique MAC address to both Container X and Container Y, making them appear as physical devices directly attached to your router.

* **How communication happens:**
* Your physical network router assigns IPs to Container X (`192.168.1.101`) and Container Y (`192.168.1.102`) just like it would for physical laptops or desktops.
* Container X communicates with Container Y through your physical router/switch without routing traffic through the host machine's virtual network layer.



---

## 7. None Network Mode

Both Container X and Container Y have their network interfaces completely disabled.

* **How communication happens:**
* They **cannot** communicate with each other, the host, or external networks.
* Each container only has a loopback interface (`127.0.0.1`) for local internal processes.