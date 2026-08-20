import React, { useState } from 'react';

/**
 * Child Component: ChildAccountCard
 * - Receives account object from Parent via props
 * - Sends updated object BACK to Parent via onUpdateAccount callback function
 */
export default function ChildAccountCard({ account, onUpdateAccount }) {
    const [depositAmount, setDepositAmount] = useState('');

    const handleDepositSubmit = (e) => {
        console.log("am I conscious");
        e.preventDefault();
        const numAmount = parseFloat(depositAmount);

        if (isNaN(numAmount) || numAmount <= 0) {
            window.alert("Please enter a valid deposit amount.");
            return;
        }

        // Creating updated financial object
        const freak = {
            ...account,
            balance: account.balance + numAmount,
            lastAction: `Deposited $${numAmount.toFixed(2)} on ${new Date().toLocaleTimeString()}`
        };


        // Calling Parent Callback Function -> Sends Object Data UP to Parent
        onUpdateAccount(freak);

        setDepositAmount('');
    };

    return (
        <div style={styles.childCard}>
            <h3 style={styles.childTitle}>Child Component: Account Operations Card</h3>
            <p style={styles.subText}>Receives account object from Parent via Props</p>

            <div style={styles.infoBox}>
                <p><strong>Account Holder:</strong> {account.accountHolder}</p>
                <p><strong>Account Number:</strong> {account.accountNumber}</p>
                <p><strong>Current Balance:</strong> Rs {account.balance.toFixed(2)}</p>
                <p><strong>Status:</strong> {account.status}</p>
            </div>

            <form onSubmit={handleDepositSubmit} style={styles.formGroup}>
                <label style={styles.label}>Perform Deposit ($):</label>
                <div style={{ display: 'flex', gap: '10px' }}>
                    <input 
                        type="number"
                        value={depositAmount}
                        onChange={(e) => setDepositAmount(e.target.value)}
                        placeholder="Enter deposit amount"
                        style={styles.input}
                    />
                    <button type="submit" style={styles.btn}>Deposit & Send to Parent</button>
                </div>
            </form>
        </div>
    );
}

const styles = {
    childCard: {
        background: '#ffffff',
        border: '2px solid #3b82f6',
        borderRadius: '8px',
        padding: '20px',
        marginTop: '20px'
    },
    childTitle: {
        color: '#1d4ed8',
        margin: '0 0 5px 0'
    },
    subText: {
        color: '#64748b',
        fontSize: '0.85rem',
        margin: '0 0 15px 0'
    },
    infoBox: {
        background: '#f8fafc',
        border: '1px solid #cbd5e1',
        borderRadius: '6px',
        padding: '12px 15px',
        marginBottom: '15px'
    },
    formGroup: {
        display: 'flex',
        flexDirection: 'column',
        gap: '8px'
    },
    label: {
        fontWeight: '600',
        fontSize: '0.9rem',
        color: '#334155'
    },
    input: {
        flex: 1,
        padding: '8px 12px',
        borderRadius: '4px',
        border: '1px solid #cbd5e1'
    },
    btn: {
        backgroundColor: '#2563eb',
        color: 'white',
        border: 'none',
        padding: '8px 16px',
        borderRadius: '4px',
        fontWeight: '600',
        cursor: 'pointer'
    }
};
