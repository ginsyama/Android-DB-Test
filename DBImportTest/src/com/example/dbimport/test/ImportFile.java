package com.example.dbimport.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class ImportFile {
    public static void importCsvFile(Context context, SQLiteOpenHelper helper, String path, String tableName){
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open("test/" + path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            List<List<String>> data = new ArrayList<>();
            for( int i = 0; (line = br.readLine()) != null; i++){
                StringTokenizer token = new StringTokenizer(line, ",");
                List<String> row = new ArrayList<>();
                while (token.hasMoreTokens()) {
                    row.add(token.nextToken().trim());
                }
                data.add(row);
            }
            SQLiteHelper.insertDB(helper, tableName, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static class SQLiteHelper{
        public static boolean isTableExists( SQLiteDatabase db, String tableName){
            Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
            if(cursor != null){
                if(cursor.getCount() > 0) {
                    return true;
                }
                cursor.close();
            }
            return false;
        }
        
        public static void insertDB( SQLiteOpenHelper helper, String tableName,List<List<String>> data) throws Exception{
            SQLiteDatabase db = helper.getWritableDatabase();
            if(isTableExists(db, tableName)){
                List<String> columnName = data.get(0);
                
                StringBuilder sb = new StringBuilder();
                StringBuilder value = new StringBuilder();
                for(int i = 0; i < columnName.size(); i++){
                    sb.append( columnName.get(i) + " ," );
                    value.append( " ? ,");
                }
                sb.deleteCharAt(sb.length() -1);
                value.deleteCharAt(value.length() -1);
                
                SQLiteStatement sqLiteStatement = 
                        db.compileStatement("INSERT INTO " + tableName + " ( " + 
                                            sb.toString() + " " + 
                                            ") VALUES ( " + value.toString() + " );");
                for(int i = 1; i < data.size(); i++){
                    sqLiteStatement.bindAllArgsAsStrings( data.get(i).toArray(new String[0]));
                    sqLiteStatement.execute();
                }
            }else{
                throw new Exception("ぐはは");
            }
        }
        
    }
    
}
