package com.siyanmo.tnrmobile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.siyanmo.tnrmobile.DomainObjects.User;
import com.siyanmo.tnrmobile.WebAPI.ItemAPI;
import com.siyanmo.tnrmobile.WebAPI.logIn;

public class Login extends AppCompatActivity {
    private static EditText UserName;
    private static EditText PassWord;
    private static Button btnSgn;
    private logIn login;
    private ItemAPI itemAPI;
    private RequestQueue requesstqueue;

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siyan_main_login);


    // find View-elements
    btnSgn = (Button) findViewById(R.id.sign);
    login();
        login = new logIn(this);
        itemAPI = new ItemAPI(this);
        requesstqueue = Volley.newRequestQueue(this);
        cd = new ConnectionDetector(getApplicationContext());
    }

    public void login() {
        UserName = (EditText)findViewById(R.id.Uname);
        PassWord = (EditText) findViewById(R.id.Pword);
        btnSgn=(Button)findViewById(R.id.sign);
        btnSgn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get Internet status
                        isInternetPresent = cd.isConnectingToInternet();


                        //Add Your Online Login Authority
                        String name = "admin";
                        String password = "123";

                        //Add Your Local Login Authority
                        String lname = "ladmin";
                        String lpassword = "l123";
                        //---------------------------------------------

// TextBox Validation and Login
                        if (UserName.getText().toString().equals("") && PassWord.getText().toString().equals("")) {
                            Toast.makeText(Login.this, "Please Enter  User Name and Password ", Toast.LENGTH_SHORT).show();
                        } else if (UserName.getText().toString().equals("")) {
                            Toast.makeText(Login.this, "Please Enter  User Name ", Toast.LENGTH_SHORT).show();
                        } else if (PassWord.getText().toString().equals("")) {
                            Toast.makeText(Login.this, "Please Enter Password ", Toast.LENGTH_SHORT).show();
                        } else {

                            //Check connectivity
                            //connection is true
                            if (isInternetPresent) {

                                User user = new User();
                                user.setLoginName(UserName.getText().toString());
                                user.setPassword(PassWord.getText().toString());
                                login.GetLog(user);
//                                JsonObjectRequest loginRequest = login.LoginRequest(user);
//                                JsonArrayRequest itemRequest = itemAPI.GetItemRequest();
//                                requesstqueue.add(loginRequest);
//                                requesstqueue.add(itemRequest);
                                Toast.makeText(Login.this, "Please wait", Toast.LENGTH_LONG).show();

                            }

                            //connection is false
                            else {
                                if (UserName.getText().toString().equals(lname) && PassWord.getText().toString().equals(lpassword))
                                //Local Connection
                                {
                                    Toast.makeText(Login.this, "Well Come", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent("com.siyanmo.tnrmobile.MainActivity");
                                    startActivity(intent);
                                } else {
                                    // Internet connection is not present
                                    Toast.makeText(Login.this, "Wrong User name or Password", Toast.LENGTH_LONG).show();
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
}









