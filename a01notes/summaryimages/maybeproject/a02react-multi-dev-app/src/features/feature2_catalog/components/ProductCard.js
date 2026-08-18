import React from 'react';

export function ProductCard({ productItem, onAddToCart }) {
  const handleAddClick = () => {
    // Child sends action object & message back to parent component
    const cartMessagePayload = {
      action: 'ADD_TO_CART',
      item: productItem,
      message: `Added ${productItem.name} ($${productItem.price}) to cart!`,
      addedAt: new Date().toLocaleTimeString(),
    };
    onAddToCart(cartMessagePayload);
  };

  return (
    <div style={styles.card}>
      <div style={styles.header}>
        <span style={styles.category}>{productItem.category}</span>
        <span style={styles.price}>${productItem.price}</span>
      </div>
      <h4 style={styles.title}>{productItem.name}</h4>
      <p style={styles.desc}>{productItem.description}</p>
      
      <button onClick={handleAddClick} style={styles.button}>
        🛒 Add to Cart (Send Callback Object)
      </button>
    </div>
  );
}

const styles = {
  card: {
    backgroundColor: '#ffffff',
    padding: '1.25rem',
    borderRadius: '8px',
    border: '1px solid #e2e8f0',
    display: 'flex',
    flexDirection: 'column',
    justify: 'space-between',
    gap: '0.75rem',
    boxShadow: '0 1px 3px rgba(0,0,0,0.1)',
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  category: {
    fontSize: '0.75rem',
    textTransform: 'uppercase',
    fontWeight: 'bold',
    color: '#64748b',
  },
  price: {
    fontSize: '1rem',
    fontWeight: '700',
    color: '#166534',
  },
  title: {
    margin: 0,
    color: '#0f172a',
  },
  desc: {
    fontSize: '0.85rem',
    color: '#475569',
    margin: 0,
  },
  button: {
    backgroundColor: '#059669',
    color: '#ffffff',
    border: 'none',
    padding: '0.5rem',
    borderRadius: '6px',
    cursor: 'pointer',
    fontWeight: '600',
    marginTop: '0.5rem',
  },
};
