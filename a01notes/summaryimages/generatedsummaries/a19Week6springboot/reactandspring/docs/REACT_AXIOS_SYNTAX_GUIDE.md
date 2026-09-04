# React & Axios Code & Syntax Guide (Line-by-Line Explanation)

This document provides a detailed line-by-line breakdown of every syntax element, hook, component, Axios method, and JSX tag used in the **`customer-frontend`** React application.

---

## Table of Contents
1. [package.json (Dependencies & Scripts)](#1-packagejson-dependencies--scripts)
2. [apiClient.js (Axios Instance & Interceptor)](#2-apiclientjs-axios-instance--interceptor)
3. [customerService.js (API Service Layer)](#3-customerservicejs-api-service-layer)
4. [Notification.jsx (Alert Toast Component)](#4-notificationjsx-alert-toast-component)
5. [CustomerList.jsx (Table Component)](#5-customerlistjsx-table-component)
6. [EditCustomerModal.jsx (Form Modal with Read-Only Constraints)](#6-editcustomermodaljsx-form-modal)
7. [App.js (Main State & Controller)](#7-appjs-main-state--controller)
8. [index.js (React DOM Entry Point)](#8-indexjs-react-dom-entry-point)

---

## 1. `package.json` (Dependencies & Scripts)

```json
{
  "name": "customer-frontend",
  "version": "0.1.0",
  "private": true,
  "dependencies": {
    "axios": "^1.6.8",
    "react": "^18.3.1",
    "react-dom": "^18.3.1",
    "react-scripts": "5.0.1"
  },
  "scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build"
  }
}
```
- `"dependencies"`: Third-party JavaScript packages required by the React application:
  - **`axios`**: Promise-based HTTP client used to send HTTP requests to Spring Boot backend.
  - **`react`**: Core React library for building UI components.
  - **`react-dom`**: Package providing DOM-specific methods to render React elements into web page.
  - **`react-scripts`**: Configuration and scripts bundled with Create React App (CRA).
- `"scripts"`: Terminal commands: `npm start` runs development server, `npm run build` bundles app for production deployment.

---

## 2. `apiClient.js` (Axios Instance & Interceptor)

```javascript
import axios from 'axios';
```
- `import axios`: Imports Axios library.

```javascript
const apiClient = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL || 'http://localhost:4500/api',
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000,
});
```
- **`axios.create()`**: Creates a customized Axios instance with default settings:
  - `baseURL`: Automatically prepended to all relative request paths (e.g. `/customers` becomes `http://localhost:4500/api/customers`). Configurable via `REACT_APP_API_BASE_URL` in `.env`.
  - `headers`: Sets default request header `Content-Type: application/json` so Spring Boot knows we are sending JSON data.
  - `timeout: 10000`: Cancels request if backend takes more than 10 seconds to respond.

```javascript
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    let customError = {
      status: 500,
      message: 'Network error or server unreachable. Please check backend status.',
      validationErrors: null,
    };

    if (error.response) {
      const data = error.response.data;
      customError.status = error.response.status;
      customError.message = data.message || `Request failed with status ${error.response.status}`;
      customError.validationErrors = data.validationErrors || null;
    }

    return Promise.reject(customError);
  }
);

export default apiClient;
```
- **`interceptors.response.use()`**: Global error handler middleware that runs automatically whenever an API call returns a response or error.
- `(response) => response`: If request succeeds, passes response directly through.
- `(error) => { ... }`: If backend returns error status (e.g. 400 or 404), extracts error details (`message`, `validationErrors`) and normalizes it into a standardized JavaScript error object.
- `Promise.reject(customError)`: Rejects promise so calling code (`try...catch`) can handle clean error details.
- `export default apiClient`: Exports configured instance for reuse across service files.

---

## 3. `customerService.js` (API Service Layer)

```javascript
import apiClient from '../api/apiClient';

export const getAllCustomers = async () => {
  const response = await apiClient.get('/customers');
  return response.data.data;
};
```
- **`async / await`**: Modern JavaScript syntax for asynchronous code handling.
- **`apiClient.get('/customers')`**: Sends HTTP `GET` request to `http://localhost:4500/api/customers`.
- `response.data.data`: `response.data` accesses Spring Boot's `ApiResponse` object, and `.data` extracts the actual array of `CustomerResponseDto` objects.

```javascript
export const updateCustomer = async (id, updatePayload) => {
  const payload = {
    email: updatePayload.email,
    location: updatePayload.location,
  };
  
  const response = await apiClient.put(`/customers/${id}`, payload);
  return response.data;
};
```
- **Payload Sanitization**: Explicitly constructs `payload` object with **ONLY `email` and `location`**, ensuring no unexpected extra fields are sent.
- **`apiClient.put(...)`**: Sends HTTP `PUT` request to `http://localhost:4500/api/customers/{id}` with JSON body `payload`.

---

## 4. `Notification.jsx` (Alert Toast Component)

```javascript
import React from 'react';

const Notification = ({ notification, onClose }) => {
  if (!notification || !notification.message) return null;
```
- **Component Props**: Destructures `notification` state object and `onClose` callback function from parent props.
- `if (!notification...) return null`: Conditional rendering pattern — returns `null` (renders nothing) if no message exists.

```javascript
  const isError = notification.type === 'error';

  return (
    <div className={`notification-banner ${isError ? 'notification-error' : 'notification-success'}`}>
      <div className="notification-content">
        <span className="notification-icon">{isError ? '⚠️' : '✅'}</span>
        <div>
          <strong className="notification-title">{isError ? 'Error' : 'Success'}</strong>
          <p className="notification-message">{notification.message}</p>
          {notification.validationErrors && (
            <ul className="notification-error-list">
              {Object.entries(notification.validationErrors).map(([field, msg]) => (
                <li key={field}>
                  <strong>{field}:</strong> {msg}
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
      <button className="notification-close-btn" onClick={onClose}>
        &times;
      </button>
    </div>
  );
};
```
- **Dynamic CSS Classes**: Templated string `${isError ? 'notification-error' : 'notification-success'}` applies red or green background based on notification type.
- **`Object.entries(validationErrors).map(...)`**: Converts validation error map object into array of `[field, msg]` pairs to render field validation errors (e.g. `email: Please provide a valid email address`).

---

## 5. `CustomerList.jsx` (Table Component)

```javascript
import React from 'react';

const CustomerList = ({ customers, onSelectCustomer, isLoading, onRefresh }) => {
  if (isLoading) {
    return (
      <div className="card loading-container">
        <div className="spinner"></div>
        <p>Loading customers from Spring Boot API...</p>
      </div>
    );
  }
```
- **Loading State**: Displays CSS spinner and message while fetching data from backend.

```javascript
  return (
    <div className="card">
      <div className="card-header">
        <h2>Customer Directory</h2>
        <button className="btn btn-secondary" onClick={onRefresh}>
          🔄 Refresh
        </button>
      </div>

      <table className="customer-table">
        <thead>
          <tr>
            <th>Customer ID</th>
            <th>Name (Read-Only)</th>
            <th>Email (Editable)</th>
            <th>Location (Editable)</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {customers.map((customer) => (
            <tr key={customer.customerId}>
              <td>#{customer.customerId}</td>
              <td>{customer.name}</td>
              <td>{customer.email}</td>
              <td>📍 {customer.location}</td>
              <td>
                <button
                  className="btn btn-primary btn-sm"
                  onClick={() => onSelectCustomer(customer)}
                >
                  ✏️ Edit
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
```
- **`customers.map((customer) => ...)`**: Iterates over customer array to dynamically generate table rows (`<tr>`).
- **`key={customer.customerId}`**: React optimization attribute required when rendering lists.
- **`onClick={() => onSelectCustomer(customer)}`**: Triggers parent function to set selected customer and open edit modal.

---

## 6. `EditCustomerModal.jsx` (Form Modal with Read-Only Constraints)

```javascript
import React, { useState, useEffect } from 'react';

const EditCustomerModal = ({ customer, isOpen, onClose, onUpdate }) => {
  const [email, setEmail] = useState('');
  const [location, setLocation] = useState('');
  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);
```
- **`useState()`**: React hook creating local component state variables for form input fields (`email`, `location`), validation errors (`errors`), and submission spinner state (`isSubmitting`).

```javascript
  useEffect(() => {
    if (customer) {
      setEmail(customer.email || '');
      setLocation(customer.location || '');
      setErrors({});
    }
  }, [customer]);
```
- **`useEffect()`**: React hook that executes whenever `customer` prop changes. Synchronizes form state with current customer being edited.

```javascript
  const validate = () => {
    const newErrors = {};
    if (!email.trim()) newErrors.email = 'Email is required';
    else if (!/\S+@\S+\.\S+/.test(email)) newErrors.email = 'Please enter a valid email address';
    if (!location.trim()) newErrors.location = 'Location is required';
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };
```
- **Client-Side Validation**: Validates form inputs before making network request. Checks empty values and regex email format.

```javascript
  return (
    <div className="modal-backdrop">
      <div className="modal-container">
        <form onSubmit={handleSubmit}>
          {/* Read-Only Field: Customer ID */}
          <div className="form-group">
            <label>Customer ID (Read-Only)</label>
            <input type="text" value={`#${customer.customerId}`} disabled className="input-disabled" />
          </div>

          {/* Read-Only Field: Name */}
          <div className="form-group">
            <label>Customer Name (Read-Only)</label>
            <input type="text" value={customer.name} disabled className="input-disabled" />
          </div>

          {/* Editable Field: Email */}
          <div className="form-group">
            <label>Email Address *</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          {/* Editable Field: Location */}
          <div className="form-group">
            <label>Location *</label>
            <input
              type="text"
              value={location}
              onChange={(e) => setLocation(e.target.value)}
            />
          </div>

          <div className="modal-footer">
            <button type="button" onClick={onClose}>Cancel</button>
            <button type="submit" disabled={isSubmitting}>
              {isSubmitting ? 'Updating...' : 'Save Changes'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
```
- **`disabled` attribute**: **Strict UI Rule Enforced!** Renders Customer ID and Customer Name in read-only / disabled state (`disabled className="input-disabled"`). User cannot modify name or ID.
- **`onChange={(e) => setEmail(e.target.value)}`**: Two-way data binding updating React state when user types in email input.

---

## 7. `App.js` (Main State & Controller)

```javascript
import React, { useState, useEffect, useCallback } from 'react';
import CustomerList from './components/CustomerList';
import EditCustomerModal from './components/EditCustomerModal';
import Notification from './components/Notification';
import { getAllCustomers, updateCustomer } from './services/customerService';
```
- Top-level parent component managing central state of application.

```javascript
  const fetchCustomers = useCallback(async () => {
    setIsLoading(true);
    try {
      const data = await getAllCustomers();
      setCustomers(data);
    } catch (err) {
      setNotification({ type: 'error', message: err.message });
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchCustomers();
  }, [fetchCustomers]);
```
- **`useCallback()`**: Memoizes `fetchCustomers` function so it isn't recreated on every re-render.
- **`useEffect()`**: Runs `fetchCustomers()` automatically on component mount to select all customers from backend.

```javascript
  const handleUpdateCustomer = async (id, updatePayload) => {
    try {
      const response = await updateCustomer(id, updatePayload);
      setNotification({ type: 'success', message: response.message });
      await fetchCustomers(); // Re-fetch updated list from backend
    } catch (err) {
      setNotification({ type: 'error', message: err.message });
      throw err;
    }
  };
```
- Calls API service to update customer email & location, displays success/error notification toast, and refreshes table data.

---

## 8. `index.js` (React DOM Entry Point)

```javascript
import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
```
- **`ReactDOM.createRoot()`**: React 18 concurrent rendering root API. Mounts React component tree into HTML `<div id="root"></div>`.
- **`<React.StrictMode>`**: Development wrapper that detects potential side effects and deprecated patterns.
