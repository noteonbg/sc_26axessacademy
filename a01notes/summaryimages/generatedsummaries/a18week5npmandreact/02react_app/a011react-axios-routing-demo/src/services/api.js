import axios from 'axios';

// Create an Axios instance with base configuration
//port on which function is defined... 
const API = axios.create({
  baseURL: 'http://localhost:5000/api',
  headers: {
    'Content-Type': 'application/json'
  }
});

// GET: Fetch all items
export const getItems = () => API.get('/items');

// POST: Add new item
export const createItem = (itemData) => API.post('/items', itemData);

// PUT: Update existing item by ID
export const updateItem = (id, itemData) => API.put(`/items/${id}`, itemData);

// DELETE: Remove item by ID
export const deleteItem = (id) => API.delete(`/items/${id}`);

// DEMO: Trigger server error responses (400, 404, 500)
export const triggerDemoError = (code) => API.get(`/test-error/${code}`);

export default API;
