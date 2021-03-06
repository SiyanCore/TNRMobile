package com.siyanmo.tnrmobile.WebAPI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.siyanmo.tnrmobile.Comman;
import com.siyanmo.tnrmobile.CommanMethode;
import com.siyanmo.tnrmobile.DomainObjects.SalesExecutive;
import com.siyanmo.tnrmobile.DomainObjects.User;
import com.siyanmo.tnrmobile.CommanMethode;
import com.siyanmo.tnrmobile.Login;
import com.siyanmo.tnrmobile.R;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2016-02-15.
 */
public class logIn {
    AppCompatActivity activity;
    ItemAPI itemAPI;
    DatabaseHandler dbHandler;
    private static EditText PassWord;

    public logIn(AppCompatActivity sactivity){
        activity =sactivity;
        itemAPI = new ItemAPI(sactivity);
        dbHandler=new DatabaseHandler(activity);
        PassWord = (EditText) activity.findViewById(R.id.Pword);
    }

    public JsonObjectRequest LoginRequest (User user){
        String URL ="http://192.168.1.105/TNR/api/SalesExecutive/LogIng";
        final User users = user;
        JSONObject userJ = CommanMethode.ObjectToJsonString(user);
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL,userJ ,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    itemAPI.GetItemAndCustomer();
                    String ff = jsonObject.toString();
                    Gson gson = new Gson();
                    SalesExecutive salesExecutive = gson.fromJson(ff, SalesExecutive.class);
                    Comman.setSalesExecutive(salesExecutive);
                    dbHandler.InsertSalesmenDetail(salesExecutive);
                    SharedPreferences sharedPreferences=activity.getSharedPreferences("TNRMobile_Login_User_Information", Context.MODE_PRIVATE);
                    CommanMethode.LogUserDetails(sharedPreferences, users);
                    PassWord.setText("");
                    Intent intent = new Intent("com.siyanmo.tnrmobile.MainActivity");
                    activity.startActivity(intent);

                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyError ee = volleyError;
                }
            });
            return jsonObjectRequest;
        }catch(Exception e){
            return null;
        }
    }
    
    public Boolean GetLog(User user){
        final User users = user;

        RequestQueue queue = Volley.newRequestQueue(activity);
        //String URL ="http://192.168.1.105/TNR/api/SalesExecutive/LogIng";
        String URL = Comman.SearverUrl+"SalesExecutive/LogIng";
        User enUser = CommanMethode.EncriptUser(user);
        JSONObject userJ = CommanMethode.ObjectToJsonString(enUser);
        try {
           JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL,userJ ,new Response.Listener<JSONObject>() {
              @Override
               public void onResponse(JSONObject jsonObject) {
                  itemAPI.GetItemAndCustomer();
                   String ff = jsonObject.toString();
                  Gson gson = new Gson();
                  SalesExecutive salesExecutive = gson.fromJson(ff, SalesExecutive.class);
                  Comman.setSalesExecutive(salesExecutive);
                  dbHandler.InsertSalesmenDetail(salesExecutive);
                  SharedPreferences sharedPreferences=activity.getSharedPreferences("TNRMobile_Login_User_Information", Context.MODE_PRIVATE);
                  CommanMethode.LogUserDetails(sharedPreferences, users);
                  PassWord.setText("");
                  Intent intent = new Intent("com.siyanmo.tnrmobile.MainActivity");
                  activity.startActivity(intent);

               }
           }
              , new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError volleyError) {
                   NetworkResponse error = volleyError.networkResponse;
                   if(error!=null) {
                       if (error.statusCode == 404) {
                           Toast tost =Toast.makeText(activity, "User Not Found Contact Admin", Toast.LENGTH_LONG);
                           ViewGroup group = (ViewGroup) tost.getView();
                           TextView messageTextView = (TextView) group.getChildAt(0);
                           messageTextView.setTextSize(20);
                           tost.show();
                       }
                       else {
                           Comman.setSalesExecutive(dbHandler.GetSalesmenDetail());
                           Toast tost = Toast.makeText(activity, "Sever Error Data Not Up Dated", Toast.LENGTH_LONG);
                           ViewGroup group = (ViewGroup) tost.getView();
                           TextView messageTextView = (TextView) group.getChildAt(0);
                           messageTextView.setTextSize(20);
                           tost.show();
                           PassWord.setText("");
                           Intent intent = new Intent("com.siyanmo.tnrmobile.MainActivity");
                           activity.startActivity(intent);
                       }
                   }
                   else{
                       SharedPreferences  sharedPreferences=activity.getSharedPreferences("TNRMobile_Login_User_Information", Context.MODE_PRIVATE);
                       if (!CommanMethode.LocalLogin(users, sharedPreferences)) {
                           Toast tost = Toast.makeText(activity, "User Not Found Contact Admin", Toast.LENGTH_LONG);
                           ViewGroup group = (ViewGroup) tost.getView();
                           TextView messageTextView = (TextView) group.getChildAt(0);
                           messageTextView.setTextSize(20);
                           tost.show();
                       }
                       else {
                           Comman.setSalesExecutive(dbHandler.GetSalesmenDetail());
                           Toast tost = Toast.makeText(activity, "Sever Error Data Not Up Dated", Toast.LENGTH_LONG);
                           ViewGroup group = (ViewGroup) tost.getView();
                           TextView messageTextView = (TextView) group.getChildAt(0);
                           messageTextView.setTextSize(20);
                           tost.show();
                           PassWord.setText("");
                           Intent intent = new Intent("com.siyanmo.tnrmobile.MainActivity");
                           activity.startActivity(intent);
                       }
                   }
               }
           });
            queue.add(jsonObjectRequest);
       }catch(Exception e){
           //disply.setText("exseption ="+e.toString());
       }

        return true;
    }


}
