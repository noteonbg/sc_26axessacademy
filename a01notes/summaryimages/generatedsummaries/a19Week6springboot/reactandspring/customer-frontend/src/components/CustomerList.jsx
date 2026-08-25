import React from 'react'; // Import React library

/**
 * Component rendering customer list directory in a structured table.
 * @param {Array} customers Array of customer objects fetched from backend
 * @param {Function} onSelectCustomer Callback when user clicks 'Edit' button
 * @param {boolean} isLoading Loading boolean indicator
 * @param {Function} onRefresh Callback to re-fetch customer list from API
 */
const CustomerList = ({ customers, onSelectCustomer, isLoading, onRefresh }) => {
  // Display loading spinner while data is being fetched from backend
  if (isLoading) {
    return (
      <div className="card loading-container">
        <div className="spinner"></div> {/* CSS animated loading spinner */}
        <p>Loading customers from Spring Boot API...</p>
      </div>
    );
  }

  return (
    <div className="card">
      <div className="card-header">
        <div>
          <h2>Customer Directory</h2> {/* Section title */}
          <p className="subtitle">All active customers fetched via Spring Boot REST API</p>
        </div>
        {/* Refresh button triggering API call */}
        <button className="btn btn-secondary" onClick={onRefresh}>
          🔄 Refresh
        </button>
      </div>

      {/* Render empty state message if customer list is empty */}
      {customers.length === 0 ? (
        <div className="empty-state">
          <p>No customers found.</p>
        </div>
      ) : (
        // Render customers directory table
        <div className="table-responsive">
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
              {/* Map customer records array to JSX table rows */}
              {customers.map((customer) => (
                <tr key={customer.customerId}> {/* React list item unique key */}
                  <td>
                    <span className="badge badge-id">#{customer.customerId}</span> {/* Customer ID */}
                  </td>
                  <td className="font-semibold">{customer.name}</td> {/* Customer Name (READ-ONLY) */}
                  <td>{customer.email}</td> {/* Customer Email (EDITABLE) */}
                  <td>
                    <span className="badge badge-location">📍 {customer.location}</span> {/* Customer Location */}
                  </td>
                  <td>
                    {/* Action edit button passing current customer record to parent modal launcher */}
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
      )}
    </div>
  );
};

export default CustomerList; // Export CustomerList component
