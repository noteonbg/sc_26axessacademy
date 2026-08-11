/**
 * ============================================================================
 * MODULE 5: EVENTS AND EVENT HANDLERS - JAVASCRIPT & REACT SYNTHETIC EVENTS
 * ============================================================================
 * Topics Covered:
 * - Mouse Events: click, dblclick, mouseenter, mouseleave
 * - Keyboard Events: keydown, keyup, keypress
 * - Form Events: submit, change, focus, blur
 * - Window/Document Events: load, scroll
 * - Event Delegation & Registration via addEventListener()
 * ============================================================================
 */

'use strict';

//goto jupiter and then moon and come back
function logEvent(message) {
    const timestamp = new Date().toLocaleTimeString();
    const formatted = `[${timestamp}] ${message}`;
    console.log(formatted);

    const logContainer = document.getElementById("eventConsole");
    if (logContainer) {
        const logLine = document.createElement("div");
        logLine.textContent = formatted;
        logContainer.appendChild(logLine);
        logContainer.scrollTop = logContainer.scrollHeight;
    }
}

function clearEventLogs() {
    const logContainer = document.getElementById("eventConsole");
    if (logContainer) logContainer.innerHTML = "";
}

// ----------------------------------------------------------------------------
// 1. Window Load Event (Window / Document Events)
// ----------------------------------------------------------------------------
window.addEventListener("load", function () {
    logEvent("EVENT: Window loaded successfully (onload). Core banking events initialized.");
});

// ----------------------------------------------------------------------------
// 2. Mouse Events (mouseenter, mouseleave, click, dblclick)
// ----------------------------------------------------------------------------
const rateCard = document.getElementById("interestRateCard");
const rateInfo = document.getElementById("rateInfo");

if (rateCard) {
    // mouseenter & mouseleave
    rateCard.addEventListener("mouseenter", function () {
        rateInfo.style.display = "block";
        rateCard.style.backgroundColor = "#e0f2fe";
        logEvent("EVENT: mouseenter fired on Interest Rate Card.");
    });

    rateCard.addEventListener("mouseleave", function () {
        rateInfo.style.display = "none";
        rateCard.style.backgroundColor = "#f8fafc";
        logEvent("EVENT: mouseleave fired on Interest Rate Card.");
    });
}

// Click Event
const singleClickBtn = document.getElementById("singleClickBtn");
if (singleClickBtn) {
    singleClickBtn.addEventListener("click", function (e) {
        logEvent("EVENT: onclick fired! Available Balance: $45,230.00");
    });
}

// Double Click Event
const dblClickBtn = document.getElementById("dblClickBtn");
if (dblClickBtn) {
    dblClickBtn.addEventListener("dblclick", function (e) {
        logEvent("EVENT: ondblclick fired! Double confirmation verified -> Transferred $100.00.");
    });
}

// ----------------------------------------------------------------------------
// 3. Form & Keyboard Events (change, focus, blur, keyup, submit)
// ----------------------------------------------------------------------------
const accSelect = document.getElementById("accTypeSelect");
if (accSelect) {
    accSelect.addEventListener("change", function (e) {
        logEvent(`EVENT: onchange fired on Account Select -> Value: '${e.target.value}'`);
    });
}

const amountInput = document.getElementById("transferAmount");
const errorMsg = document.getElementById("amountError");

if (amountInput) {
    // focus & blur
    amountInput.addEventListener("focus", function () {
        logEvent("EVENT: onfocus fired on Transfer Amount field.");
    });

    amountInput.addEventListener("blur", function () {
        logEvent(`EVENT: onblur fired. Final entered value: '$${amountInput.value}'`);
    });

    // keyup live validation
    amountInput.addEventListener("keyup", function (e) {
        const val = parseFloat(e.target.value);
        logEvent(`EVENT: onkeyup pressed Key: '${e.key}' | Current Value: '${e.target.value}'`);
        
        if (isNaN(val) || val <= 0) {
            errorMsg.style.display = "block";
        } else {
            errorMsg.style.display = "none";
        }
    });
}

// Form Submit Event (onsubmit & e.preventDefault())
const transferForm = document.getElementById("transferForm");
if (transferForm) {
    transferForm.addEventListener("submit", function (e) {
        // Prevent traditional full page reload (Crucial in Single Page Applications / React)
        e.preventDefault();
        
        const selectedAcc = accSelect ? accSelect.value : "";
        const amountVal = amountInput ? amountInput.value : "";

        if (!selectedAcc || !amountVal || parseFloat(amountVal) <= 0) {
            logEvent("ERROR: Form validation failed on submit! Please correct input fields.");
            alert("Form Submission Failed! Please check your input fields.");
            return;
        }

        logEvent(`EVENT: onsubmit Success! Account: ${selectedAcc}, Amount: $${amountVal}`);
        alert(`Transfer Request of $${amountVal} from ${selectedAcc} submitted successfully!`);
    });
}
