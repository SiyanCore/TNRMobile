package com.siyanmo.tnrmobile;

import org.json.JSONArray;

/**
 * Created by Administrator on 2016-02-15.
 */
public final class Comman {
    private static JSONArray ItemList;

    public static JSONArray getItemList() {
        return ItemList;
    }

    public static void setItemList(JSONArray itemList) {
        ItemList = itemList;
    }
}
