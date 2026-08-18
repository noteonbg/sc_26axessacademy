import React from 'react';

/**
 * AlertComponent
 * Interactive component displaying a button.
 * Clicking button triggers alert dialog "React is a great UI library"
 * I am alphabet Q here becuase I am just an event listener..
 */
export default function AlertComponent() {
    const handleButtonClick = () => {
        if (typeof window !== 'undefined') {
            window.alert("React is a great UI library is it");
        } else {
            console.log("[Node Execution] Event Triggered: 'React is a great UI library ok'");
        }
    };

    return (
        <div className="component-box">
            <button className="btn-custom" onClick={handleButtonClick}>
                Click me Please
            </button>
        </div>
    );
}
