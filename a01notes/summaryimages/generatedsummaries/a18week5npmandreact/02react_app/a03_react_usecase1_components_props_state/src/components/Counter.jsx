import React, { useState, useEffect } from 'react';

/**
 * Counter Component
 * Displays time elapsed in seconds using useState and setInterval.
 */
export default function Counter() {
    const [seconds, setSeconds] = useState(0);  
   
    /*
    const freak=useState(0); 
    let cp = freak[0];
    let changingcp=freak[1]; // function defined by react and assigned to us., we have to call it
    //when cp changes..
*/




    useEffect(() => {
        const intervalId = setInterval(() => {
            setSeconds(prev => prev + 1);
        }, 1000);

        // Cleanup timer on unmount
        return () => clearInterval(intervalId);
    }, []);

    return (
        <div className="component-box">
            Time Elapsed : {seconds}
        </div>
    );
}
