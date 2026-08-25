import React from 'react'; // Import React core library
import ReactDOM from 'react-dom/client'; // Import ReactDOM client API for rendering
import './index.css'; // Import global CSS stylesheet
import App from './App'; // Import root App component

// Create React 18 rendering root target bound to HTML element with id="root"
const root = ReactDOM.createRoot(document.getElementById('root'));

// Render React component tree into DOM
root.render(
  <React.StrictMode> {/* Enable strict mode checks during development */}
    <App /> {/* Mount root App component */}
  </React.StrictMode>
);
