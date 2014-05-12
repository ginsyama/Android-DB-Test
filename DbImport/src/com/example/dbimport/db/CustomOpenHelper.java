package com.example.dbimport.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "sampledb";
    private static final int DB_VERSION = 1;
    
    public CustomOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "create table person_table( name text not null, age text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

}
