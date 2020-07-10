package com.example.womensecuritysafetysystem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class view_guardian extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_guardian);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

        RecyclerView rv = (RecyclerView)findViewById(R.id.guardian_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        UserGuardian u1 = new UserGuardian("Usama", "090078601","usama@gmail.com", 1 );
        UserGuardian u2 = new UserGuardian("Shahid", "090079601","usama1@gmail.com", 2 );
        UserGuardian u3 = new UserGuardian("Mahmood", "090088601","usama2@gmail.com", 3 );
        UserGuardian u4 = new UserGuardian("Hamza", "090078701","usama3@gmail.com", 4 );
        UserGuardian u5 = new UserGuardian("Ali", "090078604","usama4@gmail.com", 5 );
        ArrayList<UserGuardian> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        list.add(u3);
        list.add(u4);
        list.add(u5);
        rv.setLayoutManager(llm);
        GuardianRVAdapter adapter = new GuardianRVAdapter(this,list);
        rv.setAdapter(adapter);
    }
}