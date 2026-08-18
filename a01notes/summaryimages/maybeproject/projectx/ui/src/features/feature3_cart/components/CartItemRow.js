import React from 'react';

export function CartItemRow({ itemObject, onUpdateQuantity, onRemoveItem }) {
  return (
    <div style={styles.row}>
      <div style={styles.itemDetails}>
        <strong>{itemObject.name}</strong>
        <span style={styles.itemPrice}>${itemObject.price} each</span>
      </div>

      <div style={styles.controls}>
        <button
          onClick={() => onUpdateQuantity(itemObject.id, itemObject.qty - 1)}
          disabled={itemObject.qty <= 1}
          style={styles.qtyBtn}
        >
          -
        </button>
        <span style={styles.qtyText}>{itemObject.qty}</span>
        <button
          onClick={() => onUpdateQuantity(itemObject.id, itemObject.qty + 1)}
          style={styles.qtyBtn}
        >
          +
        </button>

        <button
          onClick={() => onRemoveItem(itemObject.id, itemObject.name)}
          style={styles.removeBtn}
        >
          🗑️ Remove
        </button>
      </div>
    </div>
  );
}

const styles = {
  row: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: '#ffffff',
    padding: '0.85rem 1rem',
    borderRadius: '6px',
    border: '1px solid #e2e8f0',
    marginBottom: '0.5rem',
  },
  itemDetails: {
    display: 'flex',
    flexDirection: 'column',
  },
  itemPrice: {
    fontSize: '0.8rem',
    color: '#64748b',
  },
  controls: {
    display: 'flex',
    alignItems: 'center',
    gap: '0.5rem',
  },
  qtyBtn: {
    width: '28px',
    height: '28px',
    backgroundColor: '#f1f5f9',
    border: '1px solid #cbd5e1',
    borderRadius: '4px',
    cursor: 'pointer',
    fontWeight: 'bold',
  },
  qtyText: {
    padding: '0 0.4rem',
    fontWeight: '600',
  },
  removeBtn: {
    backgroundColor: '#fee2e2',
    color: '#991b1b',
    border: '1px solid #fca5a5',
    borderRadius: '4px',
    padding: '0.35rem 0.6rem',
    fontSize: '0.8rem',
    cursor: 'pointer',
    marginLeft: '0.5rem',
  },
};
