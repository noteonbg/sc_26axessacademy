Linux Essentials 


This document is built from two sources:

1. The training deck `Campus Content 2026/Week 1/LinuxEssentialsCampus.pdf`
2. The hands-on lab files in `Assignments/Week1/Linux Hands on/Hands On (shell-scripts)/shell-scripts/`

It has two parts:

- **Part A – Concept Summary** — numbered, prose format (no bullets). Each point explains *why it matters in real corporate/production life*, and how it plays out once you are logged into a server through PuTTY.
- **Part B – Practical Walkthrough** — a detailed, step-by-step guide for connecting with PuTTY and completing every single lab (Lab 1 to Lab 10, plus the practice files) exactly as expected by the PDF.

---

PART A — CONCEPT SUMMARY (Real-Life Importance)

#1. Operating System & the UNIX/Linux Model

1. An operating system is the software layer that manages CPU, memory and disk, hides hardware complexity, and gives you a simple interface; in real life this is why you never talk to hardware directly, you talk to Linux and Linux talks to the machine.

2. UNIX/Linux is a multi-user, multi-tasking, time-sharing system, which is exactly why in a company dozens of engineers can log into the same server at the same time without disturbing each other — this is the single most important real-world fact to internalise.

3. The OS runs on a central host and users connect to it from their laptops using a terminal emulator; **PuTTY is that terminal emulator**, so everything you do in this course happens on a remote server, not on your own Windows machine.

4. Linux is "UNIX-like", free and open source, and dominates servers, cloud and containers; this is why every bank, fintech and cloud provider expects you to know it, and why this Week-1 module exists.

5. "Everything is a file" in Linux — devices, terminals, memory and pipes are all represented as files; in production this uniform model is what lets you inspect a running system with the same commands you use on ordinary text files.

#2. Logging In & Sessions (What Actually Happens Through PuTTY)

6. Every user must be authenticated before using the system; the login program checks your username in `/etc/passwd` and your password in `/etc/shadow`, and only then starts your shell and shows a prompt — so a "connection refused" or "access denied" in PuTTY is almost always an authentication or account problem, not a network one.

7. Once authenticated you land in a shell that shows a prompt and waits for commands; you end the session cleanly with `exit` or `Ctrl+D`, and in a shared corporate server always logging out cleanly is basic hygiene.

8. The golden safety rules from the deck are real workplace rules: create and work only inside **your own space**, never step into another person's directory, and never run destructive commands like `rm` / `rmdir` unless explicitly told — on a shared production box a careless `rm` can wipe out someone else's work or an application.

#3. File System & Directory Hierarchy

9. Linux presents all disks and devices as one single tree starting at root `/`, which means there are no `C:` or `D:` drives like Windows — understanding this one tree is essential before you navigate a real server.

10. Knowing the standard directories saves you in real incidents: `/bin` and `/sbin` hold commands, `/etc` holds system-wide configuration, `/home` holds user home directories, `/var` holds variable data such as logs, `/tmp` holds temporary files, `/dev` holds device files, and `/proc` exposes live kernel/process information — when an app breaks, you will go straight to `/var/log` and `/etc`.

11. A directory is itself a special file that maps names to inodes, and the root directory contains everything else; this matters when you deal with links and permissions later.

