import React, { useState } from 'react';
import './App.css';
// Import separate Axios API service layer
import { fetchFunctionF1, fetchFunctionF2, fetchFunctionF3 } from './api/apiService';

/**
 * ============================================================================
 * App.js - Main React Component (Create React App Style)
 * ============================================================================
 * 
 * Syntax & Concepts Explained:
 * - import React, { useState }: Imports React library and the useState Hook.
 * - useState(initialValue): Declares state variable and updater function.
 *   Example: const [username, setUsername] = useState('admin');
 * - Event Handlers: Functions called when user interacts (onClick, onChange).
 * - JSX: JavaScript XML syntax allowing HTML-like markup inside JS.
 */
function App() {
  // --------------------------------------------------------------------------
  // 1. COMPONENT STATE MANAGEMENT
  // --------------------------------------------------------------------------

  // Username input state (defaults to 'admin' - User A)
  const [username, setUsername] = useState('admin');

  // Password input state (defaults to 'admin123')
  const [password, setPassword] = useState('admin123');

  // Stores the object returned by Axios API service call
  const [apiResult, setApiResult] = useState(null);

  // Indicates whether an HTTP request is currently pending
  const [loading, setLoading] = useState(false);

  // --------------------------------------------------------------------------
  // 2. PRESET CREDENTIAL SELECTION HANDLERS
  // --------------------------------------------------------------------------

  // Pre-fills form with Admin User credentials (User A -> ROLE_ADMIN)
  const handleSelectAdmin = () => {
    setUsername('admin');
    setPassword('admin123');
    setApiResult(null);
  };

  // Pre-fills form with Normal User credentials (User B -> ROLE_USER)
  const handleSelectNormalUser = () => {
    setUsername('user');
    setPassword('user123');
    setApiResult(null);
  };

  // Clears credentials to test Unauthenticated / Anonymous requests
  const handleSelectAnonymous = () => {
    setUsername('');
    setPassword('');
    setApiResult(null);
  };

  // --------------------------------------------------------------------------
  // 3. API ACTION HANDLERS (Invoking Axios from apiService.js)
  // --------------------------------------------------------------------------

  /**
   * Calls GET /api/f1 (Restricted to ADMIN role only)
   */
  const handleCallF1 = async () => {
    setLoading(true);
    setApiResult(null);

    // Call separate Axios service module
    const response = await fetchFunctionF1(username, password);

    setApiResult({
      endpoint: '/api/f1 (ADMIN Only)',
      ...response
    });
    setLoading(false);
  };

  /**
   * Calls GET /api/f2 (Restricted to USER role only)
   */
  const handleCallF2 = async () => {
    setLoading(true);
    setApiResult(null);

    // Call separate Axios service module
    const response = await fetchFunctionF2(username, password);

    setApiResult({
      endpoint: '/api/f2 (USER Only)',
      ...response
    });
    setLoading(false);
  };

  /**
   * Calls GET /api/f3 (Public Endpoint - Accessible by anyone)
   */
  const handleCallF3 = async () => {
    setLoading(true);
    setApiResult(null);

    // Call separate Axios service module
    const response = await fetchFunctionF3();

    setApiResult({
      endpoint: '/api/f3 (Public)',
      ...response
    });
    setLoading(false);
  };

  // --------------------------------------------------------------------------
  // 4. UI COMPONENT RENDERING (JSX Markup)
  // --------------------------------------------------------------------------
  return (
    <div className="App">
      <header className="App-header">
        <h1>🔐 Spring Security In-Memory Authentication</h1>
        <p>Create React App (CRA) Style Frontend + Spring Boot Backend Demo</p>
      </header>

      <main className="App-main">
        {/* PANEL 1: USER CREDENTIALS SELECTION */}
        <div className="card">
          <h2>Step 1: Choose or Enter Credentials</h2>
          <p className="subtitle">
            Spring Security uses HTTP Basic Auth. Select a role profile below or type custom credentials:
          </p>

          <div className="preset-container">
            <button 
              type="button" 
              className={`btn btn-preset ${username === 'admin' ? 'active-preset' : ''}`}
              onClick={handleSelectAdmin}
            >
              👑 <strong>User A (Admin)</strong><br />
              <small>admin / admin123 (ROLE_ADMIN)</small>
            </button>

            <button 
              type="button" 
              className={`btn btn-preset ${username === 'user' ? 'active-preset' : ''}`}
              onClick={handleSelectNormalUser}
            >
              👤 <strong>User B (Normal User)</strong><br />
              <small>user / user123 (ROLE_USER)</small>
            </button>

            <button 
              type="button" 
              className={`btn btn-preset ${username === '' ? 'active-preset' : ''}`}
              onClick={handleSelectAnonymous}
            >
              🔓 <strong>Anonymous User</strong><br />
              <small>(No Username / Password)</small>
            </button>
          </div>

          <div className="form-grid">
            <div className="form-field">
              <label htmlFor="username">Username:</label>
              <input
                id="username"
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="Enter username"
              />
            </div>

            <div className="form-field">
              <label htmlFor="password">Password:</label>
              <input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Enter password"
              />
            </div>
          </div>
        </div>

        {/* PANEL 2: ENDPOINT CALL ACTIONS */}
        <div className="card">
          <h2>Step 2: Execute Axios API Calls</h2>
          <p className="subtitle">
            These buttons trigger Axios HTTP GET calls defined in <code>src/api/apiService.js</code>.
          </p>

          <div className="button-group">
            <button 
              type="button" 
              className="btn btn-admin"
              onClick={handleCallF1}
              disabled={loading}
            >
              {loading ? 'Sending...' : 'Test /api/f1 (ADMIN Only)'}
            </button>

            <button 
              type="button" 
              className="btn btn-user"
              onClick={handleCallF2}
              disabled={loading}
            >
              {loading ? 'Sending...' : 'Test /api/f2 (USER Only)'}
            </button>

            <button 
              type="button" 
              className="btn btn-public"
              onClick={handleCallF3}
              disabled={loading}
            >
              {loading ? 'Sending...' : 'Test /api/f3 (Public)'}
            </button>
          </div>
        </div>

        {/* PANEL 3: RESPONSE DISPLAY */}
        {apiResult && (
          <div className={`card result-card ${apiResult.success ? 'result-success' : 'result-error'}`}>
            <h2>Step 3: Server Response Result</h2>

            <div className="result-row">
              <strong>Target Endpoint:</strong> <span>{apiResult.endpoint}</span>
            </div>

            <div className="result-row">
              <strong>HTTP Status:</strong>{' '}
              <span className={`status-badge ${apiResult.success ? 'badge-ok' : 'badge-fail'}`}>
                {apiResult.status === 0 ? 'NETWORK ERROR' : `HTTP ${apiResult.status}`}
              </span>
            </div>

            {apiResult.success ? (
              <div className="payload-box payload-success">
                <strong>Response Data (200 OK):</strong>
                <pre>{apiResult.data}</pre>
              </div>
            ) : (
              <div className="payload-box payload-error">
                <strong>Security Error Detail:</strong>
                <p>{apiResult.error}</p>
              </div>
            )}
          </div>
        )}

        {/* PANEL 4: EXPECTED SECURITY AUTHORIZATION MATRIX */}
        <div className="card">
          <h2>Spring Security Authorization Matrix</h2>
          <table className="matrix-table">
            <thead>
              <tr>
                <th>User / Role</th>
                <th>/api/f1 (ADMIN Only)</th>
                <th>/api/f2 (USER Only)</th>
                <th>/api/f3 (Public)</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>
                  <strong>User A (admin)</strong><br />
                  <span className="role-tag admin-tag">ROLE_ADMIN</span>
                </td>
                <td className="allowed">✅ 200 OK ("f1 at work")</td>
                <td className="denied">❌ 403 Forbidden</td>
                <td className="allowed">✅ 200 OK ("f3 at work")</td>
              </tr>
              <tr>
                <td>
                  <strong>User B (user)</strong><br />
                  <span className="role-tag user-tag">ROLE_USER</span>
                </td>
                <td className="denied">❌ 403 Forbidden</td>
                <td className="allowed">✅ 200 OK ("f2 at work")</td>
                <td className="allowed">✅ 200 OK ("f3 at work")</td>
              </tr>
              <tr>
                <td>
                  <strong>Anonymous / Invalid</strong><br />
                  <span className="role-tag anon-tag">NO ROLE</span>
                </td>
                <td className="unauth">❌ 401 Unauthorized</td>
                <td className="unauth">❌ 401 Unauthorized</td>
                <td className="allowed">✅ 200 OK ("f3 at work")</td>
              </tr>
            </tbody>
          </table>
        </div>
      </main>
    </div>
  );
}

export default App;
