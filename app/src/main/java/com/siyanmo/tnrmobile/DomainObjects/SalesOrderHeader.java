package com.siyanmo.tnrmobile.DomainObjects;

import java.util.Date;

/**
 * Created by Hiran on 2/9/2016.
 */
public class SalesOrderHeader {
    private String OrderId;
    private Date OrderDate;
    private String CustomerCode;
    private Float OrderAmount;
    private String Remark;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public Float getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(Float orderAmount) {
        OrderAmount = orderAmount;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

}
