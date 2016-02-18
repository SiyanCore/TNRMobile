package com.siyanmo.tnrmobile.Events;

import android.support.v7.app.AppCompatActivity;

import com.siyanmo.tnrmobile.Comman;
import com.siyanmo.tnrmobile.DomainObjects.FullOrder;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;
import com.siyanmo.tnrmobile.WebAPI.CustomerApi;
import com.siyanmo.tnrmobile.WebAPI.ItemAPI;
import com.siyanmo.tnrmobile.WebAPI.OrderAPI;

import java.util.List;

/**
 * Created by Administrator on 2016-02-18.
 */
public class SyncEvent {
    AppCompatActivity activity;
    OrderAPI orderAPI;
    ItemAPI itemAPI;
    CustomerApi customerApi;
    public SyncEvent (AppCompatActivity activity){
        this.activity = activity;
        orderAPI = new OrderAPI(activity);
        itemAPI =  new ItemAPI(activity);
        customerApi = new CustomerApi(activity);
    }

    public void SyncItems (){
        itemAPI.GetItem();
    }

    public void SyncCustomers (){
        customerApi.GetCustomerBySalesExecutive(Comman.getSalesExecutive().getSalesExecutiveCode());
    }
    public void SyncOrders (){
        DatabaseHandler databaseHandler = new DatabaseHandler(activity);
        List<FullOrder> orderList = databaseHandler.GetAllOrdersBySalesmanCode(Comman.getSalesExecutive().getSalesExecutiveCode());
        orderAPI.PostOrders(orderList);
    }

    public void SYncAll (){
        SyncOrders ();
        SyncCustomers();
        SyncItems ();
    }
}
