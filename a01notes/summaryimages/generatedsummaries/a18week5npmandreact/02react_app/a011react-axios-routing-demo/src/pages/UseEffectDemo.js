import React, { useState, useEffect } from 'react';

function UseEffectDemo() {
  const [count, setCount] = useState(0);
  const [timer, setTimer] = useState(0);
  const [log, setLog] = useState([]);

  // Helper to add log entries for demonstration
  const addLog = (message) => {
    setLog(prev => [`[${new Date().toLocaleTimeString()}] ${message}`, ...prev.slice(0, 4)]);
  };

  // 1. useEffect running ONCE on component mount (empty dependency array [])
  useEffect(() => {
    addLog('1. Mount Effect: Component mounted for the first time');

    // 3. Cleanup function demonstration (timer)
    const interval = setInterval(() => {
      setTimer(t => t + 1);
    }, 1000);

    // Cleanup when component unmounts
    return () => {
      clearInterval(interval);
      console.log('Cleanup: Interval timer cleared');
    };
  }, []);

  // 2. useEffect running whenever [count] state changes
  useEffect(() => {
    // Update document title dynamically
    document.title = `Count: ${count} | React Demo`;
    addLog(`2. Dependency Effect: Count changed to ${count}`);
  }, [count]);

  return (
    <div>
      <h1>useEffect Hook Demonstration</h1>
      <p>
        The <code>useEffect</code> hook lets you perform side effects (data fetching, DOM updates, timers) in functional components.
      </p>

      {/* Example 1: Dependency Array Effect */}
      <div className="card">
        <h3>Example 1: Effect Triggered on State Change <code>[count]</code></h3>
        <p>Current Count: <strong style={{ fontSize: '1.4rem', color: '#2196f3' }}>{count}</strong></p>
        <div style={{ display: 'flex', gap: '0.5rem' }}>
          <button onClick={() => setCount(count + 1)} className="btn btn-primary">
            Increment Count (+1)
          </button>
          <button onClick={() => setCount(0)} className="btn btn-secondary">
            Reset Count
          </button>
        </div>
        <p style={{ marginTop: '0.8rem', fontSize: '0.9rem', color: '#666' }}>
          <em>Notice how the document title tab in your browser updates to <strong>Count: {count}</strong> automatically.</em>
        </p>
      </div>

      {/* Example 2: Mount & Cleanup Effect */}
      <div className="card">
        <h3>Example 2: Mount & Cleanup Effect <code>[]</code></h3>
        <p>Timer running since mount: <strong style={{ fontSize: '1.4rem', color: '#4caf50' }}>{timer} seconds</strong></p>
        <p style={{ fontSize: '0.9rem', color: '#666' }}>
          Starts a <code>setInterval</code> on component mount and clears it in the cleanup function when unmounting.
        </p>
      </div>

      {/* Log Visualizer */}
      <div className="response-logger">
        <div className="response-header">
          <span><strong>useEffect Event Log</strong></span>
          <span className="badge badge-info">Side Effects</span>
        </div>
        <div className="code-block">
          {log.map((item, idx) => (
            <div key={idx}>{item}</div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default UseEffectDemo;
