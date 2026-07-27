# Git Local Lab — Solo Practice (No Remote)

**Goal:** One person, one machine. Practice the common Git commands and **watch what each one does** to your files and history. No teammates, no remote — Git only needs to be installed.

**How to use this:** After every command that changes state, run `git status` and often `git log --oneline --graph --all`. That is how you *see* the impact.

**Mental model:** Almost every Git command moves work between three places:
1. **Working folder** — files you edit
2. **Staging area** — files you chose for the next commit (`git add`)
3. **Repository** — permanent history (`git commit`)

Use simple text files throughout.

---

## Part 1. Everyday Git on one machine

### 1. Create a practice folder

mkdir GitSolo
cd GitSolo


**Why:** Keep practice work in its own folder so you do not run `git init` inside another project by mistake.

**See the impact:** `dir` / `ls` shows an empty folder. There is no `.git` yet.

### 2. Set your name and email

git config --global user.name "Your Name"
git config --global user.email "you@example.com"
git config --list


**Why:** Every commit stores an author. Git needs these before it will commit cleanly.

**See the impact:** `git config --list` shows `user.name` and `user.email`. These apply to all your repos (`--global`).

### 3. Initialize the repository

git init
git branch -m main


**Why:**
- `git init` creates the hidden `.git` folder (the real repository).
- `git branch -m main` names the default branch `main`.

**See the impact:**

git status

You should see something like: `On branch main`, `No commits yet`. A `.git` folder now exists (may be hidden).

### 4. Create the first file
Create `notes.txt`:
text
Welcome to Git
Day 1


**Why:** Git tracks file changes. A text file is enough to practice.

**See the impact:**

git status

`notes.txt` appears under **Untracked files** — Git sees it but is not recording it yet.

### 5. Status → stage → commit → log

git status
git add notes.txt
git status
git commit -m "Add notes.txt"
git log --oneline


**Why each command:**
- `git status` — shows untracked / staged / committed state
- `git add` — moves the file into the **staging area**
- `git commit` — saves a snapshot into history
- `git log` — lists that history

**See the impact:**
- After `git add`: status says **Changes to be committed**
- After `git commit`: status is clean (“nothing to commit”)
- `git log` shows one commit with your message

### 6. Edit, diff, commit again
Change `notes.txt` to:
text
Welcome to Git
Day 1
Learning basics


git diff
git add notes.txt
git commit -m "Add learning line"
git log --oneline


**Why:** Real work is many small commits. `git diff` shows exactly what changed *before* you stage.

**See the impact:**
- `git diff` (before add) shows the new line with a `+`
- After commit, `git log --oneline` shows **two** commits
- `git diff` now prints nothing (working folder matches the last commit)

### 7. Ignore junk files
Create `.gitignore` with:
text
temp.txt

Create `temp.txt` with any text, then:

git add .gitignore
git commit -m "Add gitignore"
git status


**Why:** Scratch files should not enter history. Committing `.gitignore` makes those rules part of the project.

**See the impact:**
- `temp.txt` does **not** show as untracked (ignored)
- Try `git add temp.txt` — Git refuses or warns that it is ignored
- `.gitignore` itself *is* in history (`git log` / `git ls-files`)

### 8. Work on a feature branch (not on main)

git checkout -b feature-a

Edit `notes.txt`:
text
Welcome to Git
Day 1
Learning basics
Feature A change


git add notes.txt
git commit -m "Feature A update"
git log --oneline --graph --all
git branch


**Why:** Keep experiments off `main`. `main` stays stable until you choose to merge.

**See the impact:**
- `git branch` shows `* feature-a` (star = current branch)
- `git log --graph --all` shows your new commit only on `feature-a`
- Switch back briefly:

git checkout main

Open `notes.txt` — the “Feature A change” line is **gone** (that commit is not on `main` yet).

git checkout feature-a

The line is back. That is the point of branches.

### 9. Merge the feature into main

git checkout main
git merge feature-a -m "Merge feature-a"
git log --oneline --graph --all


**Why:** When the feature is ready, merge brings it into the shared baseline (`main`).

