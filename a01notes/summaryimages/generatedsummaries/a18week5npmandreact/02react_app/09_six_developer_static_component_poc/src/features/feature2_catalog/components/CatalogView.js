import React from 'react';

// Static component built by Developer 2 (No state, No props)
export function CatalogView() {
  const style = {
    backgroundColor: '#dcfce7',
    border: '2px solid #16a34a',
    color: '#15803d',
    padding: '1rem',
    borderRadius: '8px',
    margin: '0.5rem 0',
  };

  return (
    <div style={style}>
      <h3>🛍️ Feature 2: Product Catalog Module</h3>
      <p>Static Text Component built by <strong>Developer 2</strong>.</p>
    </div>
  );
}
