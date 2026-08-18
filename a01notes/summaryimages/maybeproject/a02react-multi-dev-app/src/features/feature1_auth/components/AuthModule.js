import React, { useState } from 'react';
import { LoginForm } from './LoginForm';

export function AuthModule({ onUserAuthenticate }) {
  // Parent state object to pass down via props
  const [authConfig] = useState({
    title: 'Feature 1: User Authentication Story',
    mode: 'Enterprise Single Sign-On',
    developerAssigned: 'Developer 1',
  });

  // State to store response message object sent back from Child component
  const [receivedChildMessage, setReceivedChildMessage] = useState(null);

  // Callback handler function invoked by Child LoginForm component
  const handleChildLoginMessage = (userPayloadObject) => {
    setReceivedChildMessage(userPayloadObject);
    // Propagate up to global App container if needed
    if (onUserAuthenticate) {
      onUserAuthenticate(userPayloadObject);
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h2>👨‍👩‍👧 Parent Component: AuthModule (Developer 1 Domain)</h2>
        <span style={styles.badge}>{authConfig.developerAssigned}</span>
      </div>

      <div style={styles.grid}>
        {/* Child Component receiving props and sending messages back to parent */}
        <LoginForm
          config={authConfig}
          onLoginSuccess={handleChildLoginMessage}
        />

        {/* Display Message Received from Child Component */}
        <div style={styles.messageBox}>
          <h3>📬 Parent Received Message Hub</h3>
          {receivedChildMessage ? (
            <div style={styles.receivedContent}>
              <p style={styles.successText}>✅ <strong>Child Message:</strong> {receivedChildMessage.message}</p>
              <div style={styles.objectCard}>
                <h4>📦 Data Object Received via Callback:</h4>
                <pre style={styles.jsonCode}>
                  {JSON.stringify(receivedChildMessage, null, 2)}
                </pre>
              </div>
            </div>
          ) : (
            <p style={styles.waitingText}>
              ⏳ Waiting for Child LoginForm component to submit a message object...
            </p>
          )}
        </div>
      </div>
    </div>
  );
}

const styles = {
  container: {
    backgroundColor: '#f8fafc',
    padding: '1.5rem',
    borderRadius: '10px',
    border: '2px solid #3b82f6',
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '1rem',
  },
  badge: {
    backgroundColor: '#dbeafe',
    color: '#1e40af',
    padding: '0.35rem 0.75rem',
    borderRadius: '12px',
    fontSize: '0.8rem',
    fontWeight: 'bold',
  },
  grid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))',
    gap: '1.5rem',
  },
  messageBox: {
    backgroundColor: '#ffffff',
    padding: '1.5rem',
    borderRadius: '8px',
    border: '1px solid #cbd5e1',
  },
  receivedContent: {
    display: 'flex',
    flexDirection: 'column',
    gap: '0.75rem',
  },
  successText: {
    color: '#166534',
    margin: 0,
  },
  objectCard: {
    backgroundColor: '#0f172a',
    color: '#38bdf8',
    padding: '1rem',
    borderRadius: '6px',
  },
  jsonCode: {
    fontSize: '0.85rem',
    margin: 0,
    overflowX: 'auto',
  },
  waitingText: {
    color: '#64748b',
    fontStyle: 'italic',
  },
};
