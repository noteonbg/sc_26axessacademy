/**
 * ============================================================================
 * MODULE 6: DOM AND FORM MANIPULATION WITH REGEX VALIDATION
 * ============================================================================
 * Topics Covered:
 * - DOM Selectors: getElementById, querySelector, querySelectorAll
 * - DOM Manipulation: innerHTML, textContent, style, setAttribute
 * - Dynamic Node Creation & Deletion: createElement, appendChild, removeChild
 * - Regular Expression Patterns & Quantifiers: [A-Za-z]+, [7-9]{1}[0-9]{9}
 * - PDF Hands-on 1: Display array of strings in list format in browser
 * - PDF Hands-on 2: Name textbox validation with error message "please enter only alphabets"
 * ============================================================================
 */

'use strict';

document.addEventListener("DOMContentLoaded", function () {
    console.log("=== Module 6: DOM & Form Validation Initialized ===");

    // ------------------------------------------------------------------------
    // 1. PDF Hands-on 1: Render Array of Strings in List Format in Browser
    // ------------------------------------------------------------------------
    const bankingServices = [
        "Retail Wealth Management",
        "Personal Instant Credit Line",
        "Corporate Trade Finance",
        "Fixed Deposit & Yield Optimizer",
        "International Wire Transfer"
    ];

    const servicesListContainer = document.getElementById("servicesList");

    function renderServicesList(services) {
        if (!servicesListContainer) return;
        
        // Clear container using innerHTML
        servicesListContainer.innerHTML = "";

        // Iterate over array and dynamically create DOM <li> nodes
        services.forEach((service, index) => {
            const li = document.createElement("li");
            li.textContent = `${index + 1}. ${service}`;
            
            // Add a badge element
            const badge = document.createElement("span");
            badge.textContent = "Active";
            badge.style.backgroundColor = "#dcfce7";
            badge.style.color = "#15803d";
            badge.style.fontSize = "0.75rem";
            badge.style.padding = "2px 8px";
            badge.style.borderRadius = "12px";
            badge.style.fontWeight = "bold";

            li.appendChild(badge);
            servicesListContainer.appendChild(li);
        });
    }

    // Initial render of array list
    renderServicesList(bankingServices);

    // Dynamic Node Addition (createElement & appendChild)
    const addBtn = document.getElementById("addServiceBtn");
    if (addBtn) {
        addBtn.addEventListener("click", function () {
            const newServiceName = `Custom Service #${bankingServices.length + 1}`;
            bankingServices.push(newServiceName);
            renderServicesList(bankingServices);
        });
    }

    // Dynamic Node Deletion (removeChild)
    const removeBtn = document.getElementById("removeServiceBtn");
    if (removeBtn) {
        removeBtn.addEventListener("click", function () {
            if (bankingServices.length > 0) {
                bankingServices.pop();
                renderServicesList(bankingServices);
            } else {
                alert("No more service nodes to remove!");
            }
        });
    }

    // ------------------------------------------------------------------------
    // 2. PDF Hands-on 2: Name Input Validation (Alphabets Only Check)
    // ------------------------------------------------------------------------
    const nameInput = document.getElementById("applicantName");
    const nameError = document.getElementById("nameError");
    const validateNameBtn = document.getElementById("validateNameBtn");

    function validateNameField() {
        if (!nameInput || !nameError) return false;
        
        const nameVal = nameInput.value.trim();
        // Regex pattern: Alphabets only (case-insensitive)
        // PDF Specification: /^[A-Za-z]+$/ or /^[a-z]+$/i
        const namePattern = /^[A-Za-z\s]+$/;

        if (nameVal === "") {
            nameError.textContent = "Name field cannot be empty.";
            nameInput.style.borderColor = "#dc2626";
            return false;
        }

        // PDF search() or pattern.test() usage
        if (nameVal.search(namePattern) === -1) {
            // PDF Explicit Requirement Error Message: "please enter only alphabets"
            nameError.textContent = "please enter only alphabets";
            nameInput.style.borderColor = "#dc2626";
            return false;
        } else {
            nameError.textContent = "✓ Valid Name";
            nameError.style.color = "#16a34a";
            nameInput.style.borderColor = "#16a34a";
            return true;
        }
    }

    if (validateNameBtn) {
        validateNameBtn.addEventListener("click", validateNameField);
    }
    if (nameInput) {
        nameInput.addEventListener("keyup", function() {
            nameError.style.color = "#dc2626"; // reset to red on type
            validateNameField();
        });
    }

    // ------------------------------------------------------------------------
    // 3. Additional Regex Validation: 10 Digit Phone Number starting with 7-9
    // ------------------------------------------------------------------------
    const contactInput = document.getElementById("contactNo");
    const contactError = document.getElementById("contactError");
    const validateContactBtn = document.getElementById("validateContactBtn");

    function validateContactField() {
        if (!contactInput || !contactError) return false;
        
        const contactVal = contactInput.value.trim();
        // PDF Specified Quantifier Example: /^[7-9]{1}[0-9]{9}$/
        const contactPattern = /^[7-9]{1}[0-9]{9}$/;

        if (!contactPattern.test(contactVal)) {
            contactError.textContent = "Invalid contact! Must be 10 digits starting with 7, 8, or 9.";
            contactError.style.color = "#dc2626";
            contactInput.style.borderColor = "#dc2626";
            return false;
        } else {
            contactError.textContent = "✓ Valid Contact Number";
            contactError.style.color = "#16a34a";
            contactInput.style.borderColor = "#16a34a";
            return true;
        }
    }

    if (validateContactBtn) {
        validateContactBtn.addEventListener("click", validateContactField);
    }
});
