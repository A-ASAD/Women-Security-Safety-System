package com.example.womensecuritysafetysystem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class view_guardian extends AppCompatActivity {
    String username=home.username;
    int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_view_guardian);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

        RecyclerView rv = (RecyclerView)findViewById(R.id.guardian_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        DBHelper db = new DBHelper(view_guardian.this);
        Cursor cur = db.getReadableDatabase().query("users",null,"username = ?",new String[]{username},null,null,null);
        while (cur.moveToNext())
        {
            uid=cur.getInt(0);
        }
        Cursor cursor = db.getReadableDatabase().query("guardians",null,"uid = ?",new String[]{String.valueOf(uid)},null,null,null);
        ArrayList<UserGuardian> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            UserGuardian u1 = new UserGuardian(cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getInt(0) );
            list.add(u1);

        }
        rv.setLayoutManager(llm);
        GuardianRVAdapter adapter = new GuardianRVAdapter(this,list);
        rv.setAdapter(adapter);
    }
}