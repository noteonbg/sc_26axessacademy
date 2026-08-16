# Student Guide: How Client-Side Routing Works in React

This guide explains how client-side routing functions in React Single-Page Applications (SPAs), providing both a **Generic Layout Component Example** (Header, Sidebar, Body, Footer) and a concrete flow based on **`05_react_usecase3_apex_manufacturing_router`**, complete with detailed explanations of every React syntax used.

---

## 1. Single-Page Application (SPA) vs Traditional Multi-Page Applications (MPA)

1. **Traditional Multi-Page Applications (MPA)**  
   In traditional web apps, clicking a navigation link sends a new HTTP request to the web server, which reloads the entire browser window and returns a completely new HTML page.

2. **React Single-Page Applications (SPA)**  
   In React, `index.html` is loaded into the browser **only once**. When users click navigation links, React dynamically swaps component views on the screen without reloading the page, resulting in instant screen updates and zero page flicker.

---

## 2. Generic Layout Routing Concept (Header, Sidebar, Dynamic Body, Footer)

Most real-world web applications share a common page layout structure where the **Header**, **Sidebar**, and **Footer** remain visible at all times, while only the **Main Body Area** swaps dynamically based on the current route path.

### Visual Page Layout Architecture:

```
+-----------------------------------------------------------------+
|                       HeaderComponent                           |
+-------------------+---------------------------------------------+
|                   |                                             |
|                   |     Dynamic Main Body Area                  |
|                   |                                             |
|   SidebarComponent|     {currentRoute === '/home' && <Home />}  |
|   - Home Link     |     {currentRoute === '/profile' && <Prof>}|
|   - Profile Link  |     {currentRoute === '/settings' && <Set>}|
|   - Settings Link |                                             |
|                   |                                             |
+-------------------+---------------------------------------------+
|                       FooterComponent                           |
+-----------------------------------------------------------------+
```

### Generic Component Code Implementation:

```javascript
import React, { useState } from 'react';
import HeaderComponent from './components/HeaderComponent';
import SidebarComponent from './components/SidebarComponent';
import FooterComponent from './components/FooterComponent';
import HomePage from './pages/HomePage';
import ProfilePage from './pages/ProfilePage';
import SettingsPage from './pages/SettingsPage';

export default function GenericAppLayout() {
    const [currentRoute, setCurrentRoute] = useState('/home');

    const navigateTo = (path) => {
        setCurrentRoute(path);
    };

    return (
        <div className="app-container">
            {/* 1. Persistent Header across all pages */}
            <HeaderComponent onNavigate={navigateTo} />

            <div className="body-wrapper" style={{ display: 'flex', minHeight: '80vh' }}>
                {/* 2. Persistent Sidebar menu */}
                <SidebarComponent currentRoute={currentRoute} onNavigate={navigateTo} />

                {/* 3. Dynamic Main Body Area */}
                <main className="main-content" style={{ flex: 1, padding: '20px' }}>
                    {currentRoute === '/home' && <HomePage />}
                    {currentRoute === '/profile' && <ProfilePage />}
                    {currentRoute === '/settings' && <SettingsPage />}
                </main>
            </div>

            {/* 4. Persistent Footer across all pages */}
            <FooterComponent />
        </div>
    );
}
```

---

## 3. Explanation of Each React Code Syntax Used

Below is a detailed breakdown of what each line of code syntax does in the example above:

1. **`import React, { useState } from 'react';`**  
   - `import`: ES module syntax used to pull external code from the `'react'` library.
   - `{ useState }`: A React Hook that allows functional components to hold and update state data across re-renders.

2. **`export default function GenericAppLayout()`**  
   - `export default`: Marks this function as the main primary component exported by this file so other files can import it.
   - `function GenericAppLayout()`: Defines a standard functional React component.

3. **`const [currentRoute, setCurrentRoute] = useState('/home');`**  
   - `useState('/home')`: Initializes the state with the default starting URL path string `'/home'`.
   - `currentRoute`: State variable holding the active route path string (e.g. `'/home'`, `'/profile'`, `'/settings'`).
   - `setCurrentRoute`: The setter function used to update the `currentRoute` state value.
   - `[currentRoute, setCurrentRoute]`: ES6 Array destructuring syntax extracting the state variable and its updater function.

4. **`const navigateTo = (path) => { setCurrentRoute(path); };`**  
   - `(path) => { ... }`: ES6 arrow function syntax.
   - `navigateTo`: A helper callback function that receives a target route string (like `'/settings'`) and calls `setCurrentRoute('/settings')` to trigger a page swap.

5. **`<HeaderComponent onNavigate={navigateTo} />`**  
   - `<HeaderComponent />`: Instantiates and renders the `HeaderComponent`.
   - `onNavigate={navigateTo}`: JSX Prop syntax passing the `navigateTo` function down to `HeaderComponent` as a prop named `onNavigate`.

6. **`onClick={() => onNavigate('/home')}`**  
   - `onClick`: React synthetic event listener attached to an HTML element (like a link or button).
   - `() => onNavigate('/home')`: An inline arrow function wrapper. **Crucial Rule**: The arrow wrapper ensures `onNavigate` executes ONLY when the user clicks the element (without `() =>`, the function would run immediately when the page renders).

