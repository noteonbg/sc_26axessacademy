# Real-World Guide: Node.js, npm, and JSON in Software Engineering

This guide translates the core principles of Node.js, npm (Node Package Manager), and JSON (JavaScript Object Notation) into clear, numbered instructions for real-world application development, based on the Week 5 training curriculum.

---

## 1. How Node.js is Used in Real-Life Applications

Node.js is an open-source, cross-platform JavaScript runtime environment that executes JavaScript code outside of a web browser.

### Key Real-Life Uses:

1. **Running Server-Side Web Applications**  
   In traditional web development, JavaScript only ran inside browsers. Node.js enables developers to write backend server code, handle HTTP requests, connect to databases, and build REST APIs using JavaScript.

2. **Utilizing Built-in Core Modules**  
   Node.js includes built-in core modules so developers do not need to rewrite low-level system utilities:
   - `http`: Creates HTTP servers and handles incoming client requests.
   - `fs` (File System): Reads, writes, creates, and appends log files on server storage.
   - `path`: Resolves file and directory paths across different operating systems (Windows, Linux, macOS).
   - `url`: Parses and resolves URL strings and web addresses.
   - `querystring`: Extracts parameters from web request URL strings.

3. **Building Modular Applications (Local Modules)**  
   Developers split large applications into small, reusable JavaScript files called local modules. Modules use `export` to expose functionality and `import` to consume it. Local modules act as singletons—meaning regardless of how many times a module is imported, only one instance exists in memory.

---

## 2. How npm (Node Package Manager) is Used in Real-Life Workflows

npm is the package manager for the JavaScript programming language. It consists of three components: the website (npmjs.com), the online package registry database, and the Command Line Interface (CLI).

### Step-by-Step Package Management Commands:

1. **Creating the `package.json` Manifest File**  
   Before installing packages, initialize a project manifest file:
   ```bash
   npm init -y
   ```
   This creates `package.json` to track project metadata, scripts, and installed dependencies.

2. **Installing Local Dependencies (`npm install <package>`)**  
   When your application requires a specific library (such as React, Axios, or Bootstrap), install it locally in the project directory:
   ```bash
   npm install bootstrap
   ```
   This downloads the package into the local `node_modules/` directory and records it under `dependencies` in `package.json`.

3. **Installing Global Tools (`npm install <package> -g`)**  
   If a tool is needed as a command-line utility across your entire machine (such as TypeScript or Angular CLI), install it globally:
   ```bash
   npm install typescript -g
   ```

4. **Checking for Outdated Packages (`npm outdated`)**  
   Periodically audit installed packages to see which libraries have newer versions available:
   ```bash
   npm outdated
   ```

5. **Updating Packages (`npm update`)**  
   Upgrade project dependencies to their latest compatible versions:
   ```bash
   npm update
   ```

6. **Uninstalling Unused Packages (`npm uninstall <package>`)**  
   Remove unused or deprecated packages to keep `node_modules/` lean:
   ```bash
   npm uninstall bootstrap
   ```

---

## 3. How JSON is Used for Real-Life Data Exchange

JSON (JavaScript Object Notation) is a lightweight, language-independent data-interchange format that has replaced XML in modern software engineering.

### Essential Rules for Real-Life JSON:

1. **Keys Must Be Double-Quoted Strings**  
   In standard JavaScript objects, keys can be unquoted. In JSON, all keys **must** be strings enclosed in double quotes (e.g. `"firstName": "John"`).

2. **Allowed Data Types in JSON**  
   JSON values must strictly be one of the following data types:
   - String (`"John"`)
   - Number (`31`)
   - Object (`{"city": "New York"}`)
   - Array (`["reading", "traveling"]`)
   - Boolean (`true` or `false`)
   - `null`

3. **Forbidden Data Types in JSON**  
   JSON data **cannot** contain:
   - Functions
   - Date objects (dates must be represented as ISO strings)
   - `undefined`

---

## 4. Key JSON Processing Methods in Web Applications

When web applications communicate with backend servers over networks, data is transmitted as plain text strings.

### Step 1: Converting Incoming Server Text to JavaScript Objects (`JSON.parse`)
When receiving raw JSON text response from an API server, convert it into an interactive JavaScript object:
```javascript
const jsonStringFromServer = '{"name": "John", "age": 31, "city": "New York"}';
const userObject = JSON.parse(jsonStringFromServer);

// Access properties directly
console.log(userObject.name); // Output: John
```

### Step 2: Converting JavaScript Objects to Plain Text for API Requests (`JSON.stringify`)
Before sending JavaScript data objects across HTTP network requests to a server, convert the object into a valid JSON string:
```javascript
const newUser = {
    name: "Sandra Rogers",
    department: "Engineering"
};

const payloadString = JSON.stringify(newUser);
// Sent over HTTP request body: '{"name":"Sandra Rogers","department":"Engineering"}'
```

---

## 5. Practical Form-to-JSON Use Case Walkthrough

Follow these 4 steps to capture HTML form input and store it inside a JSON array:

1. **Step 1: Capture Form Input**  
   Read `firstName` and `lastName` values from user input textboxes when the user clicks the Submit button.

2. **Step 2: Construct JavaScript Object**  
   Create an object representing the entry:
   ```javascript
   const newEntry = {
       FirstName: firstNameInput,
       LastName: lastNameInput
   };
   ```

3. **Step 3: Push to Storage Array**  
   Add the object to a running array of records:
   ```javascript
   userRecordsArray.push(newEntry);
   ```

4. **Step 4: Serialize & Display JSON Data**  
   Convert the array into formatted JSON string output using `JSON.stringify(userRecordsArray)` and render the output text on screen:
   ```javascript
   const jsonOutput = JSON.stringify(userRecordsArray);
   document.getElementById('jsonDisplay').innerText = jsonOutput;
   ```
