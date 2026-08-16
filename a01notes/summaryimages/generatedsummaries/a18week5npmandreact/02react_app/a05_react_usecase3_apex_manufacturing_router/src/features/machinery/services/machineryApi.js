import { initialMachineryMockData } from '../mockData/machineryMockData.js';

/**
 * Decoupled API Service Module for Machinery Feature (Dev B Slice)
 * Demonstrates UI-First / Contract-Driven Development:
 * - When USE_MOCK = true, returns local mock data.
 * - When USE_MOCK = false, connects to Spring Boot backend API.
 */
const USE_MOCK = true;
const BASE_URL = 'http://localhost:8080/api/v1/machinery';

let localMemoryData = [...initialMachineryMockData];

export async function fetchMachineryList() {
    if (USE_MOCK) {
        return Promise.resolve(localMemoryData);
    } else {
        const res = await fetch(BASE_URL);
        return await res.json();
    }
}

export async function createMachinery(newMachineData) {
    if (USE_MOCK) {
        const nextId = localMemoryData.length > 0 ? Math.max(...localMemoryData.map(m => m.id)) + 1 : 1;
        const createdObj = {
            id: nextId,
            ...newMachineData,
            components: [
                { partNumber: "P-" + Math.floor(100 + Math.random() * 900), partName: "Standard Maintenance Unit", type: "GENERAL", quantity: 1, maintenanceHours: 500 }
            ]
        };
        localMemoryData = [...localMemoryData, createdObj];
        return Promise.resolve(createdObj);
    } else {
        const res = await fetch(BASE_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newMachineData)
        });
        return await res.json();
    }
}

export async function updateMachinery(updatedMachineData) {
    if (USE_MOCK) {
        localMemoryData = localMemoryData.map(m => m.id === updatedMachineData.id ? { ...m, ...updatedMachineData } : m);
        return Promise.resolve(updatedMachineData);
    } else {
        const res = await fetch(`${BASE_URL}/${updatedMachineData.id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(updatedMachineData)
        });
        return await res.json();
    }
}

export async function deleteMachinery(id) {
    if (USE_MOCK) {
        localMemoryData = localMemoryData.filter(m => m.id !== id);
        return Promise.resolve({ success: true, id });
    } else {
        const res = await fetch(`${BASE_URL}/${id}`, { method: 'DELETE' });
        return await res.json();
    }
}
