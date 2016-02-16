package com.siyanmo.tnrmobile.Adapter;

import android.content.Context;
import android.graphics.Typeface;
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

/**
 * Created by Hiran on 2/16/2016.
 */
public class ItemAdapter extends BaseExpandableListAdapter {
    private List<String> header_titles;
    private HashMap<String,List<String>> child_titles;
    private Context context;
    public ItemAdapter(Context cnt, List<Item> itemList){
        context=cnt;
        for (Item item : itemList){
            header_titles.add(item.getItemNameShown());
            List<String>itemDetails=new ArrayList<String>();
            itemDetails.add(0,"Price - Rs "+item.getSellingPrice().toString());
            itemDetails.add(0,"Stock - "+item.getStockInHandUnits());
            child_titles.put(item.getItemNameShown(),itemDetails);
        }

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
        if(context==null){
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
        if(context==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.item_child_layout,null);
        }
        TextView textView=(TextView)convertView.findViewById(R.id.child_item);
        textView.setText(item);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
