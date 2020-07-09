package com.example.womensecuritysafetysystem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        final TextView add_guardian = (TextView)findViewById(R.id.button3);
        add_guardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_guardian = new Intent(home.this, add_guardian.class);
                startActivity(add_guardian);
            }
        });
        final TextView add_template = (TextView)findViewById(R.id.button5);
        add_template.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_template = new Intent(home.this, add_template.class);
                startActivity(add_template);
            }
        });
        findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(home.this);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putBoolean("isLogin", false);
                Ed.commit();

                Intent login = new Intent(home.this, login.class);
                startActivity(login);
                Toast.makeText(home.this, "Logged out successfully!", Toast.LENGTH_SHORT);
                finish();
            }
        });
    }
}
