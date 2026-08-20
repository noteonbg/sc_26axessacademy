import React from 'react';

function About() {
  return (
    <div>
      <h1>About This Demo</h1>
      <p>
        This demo project is built using standard <strong>Create React App (CRA)</strong> folder conventions
        with a lightweight Express backend mock server.
      </p>

      <div className="card">
        <h3>Architecture Overview</h3>
        <table className="data-table">
          <thead>
            <tr>
              <th>Layer</th>
              <th>Technology</th>
              <th>Port / Path</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Frontend UI</td>
              <td>React 18 + React Router DOM v6</td>
              <td><code>http://localhost:3000</code></td>
            </tr>
            <tr>
              <td>HTTP REST Client</td>
              <td>Axios Instance</td>
              <td><code>src/services/api.js</code></td>
            </tr>
            <tr>
              <td>Backend Mock Server</td>
              <td>Node.js + Express + CORS</td>
              <td><code>http://localhost:5000/api</code></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default About;
