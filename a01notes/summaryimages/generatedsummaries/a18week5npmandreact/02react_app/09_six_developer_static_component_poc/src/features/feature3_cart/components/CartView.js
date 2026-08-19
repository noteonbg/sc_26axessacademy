import React from 'react';

// Static component built by Developer 3 (No state, No props)
export function CartView() {
  const style = {
    backgroundColor: '#ffedd5',
    border: '2px solid #ea580c',
    color: '#c2410c',
    padding: '1rem',
    borderRadius: '8px',
    margin: '0.5rem 0',
  };

  return (
    <div style={style}>
      <h3>🛒 Feature 3: Shopping Cart Module</h3>
      <p>Static Text Component built by <strong>Developer 3</strong>.</p>
    </div>
  );
}
