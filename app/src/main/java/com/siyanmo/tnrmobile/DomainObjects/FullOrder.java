package com.siyanmo.tnrmobile.DomainObjects;

import java.util.List;

/**
 * Created by Administrator on 2016-02-17.
 */
public class FullOrder {
    private int OrderId;
    private  String OrderDate;
    private  int SalesExecutiveCode;
    private String Remarks;
    private String CustomerCode;
    private List<OrderItems> Items;

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public int getSalesExecutiveCode() {
        return SalesExecutiveCode;
    }

    public void setSalesExecutiveCode(int salesExecutiveCode) {
        SalesExecutiveCode = salesExecutiveCode;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public List<OrderItems> getItems() {
        return Items;
    }

    public void setItems(List<OrderItems> items) {
        Items = items;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }
}
