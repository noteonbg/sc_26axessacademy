# Student Guide: How to Code, Verify, Debug, Test, and Deploy a React Component

This guide provides simple, numbered step-by-step instructions for student developers on how to write a React component from scratch, verify it locally, debug component and UI errors, understand testing concepts, and deploy to production—both for standalone projects and standard **`create-react-app`** projects.

---

## 1. Creating a React App using `create-react-app`

If you are starting a new React project from scratch using the official `create-react-app` tool, follow these steps:

1. **Step 1: Open Your Terminal**  
   Open your terminal (PowerShell, Command Prompt, or VS Code Terminal).

2. **Step 2: Run the `create-react-app` Command**  
   Type the following command to generate a new React project:
   ```bash
   npx create-react-app my-app
   ```
   *(Replace `my-app` with your preferred project name).*

3. **Step 3: Navigate into the Generated Project Folder**  
   ```bash
   cd my-app
   ```

4. **Step 4: Inspect the Project Directory Structure**  
   `create-react-app` generates the following standard layout:
   ```
   my-app/
   ├── public/                      # Static assets and index.html
   ├── src/                         # React source code
   │   ├── App.js                   # Root component
   │   ├── App.css                  # App styles
   │   ├── index.js                 # React DOM entrypoint
   │   └── index.css                # Global styles
   ├── package.json                 # Project dependencies & scripts
   └── .gitignore                   # Git ignore file
   ```

---

## 2. How to Code a React Component in a `create-react-app` Project

Follow these 5 simple numbered steps to write a new component inside a `create-react-app` project:

1. **Step 1: Create a `components/` Subfolder**  
   Inside `src/`, create a subfolder named `components/`:
   ```
   src/components/
   ```

2. **Step 2: Create Your Component File**  
   Create a new file named `MyCustomCard.js` (or `.jsx`) inside `src/components/`:
   ```
   src/components/MyCustomCard.js
   ```

3. **Step 3: Import React and State Hooks**  
   Write the import statement at the top of `src/components/MyCustomCard.js`:
   ```javascript
   import React, { useState } from 'react';
   ```

4. **Step 4: Define Functional Component & State**  
   Define your component function receiving `props` and setting local state:
   ```javascript
   export default function MyCustomCard({ title, initialStatus }) {
       const [status, setStatus] = useState(initialStatus);

       return (
           <div className="card-box" style={{ border: '1px solid #cbd5e1', padding: '20px', margin: '15px 0' }}>
               <h3>{title}</h3>
               <p>Current Status: <strong>{status}</strong></p>
               <button onClick={() => setStatus('COMPLETED')}>
                   Mark Completed
               </button>
           </div>
       );
   }
   ```

5. **Step 5: Import & Render Component inside `src/App.js`**  
   Open `src/App.js`, import your component, and place the tag inside the JSX output:
   ```javascript
   import React from 'react';
   import './App.css';
   import MyCustomCard from './components/MyCustomCard';

   function App() {
     return (
       <div className="App">
         <h1>My React Application</h1>
         <MyCustomCard title="Equipment Inspection" initialStatus="PENDING" />
       </div>
     );
   }

   export default App;
   ```

---

## 3. How to Check if Your Component Works Locally (`npm start`)

1. **Step 1: Run `npm start` in Terminal**  
   Inside your project directory (`my-app/`), run:
   ```bash
   npm start
   ```

2. **Step 2: Automatic Browser Launch**  
   `create-react-app` automatically compiles your code and opens your web browser to:
   **`http://localhost:3000/`**

3. **Step 3: Test Interactive State & UI**  
   - Verify that your card title `"Equipment Inspection"` and status `"PENDING"` appear on screen.
   - Click the **"Mark Completed"** button to confirm that status changes dynamically to `"COMPLETED"`.

---

## 4. How to Debug a React Component when UI is Not Appearing Correctly

If your React component is not displaying correctly, shows a blank screen, or button clicks do not work, follow these 6 numbered debugging steps:

1. **Step 1: Open Browser Developer Tools Console (F12)**  
   Press `F12` in your web browser (or right-click anywhere on the page and select **Inspect -> Console tab**).
   - Look for red JavaScript error messages.
   - A red error like `Uncaught ReferenceError: x is not defined` or `Cannot read properties of undefined` points directly to the exact file and line number causing the crash.

2. **Step 2: Inspect Props and State using React Developer Tools**  
   Install the official **React Developer Tools** extension for Chrome/Edge.
   - Open DevTools (`F12`) and click the **Components** tab.
   - Select your component in the component tree to inspect its current `props` and `state` values in real-time. Verify whether state updates when you click buttons.

3. **Step 3: Add Temporary `console.log()` Statements**  
   Insert `console.log()` right before your component's `return` statement to inspect variable values:
   ```javascript
   export default function MyCustomCard({ title, initialStatus }) {
       console.log('Props received - title:', title, 'initialStatus:', initialStatus);
       const [status, setStatus] = useState(initialStatus);
       console.log('Current state status:', status);
       ...
   ```
   Open the browser console to check if the data being passed into your component matches what you expect.

4. **Step 4: Check Component Import and Export Statements**  
   If you see the error `Element type is invalid: expected a string or a class... but got: undefined`:
   - Check if your component file has `export default function ComponentName()`.
   - Check if your import statement matches: `import ComponentName from './components/ComponentName'`.
   - Check that the file path spelling and file extensions (`.js` vs `.jsx`) are exact.

5. **Step 5: Check JSX Return Syntax & Parent Wrapper Elements**  
   React components must return a **single root JSX parent element**. Returning multiple adjacent elements without a parent container causes compilation failures:
   - **Incorrect**:
     ```jsx
     return (
         <h3>Title</h3>
         <p>Paragraph</p>
     );
     ```
   - **Correct**:
     ```jsx
     return (
         <div>
             <h3>Title</h3>
             <p>Paragraph</p>
         </div>
     );
     ```

6. **Step 6: Inspect Terminal Output for Compiler Errors**  
   Look at the terminal window where `npm start` is running.
   - If there is a missing closing bracket, syntax typo, or un-closed HTML tag (`<input />`), Node/Babel will display the exact line number and code snippet where the syntax error occurred.

---

## 5. Automated Testing Concepts (General Overview)

Unit testing and component testing in React allow automated verification of component rendering, prop calculations, and button click events in memory without requiring manual clicking in the browser.

---

## 6. How Your React Application is Deployed to Production

1. **Step 1: Run Production Build Command**  
   In your terminal, execute:
   ```bash
   npm run build
   ```
   This command compiles, compresses, and bundles all your `.js`/`.jsx` files into static HTML, JavaScript, and CSS files inside a `/build` folder.

2. **Step 2: Upload `/build` Folder to Web Server**  
   Transfer the generated static files inside `/build` to a production hosting platform (such as Nginx, AWS S3/CloudFront, Vercel, or Netlify).

3. **Step 3: Serve to Users Over HTTPS**  
   The web server delivers the static files to end users globally over secure HTTPS. No Node development server is required in production because static files execute natively in the user's browser.
