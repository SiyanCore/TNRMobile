package com.siyanmo.tnrmobile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.siyanmo.tnrmobile.Adapter.ItemAdapter;
import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    DatabaseHandler dbHandler;
    AppCompatActivity activity ;
    ExpandableListView expandableListView;
    List<Item> itemList;
    ItemAdapter itemAdapter;
    public ItemsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHandler=new DatabaseHandler(activity);
        expandableListView= (ExpandableListView) view.findViewById(R.id.expandableItemListView);
        itemList=dbHandler.GetAllItems();
        itemAdapter=new ItemAdapter(activity,itemList);
        expandableListView.setAdapter(itemAdapter);
        expandableListView.setGroupIndicator(null);
    }
    public void SetActivity(AppCompatActivity sactivity){
        activity=sactivity;
    }

}
