# 🎯 Master Guide: Spring Boot Ambiguous Beans Resolution & `@Qualifier`

This guide explains **Bean Ambiguity and Resolution Strategies in Spring Boot Dependency Injection (IoC Container)** following a strict **"Concept First, Syntax Next"** approach.

---

# 🧠 PART 1: THE CONCEPTS (Understand the Theory First)

## 1. What is Spring Dependency Injection (IoC Container)?
Spring's Inversion of Control (IoC) Container manages object creation, dependency injection, and lifecycles of Spring Beans. When a class declares a dependency on an interface (e.g., `PaymentGatewayService`), Spring automatically injects a matching bean instance at startup.

---

## 2. What is Bean Ambiguity?
**Bean Ambiguity** occurs when multiple Spring Beans implement the **same interface** in the application context.

### 💥 The Exception: `NoUniqueBeanDefinitionException`
If you have 4 implementations of `PaymentGatewayService` (`creditCardGateway`, `payPalGateway`, `upiGateway`, `cryptoGateway`) and try to inject `PaymentGatewayService` without telling Spring which one you want, Spring fails to start and throws:

```text
org.springframework.beans.factory.NoUniqueBeanDefinitionException: 
No qualifying bean of type 'com.standardchartered.qualifierdemo.service.PaymentGatewayService' available: 
expected single matching bean but found 4: creditCardGateway, payPalGateway, upiGateway, cryptoGateway
```

---

## 3. The 5 Bean Resolution Strategies

To resolve bean ambiguity, Spring provides 5 distinct mechanisms:

| Strategy | Annotation / Technique | Description |
| :--- | :--- | :--- |
| **Strategy 1: Default Bean** | `@Primary` | Marks a bean implementation as the default choice when no qualifier is specified at the injection point. |
| **Strategy 2: String Qualifier** | `@Qualifier("beanName")` | Explicitly targets a bean by its registered bean name string. |
| **Strategy 3: Custom Qualifier** | Custom Meta-Annotation (e.g., `@Upi`) | Wraps `@Qualifier` into a custom Java annotation for type-safety and auto-completion. |
| **Strategy 4: Dynamic Map** | `Map<String, PaymentGatewayService>` | Injects **all** implementations into a `Map` keyed by bean name for runtime dynamic dispatch. |
| **Strategy 5: Parameter Name** | Method parameter matching | Spring falls back to matching the constructor/method parameter name (e.g., `payPalGateway`) to bean names. |

---

# 💻 PART 2: THE SYNTAX (Code Implementation & What Each Line Does)

Below is the complete syntax breakdown for each file in **`06-banking-qualifier-beans-demo`**.

---

## Component 1: The Common Interface (`PaymentGatewayService.java`)

```java
package com.standardchartered.qualifierdemo.service;

import java.math.BigDecimal;

public interface PaymentGatewayService {
    String processPayment(BigDecimal amount);
    String getGatewayName();
}
```

### 🔍 What the Syntax Does:
* Establishes a common contract for all payment processing implementations in the application.

---

## Component 2: `@Primary` Default Bean (`CreditCardPaymentGateway.java`)

```java
package com.standardchartered.qualifierdemo.service.impl;

import com.standardchartered.qualifierdemo.service.PaymentGatewayService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("creditCardGateway")
@Primary
public class CreditCardPaymentGateway implements PaymentGatewayService {

    @Override
    public String processPayment(BigDecimal amount) {
        return "[$" + amount + "] Processed via Credit Card Gateway [@Primary]";
    }

    @Override
    public String getGatewayName() {
        return "CreditCardPaymentGateway";
    }
}
```

### 🔍 What the Syntax Does:
* **`@Component("creditCardGateway")`**: Registers the bean in the IoC container under name `"creditCardGateway"`.
* **`@Primary`**: Instructs Spring IoC: *"If someone asks for `PaymentGatewayService` without a `@Qualifier`, ALWAYS inject this bean by default!"*

---

## Component 3: Standard Qualifier Bean (`PayPalPaymentGateway.java`)

