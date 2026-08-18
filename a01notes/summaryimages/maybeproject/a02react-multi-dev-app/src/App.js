import React, { useState } from 'react';
import { Navbar } from './components/Navbar';

// Importing Features via their Public API Index Files (Strict Barrier Rule)
import { AuthModule } from './features/feature1_auth';
import { CatalogModule } from './features/feature2_catalog';
import { CartModule } from './features/feature3_cart';
import { CheckoutModule } from './features/feature4_checkout';
import { ProfileModule } from './features/feature5_user_profile';
import { AnalyticsModule } from './features/feature6_analytics';

function App() {
  const [activeFeature, setActiveFeature] = useState('f1');
  const [userObject, setUserObject] = useState({
    name: 'Alex Developer',
    email: 'alex.dev@enterprise.com',
    role: 'Senior React Engineer',
  });

  const [sharedCartItems, setSharedCartItems] = useState([
    { id: 101, name: 'Enterprise Cloud Server Node', price: 299, qty: 1 },
  ]);

  const [checkoutPayload, setCheckoutPayload] = useState(null);

  // Global Inter-Component Handlers
  const handleAuthUpdate = (newUserPayload) => {
    setUserObject({
      name: newUserPayload.username,
      email: newUserPayload.email,
      role: newUserPayload.role,
    });
  };

  const handleAddToCart = (newItemObject) => {
    setSharedCartItems((prev) => {
      const existing = prev.find((item) => item.id === newItemObject.id);
      if (existing) {
        return prev.map((item) =>
          item.id === newItemObject.id ? { ...item, qty: item.qty + 1 } : item
        );
      }
      return [...prev, { ...newItemObject, qty: 1 }];
    });
  };

  const handleTriggerCheckout = (cartPayload) => {
    setCheckoutPayload(cartPayload);
    setActiveFeature('f4'); // Switch to Feature 4: Checkout
  };

  return (
    <div className="App" style={styles.appContainer}>
      <Navbar
        activeFeature={activeFeature}
        onSelectFeature={setActiveFeature}
        activeUser={userObject}
      />

      <main style={styles.mainContent}>
        {/* Intercomponent Communication Summary Card */}
        <section style={styles.demoHeader}>
          <h1 style={styles.mainHeading}>
            ⚡ 6-Developer Enterprise React Folder Architecture
          </h1>
          <p style={styles.subHeading}>
            Demonstrating isolated feature slices (`src/features/`), parent-to-child prop object passing, and child-to-parent message callbacks.
          </p>
        </section>

        {/* Feature Render Container */}
        <section style={styles.featureContainer}>
          {activeFeature === 'f1' && (
            <AuthModule onUserAuthenticate={handleAuthUpdate} />
          )}

          {activeFeature === 'f2' && (
            <CatalogModule onGlobalAddToCart={handleAddToCart} />
          )}

          {activeFeature === 'f3' && (
            <CartModule
              globalCartItems={sharedCartItems}
              onCheckoutTrigger={handleTriggerCheckout}
            />
          )}

          {activeFeature === 'f4' && (
            <CheckoutModule orderDetails={checkoutPayload} />
          )}

          {activeFeature === 'f5' && (
            <ProfileModule
              activeUser={userObject}
              onUpdateUserProfile={setUserObject}
            />
          )}

          {activeFeature === 'f6' && <AnalyticsModule />}
        </section>
      </main>
    </div>
  );
}

const styles = {
  appContainer: {
    minHeight: '100vh',
    backgroundColor: '#f8fafc',
  },
  mainContent: {
    maxWidth: '1200px',
    margin: '0 auto',
    padding: '2rem 1.5rem',
  },
  demoHeader: {
    backgroundColor: '#ffffff',
    padding: '1.5rem 2rem',
    borderRadius: '10px',
    border: '1px solid #e2e8f0',
    marginBottom: '2rem',
    boxShadow: '0 2px 4px rgba(0,0,0,0.04)',
  },
  mainHeading: {
    color: '#0f172a',
    marginTop: 0,
    marginBottom: '0.5rem',
    fontSize: '1.6rem',
  },
  subHeading: {
    color: '#64748b',
    margin: 0,
    fontSize: '0.95rem',
  },
  featureContainer: {
    display: 'flex',
    flexDirection: 'column',
    gap: '2rem',
  },
};

export default App;
