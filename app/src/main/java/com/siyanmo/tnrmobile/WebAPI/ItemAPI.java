package com.siyanmo.tnrmobile.WebAPI;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siyanmo.tnrmobile.Comman;
import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.R;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Hiran on 2/8/2016.
 */
public class ItemAPI
{
    AppCompatActivity activity ;
    DatabaseHandler databaseHandler;
    CustomerApi customerApi;
    public ItemAPI(AppCompatActivity sactivity){
        activity = sactivity;
        customerApi = new CustomerApi(sactivity);
        databaseHandler = new DatabaseHandler(sactivity);
    }

    public void GetItemAndCustomer (){

        RequestQueue queue = Volley.newRequestQueue(activity);
        //String URL ="http://192.168.1.105/TNR/api/item/1001";
        String URL = Comman.SearverUrl+"item/1001";
        final JSONArray[] ItemList = new JSONArray[1];

        try {
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(URL,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    customerApi.GetCustomerBySalesExecutive(Comman.getSalesExecutive().getSalesExecutiveCode());
                    ArrayList<Item> itemArray = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Item>>(){}.getType());
                    databaseHandler.InsertItemList(itemArray);
                    Toast tost =  Toast.makeText(activity, "Item Data Updated", Toast.LENGTH_LONG);
                    ViewGroup group = (ViewGroup) tost.getView();
                    TextView messageTextView = (TextView) group.getChildAt(0);
                    messageTextView.setTextSize(20);
                    tost.show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast tost = Toast.makeText(activity, "Item Data Not Updated", Toast.LENGTH_LONG);
                    ViewGroup group = (ViewGroup) tost.getView();
                    TextView messageTextView = (TextView) group.getChildAt(0);
                    messageTextView.setTextSize(20);
                    tost.show();
                }
            });
            queue.add(jsonObjectRequest);
        }catch(Exception e){
            //disply.setText("exseption ="+e.toString());
        }
    }


    public void GetItem() {
        RequestQueue queue = Volley.newRequestQueue(activity);
        //String URL ="http://192.168.1.105/TNR/api/item/1001";
        String URL = Comman.SearverUrl+"item/1001";
        final JSONArray[] ItemList = new JSONArray[1];

        try {
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(URL,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    ArrayList<Item> itemArray = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Item>>(){}.getType());
                    databaseHandler.InsertItemList(itemArray);
                    Toast tost =  Toast.makeText(activity, "Item Data Updated", Toast.LENGTH_LONG);
                    ViewGroup group = (ViewGroup) tost.getView();
                    TextView messageTextView = (TextView) group.getChildAt(0);
                    messageTextView.setTextSize(20);
                    tost.show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast tost = Toast.makeText(activity, "Item Data Not Updated", Toast.LENGTH_LONG);
                    ViewGroup group = (ViewGroup) tost.getView();
                    TextView messageTextView = (TextView) group.getChildAt(0);
                    messageTextView.setTextSize(20);
                    tost.show();
                }
            });
            queue.add(jsonObjectRequest);
        }catch(Exception e){
            //disply.setText("exseption ="+e.toString());
        }
    }


    public JsonArrayRequest GetItemRequest (){
        String URL ="http://192.168.1.105/TNR/api/item/1001";

        final JSONArray[] ItemList = new JSONArray[1];

        try {
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(URL,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    ItemList[0] = jsonArray;

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
            return jsonObjectRequest;
        }catch(Exception e){
            return null;
        }
    }
}
