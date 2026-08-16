import React from 'react';

/**
 * Global Common Navbar Component
 */
export default function Navbar({ currentRoute, onNavigate }) {
    return (
        <nav className="navbar">
            <a className="navbar-brand" onClick={() => onNavigate('/home')} style={{ cursor: 'pointer' }}>
                Apex Manufacturing
            </a>
            <ul className="nav-links">
                <li>
                    <a className={currentRoute === '/home' ? "active" : ""} onClick={() => onNavigate('/home')}>Home</a>
                </li>
                <li>
                    <a className={currentRoute.startsWith('/machinery') ? "active" : ""} onClick={() => onNavigate('/machinery')}>Machinery</a>
                </li>
                <li>
                    <a className={currentRoute === '/parts' ? "active" : ""} onClick={() => onNavigate('/parts')}>Parts Inventory</a>
                </li>
                <li>
                    <a className={currentRoute === '/apex' ? "active" : ""} onClick={() => onNavigate('/apex')}>Apex Corp</a>
                </li>
            </ul>
        </nav>
    );
}
