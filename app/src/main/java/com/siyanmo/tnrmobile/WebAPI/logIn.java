package com.siyanmo.tnrmobile.WebAPI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

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
    public logIn(AppCompatActivity sactivity){
        activity =sactivity;
    }

    public synchronized boolean Login (User user){
        RequestQueue volleyRequestQueue = Volley.newRequestQueue(activity);
        String SIGNUP_URL ="http://192.168.1.105/TNR/api/SalesExecutive/LogIng";

        String reqBodyString = "{\"LoginName\":\"lovindu\",\"Password\":\"123\"}";
        JSONObject  reqBody = null;
        try {
            reqBody = new JSONObject (reqBodyString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,SIGNUP_URL,reqBody,future, future);
        volleyRequestQueue.add(jsonObjectRequest);

        try {
            JSONObject response = future.get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }

        return true;
    }
    
    public Boolean GetLog(User user){

        RequestQueue queue = Volley.newRequestQueue(activity);
        String URL ="http://192.168.1.105/TNR/api/SalesExecutive/LogIng";


        JSONObject userJ = CommanMethode.ObjectToJsonString(user);
        try {
           JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL,userJ ,new Response.Listener<JSONObject>() {
              @Override
               public void onResponse(JSONObject jsonObject) {
                   String ff = jsonObject.toString();
                  Gson gson = new Gson();
                  SalesExecutive obj = gson.fromJson(ff , SalesExecutive.class);
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
            queue.add(jsonObjectRequest);
       }catch(Exception e){
           //disply.setText("exseption ="+e.toString());
       }

        return true;
    }
}
