package com.example.dbimport.test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.example.dbimport.db.CustomOpenHelper;

public class DbTest extends AndroidTestCase {
    SQLiteOpenHelper helper;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Context context = new RenamingDelegatingContext(getContext(), "test_");
        helper = new CustomOpenHelper(context);
        try{
        ImportFile.importCsvFile(context, helper, "test.csv");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void test_dbの中身が２件入ってることを確認() throws Exception {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from person_table;", null);
        assertEquals( c.getCount(), 2);
    }
}
