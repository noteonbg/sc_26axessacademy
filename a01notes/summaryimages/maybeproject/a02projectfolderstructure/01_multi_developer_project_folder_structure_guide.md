# Multi-Developer Project Folder Structure & Architectural Reference Manual

This document is the **definitive, unambiguous architectural blueprint** for planning, structuring, and maintaining a software codebase when multiple developers (Developer A, B, C, D, and E) work concurrently on a brand-new project.

Every single folder, its exact contents, file naming conventions, developer ownership, and strict anti-pattern rules are explained in detail below so **nobody has to assume anything**.

---

## 🏛️ Executive Summary: The Feature-Based Modular Architecture

### Why Layer-Based Structures Fail in Teams:
In traditional project structures (`/controllers`, `/models`, `/views`), Developer A, B, C, D, and E all modify the exact same directories every day. This creates massive **Git Merge Conflicts**, accidental code overwrites, and blocked releases.

### The Enterprise Solution:
Group code into **Vertical Feature Slices** (`src/features/<feature_name>/`).
Each developer is assigned ownership of their feature domain module. Developer A works inside `features/auth/`, Developer B works inside `features/catalog/`, etc.

---

## 📌 Team Developer Ownership Matrix

| Developer | Feature Assignment | Feature Folder Name | Domain Responsibility |
| :--- | :--- | :--- | :--- |
| **Developer A** | **Feature 1** | `auth` | User Authentication, Login, Registration, JWT Tokens, User Profile |
| **Developer B** | **Feature 2** | `catalog` | Product Catalog, Search, Categories, Filters, Product Details |
| **Developer C** | **Feature 3** | `cart` | Shopping Cart, Quantity Updates, Saved Items, Order Summary |
| **Developer D** | **Feature 4** | `payments` | Payment Gateway Integration, Checkout, Invoicing, Receipts |
| **Developer E** | **Feature 5** | `analytics` | Admin Dashboard, Sales Reports, Revenue Metrics, System Logs |
| **Tech Lead** | **Core / Shared** | `common/` & `config/` | Shared Atomic UI, Security Configs, DB Setup, Base Utils |

---

## 📂 PART 1: ROOT DIRECTORY STRUCTURE EXPLANATION

```
my-enterprise-project/
├── .github/                        # CI/CD Workflows & GitHub Automation
├── docs/                           # Architecture Docs & API Specifications
├── client/                         # Frontend Application (React Single-Page App)
├── server/                         # Backend Application (Spring Boot / Node API)
├── docker-compose.yml              # Local Infrastructure Container Setup
├── .gitignore                      # Version Control Exclusions
└── README.md                       # Developer Onboarding & Build Instructions
```

### 1. `.github/`
- **Purpose**: Contains automated continuous integration scripts and GitHub workflow definitions.
- **What Belongs Here**: Automated build pipelines (`ci-build.yml`), code linting checks, pull request templates (`pull_request_template.md`).
- **Owner**: Tech Lead / DevOps.

### 2. `docs/`
- **Purpose**: The central documentation repository for system design and API agreements.
- **What Belongs Here**: OpenAPI / Swagger YAML files (`api-spec.yaml`), database ERD diagrams, architecture decision records (`ADR-001-JWT-Auth.md`).
- **Rule**: Every developer must update `docs/api-spec.yaml` when modifying API contracts.

### 3. `client/`
- **Purpose**: The entire frontend web application codebase (React / Next.js).
- **What Belongs Here**: All user interface components, styles, state management, and client-side routing.

### 4. `server/`
- **Purpose**: The entire backend REST API server codebase (Spring Boot / Node.js).
- **What Belongs Here**: Controllers, business logic services, database repositories, JPA entities, and security filters.

### 5. `docker-compose.yml`
- **Purpose**: Enables any developer to launch the local database (PostgreSQL), cache (Redis), and mock services with one single terminal command (`docker compose up`).

---

## 🎨 PART 2: FRONTEND DETAILED FOLDER BREAKDOWN (`client/src/`)

```
client/src/
├── assets/                         # Global Images, Fonts, Logos
├── common/                         # Shared Cross-Feature UI & Utilities (Tech Lead)
│   ├── components/                 # Atomic Shared UI (Button, Modal, Table, Input)
│   ├── hooks/                      # Global Custom React Hooks (useFetch, useDebounce)
│   ├── utils/                      # Helper Functions (formatCurrency, formatDate)
│   └── styles/                     # Global Design Tokens & CSS Variables
├── features/                       # Modular Feature Slices (Devs A-E)
│   ├── auth/                       # [Dev A] Feature 1: Auth & Profile
│   ├── catalog/                    # [Dev B] Feature 2: Product Catalog
│   ├── cart/                       # [Dev C] Feature 3: Shopping Cart
│   ├── payments/                   # [Dev D] Feature 4: Payment Gateway
│   └── analytics/                  # [Dev E] Feature 5: Admin Analytics
├── routes/                         # Client-Side Feature Router
├── App.jsx                         # Root Component Wrapper
└── main.jsx                        # React DOM Entrypoint
```

