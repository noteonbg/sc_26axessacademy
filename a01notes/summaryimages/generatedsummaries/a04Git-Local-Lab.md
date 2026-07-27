# Git Local Lab — Common Operations (No Remote)

**Story:** Two teammates, **Ramesh** and **Suresh**, work on the same project. There is no remote yet — Git is only installed locally. We simulate both people on one machine by switching `user.name` / `user.email` and using each person's branch.

**Why this story?** Conflicts and “behind main” problems show up **because two people change the same project over time**, not because Git is broken. Solo practice on one branch hides these issues; two named people make them obvious.

Use simple text files throughout.

**Mental model before you start:** Almost every Git command moves work between three places:
1. **Working folder** — files you edit
2. **Staging area** — files you chose for the next commit (`git add`)
3. **Repository** — permanent history (`git commit`)

---

## Part 1. Ramesh sets up the repo and does everyday Git

### 1. Create a practice folder
```bash
mkdir GitDemo
cd GitDemo
```

**Why:** Keep the practice repo in its own folder so you do not accidentally `git init` inside Documents or another project and mix histories.

### 2. Ramesh configures his identity
```bash
git config --global user.name "Ramesh"
git config --global user.email "ramesh@example.com"
git config --list
```
Check that `user.name=Ramesh` and `user.email=ramesh@example.com` appear.

**Why:** Every commit stores an author name and email. Without this, Git refuses to commit (or uses a wrong identity). In a real team, each person sets this once on their own laptop so history shows *who* made each change. Here we switch identity when we act as Suresh so the log still looks like two people.

### 3. Ramesh initializes the repository
```bash
git init
git branch -m main
```

**Why:**
- `git init` creates the hidden `.git` folder — that *is* the repository (history, branches, config). Without it, Git has nowhere to store commits.
- `git branch -m main` renames the default branch to `main`, the name most teams use for the shared “good” line of work.

### 4. Ramesh creates the first shared file
Create `notes.txt`:
```text
Welcome to Git
Day 1
```

**Why:** Git tracks files. A small text file is enough to practice add/commit/branch/merge without needing Java or a build tool.

### 5. Ramesh: status → stage → commit
```bash
git status
git add notes.txt
git status
git commit -m "Ramesh: add notes.txt"
git log --oneline
```
`git log` should show Author: Ramesh.

**Why each command:**
- `git status` — shows what changed and what is staged. Run this often so you always know the state.
- `git add` — copies the change into the **staging area**. You choose what goes into the next commit (you can leave unfinished edits unstaged).
- `git commit` — saves a snapshot permanently in history with a message. The message should say *why* or *what* so teammates (and future you) can read the log.
- `git log` — proves the commit exists and shows Ramesh as author.

### 6. Ramesh edits again, checks the diff, commits
Change `notes.txt` to:
```text
Welcome to Git
Day 1
Learning basics
```
```bash
git diff
git add notes.txt
git commit -m "Ramesh: add learning line"
```

**Why:** Real work is many small commits, not one giant dump. `git diff` shows exactly what you changed *before* you stage — so you do not commit a typo or an accidental delete by mistake. Then add + commit records that step in history.

### 7. Ramesh ignores junk files
Create `.gitignore` with:
```text
temp.txt
```
Create `temp.txt` with any text, then:
```bash
git add .gitignore
git commit -m "Ramesh: add gitignore"
git status
```
`temp.txt` stays untracked (ignored).

**Why:** Build outputs, logs, and scratch files should not enter history. Committing `.gitignore` itself means **both** Ramesh and Suresh ignore the same patterns. Without it, one person might commit junk that the other then has to clean up.

### 8. Ramesh works on his own feature branch
```bash
git checkout -b feature-ramesh
```
Edit `notes.txt`:
```text
Welcome to Git
Day 1
Learning basics
Ramesh: greeting module
```
```bash
git add notes.txt
git commit -m "Ramesh: add greeting module line"
```

**Why not edit on `main` directly?** `main` is the shared stable line. A **feature branch** lets Ramesh experiment and commit freely. If the work is wrong, `main` is untouched. `git checkout -b` creates the branch and switches to it in one step.

