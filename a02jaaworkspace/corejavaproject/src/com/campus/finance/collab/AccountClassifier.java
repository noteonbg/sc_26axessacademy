package com.campus.finance.collab;

/**
 * CONCEPT 1 — FUNCTIONAL INTERFACE (user-defined).
 *
 * This is a SHARED CONTRACT that the whole team agrees on: "given an Account,
 * return a label as a String". Exactly one abstract method, so it is a
 * functional interface and can be implemented by a lambda.
 *
 * Why this matters for collaboration:
 *  - The segmentation team, the risk team and the marketing team can EACH write
 *    their own classifier (their own lambda) without touching each other's code,
 *    because they all agree on this one small contract.
 */
@FunctionalInterface
public interface AccountClassifier {
    String classify(Account account);
}
