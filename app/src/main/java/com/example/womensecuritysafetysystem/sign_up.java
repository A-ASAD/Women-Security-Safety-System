package com.example.womensecuritysafetysystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class sign_up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        findViewById(R.id.signupBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uname = ((TextView)findViewById(R.id.su_uname)).getText().toString().trim();
                String name = ((TextView)findViewById(R.id.su_name)).getText().toString().trim();
                final String pass = ((TextView)findViewById(R.id.su_pass)).getText().toString().trim();
                String cPass = ((TextView)findViewById(R.id.su_cpass)).getText().toString().trim();
                String email = ((TextView)findViewById(R.id.su_email)).getText().toString().trim();
                if(uname.equals("") || pass.equals("")|| cPass.equals("")|| name.equals("")|| email.equals(""))
                {
                    new AlertDialog.Builder(sign_up.this)
                            .setMessage("Please fill out all the fields!")
                            .setNegativeButton(android.R.string.yes, null)
                            .show();
                }
                else if(!pass.equals(cPass))
                {
                    new AlertDialog.Builder(sign_up.this)
                            .setMessage("Both passwords must match!")
                            .setNegativeButton(android.R.string.yes, null)
                            .show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    new AlertDialog.Builder(sign_up.this)
                            .setMessage("Please enter a valid Email address!")
                            .setNegativeButton(android.R.string.yes, null)
                            .show();
                }
                else
                {
                    DBHelper db = new DBHelper(sign_up.this);
                    Cursor cursor = db.getReadableDatabase().query("users",null, "username = ? or email = ?",
                            new String[]{uname, email},null,null,null);
                    if(cursor.getCount() == 0)
                    {
                        ContentValues user = new ContentValues();
                        user.put("name",name);
                        user.put("username",uname);
                        user.put("password",pass);
                        user.put("email",email);
                        db.getWritableDatabase().insert("users",null, user);
                        new AlertDialog.Builder(sign_up.this)
                                .setMessage("User added successfully!\nPress OK to continue.")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        new AlertDialog.Builder(sign_up.this)
                                                .setMessage("Do you want to save credentials for next login?")
                                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(sign_up.this);
                                                        SharedPreferences.Editor Ed=sp.edit();
                                                        Ed.putBoolean("isLogin", true);
                                                        Ed.commit();

                                                        login.lgn.finish();
                                                        Intent home = new Intent(sign_up.this, home.class);
                                                        startActivity(home);
                                                        finish();

                                                    }
                                                })
                                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        login.lgn.finish();
                                                        Intent home = new Intent(sign_up.this, home.class);
                                                        startActivity(home);
                                                        finish();
                                                    }
                                                })
                                                .show();
                                    }
                                })
                                .show();
                    }
                    else
                    {
                        new AlertDialog.Builder(sign_up.this)
                                .setMessage("A user with this Username or Email already exists!\nTry changing Email or Username.")
                                .setNegativeButton(android.R.string.yes, null)
                                .show();
                    }
                }
            }
        });

    }
}