### 9. Ramesh merges his finished work into main
```bash
git checkout main
git merge feature-ramesh -m "Merge Ramesh greeting into main"
git log --oneline --graph --all
```
`main` now has Ramesh’s greeting.

**Why:** When the feature is ready, merge brings those commits into `main` so the team’s shared baseline includes the greeting. `--graph` makes the branch history visible as a picture, which helps when more than one person is involved.

---

## Part 2. Ramesh and Suresh change the same line — merge conflict

**Why this part exists:** In a team, two people often edit related code on different branches. When those branches meet, Git can combine *different* lines automatically — but if they changed the **same lines**, Git stops and asks humans to decide. That stop is a **merge conflict**, not a failure.

### 10. Suresh starts a branch from the current main
Switch identity to Suresh (same PC, different “person”):
```bash
git config --global user.name "Suresh"
git config --global user.email "suresh@example.com"
git checkout main
git checkout -b feature-suresh
```

Suresh edits the last line of `notes.txt` to his version:
```text
Welcome to Git
Day 1
Learning basics
Ramesh: greeting module
Suresh: login message
```
```bash
git add notes.txt
git commit -m "Suresh: add login message"
```

**Why:** Suresh branches from `main` so his work starts from the shared baseline. He switches `user.name` / `user.email` so his commits are attributed to him — in real life this would already be set on his laptop. He commits on his own branch so Ramesh’s `main` is not disturbed while Suresh is still coding.

### 11. Meanwhile Ramesh also changes that same area on main
Switch back to Ramesh:
```bash
git config --global user.name "Ramesh"
git config --global user.email "ramesh@example.com"
git checkout main
```

Ramesh changes the **same last line** differently:
```text
Welcome to Git
Day 1
Learning basics
Ramesh: greeting module
Ramesh: welcome banner
```
```bash
git add notes.txt
git commit -m "Ramesh: add welcome banner on main"
```

Now:
- `main` has Ramesh’s welcome banner
- `feature-suresh` still has Suresh’s login message (based on older main)

**Why we do this on purpose:** This is the everyday team race: Suresh started from yesterday’s `main`; Ramesh kept shipping on `main`. Both changed the same spot. That is when conflict appears — not when people work on unrelated files.

### 12. Merge Suresh’s branch into main — conflict
```bash
git checkout main
git merge feature-suresh
```
Git stops. Open `notes.txt` — markers look like:
```text
<<<<<<< HEAD
Ramesh: welcome banner
=======
Suresh: login message
>>>>>>> feature-suresh
```

**Why Git stops:** `HEAD` (current `main`) has one version of the line; `feature-suresh` has another. Git will not guess which text is correct — that is a product decision for Ramesh and Suresh.

**How to read the markers:**
- `<<<<<<< HEAD` … `=======` — what is already on the branch you merged *into* (here: main / Ramesh)
- `=======` … `>>>>>>> feature-suresh` — what came from the branch you merged *in* (Suresh)

### 13. They agree on the final text and resolve
After talking, they keep **both** ideas:
```text
Welcome to Git
Day 1
Learning basics
Ramesh: greeting module
Ramesh: welcome banner
Suresh: login message
```
Remove all `<<<<<<<`, `=======`, `>>>>>>>` markers, then:
```bash
git add notes.txt
git commit -m "Resolve conflict: keep Ramesh banner and Suresh login"
git status
git log --oneline --graph --all
```

**Why these steps:**
- Editing the file is the *human* decision (keep Ramesh’s, Suresh’s, both, or something new).
- Markers must be deleted — they are not real content; leaving them breaks the file.
- `git add` tells Git “conflict in this file is resolved.”
- `git commit` finishes the merge and records the agreed result in history.
- `git status` should be clean — confirmation that the merge is done.

### 14. Optional: if they are not ready to decide
```bash
git merge --abort
```

**Why:** Returns `main` to how it was before the merge. Use this if you opened the merge by mistake, or you need to talk to the other person first. Better than leaving a half-finished conflicted merge.

### 15. Useful cleanup
```bash
git branch
git branch -d feature-ramesh
git checkout -- notes.txt
git reset HEAD notes.txt
```

