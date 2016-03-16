package com.siyanmo.tnrmobile.DomainObjects;

/**
 * Created by Hiran on 2/9/2016.
 */
public class Customer {
    private String CustomerCode ;
    private String CustomerName ;
    private float Outstanding;

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

    public float getOutstanding() {
        return Outstanding;
    }

    public void setOutstanding(float outstanding) {
        Outstanding = outstanding;
    }
}
