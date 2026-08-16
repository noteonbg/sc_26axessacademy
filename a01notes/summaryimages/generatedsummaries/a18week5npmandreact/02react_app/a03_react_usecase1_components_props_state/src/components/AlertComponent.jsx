import React from 'react';

/**
 * AlertComponent
 * Interactive component displaying a button.
 * Clicking button triggers alert dialog "React is a great UI library"
 */
export default function AlertComponent() {
    const handleButtonClick = () => {
        if (typeof window !== 'undefined') {
            window.alert("React is a great UI library");
        } else {
            console.log("[Node Execution] Event Triggered: 'React is a great UI library'");
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