**See the impact:**
- `notes.txt` on `main` now includes “Feature A change”
- Log shows `main` and `feature-a` at the same commit (often a fast-forward)
- Optional cleanup:

git branch -d feature-a
git branch

`feature-a` disappears after a successful delete.

---

## Part 2. Create and resolve a merge conflict (solo)

**Why practice this alone?** You play both sides by using two branches that edit the **same line**. Same mechanics as a team conflict — you just create both edits yourself.

### 10. Create branch feature-b and change a line

git checkout main
git checkout -b feature-b

Set `notes.txt` to:
text
Welcome to Git
Day 1
Learning basics
Feature A change
Feature B version


git add notes.txt
git commit -m "Feature B change"


**See the impact:** This commit exists only on `feature-b`.

### 11. On main, change the same line differently

git checkout main

Set `notes.txt` to:
text
Welcome to Git
Day 1
Learning basics
Feature A change
Main version


git add notes.txt
git commit -m "Main change to same line"
git log --oneline --graph --all


**Why:** Now `main` and `feature-b` both changed the same spot after they diverged. That is the conflict setup.

**See the impact:** Graph shows two diverging tips — `main` and `feature-b` are no longer the same commit.

### 12. Merge and watch Git stop

git checkout main
git merge feature-b


**See the impact:**
- Terminal reports `CONFLICT`
- `git status` lists an **unmerged** path
- Open `notes.txt` — markers appear:
text
<<<<<<< HEAD
Main version
=======
Feature B version
>>>>>>> feature-b


**How to read the markers:**
- `<<<<<<< HEAD` … `=======` — version on the branch you are on (`main`)
- `=======` … `>>>>>>> feature-b` — version coming from `feature-b`

### 13. Resolve, then finish the merge
Edit `notes.txt` to the final content you want (delete all markers), for example:
text
Welcome to Git
Day 1
Learning basics
Feature A change
Resolved final version


git add notes.txt
git commit -m "Resolve merge conflict"
git status
git log --oneline --graph --all


**Why:**
- Edit = your decision about the final text
- `git add` = “this conflict is resolved”
- `git commit` = finish the merge and save it

**See the impact:** Status is clean. Log shows a merge commit (or completed merge) joining both lines of history. Markers are gone from the file.

### 14. Optional: practice abort instead of resolving
If you want to try abort, create another small conflict later, start a merge, then:

git merge --abort


**See the impact:** Conflict markers disappear; branch returns to the pre-merge state. Use this when you opened a merge by mistake.

### 15. Useful undo / cleanup commands (practice carefully)

git branch

**Impact:** Lists branches; `*` marks the current one.

Make a throwaway edit to `notes.txt`, then:

git checkout -- notes.txt

**Impact:** Uncommitted edits in that file are discarded; file matches the last commit.

Stage a file, then unstage:

git add notes.txt
git reset HEAD notes.txt
git status

**Impact:** File is no longer staged; your edits remain in the working folder.

---

## Part 3. Main moved ahead while you are on an older branch

**Why this part:** You start a feature from `main`, keep working, then later commit more work on `main` itself. Your feature is based on an **older** `main`. You must update the feature branch before merging it back.


main:        A --- B --- C --- D     (D = new work on main)
                   \
feature-login:      E --- F         (started from B — outdated)


After you merge latest `main` into the feature:


main:        A --- B --- C --- D
                   \         \
feature-login:      E --- F --- M   (M brings D into the feature)


### 16. Add a shared file on main

git checkout main

Create `team.txt`:
text
Project: Campus Demo
Owner: Me


git add team.txt
git commit -m "Add team.txt on main"


**See the impact:** `main` has a new commit; other branches do not have `team.txt` until they merge or rebase.

### 17. Branch and start feature work

git checkout -b feature-login

Append a line to `notes.txt`, for example:
text
Login feature started

(keep the earlier lines; just add this at the end)

git add notes.txt
git commit -m "Start login feature"
git log --oneline --graph --all


**Why:** Feature work is isolated. This branch’s base is whatever `main` was **when you branched**.

**See the impact:** New commit appears only on `feature-login`.

### 18. Switch to main and move it ahead

git checkout main

