package com.siyanmo.tnrmobile.SqliteDataProvider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.siyanmo.tnrmobile.DomainObjects.Customer;
import com.siyanmo.tnrmobile.DomainObjects.FullOrder;
import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.DomainObjects.OrderItems;
import com.siyanmo.tnrmobile.DomainObjects.SalesExecutive;
import com.siyanmo.tnrmobile.DomainObjects.SalesOrderDetail;
import com.siyanmo.tnrmobile.DomainObjects.SalesOrderHeader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hiran on 2/3/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static DatabaseContent DbContent=new DatabaseContent();
    private static DatabaseContent.Item Item=DbContent.new Item();
    private static DatabaseContent.Customer Customer=DbContent.new Customer();
    private static DatabaseContent.SalesOrderHeader SalesOrderHeader=DbContent.new SalesOrderHeader();
    private static DatabaseContent.SalesOrderDetail SalesOrderDetail=DbContent.new SalesOrderDetail();
    private static DatabaseContent.SalesmenDetail SalesmenDetail=DbContent.new SalesmenDetail();
    private static final String DataBaseName="TNRMOBILE.db";
    private static final String CREATE_TABLE_Item="CREATE TABLE IF NOT EXISTS "+ DbContent.Item +
                                                                                    "("+Item.Code+" TEXT PRIMARY KEY NOT NULL,"
                                                                                        +Item.Name+" TEXT NOT NULL,"
                                                                                        +Item.Price+" REAL NOT NULL,"
                                                                                        +Item.Stock+" REAL DEFAULT 0"+")";

    private static final String CREATE_TABLE_Customer="CREATE TABLE IF NOT EXISTS "+ DbContent.Customer +
                                                                                    "("+Customer.Code+" TEXT PRIMARY KEY NOT NULL,"
                                                                                    +Customer.Name+" TEXT NOT NULL,"
                                                                                    +Customer.Outstanding+" REAL NOT NULL"+")";

    private static final String CREATE_TABLE_InvoiceHeader="CREATE TABLE IF NOT EXISTS "+ DbContent.SalesOrderHeader +
                                                                                    "("+SalesOrderHeader.OrderId+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                                                    +SalesOrderHeader.Date+" DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')),"
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
    private static final String CREATE_TABLE_SalesmenDetail="CREATE TABLE IF NOT EXISTS "+ DbContent.SalesmenDetail +
                                                                                    "("+SalesmenDetail.SalesmenCode+" INTEGER PRIMARY KEY,"
                                                                                    +SalesmenDetail.SalesmenName+" TEXT NOT NULL,"
                                                                                    +SalesmenDetail.SalesmenImage+" BLOB,"
                                                                                    +SalesmenDetail.LoginId+" INTEGER NOT NULL"+")";


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
            db.execSQL(CREATE_TABLE_SalesmenDetail);
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
            db.execSQL("DROP TABLE IF EXISTS "+DbContent.SalesmenDetail);
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
            contentValues.put(Customer.Outstanding, CustomerObject.getOutstanding());
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
            db.execSQL("DROP TABLE IF EXISTS " + DbContent.Customer);
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
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
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

    public long InsertSalesOrderDetail(SalesOrderDetail SalesOrderDetailObject,int OrderId){
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(SalesOrderDetail.ItemCode,SalesOrderDetailObject.getItemCode());
            contentValues.put(SalesOrderDetail.OrderId,OrderId);
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
        List<Item> itemList=new ArrayList<>();
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
        List<Customer> customerList=new ArrayList<>();
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from "+DbContent.Customer,null);
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    Customer customer=new Customer();
                    customer.setCustomerCode(cursor.getString(cursor.getColumnIndex(Customer.Code)));
                    customer.setCustomerName(cursor.getString(cursor.getColumnIndex(Customer.Name)));
                    customer.setOutstanding(cursor.getFloat(cursor.getColumnIndex(Customer.Outstanding)));
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
                    fullOrder.setOrderId(orderHeaderCursor.getInt(orderHeaderCursor.getColumnIndex(SalesOrderHeader.OrderId)));
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
                while (orderHeaderCursor.moveToNext()){
                    Integer orderId=orderHeaderCursor.getInt(orderHeaderCursor.getColumnIndex(SalesOrderHeader.OrderId));
                    db.delete(DbContent.SalesOrderDetail, SalesOrderDetail.OrderId + "=?", new String[]{String.valueOf(orderId)});
                }
                db.delete(DbContent.SalesOrderHeader, SalesOrderHeader.SalesmanCode + "=?",new String[]{String.valueOf(salesmanCode)});
            }
            db.setTransactionSuccessful();
            result= true;
        } catch(SQLException e) {

        } catch(Exception e) {

        }finally {
                db.endTransaction();
                return result;
            }
    }

    public boolean InsertSalesmenDetail(SalesExecutive salesExecutive){
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS "+DbContent.SalesmenDetail);
            db.execSQL(CREATE_TABLE_SalesmenDetail);
            ContentValues contentValues=new ContentValues();

            contentValues.put(SalesmenDetail.SalesmenCode,salesExecutive.getSalesExecutiveCode());
            contentValues.put(SalesmenDetail.SalesmenName,salesExecutive.getSalesExecutiveName());
            contentValues.put(SalesmenDetail.SalesmenImage, salesExecutive.getImage());
            contentValues.put(SalesmenDetail.LoginId, salesExecutive.getLoginId());
            long result = db.insert(DbContent.SalesmenDetail,null,contentValues);
            if (result<0){
                return false;
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public SalesExecutive GetSalesmenDetail(){
        SalesExecutive salesExecutive=new SalesExecutive();
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            final String salesmenDetail_QUERY = "SELECT * FROM "+DbContent.SalesmenDetail;
            Cursor salesmenDetailCursor = db.rawQuery(salesmenDetail_QUERY, null);
            if (salesmenDetailCursor.moveToNext()){
                    salesExecutive.setSalesExecutiveCode(salesmenDetailCursor.getInt(salesmenDetailCursor.getColumnIndex(SalesmenDetail.SalesmenCode)));
                    salesExecutive.setSalesExecutiveName(salesmenDetailCursor.getString(salesmenDetailCursor.getColumnIndex(SalesmenDetail.SalesmenName)));
                    salesExecutive.setImage(salesmenDetailCursor.getBlob(salesmenDetailCursor.getColumnIndex(SalesmenDetail.SalesmenImage)));
                    salesExecutive.setLoginId(salesmenDetailCursor.getInt(salesmenDetailCursor.getColumnIndex(SalesmenDetail.LoginId)));
            }

        }catch (Exception e){

        }

        return salesExecutive;
    }

    public String GetCustomerNameByCustomerCode(String customerCode ){
        String CustomerName="";
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT "+Customer.Name+" FROM "+DbContent.Customer+" WHERE "+Customer.Code+"='"+customerCode+"'",null);
            if (cursor.moveToNext()){
                CustomerName=cursor.getString(0);
            }
            return CustomerName;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return CustomerName;
    }

    public String GetCustomerCodeByCustomerName(String customerName ){
        String CustomerCode="";
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT "+Customer.Code+" FROM "+DbContent.Customer+" WHERE "+Customer.Name+"='"+customerName+"'",null);
            if (cursor.moveToNext()){
                CustomerCode=cursor.getString(0);
            }
            return CustomerCode;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return CustomerCode;
    }

    public String GetOutstandingByCustomerName(String customerName ){
        String CustomerCode="";
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT "+Customer.Outstanding+" FROM "+DbContent.Customer+" WHERE "+Customer.Name+"='"+customerName+"'",null);
            if (cursor.moveToNext()){
                CustomerCode=String.format("%.2f", cursor.getFloat(0));
            }
            return CustomerCode;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return CustomerCode;
    }

    public boolean DeleteOrderById(String orderId){
        SQLiteDatabase db=this.getWritableDatabase();
        boolean result=false;
        try {
            db.beginTransaction();

            db.delete(DbContent.SalesOrderDetail, SalesOrderDetail.OrderId + "=?", new String[]{String.valueOf(orderId)});

            db.delete(DbContent.SalesOrderHeader, SalesOrderHeader.OrderId + "=?",new String[]{String.valueOf(orderId)});

            db.setTransactionSuccessful();
            result= true;
        } catch(SQLException e) {

        } catch(Exception e) {

        }finally {
            db.endTransaction();
            return result;
        }
    }

    public Item GetItemByItemCode(String itemCode){
        Item item=new Item();
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+DbContent.Item+" WHERE "+Item.Code+"='"+itemCode+"'",null);
            if (cursor.moveToNext()){
                item.setItemCode(cursor.getString(cursor.getColumnIndex(Item.Code)));
                item.setItemNameShown(cursor.getString(cursor.getColumnIndex(Item.Name)));
                item.setSellingPrice(cursor.getFloat(cursor.getColumnIndex(Item.Price)));
            }
            return item;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return item;
    }

    public Item GetItemByItemName(String itemName){
        Item item=new Item();
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+DbContent.Item+" WHERE "+Item.Name+"='"+itemName+"'",null);
            if (cursor.moveToNext()){
                item.setItemCode(cursor.getString(cursor.getColumnIndex(Item.Code)));
                item.setItemNameShown(cursor.getString(cursor.getColumnIndex(Item.Name)));
                item.setSellingPrice(cursor.getFloat(cursor.getColumnIndex(Item.Price)));
            }
            return item;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return item;
    }

    public boolean InsertOrder(SalesOrderHeader salesOrderHeader, List<SalesOrderDetail> orders) {
        int ordeId=(int)InsertSalesOrderHeader(salesOrderHeader);
        long id;
        if (ordeId>0){
            for (SalesOrderDetail detail:orders){
                id=InsertSalesOrderDetail(detail,ordeId);
                if (id<0){
                    DeleteOrderById(Integer.toString(ordeId));
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }

    }

    public SalesOrderHeader GetSalesOrderHeaderByOrderId(String orderId){
        SalesOrderHeader header=new SalesOrderHeader();
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+DbContent.SalesOrderHeader+" WHERE "+SalesOrderHeader.OrderId+"='"+orderId+"'",null);
            if (cursor.moveToNext()){
                header.setCustomerCode(cursor.getString(cursor.getColumnIndex(SalesOrderHeader.CustomerCode)));
                String date=cursor.getString(cursor.getColumnIndex(SalesOrderHeader.Date));
                DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                header.setOrderDate(iso8601Format.parse(date));
                header.setRemark(cursor.getString(cursor.getColumnIndex(SalesOrderHeader.Remark)));
                header.setOrderAmount(cursor.getFloat(cursor.getColumnIndex(SalesOrderHeader.Amount)));
            }
            return header;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return header;
    }

    public List<SalesOrderDetail> GetAllSalesOrderDetailByOrderId(String orderId) {
        List<SalesOrderDetail> detailList=new ArrayList<>();
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+DbContent.SalesOrderDetail+" WHERE "+ SalesOrderDetail.OrderId+"='"+orderId+"'",null);
            while (cursor.moveToNext()){
                SalesOrderDetail detail=new SalesOrderDetail();
                String itemCode=cursor.getString(cursor.getColumnIndex(SalesOrderDetail.ItemCode));
                float quantity=cursor.getFloat(cursor.getColumnIndex(SalesOrderDetail.Quantity));
                float value=cursor.getFloat(cursor.getColumnIndex(SalesOrderDetail.Value));
                detail.setItemCode(itemCode);
                detail.setItemName(GetItemByItemCode(itemCode).getItemNameShown());
                detail.setSoldQuantityinUnits(quantity);
                detail.setValue(value);
                detail.setUnitePrize(value/quantity);
                detailList.add(detail);
            }
            return detailList;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        return detailList;
    }

    public boolean UpdateOrders(SalesOrderHeader salesOrderHeader, List<SalesOrderDetail> orders) {
        SQLiteDatabase db=this.getWritableDatabase();
        boolean result=false;
        long id;
        try {
            db.beginTransaction();
            int orderId=salesOrderHeader.getOrderId();
            ContentValues values = new ContentValues();
            values.put(SalesOrderHeader.CustomerCode, salesOrderHeader.getCustomerCode());
            values.put(SalesOrderHeader.Amount, salesOrderHeader.getOrderAmount());
            values.put(SalesOrderHeader.Remark, salesOrderHeader.getRemark());
            int val=db.update(DatabaseContent.SalesOrderHeader, values, SalesOrderHeader.OrderId+"="+orderId, null);
            if(val>0){
                db.delete(DbContent.SalesOrderDetail, SalesOrderDetail.OrderId + "=?", new String[]{String.valueOf(orderId)});
                for (SalesOrderDetail orderDetail:orders) {
                    id=InsertSalesOrderDetail(orderDetail, orderId);
                    if (id<0){
                        return false;
                    }
                }
            }
            db.setTransactionSuccessful();
            result=true;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }finally {
            db.endTransaction();
            return result;
        }

    }
}
