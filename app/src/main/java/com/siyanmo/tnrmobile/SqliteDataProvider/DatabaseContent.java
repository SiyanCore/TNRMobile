package com.siyanmo.tnrmobile.SqliteDataProvider;

/**
 * Created by Hiran on 2/5/2016.
 */
public final class DatabaseContent {
    public static final String Item="MST_Item";
    public static final String Customer="MST_Customer";
    public static final String InvoiceHeader="TRN_InvoiceHeader";
    public static final String InvoiceDetail="TRN_InvoiceDetail";
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
    public final class InvoiceHeader {
        public static final String INVNo="INVNo";
        public static final String Date="InvoiceDate";
        public static final String Amount="InvoiceAmount";
        public static final String Stock="StockInHandUnits";
    }
    public final class InvoiceDetail {
        public static final String ID="ID";
        public static final String INVNo="INVNo";
        public static final String ItemCode = "ItemCode";
        public static final String Quantity="SoldQuantityinUnits";
    }
}
