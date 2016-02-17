package com.siyanmo.tnrmobile.DomainObjects;

import java.util.List;

/**
 * Created by Administrator on 2016-02-17.
 */
public class OrderWrap {
    private List<FullOrder> Orders;

    public List<FullOrder> getOrders() {
        return Orders;
    }

    public void setOrders(List<FullOrder> orders) {
        Orders = orders;
    }
}
