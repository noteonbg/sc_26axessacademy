import React from 'react';

/**
 * EquipmentDetails Component (Feature Level Component)
 */
export default function EquipmentDetails({ equipment }) {
    if (!equipment) {
        return (
            <div className="details-column">
                <h2 className="section-title">Equipment Specifications</h2>
                <p>Select a machinery asset from the list above to inspect specs.</p>
            </div>
        );
    }

    return (
        <div className="details-column">
            <h2 className="section-title">Equipment Specifications</h2>
            <div className="detail-row"><strong>Asset ID :</strong> {equipment.id}</div>
            <div className="detail-row"><strong>Serial Number :</strong> {equipment.serialNo}</div>
            <div className="detail-row"><strong>Machine Name :</strong> {equipment.machineName}</div>
            <div className="detail-row"><strong>Department :</strong> {equipment.department}</div>
            <div className="detail-row"><strong>Operational Status :</strong> {equipment.status}</div>
            <div className="detail-row"><strong>Last Serviced :</strong> {equipment.lastServiceDate || "2026-01-01"}</div>
        </div>
    );
}
