import React, { useState, useEffect } from 'react';
import Navbar from '../common/components/Navbar.jsx';
import HomePage from '../features/company/pages/HomePage.jsx';
import ApexCorpPage from '../features/company/pages/ApexCorpPage.jsx';
import PartsInventoryPage from '../features/parts/pages/PartsInventoryPage.jsx';
import MachineryListPage from '../features/machinery/pages/MachineryListPage.jsx';
import MachineryFormPage from '../features/machinery/pages/MachineryFormPage.jsx';
import MachineryDetailsPage from '../features/machinery/pages/MachineryDetailsPage.jsx';

import { 
    fetchMachineryList, 
    createMachinery, 
    updateMachinery, 
    deleteMachinery 
} from '../features/machinery/services/machineryApi.js';

export default function AppRouter() {
    const [currentRoute, setCurrentRoute] = useState('/machinery');
    const [routeParamId, setRouteParamId] = useState(null);
    const [machineryList, setMachineryList] = useState([]);

    useEffect(() => {
        fetchMachineryList().then(data => setMachineryList(data));
    }, []);

    const navigateTo = (path, paramId = null) => {
        setCurrentRoute(path);
        setRouteParamId(paramId);
    };

    const handleSave = async (machineData) => {
        if (machineData.id) {
            const updated = await updateMachinery(machineData);
            setMachineryList(prev => prev.map(m => m.id === updated.id ? { ...m, ...updated } : m));
        } else {
            const created = await createMachinery(machineData);
            setMachineryList(prev => [...prev, created]);
        }
        navigateTo('/machinery');
    };

    const handleDelete = async (id) => {
        const item = machineryList.find(m => m.id === id);
        if (window.confirm(`Are you sure you want to decommission machinery asset '${item.machineName}' (${item.modelNumber})?`)) {
            await deleteMachinery(id);
            setMachineryList(prev => prev.filter(m => m.id !== id));
        }
    };

    return (
        <div>
            <Navbar currentRoute={currentRoute} onNavigate={navigateTo} />

            <div className="content-container" style={{ maxWidth: '1000px', margin: '30px auto', padding: '0 20px' }}>
                {currentRoute === '/home' && <HomePage />}
                {currentRoute === '/parts' && <PartsInventoryPage />}
                {currentRoute === '/apex' && <ApexCorpPage />}

                {currentRoute === '/machinery' && (
                    <MachineryListPage 
                        machineryList={machineryList} 
                        onNavigate={navigateTo} 
                        onDelete={handleDelete} 
                    />
                )}

                {currentRoute === '/machinery/new' && (
                    <MachineryFormPage 
                        onSave={handleSave} 
                        onCancel={() => navigateTo('/machinery')} 
                    />
                )}

                {currentRoute === '/machinery/edit' && (
                    <MachineryFormPage 
                        machineryToEdit={machineryList.find(m => m.id === routeParamId)}
                        onSave={handleSave} 
                        onCancel={() => navigateTo('/machinery')} 
                    />
                )}

                {currentRoute === '/machinery/show' && (
                    <MachineryDetailsPage 
                        item={machineryList.find(m => m.id === routeParamId)} 
                        onBack={() => navigateTo('/machinery')} 
                    />
                )}
            </div>
        </div>
    );
}