**Why each is useful:**
- `git branch` — list branches; know where you are (`*`).
- `git branch -d` — delete a feature branch **after** it is merged, so the list stays tidy. Git blocks `-d` if the work was never merged (safety).
- `git checkout -- <file>` — throw away *uncommitted* edits in that file and restore the last commit. Use when you regret a local edit (cannot undo after a later commit without more advanced commands).
- `git reset HEAD <file>` — **unstage** (undo `git add`) but keep your edits in the working folder. Use when you staged too much by mistake.

---

## Part 3. Main moved ahead while Suresh is still on an older branch

### The situation (two people)

1. Suresh branches from `main` and starts work.
2. While he is busy, **Ramesh** commits more work on `main`.
3. Suresh’s branch is now based on an **older** `main`.
4. If Suresh merges into `main` without updating first, he risks big conflicts and shipping without Ramesh’s fixes/docs.

**Why this is different from Part 2:** Part 2 was “same line, two versions.” Part 3 is “I worked for days on a branch while the shared `main` kept moving.” Even if files do not conflict, Suresh’s branch is **missing** Ramesh’s later commits until he updates.

```
main (Ramesh):     A --- B --- C --- D     (D = Ramesh's new work)
                         \
feature-login:            E --- F         (Suresh started from B — outdated)
```

After Suresh updates his branch by merging latest `main` into it:

```
main (Ramesh):     A --- B --- C --- D
                         \         \
feature-login:            E --- F --- M   (M brings Ramesh's D into Suresh's branch)
```

**Why update the feature branch first (not dump into main blindly)?** Suresh should absorb Ramesh’s latest `main` *on the feature branch*, fix any clashes there, and only then merge into `main`. That keeps `main` stable and puts the pain of catching up on the person who was behind.

---

### 16. Ramesh adds shared team files on main
```bash
git config --global user.name "Ramesh"
git config --global user.email "ramesh@example.com"
git checkout main
```

Create `team.txt`:
```text
Project: Campus Demo
Owner: Ramesh
```
```bash
git add team.txt
git commit -m "Ramesh: add team.txt"
```

**Why:** Shared project facts live on `main` so everyone can build on them. This commit is the baseline Suresh will branch from next.

### 17. Suresh branches off and starts his feature (old base)
```bash
git config --global user.name "Suresh"
git config --global user.email "suresh@example.com"
git checkout -b feature-login
```

Suresh edits `notes.txt` (his feature only):
```text
Welcome to Git
Day 1
Learning basics
Ramesh: greeting module
Ramesh: welcome banner
Suresh: login message
Suresh: login feature started
```
```bash
git add notes.txt
git commit -m "Suresh: start login feature"
```

**Why:** Suresh isolates login work on `feature-login`. His branch “remembers” the `main` tip **at branch time**. Anything Ramesh adds to `main` later is invisible to Suresh until Suresh deliberately merges or rebases.

### 18. Meanwhile Ramesh moves main ahead
```bash
git config --global user.name "Ramesh"
git config --global user.email "ramesh@example.com"
git checkout main
```

Ramesh updates `team.txt`:
```text
Project: Campus Demo
Owner: Ramesh
Sprint: Week 2
```

Ramesh also creates `readme.txt`:
```text
How to run: open notes.txt
Contact: ramesh@example.com
```
```bash
git add team.txt readme.txt
git commit -m "Ramesh: add sprint and readme on main"
git log --oneline --graph --all
```

**Why:** This simulates the teammate who keeps delivering on `main` (docs, sprint info, bugfixes) while you are heads-down on a feature. `git log --graph --all` is how Suresh later *sees* that he is behind.

`main` is ahead. `feature-login` does **not** have `readme.txt` or the Sprint line.

### 19. Suresh comes back — his branch is behind
```bash
git config --global user.name "Suresh"
git config --global user.email "suresh@example.com"
git checkout feature-login
git log --oneline --graph --all
```

Suresh still has his login work, but he is missing Ramesh’s latest commits.

**Why he must not merge into `main` yet:** Merging an outdated feature into `main` can overwrite or fight with Ramesh’s newer work, and may drop important files/lines from the “latest main” view until someone cleans up. Catch up on the feature branch first, then merge.

### 20. Suresh updates his branch: merge latest main into feature-login
```bash
git checkout feature-login
git merge main -m "Suresh: merge latest main (Ramesh's work) into feature-login"
```

