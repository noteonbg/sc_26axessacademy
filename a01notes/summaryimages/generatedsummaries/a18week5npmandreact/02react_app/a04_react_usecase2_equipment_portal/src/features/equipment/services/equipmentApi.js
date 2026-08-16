import { initialEquipmentMockData } from '../mockData/equipmentMockData.js';

/**
 * Decoupled API Service Module for Equipment Feature
 * Demonstrates UI-First / Contract-Driven Development:
 * - When USE_MOCK = true, returns local mock data.
 * - When USE_MOCK = false, connects to Spring Boot backend API.
 */
const USE_MOCK = true;
const BASE_URL = 'http://localhost:8080/api/v1/equipment';

let localMemoryData = [...initialEquipmentMockData];

export async function fetchEquipmentList() {
    if (USE_MOCK) {
        return Promise.resolve(localMemoryData);
    } else {
        const res = await fetch(BASE_URL);
        return await res.json();
    }
}

export async function createEquipment(newEquipmentData) {
    if (USE_MOCK) {
        const nextId = localMemoryData.length > 0 ? Math.max(...localMemoryData.map(e => e.id)) + 1 : 1;
        const createdObj = { id: nextId, ...newEquipmentData };
        localMemoryData = [...localMemoryData, createdObj];
        return Promise.resolve(createdObj);
    } else {
        const res = await fetch(BASE_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newEquipmentData)
        });
        return await res.json();
    }
}
