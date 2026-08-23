package com.standardchartered.banking.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "customerlist")
public class StatementXmlPayload {

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns:scb")
    private String scbNamespace = "http://www.sc.com/banking/customers";

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns:amazon")
    private String amazonNamespace = "http://www.amazon.com/shopping/orders";

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "customer")
    private List<XmlCustomerRecord> customerList = new ArrayList<>();

    public StatementXmlPayload() {}

    public String getScbNamespace() { return scbNamespace; }
    public String getAmazonNamespace() { return amazonNamespace; }

    public List<XmlCustomerRecord> getCustomerList() { return customerList; }
    public void setCustomerList(List<XmlCustomerRecord> customerList) { this.customerList = customerList; }

    public static class XmlCustomerRecord {
        private String custid;
        private String firstname;
        private String lastname;
        private String accountType;
        private BigDecimal balance;

        public XmlCustomerRecord() {}

        public XmlCustomerRecord(String custid, String firstname, String lastname, String accountType, BigDecimal balance) {
            this.custid = custid;
            this.firstname = firstname;
            this.lastname = lastname;
            this.accountType = accountType;
            this.balance = balance;
        }

        public String getCustid() { return custid; }
        public void setCustid(String custid) { this.custid = custid; }

        public String getFirstname() { return firstname; }
        public void setFirstname(String firstname) { this.firstname = firstname; }

        public String getLastname() { return lastname; }
        public void setLastname(String lastname) { this.lastname = lastname; }

        public String getAccountType() { return accountType; }
        public void setAccountType(String accountType) { this.accountType = accountType; }

        public BigDecimal getBalance() { return balance; }
        public void setBalance(BigDecimal balance) { this.balance = balance; }
    }
}
