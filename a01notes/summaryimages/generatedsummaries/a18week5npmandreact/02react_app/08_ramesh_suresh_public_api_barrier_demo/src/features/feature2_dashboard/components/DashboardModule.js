import React, { useState } from 'react';

// ✅ SURESH (DEVELOPER 2) CONSUMING RAMESH'S COMPONENT VIA PUBLIC BARRIER
import { UserProfileCard } from '../../feature1_user';

export function DashboardModule() {
  const [currentUser, setCurrentUser] = useState({
    id: 101,
    name: 'Suresh Dashboard Administrator',
    email: 'suresh.dev@enterprise.com',
    role: 'Full Stack Engineer',
    status: 'ACTIVE',
  });

  const [callbackLog, setCallbackLog] = useState(null);

  const handleRoleUpdateFromRamesh = (payloadObject) => {
    setCallbackLog(payloadObject);
    setCurrentUser((prev) => ({ ...prev, role: payloadObject.newRole }));
  };

  const dashboardStyle = {
    backgroundColor: '#f0fdf4',
    border: '2px solid #16a34a',
    borderRadius: '10px',
    padding: '1.5rem',
  };

  return (
    <div style={dashboardStyle}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h3 style={{ margin: 0, color: '#15803d' }}>📊 Suresh Component: DashboardModule (Developer 2)</h3>
        <span style={{ backgroundColor: '#dcfce7', color: '#15803d', padding: '0.35rem 0.75rem', borderRadius: '12px', fontSize: '0.8rem', fontWeight: 'bold' }}>
          Suresh (Dev 2 Domain)
        </span>
      </div>

      <p style={{ fontSize: '0.9rem', color: '#374151', margin: '0.75rem 0' }}>
        Suresh imports Ramesh's <code>UserProfileCard</code> using the Public Barrier rule: 
        <code>import &#123; UserProfileCard &#125; from '../feature1_user';</code>
      </p>

      <div style={{ margin: '1rem 0' }}>
        {/* Suresh rendering Ramesh's component via props */}
        <UserProfileCard
          userObject={currentUser}
          onUpdateRole={handleRoleUpdateFromRamesh}
        />
      </div>

      {callbackLog && (
        <div style={{ backgroundColor: '#0f172a', color: '#4ade80', padding: '1rem', borderRadius: '6px', marginTop: '1rem' }}>
          <h5 style={{ margin: '0 0 0.5rem 0' }}>📩 Suresh Received Payload Object from Ramesh's Component Callback:</h5>
          <pre style={{ margin: 0, fontSize: '0.85rem' }}>{JSON.stringify(callbackLog, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}
