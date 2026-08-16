import React, { useState, useEffect } from 'react';
import EquipmentList from '../components/EquipmentList.jsx';
import EquipmentForm from '../components/EquipmentForm.jsx';
import EquipmentDetails from '../components/EquipmentDetails.jsx';
import { fetchEquipmentList, createEquipment } from '../services/equipmentApi.js';

/**
 * Feature Page Component: EquipmentPortalPage
 * Fetches data via equipmentApi service and orchestrates child components
 */
export default function EquipmentPortalPage() {
    const [equipmentList, setEquipmentList] = useState([]);
    const [selectedEquipment, setSelectedEquipment] = useState(null);

    useEffect(() => {
        fetchEquipmentList().then(data => {
            setEquipmentList(data);
            if (data && data.length > 0) {
                setSelectedEquipment(data[0]);
            }
        });
    }, []);

    const handleAddEquipment = async (newEquipmentData) => {
        const createdObj = await createEquipment(newEquipmentData);
        setEquipmentList(prev => [...prev, createdObj]);
        setSelectedEquipment(createdObj);
    };

    return (
        <div className="container">
            <EquipmentList 
                equipmentList={equipmentList} 
                selectedEquipmentId={selectedEquipment ? selectedEquipment.id : null}
                onSelectEquipment={setSelectedEquipment}
            />

            <div className="bottom-layout" style={{ display: 'flex', gap: '40px', marginTop: '30px' }}>
                <EquipmentForm onAddEquipment={handleAddEquipment} />
                <EquipmentDetails equipment={selectedEquipment} />
            </div>
        </div>
    );
}
