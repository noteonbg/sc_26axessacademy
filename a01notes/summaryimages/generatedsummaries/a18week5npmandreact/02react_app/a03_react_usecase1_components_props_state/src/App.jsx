import React from 'react';
import HelloWorld from './components/HelloWorld.jsx';
import HelloMessage from './components/HelloMessage.jsx';
import Counter from './components/Counter.jsx';
import AlertComponent from './components/AlertComponent.jsx';
import ShowObjectsComponent from './components/ShowObjectsComponent.jsx';

/**
 * Main App Component matching PDF Slide 7-9 layout specifications
 * - Title h3: "UseCase 1 - Components, Props and States"
 * - 100px left and right page margins
 * - Added: Interactive Show Objects on Button Click Example
 */
export default function App() {
    return (
        <div className="page-wrapper" style={{ marginLeft: '100px', marginRight: '100px' }}>
            <h3 className="page-title">UseCase 1 - Components, Props and States</h3>

            {/* 1. HelloWorld Component */}
            <div className="section-block">
                <HelloWorld />
            </div>

            {/* 2. Single Prop */}
            <div className="section-block">
                <div className="section-header">Single Prop</div>
                <HelloMessage name="Euler" />
            </div>

            {/* 3. Multiple Props */}
            <div className="section-block">
                <div className="section-header">Multiple Props</div>
                <HelloMessage name="Ramanujam" message="I got this in my dreams" />
            </div>

            {/* 4. State and Virtual DOM */}
            <div className="section-block">
                <div className="section-header">State and Virtual DOM</div>
                <Counter />
            </div>

            {/* 5. Interactive Component - Event Handling */}
            <div className="section-block">
                <div className="section-header">Interactive Component - Event Handling</div>
                <AlertComponent />
            </div>

            {/* 6. Interactive Component - Show Objects on Button Click */}
            <div className="section-block">
                <div className="section-header">Interactive State - Show Objects on Button Click</div>
                <ShowObjectsComponent />
            </div>
        </div>
    );
}
