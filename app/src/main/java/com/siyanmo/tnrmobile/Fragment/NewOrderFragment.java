package com.siyanmo.tnrmobile.Fragment;


import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.siyanmo.tnrmobile.CommanMethode;
import com.siyanmo.tnrmobile.DomainObjects.Customer;
import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.DomainObjects.OrderItems;
import com.siyanmo.tnrmobile.R;
import com.siyanmo.tnrmobile.SelectDateFragment;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrderFragment extends Fragment {

    private AutoCompleteTextView autoCompleteItem;
    private AutoCompleteTextView autoCompleteCus;
    private List<Customer> customerList;
    private List<Item> iemlist;
    private ArrayAdapter<String> Itemadapter;
    private ArrayAdapter<String> CusAdapter;
    private AppCompatActivity activity;
    private DatabaseHandler dbHandler;
    private TextView OrderDate;
    private GridView OrderGrid;
    public NewOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_new_order, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        OrderGrid = (GridView)view.findViewById(R.id.gridView);
        OrderGrid.setAdapter(new NewOrderViweAdapter(activity));

        dbHandler=new DatabaseHandler(activity);
        iemlist = dbHandler.GetAllItems();
        customerList = dbHandler.GetAllCustomer();
        String[] iemArry = CommanMethode.GetItemNameArry(iemlist);
        String[] customerArry = CommanMethode.GetCustomerNameArry(customerList);
        // Inflate the layout for this fragment
        Itemadapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,iemArry);
        CusAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,customerArry);

        autoCompleteItem = (AutoCompleteTextView) view.findViewById(R.id.ItemName);
        autoCompleteCus = (AutoCompleteTextView) view.findViewById(R.id.CusTomerName);

        // set adapter for the auto complete fields
        autoCompleteItem.setAdapter(Itemadapter);
        autoCompleteCus.setAdapter(CusAdapter);
        // specify the minimum type of characters before drop-down list is shown
        autoCompleteItem.setThreshold(1);
        autoCompleteCus.setThreshold(1);

        autoCompleteItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int p = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        OrderDate = (TextView) view.findViewById(R.id.txtdate);
        Date cal = (Date) Calendar.getInstance().getTime();
        String dt = cal.toLocaleString();
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        OrderDate.setText(dateFormat.format(cal));
        //onDateClick ();
        super.onViewCreated(view, savedInstanceState);
    }


    public void SetActivity(AppCompatActivity sactivity){
        activity=sactivity;
    }

    private void SaveOrder(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        boolean result = true;//call quary to Save
                        if (result) {
                            NewOrderFragment fragment=new NewOrderFragment();
                            fragment.SetActivity(activity);
                            FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
                            Toast.makeText(activity, "Save Success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(activity, "Save Failed", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Do you want Save this Order?")
                .setNegativeButton("No", dialogClickListener)
                .setPositiveButton("Yes", dialogClickListener).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {

        menu.findItem(R.id.action_update).setVisible(false);
        menu.findItem(R.id.action_delete).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // Not implemented here
                return false;
            case R.id.action_cancel:
                NewOrderFragment fragment=new NewOrderFragment();
                fragment.SetActivity(activity);
                FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
                return true;
            case R.id.action_save:
                SaveOrder();
                return true;
            default:
                break;
        }

        return false;
    }

    private void onDateClick (){
        OrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(activity.getFragmentManager(), "DatePicker");
            }
        });
    }
}

class NewOrderViweAdapter extends BaseAdapter{

    ArrayList<OrderItems> orderItemses;
    Context context;
    public NewOrderViweAdapter(Context context){
        this.context = context;
        orderItemses = new ArrayList<OrderItems>();
        OrderItems order = new OrderItems("1000", (float) 10);
        OrderItems order2 = new OrderItems("101010", (float) 10);
        orderItemses.add(order);
        orderItemses.add(order2);
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

        public OrderHolder(View view){
            Itme_Name = (TextView)view.findViewById(R.id.Itme_Name);
            Item_Code = (TextView)view.findViewById(R.id.Item_Code);
            Order_Quntity = (TextView)view.findViewById(R.id.Order_Quntity);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        OrderHolder holder = null;
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.order_grid_item, parent, false);
            holder = new OrderHolder(row);
            row.setTag(holder);
        }else{
            holder = (OrderHolder) row.getTag();
        }
        holder.Item_Code.setText(orderItemses.get(position).getItemCode());
        holder.Order_Quntity.setText(orderItemses.get(position).getOrderQuntity().toString());
        holder.Itme_Name.setText("not set yest");
        return row;
    }
}
