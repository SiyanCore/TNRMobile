package com.siyanmo.tnrmobile.DomainObjects;

/**
 * Created by Hiran on 2/9/2016.
 */
public class Customer {
    private String CustomerCode ;
    private String CustomerName ;

    public String getCustomerCode() {
        return CustomerCode;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }
    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }
}
