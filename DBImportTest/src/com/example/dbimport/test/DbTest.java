package com.example.dbimport.test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.example.dbimport.db.CustomOpenHelper;

public class DbTest extends InstrumentationTestCase {
    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Context context = new RenamingDelegatingContext(getInstrumentation().getTargetContext(), "test_");
        helper = new CustomOpenHelper(context);
        db = helper.getReadableDatabase();
    }
    
    public void test_dbの中身が２件入ってることを確認() throws Exception {
        ImportFile.importCsvFile(getInstrumentation().getContext(), helper, "test.csv", "person_table");
        Cursor c = db.rawQuery("select * from person_table;", null);
        assertEquals( 2, c.getCount());
    }
    
    public void test_dbの中身が空になっていることを確認() throws Exception {
        Cursor rawQuery = db.rawQuery("select * from person_table", null);
        assertEquals(0, rawQuery.getCount());
    }
}
