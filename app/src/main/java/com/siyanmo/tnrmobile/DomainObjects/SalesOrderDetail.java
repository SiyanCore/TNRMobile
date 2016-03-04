package com.siyanmo.tnrmobile.DomainObjects;

/**
 * Created by Hiran on 2/9/2016.
 */
public class SalesOrderDetail {
    private Integer OrderId;
    private String ItemCode ;
    private String ItemName;
    private String SoldQuantityinUnits;
    private String Value;

    public SalesOrderDetail (){}
    public SalesOrderDetail (String ItemCode,String ItemName,String SoldQuantityinUnits){
        this.ItemCode = ItemCode;
        this.ItemName = ItemName;
        this.SoldQuantityinUnits = SoldQuantityinUnits;
    }
    public Integer getOrderId() {
        return OrderId;
    }

    public void setOrderId(Integer orderId) {
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

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof SalesOrderDetail)
        {
            sameSame = this.ItemCode.equals (((SalesOrderDetail) object).ItemCode) /*&& this.OrderQuntity == ((OrderItems) object).OrderQuntity*/;
        }

        return sameSame;
    }
}