Update `team.txt`:
text
Project: Campus Demo
Owner: Me
Sprint: Week 2

Create `readme.txt`:
text
How to run: open notes.txt


git add team.txt readme.txt
git commit -m "Update team info and add readme on main"
git log --oneline --graph --all


**Why:** You are simulating “more work landed on main while I was still on my feature.”

**See the impact:** `main` tip is ahead of `feature-login`. `readme.txt` exists on `main` only.

### 19. Go back to the feature — notice you are behind

git checkout feature-login
git log --oneline --graph --all


**See the impact:**
- Your login commit is still there
- `readme.txt` is **missing** on this branch
- Graph shows `main` has commits you do not

**Do not merge `feature-login` into `main` yet.** Update the feature first.

### 20. Bring latest main into your feature branch

git checkout feature-login
git merge main -m "Merge latest main into feature-login"


**Why this direction:** Merge `main` *into* the feature so you absorb new shared work on your branch, fix problems there, then merge to `main` when ready.

#### If there is no conflict

git log --oneline --graph --all
git status
ls


**See the impact:** `readme.txt` and the Sprint line are now on `feature-login` too, along with your login change.

Then finish:

git checkout main
git merge feature-login -m "Merge feature-login"
git log --oneline --graph --all


**See the impact:** `main` has both the main-only updates and the login feature.

#### If you want to practice a conflict during the update
On `feature-login` *before* merging main, also change `team.txt` (e.g. different Owner line), commit it, then `git merge main`. Resolve markers, then:

git add team.txt
git commit -m "Resolve conflicts after merging main into feature-login"


Or cancel:

git merge --abort


**See the impact of abort:** Branch returns to the state before the merge attempt.

### 21. Alternate: rebase onto main (optional)
Same “behind main” setup as steps 17–19. Instead of merge:

git checkout feature-login
git rebase main


**Why:** Replays your feature commits on top of latest `main` for a straight-line history.

On conflict:

git add <conflicted-file>
git rebase --continue

To cancel:

git rebase --abort


| Approach | Command (on feature) | Why choose it |
|----------|----------------------|----------------|
| Merge | `git merge main` | Simpler; creates a merge commit you can see in the graph |
| Rebase | `git rebase main` | Linear history; avoid if those commits were already shared with others |

For learning, prefer **merge** first so the graph clearly shows the join.

### 22. Checklist — “my branch is old”

1. Finish or stash edits on the feature — avoid mixing half-done work with a merge  
2. `git log --oneline --graph --all` — confirm `main` is ahead  
3. `git checkout <feature-branch>`  
4. `git merge main` (or `git rebase main`) — bring latest main in  
5. Fix conflicts if any → `git add` → `git commit` / `git rebase --continue`  
6. Only then `git checkout main` and `git merge <feature-branch>`

**See the impact at every step** with `git status` and `git log --oneline --graph --all`.

---

## Command cheat sheet (what to watch)

| Command | What it does | What you should notice |
|---------|--------------|-------------------------|
| `git status` | Shows branch + staged/unstaged/untracked | State changes after almost every step |
| `git add` | Stages files | Status moves files to “to be committed” |
| `git commit` | Records a snapshot | New entry in `git log`; status clean |
| `git diff` | Unstaged changes | `+` / `-` lines; empty after commit |
| `git log --oneline --graph --all` | History picture | Branches, merges, who is ahead |
| `git branch` / `checkout -b` | List / create+switch | `*` moves; files can differ per branch |
| `git merge` | Join another branch | Fast-forward, merge commit, or CONFLICT |
| `git merge --abort` | Cancel a merge | Back to pre-merge state |
| `git rebase` | Replay commits on new base | Linear history; may stop on conflicts |
| `git checkout -- <file>` | Discard local edits | File matches last commit |
| `git reset HEAD <file>` | Unstage | Edits remain; no longer staged |

---

## What this lab covers

- Config, `init`, `status`, `add`, `commit`, `log`, `diff`, `.gitignore`
- Branches, merge, conflict markers, resolve, `merge --abort`
- Updating a feature branch after `main` moved ahead (merge or rebase)
- Reading **impact** after each step with `status` and `log --graph`
