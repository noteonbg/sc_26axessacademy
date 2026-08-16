# UI-First & API Contract-Driven Development Strategy

This guide provides a step-by-step blueprint for teams planning to **build the Frontend UI first** with mock API contracts, and **then build the Backend services** to match those contracts.

---

## 💡 Why UI-First (Contract-Driven) Development Succeeds

When 5 developers (Dev A, B, C, D, E) build UI first:
1. **Zero Blocker / 100% Speed**: Frontend developers don't wait for backend servers, databases, or cloud infrastructure to exist.
2. **Instant UX Feedback**: Stakeholders and users can test the visual workflows early before backend code is written.
3. **API Integrity**: Prevents backend engineers from creating API endpoints that don't fit what the UI actually needs.

---

## 🛠️ Step-by-Step UI-First Execution Workflow

```
┌─────────────────────────────────────────────────────────┐
│ PHASE 1: Define API Contracts (JSON Spec Document)       │
│ • Agree on Endpoint URLs, Request & Response JSONs      │
└───────────────────────────┬─────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│ PHASE 2: Build React Frontend using Mock API Services    │
│ • Devs A-E build UIs using mock JSON data handlers      │
│ • Complete visual components, state, & forms            │
└───────────────────────────┬─────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│ PHASE 3: Build Spring Boot / Node Backend Services      │
│ • Build DB entities, services, & REST Controllers        │
│ • Return exact JSON shapes defined in Phase 1 Contract  │
└───────────────────────────┬─────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│ PHASE 4: Seamless Switch & Integration                 │
│ • Flip React configuration from Mock to Real Backend API │
└─────────────────────────────────────────────────────────┘
```

---

## 📋 Phase 1: Defining the API Contract (The Agreement)

Before writing UI or backend code, the team creates a shared file: `docs/api-contracts.json` or an OpenAPI/Swagger spec.

### Example Contract for Feature 2 (Product Catalog - Dev B):
- **Endpoint**: `GET /api/v1/products`
- **Response JSON Contract (Status 200 OK)**:
```json
{
  "status": "SUCCESS",
  "data": [
    {
      "id": 101,
      "name": "Industrial CNC Lathe",
      "model": "CNC-800",
      "price": 45000.00,
      "category": "MACHINERY"
    }
  ]
}
```

---

## ⚛️ Phase 2: Building React UI with Decoupled API Services

To make switching from Mock Data to the Real Backend effortless later, **never hardcode mock data inside React UI components!**

### Rule: Isolate API Calls in Service Modules (`services/`)

Each feature folder must have an API service file that handles fetching:

```
client/src/features/catalog/
├── components/             # ProductCard.jsx, SearchBar.jsx
├── mockData/               # productsMockData.js (Sample JSON)
├── services/               # catalogApi.js  <-- DECUPLED API SERVICE
└── pages/                  # CatalogPage.jsx
```

### How `catalogApi.js` is structured:

```javascript
// catalogApi.js
import mockProducts from '../mockData/productsMockData.js';

// Global configuration flag
const USE_MOCK = true; 
const BASE_URL = 'http://localhost:8080/api/v1';

export async function fetchProducts() {
    if (USE_MOCK) {
        // Return instant local mock data during UI-First Phase
        return new Promise(resolve => setTimeout(() => resolve(mockProducts), 300));
    } else {
        // Connect to real Spring Boot backend during Integration Phase
        const response = await fetch(`${BASE_URL}/products`);
        return await response.json();
    }
}
```

---

## ⚙️ Phase 3: Building Backend (Spring Boot / Node) to Match Contract

Backend developers (Devs A-E) build Spring Boot services matching the exact contract:

1. **Entities & Repositories**: Create JPA entities (`Product.java`) and repositories.
2. **DTOs (Data Transfer Objects)**: Create `ProductResponseDTO.java` to match the exact JSON keys agreed upon in Phase 1.
3. **Controllers**: Annotate REST endpoints (`@GetMapping("/api/v1/products")`).

Because the JSON keys were locked in Phase 1, the backend response will fit the React UI perfectly!

---

## 🔌 Phase 4: Seamless Integration & Switching

When the Spring Boot backend is deployed:

1. In React, update the global environment configuration:
   - Change `USE_MOCK = false` in your API client setup.
   - Point `BASE_URL = 'http://localhost:8080/api/v1'`.
2. **Result**: Zero React UI component code needs to be modified! The entire application smoothly switches from mock data to live server databases.

---

## 📊 Summary Checklist for Team Workflow

| Project Phase | Primary Focus | Key Deliverable |
| :--- | :--- | :--- |
| **Phase 1: Contract** | Joint Team Alignment | Shared `docs/api-contracts.json` specification |
| **Phase 2: UI-First** | Frontend Devs A to E | Fully interactive React UI powered by `mockData/` and `services/` |
| **Phase 3: Backend** | Backend Devs A to E | Spring Boot Controllers & DB entities returning contract DTOs |
| **Phase 4: Flip** | Full Stack Integration | Set `USE_MOCK = false` and verify end-to-end integration |
