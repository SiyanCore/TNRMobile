package com.siyanmo.tnrmobile.SqliteDataProvider;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.siyanmo.tnrmobile.DomainObjects.Item;

import java.io.IOException;

/**
 * Created by Hiran on 2/3/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static DatabaseContent DbContent=new DatabaseContent();
    private static DatabaseContent.Item Item=DbContent.new Item();
    private static DatabaseContent.Customer Customer=DbContent.new Customer();
    private static DatabaseContent.SalesOrderHeader SalesOrderHeader=DbContent.new SalesOrderHeader();
    private static DatabaseContent.SalesOrderDetail SalesOrderDetail=DbContent.new SalesOrderDetail();
    private static final String DataBaseName="TNRMOBILE.db";
    private static final String CREATE_TABLE_Item="CREATE TABLE "+ DbContent.Item +
                                                                                    "("+Item.Code+" TEXT PRIMARY KEY NOT NULL,"
                                                                                        +Item.Name+" TEXT NOT NULL,"
                                                                                        +Item.Price+" REAL NOT NULL,"
                                                                                        +Item.Stock+" REAL NOT NULL"+")";

    private static final String CREATE_TABLE_Customer="CREATE TABLE "+ DbContent.Customer +
                                                                                    "("+Customer.Code+" TEXT PRIMARY KEY NOT NULL,"
                                                                                    +Customer.Name+" TEXT NOT NULL"+")";

    private static final String CREATE_TABLE_InvoiceHeader="CREATE TABLE "+ DbContent.SalesOrderHeader +
                                                                                    "("+SalesOrderHeader.OrderId+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                                                    +SalesOrderHeader.Date+" INTEGER NOT NULL,"
                                                                                    +SalesOrderHeader.Amount+" REAL NOT NULL,"
                                                                                    +SalesOrderHeader.Remark+" TEXT"+")";

    private static final String CREATE_TABLE_InvoiceDetail="CREATE TABLE "+ DbContent.SalesOrderDetail +
                                                                                    "("+SalesOrderDetail.ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                                                    +SalesOrderDetail.OrderId+" TEXT NOT NULL,"
                                                                                    +SalesOrderDetail.ItemCode+" TEXT NOT NULL,"
                                                                                    +SalesOrderDetail.Quantity+" REAL NOT NULL"+")";

    public DatabaseHandler(Context context) {
        super(context, DataBaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_Item);
            db.execSQL(CREATE_TABLE_Customer);
            db.execSQL(CREATE_TABLE_InvoiceHeader);
            db.execSQL(CREATE_TABLE_InvoiceDetail);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS "+DbContent.Item);
            db.execSQL("DROP TABLE IF EXISTS "+DbContent.Customer);
            db.execSQL("DROP TABLE IF EXISTS "+DbContent.SalesOrderHeader);
            db.execSQL("DROP TABLE IF EXISTS "+DbContent.SalesOrderDetail);
            onCreate(db);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    public boolean InsertItem(Item ItemObject){
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(Item.Code,ItemObject.getItemCode());
            contentValues.put(Item.Name,ItemObject.getItemNameShown());
            contentValues.put(Item.Price,ItemObject.getSellingPrice());
            contentValues.put(Item.Stock,ItemObject.getStockInHandUnits());
            long result = db.insert(DbContent.Item,null,contentValues);
            if (result==-1){
                return false;
            }
            else {
                return  true;
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return false;
    }

}
