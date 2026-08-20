# React Routing & Axios REST API Demo

A simple, participant-ready React application demonstrating **React Router v6**, **Axios REST API integration** (GET, POST, PUT, DELETE), and **HTTP Status & Error Handling** (200, 201, 204, 400, 404, 409, 500).

Built with standard **Create React App (CRA)** project folder structure.

---

## 📁 Project Folder Structure

```text
react-axios-routing-demo/
├── public/
│   └── index.html             # CRA HTML root entry point
├── src/
│   ├── pages/
│   │   ├── Home.js            # Overview & features page
│   │   ├── ItemsManager.js    # Core CRUD & Error demo page (max 3 rows)
│   │   └── About.js           # Architectural overview page
│   ├── services/
│   │   └── api.js             # Centralized Axios REST client instance
│   ├── App.js                 # Navigation bar & React Router v6 setup
│   ├── App.css                # Component styling & response badges
│   └── index.js               # React root renderer with BrowserRouter
├── server.js                  # Standalone Express mock REST server (Port 5000)
├── package.json               # CRA & Express dependencies + scripts
└── README.md                  # Step-by-step guide for participants
```

---

## 🚀 Step-by-Step Instructions to Run

### Step 1: Open Terminal in Project Folder
Open your command prompt or terminal in the project directory:
```bash
cd f:\poc\react-axios-routing-demo
```

### Step 2: Install Dependencies
Run the following command to install both client (React, React Router, Axios) and server (Express, CORS, Concurrently) dependencies:
```bash
npm install
```

### Step 3: Run the Application
You can start both the Express Mock Server and React UI simultaneously with a single command:
```bash
npm start
```

*OR* if you prefer running them in separate terminal windows:
- **Terminal 1 (Backend Mock Server):**
  ```bash
  npm run server
  ```
  *(Server runs at `http://localhost:5000`)*

- **Terminal 2 (React Frontend UI):**
  ```bash
  npm run client
  ```
  *(React App opens at `http://localhost:3000`)*

---

## 🎯 How to Demonstrate for Participants

### 1. React Routing Demonstration
- Click on the navigation header links (**Home**, **Items Manager (CRUD)**, **About**).
- Point out that page transitions occur instantly without full browser page reloads due to `react-router-dom` v6 `<Routes>` and `<NavLink>`.

### 2. Axios REST API Demonstration (`/items` page)

| HTTP Method | Action / Button | Expected HTTP Status Code | Behavior |
| :--- | :--- | :--- | :--- |
| **GET** | Click **"Refresh List (GET)"** | `200 OK` | Fetches and renders current items (initialized with 3 rows). |
| **POST** | Enter Name & Role, click **"Create (POST)"** | `201 Created` | Creates new item in memory, refreshes table, logs 201 Created. |
| **PUT** | Click **"Edit (PUT)"** on a row, edit, click **"Update (PUT)"** | `200 OK` | Updates existing item by ID, logs 200 OK. |
| **DELETE** | Click **"Delete (DELETE)"** on a row | `204 No Content` | Removes item by ID, logs 204 No Content. |

### 3. Server Error Conditions Demonstration (`/items` page)
Show participants how Axios handles non-2xx responses using `try...catch` and `error.response`:

- **400 Bad Request**: Submit the **"Create (POST)"** form with empty inputs. Observe the red status badge: `400 Bad Request` and error message `"Both 'name' and 'role' fields are required."`
- **409 Conflict**: Try creating a user with an existing name (e.g., `"Alice Johnson"`). Observe `409 Conflict`.
- **Live Error Buttons**: Click **"Trigger 400 Bad Request"**, **"Trigger 404 Not Found"**, or **"Trigger 500 Server Error"** to instantly demonstrate client-side error handling to participants.
