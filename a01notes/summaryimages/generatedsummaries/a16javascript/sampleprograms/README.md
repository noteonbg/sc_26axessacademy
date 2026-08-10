# JavaScript Training Demos — Financial Domain & React Focus

This repository contains a comprehensive suite of detailed JavaScript programs corresponding to every topic discussed in the **JavaScript.pdf** presentation (Campus Content 2026 / Week 4 — Standard Chartered Curriculum).


### 1. JavaScript & Core Language Practices
- 🔒 **Prefer `const` over `let`, Never use `var`**: Declare variables with `const` by default. Only switch to `let` if re-assignment is explicitly required. Avoid `var` completely to prevent variable hoisting bugs and global scope leaks.
- ⚡ **Strict Equality (`===`)**: Always use strict equality (`===` and `!==`) to prevent unintended type coercion (e.g., `0 == ""` is `true`, but `0 === ""` is `false`).
- 🛡️ **Guard Clauses & Early Returns**: Replace nested `if/else` blocks with guard clauses at the beginning of functions to improve readability and reduce cognitive load.
  ```js
  // Bad
  function process(amount) {
      if (amount > 0) {
          if (hasBalance) { /* logic */ }
      }
  }
  // Good
  function process(amount) {
      if (amount <= 0) return { error: "Invalid amount" };
      if (!hasBalance) return { error: "Insufficient balance" };
      /* main logic */
  }
  ```
- 💰 **Financial Floating-Point Math Safety**: Binary floating-point numbers in JS suffer from IEEE 754 precision issues (`0.1 + 0.2 === 0.30000000000000004`). In banking applications:
  - Perform calculations using integer units (cents / paise): `(10 + 20) / 100`.
  - Format monetary displays with `.toFixed(2)` or use `BigInt` / Decimal precision libraries.
- 🎭 **PII Data Masking**: Never expose sensitive financial data (PAN, Account Numbers, Card Numbers) in plain text or console logs. Always mask string outputs (e.g., `XXXX-XXXX-4820`).

---

### 2. React Architecture & State Management Practices
- 🔄 **State Immutability is Non-Negotiable**: React uses reference equality (`Object.is`) to detect state changes. Never mutate state directly (`state.push()` or `user.balance = 500`). Always return new references:
  ```js
  // ❌ Bad (Direct Mutation - No UI Re-render!)
  customer.balance += 500;
  setCustomer(customer);

  // ✅ Good (Immutable Spread Update)
  setCustomer(prev => ({ ...prev, balance: prev.balance + 500 }));
  ```
- 🔑 **Stable Key Props in List Rendering**: When mapping arrays to JSX elements via `.map()`, always provide a unique, stable `key` prop (e.g. `item.id`). Avoid using array indices (`key={index}`) if items can be inserted, deleted, or re-sorted.
- 📝 **Controlled Form Components**: Bind form inputs directly to React component state using `value` and `onChange` handlers so that state remains the single source of truth.
- 🚫 **No Direct DOM Manipulation in React**: Avoid using `document.getElementById()` or `querySelector()` inside React components. Use React state or `useRef` hooks if direct DOM node interactions (e.g. auto-focusing an input) are needed.

---

### 3. Async Programming & API Integration Practices
- ⏳ **Manage All 3 Async States**: Network calls to core banking APIs must explicitly track:
  1. **Loading State** (`isLoading = true` -> show spinner)
  2. **Success State** (`data` received -> render UI)
  3. **Failure State** (`error` caught -> display user-friendly alert)
- 🧹 **Proper Async Error Handling**: Always wrap `await` calls in `try...catch...finally` blocks to ensure loading states are reset even if an API call throws an HTTP error.
- 🛡️ **Double-Layer Validation**: Client-side validation improves user experience with instant feedback, but **SERVER-SIDE VALIDATION IS MANDATORY** for all transactions to prevent malicious tampering.

---

### 4. Code Cleanliness & Maintenance Practices
- 🏷️ **Naming Conventions**:
  - `camelCase` for variables, properties, and functions (`calculateInterest`, `accountBalance`).
  - `PascalCase` for React Functional Components and Classes (`CustomerCard`, `BankAccount`).
  - `UPPER_SNAKE_CASE` for global constants (`MAX_WITHDRAWAL_LIMIT`).
- 🎯 **Single Responsibility Principle (SRP)**: Each function should do exactly one thing well. Keep components and helper utilities concise and modular.

---

## 📁 Folder Contents Overview

