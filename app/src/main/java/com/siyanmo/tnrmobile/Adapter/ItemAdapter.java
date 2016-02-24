package com.siyanmo.tnrmobile.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hiran on 2/16/2016.
 */
public class ItemAdapter extends BaseExpandableListAdapter {
    private List<String> header_titles = new ArrayList<>();
    private List<String> i_header_titles = new ArrayList<>();
    private HashMap<String,List<String>> child_titles=new HashMap<>();
    private HashMap<String,List<String>> i_child_titles=new HashMap<>();
    private Context context;

    public ItemAdapter(Context cnt, List<Item> itemList){
        context=cnt;
        for (Item item : itemList){
            header_titles.add(item.getItemNameShown());
            List<String>itemDetails=new ArrayList<>();
            itemDetails.add(0,"Price - Rs "+item.getSellingPrice().toString());
            itemDetails.add(1,"Stock - "+item.getStockInHandUnits());
            child_titles.put(item.getItemNameShown(),itemDetails);
        }
        i_header_titles.addAll(header_titles);
        i_child_titles.putAll(child_titles);

    }
    @Override
    public int getGroupCount() {
        return header_titles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child_titles.get(header_titles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header_titles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child_titles.get(header_titles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String itemName=(String)this.getGroup(groupPosition);
        if(convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.item_parent_layout,null);
        }
        TextView textView=(TextView)convertView.findViewById(R.id.heading_item);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(itemName);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String item=(String)this.getChild(groupPosition, childPosition);
        if(convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.item_child_layout,null);
        }
        TextView textView=(TextView)convertView.findViewById(R.id.child_item);
        textView.setText(item);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query){
        query=query.toLowerCase();
        Log.v("ItemAdapter", String.valueOf(header_titles.size()));
        header_titles.clear();
        child_titles.clear();
        if(query.equals("")){
            header_titles.addAll(i_header_titles);
            child_titles.putAll(i_child_titles);
        }
        else {
            for (String h_title:i_header_titles){
                if(h_title.toLowerCase().contains(query)) {
                    for (Map.Entry<String, List<String>> c_title : i_child_titles.entrySet()) {
                        String key=c_title.getKey();
                        if(key.equals(h_title)){
                            header_titles.add(h_title);
                            List<String> value=c_title.getValue();
                            child_titles.put(h_title,value);
                        }
                    }
                }
            }
        }
        Log.v("ItemAdapter", String.valueOf(header_titles.size()));
        notifyDataSetChanged();
    }
}
