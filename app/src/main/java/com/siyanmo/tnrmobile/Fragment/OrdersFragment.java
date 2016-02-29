package com.siyanmo.tnrmobile.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.siyanmo.tnrmobile.Adapter.OrdersAdapter;
import com.siyanmo.tnrmobile.Comman;
import com.siyanmo.tnrmobile.DomainObjects.FullOrder;
import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.R;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

    DatabaseHandler dbHandler;
    AppCompatActivity activity ;
    ExpandableListView expandableListView;
    List<Item> itemList=new ArrayList<>();
    List<FullOrder> fullOrderList=new ArrayList<>();
    String[]customerNames;
    OrdersAdapter ordersAdapter;
    SearchView search;
    View rootView;
    String Id;
    public OrdersFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_orders,null);
        dbHandler=new DatabaseHandler(activity);
        //SearchManager searchManager=(SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        search=(SearchView)rootView.findViewById(R.id.expandableOrderSearchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ordersAdapter.filterData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ordersAdapter.filterData(newText);
                return false;
            }
        });


        DisplyList();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandableListView= (ExpandableListView) view.findViewById(R.id.expandableOrderListView);

        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);

                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    String header=(String)expandableListView.getItemAtPosition(position);
                    Id="";
                    for (int i=0;header.charAt(i)!='.';i++){
                        Id+=header.charAt(i);
                    }
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    UpdateOrder(Id);
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    DeleteOrder(Id);
                                    break;
                                case DialogInterface.BUTTON_NEUTRAL:

                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Do you want change this Order ?").setPositiveButton("Update", dialogClickListener)
                            .setNegativeButton("Delete", dialogClickListener).setNeutralButton("Cancel",dialogClickListener).show();
                }

                return false;
            }
        });

        LordSomeData();
        ordersAdapter=new OrdersAdapter(activity,fullOrderList,customerNames,itemList);
        expandableListView.setAdapter(ordersAdapter);
        expandableListView.setGroupIndicator(null);
    }
    public void SetActivity(AppCompatActivity sactivity){
        activity=sactivity;
    }
    private void DisplyList(){
        LordSomeData();
        expandableListView=(ExpandableListView)rootView.findViewById(R.id.expandableOrderListView);
        ordersAdapter=new OrdersAdapter(activity,fullOrderList,customerNames,itemList);
        expandableListView.setAdapter(ordersAdapter);
    }

    private void LordSomeData(){
        itemList=dbHandler.GetAllItems();
        fullOrderList=dbHandler.GetAllOrdersBySalesmanCode(Comman.getSalesExecutive().getSalesExecutiveCode());
        customerNames=new String[fullOrderList.size()];
        int i=0;
        for (FullOrder fullOrder : fullOrderList){
            customerNames[i]=dbHandler.GetCustomerNameByCustomerCode(fullOrder.getCustomerCode());
            i++;
        }
    }

    private void UpdateOrder(String ID){
        UpdateOrderFragment fragment=new UpdateOrderFragment();
        //fragment.SetActivity(activity);
        FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
    }
    private void DeleteOrder(String ID){
        boolean result=dbHandler.DeleteOrderById(ID);
        if(result){
            DisplyList();
            Toast.makeText(activity, "Delete Success", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(activity, "Delete Failed"+ID, Toast.LENGTH_LONG).show();
        }

    }

}
