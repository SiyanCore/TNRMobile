package com.siyanmo.tnrmobile.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.siyanmo.tnrmobile.DomainObjects.SalesOrderDetail;
import com.siyanmo.tnrmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiran on 3/5/2016.
 */
public class NewOrderViweAdapter extends BaseAdapter {

    private List<SalesOrderDetail> orderItemses;
    Context context;
    public NewOrderViweAdapter(Context context){
        this.context = context;
        orderItemses = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return orderItemses.size();
    }

    @Override
    public Object getItem(int position) {
        return orderItemses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class OrderHolder{
        TextView Itme_Name;
        TextView Item_Code;
        TextView Order_Quntity;
        TextView Amount;
        TextView Unit_Price;

        public OrderHolder(View view){
            Itme_Name = (TextView)view.findViewById(R.id.Itme_Name);
            Item_Code = (TextView)view.findViewById(R.id.Item_Code);
            Order_Quntity = (TextView)view.findViewById(R.id.Order_Quntity);
            Amount = (TextView)view.findViewById(R.id.Amount);
            Unit_Price = (TextView)view.findViewById(R.id.Unit_Price);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        NewOrderViweAdapter.OrderHolder holder = null;
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.order_grid_item, parent, false);
            holder = new NewOrderViweAdapter.OrderHolder(row);
            row.setTag(holder);
        }else{
            holder = (NewOrderViweAdapter.OrderHolder) row.getTag();
        }
        holder.Item_Code.setText(orderItemses.get(position).getItemCode());
        holder.Order_Quntity.setText(orderItemses.get(position).getSoldQuantityinUnits().toString());
        holder.Itme_Name.setText(orderItemses.get(position).getItemName().toString());
        holder.Unit_Price.setText(String.format("%.2f", orderItemses.get(position).getUnitePrize()));
        //holder.Amount.setText(orderItemses.get(position).getValue().toString());
        holder.Amount.setText(String.format("%.2f", orderItemses.get(position).getValue()));
        return row;
    }


    public void filterData(SalesOrderDetail item) {
        Log.v("ItemAdapter", String.valueOf(orderItemses.size()));
        if(orderItemses.contains(item)){
            Toast.makeText(context, "Item Updated", Toast.LENGTH_SHORT).show();
            int index = orderItemses.indexOf(item);
            orderItemses.set(index,item);
        }else {
            Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show();
            orderItemses.add(item);
        }
        Log.v("ItemAdapter", String.valueOf(orderItemses.size()));
        notifyDataSetChanged();
    }

    public void InsertInitialData(List<SalesOrderDetail> detailList){
        orderItemses.clear();
        orderItemses=detailList;
        Log.v("ItemAdapter", String.valueOf(orderItemses.size()));
        notifyDataSetChanged();
    }
    public void DeleteData (int position) {
        Log.v("ItemAdapter", String.valueOf(orderItemses.size()));
        orderItemses.remove(position);
        Log.v("ItemAdapter", String.valueOf(orderItemses.size()));
        notifyDataSetChanged();
    }
    public List<SalesOrderDetail>  GetOrderItemses (){
        return this.orderItemses;
    }

}
