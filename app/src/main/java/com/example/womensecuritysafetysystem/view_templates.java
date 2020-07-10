package com.example.womensecuritysafetysystem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class view_templates extends AppCompatActivity {
    String username=home.username;
    int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_templates);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        RecyclerView rv = (RecyclerView)findViewById(R.id.temp_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        DBHelper db = new DBHelper(view_templates.this);
        Toast.makeText(this, "u: "+username, Toast.LENGTH_SHORT).show();
        Cursor cur = db.getReadableDatabase().query("users",null,"username = ?",new String[]{username},null,null,null);
        while (cur.moveToNext())
        {
            uid=cur.getInt(0);
        }
        Cursor cursor = db.getReadableDatabase().query("templates",null,"uid = ?",new String[]{String.valueOf(uid)},null,null,null);
        ArrayList <UserTemplates> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            UserTemplates t1 = new UserTemplates(cursor.getString(1),cursor.getInt(0) );
            list.add(t1);

        }
        rv.setLayoutManager(llm);
        TemplateRVAdapter adapter = new TemplateRVAdapter(this,list);
        rv.setAdapter(adapter);


    }
}