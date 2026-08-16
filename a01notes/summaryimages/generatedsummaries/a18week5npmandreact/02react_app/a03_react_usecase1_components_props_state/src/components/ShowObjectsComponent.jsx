import React, { useState } from 'react';

/**
 * ShowObjectsComponent (React Use Case 1)
 * Demonstrates state-driven object visibility on button click.
 */
export default function ShowObjectsComponent() {
    const [showObjects, setShowObjects] = useState(false);

    // List of sample JavaScript objects (Manufacturing Equipment)
    const equipmentObjects = [
        { id: 101, name: "CNC Milling Machine", serial: "CNC-2024-X1", dept: "Machining", status: "Active" },
        { id: 102, name: "Robotic Assembly Arm", serial: "ROB-650-ARM", dept: "Automated Assembly", status: "Active" },
        { id: 103, name: "Hydraulic Press 50T", serial: "HYD-PRESS-50", dept: "Stamping", status: "Under Maintenance" }
    ];

    const toggleDisplay = () => {
        setShowObjects(prev => !prev);
    };

    return (
        <div className="component-box">
            <button className="btn-custom" onClick={toggleDisplay} style={{ marginBottom: '15px' }}>
                {showObjects ? "Hide Manufacturing Objects" : "Show Manufacturing Objects"}
            </button>

            {showObjects && (
                <div style={{ background: '#f8fafc', padding: '15px', borderRadius: '6px', border: '1px solid #cbd5e1' }}>
                    <h4 style={{ margin: '0 0 10px 0', color: '#1e293b' }}>Displayed Plant Equipment Objects:</h4>
                    <div style={{ display: 'grid', gap: '10px' }}>
                        {equipmentObjects.map((item) => (
                            <div key={item.id} style={{ background: '#ffffff', padding: '10px 14px', borderRadius: '4px', border: '1px solid #e2e8f0', fontSize: '0.9rem' }}>
                                <strong>[ID {item.id}] {item.name}</strong> &mdash; 
                                <span style={{ color: '#64748b' }}> Serial: {item.serial} | Dept: {item.dept} | Status: <strong>{item.status}</strong></span>
                            </div>
                        ))}
                    </div>
                </div>
            )}
        </div>
    );
}
