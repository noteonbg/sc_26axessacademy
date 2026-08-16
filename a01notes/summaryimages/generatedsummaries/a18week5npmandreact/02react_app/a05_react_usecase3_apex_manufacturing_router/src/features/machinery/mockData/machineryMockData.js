/**
 * API Contract Mock Data for Machinery Feature Module
 */
export const initialMachineryMockData = [
  {
    "id": 1,
    "modelNumber": "CNC-800-HEAVY",
    "machineName": "Industrial CNC Lathe",
    "assemblyLine": "Line 1 - Machining",
    "operatorName": "Ramesh",
    "components": [
      { "partNumber": "P-101", "partName": "Heavy Duty Spindle Unit", "type": "ROTARY", "quantity": 1, "maintenanceHours": 500 },
      { "partNumber": "P-102", "partName": "Servo Drive Motor 15kW", "type": "ELECTRICAL", "quantity": 2, "maintenanceHours": 1200 },
      { "partNumber": "P-103", "partName": "Precision Hydraulic Chuck", "type": "HYDRAULIC", "quantity": 1, "maintenanceHours": 300 }
    ]
  },
  {
    "id": 2,
    "modelNumber": "PLS-3000-CUT",
    "machineName": "Automated Plasma Cutter",
    "assemblyLine": "Line 2 - Heavy Fabrication",
    "operatorName": "Suresh",
    "components": [
      { "partNumber": "P-201", "partName": "High-Temp Torch Nozzle Assembly", "type": "CONSUMABLE", "quantity": 4, "maintenanceHours": 150 },
      { "partNumber": "P-202", "partName": "Dual Axis Gantry Controller", "type": "ELECTRONIC", "quantity": 1, "maintenanceHours": 2000 }
    ]
  },
  {
    "id": 3,
    "modelNumber": "WLD-BOT-X6",
    "machineName": "Robotic Spot Welding Station",
    "assemblyLine": "Line 3 - Chassis Welding",
    "operatorName": "Ramesh",
    "components": [
      { "partNumber": "P-301", "partName": "6-Axis Welding Gun Tip", "type": "ROBOTIC", "quantity": 2, "maintenanceHours": 250 }
    ]
  }
];
