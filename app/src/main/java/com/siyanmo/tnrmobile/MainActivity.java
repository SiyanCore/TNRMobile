package com.siyanmo.tnrmobile;

import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.siyanmo.tnrmobile.Events.SyncEvent;
import com.siyanmo.tnrmobile.Fragment.CustomersFragment;
import com.siyanmo.tnrmobile.Fragment.ItemsFragment;
import com.siyanmo.tnrmobile.Fragment.NewOrderFragment;
import com.siyanmo.tnrmobile.Fragment.OrdersFragment;
import com.siyanmo.tnrmobile.Fragment.PopUpItemFragment;
import com.siyanmo.tnrmobile.Fragment.UpdateOrderFragment;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseHandler dbHandler;
    NavigationView navigationView=null;
    Toolbar toolbar=null;
    TextView salesmanName;
    ImageView salesmanImage;
    int navId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set main fragment
        NewOrderFragment fragment=new NewOrderFragment();
        fragment.SetActivity(this);
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
        //-------------------
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        salesmanName=(TextView)findViewById(R.id.salesmanName);
        salesmanName.setText(Comman.getSalesExecutive().getSalesExecutiveName());
        salesmanImage=(ImageView)findViewById(R.id.salesmanImage);
        salesmanImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        byte[] image=Comman.getSalesExecutive().getImage();
        if (image.length>0){
            Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
            if(bitmap!=null){
                salesmanImage.setImageBitmap(bitmap);
            }

        }


        final android.app.FragmentManager fragmentManager=getFragmentManager();
        final PopUpItemFragment popUpItemFragment=new PopUpItemFragment();
        popUpItemFragment.SetActivity(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpItemFragment.show(fragmentManager,"Items List");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        dbHandler=new DatabaseHandler(this);
        navId=R.id.nav_new_order;
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (navId == R.id.nav_new_order){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            ActivityCompat.finishAffinity(MainActivity.this);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Exit Now ?")
                    .setNegativeButton("No", dialogClickListener)
                    .setPositiveButton("Yes", dialogClickListener).show();
        }
        else {
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
            navigationView.getMenu().getItem(0).setChecked(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_new_order) {
            NewOrderFragment fragment=new NewOrderFragment();
            fragment.SetActivity(this);
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
            navId=id;
        } else if (id == R.id.nav_orders) {
            OrdersFragment fragment=new OrdersFragment();
            fragment.SetActivity(this);
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
            navId=id;
        } else if (id == R.id.nav_item_details) {
            ItemsFragment fragment=new ItemsFragment();
            fragment.SetActivity(this);
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
            navId=id;
        } else if (id == R.id.nav_customers) {
            CustomersFragment fragment=new CustomersFragment();
            fragment.SetActivity(this);
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment).commit();
            navId=id;
        } else if (id == R.id.nav_sync) {
            ConnectionDetector connectionDetector = new ConnectionDetector(this);
            if(connectionDetector.isConnectingToInternet()) {
                SyncEvent syncEvent = new SyncEvent(this);
                syncEvent.SYncAll();
            }else {
                Toast tost =Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG);
                ViewGroup group = (ViewGroup) tost.getView();
                TextView messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(20);
                tost.show();
            }
        } else if (id == R.id.nav_log_out) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            finish();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Logout Now?")
                    .setNegativeButton("No", dialogClickListener)
                    .setPositiveButton("Yes", dialogClickListener).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
