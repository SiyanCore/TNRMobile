package com.siyanmo.tnrmobile;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.siyanmo.tnrmobile.DomainObjects.Customer;
import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.DomainObjects.User;
import com.siyanmo.tnrmobile.WebAPI.CryptoUtil;
import com.siyanmo.tnrmobile.DomainObjects.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.spec.KeySpec;
import java.util.List;

import javax.crypto.spec.DESKeySpec;

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

    public static User EncriptUser (User user){
        User enUser =new User();
        CryptoUtil  crypto = new CryptoUtil();
        String zPassword = crypto.encrypt(user.getPassword());
        enUser.setPassword(zPassword);
        enUser.setLoginName(user.getLoginName());
        return enUser;
    }
    
    public static void LogUserDetails(SharedPreferences sharedPreferences, User users) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Uname",users.getLoginName());
        editor.putString("Password", users.getPassword());
        editor.commit();
    }

    public static boolean LocalLogin(User users, SharedPreferences  sharedPreferences){
        try {
            String lname = sharedPreferences.getString("Uname", "");
            String lpassword = sharedPreferences.getString("Password", "");
            if (users.getLoginName().equals(lname) && users.getPassword().equals(lpassword))
            {
                return true;
            } else
            {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public static String[] GetItemNameArry (List<Item> itemList){
        String[] array = new String[itemList.size()];
        int index = 0;
        for (Item item : itemList) {
            array[index] = item.getItemNameShown();
            index++;
        }
        return array;
    }

    public static String[] GetCustomerNameArry(List<Customer> customerList) {
        String[] array = new String[customerList.size()];
        int index = 0;
        for (Customer cust : customerList) {
            array[index] = cust.getCustomerName();
            index++;
        }
        return array;
    }

    public static String[] GetItemCodeArry(List<Item> itemlist) {
        String[] array = new String[itemlist.size()];
        int index = 0;
        for (Item item : itemlist) {
            array[index] = item.getItemCode();
            index++;
        }
        return array;
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
    // }

    // }

    //  }
    }








