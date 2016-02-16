package com.siyanmo.tnrmobile;

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
}
