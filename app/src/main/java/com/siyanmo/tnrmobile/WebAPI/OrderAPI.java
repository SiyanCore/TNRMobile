package com.siyanmo.tnrmobile.WebAPI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
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
import com.siyanmo.tnrmobile.DomainObjects.User;
import com.siyanmo.tnrmobile.Fragment.NewOrderFragment;
import com.siyanmo.tnrmobile.Fragment.OrdersFragment;
import com.siyanmo.tnrmobile.Fragment.UpdateOrderFragment;
import com.siyanmo.tnrmobile.R;
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

        if(OrderList.isEmpty()){
            Toast tost = Toast.makeText(activity, "No New Orders to Sync", Toast.LENGTH_LONG);
            ViewGroup group = (ViewGroup) tost.getView();
            TextView messageTextView = (TextView) group.getChildAt(0);
            messageTextView.setTextSize(20);
            tost.show();
            return;
        }
        RequestQueue queue = Volley.newRequestQueue(activity);
        //String URL ="http://192.168.1.105/TNR/api/InvoiceRequest/PostOrder";
        String URL = Comman.SearverUrl+"InvoiceRequest/PostOrder";
        final SharedPreferences sharedPreferences = activity.getSharedPreferences("TNRMobile_Login_User_Information", Context.MODE_PRIVATE);
        OrderWrap orderWrap = new OrderWrap();
        User user = CommanMethode.GetLogedUsear(sharedPreferences);
        User enUser = CommanMethode.EncriptUser(user);
        orderWrap.setUser(enUser);
        orderWrap.setOrders(OrderList);
        JSONObject ordres = CommanMethode.ObjectToJsonString(orderWrap);
        try {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL,ordres,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    DatabaseHandler databaseHandler = new DatabaseHandler(activity);
                    databaseHandler.RemoveAllOrdersBySalesmanCode(Comman.getSalesExecutive().getSalesExecutiveCode());

                    Toast tost =Toast.makeText(activity, "Orders Posted", Toast.LENGTH_LONG);
                    ViewGroup group = (ViewGroup) tost.getView();
                    TextView messageTextView = (TextView) group.getChildAt(0);
                    messageTextView.setTextSize(20);
                    tost.show();
                    Intent intent=activity.getIntent();
                    activity.finish();
                    activity.startActivity(intent);
//                    NewOrderFragment fragment=new NewOrderFragment();
//                    fragment.SetActivity(activity);
//                    FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    NetworkResponse error = volleyError.networkResponse;
                    if(error!=null) {
                        if (error.statusCode == 406) {
                            User user1=new User("","");
                            CommanMethode.LogUserDetails(sharedPreferences,user1);
                            activity.finish();
                            Toast tost =Toast.makeText(activity, "User Not Found Contact Admin", Toast.LENGTH_LONG);
                            ViewGroup group = (ViewGroup) tost.getView();
                            TextView messageTextView = (TextView) group.getChildAt(0);
                            messageTextView.setTextSize(20);
                            tost.show();
                        }
                        else {
                            Toast tost =Toast.makeText(activity, "Net Work Error", Toast.LENGTH_LONG);
                            ViewGroup group = (ViewGroup) tost.getView();
                            TextView messageTextView = (TextView) group.getChildAt(0);
                            messageTextView.setTextSize(20);
                            tost.show();
                        }
                    }
                    else {
                        Toast tost =Toast.makeText(activity, "Net Work Error", Toast.LENGTH_LONG);
                        ViewGroup group = (ViewGroup) tost.getView();
                        TextView messageTextView = (TextView) group.getChildAt(0);
                        messageTextView.setTextSize(20);
                        tost.show();
                    }
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
