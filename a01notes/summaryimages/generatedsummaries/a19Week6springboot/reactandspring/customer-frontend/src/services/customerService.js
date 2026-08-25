import apiClient from '../api/apiClient'; // Import centralized Axios client instance

/**
 * Service function to select/fetch all customers from Spring Boot backend.
 * Calls GET /api/customers endpoint.
 */
export const getAllCustomers = async () => {
  const response = await apiClient.get('/customers'); // Send HTTP GET request to /customers
  return response.data.data; // Extract array of customers from backend ApiResponse.data
};

/**
 * Service function to fetch single customer details by ID.
 * Calls GET /api/customers/{id} endpoint.
 */
export const getCustomerById = async (id) => {
  const response = await apiClient.get(`/customers/${id}`); // Send HTTP GET request with customer ID path parameter
  return response.data.data; // Extract customer object from backend ApiResponse.data
};

/**
 * Service function to update customer (STRICT RULE: Only email and location are sent).
 * Calls PUT /api/customers/{id} endpoint.
 * @param {number|string} id Customer ID
 * @param {Object} updatePayload Object containing updated email and location
 */
export const updateCustomer = async (id, updatePayload) => {
  // Construct payload strictly containing ONLY email and location
  const payload = {
    email: updatePayload.email, // Include updated email
    location: updatePayload.location, // Include updated location
  };
  
  const response = await apiClient.put(`/customers/${id}`, payload); // Send HTTP PUT request with JSON payload
  return response.data; // Return complete backend ApiResponse wrapper
};
