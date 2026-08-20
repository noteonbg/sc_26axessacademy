const express = require('express');
const cors = require('cors');

const app = express();
const PORT = 5000;

// Middleware
app.use(cors());
app.use(express.json());

// In-memory dataset initialized with max 3 rows for simple demonstration
let items = [
  { id: 1, name: "Alice Johnson", role: "Frontend Developer" },
  { id: 2, name: "Bob Smith", role: "Backend Engineer" },
  { id: 3, name: "Charlie Brown", role: "UI/UX Designer" }
];

let nextId = 4;

// Logger middleware for demonstration
app.use((req, res, next) => {
  console.log(`[SERVER LOG] ${req.method} ${req.url}`);
  next();
});

// 1. GET /api/items - Fetch all items (Status: 200 OK)
app.get('/api/items', (req, res) => {
  res.status(200).json(items);
});

// 2. GET /api/items/:id - Fetch single item by ID
app.get('/api/items/:id', (req, res) => {
  const id = parseInt(req.params.id, 10);
  const item = items.find(i => i.id === id);
  if (!item) {
    return res.status(404).json({ message: `Not Found: Item with ID ${id} does not exist.` });
  }
  res.status(200).json(item);
});

// 3. POST /api/items - Add new item
// Demonstrates Status 201 (Created), Status 400 (Bad Request), Status 409 (Conflict)
app.post('/api/items', (req, res) => {
  const { name, role } = req.body;
//do jdbc put that into table and reutn...
  // Validation: Check empty fields
  if (!name || !name.trim() || !role || !role.trim()) {
    return res.status(400).json({ message: "Bad Request: Both 'name' and 'role' fields are required." });
  }

  // Validation: Check duplicate name
  const existing = items.find(i => i.name.toLowerCase() === name.trim().toLowerCase());
  if (existing) {
    return res.status(409).json({ message: `Conflict: An item named '${name.trim()}' already exists.` });
  }

  const newItem = { id: nextId++, name: name.trim(), role: role.trim() };
  items.push(newItem);
  res.status(201).json(newItem);
});

// 4. PUT /api/items/:id - Update an existing item
// Demonstrates Status 200 (OK), Status 404 (Not Found), Status 400 (Bad Request)
app.put('/api/items/:id', (req, res) => {
  const id = parseInt(req.params.id, 10);
  const { name, role } = req.body;

  const itemIndex = items.findIndex(i => i.id === id);
  if (itemIndex === -1) {
    return res.status(404).json({ message: `Not Found: Item with ID ${id} does not exist.` });
  }

  if (!name || !name.trim() || !role || !role.trim()) {
    return res.status(400).json({ message: "Bad Request: Updated 'name' and 'role' cannot be blank." });
  }

  items[itemIndex] = { id, name: name.trim(), role: role.trim() };
  res.status(200).json(items[itemIndex]);
});

// 5. DELETE /api/items/:id - Delete an item by ID
// Demonstrates Status 204 (No Content) & Status 404 (Not Found)
app.delete('/api/items/:id', (req, res) => {
  const id = parseInt(req.params.id, 10);
  const itemIndex = items.findIndex(i => i.id === id);

  if (itemIndex === -1) {
    return res.status(404).json({ message: `Not Found: Cannot delete item with ID ${id} because it does not exist.` });
  }

  items.splice(itemIndex, 1);
  res.status(204).send(); // 204 No Content has no body
});

// Demo routes to easily force specific HTTP error status codes during presentation
app.get('/api/test-error/400', (req, res) => {
  res.status(400).json({ message: "Demo 400 Bad Request: Triggered client-side input validation error." });
});

app.get('/api/test-error/404', (req, res) => {
  res.status(404).json({ message: "Demo 404 Not Found: The requested resource endpoint does not exist." });
});

app.get('/api/test-error/500', (req, res) => {
  res.status(500).json({ message: "Demo 500 Internal Server Error: Simulated backend crash/database failure." });
});

// Start Server
app.listen(PORT, () => {
  console.log(`=================================================`);
  console.log(` Express Mock REST API Server running at: http://localhost:${PORT}`);
  console.log(` Endpoints available:`);
  console.log(`   GET    /api/items`);
  console.log(`   POST   /api/items`);
  console.log(`   PUT    /api/items/:id`);
  console.log(`   DELETE /api/items/:id`);
  console.log(`=================================================`);
});
