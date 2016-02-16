package com.siyanmo.tnrmobile.SqliteDataProvider;

/**
 * Created by Hiran on 2/5/2016.
 */
public final class DatabaseContent {
    public static final String Item="MST_Item";
    public static final String Customer="MST_Customer";
    public static final String SalesOrderHeader="TRN_SalesOrderHeader";
    public static final String SalesOrderDetail="TRN_SalesOrderDetail";
    public final class Item {
        public static final String Code = "ItemCode";
        public static final String Name = "ItemNameShown";
        public static final String Price = "SellingPrice";
        public static final String Stock = "StockInHandUnits";
    }
    public final class Customer {
        public static final String Code="CustomerCode";
        public static final String Name="CustomerName";
    }
    public final class SalesOrderHeader {
        public static final String OrderId="OrderId";
        public static final String Date="OrderDate";
        public static final String CustomerCode="CustomerCode";
        public static final String Amount="OrderAmount";
        public static final String Remark="Remark";
        public static final String SalesmanCode="SalesExecutiveCode";

    }
    public final class SalesOrderDetail {
        public static final String ID="Id";
        public static final String OrderId="OrderId";
        public static final String ItemCode = "ItemCode";
        public static final String Quantity="SoldQuantityinUnits";
        public static final String Value="Value";
    }
}
