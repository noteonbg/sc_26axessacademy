package com.standardchartered.qualifierdemo.service.impl;

import com.standardchartered.qualifierdemo.service.PaymentGatewayService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Implementation 4: Cryptocurrency Payment Gateway
 * Annotated with @Component("cryptoGateway") -> Explicit bean name.
 */
@Component("cryptoGateway")
public class CryptoPaymentGateway implements PaymentGatewayService {

    @Override
    public String processPayment(BigDecimal amount) {
        return "[$" + amount + "] Processed successfully via Blockchain Decentralized Crypto Gateway [Resolved via Dynamic Map Lookup]";
    }

    @Override
    public String getGatewayName() {
        return "CryptoPaymentGateway (@Component(\"cryptoGateway\"))";
    }
}
