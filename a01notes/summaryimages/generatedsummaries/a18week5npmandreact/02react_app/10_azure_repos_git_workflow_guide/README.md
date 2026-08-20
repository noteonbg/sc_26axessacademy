# 1.0 Quick Terminal Command Cheat Sheet (Commands Only)

## 1.1 Category 1: One-Time Repository Cloning & Setup
- **1.1.1** `git -c http.sslVerify=false clone https://{PAT_TOKEN}@dev.azure.com/AxessAcademy/ProjectX/_git/projectx-ui`
- **1.1.2** `cd projectx-ui`

## 1.2 Category 2: Fetching & Checking Out Pre-Created Remote Feature Branch (Assigned by Repo Manager)
- **1.2.1** `git fetch origin`
- **1.2.2** `git checkout feature/ui-dev1-auth`
- **1.2.3** `git pull origin feature/ui-dev1-auth`

## 1.3 Category 3: Midway Sync When Active Feature Branch Has Changed & Remote "common" Branch (`develop`) Has Changed Due To PR
- **1.3.1** `git add .`
- **1.3.2** `git commit -m "wip: save local feature progress"`
- **1.3.3** `git fetch origin`
- **1.3.4** `git rebase origin/develop`
- **1.3.5** `npm start`
- **1.3.6** `git push origin feature/ui-dev1-auth --force-with-lease`

---

# 2.0 Complete 6-Developer Azure Repos Git Workflow, POC & Integration Guide

## 3.0 Step 1: One-Time Azure Repos Repository Cloning & Pre-Created Branch Setup
- **3.1** Azure Repos Repository URL: `https://dev.azure.com/{Organization}/{Project}/_git/projectx-ui`
- **3.2** Personal Access Token (PAT) Authentication: Use your Azure DevOps Personal Access Token (PAT) as your password during git operations or embed it in the URL (`https://{PAT_TOKEN}@dev.azure.com/...`).
- **3.3** SSL Verification Bypass Command for Corporate Firewalls:
  - Execute command: `git -c http.sslVerify=false clone https://dev.azure.com/AxessAcademy/ProjectX/_git/projectx-ui`
  - Or with PAT embedded: `git -c http.sslVerify=false clone https://{YOUR_PAT_TOKEN}@dev.azure.com/AxessAcademy/ProjectX/_git/projectx-ui`
- **3.4** CRITICAL RULE: PRE-CREATED REMOTE FEATURE BRANCHES (ASSIGNED BY REPO MANAGER):
  - Developers **do NOT create new local branches**.
  - Repo Manager (Developer 6) pre-creates feature branches in Azure Repos: `feature/ui-dev1-auth`, `feature/ui-dev2-catalog`, `feature/ui-dev3-cart`, `feature/ui-dev4-checkout`, `feature/ui-dev5-profile`, `feature/ui-dev6-analytics`.
  - Developers fetch and check out their assigned remote branch directly from Azure Repos.
- **3.5** Step 2 (Navigate to Repository Directory):
  - Execute command: `cd projectx-ui`
- **3.6** Step 3 (Fetch & Checkout Assigned Remote Feature Branch):
  - Execute command: `git fetch origin`
  - Execute command: `git checkout feature/ui-dev1-auth`  (Replace `dev1` with your assigned feature branch)

## 4.0 Step 2: Cycle 1 - Building Static Components & Raising Azure Repos PRs (Dev 1 to Dev 5)
- **4.1** Instructions for Developer 1 (Feature 1 User Auth):
  - Step 1 (Checkout Pre-Assigned Branch): `git fetch origin` && `git checkout feature/ui-dev1-auth`
  - Step 2 (Write Static Component): Create `src/features/feature1_auth/components/AuthView.js`:
    ```javascript
    import React from 'react';
    export function AuthView() {
      return <div>🔐 Feature 1: User Auth Module (Dev 1 - Version 1)</div>;
    }
    ```
  - Step 3 (Export Public Barrier): Create `src/features/feature1_auth/index.js`: `export { AuthView } from './components/AuthView';`
  - Step 4 (Commit & Push to Azure Repos): `git add src/features/feature1_auth/` && `git commit -m "feat(auth): add AuthView static component v1"` && `git push origin feature/ui-dev1-auth`
  - Step 5 (Raise PR in Azure DevOps Portal): Open Azure DevOps ➔ `Repos` ➔ `Pull Requests` ➔ `New Pull Request` ➔ Source: `feature/ui-dev1-auth` ➔ Target `develop` branch ➔ Assign **Developer 6** (Repo Manager) as reviewer.
