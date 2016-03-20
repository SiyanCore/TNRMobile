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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.siyanmo.tnrmobile.Adapter.NewOrderViweAdapter;
import com.siyanmo.tnrmobile.Comman;
import com.siyanmo.tnrmobile.CommanMethode;
import com.siyanmo.tnrmobile.DomainObjects.Customer;
import com.siyanmo.tnrmobile.DomainObjects.Item;
import com.siyanmo.tnrmobile.DomainObjects.SalesOrderDetail;
import com.siyanmo.tnrmobile.DomainObjects.SalesOrderHeader;
import com.siyanmo.tnrmobile.R;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrderFragment extends Fragment {

    private ImageButton AddButton;
    private AutoCompleteTextView autoCompleteItem;
    private AutoCompleteTextView autoCompleteCus;
    private TextView OrderDate;
    private TextView TotalAmount;
    private TextView Outstanding;
    private GridView OrderGrid;
    private EditText Quntity;
    private AutoCompleteTextView ItemCode;
    private TextView ItemName;
    private TextView Remark;

    private List<Customer> customerList;
    private List<Item> iemlist;
    private ArrayAdapter<String> Itemadapter;
    private ArrayAdapter<String> CusAdapter;
    private ArrayAdapter<String> ItemCodeadapter;
    private AppCompatActivity activity;
    private DatabaseHandler dbHandler;
    private NewOrderViweAdapter newOrderViweAdapter;


    private List<String> ItemCodes;
    private List<String> CustomerCodes;
    private List<String> ItemNames;
    private Item SelectedItem;
    private String  CustomerCode;
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
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        OrderGrid = (GridView)view.findViewById(R.id.gridView);
        AddButton = (ImageButton) view.findViewById(R.id.add);
        OrderDate = (TextView) view.findViewById(R.id.txtdate);
        Outstanding=(TextView) view.findViewById(R.id.textOutstanding);
        autoCompleteItem = (AutoCompleteTextView) view.findViewById(R.id.ItemName);
        autoCompleteCus = (AutoCompleteTextView) view.findViewById(R.id.CusTomerName);
        Quntity = (EditText) view.findViewById(R.id.txtquntity);
        ItemCode = (AutoCompleteTextView)view.findViewById(R.id.Itemcode);
        ItemName = (TextView)view.findViewById(R.id.ItemName);
        TotalAmount = (TextView)view.findViewById(R.id.textTotal);
        Remark=(EditText)view.findViewById(R.id.Remark);
        newOrderViweAdapter = new NewOrderViweAdapter(activity);
        OrderGrid.setAdapter(newOrderViweAdapter);

        dbHandler=new DatabaseHandler(activity);
        iemlist = dbHandler.GetAllItems();
        customerList = dbHandler.GetAllCustomer();
        String[] itemArry = CommanMethode.GetItemNameArry(iemlist);
        ItemNames =  Arrays.asList(itemArry);
        String[] itemCodeArry = CommanMethode.GetItemCodeArry(iemlist);
        ItemCodes = Arrays.asList(itemCodeArry);
        String[] customerArry = CommanMethode.GetCustomerNameArry(customerList);
        CustomerCodes=Arrays.asList(customerArry);
        // Inflate the layout for this fragment
        Itemadapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, itemArry);
        CusAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,customerArry);
        ItemCodeadapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, itemCodeArry);


        // set adapter for the auto complete fields
        autoCompleteItem.setAdapter(Itemadapter);
        autoCompleteCus.setAdapter(CusAdapter);
        ItemCode.setAdapter(ItemCodeadapter);
        // specify the minimum type of characters before drop-down list is shown
        autoCompleteItem.setThreshold(1);
        ItemCode.setThreshold(1);
        autoCompleteCus.setThreshold(1);

        Date cal = (Date) Calendar.getInstance().getTime();
        OrderDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(cal));
        //onDateClick ();
        OnButtonClick();
        OnGridItemLongClick();
        OnItemNameClicked();
        OnItemCodeClicked();
        OnCustomerClicked();
        view.setOnTouchListener(new View.OnTouchListener() {

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

    private void OnCustomerClicked() {
        autoCompleteCus.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                Outstanding.setText(dbHandler.GetOutstandingByCustomerName(autoCompleteCus.getText().toString()));
            }
        });
    }

    private void OnItemCodeClicked() {
        ItemCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                //Toast.makeText(activity, " selected" + ItemCodes.get(pos)+"/"+pos, Toast.LENGTH_LONG).show();
                SelectedItem = dbHandler.GetItemByItemCode(ItemCode.getText().toString());
                autoCompleteItem.setText(SelectedItem.getItemNameShown());

            }
        });
    }

    private void OnItemNameClicked() {
        autoCompleteItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                //Toast.makeText(activity, " selected" + ItemNames.get(pos)+"/"+pos, Toast.LENGTH_LONG).show();
                SelectedItem = dbHandler.GetItemByItemName(autoCompleteItem.getText().toString());
                ItemCode.setText(SelectedItem.getItemCode());
            }
        });
    }

    private void OnGridItemLongClick() {
        OrderGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                try {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    newOrderViweAdapter.DeleteData(position);
                                    SetTotalAmount();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Do you want Delete this Order?")
                            .setNegativeButton("No", dialogClickListener)
                            .setPositiveButton("Yes", dialogClickListener).show();
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            }
        });
    }


    public void SetActivity(AppCompatActivity sactivity){
        activity=sactivity;
    }

    private void SaveOrder() {
        if (newOrderViweAdapter.getCount()>0) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            List<SalesOrderDetail> orders = newOrderViweAdapter.GetOrderItemses();
                            SalesOrderHeader salesOrderHeader = MakeSalesOrderHearde();
                            boolean result = dbHandler.InsertOrder(salesOrderHeader, orders);
                            //dbHandler.
                            if (result) {
                                NewOrderFragment fragment = new NewOrderFragment();
                                fragment.SetActivity(activity);
                                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
                                Toast tost = Toast.makeText(activity, "Save Success", Toast.LENGTH_LONG);
                                ViewGroup group = (ViewGroup) tost.getView();
                                TextView messageTextView = (TextView) group.getChildAt(0);
                                messageTextView.setTextSize(20);
                                tost.show();
                            } else {
                                Toast tost = Toast.makeText(activity, "Save Failed", Toast.LENGTH_LONG);
                                ViewGroup group = (ViewGroup) tost.getView();
                                TextView messageTextView = (TextView) group.getChildAt(0);
                                messageTextView.setTextSize(20);
                                tost.show();
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
        else {
            Toast tost = Toast.makeText(activity, "There are no Items to Save", Toast.LENGTH_LONG);
            ViewGroup group = (ViewGroup) tost.getView();
            TextView messageTextView = (TextView) group.getChildAt(0);
            messageTextView.setTextSize(20);
            tost.show();
        }
    }
    private SalesOrderHeader MakeSalesOrderHearde(){
        SalesOrderHeader salesHeader = new SalesOrderHeader();
        if(ValidateHeader()) {

            salesHeader.setSalesmanCode(Comman.getSalesExecutive().getSalesExecutiveCode());
            salesHeader.setOrderAmount(Float.parseFloat(TotalAmount.getText().toString()));
            salesHeader.setCustomerCode(CustomerCode);
            salesHeader.setRemark(Remark.getText().toString());
        }
        return salesHeader;
    }

    private boolean ValidateHeader() {
        boolean ok =true;
        if(autoCompleteCus.getText().toString().equals("")){
            ok=false;
            Toast tost =Toast.makeText(activity, "Customer Name Empty", Toast.LENGTH_SHORT);
            ViewGroup group = (ViewGroup) tost.getView();
            TextView messageTextView = (TextView) group.getChildAt(0);
            messageTextView.setTextSize(20);
            tost.show();
        }else {
            CustomerCode = dbHandler.GetCustomerCodeByCustomerName(autoCompleteCus.getText().toString());
            if(CustomerCode.equals("")||CustomerCode.isEmpty()){
                ok =false;
                Toast tost =Toast.makeText(activity, "Customer Name Error", Toast.LENGTH_SHORT);
                ViewGroup group = (ViewGroup) tost.getView();
                TextView messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(20);
                tost.show();
            }

        }
        return ok;
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

    private void OnButtonClick(){
       AddButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               hideSoftKeyboard();
               OnAddButtonClick();
           }
       });
    }

    public void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void OnAddButtonClick() {
        if(ValidationItemAdding()) {
            Float quantity = Float.parseFloat(Quntity.getText().toString());
            Float amount = quantity*SelectedItem.getSellingPrice();
            String itemName =(dbHandler.GetItemByItemCode(ItemCode.getText().toString())).getItemNameShown();
            SalesOrderDetail NewOrder = new SalesOrderDetail(ItemCode.getText().toString(), itemName,quantity,amount,SelectedItem.getSellingPrice());
            newOrderViweAdapter.filterData(NewOrder);
            ClearItemFieds();
            SetTotalAmount();
            int index = OrderGrid.getLastVisiblePosition();
            OrderGrid.smoothScrollToPosition(index);
        }
    }
    private void SetTotalAmount (){
        List<SalesOrderDetail> orders = newOrderViweAdapter.GetOrderItemses();
        float sum = 0;
        for (SalesOrderDetail order:
                orders) {
            sum = sum+ order.getValue();
        }
        String total = String.format("%.2f", sum);
        TotalAmount.setText( total);
    }
    private void ClearItemFieds() {
        Quntity.setText("");
        ItemCode.setText("");
        ItemName.setText("");
    }

    private boolean ValidationItemAdding() {
        boolean ok=true;
        try {

            if(Float.parseFloat(Quntity.getText().toString()) < 0) {
                ok = false;
                Toast tost =Toast.makeText(activity, "Item Quantity Error", Toast.LENGTH_SHORT);
                ViewGroup group = (ViewGroup) tost.getView();
                TextView messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(20);
                tost.show();

            }
            String itemCod = ItemCode.getText().toString();
            if(itemCod.equals("")){
                ok = false;
                Toast tost =Toast.makeText(activity, "Item Code Empty", Toast.LENGTH_SHORT);
                ViewGroup group = (ViewGroup) tost.getView();
                TextView messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(20);
                tost.show();
            }
            if(!ItemCodes.contains(itemCod)){
                ok = false;
                Toast tost =Toast.makeText(activity, "Item Code Not Exist", Toast.LENGTH_SHORT);
                ViewGroup group = (ViewGroup) tost.getView();
                TextView messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(20);
                tost.show();
            }
        }catch(NumberFormatException ex){
            ok =false;
            Toast tost =Toast.makeText(activity, "Item Quantity Error", Toast.LENGTH_SHORT);
            ViewGroup group = (ViewGroup) tost.getView();
            TextView messageTextView = (TextView) group.getChildAt(0);
            messageTextView.setTextSize(20);
            tost.show();
        }catch(Exception ex){

        }
        return ok;
    }
}

