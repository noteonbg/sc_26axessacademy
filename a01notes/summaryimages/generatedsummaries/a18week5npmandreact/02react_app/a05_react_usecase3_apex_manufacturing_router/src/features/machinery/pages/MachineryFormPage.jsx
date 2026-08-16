import React, { useState, useEffect } from 'react';

/**
 * MachineryFormPage Component (Feature Level Page)
 */
export default function MachineryFormPage({ machineryToEdit, onSave, onCancel }) {
    const [modelNumber, setModelNumber] = useState('');
    const [machineName, setMachineName] = useState('');
    const [assemblyLine, setAssemblyLine] = useState('');
    const [operatorName, setOperatorName] = useState('');

    useEffect(() => {
        if (machineryToEdit) {
            setModelNumber(machineryToEdit.modelNumber || '');
            setMachineName(machineryToEdit.machineName || '');
            setAssemblyLine(machineryToEdit.assemblyLine || '');
            setOperatorName(machineryToEdit.operatorName || '');
        }
    }, [machineryToEdit]);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!modelNumber.trim() || !machineName.trim() || !assemblyLine.trim()) {
            window.alert('Please complete all required fields.');
            return;
        }

        onSave({
            id: machineryToEdit ? machineryToEdit.id : undefined,
            modelNumber,
            machineName,
            assemblyLine,
            operatorName
        });
    };

    const isEditMode = !!machineryToEdit;

    return (
        <div className="form-card">
            <h2 className="page-heading">{isEditMode ? "Edit Machinery Asset" : "Register New Machinery"}</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Model Number</label>
                    <input 
                        type="text" 
                        value={modelNumber} 
                        onChange={(e) => setModelNumber(e.target.value)} 
                        placeholder="Enter Model Number"
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
                    <label>Assembly Line / Shopfloor</label>
                    <input 
                        type="text" 
                        value={assemblyLine} 
                        onChange={(e) => setAssemblyLine(e.target.value)} 
                        placeholder="Enter Assembly Line Name"
                    />
                </div>
                <div className="form-group">
                    <label>Lead Operator Name</label>
                    <input 
                        type="text" 
                        value={operatorName} 
                        onChange={(e) => setOperatorName(e.target.value)} 
                        placeholder="Enter Operator Name"
                    />
                </div>
                
                <div style={{ marginTop: '20px', display: 'flex', gap: '15px' }}>
                    <button type="submit" className="btn-primary">
                        {isEditMode ? "Update Asset" : "Register Machinery"}
                    </button>
                    <button type="button" className="btn-secondary" onClick={onCancel}>
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
}
