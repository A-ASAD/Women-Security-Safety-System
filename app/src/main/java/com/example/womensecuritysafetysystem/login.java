package com.example.womensecuritysafetysystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        final TextView signUp = (TextView)findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign_UP = new Intent(login.this, sign_up.class);
                startActivity(sign_UP);
            }
        });
        final TextView login = (TextView)findViewById(R.id.lgn_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(login.this);
                String uname = ((TextView)findViewById(R.id.login_uname)).getText().toString().trim();
                String pass = ((TextView)findViewById(R.id.login_pass)).getText().toString().trim();
                if(uname.equals("") || pass.equals(""))
                {
                    new AlertDialog.Builder(login.this)
                            .setMessage("Please fill out all the fields!")
                            .setNegativeButton(android.R.string.yes, null)
                            .show();
                }
                else
                {
                    Cursor cursor = db.getReadableDatabase().query("users",null, "username = ? and password = ?",
                            new String[]{uname, pass},null,null,null);
                    if(cursor.getCount()>0)
                    {
                        Intent home = new Intent(login.this, home.class);
                        startActivity(home);
                        finish();
                    }
                    else
                    {
                        new AlertDialog.Builder(login.this)
                                .setMessage("Invalid Username or Password")
                                .setNegativeButton(android.R.string.yes, null)
                                .show();
                        ((TextView)findViewById(R.id.login_pass)).setText("");
                    }
                }

            }
        });
    }
}
