import React, { useState, useEffect } from 'react'; // Import React state and lifecycle hooks

/**
 * Edit Customer Modal Dialog Component.
 * STRICT RULE ENFORCED:
 * - Customer ID and Name inputs are READ-ONLY / DISABLED.
 * - ONLY Email and Location inputs are editable by the user.
 */
const EditCustomerModal = ({ customer, isOpen, onClose, onUpdate }) => {
  const [email, setEmail] = useState(''); // State holding editable email input value
  const [location, setLocation] = useState(''); // State holding editable location input value
  const [errors, setErrors] = useState({}); // State holding client-side validation errors
  const [isSubmitting, setIsSubmitting] = useState(false); // State indicating submission in progress

  // Synchronize form state whenever selected customer prop changes
  useEffect(() => {
    if (customer) {
      setEmail(customer.email || ''); // Populate current customer email
      setLocation(customer.location || ''); // Populate current customer location
      setErrors({}); // Reset error messages
    }
  }, [customer]); // Dependency array triggers effect when customer object changes

  if (!isOpen || !customer) return null; // If modal is closed or customer null, render nothing

  /**
   * Performs client-side form validation before sending request to backend API.
   */
  const validate = () => {
    const newErrors = {};
    if (!email.trim()) {
      newErrors.email = 'Email is required'; // Email cannot be empty
    } else if (!/\S+@\S+\.\S+/.test(email)) {
      newErrors.email = 'Please enter a valid email address'; // Format validation
    }

    if (!location.trim()) {
      newErrors.location = 'Location is required'; // Location cannot be empty
    }

    setErrors(newErrors); // Update errors state
    return Object.keys(newErrors).length === 0; // Return true if no validation errors exist
  };

  /**
   * Handles modal form submission.
   */
  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevent default browser form submission refresh
    if (!validate()) return; // Stop if form validation fails

    setIsSubmitting(true); // Set submitting state true
    try {
      // Trigger parent update handler sending ONLY email and location
      await onUpdate(customer.customerId, { email, location });
      onClose(); // Close modal upon successful update
    } catch (err) {
      // Error is caught and displayed by parent Notification state
    } finally {
      setIsSubmitting(false); // Reset submitting state false
    }
  };

  return (
    <div className="modal-backdrop">
      <div className="modal-container">
        <div className="modal-header">
          <h3>Edit Customer Details</h3>
          {/* Modal close button */}
          <button className="modal-close-btn" onClick={onClose}>
            &times;
          </button>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="modal-body">
            {/* Rule banner reminder */}
            <div className="alert-info">
              ℹ️ <strong>Rule Enforced:</strong> Only <em>Email</em> and <em>Location</em> can be updated. Customer ID and Name are fixed/read-only.
            </div>

            {/* Read-Only Field: Customer ID */}
            <div className="form-group">
              <label htmlFor="customerId">Customer ID (Read-Only)</label>
              <input
                type="text"
                id="customerId"
                value={`#${customer.customerId}`} // Display customer ID
                disabled // READ-ONLY: Input is disabled so user cannot edit
                className="input-disabled"
              />
            </div>

            {/* Read-Only Field: Customer Name */}
            <div className="form-group">
              <label htmlFor="customerName">Customer Name (Read-Only)</label>
              <input
                type="text"
                id="customerName"
                value={customer.name} // Display customer name
                disabled // READ-ONLY: Input is disabled so user cannot edit
                className="input-disabled"
              />
            </div>

            {/* Editable Field: Email */}
            <div className="form-group">
              <label htmlFor="customerEmail">
                Email Address <span className="required">*</span>
              </label>
              <input
                type="email"
                id="customerEmail"
                value={email} // Controlled input binding to email state
                onChange={(e) => setEmail(e.target.value)} // Two-way data binding state update
                placeholder="e.g. john@example.com"
                className={errors.email ? 'input-error' : ''} // Apply red border if validation error exists
              />
              {errors.email && <span className="error-message">{errors.email}</span>} {/* Error text */}
            </div>

            {/* Editable Field: Location */}
            <div className="form-group">
              <label htmlFor="customerLocation">
                Location <span className="required">*</span>
              </label>
              <input
                type="text"
                id="customerLocation"
                value={location} // Controlled input binding to location state
                onChange={(e) => setLocation(e.target.value)} // Two-way data binding state update
                placeholder="e.g. Chicago"
                className={errors.location ? 'input-error' : ''} // Apply red border if validation error exists
              />
              {errors.location && <span className="error-message">{errors.location}</span>} {/* Error text */}
            </div>
          </div>

          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              onClick={onClose}
              disabled={isSubmitting}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="btn btn-primary"
              disabled={isSubmitting}
            >
              {isSubmitting ? 'Updating...' : 'Save Changes'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EditCustomerModal; // Export EditCustomerModal component