**Why this direction:** `git merge main` *while on* `feature-login` means: “Bring Ramesh’s latest shared work **into my feature**.” Suresh stays in control of his branch, resolves problems there, and only later offers a finished branch to `main`.

#### If there is no conflict
Git finishes. Suresh’s branch now has:
- his login work, **and**
- Ramesh’s `team.txt` / `readme.txt` updates

```bash
git log --oneline --graph --all
git status
```

When Suresh is done, merge into main:
```bash
git checkout main
git merge feature-login -m "Merge Suresh login feature"
```

**Why merge into main only now:** `feature-login` already contains Ramesh’s latest `main` plus Suresh’s feature, so integrating into `main` is usually a fast-forward or a clean merge.

#### If there is a conflict (both touched the same file)

Example: Suresh also edited `team.txt` on his branch (Owner: Suresh) while Ramesh set Sprint on main. After `git merge main`, Suresh may see:

```text
<<<<<<< HEAD
Owner: Suresh
=======
Owner: Ramesh
Sprint: Week 2
>>>>>>> main
```

**Why:** Same rule as Part 2 — overlapping edits. Here `HEAD` is Suresh’s feature branch; the other side is Ramesh’s `main`.

They agree on the final content, for example:
```text
Project: Campus Demo
Owner: Ramesh
Co-owner: Suresh
Sprint: Week 2
```

Then Suresh runs:
```bash
git add team.txt
git commit -m "Suresh: resolve conflict after merging Ramesh's main"
```

**Why add + commit again:** Same as any conflict: mark resolved, then record the merge result.

To cancel and try later:
```bash
git merge --abort
```

**Why abort:** Leaves `feature-login` as it was before the update attempt — useful if Suresh needs to ask Ramesh what the correct Owner/Sprint text should be.

### 21. Alternate approach Suresh can use: rebase onto main
Same story as steps 18–19. Instead of merging main into his branch:

```bash
git checkout feature-login
git rebase main
```

**Why rebase exists:** It replays Suresh’s commits **on top of** Ramesh’s latest `main`, producing a straight-line history (no merge commit). Some teams prefer that for readability.

- On conflict: fix the file, then:
```bash
git add <conflicted-file>
git rebase --continue
```
- To cancel:
```bash
git rebase --abort
```

| Approach | Who runs it | Why choose it |
|----------|-------------|----------------|
| `git merge main` on feature | Suresh | Safer and simpler; merge commit clearly shows “I took Ramesh’s main into my branch” |
| `git rebase main` on feature | Suresh | Straight history; use only if those commits are **not** already shared with others (rewriting shared commits confuses teammates) |

For this campus lab, prefer **merge**.

### 22. Checklist for Suresh — “Ramesh moved main while I was working”

1. Finish or stash your edits on `feature-login` — **why:** do not mix half-saved work with a merge.
2. Look at `main` / `git log --graph --all` — **why:** confirm you are actually behind.
3. `git checkout feature-login`
4. `git merge main` (or `git rebase main`) — **why:** bring Ramesh’s work into your branch first.
5. Talk to Ramesh if conflicted → fix file → `git add` → `git commit` (or `git rebase --continue`) — **why:** only humans can choose the final text.
6. Only then merge `feature-login` into `main` — **why:** keep the shared branch stable.

---

## Who did what (quick cast)

| Person | Role in this lab |
|--------|------------------|
| **Ramesh** | Creates the repo, owns `main`, keeps adding shared files while others work |
| **Suresh** | Works on feature branches; hits conflict when same lines change; must update when `main` moves |

**Takeaway:** Git issues like conflicts and stale branches appear when **more than one person** changes the project over time. The fix is always: communicate, bring latest `main` into your branch, resolve together, then merge.

---

## What this lab covers

- Config (name / email), `init`, `status`, `add`, `commit`, `log`, `diff`, `.gitignore` — and **why** each exists
- Branch / checkout / merge / conflict resolve / `merge --abort`
- Two people (Ramesh & Suresh) editing the same project
- Merge conflict when both change the same line
- Stale feature branch when Ramesh advances `main` while Suresh is still working — and **why** you update the feature before merging to `main`
