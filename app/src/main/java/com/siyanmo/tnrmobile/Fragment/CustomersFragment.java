package com.siyanmo.tnrmobile.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.siyanmo.tnrmobile.DomainObjects.Customer;
import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.R;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomersFragment extends Fragment {

    ListView listView;
    SearchView searchView;
    ArrayAdapter<String> arrayAdapter;
    String[] customers;
    DatabaseHandler dbHandler;
    AppCompatActivity activity ;

    public CustomersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_customers,null);
        listView=(ListView)rootView.findViewById(R.id.CustomersListView);
        searchView=(SearchView)rootView.findViewById(R.id.CustomersSearchView);
        dbHandler=new DatabaseHandler(activity);
        SetCustomers();
        arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,customers);
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

        // Inflate the layout for this fragment
        return rootView;
    }

    public void SetCustomers(){
        List<Customer> customerList=new ArrayList<>();
        customerList=dbHandler.GetAllCustomer();
        List<String>customerDetails=new ArrayList<>();
        for (Customer customer : customerList){
            customerDetails.add(customer.getCustomerName()+" / Customer Code-"+customer.getCustomerCode().toString());
        }
        customers=new String[customerDetails.size()];
        customers=customerDetails.toArray(customers);
    }

    public void SetActivity(AppCompatActivity sactivity){
        activity=sactivity;
    }
}
