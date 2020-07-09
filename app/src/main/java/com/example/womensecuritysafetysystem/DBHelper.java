package com.example.womensecuritysafetysystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    Context context;
    DBHelper(Context c)
    {
        super(c, "wsss.db", null, 1);
        this.context = c;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table users (id INTEGER primary key autoincrement, name TEXT, username TEXT, password TEXT, email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
