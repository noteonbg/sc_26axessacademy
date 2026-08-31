/**
 * ============================================================================
 * apiService.js - Separate Axios Service Module for JWT Authentication
 * ============================================================================
 * 
 * Purpose:
 * Decouples all HTTP API interactions with the Spring Boot JWT Backend.
 * Handles user authentication (login/register) and injects JWT Bearer tokens
 * into HTTP headers (`Authorization: Bearer <jwt_token>`) for protected endpoints.
 */

import axios from 'axios';

// Base URL pointing to Spring Boot JWT Backend API v1
const API_BASE_URL = 'http://localhost:8080/api/v1';

/**
 * Axios instance pre-configured with base URL and timeout limits.
 */
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * Helper function to create HTTP Header configuration containing JWT Bearer Token.
 * 
 * JWT HTTP Header Mechanics:
 * - Standard RFC 6750 specification requires header key: `Authorization`
 * - Value format: `Bearer <jwt_access_token>`
 * 
 * @param {string} token - The JWT string received upon successful login
 * @returns {object} Axios request configuration object containing Authorization header
 */
const getBearerAuthConfig = (token) => {
  if (!token) {
    return {};
  }
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

/**
 * 1. LOGIN API CALL
 * Sends POST request to /api/v1/auth/login with credentials payload.
 * Returns JWT token, username, and assigned user role upon HTTP 200 OK.
 * 
 * @param {string} username - User login username
 * @param {string} password - User login password
 * @returns {Promise<object>} Response object { success, data: { token, username, role }, status, error }
 */
export const loginUser = async (username, password) => {
  try {
    const payload = { username, password };
    const response = await apiClient.post('/auth/login', payload);

    return {
      success: true,
      status: response.status,
      data: response.data, // Expected object: { token: "...", username: "...", role: "..." }
    };
  } catch (error) {
    return handleAxiosError(error);
  }
};

/**
 * 2. REGISTER NEW USER API CALL
 * Sends POST request to /api/v1/auth/register with user details payload.
 * 
 * @param {string} username - New account username
 * @param {string} password - New account password
 * @param {string} role - Desired role ('ROLE_CUSTOMER' or 'ROLE_ADMIN')
 * @returns {Promise<object>} Response object { success, data, status, error }
 */
export const registerUser = async (username, password, role) => {
  try {
    const payload = { username, password, role };
    const response = await apiClient.post('/auth/register', payload);

    return {
      success: true,
      status: response.status,
      data: response.data, // String message: "User registered successfully!..."
    };
  } catch (error) {
    return handleAxiosError(error);
  }
};

/**
 * 3. GET CUSTOMER ACCOUNT PROFILE (Protected Endpoint)
 * Sends GET request to /api/v1/account/profile with JWT Bearer header.
 * Allowed Roles: ROLE_CUSTOMER, ROLE_ADMIN
 * 
 * @param {string} token - Valid JWT Bearer token
 * @returns {Promise<object>} Response object
 */
export const fetchAccountProfile = async (token) => {
  try {
    const response = await apiClient.get('/account/profile', getBearerAuthConfig(token));

    return {
      success: true,
      status: response.status,
      data: response.data,
    };
  } catch (error) {
    return handleAxiosError(error);
  }
};

/**
 * 4. GET CUSTOMER RECENT TRANSACTIONS (Protected Endpoint)
 * Sends GET request to /api/v1/account/transactions with JWT Bearer header.
 * Allowed Roles: ROLE_CUSTOMER, ROLE_ADMIN
 * 
 * @param {string} token - Valid JWT Bearer token
 * @returns {Promise<object>} Response object
 */
export const fetchAccountTransactions = async (token) => {
  try {
    const response = await apiClient.get('/account/transactions', getBearerAuthConfig(token));

    return {
      success: true,
      status: response.status,
      data: response.data,
    };
  } catch (error) {
    return handleAxiosError(error);
  }
};

/**
 * 5. GET SYSTEM ADMIN STATUS (Protected Admin Endpoint)
 * Sends GET request to /api/v1/admin/system-status with JWT Bearer header.
 * Allowed Roles: ROLE_ADMIN ONLY
 * 
 * @param {string} token - Valid JWT Bearer token
 * @returns {Promise<object>} Response object
 */
export const fetchSystemStatus = async (token) => {
  try {
    const response = await apiClient.get('/admin/system-status', getBearerAuthConfig(token));

    return {
      success: true,
      status: response.status,
      data: response.data,
    };
  } catch (error) {
    return handleAxiosError(error);
  }
};

/**
 * Standardized Error Handler for Axios HTTP requests.
 * Parses HTTP status codes and extracts readable server error messages.
 * 
 * @param {object} error - Caught Axios error
 * @returns {object} Formatted error response object
 */
const handleAxiosError = (error) => {
  if (error.response) {
    const status = error.response.status;
    let message = 'An error occurred';

    if (typeof error.response.data === 'string') {
      message = error.response.data;
    } else if (error.response.data && error.response.data.message) {
      message = error.response.data.message;
    } else if (status === 401) {
      message = '401 Unauthorized: Invalid, expired, or missing JWT Bearer token.';
    } else if (status === 403) {
      message = '403 Forbidden: Access Denied! Your user role does not have permission for this endpoint.';
    } else if (status === 400) {
      message = `400 Bad Request: ${error.response.data || 'Invalid payload parameters.'}`;
    }

    return {
      success: false,
      status: status,
      data: null,
      error: message,
    };
  } else if (error.request) {
    return {
      success: false,
      status: 0,
      data: null,
      error: 'Network Error: Unable to connect to Spring Boot JWT backend at http://localhost:8080. Ensure backend is running and CORS is enabled.',
    };
  } else {
    return {
      success: false,
      status: -1,
      data: null,
      error: error.message,
    };
  }
};
