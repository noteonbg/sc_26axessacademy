import React from 'react';

/**
 * Common StatusBadge Component (Shared UI)
 */
export default function StatusBadge({ status }) {
    const statusLower = (status || '').toLowerCase();
    return (
        <span className={`status-badge status-${statusLower}`}>
            {status}
        </span>
    );
}
