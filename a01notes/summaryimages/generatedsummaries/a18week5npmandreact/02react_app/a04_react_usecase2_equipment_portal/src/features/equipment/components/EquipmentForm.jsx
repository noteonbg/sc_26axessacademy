import React, { useState } from 'react';

/**
 * EquipmentForm Component (Feature Level Component)
 */
export default function EquipmentForm({ onAddEquipment }) {
    const [serialNo, setSerialNo] = useState('');
    const [machineName, setMachineName] = useState('');
    const [department, setDepartment] = useState('');
    const [status, setStatus] = useState('ACTIVE');

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!serialNo.trim() || !machineName.trim() || !department.trim()) {
            window.alert('Please fill in all mandatory equipment details.');
            return;
        }

        onAddEquipment({
            serialNo,
            machineName,
            department,
            status,
            lastServiceDate: new Date().toISOString().split('T')[0]
        });

        setSerialNo('');
        setMachineName('');
        setDepartment('');
        setStatus('ACTIVE');
    };

    return (
        <div className="form-column">
            <h2 className="section-title">Add New Machinery</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Serial Number</label>
                    <input 
                        type="text" 
                        value={serialNo} 
                        onChange={(e) => setSerialNo(e.target.value)} 
                        placeholder="Enter Serial Number"
                    />
                </div>
                <div className="form-group">
                    <label>Machine Name</label>
                    <input 
                        type="text" 
                        value={machineName} 
                        onChange={(e) => setMachineName(e.target.value)} 
                        placeholder="Enter Machine Name"
                    />
                </div>
                <div className="form-group">
                    <label>Department / Plant Shopfloor</label>
                    <input 
                        type="text" 
                        value={department} 
                        onChange={(e) => setDepartment(e.target.value)} 
                        placeholder="Enter Department Name"
                    />
                </div>
                <div className="form-group">
                    <label>Operational Status</label>
                    <select value={status} onChange={(e) => setStatus(e.target.value)}>
                        <option value="ACTIVE">ACTIVE</option>
                        <option value="UNDER_MAINTENANCE">UNDER_MAINTENANCE</option>
                        <option value="STANDBY">STANDBY</option>
                    </select>
                </div>
                <button type="submit" className="btn-submit">Register Equipment</button>
            </form>
        </div>
    );
}
