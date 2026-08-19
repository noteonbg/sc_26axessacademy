import React from 'react';

// Static component built by Developer 4 (No state, No props)
export function CheckoutView() {
  const style = {
    backgroundColor: '#f3e8ff',
    border: '2px solid #9333ea',
    color: '#7e22ce',
    padding: '1rem',
    borderRadius: '8px',
    margin: '0.5rem 0',
  };

  return (
    <div style={style}>
      <h3>💳 Feature 4: Payment Checkout Module</h3>
      <p>Static Text Component built by <strong>Developer 4</strong>.</p>
    </div>
  );
}
