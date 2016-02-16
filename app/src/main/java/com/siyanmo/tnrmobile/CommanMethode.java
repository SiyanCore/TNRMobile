package com.siyanmo.tnrmobile;



import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by Administrator on 2016-02-16.
 */
public final class CommanMethode {


    public static JSONObject ObjectToJsonString (Object myObj){
        Gson gson = new Gson();
        String json = gson.toJson(myObj);

        JSONObject reqBody = null;
       try {
          reqBody = new JSONObject (json);
       } catch (JSONException e) {
           e.printStackTrace();
        }
        return reqBody;
    }

    public static <T> T JsonToBoject (JSONObject json,Class<T> obj){
        T newObject =null ;
        Gson gson = new Gson();
       //newObject = gson.fromJson(json, obj.class);
        return newObject;
    }
    //Lakmal//
    //Save User Name And Password
    //class  SaveUserNamePassword extends AppCompatActivity {
        //public void SaveUserNamePassword(String Uname, String Pword) {
           // String UserName = Uname;
           // String PassWord = Pword;

           // SharedPreferences sharedPreferences = getSharedPreferences("TNRMobile_Login_User_Information", Context.MODE_PRIVATE);
            //SharedPreferences.Editor editor = sharedPreferences.edit();
           // editor.putString("Uname", UserName);
            //editor.putString("Password", PassWord);
            //editor.commit();
        }

   // }

      //  }
//







