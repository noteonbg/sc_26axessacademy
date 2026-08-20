import React from 'react';
import { Routes, Route, NavLink } from 'react-router-dom';
import Home from './pages/Home';
import ItemsManager from './pages/ItemsManager';
import UseEffectDemo from './pages/UseEffectDemo';
import About from './pages/About';

function App() {
  return (
    <div className="App">
      {/* Navigation Bar */}
      <nav className="navbar">
        <div className="brand">React Routing & Axios REST Demo</div>
        <ul className="nav-links">
          <li>
            <NavLink to="/" end>Country</NavLink>
          </li>
          <li>
            <NavLink to="/items">Items Manager (CRUD)</NavLink>
          </li>
          <li>
            <NavLink to="/useeffect">useEffect Demo</NavLink>
          </li>
          <li>
            <NavLink to="/about">About</NavLink>
          </li>
        </ul>
      </nav>

      {/* Route Views */}
      <main className="container">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/items" element={<ItemsManager />} />
          <Route path="/useeffect" element={<UseEffectDemo />} />
          <Route path="/about" element={<About />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;
