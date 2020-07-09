package com.example.womensecuritysafetysystem;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class add_guardian extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guardian);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

        final TextView saveG = (TextView) findViewById(R.id.saveGuardian);
        final TextView name = (TextView) findViewById(R.id.editText);
        final TextView phNo = (TextView) findViewById(R.id.editText1);
        final TextView email = (TextView) findViewById(R.id.login_uname);

        saveG.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                if (name.getText().toString().trim().equals("")||phNo.getText().toString().trim().equals("")
                        ||email.getText().toString().trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Error: Empty Field!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Guardian added successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

