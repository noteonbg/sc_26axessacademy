package com.standardchartered.qualifierdemo.service;

import java.math.BigDecimal;

/**
 * Common interface implemented by multiple payment gateway spring beans.
 */
public interface PaymentGatewayService {
    String processPayment(BigDecimal amount);
    String getGatewayName();
}
