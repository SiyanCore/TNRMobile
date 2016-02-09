package com.siyanmo.tnrmobile.DomainObjects;

/**
 * Created by Hiran on 2/9/2016.
 */
public class SalesOrderDetail {
    private String OrderId;
    private String ItemCode ;
    private String SoldQuantityinUnits;
    private String Value;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getSoldQuantityinUnits() {
        return SoldQuantityinUnits;
    }

    public void setSoldQuantityinUnits(String soldQuantityinUnits) {
        SoldQuantityinUnits = soldQuantityinUnits;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
