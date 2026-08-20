import React, { useState, useEffect } from 'react';
import { getItems, createItem, updateItem, deleteItem, triggerDemoError } from '../services/api';

function ItemsManager() {
  const [items, setItems] = useState([]);
  const [formData, setFormData] = useState({ name: '', role: '' });
  const [editId, setEditId] = useState(null);
  const [loading, setLoading] = useState(false);
  const [responseLog, setResponseLog] = useState(null);

  // Initial GET call on component mount
  useEffect(() => {
    console.log("ItemsManager mounted, fetching initial items...");
    fetchItems();
  }, []);

  // 1. GET Request
  const fetchItems = async () => {
    setLoading(true);
    try {
      const res = await getItems();
      setItems(res.data);
      logResponse('GET', res.status, res.statusText, res.data, false);
    } catch (err) {
      handleAxiosError('GET', err);
    } finally {
      setLoading(false);
    }
  };

  // Handle Form Inputs
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // 2. POST / PUT Submit Handler
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      if (editId === null) {
        // POST: Create New Item (Expect 201 Created)
        const res = await createItem(formData);
        logResponse('POST', res.status, res.statusText, res.data, false);
      } else {
        // PUT: Update Existing Item (Expect 200 OK)
        const res = await updateItem(editId, formData);
        logResponse('PUT', res.status, res.statusText, res.data, false);
        setEditId(null);
      }
      setFormData({ name: '', role: '' });
      fetchItems();
    } catch (err) {
      handleAxiosError(editId === null ? 'POST' : 'PUT', err);
    } finally {
      setLoading(false);
    }
  };

  // Prepare Item for PUT Editing
  const handleEditClick = (item) => {
    setEditId(item.id);
    setFormData({ name: item.name, role: item.role });
  };

  // Cancel PUT Edit mode
  const handleCancelEdit = () => {
    setEditId(null);
    setFormData({ name: '', role: '' });
  };

  // 3. DELETE Request Handler
  const handleDeleteClick = async (id) => {
    if (!window.confirm(`Are you sure you want to delete item ID #${id}?`)) return;
    setLoading(true);

    try {
      const res = await deleteItem(id);
      // Status 204 No Content has no body
      logResponse('DELETE', res.status, res.statusText || 'No Content', { message: `Item #${id} successfully deleted.` }, false);
      fetchItems();
    } catch (err) {
      handleAxiosError('DELETE', err);
    } finally {
      setLoading(false);
    }
  };

  // 4. Force Error Demonstration Handler (400, 404, 500)
  const handleTriggerError = async (code) => {
    setLoading(true);
    try {
      await triggerDemoError(code);
    } catch (err) {
      handleAxiosError(`TEST ERROR ${code}`, err);
    } finally {
      setLoading(false);
    }
  };

  // Helper: Log Success Response
  const logResponse = (method, status, statusText, data, isError) => {
    setResponseLog({
      method,
      status,
      statusText,
      data,
      isError,
      timestamp: new Date().toLocaleTimeString()
    });
  };

  // Helper: Log Axios Error Response (catches 4xx, 5xx)
  const handleAxiosError = (method, err) => {
    if (err.response) {
      // Server responded with status code outside 2xx range
      setResponseLog({
        method,
        status: err.response.status,
        statusText: err.response.statusText || 'Error',
        data: err.response.data,
        isError: true,
        timestamp: new Date().toLocaleTimeString()
      });
    } else {
      // Network error / server down
      setResponseLog({
        method,
        status: 'NETWORK ERROR',
        statusText: 'Server unreachable',
        data: { message: 'Could not connect to backend server at http://localhost:5000' },
        isError: true,
        timestamp: new Date().toLocaleTimeString()
      });
    }
  };

  return (
    <div>
      <h2>Items Manager (Axios CRUD Demonstration)</h2>
      <p>Manage items using Axios REST calls. Observed max 3 initial rows for simple logic.</p>

      {/* Form Section: POST / PUT */}
      <div className="card">
        <h3>{editId ? `Edit Item #${editId} (PUT Request)` : 'Add New Item (POST Request)'}</h3>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <input
              type="text"
              name="name"
              placeholder="Name (e.g., Jane Doe)"
              value={formData.name}
              onChange={handleChange}
              className="form-input"
            />
            <input
              type="text"
              name="role"
              placeholder="Role (e.g., DevOps Engineer)"
              value={formData.role}
              onChange={handleChange}
              className="form-input"
            />
            <button type="submit" className={`btn ${editId ? 'btn-warning' : 'btn-success'}`} disabled={loading}>
              {editId ? 'Update (PUT)' : 'Create (POST)'}
            </button>
            {editId && (
              <button type="button" onClick={handleCancelEdit} className="btn btn-secondary">
                Cancel
              </button>
            )}
          </div>
        </form>
      </div>

      {/* Data Table Section: GET & DELETE */}
      <div className="card">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h3>Current Items List ({items.length} rows)</h3>
          <button onClick={fetchItems} className="btn btn-primary btn-sm" disabled={loading}>
            Refresh List (GET)
          </button>
        </div>

        {loading && <p style={{ color: '#2196f3', fontWeight: 'bold' }}>Executing Axios Request...</p>}

        <table className="data-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Role</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {items.length === 0 ? (
              <tr>
                <td colSpan="4" style={{ textAlign: 'center', color: '#888' }}>
                  No items found. Click "Add New Item" or "Refresh List".
                </td>
              </tr>
            ) : (
              items.map((item) => (
                <tr key={item.id}>
                  <td><strong>#{item.id}</strong></td>
                  <td>{item.name}</td>
                  <td>{item.role}</td>
                  <td className="actions-cell">
                    <button
                      onClick={() => handleEditClick(item)}
                      className="btn btn-warning btn-sm"
                    >
                      Edit (PUT)
                    </button>
                    <button
                      onClick={() => handleDeleteClick(item.id)}
                      className="btn btn-danger btn-sm"
                    >
                      Delete (DELETE)
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {/* Demonstration Panel: Error Scenarios */}
      <div className="card" style={{ borderLeft: '4px solid #ff9800' }}>
        <h3>Live Participant Error Testing</h3>
        <p style={{ fontSize: '0.9rem', marginBottom: '0.5rem' }}>
          Click the buttons below to deliberately trigger server error HTTP status codes for teaching demonstration:
        </p>
        <div className="quick-error-panel">
          <button onClick={() => handleTriggerError(400)} className="btn btn-danger btn-sm">
            Trigger 400 Bad Request
          </button>
          <button onClick={() => handleTriggerError(404)} className="btn btn-danger btn-sm">
            Trigger 404 Not Found
          </button>
          <button onClick={() => handleTriggerError(500)} className="btn btn-danger btn-sm">
            Trigger 500 Server Error
          </button>
        </div>
      </div>

      {/* Response Logger Panel */}
      {responseLog && (
        <div className="response-logger">
          <div className="response-header">
            <span>
              <strong>Axios {responseLog.method} Response Log</strong> ({responseLog.timestamp})
            </span>
            <span className={`badge ${responseLog.isError ? 'badge-error' : 'badge-success'}`}>
              {responseLog.status} {responseLog.statusText}
            </span>
          </div>
          <div className="code-block">
            <pre>{JSON.stringify(responseLog.data, null, 2)}</pre>
          </div>
        </div>
      )}
    </div>
  );
}

export default ItemsManager;
