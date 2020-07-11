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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class add_guardian extends AppCompatActivity {
    String username;
    int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guardian);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        username=home.username;
        final TextView saveG = (TextView) findViewById(R.id.saveGuardian);

        Bundle extras = getIntent().getExtras();
        final int gid = extras.getInt("recordNo");

        if(gid!=-1){
            String guard_name = extras.getString("name");
            String guard_email = extras.getString("email");
            String guard_no= extras.getString("phone");
            TextView heading = findViewById(R.id.textView4);
            heading.setText("Update Guardian");
            EditText guardian_name = findViewById(R.id.g_name);
            EditText guardian_mail= findViewById(R.id.g_email);
            EditText guardian_no = findViewById(R.id.g_phno);
            guardian_name.setText(guard_name);
            guardian_mail.setText(guard_email);
            guardian_no.setText(guard_no);
        }
        saveG.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                final String g_name = ((TextView)findViewById(R.id.g_name)).getText().toString().trim();
                final String  g_phno = ((TextView)findViewById(R.id.g_phno)).getText().toString().trim();
                final String g_email = ((TextView)findViewById(R.id.g_email)).getText().toString().trim();
                if (g_name.equals("")||g_phno.equals("") ||g_email.equals("")) {
                    new AlertDialog.Builder(add_guardian.this)
                            .setMessage("Please fill out all the fields!")
                            .setNegativeButton(android.R.string.yes, null)
                            .show();
                }
                else if(g_phno.length()<11)
                {
                    new AlertDialog.Builder(add_guardian.this)
                            .setMessage("Please enter a Valid Number!")
                            .setNegativeButton(android.R.string.yes, null)
                            .show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(g_email).matches())
                {
                    new AlertDialog.Builder(add_guardian.this)
                            .setMessage("Please enter a valid Email address!")
                            .setNegativeButton(android.R.string.yes, null)
                            .show();
                }
                else {

                    DBHelper db = new DBHelper(add_guardian.this);
                    Cursor cur = db.getReadableDatabase().query("users",null,"username = ?",new String[]{username},null,null,null);
                    while (cur.moveToNext())
                    {
                         uid=cur.getInt(0);
                    }

                    ContentValues guardian = new ContentValues();
                    guardian.put("name",g_name);
                    guardian.put("phno",g_phno);
                    guardian.put("email",g_email);
                    guardian.put("uid",uid);
                    if(gid==-1){ //for new record
                        Cursor cursor = db.getReadableDatabase().query("guardians",null, "phno = ? or email = ?",
                                new String[]{g_phno, g_email},null,null,null);
                        if(cursor.getCount() == 0)
                        {
                            db.getWritableDatabase().insert("guardians",null, guardian);

                            new AlertDialog.Builder(add_guardian.this)
                                    .setMessage("Guardian added successfully!\nPress OK to continue.")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            finish();
                                        }
                                    })
                                    .show();
                        }
                        else {
                            new AlertDialog.Builder(add_guardian.this)
                                    .setMessage("A user with this phone no. or Email already exists!\nTry changing Email or phone no.")
                                    .setNegativeButton(android.R.string.yes, null)
                                    .show();
                        }
                    }
                    else { //for editing record
                        int rows = db.getWritableDatabase().update("guardians", guardian, "id=?",new String[]{gid+""});
                        if (rows>0)
                        {
                            new AlertDialog.Builder(add_guardian.this)
                                    .setMessage("Guardian Updated successfully!\nPress OK to continue.")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            finish();
                                        }
                                    })
                                    .show();
                        }

                    }
                }
            }
        });
    }
}

