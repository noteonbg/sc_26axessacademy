import React from 'react';

// Importing 6 Static Components via Public Barriers (Strict Boundary Rule)
import { AuthView } from './features/feature1_auth';
import { CatalogView } from './features/feature2_catalog';
import { CartView } from './features/feature3_cart';
import { CheckoutView } from './features/feature4_checkout';
import { ProfileView } from './features/feature5_user_profile';
import { AnalyticsView } from './features/feature6_analytics';

function App() {
  return (
    <div style={{ maxWidth: '1000px', margin: '2rem auto', padding: '0 1rem' }}>
      <header style={{ backgroundColor: '#0f172a', color: '#38bdf8', padding: '1.5rem', borderRadius: '10px', marginBottom: '1.5rem' }}>
        <h1 style={{ margin: 0, fontSize: '1.6rem' }}>🚀 6-Developer Static Component React POC</h1>
        <p style={{ color: '#94a3b8', margin: '0.4rem 0 0 0', fontSize: '0.9rem' }}>
          Demonstrating zero merge conflicts with 6 independent feature branches merged into develop.
        </p>
      </header>

      <main style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
        <AuthView />
        <CatalogView />
        <CartView />
        <CheckoutView />
        <ProfileView />
        <AnalyticsView />
      </main>
    </div>
  );
}

export default App;
