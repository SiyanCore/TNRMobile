package com.siyanmo.tnrmobile.SqliteDataProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Hiran on 2/3/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static DatabaseContent DbContent=new DatabaseContent();
    private static DatabaseContent.Item Item=DbContent.new Item();
    private static DatabaseContent.Customer Customer=DbContent.new Customer();
    private static DatabaseContent.InvoiceHeader InvoiceHeader=DbContent.new InvoiceHeader();
    private static DatabaseContent.InvoiceDetail InvoiceDetail=DbContent.new InvoiceDetail();
    private static final String DataBaseName="TNRMOBILE.db";
    private static final String CREATE_TABLE_Item="CREATE TABLE "+ DbContent.Item +
                                                                                    "("+Item.Code+" INTEGER PRIMARY KEY,"
                                                                                        +Item.Name+" TEXT,"
                                                                                        +Item.Price+" ";

    public DatabaseHandler(Context context) {
        super(context, DataBaseName, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
