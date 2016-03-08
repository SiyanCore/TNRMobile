package com.siyanmo.tnrmobile.Fragment;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.R;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopUpItemFragment extends DialogFragment {

    Button button;
    ListView listView;
    SearchView searchView;
    ArrayAdapter<String> arrayAdapter;
    String[] items;
    DatabaseHandler dbHandler;
    AppCompatActivity activity ;
    Dialog dialog;

    public PopUpItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_pop_up_item, null);
        dialog=getDialog();
        dialog.setTitle("Item Prices");
        button=(Button)rootView.findViewById(R.id.PopUpItemCancellButlon);
        listView=(ListView)rootView.findViewById(R.id.PopUpItemListView);
        searchView=(SearchView)rootView.findViewById(R.id.PopUpItemSearchView);
        dbHandler=new DatabaseHandler(activity);
        SetItems();
        arrayAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,items);
        listView.setAdapter(arrayAdapter);

        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);
                dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        // Inflate the layout for this fragment
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideSoftKeyboard(view);
            }
        });
        rootView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hideSoftKeyboard(v);
                }

                return false;
            }
        });
        return rootView;
    }
    public void hideSoftKeyboard(View view) {

        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
    public void SetItems(){
        List<Item> itemList=new ArrayList<>();
        itemList=dbHandler.GetAllItems();
        List<String>itemDetails=new ArrayList<>();
        for (Item item : itemList){
            itemDetails.add(item.getItemNameShown()+" - Rs "+item.getSellingPrice().toString());
        }
        items=new String[itemDetails.size()];
        items=itemDetails.toArray(items);
    }

    public void SetActivity(AppCompatActivity sactivity){
        activity=sactivity;
    }

}
