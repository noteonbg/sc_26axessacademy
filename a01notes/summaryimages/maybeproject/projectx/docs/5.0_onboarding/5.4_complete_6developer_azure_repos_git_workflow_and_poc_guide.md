# 1.0 Complete 6-Developer Azure Repos Git Workflow, POC & Integration Guide

## 2.0 Step 1: One-Time Azure Repos Repository Cloning & Setup
- **2.1** Azure Repos Repository URL: `https://dev.azure.com/{Organization}/{Project}/_git/projectx-ui`
- **2.2** Personal Access Token (PAT) Authentication: Use your Azure DevOps Personal Access Token (PAT) as your password during git operations or embed it in the URL (`https://{PAT_TOKEN}@dev.azure.com/...`).
- **2.3** SSL Verification Bypass Command for Corporate Firewalls:
  - Execute command: `git -c http.sslVerify=false clone https://dev.azure.com/AxessAcademy/ProjectX/_git/projectx-ui`
  - Or with PAT embedded: `git -c http.sslVerify=false clone https://{YOUR_PAT_TOKEN}@dev.azure.com/AxessAcademy/ProjectX/_git/projectx-ui`
- **2.4** CRITICAL RULE FOR NEW DEVELOPERS (ONE-TIME STEP ONLY):
  - Cloning is performed **EXACTLY ONCE** on Day 1 to set up your local workspace folder.
  - Do NOT execute `git clone` again when creating feature branches or pulling updates.
- **2.5** Step 2 (Navigate to Repository Directory):
  - Execute command: `cd projectx-ui`
- **2.6** Step 3 (Verify Remote Integration Branch):
  - Execute command: `git branch -a` to confirm `origin/develop` and `origin/main` exist.

## 3.0 Step 2: Cycle 1 - Building Static Components & Raising Azure Repos PRs (Dev 1 to Dev 5)
- **3.1** Instructions for Developer 1 (Feature 1 User Auth):
  - Step 1 (Checkout & Create Branch): `git checkout develop` && `git pull origin develop` && `git checkout -b feature/ui-dev1-auth-v1`
  - Step 2 (Write Static Component): Create `src/features/feature1_auth/components/AuthView.js`:
    ```javascript
    import React from 'react';
    export function AuthView() {
      return <div>🔐 Feature 1: User Auth Module (Dev 1 - Version 1)</div>;
    }
    ```
  - Step 3 (Export Public Barrier): Create `src/features/feature1_auth/index.js`: `export { AuthView } from './components/AuthView';`
  - Step 4 (Commit & Push to Azure Repos): `git add src/features/feature1_auth/` && `git commit -m "feat(auth): add AuthView static component v1"` && `git push origin feature/ui-dev1-auth-v1`
  - Step 5 (Raise PR in Azure DevOps Portal): Open Azure DevOps ➔ `Repos` ➔ `Pull Requests` ➔ `New Pull Request` ➔ Target `develop` branch ➔ Assign **Developer 6** as reviewer.
- **3.2** Instructions for Developer 2 (Feature 2 Product Catalog):
  - Step 1: `git checkout develop` && `git pull origin develop` && `git checkout -b feature/ui-dev2-catalog-v1`
  - Step 2: Create `src/features/feature2_catalog/components/CatalogView.js` rendering `<div>🛍️ Feature 2: Product Catalog Module (Dev 2 - Version 1)</div>`.
  - Step 3: Create `src/features/feature2_catalog/index.js` exporting `CatalogView`.
  - Step 4: `git add src/features/feature2_catalog/` && `git commit -m "feat(catalog): add CatalogView v1"` && `git push origin feature/ui-dev2-catalog-v1`
  - Step 5: Raise PR in Azure DevOps Portal targeting `develop` branch and assign Developer 6.
- **3.3** Instructions for Developer 3 (Feature 3 Shopping Cart):
  - Step 1: `git checkout develop` && `git pull origin develop` && `git checkout -b feature/ui-dev3-cart-v1`
  - Step 2: Create `src/features/feature3_cart/components/CartView.js` rendering `<div>🛒 Feature 3: Shopping Cart Module (Dev 3 - Version 1)</div>`.
  - Step 3: Create `src/features/feature3_cart/index.js` exporting `CartView`.
  - Step 4: `git add src/features/feature3_cart/` && `git commit -m "feat(cart): add CartView v1"` && `git push origin feature/ui-dev3-cart-v1`
  - Step 5: Raise PR in Azure DevOps Portal targeting `develop` branch and assign Developer 6.
- **3.4** Instructions for Developer 4 (Feature 4 Payment Checkout):
  - Step 1: `git checkout develop` && `git pull origin develop` && `git checkout -b feature/ui-dev4-checkout-v1`
  - Step 2: Create `src/features/feature4_checkout/components/CheckoutView.js` rendering `<div>💳 Feature 4: Payment Checkout Module (Dev 4 - Version 1)</div>`.
  - Step 3: Create `src/features/feature4_checkout/index.js` exporting `CheckoutView`.
  - Step 4: `git add src/features/feature4_checkout/` && `git commit -m "feat(checkout): add CheckoutView v1"` && `git push origin feature/ui-dev4-checkout-v1`
  - Step 5: Raise PR in Azure DevOps Portal targeting `develop` branch and assign Developer 6.