7. **`className={currentRoute === '/home' ? "active" : ""}`**  
   - `className`: React's JSX equivalent of the standard HTML `class` attribute.
   - `condition ? trueValue : falseValue`: JavaScript Ternary Operator. If `currentRoute` equals `'/home'`, it returns `"active"` to highlight the tab; otherwise, it returns `""` (empty string).

8. **`{currentRoute === '/home' && <HomePage />}`**  
   - `{ ... }`: Curly braces allow embedding JavaScript expressions directly inside JSX markup.
   - `&&` (Short-Circuit Logical AND Operator): 
     - If `currentRoute === '/home'` is `true`, React evaluates and renders `<HomePage />`.
     - If `currentRoute === '/home'` is `false`, React short-circuits and renders nothing (`null`).

---

## 4. Step-by-Step Execution Flow in `05_react_usecase3_apex_manufacturing_router`

Below is the concrete execution flow implemented in the **`05_react_usecase3_apex_manufacturing_router`** codebase:

### Step 1: Central Route State Declaration
Inside [`src/routes/AppRouter.jsx`](file:///f:/poc/week5/05_react_usecase3_apex_manufacturing_router/src/routes/AppRouter.jsx), the application maintains state tracking the active path string (`currentRoute`) and any route parameters (`routeParamId`):

```javascript
const [currentRoute, setCurrentRoute] = useState('/machinery');
const [routeParamId, setRouteParamId] = useState(null);
```

*Syntax Explanation*:
- `routeParamId`: Holds target object identifiers (e.g. Asset ID `2`) when viewing details or editing a specific machinery item.

---

### Step 2: Navigation Dispatcher Function
A central navigation function updates route state whenever a user clicks a button or link:

```javascript
const navigateTo = (path, paramId = null) => {
    setCurrentRoute(path);
    setRouteParamId(paramId);
};
```

*Syntax Explanation*:
- `paramId = null`: Default parameter value. If no ID is passed (e.g. `navigateTo('/home')`), `paramId` defaults to `null`.

---

### Step 3: Navbar Header Interaction
The top header component ([`src/common/components/Navbar.jsx`](file:///f:/poc/week5/05_react_usecase3_apex_manufacturing_router/src/common/components/Navbar.jsx)) receives `currentRoute` and `onNavigate` as props. 

Clicking a navigation tab calls `onNavigate('/parts')` without triggering a browser refresh:

```jsx
<ul className="nav-links">
    <li>
        <a 
            className={currentRoute === '/home' ? "active" : ""} 
            onClick={() => onNavigate('/home')}
        >
            Home
        </a>
    </li>
    <li>
        <a 
            className={currentRoute === '/parts' ? "active" : ""} 
            onClick={() => onNavigate('/parts')}
        >
            Parts Inventory
        </a>
    </li>
</ul>
```

---

### Step 4: Conditional View Component Rendering
Inside `AppRouter.jsx`, conditional evaluation renders the page component matching the active `currentRoute` string:

```jsx
<div className="content-container">
    {currentRoute === '/home' && <HomePage />}
    {currentRoute === '/parts' && <PartsInventoryPage />}
    {currentRoute === '/apex' && <ApexCorpPage />}

    {currentRoute === '/machinery' && (
        <MachineryListPage 
            machineryList={machineryList} 
            onNavigate={navigateTo} 
            onDelete={handleDelete} 
        />
    )}
</div>
```

---

### Step 5: Parameterized Routing (Details & Edit Screens)
When a user clicks **"Show Details"** or **"Edit"** on a specific table row in `MachineryListPage`:

```jsx
{currentRoute === '/machinery/show' && (
    <MachineryDetailsPage 
        item={machineryList.find(m => m.id === routeParamId)} 
        onBack={() => navigateTo('/machinery')} 
    />
)}
```

*Syntax Explanation*:
- `machineryList.find(m => m.id === routeParamId)`: JavaScript Array `.find()` method. It iterates through `machineryList` and returns the single item object whose `id` matches `routeParamId`.

---

### Step 6: Form Submission and Programmatic Redirect
When a user fills out the form in `MachineryFormPage` and clicks **"Register Machinery"**:

```javascript
const handleSave = async (machineData) => {
    if (machineData.id) {
        await updateMachinery(machineData);
    } else {
        await createMachinery(machineData);
    }
    // Programmatic redirect after save
    navigateTo('/machinery');
};
```

*Syntax Explanation*:
- `async / await`: Asynchronous JavaScript keywords that wait for the backend API call to finish before navigating.
- `navigateTo('/machinery')`: Programmatically changes the route back to the machinery list view once the save completes.

---

## 5. Summary Checklist for Student Projects

Follow these 5 steps when adding client-side routing to any React project:

1. **Define Active Route State**: Maintain a `currentRoute` string state in your top-level router component.
2. **Build Navigation Handler**: Create a `navigateTo(path, param)` callback function.
3. **Pass Callback to Navigation Bar**: Connect menu click events to `navigateTo('/path')`.
4. **Use Conditional Rendering**: Render `{currentRoute === '/path' && <YourPage />}` inside the main body area.
5. **Implement Programmatic Redirects**: Call `navigateTo('/list')` inside submit handlers after creating or updating records.
