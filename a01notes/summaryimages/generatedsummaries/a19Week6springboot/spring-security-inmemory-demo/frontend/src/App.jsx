import React, { useState } from 'react';
// Import dedicated API service module containing Axios call logic
import { fetchFunctionF1, fetchFunctionF2, fetchFunctionF3 } from './api/apiService';

/**
 * ============================================================================
 * App.jsx - React Component demonstrating Spring Security In-Memory Auth
 * ============================================================================
 * 
 * Syntax & Concepts Explained:
 * 1. useState Hook: React hook for managing local component state variables
 *    (e.g., username, password, response outcome, loading state).
 * 2. Event Handlers: Functions triggered by user actions (button clicks, input edits).
 * 3. Conditional Rendering: Showing loading spinner, success messages, or error banners
 *    dynamically based on response state.
 */
function App() {
  // --------------------------------------------------------------------------
  // 1. REACT STATE DEFINITIONS
  // --------------------------------------------------------------------------

  // State to hold the current username typed or selected by user
  const [username, setUsername] = useState('admin');

  // State to hold the current password typed or selected by user
  const [password, setPassword] = useState('admin123');

  // State to store the result returned from the Axios API call
  const [result, setResult] = useState(null);

  // State to track if an HTTP request is actively in progress
  const [loading, setLoading] = useState(false);

  // --------------------------------------------------------------------------
  // 2. HELPER PRESET HANDLERS
  // --------------------------------------------------------------------------

  // Pre-fills form credentials with Admin User (User A: admin / admin123 -> Role: ADMIN)
  const selectAdminUser = () => {
    setUsername('admin');
    setPassword('admin123');
    setResult(null);
  };

  // Pre-fills form credentials with Normal User (User B: user / user123 -> Role: USER)
  const selectNormalUser = () => {
    setUsername('user');
    setPassword('user123');
    setResult(null);
  };

  // Clears credentials to simulate Unauthenticated / Anonymous user requests
  const selectAnonymousUser = () => {
    setUsername('');
    setPassword('');
    setResult(null);
  };

  // --------------------------------------------------------------------------
  // 3. API CALL HANDLER FUNCTIONS
  // --------------------------------------------------------------------------

  /**
   * Triggers Axios call to GET /api/f1 (Restricted to ADMIN role only)
   */
  const handleCallF1 = async () => {
    setLoading(true);
    setResult(null);

    // Invoke dedicated Axios call from apiService.js
    const res = await fetchFunctionF1(username, password);

    setResult({
      endpoint: '/api/f1 (ADMIN Only)',
      ...res
    });
    setLoading(false);
  };

  /**
   * Triggers Axios call to GET /api/f2 (Restricted to USER role only)
   */
  const handleCallF2 = async () => {
    setLoading(true);
    setResult(null);

    // Invoke dedicated Axios call from apiService.js
    const res = await fetchFunctionF2(username, password);

    setResult({
      endpoint: '/api/f2 (USER Only)',
      ...res
    });
    setLoading(false);
  };

  /**
   * Triggers Axios call to GET /api/f3 (Public Endpoint - Anyone)
   */
  const handleCallF3 = async () => {
    setLoading(true);
    setResult(null);

    // Invoke dedicated Axios call from apiService.js
    const res = await fetchFunctionF3();

    setResult({
      endpoint: '/api/f3 (Public)',
      ...res
    });
    setLoading(false);
  };

  // --------------------------------------------------------------------------
  // 4. UI RENDER (JSX)
  // --------------------------------------------------------------------------
  return (
    <div className="container">
      <header className="header">
        <h1>🔒 Spring Security In-Memory Auth + React Demo</h1>
        <p>Demonstrating HTTP Basic Authentication and Role-Based Access Control (RBAC)</p>
      </header>

      {/* SECTION 1: CREDENTIAL SELECTOR & FORM */}
      <section className="card">
        <h2>1. Select or Enter Credentials</h2>
        <p className="description">
          Choose a pre-configured user profile or enter custom credentials to test Spring Security rules.
        </p>

        <div className="preset-buttons">
          <button 
            type="button" 
            className={`btn btn-preset ${username === 'admin' ? 'active' : ''}`}
            onClick={selectAdminUser}
          >
            👑 Admin User (User A)<br/>
            <small>admin / admin123 (ROLE_ADMIN)</small>
          </button>

          <button 
            type="button" 
            className={`btn btn-preset ${username === 'user' ? 'active' : ''}`}
            onClick={selectNormalUser}
          >
            👤 Normal User (User B)<br/>
            <small>user / user123 (ROLE_USER)</small>
          </button>

          <button 
            type="button" 
            className={`btn btn-preset ${username === '' ? 'active' : ''}`}
            onClick={selectAnonymousUser}
          >
            🔓 Anonymous User<br/>
            <small>(No credentials / Empty)</small>
          </button>
        </div>

        <div className="form-group-grid">
          <div className="form-group">
            <label htmlFor="username-input">Username:</label>
            <input
              id="username-input"
              type="text"
              className="input-field"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="e.g. admin or user"
            />
          </div>

          <div className="form-group">
            <label htmlFor="password-input">Password:</label>
            <input
              id="password-input"
              type="password"
              className="input-field"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="e.g. admin123 or user123"
            />
          </div>
        </div>
      </section>

      {/* SECTION 2: ENDPOINT TEST ACTION BUTTONS */}
      <section className="card">
        <h2>2. Test Backend Endpoints (Axios Calls)</h2>
        <p className="description">
          Click an endpoint button below. The dedicated <code>apiService.js</code> file will send an Axios GET request with HTTP Basic Auth headers.
        </p>

        <div className="action-buttons">
          <button 
            type="button" 
            className="btn btn-primary"
            onClick={handleCallF1}
            disabled={loading}
          >
            {loading ? 'Calling...' : 'Call /api/f1 (ADMIN Role Only)'}
          </button>

          <button 
            type="button" 
            className="btn btn-secondary"
            onClick={handleCallF2}
            disabled={loading}
          >
            {loading ? 'Calling...' : 'Call /api/f2 (USER Role Only)'}
          </button>

          <button 
            type="button" 
            className="btn btn-success"
            onClick={handleCallF3}
            disabled={loading}
          >
            {loading ? 'Calling...' : 'Call /api/f3 (Public Endpoint)'}
          </button>
        </div>
      </section>

      {/* SECTION 3: AXIOS RESPONSE DISPLAY PANEL */}
      {result && (
        <section className={`card result-card ${result.success ? 'success-border' : 'error-border'}`}>
          <h2>3. Axios Response Result</h2>
          
          <div className="result-detail">
            <strong>Target Endpoint:</strong> <span>{result.endpoint}</span>
          </div>

          <div className="result-detail">
            <strong>HTTP Status Code:</strong>{' '}
            <span className={`badge ${result.success ? 'badge-success' : 'badge-error'}`}>
              {result.status === 0 ? 'NETWORK_ERROR' : result.status}
            </span>
          </div>

          {result.success ? (
            <div className="response-box response-success">
              <strong>Server Response Payload:</strong>
              <pre>{result.data}</pre>
            </div>
          ) : (
            <div className="response-box response-error">
              <strong>Security Policy / Error Outcome:</strong>
              <p>{result.error}</p>
            </div>
          )}
        </section>
      )}

      {/* SECTION 4: SECURITY AUTHORIZATION MATRIX REFERENCE */}
      <section className="card">
        <h2>4. Expected Security Authorization Matrix</h2>
        <table className="matrix-table">
          <thead>
            <tr>
              <th>User & Role</th>
              <th>/api/f1 (ADMIN Only)</th>
              <th>/api/f2 (USER Only)</th>
              <th>/api/f3 (Public)</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td><strong>User A (admin / admin123)</strong><br/><small>ROLE_ADMIN</small></td>
              <td className="status-allowed">✅ 200 OK ("f1 at work")</td>
              <td className="status-denied">❌ 403 Forbidden</td>
              <td className="status-allowed">✅ 200 OK ("f3 at work")</td>
            </tr>
            <tr>
              <td><strong>User B (user / user123)</strong><br/><small>ROLE_USER</small></td>
              <td className="status-denied">❌ 403 Forbidden</td>
              <td className="status-allowed">✅ 200 OK ("f2 at work")</td>
              <td className="status-allowed">✅ 200 OK ("f3 at work")</td>
            </tr>
            <tr>
              <td><strong>Anonymous / Bad Password</strong></td>
              <td className="status-denied">❌ 401 Unauthorized</td>
              <td className="status-denied">❌ 401 Unauthorized</td>
              <td className="status-allowed">✅ 200 OK ("f3 at work")</td>
            </tr>
          </tbody>
        </table>
      </section>
    </div>
  );
}

export default App;
