import React, { useState } from 'react';

/**
 * Strategy 4: Dynamic Inline Styles for State-Driven UI Overrides
 */
export default function DynamicBadge({ status }) {
    const [isHovered, setIsHovered] = useState(false);

    // Dynamic style calculation based on status prop & state
    const getBadgeStyle = () => {
        let bgColor = '#e2e8f0';
        let textColor = '#475569';

        if (status === 'ACTIVE') {
            bgColor = isHovered ? '#15803d' : '#dcfce7';
            textColor = isHovered ? '#ffffff' : '#15803d';
        } else if (status === 'MAINTENANCE') {
            bgColor = isHovered ? '#b91c1c' : '#fee2e2';
            textColor = isHovered ? '#ffffff' : '#b91c1c';
        }

        return {
            backgroundColor: bgColor,
            color: textColor,
            padding: '4px 12px',
            borderRadius: '12px',
            fontSize: '0.85rem',
            fontWeight: '700',
            cursor: 'pointer',
            transition: 'all 0.2s ease-in-out',
            display: 'inline-block'
        };
    };

    return (
        <span 
            style={getBadgeStyle()}
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            Status: {status}
        </span>
    );
}
