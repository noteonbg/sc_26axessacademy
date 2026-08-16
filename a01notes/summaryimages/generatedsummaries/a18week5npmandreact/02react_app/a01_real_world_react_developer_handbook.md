# Real-World React Developer Handbook: A Simple Guide for Project Teams

This handbook translates the core concepts from the React Training Curriculum (Modules 1, 2, and 3) into simple, numbered step-by-step instructions with clear code syntax breakdowns. It is designed for freshers building production React applications.

---

## Part 1: React Fundamentals, Components & State (Module 1)

### 1. Understanding React and Project Directory Layout
React is a declarative JavaScript library for building user interfaces by composing small, isolated code blocks called **components**.

Standard project layout:
```
my-app/
├── public/
│   └── index.html             # The HTML template host page
├── src/
│   ├── index.js               # JavaScript entry point mounting App to DOM
│   ├── App.js                 # Root component container
│   └── components/            # Subfolder for reusable custom components
└── package.json               # Project dependencies and npm scripts
```

---

### 2. How to Write a Functional Component with JSX
JSX is a syntax extension that combines JavaScript logic with HTML-like markup inside curly braces `{}`.

```javascript
import React from 'react';

export default function WelcomeBanner() {
    const userName = "Euler";

    return (
        <div className="banner-box">
            <h1>Welcome, {userName}!</h1>
        </div>
    );
}
```

#### Syntax Breakdown:
1. `import React from 'react';`: Imports the React library.
2. `export default function WelcomeBanner()`: Declares a reusable component function and exports it.
3. `const userName = "Euler";`: Standard JavaScript variable inside the function.
4. `{userName}`: Curly braces `{}` allow embedding any JavaScript variable or expression directly inside HTML markup.

---

### 3. Passing Data via Component Props and Default Values
Props (properties) pass data from a parent component down to a child component. Props are read-only and immutable.

```javascript
import React from 'react';

export default function HelloMessage({ name, message = "Hi, Hello" }) {
    return (
        <div className="message-box">
            <p>Message from {name} : {message}</p>
        </div>
    );
}
```

#### Syntax Breakdown:
1. `{ name, message = "Hi, Hello" }`: Object destructuring of incoming props. `message = "Hi, Hello"` provides a fallback default value if the parent does not send a `message` prop.
2. **Parent Invocation**:
   - `<HelloMessage name="Ramanujam" />` renders `"Message from Ramanujam : Hi, Hello"`.
   - `<HelloMessage name="Ramanujam" message="Welcome to the team!" />` renders `"Message from Ramanujam : Welcome to the team!"`.

---

### 4. Managing Dynamic State (`useState` Hook)
State is a component's private data storage. When state changes, React automatically re-renders the component using its Virtual DOM diffing algorithm.

```javascript
import React, { useState } from 'react';

export default function Counter() {
    const [count, setCount] = useState(0);

    const handleIncrement = () => {
        setCount(prevCount => prevCount + 1);
    };

    return (
        <div>
            <h3>Current Count: {count}</h3>
            <button onClick={handleIncrement}>Increment</button>
        </div>
    );
}
```

#### Syntax Breakdown:
1. `const [count, setCount] = useState(0);`: Declares a state variable `count` initialized to `0`, and a setter function `setCount`.
2. `setCount(prevCount => prevCount + 1)`: Updates the state value. **Rule**: Never mutate state directly (e.g. `count = count + 1` is strictly forbidden). Always use `setCount()`.
3. `onClick={handleIncrement}`: Attaches a click event listener passing the function reference.

---

## Part 2: Lists, Communication & Controlled Forms (Module 2)

### 5. Rendering Data Lists (`.map()` and `key` Prop)
When displaying arrays of objects (like customer tables), use JavaScript `.map()` and supply a unique `key` prop on each item.

```javascript
import React from 'react';

export default function CustomerList({ customers }) {
    return (
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody>
                {customers.map((item) => (
                    <tr key={item.id}>
                        <td>{item.id}</td>
                        <td>{item.firstName} {item.lastName}</td>
                        <td>{item.email}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
}
```

#### Syntax Breakdown:
1. `customers.map((item) => ...)`: Iterates through the array and returns a `<tr>` table row for every customer.
2. `key={item.id}`: **Mandatory Rule**: React requires a unique `key` attribute on repeated list elements to optimize Virtual DOM re-rendering performance.

---

### 6. Child-to-Parent Communication (Callback Functions)
Child components cannot send props up to parents directly. Instead, the parent passes a callback function down via props, and the child calls that function with data.

```javascript
// Child Component
export default function CustomerForm({ onAddCustomer }) {
    const [email, setEmail] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault(); // Prevents full browser page reload
        if (!email.trim()) {
            window.alert("Email is required");
            return;
        }
        onAddCustomer({ email }); // Calls parent function with new data
        setEmail('');
    };

    return (
        <form onSubmit={handleSubmit}>
            <input 
                type="email" 
                value={email} 
                onChange={(e) => setEmail(e.target.value)} 
            />
            <button type="submit">Submit</button>
        </form>
    );
}
```