12. File types you must recognise are ordinary files, directories, special/device files under `/dev`, symbolic links (a file pointing to another file), and pipes (a temporary channel that feeds one command's output into another) — recognising them from `ls -l` output is a daily skill.

#4. Core File Commands (Daily Bread on Any Server)

13. The commands you will use every single day are `ls` (list), `cd` (change directory), `cp` (copy), `mv` (move/rename), `mkdir` (make directory), `cat` (view file), `rmdir`/`rm` (delete — use with extreme caution), and `file` (identify type); mastering these is the difference between being productive and being lost on a server.

14. `find` is the real-world search tool for locating files across huge server trees, e.g. `find / -name "*.java" -type f -print`, and combined with `-exec` it can act on every match at once, which is invaluable when hunting a config or a rogue log across thousands of directories.

15. Links matter operationally: `ln` creates a hard link and `ln -s` creates a soft (symbolic) link; in production soft links are used constantly to point a stable path (like `/opt/app/current`) at whichever real versioned directory is live, enabling clean releases and rollbacks.

#5. Permissions & Security (Why Access Fails in Real Life)

16. Every file and directory carries read, write and execute permissions for three classes — user (owner), group and others — and `ls -l` shows them; nine times out of ten a "Permission denied" error in a real job is explained purely by reading this output correctly.

17. On a directory the meanings shift: read lets you list contents, write lets you add or delete files inside it, and execute lets you enter/"search" it — this distinction is why you sometimes can see a folder name but cannot `cd` into it.

18. `chmod` changes permissions and only the owner can do it; you can use symbolic mode (`chmod u+x script.sh`) or absolute octal mode where read=4, write=2, execute=1 (so `chmod 760 file` = rwx for user, rw for group, nothing for others) — making a shell script executable with `chmod +x` is something you will do in almost every lab and every job.

19. `umask` sets the default permissions for newly created files by subtracting from 777, so `umask 022` yields `rwxr-xr-x`; in a secured corporate environment umask is often hardened so that new files are not world-writable by default.

#6. Archiving & Compression (Backups, Transfers, Deployments)

20. `tar` bundles many files/directories into one archive (a `.tar` is a bundle, **not** compressed by itself), and is the standard way to package application files, take snapshots, or move a whole directory between servers.

21. `gzip` compresses files (and you decompress with `gzip -d`, `gunzip` or `zcat`); in practice you constantly meet `.tar.gz`/`.tgz` files — a tarred *and* gzipped archive — when downloading software or shipping logs, so knowing `tar zxvf file.tgz` is a real deployment skill.

#7. Utilities & Text Processing (The Real Superpower of Linux)

22. Linux tools follow a simple contract — take input, transform it, produce output — and this uniformity is what makes them combinable; internalising this is the key to becoming fast on the command line.

23. `head` and `tail` show the first/last N lines of a file, and `tail` in particular is critical in real life for watching the end of a live log to see the latest errors.

24. `more` and `less` page through large files without loading them fully; `less` (scroll both ways) is the safe way to read a huge production log without freezing your terminal.

25. `cut` extracts specific columns/fields (e.g. `cut -d: -f1 /etc/passwd` pulls usernames), which is how you slice structured data and log lines into just the fields you care about.

26. `sort` orders data (numeric `-n`, reverse `-r`, by key field `-k`, custom delimiter `-t`) and `uniq` collapses or counts duplicates; the classic real-life combo `sort | uniq -c` is how you answer "which error appears most often?".

27. `tee` writes output to a file *and* to the screen at once, useful when you want to both watch a command run and keep a saved record of it.

28. `grep` is the most important text tool of all — it searches for lines matching a pattern, with options for count (`-c`), line numbers (`-n`), invert match (`-v`), ignore case (`-i`) and whole word (`-w`); in every real job "grep the logs" is a daily verb.

29. Piping with `|` sends one command's output into the next, letting you build powerful one-liners like `cut -d: -f7 /etc/passwd | sort | uniq -c`; this composition of small tools is the essence of the UNIX philosophy, though too many pipes can hurt performance so use them thoughtfully.

30. Regular expressions supercharge `grep` and editors: `^` anchors start of line, `$` anchors end, `.` matches any character, `*` matches repetition, `[...]` matches a character set, and `\` escapes special meaning — being able to write `grep '^error' file` or `grep '[0-9][0-9]'` is exactly what you need to hunt patterns in real logs.

#8. The vi Editor (Always There When You Need It)

31. `vi`/`vim` is the editor that is guaranteed to exist on every Linux/UNIX box, even a broken one booted into single-user mode, uses almost no resources, and needs no graphical interface — which is precisely why on a bare production server it is often the *only* editor available to fix a config.

32. `vi` has two core modes you must never confuse: **command mode** (the default on open — for moving, deleting, copying, saving) and **insert mode** (for typing text, entered with `i`/`a`/`o`), and you switch back to command mode with `Esc`; almost every beginner's "vi is stuck" problem is simply being in the wrong mode.

33. The essential save/quit commands are `:w` (save), `:q` (quit), `:wq` (save and quit) and `:q!` (force quit without saving), and knowing `:q!` alone will rescue you when you have made a mess and just want out safely.

34. The high-value editing keystrokes are `i/a/o` to insert, `x` to delete a character, `dd` to delete a line (`3dd` for three), `yy` to yank/copy a line (`3yy`), `p` to paste, `u` to undo, `/text` to search, and `:n` to jump to a line — this small set covers the vast majority of real edits you will make on a server.

#9. Shell Scripting (Automation = The Point of the Whole Module)

35. A shell script is just a file of commands that runs like a program, which lets you automate repetitive administrative work; in real life scripts are used for monitoring servers, taking backups and snapshots, starting/stopping services, scanning logs for errors, and managing users — this is the payoff of everything above.

36. Creating a script is a fixed four-step ritual: open a file in `vi`, write the commands (starting with `#!/bin/bash`), make it executable with `chmod +x script.sh`, and run it with `./script.sh` — you will repeat this ritual for every lab.

37. Variables come in system variables (usually uppercase) and user-defined variables (e.g. `title="hello"`), are case-sensitive, must have no spaces around `=`, and are read back with a `$` prefix (`echo $title`); getting the `$` and the no-spaces rule right prevents the most common scripting bugs.

38. Scripts take input two ways — interactively via `read` (prompting the user during execution) and via command-line arguments `$0 $1 $2 …` where `$0` is the script name — and knowing both is essential because production scripts are usually driven by arguments while learning scripts use `read`.

39. `case … esac` is the branching/menu construct for matching a variable against several patterns (with `*` as the catch-all default), and it is the clean way to build the option menus you will write in Lab 3 and Lab 10.

40. `if … elif … else … fi` handles conditional logic using numeric test operators `-eq`, `-ne`, `-lt`, `-gt`, `-le`, `-ge` inside `[ ... ]`; in real scripts this is how you check "is disk usage over 90%?" and act accordingly.

41. Loops repeat work: the `while [ condition ]; do … done` loop (great for reading a file line by line with `while read`) and the `for var in list; do … done` loop (great for iterating over files) are the two you must know, and both appear directly in these labs.

42. Functions group reusable statements under a name (`myfunc() { … }`) and receive their own arguments via `$1`, `$2`; structuring a script into functions is what separates a throwaway script from maintainable, real-world automation.

#10. Diagnostics & Monitoring (Keeping Production Healthy)

43. The OS's core job is to manage CPU, memory, disk and network, so monitoring reduces to watching those four resources, and knowing the right command for each is what makes you useful during an incident.

44. For CPU and load: `nproc` shows how many processing units you have and `uptime` shows how long the system has run, current users and the 1/5/15-minute load averages — the load average is your first glance at "is this box overloaded?".

45. For memory: `free -h` shows total, used, free, buffer/cache and swap in human-readable units, and reading it correctly tells you whether a server is genuinely out of memory or just using cache normally.

46. For storage: `df -h` reports free/used disk space per filesystem (a "disk full" outage is one of the most common real incidents), `du` summarises what is consuming space in a directory, and `mount` shows what filesystems are attached.

47. For network: `ip` shows and manipulates addresses, links and routes, and `netstat` lists connections, listening ports and interface stats — these are your go-to tools when "the app can't reach the database".

48. For deeper diagnostics the deck lists `top` (live process/CPU/memory view), `sar` (collected historical activity), `vmstat` (virtual memory, paging, IO, CPU), and `iostat` (CPU plus per-device IO); `top` in particular is the command you open first when a server is "slow".

#11. Packages & Installation (Getting Software Onto a Server)

49. Software is delivered as packages — compressed archives containing files plus dependency and OS instructions — in formats like `rpm` (Red Hat), `deb` (Debian) and `tgz`, and package managers install/update/remove them by pulling from a central repository.

50. Real-world install paths are: `yum` on Red Hat family (`sudo yum install <pkg>`, `sudo yum remove <pkg>`, `sudo yum update`, `sudo yum repolist`), the lower-level `rpm` (`sudo rpm -i pkg.rpm` to install, `-U` to upgrade, often after fetching the file with `wget`), and fully-manual `.tar.gz` installs where you extract with `tar zxvf`, copy binaries into place and configure by hand — the docker example in the deck (`tar zxvf docker-*.tgz` → copy to `/usr/bin` → run) is a textbook manual install.

---

PART B — PRACTICAL WALKTHROUGH (Do Everything, Step by Step)

#B.0 — Connecting to the Server with PuTTY

Before any lab, you get onto the Linux host from your Windows machine.

1. **Open PuTTY** (download from the official site if not installed).
2. In **Host Name (or IP address)**, type the server hostname/IP your trainer gave you.
3. Keep **Port = 22** and **Connection type = SSH**.
4. (Optional) Type a name under **Saved Sessions** and click **Save** so you can reconnect with one double-click next time.
5. Click **Open**. On the first connection accept the host key security alert (**Accept**).
6. At `login as:` type your **username** and press Enter, then type your **password** (the screen shows nothing while you type — that is normal) and press Enter.
7. You are now at the shell prompt (something like `user@host:~$`). Confirm where you are and who you are:

```bash
whoami        shows your username
pwd           shows your current directory (your "own space")
hostname      shows which server you are on
```

8. **Always work inside your own home directory.** Create a working folder for these labs and go into it:

```bash
mkdir -p ~/week1-labs
cd ~/week1-labs
```

9. When finished for the day, log out cleanly with `exit` (or `Ctrl+D`).

> Safety, per the deck: stay in your own directory, never enter others' directories, and do **not** use `rm`/`rmdir` unless explicitly required.

##Getting the lab files onto the server

The lab files (`lab1.txt` … `lab10.txt`, `data1.txt`, `data2.txt`, `data3.txt`) currently sit on your Windows PC. You have two options:

- **Option A – Type them in with `vi`.** Best for learning; you recreate each script by hand (steps given below).
- **Option B – Copy them up with PSCP/WinSCP.** From a Windows Command Prompt (not inside PuTTY):

```bash
pscp -r "\Assignments\Week1\Linux Hands on\Hands On (shell-scripts)\shell-scripts" user@server:~/week1-labs/
```

Then on the server: `cd ~/week1-labs/shell-scripts`.

> Note: the files are named `.txt`. On Linux the extension does not matter for execution — what matters is that the file contains valid shell commands and is made executable. You can keep them as `.txt`, or rename to `.sh` for clarity: `mv lab1.txt lab1.sh`.

##The universal "create → permit → run" ritual (used for every lab)

```bash
vi labX.sh            1. open editor  (press i to insert, type code, Esc, then :wq)
chmod +x labX.sh      2. make it executable
./labX.sh             3. run it
```

If you ever prefer not to `chmod`, you can also run a script with `bash labX.sh` or `sh labX.sh`.

##A 60-second vi survival drill (do this once before Lab 1)

1. `vi test.txt` → opens in **command mode**.
2. Press `i` → now in **insert mode**, type any text.
3. Press `Esc` → back to **command mode**.
4. Type `:wq` and Enter → saves and quits.
5. If you ever panic, press `Esc` then type `:q!` and Enter to quit without saving.

---

#B.1 — Lab 1: Format text using the vi editor

**Goal (from PDF slide 38):** take the messy paragraph about the history of `vi` and reformat it into a clean, readable layout. The source text is in `lab1.txt`; a sample of the expected result is in `pratice01.txt` (headings, underlines, separated sections).

**What the finished layout looks like** (`pratice01.txt`):

```
                  ****** History of Vi Text Editor ****************
___________
Introducion
-----------
vi is a screen-oriented text editor originally created for the Unix operating system.

******Bill Joy : Cofounder of Sun systems*****
The original code for vi was written by Bill Joy in 1976.
...
```

**Steps:**

1. Open the raw text: `vi lab1.txt`
2. Press `i` to enter insert mode and add a centered banner title at the top, e.g. `****** History of Vi Text Editor ******`.
3. Use `Esc` then `o` to open new lines and insert section headings (`Introduction`, `Open Source`, `Interesting Facts`) with underline rows of `-` or `^`.
4. Practise the core editing keys while you reformat:
   - `dd` to delete a line, `3dd` to delete three lines.
   - `yy` to copy a line, `p` to paste it.
   - `x` to delete a stray character, `r` to replace one character.
   - `u` to undo a mistake.
   - `/Joy` to search for a word.
5. Press `Esc`, then `:wq` to save and quit.
6. Check the result: `cat lab1.txt`

**Real-life point:** this lab is really about becoming fluent switching between command and insert modes and using `dd`/`yy`/`p`/`u`, because on a production server `vi` is often the only editor available.

---

#B.2 — Lab 2: Script that lists files with a title and subtitle

**Goal (PDF slide 44):** a script that prints a custom title and subtitle, then lists the files in the current folder, and works when run from any folder.

**Reference (`lab2.txt`):**

```bash
title="welcome to shell scripting"
subtitle="List files from current folder"
filelist=`ls `
echo $title
echo $subtitle
echo $filelist
```

**Steps:**

1. `vi lab2.sh`, press `i`, type the script above (add `#!/bin/bash` as the first line as good practice).
2. `Esc`, `:wq`.
3. `chmod +x lab2.sh`
4. Run it: `./lab2.sh`
5. Test "from any folder": `cd ..` then run it with its path, e.g. `~/week1-labs/shell-scripts/lab2.sh`, and confirm it lists that folder's files.

**Key concepts shown:** variables, `echo $var` with the `$` prefix, and command substitution with backticks `` `ls` `` (modern equivalent: `filelist=$(ls)`).

---

#B.3 — Lab 3: Read a file / header / footer using `case`

**Goal (PDF slide 47):** ask the user for a filename and an option (`cat`, `head`, or `tail`), then show the whole file, its header, or its footer accordingly, using a `case` statement.

**Reference (`lab3.txt`):**

```bash
title="Lab 3 : Read a file or header or footer"
echo $title
echo "Enter name of the file to read"
read filename
echo "Enter the option : cat or head or tail"
read option
case $option in
     cat)  cat $filename ;;
     head) head $filename ;;
     tail) tail $filename ;;
     *) echo "unknown option";;
esac
```

**Steps:**

1. `vi lab3.sh`, insert the code, save (`:wq`).
2. `chmod +x lab3.sh`
3. Run: `./lab3.sh`
4. When prompted, enter a filename (e.g. `data1.txt`) and then an option (`cat`, `head`, or `tail`).
5. Try an invalid option (e.g. `xyz`) to see the `*)` default branch print `unknown option`.

**Real-life point:** this is the pattern for interactive tools — `read` for input and `case` for menu-style branching. Header/footer viewing (`head`/`tail`) is exactly how you inspect the top/bottom of real log files.

---

#B.4 — Lab 4: Number range check using `if / elif / else`

**Goal (PDF slide 49):** read a number and report whether it is ≤ 10, between 11–100, or > 100.

**Reference (`lab4.txt`):**

```bash
title="Welcome to Shell programming"
subtitle="Lab excerise to learn conditional statement using if"
echo $title
echo $subtitle
echo "Enter a number "
read input
if [ $input -le 10 ]
   then
       echo "your input $input is less than or equal to 10"
   elif [ $input -gt 100 ]
   then
       echo "your input $input is greater than 100"
   else
       echo "your input $input is between 10 to 100"
fi
```

**Steps:**

1. `vi lab4.sh`, insert, save.
2. `chmod +x lab4.sh`
3. Run and test three values that hit each branch: e.g. `5`, `50`, `500`.

**Watch out for:** spaces are required inside `[ ... ]` and around the operators; `-le` / `-gt` are numeric comparisons. This is the exact logic you would use in a monitoring script (e.g. "if disk usage `-ge` 90 then alert").

---

#B.5 — Lab 5: Iterate 5 times with a `while` loop

**Goal (PDF slide 50):** loop 5 times, printing a welcome message each iteration.

**Reference (`lab5.txt`):**

```bash
number=1
while [ $number -le 5 ]
  do
    echo "Welcome $number times"
    number=$(($number+1))
  done
echo "Completed the while loop $number times"
```

**Steps:**

1. `vi lab5.sh`, insert, save.
2. `chmod +x lab5.sh`
3. Run: `./lab5.sh`
4. Confirm it prints "Welcome 1 times" through "Welcome 5 times".

**Key concept:** loop counter arithmetic with `$(( ... ))`, and the `-le` loop condition. `while` loops are the backbone of "keep doing this until done" automation.

---

#B.6 — Lab 6: Print file names using a `for` loop

**Goal (PDF slide 51):** extract and print the file names from the current folder, run from any folder, using a `for` loop.

**Reference (`lab6.txt`):**

```bash
lstext=`ls `
for w in $lstext
do
   echo $w
done
echo "Print file names from current folder is completed"
```

(The full lab file also decorates the output with title/separator variables — reproduce those `echo $dash` lines for the exact expected look.)

**Steps:**

1. `vi lab6.sh`, insert (include the title/dash decoration lines from `lab6.txt` for full marks), save.
2. `chmod +x lab6.sh`
3. Run: `./lab6.sh`
4. `cd` elsewhere and run it by full path to prove it works from any folder.

**Difference vs Lab 2:** Lab 2 printed the whole `ls` in one line; Lab 6 iterates and prints each name on its own line — this teaches the `for var in list` idiom used to process items one by one.

---

#B.7 — Lab 7: Read a data file and apply logic (`while` + `if`)

**Goal (PDF slide 52):** create a data file with one number per row, then read it line by line and print a meaningful message per value. Here, mark value `1` as a "priority item".

**Data (`data2.txt`):** one number per line (`1, 2, 1, 0, 1, 2, 2, 1, 3, 4, 6`).

**Reference (`lab7.txt`):**

```bash
while read -r i
do
   if [ $i -eq 1 ] ;
      then
      echo "$i is a priority item"
   else
      echo $i
   fi
done < data2.txt
```

**Steps:**

1. Make sure `data2.txt` exists (create it in `vi` if copying wasn't done).
2. `vi lab7.sh`, insert (include the decorative title/subtitle/dash lines from `lab7.txt`), save.
3. `chmod +x lab7.sh`
4. Run: `./lab7.sh`
5. Confirm each `1` prints "1 is a priority item" and other numbers print as-is.

**Key concept:** `while read -r i ... done < file` is the canonical "read a file line by line" pattern — the single most useful log-processing idiom in real shell work.

---

#B.8 — Lab 8: Log dashboard — list high-severity ("flag 0") records

**Goal (PDF slide 53):** read a log file and print only the records whose flag is `0` (the failed/exception rows), combining `while`, `for` and `if`.

**Data (`data3.txt`):** ATM log where column 1 is the flag (`1` = ok, `0` = error):

```
1 atmlog 01042020 11:40:00 started ok user1 txnposted
0 atmlog 01042020 11:42:00 error failed user2 Notposted
...
```

**Reference (`lab8.txt`):**

```bash
while read -r i
    do
      for w in $i
      do
        if [ "$w" -eq 0 ] 2>/dev/null ;
        then
           echo "$i is a severity ticket"
        fi
    done
done < data3.txt
```

**Steps:**

1. Ensure `data3.txt` exists.
2. `vi lab8.sh`, insert (with the title/subtitle decoration from `lab8.txt`), save.
3. `chmod +x lab8.sh`
4. Run: `./lab8.sh`
5. Confirm only the three `0`-flagged rows are reported as "severity ticket".

**Clever detail to understand:** `for w in $i` splits each line into words; `[ "$w" -eq 0 ] 2>/dev/null` tries a numeric comparison and silently discards the errors thrown when a word is non-numeric text — so only the leading `0` flag matches. This mirrors real log triage: scan every line, pull out the failures.

---

#B.9 — Lab 9: Add two numbers using a function

**Goal (PDF slide 55):** take two numbers from the user and print their sum, using a function.

**Reference (`lab9.txt`):**

```bash
add()
  {
    a=$1
    b=$2
    c=`expr $a + $b`
    echo "Sum of two numbers is $c"
  }
while :
do
  echo "Control+c to exit"
  echo "Enter First Number"
  read first
  echo "Enter Second Number"
  read second
  add $first $second
done
```

**Steps:**

1. `vi lab9.sh`, insert (with decorative header lines from `lab9.txt`), save.
2. `chmod +x lab9.sh`
3. Run: `./lab9.sh`
4. Enter two numbers; it prints the sum, then loops again.
5. **Exit with `Ctrl+C`** (the script uses `while :` = infinite loop by design).

**Key concepts:** defining a function `add()`, passing arguments to it (`$1`, `$2`), and arithmetic via `expr` (modern alternative: `c=$((a + b))`). Functions + arguments = reusable, maintainable automation.

---

#B.10 — Lab 10: Menu-driven file reader (function + `case` + `while read`)

**Goal (PDF slide 56):** present a menu of files, let the user pick one, and display its content — combining a function, a `case` statement and a `while read` loop. (The deck advises avoiding fancy `dialog`/menu tools since they are distribution-specific — do it with plain shell.)

**Reference (`lab10.txt`):**

```bash
readfile()
  {
    while read -r line
      do
        echo $line
      done < $filename
  }
echo "Select option to read file **********"
echo "        1) data1.txt"
echo "        2) data2.txt"
echo "        3) data3.txt"
echo "Please enter your option"
read option
echo $option
case $option in
     1) filename="data1.txt" ;;
     2) filename="data2.txt" ;;
     3) filename="data3.txt" ;;
     *) echo "unknown file" ;;
esac
readfile $filename
```

**Steps:**

1. Ensure `data1.txt`, `data2.txt`, `data3.txt` all exist.
2. `vi lab10.sh`, insert (with the title/dash decoration lines from `lab10.txt`), save.
3. `chmod +x lab10.sh`
4. Run: `./lab10.sh`
5. Enter `1`, `2`, or `3` and confirm the matching file prints; try `9` to hit the `*)` default.

**Why this is the capstone:** it ties together everything — a menu (`echo` + `read`), branching (`case`), a reusable routine (`readfile()` function), and line-by-line reading (`while read ... done < $filename`). This is essentially a miniature version of a real "log viewer" admin tool.

---

#B.11 — Practice files (`pratice00.txt`, `pratice01.txt`) and the data files

1. **`pratice01.txt`** is the *sample formatted output* for Lab 1 — use it as the target when you reformat `lab1.txt` in vi.
2. **`pratice00.txt`** is a pipe-delimited server log; practise your utilities on it, for example:
   - `cut -d'|' -f3 pratice00.txt` → pull out the "server started" column.
   - `grep "completed" pratice00.txt` → find completed transactions.
   - `wc -l pratice00.txt` → count the records.
3. **`data1.txt`** (pipe-delimited log), **`data2.txt`** (one number per line), **`data3.txt`** (ATM log with a flag column) feed Labs 7, 8 and 10 respectively — make sure each exists before running those labs.

---

#B.12 — Suggested order & final checklist

1. Do the **PuTTY connect** and the **vi survival drill** first (B.0).
2. Then work Lab 1 → Lab 10 in order; each builds on the previous concepts (variables → case → if → while → for → functions → combined menu).
3. For each lab remember the ritual: **write in vi → `chmod +x` → `./run`**.
4. Verify output against the "Expected output" slide referenced for each lab in the PDF.
5. Practise the utilities (`grep`, `cut`, `sort`, `uniq`, `head`, `tail`, `wc`, pipes) on the data/practice files until the one-liners feel natural — that fluency is what actually transfers to a real job.
6. Log out cleanly with `exit`.

##One-shot commands to create and run everything (if you copied the files up)

```bash
cd ~/week1-labs/shell-scripts
chmod +x lab*.txt          make all lab scripts executable
bash lab2.txt              non-interactive labs: 2, 5, 6, 7, 8
bash lab5.txt
bash lab6.txt
bash lab7.txt
bash lab8.txt
bash lab3.txt              interactive labs (follow the prompts): 3, 4, 9, 10
bash lab4.txt
bash lab9.txt              exit with Ctrl+C
bash lab10.txt
```
