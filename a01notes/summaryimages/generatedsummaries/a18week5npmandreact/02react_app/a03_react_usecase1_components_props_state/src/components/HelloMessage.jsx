import React from 'react';

/**
 * HelloMessage Component
 * Receives 2 props: name and message.
 * Default prop requirement: If message is missing, fallback to "Hi, Hello"
 */
export default function HelloMessage({ name, message = "Hi, Hello" }) {
    return (
        <div className="component-box">
            Message from {name} : {message}
        </div>
    );
}
