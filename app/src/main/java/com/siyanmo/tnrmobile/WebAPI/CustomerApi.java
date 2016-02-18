package com.siyanmo.tnrmobile.WebAPI;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siyanmo.tnrmobile.Comman;
import com.siyanmo.tnrmobile.DomainObjects.Customer;
import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-02-16.
 */
public class CustomerApi {
    AppCompatActivity activity ;
    DatabaseHandler databaseHandler;
    public CustomerApi (AppCompatActivity sactivity){
        activity = sactivity;
        databaseHandler = new DatabaseHandler(sactivity);
    }
    public void GetCustomerBySalesExecutive (int SalesExecutiveCode){

        RequestQueue queue = Volley.newRequestQueue(activity);
        //String URL ="http://192.168.1.105/TNR/api/Customer/GetCustomerBySalesExecutive?salesExecutiveCode="+SalesExecutiveCode;
        String URL = Comman.SearverUrl+"Customer/GetCustomerBySalesExecutive?salesExecutiveCode="+SalesExecutiveCode;
        try {
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    ArrayList<Customer> customerArray = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Customer>>(){}.getType());
                    databaseHandler.InsertCustomerList(customerArray);
                    Toast.makeText(activity, "Customer Data UpDated", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Comman.setItemList(new JSONArray());
                    Toast.makeText(activity, "Customer Data Not UpDated", Toast.LENGTH_LONG).show();
                }
            });
            queue.add(jsonObjectRequest);
        }catch(Exception e){
            //disply.setText("exseption ="+e.toString());
        }
    }
}
