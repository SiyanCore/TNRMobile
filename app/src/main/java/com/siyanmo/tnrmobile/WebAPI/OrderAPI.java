package com.siyanmo.tnrmobile.WebAPI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siyanmo.tnrmobile.Comman;
import com.siyanmo.tnrmobile.CommanMethode;
import com.siyanmo.tnrmobile.DomainObjects.FullOrder;
import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.DomainObjects.OrderWrap;
import com.siyanmo.tnrmobile.DomainObjects.SalesExecutive;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-02-17.
 */
public class OrderAPI  {
    AppCompatActivity activity ;
    DatabaseHandler databaseHandler;
    CustomerApi customerApi;
    public OrderAPI(AppCompatActivity sactivity){
        activity = sactivity;
        customerApi = new CustomerApi(sactivity);
        databaseHandler = new DatabaseHandler(sactivity);
    }

    public void PostOrders (List<FullOrder> OrderList){

        RequestQueue queue = Volley.newRequestQueue(activity);
        String URL ="http://192.168.1.105/TNR/api/InvoiceRequest/PostOrder";
        OrderWrap orderWrap = new OrderWrap();
        orderWrap.setOrders(OrderList);
        JSONObject ordres = CommanMethode.ObjectToJsonString(orderWrap);
        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL,ordres,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyError ee = volleyError;
                    Toast.makeText(activity, "Net Work Error", Toast.LENGTH_LONG).show();
                }
            });
            queue.add(jsonObjectRequest);
//            JsonArrayRequest jsonArryRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
//                @Override
//                public void onResponse(JSONArray jsonArray) {
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError volleyError) {
//
//                }
//            });
//            queue.add(jsonArryRequest);
        }catch(Exception e){
            //disply.setText("exseption ="+e.toString());
        }
    }
}
