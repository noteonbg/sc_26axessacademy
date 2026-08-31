import React, { useState } from 'react';
import './App.css';
// Import separate Axios service module for JWT API calls
import {
  loginUser,
  registerUser,
  fetchAccountProfile,
  fetchAccountTransactions,
  fetchSystemStatus,
} from './api/apiService';

/**
 * ============================================================================
 * App.js - React Main Component for JWT Security Demo (Create React App Style)
 * ============================================================================
 * 
 * Syntax & Concepts Explained:
 * 1. useState: React hook to preserve state across component re-renders
 *    (e.g., active JWT token, current user, login inputs, API response payload).
 * 2. JWT Bearer Token Flow:
 *    a. User submits login form -> API returns JWT token string.
 *    b. React stores token in state (`jwtToken`).
 *    c. Subsequent requests attach header: `Authorization: Bearer <jwtToken>`.
 * 3. Conditional Rendering: Renders token status banners and response panels dynamically.
 */
function App() {
  // --------------------------------------------------------------------------
  // 1. REACT STATE HOOKS
  // --------------------------------------------------------------------------

  // Form input state: username
  const [username, setUsername] = useState('john_doe');

  // Form input state: password
  const [password, setPassword] = useState('password123');

  // Registration role input state
  const [registerRole, setRegisterRole] = useState('ROLE_CUSTOMER');

  // Active JWT Token string received from server upon login
  const [jwtToken, setJwtToken] = useState('');

  // Active User Info object { username, role }
  const [currentUser, setCurrentUser] = useState(null);

  // Tab view toggle: 'login' or 'register'
  const [authMode, setAuthMode] = useState('login');

  // API response object returned from backend
  const [apiResult, setApiResult] = useState(null);

  // Loading state boolean during active HTTP calls
  const [loading, setLoading] = useState(false);

  // Feedback message banner for registration/auth actions
  const [authMessage, setAuthMessage] = useState(null);

  // --------------------------------------------------------------------------
  // 2. PRESET SELECTION HANDLERS
  // --------------------------------------------------------------------------

  // Fills credentials for Seed Customer User (john_doe / password123 -> ROLE_CUSTOMER)
  const selectCustomerPreset = () => {
    setUsername('john_doe');
    setPassword('password123');
    setAuthMessage(null);
  };

  // Fills credentials for Seed Admin User (admin / admin123 -> ROLE_ADMIN)
  const selectAdminPreset = () => {
    setUsername('admin');
    setPassword('admin123');
    setAuthMessage(null);
  };

  // Clears active JWT token and user session (Simulates Logout)
  const handleLogout = () => {
    setJwtToken('');
    setCurrentUser(null);
    setApiResult(null);
    setAuthMessage('Logged out successfully. JWT session cleared.');
  };

  // --------------------------------------------------------------------------
  // 3. AUTHENTICATION API ACTION HANDLERS
  // --------------------------------------------------------------------------

  /**
   * Handles Login Form Submission: Calls POST /api/v1/auth/login
   */
  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setAuthMessage(null);
    setApiResult(null);

    const res = await loginUser(username, password);

    if (res.success) {
      // Store JWT token string and user info in React state
      setJwtToken(res.data.token);
      setCurrentUser({
        username: res.data.username,
        role: res.data.role,
      });
      setAuthMessage(`✅ Login successful! JWT token generated for user: ${res.data.username}`);
    } else {
      setAuthMessage(`❌ Login failed: ${res.error}`);
    }

    setLoading(false);
  };

  /**
   * Handles User Registration Submission: Calls POST /api/v1/auth/register
   */
  const handleRegister = async (e) => {
    e.preventDefault();
    setLoading(true);
    setAuthMessage(null);
    setApiResult(null);

    const res = await registerUser(username, password, registerRole);

    if (res.success) {
      setAuthMessage(`✅ ${res.data}`);
      setAuthMode('login'); // Switch to login tab
    } else {
      setAuthMessage(`❌ Registration failed: ${res.error}`);
    }

    setLoading(false);
  };

  // --------------------------------------------------------------------------
  // 4. PROTECTED API ACTION HANDLERS (Invoking Axios with Bearer Header)
  // --------------------------------------------------------------------------

  /**
   * Calls GET /api/v1/account/profile with active JWT token
   */
  const handleCallAccountProfile = async (overrideToken = null) => {
    setLoading(true);
    setApiResult(null);

    // Use token from parameter if passed (e.g. testing invalid token), otherwise state token
    const tokenToUse = overrideToken !== null ? overrideToken : jwtToken;
    const res = await fetchAccountProfile(tokenToUse);

    setApiResult({
      endpoint: '/api/v1/account/profile (CUSTOMER/ADMIN)',
      ...res,
    });
    setLoading(false);
  };

  /**
   * Calls GET /api/v1/account/transactions with active JWT token
   */
  const handleCallAccountTransactions = async () => {
    setLoading(true);
    setApiResult(null);

    const res = await fetchAccountTransactions(jwtToken);

    setApiResult({
      endpoint: '/api/v1/account/transactions (CUSTOMER/ADMIN)',
      ...res,
    });
    setLoading(false);
  };

  /**
   * Calls GET /api/v1/admin/system-status with active JWT token
   */
  const handleCallSystemStatus = async () => {
    setLoading(true);
    setApiResult(null);

    const res = await fetchSystemStatus(jwtToken);

    setApiResult({
      endpoint: '/api/v1/admin/system-status (ADMIN Only)',
      ...res,
    });
    setLoading(false);
  };

  // --------------------------------------------------------------------------
  // 5. UI RENDER (JSX)
  // --------------------------------------------------------------------------
  return (
    <div className="App">
      <header className="App-header">
        <h1>🏦 Standard Chartered Banking JWT Security Demo</h1>
        <p>Stateless JWT Bearer Token Authentication (React CRA + Spring Boot 3)</p>
      </header>

      <main className="App-main">
        {/* PANEL 1: AUTHENTICATION & TOKEN GENERATION */}
        <div className="card">
          <div className="auth-header-tabs">
            <button
              type="button"
              className={`tab-btn ${authMode === 'login' ? 'active-tab' : ''}`}
              onClick={() => setAuthMode('login')}
            >
              🔑 Login & Get JWT Token
            </button>
            <button
              type="button"
              className={`tab-btn ${authMode === 'register' ? 'active-tab' : ''}`}
              onClick={() => setAuthMode('register')}
            >
              📝 Register New User
            </button>
          </div>

          {authMode === 'login' ? (
            <div className="tab-content">
              <p className="subtitle">
                Select a preset account or type credentials to request a JWT token from <code>/api/v1/auth/login</code>:
              </p>

              <div className="preset-container">
                <button
                  type="button"
                  className={`btn btn-preset ${username === 'john_doe' ? 'active-preset' : ''}`}
                  onClick={selectCustomerPreset}
                >
                  👤 <strong>Customer Account</strong><br />
                  <small>john_doe / password123 (ROLE_CUSTOMER)</small>
                </button>

                <button
                  type="button"
                  className={`btn btn-preset ${username === 'admin' ? 'active-preset' : ''}`}
                  onClick={selectAdminPreset}
                >
                  👑 <strong>Admin Account</strong><br />
                  <small>admin / admin123 (ROLE_ADMIN)</small>
                </button>
              </div>

              <form onSubmit={handleLogin} className="form-grid">
                <div className="form-field">
                  <label htmlFor="login-username">Username:</label>
                  <input
                    id="login-username"
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </div>

                <div className="form-field">
                  <label htmlFor="login-password">Password:</label>
                  <input
                    id="login-password"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </div>

                <div className="form-actions">
                  <button type="submit" className="btn btn-primary" disabled={loading}>
                    {loading ? 'Authenticating...' : 'Login & Obtain JWT Token'}
                  </button>
                </div>
              </form>
            </div>
          ) : (
            <div className="tab-content">
              <p className="subtitle">
                Register a new user entity into database via <code>/api/v1/auth/register</code>:
              </p>

              <form onSubmit={handleRegister} className="form-grid">
                <div className="form-field">
                  <label htmlFor="reg-username">Username:</label>
                  <input
                    id="reg-username"
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </div>

                <div className="form-field">
                  <label htmlFor="reg-password">Password:</label>
                  <input
                    id="reg-password"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </div>

                <div className="form-field">
                  <label htmlFor="reg-role">Role:</label>
                  <select
                    id="reg-role"
                    value={registerRole}
                    onChange={(e) => setRegisterRole(e.target.value)}
                  >
                    <option value="ROLE_CUSTOMER">ROLE_CUSTOMER (Banking Customer)</option>
                    <option value="ROLE_ADMIN">ROLE_ADMIN (Bank System Administrator)</option>
                  </select>
                </div>

                <div className="form-actions">
                  <button type="submit" className="btn btn-success" disabled={loading}>
                    {loading ? 'Registering...' : 'Register User'}
                  </button>
                </div>
              </form>
            </div>
          )}

          {authMessage && (
            <div className={`auth-banner ${authMessage.startsWith('✅') ? 'banner-success' : 'banner-error'}`}>
              {authMessage}
            </div>
          )}
        </div>

        {/* PANEL 2: ACTIVE JWT TOKEN SESSION STATUS */}
        <div className={`card session-card ${jwtToken ? 'session-active' : 'session-inactive'}`}>
          <div className="session-header">
            <h2>Active JWT Token Session Status</h2>
            {jwtToken && (
              <button type="button" className="btn btn-sm btn-logout" onClick={handleLogout}>
                🚪 Logout / Clear Token
              </button>
            )}
          </div>

          {jwtToken ? (
            <div className="session-info">
              <div className="session-row">
                <strong>Authenticated User:</strong> <span>{currentUser?.username}</span>
              </div>
              <div className="session-row">
                <strong>Assigned Role Claim:</strong>{' '}
                <span className={`role-badge ${currentUser?.role === 'ROLE_ADMIN' ? 'role-admin' : 'role-customer'}`}>
                  {currentUser?.role}
                </span>
              </div>
              <div className="session-row token-preview-row">
                <strong>Raw JWT Bearer Token:</strong>
                <code className="token-code">{jwtToken}</code>
              </div>
            </div>
          ) : (
            <p className="no-session-text">
              ⚠️ No active JWT session. You are currently unauthenticated. Requests to protected endpoints will receive <code>401 Unauthorized</code>.
            </p>
          )}
        </div>

        {/* PANEL 3: PROTECTED ENDPOINTS TESTING (AXIOS CALLS) */}
        <div className="card">
          <h2>Execute Protected Banking API Calls</h2>
          <p className="subtitle">
            These actions invoke Axios calls in <code>src/api/apiService.js</code>, attaching the header <code>Authorization: Bearer &lt;jwtToken&gt;</code>.
          </p>

          <div className="button-group">
            <button
              type="button"
              className="btn btn-api btn-cust"
              onClick={() => handleCallAccountProfile()}
              disabled={loading}
            >
              Get Account Profile (/api/v1/account/profile)
            </button>

            <button
              type="button"
              className="btn btn-api btn-cust"
              onClick={handleCallAccountTransactions}
              disabled={loading}
            >
              Get Transactions (/api/v1/account/transactions)
            </button>

            <button
              type="button"
              className="btn btn-api btn-admin-api"
              onClick={handleCallSystemStatus}
              disabled={loading}
            >
              Get System Status (/api/v1/admin/system-status)
            </button>

            <button
              type="button"
              className="btn btn-api btn-warning-api"
              onClick={() => handleCallAccountProfile('')}
              disabled={loading}
            >
              Test NO Token (Simulate 401)
            </button>

            <button
              type="button"
              className="btn btn-api btn-warning-api"
              onClick={() => handleCallAccountProfile('INVALID_CORRUPTED_JWT_TOKEN')}
              disabled={loading}
            >
              Test Invalid Token (Simulate 401)
            </button>
          </div>
        </div>

        {/* PANEL 4: RESPONSE DISPLAY PANEL */}
        {apiResult && (
          <div className={`card result-card ${apiResult.success ? 'result-success' : 'result-error'}`}>
            <h2>Server Response Outcome</h2>

            <div className="result-row">
              <strong>Target Endpoint:</strong> <span>{apiResult.endpoint}</span>
            </div>

            <div className="result-row">
              <strong>HTTP Status Code:</strong>{' '}
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
                <strong>Security Policy / Error Detail:</strong>
                <p>{apiResult.error}</p>
              </div>
            )}
          </div>
        )}

        {/* PANEL 5: JWT AUTHORIZATION MATRIX TABLE */}
        <div className="card">
          <h2>Banking JWT Authorization Security Matrix</h2>
          <table className="matrix-table">
            <thead>
              <tr>
                <th>User / Role</th>
                <th>/account/profile</th>
                <th>/account/transactions</th>
                <th>/admin/system-status</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>
                  <strong>john_doe</strong><br />
                  <span className="role-badge role-customer">ROLE_CUSTOMER</span>
                </td>
                <td className="allowed">✅ 200 OK</td>
                <td className="allowed">✅ 200 OK</td>
                <td className="denied">❌ 403 Forbidden</td>
              </tr>
              <tr>
                <td>
                  <strong>admin</strong><br />
                  <span className="role-badge role-admin">ROLE_ADMIN</span>
                </td>
                <td className="allowed">✅ 200 OK</td>
                <td className="allowed">✅ 200 OK</td>
                <td className="allowed">✅ 200 OK</td>
              </tr>
              <tr>
                <td>
                  <strong>Unauthenticated / Invalid JWT</strong><br />
                  <span className="role-badge role-anon">NO TOKEN</span>
                </td>
                <td className="unauth">❌ 401 Unauthorized</td>
                <td className="unauth">❌ 401 Unauthorized</td>
                <td className="unauth">❌ 401 Unauthorized</td>
              </tr>
            </tbody>
          </table>
        </div>
      </main>
    </div>
  );
}

export default App;
