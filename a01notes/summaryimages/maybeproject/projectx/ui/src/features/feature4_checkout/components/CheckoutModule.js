import React, { useState } from 'react';

export function CheckoutModule({ orderDetails }) {
  const [paymentStatus, setPaymentStatus] = useState(null);
  const [cardNumber, setCardNumber] = useState('4532 •••• •••• 8892');

  const defaultDetails = orderDetails || {
    total: 597,
    itemCount: 3,
  };

  const handlePay = (e) => {
    e.preventDefault();
    setPaymentStatus({
      transactionId: `TXN-${Math.floor(100000 + Math.random() * 900000)}`,
      amountPaid: defaultDetails.total,
      status: 'APPROVED',
      processedAt: new Date().toLocaleString(),
    });
  };

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h2>💳 Parent Component: CheckoutModule (Developer 4 Domain)</h2>
        <span style={styles.badge}>Developer 4</span>
      </div>

      <div style={styles.contentGrid}>
        <div style={styles.summaryCard}>
          <h3>Order Details Object Received via Props</h3>
          <p>Total Items: <strong>{defaultDetails.itemCount || 3}</strong></p>
          <p>Total Amount: <strong>${defaultDetails.total}</strong></p>
        </div>

        <div style={styles.formCard}>
          <h3>Payment Processing</h3>
          <form onSubmit={handlePay}>
            <div style={styles.field}>
              <label>Card Number:</label>
              <input
                type="text"
                value={cardNumber}
                onChange={(e) => setCardNumber(e.target.value)}
                style={styles.input}
              />
            </div>
            <button type="submit" style={styles.payBtn}>
              ⚡ Confirm & Pay ${defaultDetails.total}
            </button>
          </form>

          {paymentStatus && (
            <div style={styles.receiptBox}>
              <h4>🎉 Payment Success Object Emitted:</h4>
              <pre>{JSON.stringify(paymentStatus, null, 2)}</pre>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

const styles = {
  container: {
    backgroundColor: '#faf5ff',
    padding: '1.5rem',
    borderRadius: '10px',
    border: '2px solid #9333ea',
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '1rem',
  },
  badge: {
    backgroundColor: '#f3e8ff',
    color: '#7e22ce',
    padding: '0.35rem 0.75rem',
    borderRadius: '12px',
    fontSize: '0.8rem',
    fontWeight: 'bold',
  },
  contentGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))',
    gap: '1.5rem',
  },
  summaryCard: {
    backgroundColor: '#ffffff',
    padding: '1.25rem',
    borderRadius: '8px',
    border: '1px solid #e9d5ff',
  },
  formCard: {
    backgroundColor: '#ffffff',
    padding: '1.25rem',
    borderRadius: '8px',
    border: '1px solid #e9d5ff',
  },
  field: {
    display: 'flex',
    flexDirection: 'column',
    gap: '0.3rem',
    marginBottom: '1rem',
  },
  input: {
    padding: '0.5rem',
    borderRadius: '4px',
    border: '1px solid #cbd5e1',
  },
  payBtn: {
    backgroundColor: '#9333ea',
    color: '#ffffff',
    border: 'none',
    padding: '0.65rem',
    borderRadius: '6px',
    width: '100%',
    fontWeight: 'bold',
    cursor: 'pointer',
  },
  receiptBox: {
    backgroundColor: '#0f172a',
    color: '#a855f7',
    padding: '1rem',
    borderRadius: '6px',
    marginTop: '1rem',
  },
};
