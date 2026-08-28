package com.standardchartered.qualifierdemo.service.impl;

import com.standardchartered.qualifierdemo.service.PaymentGatewayService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Implementation 1: Credit Card Payment Gateway
 * Annotated with @Primary -> Acts as the default bean when no @Qualifier is specified.
 */
@Component("creditCardGateway")
@Primary
public class CreditCardPaymentGateway implements PaymentGatewayService {

    @Override
    public String processPayment(BigDecimal amount) {
        return "[$" + amount + "] Processed successfully via Visa/MasterCard Credit Card Gateway [Resolved via @Primary]";
    }

    @Override
    public String getGatewayName() {
        return "CreditCardPaymentGateway (@Primary Default)";
    }
}
