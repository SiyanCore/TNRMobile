package com.siyanmo.tnrmobile.DomainObjects;

/**
 * Created by Administrator on 2016-02-17.
 */
public class OrderItems {
    private String ItemCode;
    private Float OrderQuntity;

    public OrderItems(){

    }
    public OrderItems(String ItemCode,Float OrderQuntity){
        this.ItemCode = ItemCode;
        this.OrderQuntity = OrderQuntity;
    }
    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public Float getOrderQuntity() {
        return OrderQuntity;
    }

    public void setOrderQuntity(Float orderQuntity) {
        OrderQuntity = orderQuntity;
    }

}
