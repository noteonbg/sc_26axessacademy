import React from 'react';

// Ramesh's Private Helper Component (NOT EXPORTED in feature1_user/index.js)
export function PrivateUserBadge({ status }) {
  const badgeStyle = {
    backgroundColor: status === 'ACTIVE' ? '#dcfce7' : '#fee2e2',
    color: status === 'ACTIVE' ? '#15803d' : '#991b1b',
    padding: '0.25rem 0.6rem',
    borderRadius: '12px',
    fontSize: '0.75rem',
    fontWeight: 'bold',
    display: 'inline-block',
  };

  return <span style={badgeStyle}>🔒 Ramesh Private Badge: {status}</span>;
}
