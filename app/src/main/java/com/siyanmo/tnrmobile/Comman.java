package com.siyanmo.tnrmobile;

import com.siyanmo.tnrmobile.DomainObjects.SalesExecutive;

import org.json.JSONArray;

/**
 * Created by Administrator on 2016-02-15.
 */
public final class Comman {
    public static String SearverUrl = "http://192.168.1.105/TNR/api/";
    private static SalesExecutive SalesExecutive;


    public static SalesExecutive getSalesExecutive() {
        return SalesExecutive;
    }

    public static void setSalesExecutive(SalesExecutive salesExecutive) {
        SalesExecutive = salesExecutive;
    }
}
