package com.siyanmo.tnrmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    private static EditText UserName;
    private static EditText PassWord;
    private static Button btnSgn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siyan_main_login);


    // find View-elements
    btnSgn = (Button) findViewById(R.id.sign);
    login();
    }

    public void login() {
        UserName = (EditText)findViewById(R.id.Uname);
        PassWord = (EditText) findViewById(R.id.Pword);
        btnSgn=(Button)findViewById(R.id.sign);
        btnSgn.setOnClickListener(
            new View.OnClickListener() {
            @Override
            public void onClick(View v) {

    //Add Your Login Authority
                String name="admin";
                String password="123";
    //---------------------------------------------

    // TextBox Validation and Login
                if(UserName.getText().toString().equals("")&& PassWord.getText().toString().equals("")){
                    Toast.makeText(Login.this, "Please Enter User Nme", Toast.LENGTH_SHORT).show();
                }
                else if(UserName.getText().toString().equals("")){
                    Toast.makeText(Login.this, "Please Enter  User Name and Password", Toast.LENGTH_SHORT).show();
                }
                else if(PassWord.getText().toString().equals(""))
                {
                    Toast.makeText(Login.this, "Please Enter Password ", Toast.LENGTH_SHORT).show();
                }
                else if(UserName.getText().toString().equals(name)&& PassWord.getText().toString().equals(password))
                {
                    Toast.makeText(Login.this, "Well Come "+name, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent("com.siyanmo.tnrmobile.MainActivity");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Login.this,"Wrong Pass word or User Name",Toast.LENGTH_LONG).show();
                }
    //----------------------------------------------------------------------------------------------------
            }
        }
        );

    }



}