- **4.2** Instructions for Developer 2 (Feature 2 Product Catalog):
  - Step 1: `git fetch origin` && `git checkout feature/ui-dev2-catalog`
  - Step 2: Create `src/features/feature2_catalog/components/CatalogView.js` rendering `<div>🛍️ Feature 2: Product Catalog Module (Dev 2 - Version 1)</div>`.
  - Step 3: Create `src/features/feature2_catalog/index.js` exporting `CatalogView`.
  - Step 4: `git add src/features/feature2_catalog/` && `git commit -m "feat(catalog): add CatalogView v1"` && `git push origin feature/ui-dev2-catalog`
  - Step 5: Raise PR in Azure DevOps Portal targeting `develop` branch and assign Developer 6.
- **4.3** Instructions for Developer 3 (Feature 3 Shopping Cart):
  - Step 1: `git fetch origin` && `git checkout feature/ui-dev3-cart`
  - Step 2: Create `src/features/feature3_cart/components/CartView.js` rendering `<div>🛒 Feature 3: Shopping Cart Module (Dev 3 - Version 1)</div>`.
  - Step 3: Create `src/features/feature3_cart/index.js` exporting `CartView`.
  - Step 4: `git add src/features/feature3_cart/` && `git commit -m "feat(cart): add CartView v1"` && `git push origin feature/ui-dev3-cart`
  - Step 5: Raise PR in Azure DevOps Portal targeting `develop` branch and assign Developer 6.
- **4.4** Instructions for Developer 4 (Feature 4 Payment Checkout):
  - Step 1: `git fetch origin` && `git checkout feature/ui-dev4-checkout`
  - Step 2: Create `src/features/feature4_checkout/components/CheckoutView.js` rendering `<div>💳 Feature 4: Payment Checkout Module (Dev 4 - Version 1)</div>`.
  - Step 3: Create `src/features/feature4_checkout/index.js` exporting `CheckoutView`.
  - Step 4: `git add src/features/feature4_checkout/` && `git commit -m "feat(checkout): add CheckoutView v1"` && `git push origin feature/ui-dev4-checkout`
  - Step 5: Raise PR in Azure DevOps Portal targeting `develop` branch and assign Developer 6.
- **4.5** Instructions for Developer 5 (Feature 5 User Profile):
  - Step 1: `git fetch origin` && `git checkout feature/ui-dev5-profile`
  - Step 2: Create `src/features/feature5_user_profile/components/ProfileView.js` rendering `<div>👤 Feature 5: User Profile Module (Dev 5 - Version 1)</div>`.
  - Step 3: Create `src/features/feature5_user_profile/index.js` exporting `ProfileView`.
  - Step 4: `git add src/features/feature5_user_profile/` && `git commit -m "feat(profile): add ProfileView v1"` && `git push origin feature/ui-dev5-profile`
  - Step 5: Raise PR in Azure DevOps Portal targeting `develop` branch and assign Developer 6.

## 5.0 Step 3: Developer 6 (Repo Manager & Lead Integrator) PR Review & Merge Protocol
- **5.1** Step 1 (Deliver Feature 6 Component):
  - Checkout pre-created branch `feature/ui-dev6-analytics`: `git fetch origin` && `git checkout feature/ui-dev6-analytics`.
  - Create `src/features/feature6_analytics/components/AnalyticsView.js` rendering `<div>📊 Feature 6: Admin Analytics Module (Dev 6 - Version 1)</div>`.
  - Create `src/features/feature6_analytics/index.js` exporting `AnalyticsView`.
  - Commit and push: `git push origin feature/ui-dev6-analytics`.
- **5.2** Step 2 (Review Incoming Azure Repos PRs):
  - Open Azure DevOps Portal ➔ `Repos` ➔ `Pull Requests`.
  - Audit PRs 1 through 5 to verify zero file collisions outside designated feature folders.
- **5.3** Step 3 (Approve & Complete Merges in Azure Repos):
  - For each PR, click **Approve** ➔ Click **Complete** ➔ Select Merge Strategy ➔ Click **Complete merge**.
- **5.4** Step 4 (Register All 6 Components in `src/App.js`):
  - Switch to `develop` branch locally: `git checkout develop` && `git pull origin develop`.
  - Update `src/App.js` to render all 6 components cleanly imported through public barriers (`AuthView`, `CatalogView`, `CartView`, `CheckoutView`, `ProfileView`, `AnalyticsView`).
  - Commit and push: `git commit -m "chore: integrate all 6 feature components into App.js"` && `git push origin develop`.

## 6.0 Step 4: Cycle 2 - Pulling Latest Integrated Branch, Adding 2nd Change & Pushing
- **6.1** Step 1 (Switch to `develop` Branch Locally):
  - Execute command: `git checkout develop`
