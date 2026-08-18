import React from 'react';

// Importing Suresh's module via Suresh's Public Barrier index.js
import { DashboardModule } from './features/feature2_dashboard';

function App() {
  return (
    <div style={{ maxWidth: '1000px', margin: '2rem auto', padding: '0 1rem' }}>
      <header style={{ backgroundColor: '#1e293b', color: '#ffffff', padding: '1.5rem 2rem', borderRadius: '10px', marginBottom: '2rem' }}>
        <h1 style={{ margin: 0, color: '#38bdf8', fontSize: '1.5rem' }}>
          🚀 Multi-Developer React Public API Barrier Demo
        </h1>
        <p style={{ margin: '0.5rem 0 0 0', color: '#94a3b8', fontSize: '0.9rem' }}>
          Ramesh (Developer 1) & Suresh (Developer 2) Feature Integration
        </p>
      </header>

      <main>
        <DashboardModule />
      </main>
    </div>
  );
}

export default App;
