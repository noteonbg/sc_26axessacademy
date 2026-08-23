package com.standardchartered.servicedemo.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * SYNTAX COMMENTARY: @Service Annotation
 *
 * @Service:
 * - Marks this class as a Spring Service Component in the Business Layer.
 * - Indicates to Spring Container during classpath scanning (@ComponentScan) that this bean holds core business logic.
 * - Managed as a Singleton Spring Bean by default in the ApplicationContext.
 */
@Service
public class InterestCalculationService {

    private static final BigDecimal SAVINGS_INTEREST_RATE = new BigDecimal("0.04"); // 4% Annual Interest
    private static final BigDecimal CURRENT_INTEREST_RATE = new BigDecimal("0.00"); // 0% Interest

    /*
     * Business Logic: Calculates quarterly interest payout based on account balance and type.
     */
    public BigDecimal calculateQuarterlyInterest(BigDecimal balance, String accountType) {
        if (balance == null || balance.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal annualRate = "SAVINGS".equalsIgnoreCase(accountType) ? SAVINGS_INTEREST_RATE : CURRENT_INTEREST_RATE;
        
        // Quarterly Interest = (Balance * Annual Rate) / 4
        BigDecimal annualInterest = balance.multiply(annualRate);
        return annualInterest.divide(new BigDecimal("4"), 2, RoundingMode.HALF_UP);
    }
}
