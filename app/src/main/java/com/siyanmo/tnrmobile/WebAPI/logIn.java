package com.siyanmo.tnrmobile.WebAPI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
    public logIn(AppCompatActivity sactivity){
        activity =sactivity;
        itemAPI = new ItemAPI(sactivity);
    }

    public JsonObjectRequest LoginRequest (User user){
        String URL ="http://192.168.1.105/TNR/api/SalesExecutive/LogIng";
        final User users = user;
        JSONObject userJ = CommanMethode.ObjectToJsonString(user);
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL,userJ ,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    itemAPI.GetItem();
                    String ff = jsonObject.toString();
                    Gson gson = new Gson();
                    SalesExecutive salesExecutive = gson.fromJson(ff, SalesExecutive.class);
                    Comman.setSalesExecutive(salesExecutive);
                    SharedPreferences sharedPreferences=activity.getSharedPreferences("TNRMobile_Login_User_Information", Context.MODE_PRIVATE);
                    CommanMethode.LogUserDetails(sharedPreferences,users);
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

        RequestQueue queue = Volley.newRequestQueue(activity);
        String URL ="http://192.168.1.105/TNR/api/SalesExecutive/LogIng";


        JSONObject userJ = CommanMethode.ObjectToJsonString(user);
        try {
           JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL,userJ ,new Response.Listener<JSONObject>() {
              @Override
               public void onResponse(JSONObject jsonObject) {
                  itemAPI.GetItem();
                   String ff = jsonObject.toString();
                  Gson gson = new Gson();
                  SalesExecutive salesExecutive = gson.fromJson(ff, SalesExecutive.class);
                  Comman.setSalesExecutive(salesExecutive);
                  Intent intent = new Intent("com.siyanmo.tnrmobile.MainActivity");
                  activity.startActivity(intent);

               }
           }
              , new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError volleyError) {
                   VolleyError ee = volleyError;
                   Toast.makeText(activity, "Log In Error", Toast.LENGTH_LONG).show();
               }
           });
            queue.add(jsonObjectRequest);
       }catch(Exception e){
           //disply.setText("exseption ="+e.toString());
       }

        return true;
    }


}
