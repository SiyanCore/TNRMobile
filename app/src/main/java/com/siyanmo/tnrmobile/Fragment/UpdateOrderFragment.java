package com.siyanmo.tnrmobile.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.siyanmo.tnrmobile.R;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateOrderFragment extends Fragment {
    AppCompatActivity activity ;
    String orderId;
    DatabaseHandler dbHandler;
    View rootView;
    public UpdateOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView=inflater.inflate(R.layout.fragment_update_order,null);
        dbHandler=new DatabaseHandler(activity);

        rootView.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    OrdersFragment fragment=new OrdersFragment();
                    fragment.SetActivity(activity);
                    FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
                    return true;
                }
                return false;
            }
        } );
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_order, container, false);
    }

    public void SetActivity(AppCompatActivity sactivity,String ID){
        activity=sactivity;
        orderId=ID;
    }

    private void DeleteOrder() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        boolean result = dbHandler.DeleteOrderById(orderId);
                        if (result) {
                            OrdersFragment fragment=new OrdersFragment();
                            fragment.SetActivity(activity);
                            FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
                            Toast.makeText(activity, "Delete Success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(activity, "Delete Failed", Toast.LENGTH_LONG).show();
                        }
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
    }

    private void UpdateOrder(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        boolean result = true;//call quary to update
                        if (result) {
                            OrdersFragment fragment=new OrdersFragment();
                            fragment.SetActivity(activity);
                            FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
                            Toast.makeText(activity, "Update Success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(activity, "Update Failed", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Do you want Update this Order?")
                .setNegativeButton("No", dialogClickListener)
                .setPositiveButton("Yes", dialogClickListener).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        menu.findItem(R.id.action_save).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // Not implemented here
                return false;
            case R.id.action_cancel:
                OrdersFragment fragment=new OrdersFragment();
                fragment.SetActivity(activity);
                FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
                return true;
            case R.id.action_delete:
                DeleteOrder();
                return true;
            case R.id.action_update:
                UpdateOrder();
                return true;
            default:
                break;
        }

        return false;
    }
}
