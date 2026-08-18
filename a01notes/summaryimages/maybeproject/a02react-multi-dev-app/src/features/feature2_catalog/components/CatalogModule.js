import React, { useState } from 'react';
import { ProductCard } from './ProductCard';

export function CatalogModule({ onGlobalAddToCart }) {
  const [products] = useState([
    { id: 101, name: 'Enterprise Cloud Server Node', price: 299, category: 'Infrastructure', description: 'High-performance cloud compute cluster node.' },
    { id: 102, name: 'Developer Ergonomic Keyboard', price: 149, category: 'Hardware', description: 'Mechanical split keyboard optimized for speed.' },
    { id: 103, name: 'AI Code Assistant License', price: 99, category: 'Software', description: 'Annual license for enterprise code intelligence.' },
  ]);

  const [lastCartNotification, setLastCartNotification] = useState(null);

  const handleAddToCartChildCallback = (payloadObject) => {
    setLastCartNotification(payloadObject);
    if (onGlobalAddToCart) {
      onGlobalAddToCart(payloadObject.item);
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h2>🛍️ Parent Component: CatalogModule (Developer 2 Domain)</h2>
        <span style={styles.badge}>Developer 2</span>
      </div>

      {lastCartNotification && (
        <div style={styles.notificationBanner}>
          <strong>📩 Child Component Message Received:</strong> {lastCartNotification.message}
        </div>
      )}

      <div style={styles.grid}>
        {products.map((item) => (
          <ProductCard
            key={item.id}
            productItem={item}
            onAddToCart={handleAddToCartChildCallback}
          />
        ))}
      </div>
    </div>
  );
}

const styles = {
  container: {
    backgroundColor: '#f0fdf4',
    padding: '1.5rem',
    borderRadius: '10px',
    border: '2px solid #16a34a',
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '1rem',
  },
  badge: {
    backgroundColor: '#dcfce7',
    color: '#15803d',
    padding: '0.35rem 0.75rem',
    borderRadius: '12px',
    fontSize: '0.8rem',
    fontWeight: 'bold',
  },
  notificationBanner: {
    backgroundColor: '#bbf7d0',
    color: '#14532d',
    padding: '0.75rem 1rem',
    borderRadius: '6px',
    marginBottom: '1rem',
    border: '1px solid #86efac',
  },
  grid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(260px, 1fr))',
    gap: '1rem',
  },
};
