package com.example.womensecuritysafetysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class select_template extends AppCompatActivity {
    String username;
    int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_template);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        username = home.username;
        RecyclerView rc = (RecyclerView) findViewById(R.id.select_template_rv);
        rc.setLayoutManager(new LinearLayoutManager(this));
        DBHelper db = new DBHelper(select_template.this);
        Cursor cur = db.getReadableDatabase().query("users",null,"username = ?",new String[]{username},null,null,null);
        while (cur.moveToNext())
        {
            uid=cur.getInt(0);
        }
        Cursor cursor = db.getReadableDatabase().query("templates",null,"uid = ?",new String[]{String.valueOf(uid)},null,null,null);
        ArrayList <String> templates = new ArrayList<>();
        while (cursor.moveToNext())
        {
            templates.add(cursor.getString(1));
        }
        rc.setAdapter(new SelectTemplateRVAdapter(this, templates));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED)
                    Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
