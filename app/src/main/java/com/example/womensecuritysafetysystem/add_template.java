package com.example.womensecuritysafetysystem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class add_template extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        final TextView save = (TextView)findViewById(R.id.saveTemp);
        final TextView text = (TextView)findViewById(R.id.editText8);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.getText().toString().trim().equals("")){
                    Toast.makeText(getBaseContext(), "Error: Empty Field!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getBaseContext(), "Message added successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}