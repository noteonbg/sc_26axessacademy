import React, { useState } from 'react'; // Syntax: Import statement importing React and useState hook
import axios from 'axios'; // Syntax: Import statement importing Axios HTTP client library
import './App.css'; // Syntax: Import statement loading CSS stylesheet rules

/**
 * React Main App Component.
 */
function App() { // Syntax: Function component definition syntax
  // State Hook Syntax: useState() declares state variable and setter function
  const [length, setLength] = useState('5'); // Syntax: Declares 'length' state initialized to '5'
  const [breadth, setBreadth] = useState('4'); // Syntax: Declares 'breadth' state initialized to '4'

  const [result, setResult] = useState(null); // Syntax: Declares 'result' state initialized to null
  const [error, setError] = useState(null); // Syntax: Declares 'error' state initialized to null
  const [loading, setLoading] = useState(false); // Syntax: Declares 'loading' boolean state initialized to false

  /**
   * Async Arrow Function Syntax: Form submission handler method.
   */
  const handleCalculate = async (e) => { // Syntax: Async arrow function accepting event object
    e.preventDefault(); // Syntax: Prevents default browser form submit page reload
    setError(null); // Syntax: Resets error state
    setResult(null); // Syntax: Resets result state

    // Conditional Syntax: Checks empty input strings
    if (length === '' || breadth === '') { // If condition syntax: Strict equality (===) and logical OR (||)
      setError('Please enter both length and breadth values.');
      return;
    }

    // Object Literal Syntax: Constructs input Rectangle payload object
    const rectangleObject = {
      length: parseFloat(length), // Syntax: Converts length string to floating-point number
      breadth: parseFloat(breadth) // Syntax: Converts breadth string to floating-point number
    };

    setLoading(true); // Syntax: Sets loading state to true

    // Try-Catch-Finally Exception Handling Syntax
    try {
      // Axios POST API Call Syntax: Simple direct HTTP POST request without interceptors
      const response = await axios.post( // Syntax: 'await' pauses execution until promise resolves
        'http://localhost:8080/api/rectangle/calculate', // URL argument
        rectangleObject // Request payload object argument
      );

      // Axios Response Data Access Syntax
      setResult(response.data); // Syntax: response.data accesses JSON response body returned from Spring Boot
    } catch (err) {
      // Catch Syntax: Catches error thrown by Axios on HTTP 400/500 responses
      if (err.response && err.response.data && err.response.data.message) {
        setError(err.response.data.message); // Syntax: Accesses error message returned from GlobalExceptionHandler
      } else {
        setError('Network Error: Could not connect to Spring Boot backend at http://localhost:8080');
      }
    } finally {
      // Finally Syntax: Executes regardless of success or failure
      setLoading(false); // Syntax: Resets loading state to false
    }
  };

  // JSX Markup Return Syntax
  return (
    <div className="calculator-container">
      <div className="calculator-card">
        <h2>📐 Rectangle PA Calculator</h2>
        <p className="subtitle">Spring Boot & React POC (Perimeter & Area Calculation)</p>

        {/* JSX Form Event Syntax: onSubmit event handler binding */}
        <form onSubmit={handleCalculate}>
          <div className="form-group">
            <label htmlFor="length">Length:</label>
            {/* Controlled Component Syntax: value and onChange binding */}
            <input
              type="number"
              id="length"
              step="any"
              value={length}
              onChange={(e) => setLength(e.target.value)} // Event listener updating length state
              placeholder="Enter length (e.g. 5)"
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="breadth">Breadth:</label>
            {/* Controlled Component Syntax */}
            <input
              type="number"
              id="breadth"
              step="any"
              value={breadth}
              onChange={(e) => setBreadth(e.target.value)} // Event listener updating breadth state
              placeholder="Enter breadth (e.g. 4)"
              required
            />
          </div>

          {/* Action Buttons */}
          <div className="button-group">
            <button type="submit" className="btn btn-primary" disabled={loading}>
              {loading ? 'Calculating...' : 'Calculate PA'}
            </button>
            <button
              type="button"
              className="btn btn-warning"
              onClick={() => {
                setLength('0');
                setBreadth('0');
              }}
            >
              Set Both to 0 (Test Exception)
            </button>
          </div>
        </form>

        {/* Short-Circuit Conditional Rendering Syntax: {error && (...)} */}
        {error && (
          <div className="error-banner">
            <strong>❌ Exception Handled:</strong>
            <p>{error}</p>
          </div>
        )}

        {/* Short-Circuit Conditional Rendering Syntax: {result && (...)} */}
        {result && (
          <div className="result-card">
            <h3>✅ Calculated PA Response Object</h3>
            <div className="result-grid">
              <div className="result-box">
                <span className="result-label">Area ($\text{length} \times \text{breadth}$):</span>
                {/* Object Property Access Syntax: result.area */}
                <span className="result-value">{result.area}</span>
              </div>
              <div className="result-box">
                <span className="result-label">Perimeter ($2 \times (\text{length} + \text{breadth})$):</span>
                {/* Object Property Access Syntax: result.perimeter */}
                <span className="result-value">{result.perimeter}</span>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default App; // Export Syntax: Exports App component
