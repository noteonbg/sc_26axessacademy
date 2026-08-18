import React, { useState } from 'react';

export function LoginForm({ config, onLoginSuccess }) {
  const [username, setUsername] = useState('Alex Developer');
  const [email, setEmail] = useState('alex.dev@enterprise.com');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Child creating a message & data object to send to parent component
    const userPayload = {
      username: username,
      email: email,
      role: 'Full Stack Engineer',
      timestamp: new Date().toLocaleTimeString(),
      message: `User ${username} successfully authenticated from Child LoginForm component!`,
    };
    
    // Invoke parent callback prop
    onLoginSuccess(userPayload);
  };

  return (
    <div style={styles.card}>
      <h3 style={styles.cardTitle}>👶 Child Component: LoginForm</h3>
      <p style={styles.configInfo}>
        Received Prop Object from Parent: <strong>{config.title}</strong> (App Mode: {config.mode})
      </p>

      <form onSubmit={handleSubmit} style={styles.form}>
        <div style={styles.field}>
          <label style={styles.label}>Developer Name:</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            style={styles.input}
            required
          />
        </div>

        <div style={styles.field}>
          <label style={styles.label}>Developer Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            style={styles.input}
            required
          />
        </div>

        <button type="submit" style={styles.button}>
          📤 Send User Object to Parent Component
        </button>
      </form>
    </div>
  );
}

const styles = {
  card: {
    backgroundColor: '#ffffff',
    padding: '1.5rem',
    borderRadius: '8px',
    border: '1px solid #cbd5e1',
    boxShadow: '0 2px 4px rgba(0,0,0,0.05)',
  },
  cardTitle: {
    color: '#0f172a',
    marginTop: 0,
    marginBottom: '0.5rem',
  },
  configInfo: {
    fontSize: '0.9rem',
    color: '#475569',
    backgroundColor: '#f1f5f9',
    padding: '0.5rem',
    borderRadius: '4px',
    marginBottom: '1rem',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '1rem',
  },
  field: {
    display: 'flex',
    flexDirection: 'column',
    gap: '0.25rem',
  },
  label: {
    fontSize: '0.85rem',
    fontWeight: '600',
    color: '#334155',
  },
  input: {
    padding: '0.55rem',
    border: '1px solid #cbd5e1',
    borderRadius: '6px',
    fontSize: '0.9rem',
  },
  button: {
    backgroundColor: '#2563eb',
    color: '#ffffff',
    padding: '0.65rem',
    border: 'none',
    borderRadius: '6px',
    fontWeight: '600',
    cursor: 'pointer',
  },
};