```java
package com.standardchartered.qualifierdemo.service.impl;

import com.standardchartered.qualifierdemo.service.PaymentGatewayService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("payPalGateway")
public class PayPalPaymentGateway implements PaymentGatewayService {

    @Override
    public String processPayment(BigDecimal amount) {
        return "[$" + amount + "] Processed via PayPal Gateway [@Qualifier(\"payPalGateway\")]";
    }

    @Override
    public String getGatewayName() {
        return "PayPalPaymentGateway";
    }
}
```

### 🔍 What the Syntax Does:
* Registered under explicit bean name `"payPalGateway"`. Can be injected using `@Qualifier("payPalGateway")`.

---

## Component 4: Custom Qualifier Meta-Annotation (`Upi.java` & `UpiPaymentGateway.java`)

### A. Annotation Definition (`Upi.java`):
```java
package com.standardchartered.qualifierdemo.annotation;

import org.springframework.beans.factory.annotation.Qualifier;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier("upiGateway")
public @interface Upi {
}
```

### B. Bean Implementation (`UpiPaymentGateway.java`):
```java
@Component("upiGateway")
@Upi
public class UpiPaymentGateway implements PaymentGatewayService { ... }
```

### 🔍 What the Syntax Does:
* **`@Qualifier("upiGateway")` on Annotation**: Creates a type-safe custom annotation `@Upi`. Eliminates typos from hardcoded string qualifiers like `@Qualifier("upiGateway")`.

---

## Component 5: Injecting and Resolving Beans (`PaymentProcessingService.java`)

```java
@Service
public class PaymentProcessingService {

    private final PaymentGatewayService primaryGateway;
    private final PaymentGatewayService payPalGateway;
    private final PaymentGatewayService upiGateway;
    private final Map<String, PaymentGatewayService> gatewayMap;

    @Autowired
    public PaymentProcessingService(
            PaymentGatewayService primaryGateway,                                  // 1. Resolves via @Primary
            @Qualifier("payPalGateway") PaymentGatewayService payPalGateway,         // 2. Resolves via @Qualifier
            @Upi PaymentGatewayService upiGateway,                                  // 3. Resolves via Custom @Upi
            Map<String, PaymentGatewayService> gatewayMap                          // 4. Injects ALL beans into Map
    ) {
        this.primaryGateway = primaryGateway;
        this.payPalGateway = payPalGateway;
        this.upiGateway = upiGateway;
        this.gatewayMap = gatewayMap;
    }

    public String processDynamic(String gatewayBeanName, BigDecimal amount) {
        PaymentGatewayService gateway = gatewayMap.get(gatewayBeanName);
        if (gateway == null) {
            throw new IllegalArgumentException("Unknown Gateway: " + gatewayBeanName);
        }
        return gateway.processPayment(amount);
    }
}
```

### 🔍 What the Syntax Does:
1. **`primaryGateway`**: No qualifier specified -> Spring checks for `@Primary` and injects `CreditCardPaymentGateway`.
2. **`@Qualifier("payPalGateway")`**: Overrides `@Primary` and explicitly injects `PayPalPaymentGateway`.
3. **`@Upi`**: Uses custom annotation to inject `UpiPaymentGateway`.
4. **`Map<String, PaymentGatewayService>`**: Spring automatically populates this map with **all 4 registered gateway beans** (`creditCardGateway`, `payPalGateway`, `upiGateway`, `cryptoGateway`), allowing runtime dynamic lookup!

---

## 📊 Summary Comparison of Resolution Strategies

| Technique | Annotations Used | Pros | Cons |
| :--- | :--- | :--- | :--- |
| **Default Fallback** | `@Primary` on Bean | Simple, provides global default. | Only 1 bean per interface can be `@Primary`. |
| **Explicit Qualifier** | `@Qualifier("name")` at Injection | Precise, clear. | Uses hardcoded strings susceptible to typos. |
| **Custom Qualifier** | Meta-Annotation with `@Qualifier` | Type-safe, IDE autocomplete, zero typos. | Requires creating custom annotation files. |
| **Dynamic Map** | `Map<String, Service>` | Extremely flexible for Strategy Pattern / Plugin architectures. | Requires manual map lookup check. |
