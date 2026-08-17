import React, { useState } from 'react';
import ChildAccountCard from './ChildAccountCard.jsx';

/**
 * Parent Component: ParentAccountManager
 * - Holds single source of truth account state object
 * - Passes account object DOWN to Child via props
 * - Receives updated object UP from Child via callback function
 */
export default function ParentAccountManager() {
    // Initial Parent State Object
    const [accountDetails, setAccountDetails] = useState({
        accountHolder: "Euler",
        accountNumber: "ACC-789012",
        balance: 5000.00,
        status: "ACTIVE",
        lastAction: "Initial State Loaded"
    });

    // Callback function passed to Child component via props
    const handleChildUpdate = (updatedAccountObject) => {
        // Parent receives updated object from Child and updates state
        setAccountDetails(updatedAccountObject);
    };

    return (
        <div style={styles.parentContainer}>
            <h2 style={styles.parentTitle}>Parent Component: Account Management Portal</h2>
            <p style={styles.subText}>Holds central account state object and listens to Child updates</p>

            <div style={styles.parentStatusCard}>
                <h4>Parent State Monitor</h4>
                <p><strong>Master Balance:</strong> ${accountDetails.balance.toFixed(2)}</p>
                <p><strong>Last Action Log:</strong> {accountDetails.lastAction}</p>
            </div>

            {/* Rendering Child Component & Passing Props */}
            {/* 1. account object passed DOWN to Child */}
            {/* 2. handleChildUpdate function passed DOWN to Child */}
            <ChildAccountCard 
                account={accountDetails} 
                onUpdateAccount={handleChildUpdate} 
            />
        </div>
    );
}

const styles = {
    parentContainer: {
        maxWidth: '800px',
        margin: '0 auto',
        background: '#f8fafc',
        border: '1px solid #cbd5e1',
        borderRadius: '8px',
        padding: '25px',
        boxShadow: '0 4px 12px rgba(0,0,0,0.05)'
    },
    parentTitle: {
        color: '#0f172a',
        margin: '0 0 5px 0'
    },
    subText: {
        color: '#64748b',
        fontSize: '0.9rem',
        margin: '0 0 20px 0'
    },
    parentStatusCard: {
        background: '#e0f2fe',
        borderLeft: '4px solid #0284c7',
        padding: '15px',
        borderRadius: '4px',
        marginBottom: '20px'
    }
};
