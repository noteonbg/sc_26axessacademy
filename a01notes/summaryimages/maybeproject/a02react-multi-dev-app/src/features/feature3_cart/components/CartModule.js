import React, { useState } from 'react';
import { CartItemRow } from './CartItemRow';

export function CartModule({ globalCartItems, onCheckoutTrigger }) {
  const [localCart, setLocalCart] = useState(
    globalCartItems && globalCartItems.length > 0
      ? globalCartItems
      : [
          { id: 101, name: 'Enterprise Cloud Server Node', price: 299, qty: 1 },
          { id: 102, name: 'Developer Ergonomic Keyboard', price: 149, qty: 2 },
        ]
  );

  const [lastActionMessage, setLastActionMessage] = useState('');

  const handleUpdateQty = (id, newQty) => {
    setLocalCart((prev) =>
      prev.map((item) => (item.id === id ? { ...item, qty: newQty } : item))
    );
    setLastActionMessage(`Child CartItemRow updated quantity for item #${id} to ${newQty}`);
  };

  const handleRemoveItem = (id, name) => {
    setLocalCart((prev) => prev.filter((item) => item.id !== id));
    setLastActionMessage(`Child CartItemRow removed item "${name}" from cart`);
  };

  const totalCartAmount = localCart.reduce((sum, item) => sum + item.price * item.qty, 0);

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h2>🛒 Parent Component: CartModule (Developer 3 Domain)</h2>
        <span style={styles.badge}>Developer 3</span>
      </div>

      {lastActionMessage && (
        <div style={styles.toast}>
          📢 <strong>Child Component Notification:</strong> {lastActionMessage}
        </div>
      )}

      <div style={styles.cartList}>
        {localCart.length > 0 ? (
          localCart.map((item) => (
            <CartItemRow
              key={item.id}
              itemObject={item}
              onUpdateQuantity={handleUpdateQty}
              onRemoveItem={handleRemoveItem}
            />
          ))
        ) : (
          <p style={styles.emptyText}>Your shopping cart is currently empty.</p>
        )}
      </div>

      <div style={styles.footer}>
        <div style={styles.totalSection}>
          <span>Cart Subtotal:</span>
          <strong style={styles.totalAmount}>${totalCartAmount}</strong>
        </div>

        <button
          onClick={() => onCheckoutTrigger && onCheckoutTrigger({ items: localCart, total: totalCartAmount })}
          disabled={localCart.length === 0}
          style={styles.checkoutBtn}
        >
          💳 Proceed to Checkout (Feature 4)
        </button>
      </div>
    </div>
  );
}

const styles = {
  container: {
    backgroundColor: '#fff7ed',
    padding: '1.5rem',
    borderRadius: '10px',
    border: '2px solid #ea580c',
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '1rem',
  },
  badge: {
    backgroundColor: '#ffedd5',
    color: '#c2410c',
    padding: '0.35rem 0.75rem',
    borderRadius: '12px',
    fontSize: '0.8rem',
    fontWeight: 'bold',
  },
  toast: {
    backgroundColor: '#fed7aa',
    color: '#9a3412',
    padding: '0.6rem 0.85rem',
    borderRadius: '6px',
    fontSize: '0.85rem',
    marginBottom: '1rem',
  },
  cartList: {
    marginBottom: '1rem',
  },
  emptyText: {
    color: '#9a3412',
    fontStyle: 'italic',
  },
  footer: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    borderTop: '1px solid #fdba74',
    paddingTop: '1rem',
  },
  totalSection: {
    fontSize: '1.1rem',
    color: '#431407',
  },
  totalAmount: {
    color: '#c2410c',
    marginLeft: '0.5rem',
    fontSize: '1.25rem',
  },
  checkoutBtn: {
    backgroundColor: '#ea580c',
    color: '#ffffff',
    border: 'none',
    padding: '0.65rem 1.25rem',
    borderRadius: '6px',
    fontWeight: 'bold',
    cursor: 'pointer',
  },
};
