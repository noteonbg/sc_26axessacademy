package com.standardchartered.qualifierdemo.controller;

import com.standardchartered.qualifierdemo.service.PaymentProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentGatewayController {

    private final PaymentProcessingService paymentProcessingService;

    public PaymentGatewayController(PaymentProcessingService paymentProcessingService) {
        this.paymentProcessingService = paymentProcessingService;
    }

    // 1. Endpoint using @Primary Default Bean
    @PostMapping("/primary")
    public ResponseEntity<String> payWithPrimary(@RequestParam(defaultValue = "150.00") BigDecimal amount) {
        String result = paymentProcessingService.processWithPrimary(amount);
        return ResponseEntity.ok(result);
    }

    // 2. Endpoint using @Qualifier("payPalGateway")
    @PostMapping("/qualifier/paypal")
    public ResponseEntity<String> payWithPayPal(@RequestParam(defaultValue = "299.99") BigDecimal amount) {
        String result = paymentProcessingService.processWithPayPal(amount);
        return ResponseEntity.ok(result);
    }

    // 3. Endpoint using Custom @Upi Qualifier
    @PostMapping("/custom-qualifier/upi")
    public ResponseEntity<String> payWithUpi(@RequestParam(defaultValue = "500.00") BigDecimal amount) {
        String result = paymentProcessingService.processWithUpi(amount);
        return ResponseEntity.ok(result);
    }

    // 4. Endpoint using Dynamic Bean Lookup from Map
    @PostMapping("/dynamic/{gatewayType}")
    public ResponseEntity<String> payWithDynamicGateway(
            @PathVariable String gatewayType,
            @RequestParam(defaultValue = "1000.00") BigDecimal amount) {
        // e.g. gatewayType = "creditCardGateway", "payPalGateway", "upiGateway", "cryptoGateway"
        String result = paymentProcessingService.processDynamic(gatewayType, amount);
        return ResponseEntity.ok(result);
    }

    // 5. Endpoint returning all registered PaymentGatewayService beans
    @GetMapping("/all-gateways")
    public ResponseEntity<Set<String>> getAllRegisteredGateways() {
        return ResponseEntity.ok(paymentProcessingService.getAllRegisteredGatewayBeanNames());
    }
}