| File Name | PDF Module | Description & Topics Covered | Financial Domain Example | React Connection |
| :--- | :--- | :--- | :--- | :--- |
| [`01_fundamentals.js`](javascriptdemos/01_fundamentals.js) | **Module 1** | Dynamic typing, client vs server side scripting, inline vs external script loading | Bank withdrawal validation & available balance checks | React relies on client-side JS runtime; understanding dynamic typing prevents string concatenation bugs |
| [`02_variables_data_types.js`](javascriptdemos/02_variables_data_types.js) | **Module 2** | Data types (`undefined`, `null`, `number`, `string`, `boolean`, `array`, `object`), String/Number methods, IEEE 754 floating point issues, type casting | Masking account numbers (`XXXX-4820`), formatting currency (`toFixed`), computing paise/cents safe math | Primitive vs reference equality in `useEffect` dependency arrays & `React.memo` |
| [`03_functions_and_scope.js`](javascriptdemos/03_functions_and_scope.js) | **Module 3** | Function declarations vs expressions (anonymous/named), global vs local scope, **Fibonacci (10 terms)** & **Table of 8** | Compound growth series & 8% interest rate tier matrix | Modern React apps are composed of Functional Components & callback props |
| [`04_arrays_strings_objects.js`](javascriptdemos/04_arrays_strings_objects.js) | **Module 4** | Arrays, mutating methods (`push`/`sort`) vs functional methods (`map`/`filter`/`reduce`), **String uppercase transformation** | Ledger statement analysis, transaction net worth sum, bank customer object | **Immutability in React State**: Why `map()` & `filter()` are mandatory; JSX key props |
| [`05_events_and_handlers.html`](javascriptdemos/05_events_and_handlers.html) & [`.js`](javascriptdemos/05_events_and_handlers.js) | **Module 5** | Mouse (`click`, `dblclick`, `hover`), Keyboard (`keyup`), Form (`submit`, `change`, `focus`, `blur`), Window events, `addEventListener` | Double-click transfer confirmation, rate card hover, live transfer amount validation | React Synthetic Events system (`onClick`, `onChange`), event delegation, controlled components |
| [`06_dom_and_validation.html`](javascriptdemos/06_dom_and_validation.html) & [`.js`](javascriptdemos/06_dom_and_validation.js) | **Module 6** | DOM tree, selector methods, node creation/deletion (`createElement`, `appendChild`), Regex patterns & quantifiers (`[A-Za-z]+`, `[7-9]{1}[0-9]{9}`) | Dynamic service listing, applicant **Name alphabet check** (`please enter only alphabets`), 10-digit phone check | Direct DOM manipulation vs React Virtual DOM abstraction tree |
| [`07_advanced_javascript.js`](javascriptdemos/07_advanced_javascript.js) | **Module 7** | `let`/`const` block scope, Arrow functions, ES6 Classes (`BankAccount`), Destructuring, Spread operator (`...`) | Customer Account class, portfolio state merging with spread operator | Prop destructuring (`const Card = ({ name, balance }) => ...`), immutable state updates |
| [`08_async_promises_fetch.js`](javascriptdemos/08_async_promises_fetch.js) | **Module 8** | Promises (`resolve`/`reject`), Fetch API, `async`/`await`, `try...catch...finally` | Asynchronous credit score verification & foreign exchange (Forex) rate fetching | Asynchronous data fetching inside `useEffect` with loading, success, and error states |
| [`09_usecase_customer_onboarding.html`](javascriptdemos/09_usecase_customer_onboarding.html) | **Use Case 1** (Slide 56-57) | Bootstrap 5 customer form, Regex validation for Name, Email, Contact (10 digits starting 7-9), Account Type, tabular display | Bank customer registration & dynamic tabular summary listing | Controlled forms and dynamic table rendering |
| [`10_usecase_testimonial_manager.html`](javascriptdemos/10_usecase_testimonial_manager.html) | **Use Case 2** (Slide 58-59) | Customer Feedback Portal with dynamic append, inline **Edit** (pre-fill form/update) and **Delete** operations | Standard Chartered customer testimonial management system | Immutable array CRUD state operations (`filter` for delete, `map` for edit) |
| [`index.html`](javascriptdemos/index.html) | **Master Hub** | Interactive master launcher dashboard to browse and run all 10 module demos in browser | Complete Standard Chartered Training Portal | Unified showcase for all modules |

---

## 🚀 How to Run the Demos

### 1. Terminal / Command Line Execution (Node.js)
You can run any of the standalone `.js` logic files directly using Node.js:
```bash
# Navigate to the demos directory
cd javascriptdemos

# Run individual module scripts
node 01_fundamentals.js
node 02_variables_data_types.js
node 03_functions_and_scope.js
node 04_arrays_strings_objects.js
node 07_advanced_javascript.js
node 08_async_promises_fetch.js
```

### 2. Browser Interactive Execution
Double-click or open any of the `.html` files in Google Chrome or any modern web browser:
- Open [`index.html`](javascriptdemos/index.html) to access the **Master Dashboard Launcher**.
- Open [`05_events_and_handlers.html`](javascriptdemos/05_events_and_handlers.html) for the Event Trigger Simulator.
- Open [`06_dom_and_validation.html`](javascriptdemos/06_dom_and_validation.html) for DOM Manipulation & Regex Validation.
- Open [`09_usecase_customer_onboarding.html`](javascriptdemos/09_usecase_customer_onboarding.html) for Presentation Use Case 1.
- Open [`10_usecase_testimonial_manager.html`](javascriptdemos/10_usecase_testimonial_manager.html) for Presentation Use Case 2.
