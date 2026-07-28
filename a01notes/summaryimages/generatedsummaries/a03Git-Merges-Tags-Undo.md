# Git Lab — Merges, Tags, References & Undo

Small local examples. No remote needed. After each step, use `git status` and `git log --oneline --graph --all --decorate` to see the impact.

```bash
mkdir GitMergeTagDemo
cd GitMergeTagDemo
git init
git branch -m main
git config user.name "Demo User"
git config user.email "demo@example.com"
```

Create `app.txt` with:
```text
Version 1
```
```bash
git add app.txt
git commit -m "Start app.txt"
```

---

## Part 1. Fast-forward merge

### What it is
A **fast-forward** happens when the branch you merge *into* has no new commits of its own since the other branch started. Git only **slides the branch pointer forward**. No merge commit is created. History stays a straight line.

```
main:        A
              \
feature:       B --- C

After merge into main:
main / feature:  A --- B --- C
```

### Example
```bash
git checkout -b feature-ff
```
Change `app.txt` to:
```text
Version 1
Feature FF line
```
```bash
git add app.txt
git commit -m "Feature FF work"

git checkout main
git merge feature-ff
git log --oneline --graph --all --decorate
```

### What you should see
- Message like **Fast-forward**
- `main` and `feature-ff` point at the **same** commit
- Graph is a straight line (no join with two parents)

**Why it matters:** Simple and clean. You did not fork history on `main` while the feature was in progress.

Optional cleanup:
```bash
git branch -d feature-ff
```

---

## Part 2. True merge (3-way merge)

### What it is
A **true merge** happens when **both** branches have moved ahead. Git combines them and creates a **merge commit with two parents**.

```
main:        A --- D
              \
feature:       B --- C

After merge into main:
main:        A --- D ------ M
              \           /
feature:       B --- C --/
```

### Example
```bash
git checkout -b feature-true
```
Change `app.txt` to (keep previous lines, add one):
```text
Version 1
Feature FF line
Feature true branch line
```
```bash
git add app.txt
git commit -m "Work on feature-true"

git checkout main
```
Change `app.txt` differently (same file, **different** line so it merges cleanly):
```text
Version 1
Feature FF line
Main continued line
```
```bash
git add app.txt
git commit -m "More work on main while feature was open"

git merge feature-true -m "True merge: main + feature-true"
git log --oneline --graph --all --decorate
```

### What you should see
- Not “Fast-forward”
- A merge commit appears
- Graph shows two lines joining into one

**Why it matters:** This is the normal team case — `main` kept moving while a feature branch was open. The merge commit records that join.

> If both sides change the **same lines**, you get a conflict. Fix the file, `git add`, then `git commit`.

---

## Part 3. Tagging

### What it is
A **tag** is a fixed name for one commit — usually a release (`v1.0`). Unlike a branch, a tag does not move when you make new commits.

| Type | Command | What it stores |
|------|---------|----------------|
| Lightweight | `git tag v1.0` | Just a name pointing at a commit |
| Annotated | `git tag -a v1.0 -m "msg"` | Name + message + tagger + date (preferred for releases) |

### Example
```bash
git checkout main
git log --oneline
```
Pick the current tip (or an older SHA) and tag it:
```bash
git tag -a v1.0 -m "First stable release"
git tag
git show v1.0
git log --oneline --decorate
```

Lightweight tag (optional compare):
```bash
git tag v1.0-lite
git show v1.0-lite
```

### What each does
- `git tag -a ...` — marks this commit as a release you can find later
- `git tag` — lists tag names
- `git show v1.0` — shows tag metadata and the commit it points to
- `--decorate` on log — prints tag names next to commits
hoy 
Delete a practice tag:
```bash
git tag -d v1.0-lite
```

**Why:** Months later you can return to “exactly what we shipped” without remembering a SHA:
```bash
git checkout v1.0
```
(Detached HEAD — fine for looking. Switch back with `git checkout main`.)

---

## Part 4. Referencing commits (“tree-ish”)

From the slide: ways to point at a commit without always copying a long SHA.

| Reference | Meaning |
|-----------|---------|
| Full / short SHA | Exact commit id (`a1b2c3d`) |
| `HEAD` | Commit you are on now |
| Branch / tag | Tip of that branch, or the tagged commit (`main`, `v1.0`) |
| Ancestry | Parent / grandparent of a commit |

### Ancestry examples
```bash
git log --oneline -5

git show HEAD^
git show main^
git show HEAD~2
```

