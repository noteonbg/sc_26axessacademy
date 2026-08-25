import React from 'react'; // Import React library

/**
 * Component for displaying success banners or error validation alerts.
 * @param {Object} notification Notification state object { type, message, validationErrors }
 * @param {Function} onClose Callback function triggered when close button is clicked
 */
const Notification = ({ notification, onClose }) => {
  if (!notification || !notification.message) return null; // If no notification exists, render nothing

  const isError = notification.type === 'error'; // Determine if notification is an error banner

  return (
    // Dynamic CSS class applying red background for errors or green background for success
    <div className={`notification-banner ${isError ? 'notification-error' : 'notification-success'}`}>
      <div className="notification-content">
        <span className="notification-icon">{isError ? '⚠️' : '✅'}</span> {/* Render warning or check icon */}
        <div>
          <strong className="notification-title">{isError ? 'Error' : 'Success'}</strong> {/* Title header */}
          <p className="notification-message">{notification.message}</p> {/* Display main error/success message */}
          {/* Conditionally render list of field validation errors if returned from backend */}
          {notification.validationErrors && (
            <ul className="notification-error-list">
              {Object.entries(notification.validationErrors).map(([field, msg]) => (
                <li key={field}>
                  <strong>{field}:</strong> {msg} {/* Render field name and validation rule message */}
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
      {/* Close button triggering onClose callback */}
      <button className="notification-close-btn" onClick={onClose}>
        &times;
      </button>
    </div>
  );
};

export default Notification; // Export Notification component
