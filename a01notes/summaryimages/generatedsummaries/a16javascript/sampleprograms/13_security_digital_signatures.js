/**
 * ============================================================================
 * PRACTICAL LAB 3: DIGITAL SIGNATURES & WIRE TRANSFER INTEGRITY
 * ============================================================================
 * Concepts Demonstrated:
 * - Asymmetric Encryption (Public / Private Keypair).
 * - Hashing + Encryption for Non-Repudiation & File Integrity verification.
 * ============================================================================
 */

'use strict';

const crypto = require('crypto');

console.log("=== Cybersecurity Lab 3: Digital Signatures & Non-Repudiation ===");

// 1. Generate RSA 2048-bit Public and Private Key Pair
console.log("Generating RSA 2048-bit Key Pair...");
const { publicKey, privateKey } = crypto.generateKeyPairSync('rsa', {
    modulusLength: 2048,
});

// 2. Financial Wire Transfer Document Payload
const bankWireTransfer = JSON.stringify({
    transactionId: "TX-9988110022",
    senderAcc: "SCB-7749112",
    receiverAcc: "SCB-8830192",
    amount: 1000000.00, // $1,000,000 USD
    currency: "USD",
    timestamp: new Date().toISOString()
});

console.log("\nOriginal Financial Wire Document:");
console.log(bankWireTransfer);

// 3. Sender signs transaction with Private Key (Creates Digital Signature)
const signer = crypto.createSign('SHA256');
signer.update(bankWireTransfer);
signer.end();
const digitalSignature = signer.sign(privateKey, 'hex');

console.log("\nGenerated Digital Signature (Hex):");
console.log(digitalSignature);

// 4. Receiver verifies signature using Sender's Public Key
function verifyWireTransfer(documentData, signatureHex, pubKey) {
    const verifier = crypto.createVerify('SHA256');
    verifier.update(documentData);
    verifier.end();
    return verifier.verify(pubKey, signatureHex, 'hex');
}

// Verification Test 1: Intact Original Document
const isLegitimate = verifyWireTransfer(bankWireTransfer, digitalSignature, publicKey);
console.log(`\nVerification Result (Original Document): ${isLegitimate ? "✓ AUTHENTIC (Integrity & Non-Repudiation Verified)" : "❌ INVALID"}`);

// Verification Test 2: Tampered Document (Attacker modifies amount to $5,000,000)
const tamperedWireTransfer = bankWireTransfer.replace("1000000", "5000000");
const isTamperedLegitimate = verifyWireTransfer(tamperedWireTransfer, digitalSignature, publicKey);
console.log(`Verification Result (Tampered Document):   ${isTamperedLegitimate ? "✓ AUTHENTIC" : "❌ TAMPER DETECTED! Transaction Blocked!"}`);
