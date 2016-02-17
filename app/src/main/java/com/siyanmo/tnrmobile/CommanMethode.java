package com.siyanmo.tnrmobile;



import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import com.google.gson.Gson;
import com.siyanmo.tnrmobile.DomainObjects.User;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by Administrator on 2016-02-16.
 */
public final class CommanMethode {

    public static JSONObject ObjectToJsonString(Object myObj) {
        Gson gson = new Gson();
        String json = gson.toJson(myObj);

        JSONObject reqBody = null;
        try {
            reqBody = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqBody;
    }

    public static <T> T JsonToBoject(JSONObject json, Class<T> obj) {
        T newObject = null;
        Gson gson = new Gson();
        //newObject = gson.fromJson(json, obj.class);
        return newObject;
    }

    public static void LogUserDetails(SharedPreferences sharedPreferences, User users) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Uname",users.getLoginName());
        editor.putString("Password",users.getPassword());
        editor.commit();
    }
    //Password encrypt
   // public static String encrypt(String input) {
        // Simple encryption, not very strong!
      //  return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
   // }
    //Password decrypt--
  //  public static String decrypt(String input) {
     //   return new String(Base64.decode(input, Base64.DEFAULT));
    }











