package com.siyanmo.tnrmobile.Fragment;



import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.siyanmo.tnrmobile.Adapter.ItemAdapter;
import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.R;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment{

    DatabaseHandler dbHandler;
    AppCompatActivity activity ;
    ExpandableListView expandableListView;
    List<Item> itemList=new ArrayList<>();
    ItemAdapter itemAdapter;
    SearchView search;
    View rootView;
    public ItemsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_items,null);
        dbHandler=new DatabaseHandler(activity);
        setHasOptionsMenu(true);
        //SearchManager searchManager=(SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        search=(SearchView)rootView.findViewById(R.id.expandableItemListsearchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                itemAdapter.filterData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemAdapter.filterData(newText);
                return false;
            }
        });
        //search.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));
        DisplyList();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandableListView= (ExpandableListView) view.findViewById(R.id.expandableItemListView);
        itemList=dbHandler.GetAllItems();
        itemAdapter=new ItemAdapter(activity,itemList);
        expandableListView.setAdapter(itemAdapter);
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hideSoftKeyboard(v);
                }

                return false;
            }
        });

    }
    public void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void SetActivity(AppCompatActivity sactivity){
        activity=sactivity;
    }
    private void DisplyList(){
            LordSomeData();
            expandableListView=(ExpandableListView)rootView.findViewById(R.id.expandableItemListView);
            itemAdapter=new ItemAdapter(activity,itemList);
            expandableListView.setAdapter(itemAdapter);
    }
    private void LordSomeData(){
            itemList=dbHandler.GetAllItems();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_update).setVisible(false);
        menu.findItem(R.id.action_delete).setVisible(false);
        menu.findItem(R.id.action_cancel).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // Not implemented here
                return false;
            default:
                break;
        }

        return false;
    }

}
