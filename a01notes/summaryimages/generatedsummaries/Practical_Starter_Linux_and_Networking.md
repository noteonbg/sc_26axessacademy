# Getting the Basics Right — A Practical Starter (Linux + Networking)

This is a **do-focused checklist**, drawn from three sources:

- `Assignments/Week1/Linux Hands on/Hands on (Filesystems and utilities)_v1.pdf`
- `Campus Content 2026/Week 1/LinuxEssentialsCampus.pdf`
- `Campus Content 2026/Week 1/Basic Networking .pdf`

It deliberately **does not re-explain the theory** (what an IP address is, what a subnet is, what the
OSI layers mean, etc.) — that is already in the PDFs. Instead, every point below is a **practical action
with the exact command**, so a new joiner can start being useful on the project quickly. Read a point,
try the command, move on.

> Assume you connect to the project's Linux server through **PuTTY**, and your files are on Windows.

---

## A. Your first five minutes on the server

1. Log in through PuTTY with the username/password you were given, and **change the default password
   immediately**: `passwd` (type old, then new twice; nothing shows while typing — that's normal).
2. Know who and where you are before doing anything: `whoami`, `pwd`, `hostname`.
3. Check your basic environment: `echo $HOME`, `echo $SHELL`, `echo $LOGNAME`.
4. Work **only inside your own home directory** (`cd` with no argument takes you there); never edit or
   delete files in other people's directories.
5. Get help on any command instead of guessing: `man ls`, `man date`, `man find` (press `q` to quit).
6. Leave cleanly when done: `exit` (or `Ctrl+D`).

---

## B. Moving around and handling files (be fluent without thinking)

7. Move between directories: `cd <path>`, `cd ..` (up one), `cd` (home), `cd /dev` then `pwd` to confirm.
8. List with detail: `ls -l` (permissions, size, owner, time), `ls -lt` (newest first), `ls -a` (show
   hidden files).
9. Create things: `mkdir unixlab` (directory), `touch file1.txt` (empty file).
10. Copy files: `cp *.txt ./unixlab/.` then **verify** with `ls -l ./unixlab`.
11. Rename or move: `mv file1.txt newfile1.txt` (rename), `mv unixlab newunixlab` (rename a folder).
12. Always read the **timestamp** in `ls -l` to spot which file changed most recently — this is how you
    find "last night's log".
13. Delete **only when told**, and carefully: `rm <file>` for one file; never run a blind `rm -rf`.

---

## C. Permissions (fix "Permission denied" and make scripts run)

14. Read the permission string in `ls -l` (e.g. `-rwxr-xr--`) to see who can read/write/execute.
15. Set exact permissions with numbers: `chmod 754 file1.txt` (owner full, group read+execute, others
    read only).
16. Make a script runnable before executing it: `chmod +x myscript.sh`, then `./myscript.sh`.

---

## D. Links, backups, and mounted disks

17. Create links and tell them apart with `ls -l`: `ln file lnk` (hard link),
    `ln -s file lnk` (soft/symbolic link).
18. **Back up before you delete** anything important: `tar -cvf unixlab.tar ./unixlab`.
19. See which filesystems/disks are mounted: `cat /etc/mtab` (or `df -h`, `mount`).

---

## E. Finding files fast (a huge time saver on big servers)

20. Find by name, hiding permission-denied noise: `find / -name "*.log" -type f 2>/dev/null`.
21. Find by type (example: all symbolic links): `find / -type s -print 2>/dev/null`.
22. Run a command on each result: `find / -type s -exec ls -l {} \; 2>/dev/null`.

---

## F. Reading and slicing files/logs (your daily project work)

23. Peek at parts of a file: `head -1 file` (first line/header), `head -30 file`, `tail -15 file`
    (last lines), and number lines with `nl file`. Example combo: `nl sampledata.csv | head -30 | tail -15`
    shows lines 15–30.
24. Count records: `wc -l /etc/passwd` (total lines/records).
25. Search text and count hits: `grep "East" sampledata.csv`, `grep -c "East" sampledata.csv`.
26. Pull out specific columns: `cut -d"," -f2,7 sampledata.csv` (fields 2 and 7, comma-separated).
27. Sort data: `sort -t"," -k1,2n sampledata.csv`; find "most frequent" with `sort | uniq -c`.
28. Chain small tools with pipes `|`: e.g. `cut -d"," -f2,7 sampledata.csv | sort -t"," -k1,2n`, or
    strip blank lines with `man ls | grep -v "^$"`.
29. See output on screen **and** save it at once: `... | tee output.txt`.
30. For huge logs use `less file` (scroll, `/text` to search, `q` to quit) or `tail`; **never `cat` a
    giant log** — it floods your terminal.

---

## G. Moving files between Windows and the server

31. Upload from Windows (run in **PowerShell/CMD**, not inside PuTTY):
    `pscp sampledata.csv infra2@<serverip>:/home/infra2/<yourdir>`.
32. Upload a whole folder: `pscp -r "<windows folder>" user@<serverip>:~/`; download by reversing:
    `pscp user@<serverip>:~/report.txt "E:\temp\report.txt"`.
33. Prefer **WinSCP** (drag-and-drop GUI) if you like a visual tool; or `psftp user@host` then
    `put`/`get`.
34. After copying Windows files, fix line endings on the server: `dos2unix <file>` (or
    `sed -i 's/\r$//' <file>`), then `chmod +x` any scripts.

---

## H. Networking basics so you're never stuck

35. Find this server's IP: `ip addr` or `hostname -I`.
36. Check you can reach a host: `ping -c 4 <host>` (the `-c 4` stops it after 4 tries).
37. Check whether a specific port is open (e.g. database, API): `nc -zv <host> <port>`.
38. Check your own service is listening: `ss -tlnp` (look for its port, e.g. 8080).
39. Confirm a name resolves to the right address: `nslookup <name>` or `dig <name> +short`.
40. See where the path to a host breaks: `traceroute <host>`.
41. Test a web/API endpoint: `curl -i https://<host>/health` (look for `200 OK`).
42. Log into / copy to another server securely: `ssh user@<host>`, `scp file user@<host>:~/`.
43. Keep the common ports handy so you know what to test: **22** SSH, **443** HTTPS, **80** HTTP,
    **1521** Oracle, **3306** MySQL, **25/587** mail, **8080** app servers.

---

## I. Habits that make you genuinely useful on the project

44. Always confirm the server with `hostname` **before** running anything — especially on production.
45. When something fails, **gather evidence first** (run the commands, copy the output) before
    escalating; a clear "here's what I ran and what it showed" makes you look sharp.
46. Learn the difference in symptoms: **"connection refused"** = the service is down / wrong port;
    **"timed out"** = usually a firewall is blocking you.
47. Prefer names over hard-coded IPs when possible, but know how to fall back to an IP to test.
48. Keep a personal cheat-sheet of the project's servers, key file paths (like `/var/log/...`), and
    ports — you'll reuse it every day.
49. Never run a command you don't understand on a shared/production server; test it on your own space
    first or check `man` / ask.
50. Practise these commands until they're automatic — speed and safety on the command line is what
    turns "the new joiner" into "the person who can help during an incident."

---

### How to use this document

Go top to bottom once, typing each command on a safe test file (create `sampledata.csv` and a
`unixlab` folder to play in). Then keep it open as a quick reference during your first weeks. For the
*why* behind any of these, go back to the three PDFs — this sheet is only the *how* and the *do*.
