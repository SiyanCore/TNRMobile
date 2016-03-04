package com.siyanmo.tnrmobile.DomainObjects;

/**
 * Created by Hiran on 2/9/2016.
 */
public class SalesOrderDetail {
    private Integer OrderId;
    private String ItemCode ;
    private String ItemName;
    private Float SoldQuantityinUnits;
    private Float Value;
    private Float UnitePrize;

    public SalesOrderDetail (){}
    public SalesOrderDetail (String ItemCode,String ItemName,Float SoldQuantityinUnits,Float Value,Float UnitePrize){
        this.ItemCode = ItemCode;
        this.ItemName = ItemName;
        this.SoldQuantityinUnits = SoldQuantityinUnits;
        this.Value = Value;
        this.UnitePrize = UnitePrize;
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

    public Float getSoldQuantityinUnits() {
        return SoldQuantityinUnits;
    }

    public void setSoldQuantityinUnits(Float soldQuantityinUnits) {
        SoldQuantityinUnits = soldQuantityinUnits;
    }

    public Float getValue() {
        return Value;
    }

    public void setValue(Float value) {
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

    public Float getUnitePrize() {
        return UnitePrize;
    }

    public void setUnitePrize(Float unitePrize) {
        UnitePrize = unitePrize;
    }
}
