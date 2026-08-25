import React, { useState, useEffect, useCallback } from 'react'; // Import React core hooks
import CustomerList from './components/CustomerList'; // Import CustomerList component
import EditCustomerModal from './components/EditCustomerModal'; // Import EditCustomerModal component
import Notification from './components/Notification'; // Import Notification alert banner component
import { getAllCustomers, updateCustomer } from './services/customerService'; // Import API service methods
import './App.css'; // Import layout and UI styling rules

/**
 * Root Application Component managing top-level state and API interactions.
 */
function App() {
  const [customers, setCustomers] = useState([]); // Central state array storing customer records
  const [selectedCustomer, setSelectedCustomer] = useState(null); // State holding customer object currently being edited
  const [isModalOpen, setIsModalOpen] = useState(false); // Boolean state controlling modal visibility
  const [isLoading, setIsLoading] = useState(true); // Boolean state controlling loading spinner
  const [notification, setNotification] = useState(null); // State holding current notification object { type, message }

  /**
   * Memoized callback function fetching all customers from Spring Boot API via Axios.
   */
  const fetchCustomers = useCallback(async () => {
    setIsLoading(true); // Set loading spinner active
    try {
      const data = await getAllCustomers(); // Execute API GET call to fetch customers array
      setCustomers(data); // Store fetched customer array into React state
    } catch (err) {
      // Set error notification banner if API call fails
      setNotification({
        type: 'error',
        message: err.message || 'Failed to fetch customer list from backend',
        validationErrors: err.validationErrors,
      });
    } finally {
      setIsLoading(false); // Turn off loading spinner
    }
  }, []); // Empty dependency array means function reference remains stable

  // Lifecycle hook running fetchCustomers() once when component mounts
  useEffect(() => {
    fetchCustomers(); // Initial select all customers query
  }, [fetchCustomers]);

  /**
   * Opens Edit modal for a selected customer.
   */
  const handleOpenEditModal = (customer) => {
    setSelectedCustomer(customer); // Set selected customer target
    setIsModalOpen(true); // Open edit modal dialog
    setNotification(null); // Clear any previous notification messages
  };

  /**
   * Closes Edit modal.
   */
  const handleCloseEditModal = () => {
    setIsModalOpen(false); // Close edit modal dialog
    setSelectedCustomer(null); // Clear selected customer target
  };

  /**
   * Handles customer update submission.
   * Sends ONLY email and location payload to Spring Boot REST backend.
   */
  const handleUpdateCustomer = async (id, updatePayload) => {
    try {
      // Send PUT request with email & location payload to backend API
      const response = await updateCustomer(id, updatePayload);
      
      // Set success notification message
      setNotification({
        type: 'success',
        message: response.message || 'Customer updated successfully!',
      });
      
      // Refresh customer list table with updated data from backend
      await fetchCustomers();
    } catch (err) {
      // Set error notification banner with error details
      setNotification({
        type: 'error',
        message: err.message || 'Failed to update customer details',
        validationErrors: err.validationErrors,
      });
      throw err; // Rethrow error so modal component knows submission failed
    }
  };

  return (
    <div className="app-container">
      {/* App Header Bar */}
      <header className="app-header">
        <div className="header-content">
          <h1>⚡ Customer Portal</h1>
          <p>Full-Stack Spring Boot & React Application</p>
        </div>
      </header>

      {/* Main Container Area */}
      <main className="main-content">
        {/* Render notification banner */}
        <Notification
          notification={notification}
          onClose={() => setNotification(null)} // Clear notification on close click
        />

        {/* Render customer list table */}
        <CustomerList
          customers={customers} // Pass customers array state
          onSelectCustomer={handleOpenEditModal} // Pass edit click callback
          isLoading={isLoading} // Pass loading state
          onRefresh={fetchCustomers} // Pass refresh callback
        />

        {/* Render edit customer modal */}
        <EditCustomerModal
          customer={selectedCustomer} // Pass currently selected customer
          isOpen={isModalOpen} // Pass modal open visibility state
          onClose={handleCloseEditModal} // Pass close modal callback
          onUpdate={handleUpdateCustomer} // Pass update API submission callback
        />
      </main>

      {/* App Footer */}
      <footer className="app-footer">
        <p>Spring Boot (Maven) + React (Axios) • Customer Management Demo</p>
      </footer>
    </div>
  );
}

export default App; // Export root App component
