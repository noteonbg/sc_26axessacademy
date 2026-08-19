import React from 'react';

// Static component built by Developer 5 (No state, No props)
export function ProfileView() {
  const style = {
    backgroundColor: '#ffe4e6',
    border: '2px solid #e11d48',
    color: '#be123c',
    padding: '1rem',
    borderRadius: '8px',
    margin: '0.5rem 0',
  };

  return (
    <div style={style}>
      <h3>👤 Feature 5: User Profile Module</h3>
      <p>Static Text Component built by <strong>Developer 5</strong>.</p>
    </div>
  );
}
