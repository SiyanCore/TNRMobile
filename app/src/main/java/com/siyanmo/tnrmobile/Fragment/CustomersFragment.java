package com.siyanmo.tnrmobile.Fragment;


import android.content.Context;
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

        setHasOptionsMenu(true);

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hideSoftKeyboard();
                }

                return false;
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void SetCustomers(){
        List<Customer> customerList=new ArrayList<>();
        customerList=dbHandler.GetAllCustomer();
        List<String>customerDetails=new ArrayList<>();
        for (Customer customer : customerList){
            customerDetails.add(customer.getCustomerName()+" / Outstanding - Rs "+String.format("%.2f", customer.getOutstanding()));
        }
        customers=new String[customerDetails.size()];
        customers=customerDetails.toArray(customers);
    }

    public void SetActivity(AppCompatActivity sactivity){
        activity=sactivity;
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
