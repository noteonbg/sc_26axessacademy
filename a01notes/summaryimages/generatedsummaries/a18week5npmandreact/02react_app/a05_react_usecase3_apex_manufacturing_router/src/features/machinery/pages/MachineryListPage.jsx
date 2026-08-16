import React from 'react';

/**
 * MachineryListPage Component (Feature Level Page)
 */
export default function MachineryListPage({ machineryList, onNavigate, onDelete }) {
    return (
        <div>
            <div style={{ marginBottom: '15px' }}>
                <button 
                    className="btn-primary"
                    onClick={() => onNavigate('/machinery/new')}
                >
                    + Register New Machinery
                </button>
            </div>

            <h2 className="page-heading">Plant Machinery Assets</h2>
            <table className="bank-table">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Model Number</th>
                        <th>Machine Name</th>
                        <th>Assembly Line</th>
                        <th>Operator</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {machineryList.map((item) => (
                        <tr key={item.id}>
                            <td>{item.id}</td>
                            <td>{item.modelNumber}</td>
                            <td>{item.machineName}</td>
                            <td>{item.assemblyLine}</td>
                            <td>{item.operatorName}</td>
                            <td>
                                <a className="action-link" onClick={() => onNavigate('/machinery/show', item.id)}>Show</a>
                                <a className="action-link" onClick={() => onNavigate('/machinery/edit', item.id)}>Edit</a>
                                <a className="action-link action-danger" onClick={() => onDelete(item.id)}>Decommission</a>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
