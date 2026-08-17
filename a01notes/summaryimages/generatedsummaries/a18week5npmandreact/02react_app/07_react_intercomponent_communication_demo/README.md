# Developer Guide: Intercomponent Communication in React (Parent-to-Child & Child-to-Parent)

This guide provides simple, decimal-numbered step-by-step instructions for implementing bi-directional communication between React components using JavaScript objects, with **`07_react_intercomponent_communication_demo`** as a standalone reference.

---

## 1. Overview of Intercomponent Communication

1.1 Parent-to-Child Communication (Data Flow Downward)  
Parent components send data objects down to child components via `props`. The child component receives these props as read-only parameters.

1.2 Child-to-Parent Communication (Data Flow Upward)  
Child components cannot modify parent props directly. To send data back up, the parent passes a callback function down in props, and the child calls that callback function with updated object parameters.

---

## 2. Directory Structure Blueprint

2.1 File Organization:
```
src/
├── components/
│   ├── ParentAccountManager.jsx     # Parent Component (Central State & Callback)
│   └── ChildAccountCard.jsx         # Child Component (Receives Props & Calls Parent Callback)
├── App.jsx                          # Root Application Container
└── index.js                         # React DOM Entrypoint
```

---

## 3. Step-by-Step Code Implementations with Syntax Explanations

### 3.1 Parent Component Implementation (`ParentAccountManager.jsx`)

3.1.1 Step 1: Define Central Account State Object  
The parent component initializes state with a financial account object:
```javascript
const [accountDetails, setAccountDetails] = useState({
    accountHolder: "Euler",
    accountNumber: "ACC-789012",
    balance: 5000.00,
    status: "ACTIVE",
    lastAction: "Initial State Loaded"
});
```

3.1.2 Step 2: Implement Callback Receiver Function  
The parent creates a handler function to receive updated objects from the child:
```javascript
const handleChildUpdate = (updatedAccountObject) => {
    setAccountDetails(updatedAccountObject);
};
```

3.1.3 Step 3: Pass Data Object and Callback Down via Props  
The parent renders the child component and passes both the `account` object and `onUpdateAccount` callback function:
```jsx
<ChildAccountCard 
    account={accountDetails} 
    onUpdateAccount={handleChildUpdate} 
/>
```

---

### 3.2 Child Component Implementation (`ChildAccountCard.jsx`)

3.2.1 Step 1: Receive Props via Object Destructuring  
The child component receives `account` and `onUpdateAccount` in its signature:
```javascript
export default function ChildAccountCard({ account, onUpdateAccount }) { ... }
```

3.2.2 Step 2: Construct Updated Object & Call Parent Function  
When the user submits a deposit, the child creates a new updated object and invokes `onUpdateAccount(updatedAccountObject)`:
```javascript
const handleDepositSubmit = (e) => {
    e.preventDefault();
    const updatedAccountObject = {
        ...account,
        balance: account.balance + parseFloat(depositAmount),
        lastAction: `Deposited $${depositAmount}`
    };

    // Communicating data UP to parent
    onUpdateAccount(updatedAccountObject);
};
```

---

## 4. How to Run the Project

4.1 Step 1: Open Terminal in Directory  
```bash
cd 07_react_intercomponent_communication_demo
```

4.2 Step 2: Install Dependencies  
```bash
npm install
```

4.3 Step 3: Start Dev Server  
```bash
npm start
```
Open `http://localhost:3000/` in your browser to test parent-child intercomponent communication live.