- **6.2** Step 2 (Pull Latest Integrated Codebase from Azure Repos):
  - Execute command: `git pull origin develop`
  - *Result*: Your local `develop` branch now contains all components merged by Developers 1 through 6 from Azure Repos!
- **6.3** Step 3 (Checkout Assigned Feature Branch):
  - Execute command: `git checkout feature/ui-dev1-auth`  (Replace `dev1` with your assigned feature branch)
- **6.4** Step 4 (Make Second Component Modification):
  - Open `src/features/feature1_auth/components/AuthView.js` and update text:
    ```javascript
    import React from 'react';
    export function AuthView() {
      return (
        <div style={{ padding: '1rem', backgroundColor: '#dbeafe' }}>
          <h3>🔐 Feature 1: User Auth Module (Dev 1 - Version 2 Updated)</h3>
          <p>Status: Synchronized with latest team code pulled from Azure Repos.</p>
        </div>
      );
    }
    ```
- **6.5** Step 5 (Commit, Push & Open 2nd PR in Azure Repos):
  - `git add src/features/feature1_auth/`
  - `git commit -m "feat(auth): update AuthView component to version 2"`
  - `git push origin feature/ui-dev1-auth`
  - Open 2nd Pull Request in Azure Repos targeting `develop` branch and assign Developer 6.
- **6.6** Step 6 (Developer 6 Final Approval & Merge):
  - Developer 6 approves and completes Cycle 2 PR merges into `develop`.

## 7.0 Step 5: Midway Local Feature Branch Rebase Protocol (When Remote `develop` Changes Mid-Task)
- **7.1** Scenario: Developer 1 is working on `feature/ui-dev1-auth` when Developer 2 merges new code into remote `develop`.
- **7.2** Step 1 (Commit Active Local Work on Feature Branch):
  - `git add .` && `git commit -m "wip(auth): save local progress"`
- **7.3** Step 2 (Fetch Latest Remote Updates from Azure Repos):
  - `git fetch origin`
- **7.4** Step 3 (Rebase Feature Branch onto `origin/develop`):
  - `git rebase origin/develop`
  - *Result*: Git unhooks Developer 1's local commits, pulls Developer 2's new code underneath, and re-applies Developer 1's commits cleanly on top!
- **7.5** Step 4 (Test & Push Updated Branch to Azure Repos):
  - Run `npm start` locally to verify zero build errors.
  - Push updated branch: `git push origin feature/ui-dev1-auth --force-with-lease`

## 8.0 Step 6: Syncing `develop` with `main` UI Branch & Release Protocol
- **8.1** Scenario A: Releasing Integrated Sprint Code from `develop` into `main`:
  - Developer 6 opens PR in Azure Repos: Source `develop` ➔ Target `main`.
  - Upon approval, complete merge in Azure Repos with release tag (e.g. `release/v1.0.0`).
- **8.2** Scenario B: Syncing `develop` Branch when `main` Receives Upstream Hotfixes:
  - Switch to local `develop`: `git checkout develop`
  - Fetch latest remote changes: `git fetch origin`
  - Merge latest `main` into `develop`: `git merge origin/main`
  - Push updated `develop` back to Azure Repos: `git push origin develop`

## 9.0 Conceptual Guide: What Do `git fetch origin` and `git rebase` Actually Do?
- **9.1** Conceptual Explanation of `git fetch origin`:
  - What it does: `git fetch origin` connects to the remote Azure Repos server and downloads all new commits, new branches, and updated tags into your local hidden cache (`.git` folder).
  - Safe & Non-Destructive: It does **NOT** modify your local working files, does **NOT** change your current branch, and does **NOT** merge anything automatically.
  - Plain English Analogy: It is like refreshing your email inbox — it downloads the latest messages (commits) to your computer memory, but doesn't force open or change any document you are currently writing.
- **9.2** Conceptual Explanation of `git rebase origin/develop`:
  - What it does: `git rebase origin/develop` rewrites the starting point (base) of your current feature branch so that it sits on top of the newest `origin/develop` branch.
  - Mechanics (3-Step Automatic Process):
    - 1. Temporarily **unhooks and saves** all your local feature commits on the side.
    - 2. **Fast-forwards** your branch base to match the latest `origin/develop` (containing all newly merged code from team members).
    - 3. **Re-applies** your saved local commits one-by-one on top of the new base.
  - Plain English Analogy: Imagine your feature commits are a stack of sticky notes placed on a desk. `git rebase` lifts your sticky notes off the desk, slides the latest stack of papers (team's new code) onto the desk underneath, and re-sticks your sticky notes cleanly right on top!
