package com.standardchartered.banking.controller;

import com.standardchartered.banking.model.xml.StatementXmlPayload;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/xml")
public class XmlIntegrationController {

    // GET /api/v1/xml/statement -> Produces XML response with namespaces scb: and amazon:
    @GetMapping(value = "/statement", produces = MediaType.APPLICATION_XML_VALUE)
    public StatementXmlPayload getXmlStatement() {
        StatementXmlPayload payload = new StatementXmlPayload();
        payload.setCustomerList(List.of(
                new StatementXmlPayload.XmlCustomerRecord("scb132", "Sandra", "Rogers", "Savings", new BigDecimal("100000.00")),
                new StatementXmlPayload.XmlCustomerRecord("scb133", "Steve", "Casey", "Current", new BigDecimal("300000.00"))
        ));
        return payload;
    }
}