#### Syntax Breakdown:
1. `e.preventDefault()`: Stops the default browser behavior of refreshing the webpage upon form submission.
2. `onChange={(e) => setEmail(e.target.value)}`: Captures every keystroke and syncs the text input value directly into React state. This pattern is called a **Controlled Component**.
3. `onAddCustomer({ email })`: Triggers the parent's function, passing the new customer object up to the parent component.

---

### 7. Debugging State & Props with React DevTools
1. **Install Browser Extension**: Install "React Developer Tools" from the Chrome Web Store.
2. **Inspect Component Tree**: Press `F12` in Chrome, click the **Components** tab.
3. **Real-Time Audit**: Click any component in the tree to inspect its live `props`, `state`, and hooks values without modifying code.

---

## Part 3: Client-Side Routing, REST APIs & `useEffect` (Module 3)

### 8. Side-Effects and Data Fetching (`useEffect` Hook)
The `useEffect` hook handles side-effects like fetching data from REST API servers after a component mounts.

```javascript
import React, { useState, useEffect } from 'react';
import axios from 'axios';

export default function CustomerDataPage() {
    const [customers, setCustomers] = useState([]);

    useEffect(() => {
        // Runs once after initial component mount
        axios.get('http://localhost:8080/api/customers')
            .then(response => {
                setCustomers(response.data);
            })
            .catch(error => {
                console.error("API Error:", error);
            });
    }, []); // Empty dependency array [] = run once on mount

    return (
        <div>
            <h2>Customer Count: {customers.length}</h2>
        </div>
    );
}
```

#### Syntax Breakdown:
1. `useEffect(callback, [])`: The empty dependency array `[]` instructs React to run the API call **only once** when the component first loads.
2. `axios.get(url)`: Returns a JavaScript Promise.
3. `.then(response => setCustomers(response.data))`: Handles successful HTTP 200 responses and updates component state.
4. `.catch(error => ...)`: Catches network errors or HTTP error status codes.

---

### 9. Consuming REST APIs with Axios (Full CRUD Operations)

Follow these syntax patterns for REST API integration:

#### A. GET (Fetch Data)
```javascript
axios.get('http://localhost:8080/api/customers')
    .then(res => setCustomers(res.data));
```

#### B. POST (Create New Resource)
```javascript
const newCustomer = { firstName: "Jane", email: "jane@corp.com" };

axios.post('http://localhost:8080/api/customers', newCustomer)
    .then(res => console.log("Created:", res.data));
```

#### C. PUT (Update Resource)
```javascript
const updatedCustomer = { firstName: "Jane", email: "jane.updated@corp.com" };

axios.put('http://localhost:8080/api/customers/123', updatedCustomer)
    .then(res => console.log("Updated:", res.data));
```

#### D. DELETE (Remove Resource)
```javascript
axios.delete('http://localhost:8080/api/customers/123')
    .then(res => console.log("Deleted successfully"));
```

---

### 10. Declarative Routing with `react-router-dom`
Client-side routing allows navigating between views without reloading the browser window.

```javascript
import React from 'react';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import HomePage from './pages/HomePage';
import CustomersPage from './pages/CustomersPage';
import CustomerDetailsPage from './pages/CustomerDetailsPage';

export default function AppRouter() {
    return (
        <BrowserRouter>
            <nav>
                <Link to="/">Home</Link> | 
                <Link to="/customers">Customers</Link>
            </nav>

            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/customers" element={<CustomersPage />} />
                <Route path="/customers/:id" element={<CustomerDetailsPage />} />
            </Routes>
        </BrowserRouter>
    );
}
```

#### Syntax Breakdown:
1. `<BrowserRouter>`: Root routing wrapper component connecting the app to browser URL history.
2. `<Link to="/customers">`: Renders an accessible link that updates the URL path without refreshing the page.
3. `<Routes>` & `<Route path="..." element={<Component />} />`: Compares the browser URL path against registered routes and renders the matching `element` component.
4. `path="/customers/:id"`: Parameterized route matching dynamic IDs (e.g. `/customers/101`).

---

## Summary Developer Rules Checklist for Freshers

1. **State Immutability**: Always update state using setter functions (`setCount`), never assign directly.
2. **Controlled Inputs**: Bind HTML input values to `useState` and update on `onChange`.
3. **List Keys**: Always assign a unique `key={item.id}` when mapping arrays to JSX elements.
4. **Form Submissions**: Call `e.preventDefault()` inside submit handlers to prevent page reload.
5. **Rest API Calls**: Place initial `axios.get()` calls inside `useEffect(() => {}, [])`.
6. **ESLint Compliance**: Use `window.alert()` and `window.confirm()` explicitly to avoid build warnings.