---

### Detailed Breakdown of Frontend Folders:

#### 📁 `src/assets/`
- **Purpose**: Stores static media files used across the application.
- **Exact File Examples**: `logo.svg`, `company-banner.png`, `inter-font.woff2`.
- **Rule**: Do NOT store feature-specific mock data here (put mock data inside `features/<feature>/mockData/`).

#### 📁 `src/common/components/`
- **Purpose**: Reusable, generic UI components shared by **two or more features**.
- **Exact File Examples**:
  - `Button.jsx` (Primary/Secondary button component used everywhere)
  - `Modal.jsx` (Generic popup dialog overlay)
  - `Input.jsx` (Standardized styled form text input)
  - `Table.jsx` (Reusable data grid table)
  - `Spinner.jsx` (Loading spinner indicator)
- **Rule**: Common components MUST NOT contain feature-specific business logic or API calls!

#### 📁 `src/common/hooks/`
- **Purpose**: Custom React hooks usable by any feature.
- **Exact File Examples**: `useFetch.js`, `useDebounce.js`, `useWindowSize.js`.

#### 📁 `src/common/utils/`
- **Purpose**: Pure JavaScript helper functions with zero UI or React dependencies.
- **Exact File Examples**:
  - `formatCurrency.js` (Converts number `50000` to `₹50,000.00`)
  - `formatDate.js` (Converts ISO date to `DD/MM/YYYY`)
  - `validator.js` (Regex helper functions for email/phone validation)

#### 📁 `src/common/styles/`
- **Purpose**: Global styling resets and design tokens.
- **Exact File Examples**: `global.css`, `theme.css`, `variables.css`.

---

### 🧱 Feature Modules Breakdown (`src/features/`)

Each feature module under `src/features/` follows a standardized 4-folder sub-structure:

```
src/features/<feature_name>/
├── components/                     # Feature-specific sub-components
├── mockData/                       # Local JSON API Contract mock files
├── services/                       # Decoupled API Service Module (Axios / Fetch)
└── pages/                          # Main Feature Page Containers
```

#### 📁 `src/features/auth/` (Assigned to Developer A)
- **`components/LoginForm.jsx`**: Form component for user login credentials.
- **`components/RegisterModal.jsx`**: Registration modal popup.
- **`components/UserAvatar.jsx`**: Profile picture badge in header.
- **`mockData/authMockData.js`**: Mock user object `{ id: 1, name: "Marcus", token: "eyJ..." }`.
- **`services/authApi.js`**: Handles login API requests with `USE_MOCK` toggle.
- **`pages/LoginPage.jsx`**: Page container for `/login` route.
- **`pages/ProfilePage.jsx`**: Page container for `/profile` route.

#### 📁 `src/features/catalog/` (Assigned to Developer B)
- **`components/ProductCard.jsx`**: Individual product grid card.
- **`components/SearchBar.jsx`**: Search input bar for catalog.
- **`components/CategoryFilter.jsx`**: Filter sidebar checkboxes.
- **`mockData/catalogMockData.js`**: Array of 10 mock product items.
- **`services/catalogApi.js`**: API client for fetching products (`fetchProducts()`).
- **`pages/CatalogPage.jsx`**: Main catalog browsing page.
- **`pages/ProductDetailPage.jsx`**: Individual product detail view page.

#### 📁 `src/features/cart/` (Assigned to Developer C)
- **`components/CartDrawer.jsx`**: Slide-out cart container.
- **`components/CartItemRow.jsx`**: Quantity increment/decrement item row.
- **`components/OrderSummary.jsx`**: Total price and tax breakdown box.
- **`mockData/cartMockData.js`**: Mock active cart items.
- **`services/cartApi.js`**: API client for cart CRUD operations.
- **`pages/CartPage.jsx`**: Full shopping cart page.

#### 📁 `src/features/payments/` (Assigned to Developer D)
- **`components/CreditCardForm.jsx`**: Credit card / UPI payment input form.
- **`components/ReceiptViewer.jsx`**: Printable payment confirmation receipt.
- **`mockData/paymentMockData.js`**: Mock transaction payment status.
- **`services/paymentApi.js`**: API client connecting to Stripe / Bank payment gateway.
- **`pages/CheckoutPage.jsx`**: Payment checkout page.

#### 📁 `src/features/analytics/` (Assigned to Developer E)
- **`components/SalesChart.jsx`**: Bar chart showing monthly sales metrics.
- **`components/RevenueSummaryCard.jsx`**: KPI summary card.
- **`mockData/analyticsMockData.js`**: Mock revenue metrics JSON.
- **`services/analyticsApi.js`**: API client fetching admin metrics.
- **`pages/AdminDashboardPage.jsx`**: Admin dashboard page container.

---

