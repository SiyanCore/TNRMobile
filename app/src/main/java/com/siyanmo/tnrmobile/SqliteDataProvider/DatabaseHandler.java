package com.siyanmo.tnrmobile.SqliteDataProvider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.siyanmo.tnrmobile.DomainObjects.Customer;
import com.siyanmo.tnrmobile.DomainObjects.FullOrder;
import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.DomainObjects.OrderItems;
import com.siyanmo.tnrmobile.DomainObjects.SalesOrderDetail;
import com.siyanmo.tnrmobile.DomainObjects.SalesOrderHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private static final String CREATE_TABLE_Item="CREATE TABLE IF NOT EXISTS "+ DbContent.Item +
                                                                                    "("+Item.Code+" TEXT PRIMARY KEY NOT NULL,"
                                                                                        +Item.Name+" TEXT NOT NULL,"
                                                                                        +Item.Price+" REAL NOT NULL,"
                                                                                        +Item.Stock+" REAL NOT NULL"+")";

    private static final String CREATE_TABLE_Customer="CREATE TABLE IF NOT EXISTS "+ DbContent.Customer +
                                                                                    "("+Customer.Code+" TEXT PRIMARY KEY NOT NULL,"
                                                                                    +Customer.Name+" TEXT NOT NULL"+")";

    private static final String CREATE_TABLE_InvoiceHeader="CREATE TABLE IF NOT EXISTS "+ DbContent.SalesOrderHeader +
                                                                                    "("+SalesOrderHeader.OrderId+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                                                    +SalesOrderHeader.Date+" INTEGER NOT NULL,"
                                                                                    +SalesOrderHeader.CustomerCode+" TEXT NOT NULL,"
                                                                                    +SalesOrderHeader.Amount+" REAL NOT NULL,"
                                                                                    +SalesOrderHeader.SalesmanCode+" INTEGER NOT NULL,"
                                                                                    +SalesOrderHeader.Remark+" TEXT"+")";

    private static final String CREATE_TABLE_InvoiceDetail="CREATE TABLE IF NOT EXISTS "+ DbContent.SalesOrderDetail +
                                                                                    "("+SalesOrderDetail.ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                                                    +SalesOrderDetail.OrderId+" TEXT NOT NULL,"
                                                                                    +SalesOrderDetail.ItemCode+" TEXT NOT NULL,"
                                                                                    +SalesOrderDetail.Quantity+" REAL NOT NULL,"
                                                                                    +SalesOrderDetail.Value+" REAL NOT NULL"+")";

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

    public long InsertItem(Item ItemObject,SQLiteDatabase db){
        try {
            ContentValues contentValues=new ContentValues();
            contentValues.put(Item.Code, ItemObject.getItemCode());
            contentValues.put(Item.Name, ItemObject.getItemNameShown());
            contentValues.put(Item.Price, ItemObject.getSellingPrice());
            contentValues.put(Item.Stock, ItemObject.getStockInHandUnits());
            long result = db.insert(DbContent.Item,null,contentValues);
            return result;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            throw e;
        }
        return -1;
    }

    public boolean InsertItemList(List<Item> ItemList) {
        SQLiteDatabase db=this.getWritableDatabase();
        boolean result=false;
        try {
            db.beginTransaction();
            db.execSQL("DROP TABLE IF EXISTS " + DbContent.Item);
            db.execSQL(CREATE_TABLE_Item);
            for (Item ItemObject:ItemList) {
                InsertItem(ItemObject,db);
            }
            db.setTransactionSuccessful();
            result= true;
        } catch(SQLException e) {

        } finally {
            db.endTransaction();
            return result;
        }
    }

    public long InsertCustomer(Customer CustomerObject,SQLiteDatabase db){
        try {
            ContentValues contentValues=new ContentValues();
            contentValues.put(Customer.Code, CustomerObject.getCustomerCode());
            contentValues.put(Customer.Name, CustomerObject.getCustomerName());
            long result = db.insert(DbContent.Customer,null,contentValues);
            return result;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return -1;
    }

    public boolean InsertCustomerList(List<Customer> CustomerList) {
        SQLiteDatabase db=this.getWritableDatabase();
        boolean result=false;
        try {
            db.beginTransaction();
            db.execSQL("DROP TABLE IF EXISTS "+DbContent.Customer);
            db.execSQL(CREATE_TABLE_Customer);
            for (Customer CustomerObject:CustomerList) {
                InsertCustomer(CustomerObject,db);
            }
            db.setTransactionSuccessful();
            result= true;
        } catch(SQLException e) {

        } finally {
            db.endTransaction();
            return result;
        }
    }

    public long InsertSalesOrderHeader(SalesOrderHeader SalesOrderHeaderObject){
        try {
            long currentTimeMillis= System.currentTimeMillis();
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(SalesOrderHeader.Date,currentTimeMillis);
            contentValues.put(SalesOrderHeader.CustomerCode, SalesOrderHeaderObject.getCustomerCode());
            contentValues.put(SalesOrderHeader.Amount, SalesOrderHeaderObject.getOrderAmount());
            contentValues.put(SalesOrderHeader.Remark, SalesOrderHeaderObject.getRemark());
            contentValues.put(SalesOrderHeader.SalesmanCode, SalesOrderHeaderObject.getSalesmanCode());
            long result = db.insert(DbContent.SalesOrderHeader,null,contentValues);
            return result;

        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return -1;
    }

    public long InsertSalesOrderDetail(SalesOrderDetail SalesOrderDetailObject){
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(SalesOrderDetail.ItemCode,SalesOrderDetailObject.getItemCode());
            contentValues.put(SalesOrderDetail.OrderId,SalesOrderDetailObject.getOrderId());
            contentValues.put(SalesOrderDetail.Quantity,SalesOrderDetailObject.getSoldQuantityinUnits());
            contentValues.put(SalesOrderDetail.Value,SalesOrderDetailObject.getValue());
            long result = db.insert(DbContent.SalesOrderDetail,null,contentValues);
            return result;

        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return -1;
    }

    public List<Item> GetAllItems(){
        List<Item> itemList=new ArrayList<Item>();
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from " + DbContent.Item, null);
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    Item item=new Item();
                    item.setItemCode(cursor.getString(cursor.getColumnIndex(Item.Code)));
                    item.setItemNameShown(cursor.getString(cursor.getColumnIndex(Item.Name)));
                    item.setSellingPrice(cursor.getFloat(cursor.getColumnIndex(Item.Price)));
                    item.setStockInHandUnits(cursor.getFloat(cursor.getColumnIndex(Item.Stock)));
                    itemList.add(item);
                }
            }
            return itemList;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return itemList;
    }

    public List<Customer> GetAllCustomer(){
        List<Customer> customerList=new ArrayList<Customer>();
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from "+DbContent.Customer,null);
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    Customer customer=new Customer();
                    customer.setCustomerCode(cursor.getString(cursor.getColumnIndex(Customer.Code)));
                    customer.setCustomerName(cursor.getString(cursor.getColumnIndex(Customer.Name)));
                    customerList.add(customer);
                }
            }
            return customerList;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return customerList;
    }

    public List<FullOrder> GetAllOrdersBySalesmanCode(Integer salesmanCode){
        List<FullOrder> orderList=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        try {
            db.beginTransaction();
            final String orderHeader_QUERY = "SELECT * FROM "+DbContent.SalesOrderHeader+" WHERE "+SalesOrderHeader.SalesmanCode+"=?";
            Cursor orderHeaderCursor = db.rawQuery(orderHeader_QUERY, new String[]{String.valueOf(salesmanCode)});
            if (orderHeaderCursor.getCount()>0){
                while (orderHeaderCursor.moveToNext()){
                    FullOrder fullOrder=new FullOrder();
                    fullOrder.setCustomerCode(orderHeaderCursor.getString(orderHeaderCursor.getColumnIndex(SalesOrderHeader.CustomerCode)));
                    fullOrder.setOrderDate(orderHeaderCursor.getString(orderHeaderCursor.getColumnIndex(SalesOrderHeader.Date)));
                    fullOrder.setRemarks(orderHeaderCursor.getString(orderHeaderCursor.getColumnIndex(SalesOrderHeader.Remark)));
                    fullOrder.setSalesExecutiveCode(orderHeaderCursor.getInt(orderHeaderCursor.getColumnIndex(SalesOrderHeader.SalesmanCode)));

                    List<OrderItems> orderItemsList=new ArrayList<>();
                    Integer orderId=orderHeaderCursor.getInt(orderHeaderCursor.getColumnIndex(SalesOrderHeader.OrderId));
                    final String orderDetail_QUERY = "SELECT * FROM "+DbContent.SalesOrderDetail+" WHERE "+SalesOrderDetail.OrderId+"=?";
                    Cursor orderDetailCursor = db.rawQuery(orderDetail_QUERY, new String[]{String.valueOf(orderId)});
                    while (orderDetailCursor.moveToNext()){
                        OrderItems orderItems=new OrderItems();
                        orderItems.setItemCode(orderDetailCursor.getString(orderDetailCursor.getColumnIndex(SalesOrderDetail.ItemCode)));
                        orderItems.setOrderQuntity(orderDetailCursor.getFloat(orderDetailCursor.getColumnIndex(SalesOrderDetail.Quantity)));
                        orderItemsList.add(orderItems);
                    }
                    fullOrder.setItems(orderItemsList);

                    orderList.add(fullOrder);
                }
            }
            db.setTransactionSuccessful();
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        } finally {
            db.endTransaction();
            return orderList;
        }

    }

    public boolean RemoveAllOrdersBySalesmanCode(Integer salesmanCode) {
        SQLiteDatabase db=this.getWritableDatabase();
        boolean result=false;
        try {
            db.beginTransaction();
            final String orderHeader_QUERY = "SELECT * FROM "+DbContent.SalesOrderHeader+" WHERE "+SalesOrderHeader.SalesmanCode+"=?";
            Cursor orderHeaderCursor = db.rawQuery(orderHeader_QUERY, new String[]{String.valueOf(salesmanCode)});
            if (orderHeaderCursor.getCount()>0){
                Integer orderId=orderHeaderCursor.getInt(orderHeaderCursor.getColumnIndex(SalesOrderHeader.OrderId));
                while (orderHeaderCursor.moveToNext()){
                    db.delete(DbContent.SalesOrderDetail, SalesOrderDetail.OrderId + "=" + orderId, null);
                }
                db.delete(DbContent.SalesOrderHeader, SalesOrderHeader.SalesmanCode + "=" + salesmanCode, null);
            }
            db.setTransactionSuccessful();
            result= true;
        } catch(SQLException e) {

        } finally {
            db.endTransaction();
            return result;
        }
    }
}