| Expression | What it does |
|------------|--------------|
| `HEAD^` | Parent of the current commit (one step back) |
| `main^` | Parent of the tip of `main` |
| `a1b2c3d^` | Parent of that specific commit |
| `HEAD~2` | Two steps back from HEAD (grandparent) |

**Note:** For a normal commit, `HEAD^` and `HEAD~1` are the same. On a **merge commit**, `HEAD^1` is the first parent (usually the branch you were on) and `HEAD^2` is the second parent (the branch you merged in).

**Why:** Lets you say “previous commit” or “two commits ago” without hunting SHAs.

---

## Part 5. Navigating the tree

A commit points at a **tree** (the folder snapshot). `git ls-tree` lists files/folders stored in that snapshot.

```bash
git ls-tree HEAD
git ls-tree main^
git ls-tree v1.0
```

### What it does
- `git ls-tree HEAD` — files/folders in the **current** commit
- `git ls-tree main^` — files/folders in the **parent** of `main`’s tip
- `git ls-tree v1.0` — snapshot as it was at the tag

**Why:** Confirms what was in the project at that point in history (blobs = files, trees = folders).

---

## Part 6. Commit log — view, show, compare

### Viewing log (most useful form)
```bash
git log --oneline --graph --all --decorate
```

| Flag | What it does |
|------|----------------|
| `--oneline` | One line per commit |
| `--graph` | Text drawing of branch joins |
| `--all` | Every branch, not only current |
| `--decorate` | Shows branch and tag names |

**Why:** Best single command to see fast-forward vs true merge, and where tags sit.

### Showing details of one commit
```bash
git show HEAD
git show v1.0
git show HEAD~1
```

**What it does:** Prints the commit message and the **diff** introduced by that commit.

### Comparing commits
```bash
git diff HEAD~1 HEAD
git diff main feature-true
git diff v1.0 HEAD
```

**What it does:** Shows line changes between two points (working tree, index, or any two commits/branches/tags).

---

## Part 7. Undo changes

Practice these one at a time. Prefer `revert` for anything you would share; prefer `reset` / amend only on local unpushed work.

### Setup for undo demos
```bash
git checkout main
```
Ensure `app.txt` exists and make a small edit you do **not** stage yet, e.g. add a line `TEMP EDIT`.

### 1. Discard working-directory edits → `git checkout`
```bash
git status
git checkout app.txt
git status
```

**What it does:** Throws away **uncommitted** edits in that file and restores the last committed version.
**When:** “I ruined this file locally; I have not committed; put it back.”

> Newer Git also uses: `git restore app.txt`

### 2. Unstage (keep edits) → `git reset HEAD`
Edit `app.txt` again, then:
```bash
git add app.txt
git status
git reset HEAD app.txt
git status
```

**What it does:** Removes the file from the **staging area** but keeps your edits in the working folder.

**When:** “I ran `git add` too early / added the wrong file.”

> Newer Git also uses: `git restore --staged app.txt`

### 3. Fix the last commit → `git commit --amend`
```bash
echo "Forgot this line" >> app.txt
git add app.txt
git commit --amend -m "Start app.txt (amended message or add forgotten file)"
git log --oneline -3
```

**What it does:** Replaces the **latest** commit with a new one (new SHA) that includes your staged fixes and/or a new message.

**When:** Typo in the last message, or you forgot one file in the last commit.  
**Do not amend** a commit already shared with others.

### 4. Retrieve an older version of one file → `git checkout <SHA> -- <file>`
```bash
git log --oneline
git checkout HEAD~2 -- app.txt
git status
git diff --staged
```

**What it does:** Checks out that file **as it was in `<SHA>`** into your working tree and stages it. History of other files is unchanged.

**When:** “Bring back yesterday’s version of this one file.”  
Commit if you want to keep it:
```bash
git commit -m "Restore older app.txt from HEAD~2"
```

### 5. Undo a commit safely (new commit) → `git revert <SHA>`
```bash
git log --oneline
git revert HEAD --no-edit
git log --oneline --graph -5
```

**What it does:** Creates a **new** commit that reverses the changes of `<SHA>`. History is not rewritten.

**When:** Safest undo after work is shared / would be pushed. Prefer this over `reset --hard` on shared branches.

| Goal | Command | Rewrites history? |
|------|---------|-------------------|
| Drop local uncommitted edits | `git checkout -- file` | No |
| Unstage | `git reset HEAD file` | No |
| Fix last local commit | `git commit --amend` | Yes (last commit SHA changes) |
| Restore one file from old commit | `git checkout SHA -- file` | No (until you commit) |
| Undo a commit publicly | `git revert SHA` | No (adds a commit) |

