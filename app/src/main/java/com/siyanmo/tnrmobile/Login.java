// create by LKMAL

package com.siyanmo.tnrmobile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.siyanmo.tnrmobile.DomainObjects.User;
import com.siyanmo.tnrmobile.SqliteDataProvider.DatabaseHandler;
import com.siyanmo.tnrmobile.WebAPI.ItemAPI;
import com.siyanmo.tnrmobile.WebAPI.logIn;
import static com.siyanmo.tnrmobile.CommanMethode.GetLogedUsear;

public class Login extends AppCompatActivity {
    private static EditText UserName;
    private static EditText PassWord;
    private static Button btnSgn;
    //public static final String Default ="";
    String lname ="";
    String lpassword="";
    private logIn login;
    private ItemAPI itemAPI;
    private RequestQueue requesstqueue;
    DatabaseHandler dbHandler;

    // flag for Internet connection status
    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siyan_main_login);

        dbHandler=new DatabaseHandler(this);
        // find View-elements
        UserName = (EditText)findViewById(R.id.Uname);
        PassWord = (EditText) findViewById(R.id.Pword);
        btnSgn=(Button)findViewById(R.id.sign);
        PassWord.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    btnSgn.performClick();
                    return true;
                }
                return false;
            }
        });

        login();

        login = new logIn(this);
        itemAPI = new ItemAPI(this);
        requesstqueue = Volley.newRequestQueue(this);
        cd = new ConnectionDetector(getApplicationContext());

        //deflt user save user name and password
        SharedPreferences  sharedPreferences=getSharedPreferences("TNRMobile_Login_User_Information", Context.MODE_PRIVATE);
        User user=GetLogedUsear(sharedPreferences);
        lname =user.getLoginName();
        lpassword=user.getPassword();

        UserName.setText(lname);


        //if have user name Cursor move to Password
        if(lname!="")
        {
            findViewById(R.id.Pword).requestFocus();
        }
        View view=findViewById(R.id.login_layout);
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hideSoftKeyboard();
                }

                return false;
            }
        });
    }

    //Login Button Acctivity
    public void login()
    {

        btnSgn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get Internet status
                        isInternetPresent = cd.isConnectingToInternet();

                        // TextBox Validation and Login
                        if (UserName.getText().toString().equals("") && PassWord.getText().toString().equals("")) {
                            Toast tost =Toast.makeText(Login.this, "Please Enter  User Name and Password ", Toast.LENGTH_SHORT);
                            ViewGroup group = (ViewGroup) tost.getView();
                            TextView messageTextView = (TextView) group.getChildAt(0);
                            messageTextView.setTextSize(20);
                            tost.show();
                        } else if (UserName.getText().toString().equals("")) {
                            Toast tost =Toast.makeText(Login.this, "Please Enter  User Name ", Toast.LENGTH_SHORT);
                            ViewGroup group = (ViewGroup) tost.getView();
                            TextView messageTextView = (TextView) group.getChildAt(0);
                            messageTextView.setTextSize(20);
                            tost.show();
                        } else if (PassWord.getText().toString().equals("")) {
                            Toast tost =Toast.makeText(Login.this, "Please Enter Password ", Toast.LENGTH_SHORT);
                            ViewGroup group = (ViewGroup) tost.getView();
                            TextView messageTextView = (TextView) group.getChildAt(0);
                            messageTextView.setTextSize(20);
                            tost.show();
                        } else {

                            //Check connectivity
                            //connection is true
                           if (isInternetPresent)
                            //( 1==2)
                            {
                                User user = new User();
                                user.setLoginName(UserName.getText().toString());
                                user.setPassword(PassWord.getText().toString());
                                login.GetLog(user);

                                Toast tost =Toast.makeText(Login.this, "Please wait", Toast.LENGTH_LONG);
                                ViewGroup group = (ViewGroup) tost.getView();
                                TextView messageTextView = (TextView) group.getChildAt(0);
                                messageTextView.setTextSize(20);
                                tost.show();
                                //save user name and Password
                                //SharedPreferences  sharedPreferences=getSharedPreferences("TNRMobile_Login_User_Information",Context.MODE_PRIVATE);
                                //SharedPreferences.Editor editor=sharedPreferences.edit();
                               // editor.putString("Uname",UserName.getText().toString());
                               // editor.putString("Password",PassWord.getText().toString());
                                //editor.commit();
                            }

                            //not connection// Local Login
                            else
                            {
                                if (UserName.getText().toString().equals(lname) && PassWord.getText().toString().equals(lpassword))
                                {
                                    Comman.setSalesExecutive(dbHandler.GetSalesmenDetail());
                                    //Toast.makeText(Login.this, "Well Come", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent("com.siyanmo.tnrmobile.MainActivity");
                                    startActivity(intent);
                                    //save user name and Password

//                                    SharedPreferences  sharedPreferences=getSharedPreferences("TNRMobile_Login_User_Information",Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor=sharedPreferences.edit();
//                                    editor.putString("Uname",UserName.getText().toString());
//                                    editor.putString("Password",(PassWord.getText().toString()));
//                                    editor.commit();
                                    //PassWord.setText("");

                                } else
                                {
                                    // Internet connection is not present
                                    Toast tost =Toast.makeText(Login.this, "No Internet Connection", Toast.LENGTH_LONG);
                                    ViewGroup group = (ViewGroup) tost.getView();
                                    TextView messageTextView = (TextView) group.getChildAt(0);
                                    messageTextView.setTextSize(20);
                                    tost.show();
                                }

                            }

                        }
                    }
                }
        );

    }



    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.login : R.drawable.login);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}









