/**
 * ============================================================================
 * apiService.js - Separate API Service Layer using Axios for HTTP Basic Auth
 * ============================================================================
 * 
 * Purpose:
 * This module isolates all HTTP network requests made to the Spring Boot
 * backend API (http://localhost:8080/api). It encapsulates Axios configuration,
 * HTTP Basic Authentication header formatting, and standardized error handling.
 */

import axios from 'axios';

// Base URL of the Spring Boot backend server
const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Creates an Axios instance with base configuration.
 * - baseURL: Pre-appends http://localhost:8080/api to all relative request paths.
 * - timeout: Cancels request if server takes longer than 5000ms (5 seconds).
 * - withCredentials: Set to true to allow sending cross-origin cookies/credentials.
 */
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * Helper function to generate Axios request configuration for HTTP Basic Auth.
 * 
 * HTTP Basic Authentication Mechanics:
 * - Basic Auth requires an HTTP Header: `Authorization: Basic <base64_encoded_credentials>`
 * - Credentials format: `username:password`
 * - `btoa()` is a built-in browser JavaScript function that encodes string to Base64.
 * - Axios also accepts `auth: { username, password }` object option, which automatically
 *   generates the Base64 Authorization header under the hood.
 * 
 * @param {string} username - User's login username (e.g. 'admin' or 'user')
 * @param {string} password - User's login password (e.g. 'admin123' or 'user123')
 * @returns {object} Axios configuration object containing auth credentials
 */
const getAuthConfig = (username, password) => {
  // If no credentials provided (unauthenticated attempt), return empty config object
  if (!username && !password) {
    return {};
  }

  return {
    // Axios built-in 'auth' option automatically sets the 'Authorization: Basic Base64' header
    auth: {
      username: username,
      password: password,
    },
  };
};

/**
 * Executes a GET request to /api/f1 (Restricted to ADMIN role only - User A)
 * 
 * @param {string} username - The username provided in UI
 * @param {string} password - The password provided in UI
 * @returns {Promise<object>} Standardized response object { success, status, data, error }
 */
export const fetchFunctionF1 = async (username, password) => {
  try {
    // axios.get(url, config) makes an asynchronous HTTP GET request
    const response = await apiClient.get('/f1', getAuthConfig(username, password));
    
    // HTTP 200 OK Response
    return {
      success: true,
      status: response.status,
      data: response.data, // Expected string: "f1 at work"
    };
  } catch (error) {
    return handleAxiosError(error);
  }
};

/**
 * Executes a GET request to /api/f2 (Restricted to USER role only - User B)
 * 
 * @param {string} username - The username provided in UI
 * @param {string} password - The password provided in UI
 * @returns {Promise<object>} Standardized response object { success, status, data, error }
 */
export const fetchFunctionF2 = async (username, password) => {
  try {
    const response = await apiClient.get('/f2', getAuthConfig(username, password));
    
    return {
      success: true,
      status: response.status,
      data: response.data, // Expected string: "f2 at work"
    };
  } catch (error) {
    return handleAxiosError(error);
  }
};

/**
 * Executes a GET request to /api/f3 (Public Endpoint - Accessible by ANYONE)
 * Does not require authentication headers.
 * 
 * @returns {Promise<object>} Standardized response object { success, status, data, error }
 */
export const fetchFunctionF3 = async () => {
  try {
    // Public endpoint: No auth config needed
    const response = await apiClient.get('/f3');
    
    return {
      success: true,
      status: response.status,
      data: response.data, // Expected string: "f3 at work"
    };
  } catch (error) {
    return handleAxiosError(error);
  }
};

/**
 * Standardized Error Handler for Axios HTTP errors.
 * Categorizes HTTP Status Codes (401 Unauthorized, 403 Forbidden, 500 Server Error, Network Failure).
 * 
 * @param {object} error - The caught Axios error object
 * @returns {object} Formatted error response object
 */
const handleAxiosError = (error) => {
  if (error.response) {
    // The request was made and the server responded with a status code outside the 2xx range
    const status = error.response.status;
    let message = 'An error occurred';

    if (status === 401) {
      message = '401 Unauthorized: Invalid username or password credentials provided.';
    } else if (status === 403) {
      message = '403 Forbidden: Access denied! You do not have the required role permission to access this endpoint.';
    } else if (status === 404) {
      message = '404 Not Found: Requested endpoint path does not exist.';
    } else {
      message = `HTTP ${status}: ${error.response.statusText || 'Server Error'}`;
    }

    return {
      success: false,
      status: status,
      data: null,
      error: message,
    };
  } else if (error.request) {
    // The request was made but no response was received (e.g. Spring Boot server is down or CORS blocked)
    return {
      success: false,
      status: 0,
      data: null,
      error: 'Network Error: Cannot connect to Spring Boot backend server at http://localhost:8080. Ensure backend is running and CORS is enabled.',
    };
  } else {
    // Something happened in setting up the request that triggered an error
    return {
      success: false,
      status: -1,
      data: null,
      error: error.message,
    };
  }
};
