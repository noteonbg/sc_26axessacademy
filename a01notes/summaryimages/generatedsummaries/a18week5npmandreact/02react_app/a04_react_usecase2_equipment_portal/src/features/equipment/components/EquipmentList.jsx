import React from 'react';
import StatusBadge from '../../../common/components/StatusBadge.jsx';

/**
 * EquipmentList Component (Feature Level Component)
 */
export default function EquipmentList({ equipmentList, selectedEquipmentId, onSelectEquipment }) {
    return (
        <div>
            <h2 className="section-title">Plant Equipment Assets</h2>
            <table className="custom-table">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Serial No</th>
                        <th>Machine Name</th>
                        <th>Department</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    {equipmentList.map((item) => (
                        <tr 
                            key={item.id}
                            className={selectedEquipmentId === item.id ? "selected-row" : ""}
                            onClick={() => onSelectEquipment(item)}
                            style={{ cursor: 'pointer' }}
                        >
                            <td>{item.id}</td>
                            <td>{item.serialNo}</td>
                            <td>{item.machineName}</td>
                            <td>{item.department}</td>
                            <td>
                                <StatusBadge status={item.status} />
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