- **3.5** Instructions for Developer 5 (Feature 5 User Profile):
  - Step 1: `git checkout develop` && `git pull origin develop` && `git checkout -b feature/ui-dev5-profile-v1`
  - Step 2: Create `src/features/feature5_user_profile/components/ProfileView.js` rendering `<div>👤 Feature 5: User Profile Module (Dev 5 - Version 1)</div>`.
  - Step 3: Create `src/features/feature5_user_profile/index.js` exporting `ProfileView`.
  - Step 4: `git add src/features/feature5_user_profile/` && `git commit -m "feat(profile): add ProfileView v1"` && `git push origin feature/ui-dev5-profile-v1`
  - Step 5: Raise PR in Azure DevOps Portal targeting `develop` branch and assign Developer 6.

## 4.0 Step 3: Developer 6 (Lead Integrator) PR Review & Merge Protocol
- **4.1** Step 1 (Deliver Feature 6 Component):
  - Create branch `feature/ui-dev6-analytics-v1` from `develop`.
  - Create `src/features/feature6_analytics/components/AnalyticsView.js` rendering `<div>📊 Feature 6: Admin Analytics Module (Dev 6 - Version 1)</div>`.
  - Create `src/features/feature6_analytics/index.js` exporting `AnalyticsView`.
  - Commit and push: `git push origin feature/ui-dev6-analytics-v1`.
- **4.2** Step 2 (Review Incoming Azure Repos PRs):
  - Open Azure DevOps Portal ➔ `Repos` ➔ `Pull Requests`.
  - Audit PRs 1 through 5 to verify zero file collisions outside designated feature folders.
- **4.3** Step 3 (Approve & Complete Merges in Azure Repos):
  - For each PR, click **Approve** ➔ Click **Complete** ➔ Select Merge Strategy ➔ Click **Complete merge**.
- **4.4** Step 4 (Register All 6 Components in `src/App.js`):
  - Switch to `develop` branch locally: `git checkout develop` && `git pull origin develop`.
  - Update `src/App.js` to render all 6 components cleanly imported through public barriers (`AuthView`, `CatalogView`, `CartView`, `CheckoutView`, `ProfileView`, `AnalyticsView`).
  - Commit and push: `git commit -m "chore: integrate all 6 feature components into App.js"` && `git push origin develop`.

## 5.0 Step 4: Cycle 2 - Pulling Latest Integrated Branch, Adding 2nd Change & Pushing
- **5.1** Step 1 (Switch to `develop` Branch Locally):
  - Execute command: `git checkout develop`
- **5.2** Step 2 (Pull Latest Integrated Codebase from Azure Repos):
  - Execute command: `git pull origin develop`
  - *Result*: Your local `develop` branch now contains all components merged by Developers 1 through 6 from Azure Repos!
- **5.3** Step 3 (Create 2nd Iteration Feature Branch):
  - Execute command: `git checkout -b feature/ui-dev1-auth-v2`  (Replace `dev1` with your developer number)
- **5.4** Step 4 (Make Second Component Modification):
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
- **5.5** Step 5 (Commit, Push & Open 2nd PR in Azure Repos):
  - `git add src/features/feature1_auth/`
  - `git commit -m "feat(auth): update AuthView component to version 2"`
  - `git push origin feature/ui-dev1-auth-v2`
  - Open 2nd Pull Request in Azure Repos targeting `develop` branch and assign Developer 6.
- **5.6** Step 6 (Developer 6 Final Approval & Merge):
  - Developer 6 approves and completes Cycle 2 PR merges into `develop`.

## 6.0 Step 5: Midway Local Feature Branch Rebase Protocol (When Remote `develop` Changes Mid-Task)
- **6.1** Scenario: Developer 1 is working locally on `feature/ui-dev1-auth-v1` when Developer 2 merges new code into remote `develop`.
- **6.2** Step 1 (Commit Active Local Work on Feature Branch):
  - `git add .` && `git commit -m "wip(auth): save local progress"`
- **6.3** Step 2 (Fetch Latest Remote Updates from Azure Repos):
  - `git fetch origin`
- **6.4** Step 3 (Rebase Local Feature Branch onto `origin/develop`):
  - `git rebase origin/develop`
  - *Result*: Git unhooks Developer 1's local commits, pulls Developer 2's new code underneath, and re-applies Developer 1's commits cleanly on top!
- **6.5** Step 4 (Test & Push Clean Branch to Azure Repos):
  - Run `npm start` locally to verify zero build errors.
  - Push updated branch: `git push origin feature/ui-dev1-auth-v1` (Use `--force-with-lease` if branch was previously pushed).

## 7.0 Step 6: Syncing `develop` with `main` UI Branch & Release Protocol
- **7.1** Scenario A: Releasing Integrated Sprint Code from `develop` into `main`:
  - Developer 6 opens PR in Azure Repos: Source `develop` ➔ Target `main`.
  - Upon approval, complete merge in Azure Repos with release tag (e.g. `release/v1.0.0`).
- **7.2** Scenario B: Syncing `develop` Branch when `main` Receives Upstream Hotfixes:
  - Switch to local `develop`: `git checkout develop`
  - Fetch latest remote changes: `git fetch origin`
  - Merge latest `main` into `develop`: `git merge origin/main`
  - Push updated `develop` back to Azure Repos: `git push origin develop`
