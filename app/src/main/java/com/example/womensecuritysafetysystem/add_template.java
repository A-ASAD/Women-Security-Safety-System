package com.example.womensecuritysafetysystem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class add_template extends AppCompatActivity {
    String username;
    int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        username=home.username;
        final TextView save = (TextView)findViewById(R.id.saveTemp);
        save.setOnClickListener(new View.OnClickListener() {
            final String text = ((TextView)findViewById(R.id.msg)).getText().toString().trim();
            @Override
            public void onClick(View v) {
                if(text.equals("")){
                    new AlertDialog.Builder(add_template.this)
                            .setMessage("Please fill out all the fields!")
                            .setNegativeButton(android.R.string.yes, null)
                            .show();
                    Toast.makeText(getBaseContext(), "Error: Empty Field!", Toast.LENGTH_SHORT).show();
                }
                else{
                    DBHelper db = new DBHelper(add_template.this);
                    Cursor cur = db.getReadableDatabase().query("users",null,"username = ?",new String[]{username},null,null,null);
                    while (cur.moveToNext()) {
                        uid = cur.getInt(0);
                    }
                    ContentValues template = new ContentValues();
                    template.put("template",text);
                    template.put("uid",uid);
                    db.getWritableDatabase().insert("templates",null, template);

                    new AlertDialog.Builder(add_template.this)
                            .setMessage("Template added successfully!\nPress OK to continue.")
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
        });

    }
}