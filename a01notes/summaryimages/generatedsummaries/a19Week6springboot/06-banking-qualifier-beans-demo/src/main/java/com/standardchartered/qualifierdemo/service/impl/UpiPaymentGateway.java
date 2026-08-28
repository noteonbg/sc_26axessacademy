package com.standardchartered.qualifierdemo.service.impl;

import com.standardchartered.qualifierdemo.annotation.Upi;
import com.standardchartered.qualifierdemo.service.PaymentGatewayService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Implementation 3: Unified Payments Interface (UPI) Gateway
 * Annotated with custom @Upi qualifier.
 */
@Component("upiGateway")
@Upi
public class UpiPaymentGateway implements PaymentGatewayService {

    @Override
    public String processPayment(BigDecimal amount) {
        return "[$" + amount + "] Processed successfully via Instant UPI Transfer [Resolved via Custom @Upi Meta-Qualifier]";
    }

    @Override
    public String getGatewayName() {
        return "UpiPaymentGateway (Resolved via Custom @Upi Annotation)";
    }
}
