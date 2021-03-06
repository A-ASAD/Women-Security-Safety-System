package com.example.womensecuritysafetysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent login = new Intent(MainActivity.this, login.class);
                startActivity(login);
                finish();
            }
        },1000);
    }

}