#### 📁 `src/routes/`
- **Purpose**: Defines application routing and maps URL paths to feature pages.
- **Exact File Examples**:
  - `AppRouter.jsx` (Defines `/login` -> `LoginPage`, `/catalog` -> `CatalogPage`, etc.)
  - `PrivateRoute.jsx` (Guards admin/authenticated routes)

---

## ⚙️ PART 3: BACKEND DETAILED FOLDER BREAKDOWN (`server/src/main/java/com/company/project/`)

```
server/src/main/java/com/company/project/
├── common/                         # SHARED BACKEND CONCERNS (Tech Lead)
│   ├── config/                     # Security, CORS, JPA, Redis Configs
│   ├── exception/                  # Global Exception Handling & Error DTOs
│   └── utils/                      # Helper Functions (DateTime, SecurityContext)
├── features/                       # FEATURE DOMAIN MODULES (Devs A-E)
│   ├── auth/                       # [Dev A] Feature 1: Auth & Users
│   ├── catalog/                    # [Dev B] Feature 2: Product Catalog
│   ├── cart/                       # [Dev C] Feature 3: Cart Management
│   ├── payments/                   # [Dev D] Feature 4: Payment Gateway
│   └── analytics/                  # [Dev E] Feature 5: Admin Analytics
└── Application.java                # Spring Boot Main Launcher
```

---

### Detailed Breakdown of Backend Feature Sub-Packages:

Each backend feature module (`server/.../features/<feature>/`) contains 5 explicit layers:

```
server/.../features/<feature_name>/
├── controller/                     # REST API Endpoints (HTTP Layer)
├── service/                        # Business Logic & Validation Rules
├── repository/                     # Database Queries & JPA Repositories
├── entity/                         # Database Table Schema Entities
└── dto/                            # API Contract Data Transfer Objects
```

#### 📁 `server/.../features/auth/` (Assigned to Developer A)
- **`controller/AuthController.java`**: Maps `@PostMapping("/api/v1/auth/login")` and `@PostMapping("/api/v1/auth/register")`.
- **`service/AuthService.java`** & **`AuthServiceImpl.java`**: Password hashing, JWT token generation, user validation.
- **`repository/UserRepository.java`**: Extends `JpaRepository<User, Long>` (`findByUsername()`).
- **`entity/User.java`**: `@Entity` mapping database table `users` (id, username, password_hash, role).
- **`dto/LoginRequestDTO.java`** & **`AuthResponseDTO.java`**: Contract objects matching Phase 1 API spec.

#### 📁 `server/.../features/catalog/` (Assigned to Developer B)
- **`controller/ProductController.java`**: Maps `@GetMapping("/api/v1/products")` and `@GetMapping("/api/v1/products/{id}")`.
- **`service/ProductService.java`**: Product searching, inventory checking.
- **`repository/ProductRepository.java`**: Database queries for products.
- **`entity/Product.java`**: `@Entity` mapping database table `products`.
- **`dto/ProductResponseDTO.java`**: JSON contract DTO sent back to React UI.

#### 📁 `server/.../features/cart/` (Assigned to Developer C)
- **`controller/CartController.java`**: Maps `@PostMapping("/api/v1/cart/items")` and `@DeleteMapping("/api/v1/cart/items/{id}")`.
- **`service/CartService.java`**: Cart total calculation, item quantity rules.
- **`repository/CartRepository.java`**: Database queries for active carts.
- **`entity/CartItem.java`**: `@Entity` mapping table `cart_items`.

#### 📁 `server/.../features/payments/` (Assigned to Developer D)
- **`controller/PaymentController.java`**: Maps `@PostMapping("/api/v1/payments/charge")`.
- **`service/PaymentService.java`**: Integrates payment gateway, records transaction.
- **`repository/TransactionRepository.java`**: Database access for audit history.
- **`entity/Transaction.java`**: `@Entity` mapping table `payment_transactions`.

#### 📁 `server/.../features/analytics/` (Assigned to Developer E)
- **`controller/AnalyticsController.java`**: Maps `@GetMapping("/api/v1/analytics/sales")`.
- **`service/AnalyticsService.java`**: Aggregates sales metrics from database.
- **`dto/RevenueSummaryDTO.java`**: DTO containing sales numbers and monthly growth percentages.

---

## 🚫 STRICT ANTI-PATTERN RULES (WHAT NOT TO DO)

1. ⛔ **No Direct Inter-Feature Import Chaos**:
   - Developer C (`cart`) must NOT import private internal components from Developer A (`auth`).
   - If Dev C needs a component created by Dev A (e.g. `UserAvatar.jsx`), it must be moved to `common/components/` after Tech Lead review.
2. ⛔ **No Raw API Calls inside UI Components**:
   - React UI components (`ProductCard.jsx`) must NEVER call `fetch()` or `axios()` directly inside `useEffect`.
   - Always call the feature's API service (`catalogApi.fetchProducts()`).
3. ⛔ **No Raw Database Logic in Backend Controllers**:
   - Controllers only receive HTTP requests and call Service methods. Controllers must NEVER write raw SQL queries or direct repository calls.
