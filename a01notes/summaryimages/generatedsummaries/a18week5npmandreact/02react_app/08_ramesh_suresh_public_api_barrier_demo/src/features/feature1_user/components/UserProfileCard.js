import React from 'react';
import { PrivateUserBadge } from './PrivateUserBadge';

// Ramesh's Public Component (Exported through index.js for team use)
export function UserProfileCard({ userObject, onUpdateRole }) {
  const handleRoleChange = () => {
    const updatedPayload = {
      userId: userObject.id,
      newRole: 'Lead Architect',
      updatedBy: 'Ramesh Component Callback',
      timestamp: new Date().toLocaleTimeString(),
    };
    if (onUpdateRole) {
      onUpdateRole(updatedPayload);
    }
  };

  const cardStyle = {
    backgroundColor: '#ffffff',
    border: '2px solid #3b82f6',
    borderRadius: '8px',
    padding: '1.25rem',
    boxShadow: '0 2px 4px rgba(0,0,0,0.05)',
  };

  return (
    <div style={cardStyle}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h4 style={{ margin: 0, color: '#1e40af' }}>👤 Ramesh Public Component: UserProfileCard</h4>
        <PrivateUserBadge status={userObject.status || 'ACTIVE'} />
      </div>

      <div style={{ margin: '0.75rem 0', fontSize: '0.9rem', color: '#334155' }}>
        <p style={{ margin: '0.25rem 0' }}><strong>Developer:</strong> Ramesh (Dev 1)</p>
        <p style={{ margin: '0.25rem 0' }}><strong>Name:</strong> {userObject.name}</p>
        <p style={{ margin: '0.25rem 0' }}><strong>Email:</strong> {userObject.email}</p>
        <p style={{ margin: '0.25rem 0' }}><strong>Role:</strong> {userObject.role}</p>
      </div>

      <button
        onClick={handleRoleChange}
        style={{
          backgroundColor: '#2563eb',
          color: '#ffffff',
          border: 'none',
          padding: '0.5rem 0.85rem',
          borderRadius: '6px',
          cursor: 'pointer',
          fontWeight: 'bold',
          fontSize: '0.85rem',
        }}
      >
        ⚡ Trigger Ramesh Callback to Parent Component
      </button>
    </div>
  );
}
