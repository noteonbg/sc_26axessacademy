import React, { useState } from 'react';

export function ProfileModule({ activeUser, onUpdateUserProfile }) {
  const [profileData, setProfileData] = useState(
    activeUser || {
      name: 'Alex Developer',
      email: 'alex.dev@enterprise.com',
      role: 'Senior React Architect',
      notifications: true,
      theme: 'Dark Professional',
    }
  );

  const handleSave = (e) => {
    e.preventDefault();
    if (onUpdateUserProfile) {
      onUpdateUserProfile(profileData);
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h2>👤 Parent Component: ProfileModule (Developer 5 Domain)</h2>
        <span style={styles.badge}>Developer 5</span>
      </div>

      <div style={styles.card}>
        <form onSubmit={handleSave} style={styles.form}>
          <div style={styles.field}>
            <label>Full Name:</label>
            <input
              type="text"
              value={profileData.name}
              onChange={(e) => setProfileData({ ...profileData, name: e.target.value })}
              style={styles.input}
            />
          </div>

          <div style={styles.field}>
            <label>Email Address:</label>
            <input
              type="email"
              value={profileData.email}
              onChange={(e) => setProfileData({ ...profileData, email: e.target.value })}
              style={styles.input}
            />
          </div>

          <div style={styles.field}>
            <label>Team Role:</label>
            <input
              type="text"
              value={profileData.role}
              onChange={(e) => setProfileData({ ...profileData, role: e.target.value })}
              style={styles.input}
            />
          </div>

          <button type="submit" style={styles.saveBtn}>
            💾 Save Profile Object & Sync Global App State
          </button>
        </form>
      </div>
    </div>
  );
}

const styles = {
  container: {
    backgroundColor: '#fff1f2',
    padding: '1.5rem',
    borderRadius: '10px',
    border: '2px solid #e11d48',
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '1rem',
  },
  badge: {
    backgroundColor: '#ffe4e6',
    color: '#be123c',
    padding: '0.35rem 0.75rem',
    borderRadius: '12px',
    fontSize: '0.8rem',
    fontWeight: 'bold',
  },
  card: {
    backgroundColor: '#ffffff',
    padding: '1.5rem',
    borderRadius: '8px',
    border: '1px solid #fecdd3',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '1rem',
  },
  field: {
    display: 'flex',
    flexDirection: 'column',
    gap: '0.3rem',
  },
  input: {
    padding: '0.55rem',
    borderRadius: '6px',
    border: '1px solid #cbd5e1',
  },
  saveBtn: {
    backgroundColor: '#e11d48',
    color: '#ffffff',
    padding: '0.65rem',
    border: 'none',
    borderRadius: '6px',
    fontWeight: 'bold',
    cursor: 'pointer',
  },
};
