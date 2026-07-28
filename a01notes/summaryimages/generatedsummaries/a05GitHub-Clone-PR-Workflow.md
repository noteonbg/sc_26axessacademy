# GitHub Workflow — Clone, Branch, Pull Request, Stay Updated

Simple solo-friendly flow. You work on a **branch**, open a **Pull Request (PR)** to merge into `main`, then keep your local `main` up to date.

Replace:
- `YOUR_USER` with your GitHub username  
- `YOUR_REPO` with the repository name  
- `feature-login` with any short feature name  


This is my repo location
https://github.com/noteonbg/pocrepo.git
i have known how to contact github and is is already known..

---

## What you will do (big picture)

```
1. Clone the GitHub repo to your laptop
2. Create a branch → edit files → commit → push the branch
3. Open a Pull Request on GitHub → merge it into main
4. Pull latest main to your laptop
5. Start a new branch for the next task
6. If main changed again while you were working → update your branch first
```

---

## Part 1. Clone a repo from GitHub

### Step 1 — Copy the repo URL
On GitHub: open the repository → green **Code** button → copy the HTTPS URL, for example:

```text
https://github.com/YOUR_USER/YOUR_REPO.git
```

### Step 2 — Clone it
```bash
cd Documents
git clone https://github.com/YOUR_USER/YOUR_REPO.git
cd YOUR_REPO
```

**What this does:** Downloads the whole project (files + history) into a new folder and sets `origin` as the nickname for that GitHub URL.

### Step 3 — Check you are on main
```bash
git status
git branch
git remote -v
```

**What you should see:** On `main` (or `master`), and `origin` pointing at GitHub.

---

## Part 2. Create a branch, add work, push, request a merge

### Step 4 — Make sure local main is current
```bash
git checkout main
git pull origin main
```

**What this does:** Downloads the latest commits from GitHub’s `main` into your laptop. Always do this before starting new work.

### Step 5 — Create and switch to a feature branch
```bash
git checkout -b feature-login
```

**Why a branch?** Keep unfinished work off `main`. `main` stays the shared stable line.

### Step 6 — Edit files, stage, commit
Create or edit a simple file, for example `notes.txt`:
```text
Login feature started
```
```bash
git status
git add notes.txt
git commit -m "Add login notes"
git status
```

**What this does:** Saves your change in **local** history on `feature-login` only. GitHub does not have it yet.

### Step 7 — Push your branch to GitHub
```bash
git push -u origin feature-login
```

**What this does:** Uploads the branch to GitHub and links local `feature-login` to `origin/feature-login` (`-u` = set upstream so later you can just run `git push`).

### Step 8 — Open a Pull Request (request to merge)
1. Open the repo on GitHub in a browser  
2. You often see a banner **Compare & pull request** — click it  
   (or: **Pull requests** → **New pull request**)  
3. Base branch: `main` ← compare: `feature-login`  
4. Add a short title and description  
5. Click **Create pull request**

**What a PR is:** A request saying “please review these commits and merge them into `main`.” On GitHub this is the normal way to merge — not merging straight on your laptop into shared `main` (unless your team says otherwise).

### Step 9 — Merge the Pull Request
On the PR page:
1. Review the files changed  
2. Click **Merge pull request** → **Confirm merge**  
3. Optional: **Delete branch** on GitHub (keeps the branch list tidy)

**What this does:** Your feature commits are now part of GitHub’s `main`.

---

## Part 3. Pull the latest main back to your laptop

Your laptop’s `main` is still old until you pull.

### Step 10 — Update local main
```bash
git checkout main
git pull origin main
```

**What this does:** Brings the merged PR (and any other new main commits) onto your machine.

### Step 11 — Optional: delete the local feature branch
```bash
git branch -d feature-login
```

**What this does:** Removes the finished local branch. Safe after it is merged.

Check:
```bash
git log --oneline --graph --decorate -10
```

You should see your merge / feature commits on `main`.

---

## Part 4. Start the next task (new branch from latest main)

### Step 12 — Always branch from updated main
```bash
git checkout main
git pull origin main
git checkout -b feature-payment
```

Edit, commit, push, open another PR — same as Part 2.

**Why pull again?** Someone else (or you) may have merged more work since your last pull. Starting from old `main` creates avoidable conflicts later.

---

## Part 5. Main changed again while you are still on a feature branch

This is the common case:

1. You created `feature-payment` from `main`  
2. Meanwhile another PR was merged into GitHub `main`  
3. Your branch is now **behind** latest `main`

### How you notice
```bash
git checkout main
git pull origin main
git checkout feature-payment
git log --oneline --graph --all --decorate -15
```

`main` has commits your feature branch does not.

### What to do (recommended: update your branch with merge)

```bash
git checkout feature-payment
git fetch origin
git merge origin/main
```

Or, after you already pulled main locally:
```bash
git checkout main
git pull origin main
git checkout feature-payment
git merge main
```

**What this does:** Brings latest `main` **into your feature branch**. Fix conflicts if Git stops, then:

```bash
git add .
git commit -m "Merge latest main into feature-payment"
git push
```

Then open/update the PR. The PR now includes your work **plus** the latest main.

### Alternate: rebase (linear history)
```bash
git checkout feature-payment
git fetch origin
git rebase origin/main
```

If conflicts: fix → `git add <file>` → `git rebase --continue`  
To cancel: `git rebase --abort`

After a rebase you already pushed once:
```bash
git push --force-with-lease
```

**Only** if you are sure nobody else is using that branch. Prefer **merge** if you are unsure.

### Do not do this
- Do not keep committing on an old feature and merge the PR without updating — you risk big conflicts and missing others’ fixes  
- Do not work directly on `main` for features if your team uses PRs  

---

## Short checklist you can reuse every time

**Start work**
1. `git checkout main`  
2. `git pull origin main`  
3. `git checkout -b feature-name`  
4. Edit → `git add` → `git commit`  
5. `git push -u origin feature-name`  
6. Open PR on GitHub → merge  

**After PR is merged**
7. `git checkout main`  
8. `git pull origin main`  
9. `git branch -d feature-name`  

**If main moved while your PR/branch is still open**
10. Update feature: `git merge origin/main` (or rebase)  
11. Fix conflicts if any → commit → `git push`  
12. Then merge the PR  

---

## Tiny command cheat sheet

| Goal | Commands |
|------|----------|
| Copy repo to laptop | `git clone <url>` |
| Update local main | `git checkout main` then `git pull origin main` |
| New feature branch | `git checkout -b feature-name` |
| Save work | `git add .` then `git commit -m "msg"` |
| Upload branch | `git push -u origin feature-name` |
| Request merge | Create **Pull Request** on GitHub |
| Bring main into my branch | `git merge origin/main` (while on feature) |
| See history | `git log --oneline --graph --all --decorate` |

---

## First-time GitHub tip (auth)

If `git push` or `git clone` asks for a password, GitHub no longer accepts account passwords for Git. Use either:
- **HTTPS + Personal Access Token (PAT)** as the password, or  
- **SSH keys** and an `git@github.com:...` clone URL  

Ask your trainer which method your campus uses.
