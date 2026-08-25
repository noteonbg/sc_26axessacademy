import axios from 'axios'; // Import Axios HTTP client library

// Create centralized Axios instance with default configuration for contacting Spring Boot backend
const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api', // Prepend base URL to all relative API endpoint paths
  headers: {
    'Content-Type': 'application/json', // Send JSON content-type header with all HTTP requests
  },
  timeout: 10000, // Timeout requests after 10,000 milliseconds (10 seconds)
});

// Configure global response interceptor for unified error handling
apiClient.interceptors.response.use(
  (response) => response, // If HTTP request succeeds, pass response straight through
  (error) => { // If HTTP request encounters an error (e.g. 400 Bad Request, 404 Not Found, or Network Error)
    // Create default fallback error structure
    let customError = {
      status: 500, // Default status 500
      message: 'Network error or server unreachable. Please check backend status.', // Default message
      validationErrors: null, // Default validation errors null
    };

    if (error.response) { // Check if backend server responded with an error HTTP status code
      const data = error.response.data; // Extract backend response JSON body
      customError.status = error.response.status; // Copy backend HTTP status code
      customError.message = data.message || `Request failed with status ${error.response.status}`; // Extract backend message
      customError.validationErrors = data.validationErrors || null; // Extract field validation errors if present
    }

    return Promise.reject(customError); // Reject promise with clean normalized customError object
  }
);

export default apiClient; // Export configured Axios instance for use across application services
