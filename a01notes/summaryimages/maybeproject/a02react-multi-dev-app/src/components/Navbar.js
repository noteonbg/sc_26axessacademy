import React from 'react';

export function Navbar({ activeFeature, onSelectFeature, activeUser }) {
  const features = [
    { id: 'f1', label: 'Feature 1: Auth (Dev 1)' },
    { id: 'f2', label: 'Feature 2: Catalog (Dev 2)' },
    { id: 'f3', label: 'Feature 3: Cart (Dev 3)' },
    { id: 'f4', label: 'Feature 4: Checkout (Dev 4)' },
    { id: 'f5', label: 'Feature 5: Profile (Dev 5)' },
    { id: 'f6', label: 'Feature 6: Analytics (Dev 6)' },
  ];

  return (
    <header style={styles.navbar}>
      <div style={styles.brand}>
        <h2 style={styles.logoTitle}>🚀 Enterprise Modular React App</h2>
        <span style={styles.subTitle}>6 Developers | 6 Feature Stories</span>
      </div>
      
      <nav style={styles.navLinks}>
        {features.map((feat) => (
          <button
            key={feat.id}
            onClick={() => onSelectFeature(feat.id)}
            style={{
              ...styles.navButton,
              ...(activeFeature === feat.id ? styles.activeNavButton : {}),
            }}
          >
            {feat.label}
          </button>
        ))}
      </nav>

      <div style={styles.userInfo}>
        <span style={styles.userBadge}>
          👤 User: {activeUser ? activeUser.name : 'Guest User'}
        </span>
      </div>
    </header>
  );
}

const styles = {
  navbar: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: '1rem 2rem',
    backgroundColor: '#1e293b',
    color: '#ffffff',
    boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.1)',
    flexWrap: 'wrap',
    gap: '1rem',
  },
  brand: {
    display: 'flex',
    flexDirection: 'column',
  },
  logoTitle: {
    fontSize: '1.25rem',
    fontWeight: '700',
    margin: 0,
    color: '#38bdf8',
  },
  subTitle: {
    fontSize: '0.75rem',
    color: '#94a3b8',
  },
  navLinks: {
    display: 'flex',
    gap: '0.5rem',
    flexWrap: 'wrap',
  },
  navButton: {
    backgroundColor: 'transparent',
    border: '1px solid #475569',
    color: '#cbd5e1',
    padding: '0.5rem 0.85rem',
    borderRadius: '6px',
    cursor: 'pointer',
    fontSize: '0.85rem',
    transition: 'all 0.2s ease',
  },
  activeNavButton: {
    backgroundColor: '#0284c7',
    borderColor: '#38bdf8',
    color: '#ffffff',
    fontWeight: 'bold',
  },
  userInfo: {
    display: 'flex',
    alignItems: 'center',
  },
  userBadge: {
    backgroundColor: '#334155',
    padding: '0.4rem 0.8rem',
    borderRadius: '20px',
    fontSize: '0.85rem',
    color: '#e2e8f0',
    border: '1px solid #475569',
  },
};
