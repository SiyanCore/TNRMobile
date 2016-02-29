package com.siyanmo.tnrmobile.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.siyanmo.tnrmobile.Comman;
import com.siyanmo.tnrmobile.DomainObjects.FullOrder;
import com.siyanmo.tnrmobile.R;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

    ListView listView;
    SearchView searchView;
    ArrayAdapter<String> arrayAdapter;
    String[] orders;
    DatabaseHandler dbHandler;
    AppCompatActivity activity ;

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_orders,null);
        listView=(ListView)rootView.findViewById(R.id.OrderListView);
        searchView=(SearchView)rootView.findViewById(R.id.OrderSearchView);
        dbHandler=new DatabaseHandler(activity);
        SetOrders();
        arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,orders);
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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Logout Now?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return false;
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    public void SetOrders(){
        List<FullOrder> fullOrderList=new ArrayList<>();
        fullOrderList=dbHandler.GetAllOrdersBySalesmanCode(Comman.getSalesExecutive().getSalesExecutiveCode());
        List<String>orderList=new ArrayList<>();
        for (FullOrder fullOrder : fullOrderList){
            String customerName = dbHandler.GetCustomerNameByCustomerCode(fullOrder.getCustomerCode());
            orderList.add((fullOrder.getOrderId())+"."+customerName+" / Date-"+fullOrder.getOrderDate().toString());
        }
        orders=new String[orderList.size()];
        orders=orderList.toArray(orders);
    }

    public void SetActivity(AppCompatActivity sactivity){
        activity=sactivity;
    }


}