---

## Part 8. Rebase — clean example

### What rebase is
`git rebase` takes the commits that are **only on your branch** and **replays them on top of another branch** (usually latest `main`). History becomes a straight line. Unlike merge, rebase does **not** create a merge commit.
git b
```
Before:
main:        A --- B --- C
              \
feature:       D --- E

After: git checkout feature && git rebase main
main:        A --- B --- C
                          \
feature:                   D' --- E'
```

`D'` and `E'` are new commits with the same changes as `D` and `E`, but new SHAs (history was rewritten).

**Merge vs rebase (same starting point):**
| | `git merge main` (on feature) | `git rebase main` (on feature) |
|--|-------------------------------|--------------------------------|
| Result | Merge commit joining both lines | Straight line on top of main |
| History | Preserves exact branch shape | Rewrites your branch commits |
| Safety | Always safe to use | Only on commits **not** shared with others |

---

### Example A — Rebase a feature onto updated main

Use a fresh folder if your earlier demo history is messy:

```bash
mkdir GitRebaseDemo
cd GitRebaseDemo
git init
git branch -m main
git config user.name "Demo User"
git config user.email "demo@example.com"

echo "base" > notes.txt
git add notes.txt
git commit -m "Base commit"
```

**1. Start a feature branch and make two commits**
```bash
git checkout -b feature-login

echo "login page" >> notes.txt
git add notes.txt
git commit -m "Add login page"

echo "login validation" >> notes.txt
git add notes.txt
git commit -m "Add login validation"

git log --oneline --graph --all --decorate
```

**2. Meanwhile, main moves ahead (new shared work)**
```bash
git checkout main

echo "readme" > readme.txt
git add readme.txt
git commit -m "Add readme on main"

git log --oneline --graph --all --decorate
```

You should see `main` and `feature-login` diverged.

**3. Rebase the feature onto latest main**
```bash
git checkout feature-login
git rebase main
git log --oneline --graph --all --decorate
```

### What you should see
- No merge commit
- `feature-login` sits **on top of** main’s latest commit
- Your two feature commits are still there (new SHAs)
- `readme.txt` is now in the feature branch too

**What `git rebase main` did:** temporarily set aside your feature commits, moved your branch to match `main`, then replayed your commits one by one on top.

**4. After rebase, merge into main is usually a fast-forward**
```bash
git checkout main
git merge feature-login
git log --oneline --graph --all --decorate
```

---

### If a conflict happens during rebase
Git stops on the conflicting commit. Fix the file, then:

```bash
git add <conflicted-file>
git rebase --continue
```

To cancel the whole rebase and go back to how the branch was before:

```bash
git rebase --abort
```

**Why abort exists:** You can safely bail out if the conflict is confusing; nothing on main is damaged.

---


### When to use rebase
- Your feature branch is behind `main` and you want a **linear** history
- You want to clean up **local** commit messages before sharing
- Prefer **merge** if you are unsure, or if the commits are already on a shared remote

---

## Quick map: slide topics → commands

**Merges**
- Fast-forward: merge when `main` did not move → pointer slides
- True merge: both sides moved → merge commit with two parents

**Tags**
- `git tag -a v1.0 -m "..."` / `git tag` / `git show v1.0`

**Tree-ish & history (picture 1)**
- `HEAD^`, `HEAD~2`, branch, tag, short SHA
- `git ls-tree HEAD`
- `git log --oneline --graph --all --decorate`
- `git show` / `git diff`

**Undo (picture 2)**
- `git checkout` → working directory
- `git reset HEAD` → unstage
- `git commit --amend` → last commit
- `git checkout <SHA> -- <file>` → older file version
- `git revert <SHA>` → new opposite commit

**Rebase**
- `git rebase main` → replay feature commits on top of main
- `git rebase -i HEAD~n` → edit recent local commits
- `git rebase --continue` / `git rebase --abort` → finish or cancel after conflict

---

## Suggested practice order

1. Fast-forward merge → confirm straight graph  
2. True merge → confirm merge commit / forked graph  
3. Annotated tag on `main` → `git show` / decorate  
4. `HEAD^`, `HEAD~2`, `git ls-tree`, full log command  
5. Each undo command once, checking `git status` and `git log` after  
6. Rebase feature onto updated `main` → confirm linear graph, then optional interactive reword
