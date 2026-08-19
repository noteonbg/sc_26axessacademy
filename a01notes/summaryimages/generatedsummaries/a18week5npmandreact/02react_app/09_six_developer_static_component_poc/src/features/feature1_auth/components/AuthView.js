import React from 'react';

// Static component built by Developer 1 (No state, No props)
export function AuthView() {
  const style = {
    backgroundColor: '#dbeafe',
    border: '2px solid #2563eb',
    color: '#1e40af',
    padding: '1rem',
    borderRadius: '8px',
    margin: '0.5rem 0',
  };

  return (
    <div style={style}>
      <h3>🔐 Feature 1: User Authentication Module</h3>
      <p>Static Text Component built by <strong>Developer 1</strong>.</p>
    </div>
  );
}
