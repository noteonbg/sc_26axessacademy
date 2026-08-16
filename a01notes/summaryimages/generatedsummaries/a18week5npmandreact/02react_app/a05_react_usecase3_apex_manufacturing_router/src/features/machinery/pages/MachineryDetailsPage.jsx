import React from 'react';

/**
 * MachineryDetailsPage Component (Feature Level Page)
 */
export default function MachineryDetailsPage({ item, onBack }) {
    if (!item) {
        return (
            <div>
                <p>Machinery asset not found.</p>
                <button className="btn-secondary" onClick={onBack}>Back to List</button>
            </div>
        );
    }

    const components = item.components || [];

    return (
        <div>
            <div style={{ marginBottom: '20px' }}>
                <a className="action-link" onClick={onBack} style={{ fontSize: '1rem', fontWeight: '600' }}>
                    &larr; Back to Machinery List
                </a>
            </div>

            <div className="details-card" style={{ background: '#f8fafc', padding: '25px', borderRadius: '8px', border: '1px solid #cbd5e1', marginBottom: '30px' }}>
                <h2 className="page-heading" style={{ marginTop: 0 }}>Machinery Asset Specification</h2>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px' }}>
                    <div><strong>Asset ID:</strong> {item.id}</div>
                    <div><strong>Model Number:</strong> {item.modelNumber}</div>
                    <div><strong>Machine Name:</strong> {item.machineName}</div>
                    <div><strong>Assembly Line:</strong> {item.assemblyLine}</div>
                    <div><strong>Lead Operator:</strong> {item.operatorName || "Unassigned"}</div>
                    <div><strong>Installed Components Count:</strong> {components.length}</div>
                </div>
            </div>

            <h3 className="section-title">Installed Assembly Components & Spare Parts</h3>
            <table className="bank-table">
                <thead>
                    <tr>
                        <th>Part Number</th>
                        <th>Part Name</th>
                        <th>Type</th>
                        <th>Quantity</th>
                        <th>Maintenance SLA (Hours)</th>
                    </tr>
                </thead>
                <tbody>
                    {components.length > 0 ? (
                        components.map((part, idx) => (
                            <tr key={idx}>
                                <td><strong>{part.partNumber}</strong></td>
                                <td>{part.partName}</td>
                                <td><span className="badge-type">{part.type}</span></td>
                                <td>{part.quantity}</td>
                                <td>{part.maintenanceHours} hrs</td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="5" style={{ textAlign: 'center', color: '#64748b' }}>
                                No assembly parts registered for this machinery.
                            </td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}
