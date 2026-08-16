import React from 'react';
import ReactDOM from 'react-dom/client';
import './styles/global.css'; // Importing Strategy 1 Global CSS first
import App from './App.jsx';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
