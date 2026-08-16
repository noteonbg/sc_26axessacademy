import React from 'react';
import Header from './components/Header/Header.jsx';
import ProductCard from './components/ProductCard/ProductCard.jsx';
import DynamicBadge from './components/DynamicBadge/DynamicBadge.jsx';

export default function App() {
    return (
        <div>
            {/* Strategy 2 Component: Header */}
            <Header />

            {/* Strategy 1 Container: Global CSS (.global-container) */}
            <div className="global-container">
                <h2 className="global-heading">CSS Architecture Across React Website Components</h2>

                <div style={{ marginBottom: '20px' }}>
                    <h3>Strategy 4: Dynamic Inline Styles</h3>
                    <div style={{ display: 'flex', gap: '15px' }}>
                        <DynamicBadge status="ACTIVE" />
                        <DynamicBadge status="MAINTENANCE" />
                    </div>
                </div>

                <div>
                    <h3>Strategy 3: CSS Modules (Scoped Product Cards)</h3>
                    <ProductCard 
                        title="CNC Precision Milling Machine" 
                        description="Heavy duty shopfloor machining asset with automated tool changers."
                        price="45,000"
                    />
                    <ProductCard 
                        title="Robotic Assembly Arm 6-Axis" 
                        description="High-precision industrial robotic arm for automated component assembly."
                        price="32,000"
                    />
                </div>
            </div>
        </div>
    );
}
