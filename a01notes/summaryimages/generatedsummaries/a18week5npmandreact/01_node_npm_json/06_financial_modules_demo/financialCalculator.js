/**
 * Financial Calculation Local Module (Banking & Financial Services Domain)
 */
'use strict';

/**
 * Calculates Simple Interest
 * Formula: SI = (P * R * T) / 100
 */
function calculateSimpleInterest(principal, annualRate, timeYears) {
    const interest = (principal * annualRate * timeYears) / 100;
    const totalAmount = principal + interest;
    return {
        principal,
        annualRate,
        timeYears,
        interestAmount: Number(interest.toFixed(2)),
        totalAmount: Number(totalAmount.toFixed(2))
    };
}

/**
 * Calculates Monthly EMI for Loans
 * Formula: EMI = [P x R x (1+R)^N]/[(1+R)^N-1]
 */
function calculateMonthlyEMI(loanAmount, annualInterestRate, tenureMonths) {
    const monthlyRate = annualInterestRate / (12 * 100);
    const emi = (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) / 
                (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    const totalPayment = emi * tenureMonths;
    const totalInterest = totalPayment - loanAmount;

    return {
        loanAmount,
        annualInterestRate,
        tenureMonths,
        monthlyEMI: Number(emi.toFixed(2)),
        totalInterest: Number(totalInterest.toFixed(2)),
        totalPayment: Number(totalPayment.toFixed(2))
    };
}

module.exports = {
    calculateSimpleInterest,
    calculateMonthlyEMI
};
