import React from 'react';
import { Link } from 'react-router-dom';

function Home() {
  return (
    <div>
      <h1>Welcome to React Routing & Axios REST API Demo</h1>
      <p>
        This simple demonstration application showcases <strong>React Router v6</strong> for client-side navigation,
        the <strong>useEffect Hook</strong> for side effects, and <strong>Axios</strong> for making asynchronous REST API requests (GET, POST, PUT, DELETE) to an HTTP server.
      </p>

      <div className="card">
        <h2>Demo Features Summary</h2>
        <ul style={{ paddingLeft: '1.2rem', lineHeight: '1.8' }}>
          <li><strong>React Routing:</strong> Navigate seamlessly between <code>/</code>, <code>/items</code>, <code>/useeffect</code>, and <code>/about</code> without page reloads.</li>
          <li><strong>useEffect Hook:</strong> Interactive demonstration of side-effects, dependency array triggers, browser document title updates, and timer cleanup functions.</li>
          <li><strong>Axios HTTP Client:</strong> Performs full CRUD operations on an in-memory dataset (max 3 initial rows).</li>
          <li><strong>HTTP Status Code Demonstrations:</strong>
            <ul>
              <li><span className="badge badge-success">200 OK</span> – Successful GET or PUT update</li>
              <li><span className="badge badge-success">201 Created</span> – Successful POST creation</li>
              <li><span className="badge badge-success">204 No Content</span> – Successful DELETE operation</li>
              <li><span className="badge badge-error">400 Bad Request</span> – Client input validation error</li>
              <li><span className="badge badge-error">404 Not Found</span> – Requesting non-existent resource ID</li>
              <li><span className="badge badge-error">409 Conflict</span> – Creating item with duplicate name</li>
            </ul>
          </li>
        </ul>
      </div>

      <div style={{ marginTop: '1.5rem', display: 'flex', gap: '1rem', justifyContent: 'center' }}>
        <Link to="/items" className="btn btn-primary" style={{ textDecoration: 'none' }}>
          Launch Items Manager (CRUD Demo) &rarr;
        </Link>
        <Link to="/useeffect" className="btn btn-success" style={{ textDecoration: 'none' }}>
          Launch useEffect Demo &rarr;
        </Link>
      </div>
    </div>
  );
}

export default Home;
