package com.standardchartered.qualifierdemo.service;

import com.standardchartered.qualifierdemo.annotation.Upi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * Service demonstrating multiple bean resolution techniques in Spring Boot IoC Container.
 */
@Service
public class PaymentProcessingService {

    // Strategy 1: Default Bean Resolution via @Primary
    private final PaymentGatewayService primaryGateway;

    // Strategy 2: Explicit Resolution via @Qualifier("payPalGateway")
    private final PaymentGatewayService payPalGateway;

    // Strategy 3: Custom Meta-Annotation Qualifier (@Upi)
    private final PaymentGatewayService upiGateway;

    // Strategy 4: Dynamic Collection Injection (Map of all available beans keyed by bean name)
    private final Map<String, PaymentGatewayService> gatewayMap;

    @Autowired
    public PaymentProcessingService(
            PaymentGatewayService primaryGateway, // Resolves CreditCardPaymentGateway because it has @Primary
            @Qualifier("payPalGateway") PaymentGatewayService payPalGateway, // Explicit @Qualifier
            @Upi PaymentGatewayService upiGateway, // Custom @Upi annotation
            Map<String, PaymentGatewayService> gatewayMap // Map containing all PaymentGatewayService beans
    ) {
        this.primaryGateway = primaryGateway;
        this.payPalGateway = payPalGateway;
        this.upiGateway = upiGateway;
        this.gatewayMap = gatewayMap;
    }

    // 1. Process via @Primary Default Gateway
    public String processWithPrimary(BigDecimal amount) {
        return primaryGateway.processPayment(amount);
    }

    // 2. Process via @Qualifier("payPalGateway")
    public String processWithPayPal(BigDecimal amount) {
        return payPalGateway.processPayment(amount);
    }

    // 3. Process via Custom @Upi Qualifier
    public String processWithUpi(BigDecimal amount) {
        return upiGateway.processPayment(amount);
    }

    // 4. Process Dynamically by Bean Name from Map
    public String processDynamic(String gatewayBeanName, BigDecimal amount) {
        PaymentGatewayService gateway = gatewayMap.get(gatewayBeanName);
        if (gateway == null) {
            throw new IllegalArgumentException("Unknown Gateway Bean: '" + gatewayBeanName + 
                    "'. Available Beans: " + gatewayMap.keySet());
        }
        return gateway.processPayment(amount);
    }

    // 5. Get List of all registered gateway bean names
    public Set<String> getAllRegisteredGatewayBeanNames() {
        return gatewayMap.keySet();
    }
}
