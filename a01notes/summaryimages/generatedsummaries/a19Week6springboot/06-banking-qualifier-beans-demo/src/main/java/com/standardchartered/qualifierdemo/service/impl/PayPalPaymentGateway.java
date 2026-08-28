package com.standardchartered.qualifierdemo.service.impl;

import com.standardchartered.qualifierdemo.service.PaymentGatewayService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Implementation 2: PayPal Payment Gateway
 * Annotated with @Component("payPalGateway") -> Explicit bean name.
 */
@Component("payPalGateway")
public class PayPalPaymentGateway implements PaymentGatewayService {

    @Override
    public String processPayment(BigDecimal amount) {
        return "[$" + amount + "] Processed successfully via PayPal Express Checkout [Resolved via @Qualifier(\"payPalGateway\")]";
    }

    @Override
    public String getGatewayName() {
        return "PayPalPaymentGateway (@Qualifier(\"payPalGateway\"))";
    }
}
