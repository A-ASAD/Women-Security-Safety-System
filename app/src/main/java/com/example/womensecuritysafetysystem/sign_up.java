package com.example.womensecuritysafetysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
                //Toast.makeText(sign_up.this, "Sign Up", Toast.LENGTH_SHORT);
                DBHelper db = new DBHelper(sign_up.this);
                ContentValues user = new ContentValues();
                user.put("name","asad");
                user.put("username","asad");
                user.put("password","123");
                user.put("email","asad@gmail.com");
                Cursor cursor = db.getReadableDatabase().query("users",null, "username = ? and email = ?",
                        new String[]{"asad", "asad@gmail.com"},null,null,null);
                if(cursor.getCount() == 0)
                {
                    db.getWritableDatabase().insert("users",null, user);
                    Toast.makeText(sign_up.this, "Inserted", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
