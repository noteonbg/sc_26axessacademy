import React from 'react';
import styles from './ProductCard.module.css'; // Importing Scoped CSS Modules

export default function ProductCard({ title, description, price }) {
    return (
        <div className={styles.card}>
            <h3 className={styles.title}>{title}</h3>
            <p className={styles.description}>{description}</p>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <strong style={{ color: '#0f172a' }}>${price}</strong>
                <button className={styles.button}>View Item</button>
            </div>
        </div>
    );
}
