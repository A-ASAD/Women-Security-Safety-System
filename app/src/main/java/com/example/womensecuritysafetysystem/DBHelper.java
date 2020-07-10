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
        db.execSQL("Create table guardians (id INTEGER primary key autoincrement, name TEXT, phno TEXT, email TEXT,uid INTEGER,FOREIGN KEY(uid) REFERENCES users(id))");
        db.execSQL("CREATE table templlates (id INTEGER primary key autoincrement, template TEXT,uid INTEGER,FOREIGN KEY(uid) REFERENCES users(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
