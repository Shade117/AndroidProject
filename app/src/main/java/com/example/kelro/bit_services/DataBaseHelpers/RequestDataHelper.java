package com.example.kelro.bit_services.DataBaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.kelro.bit_services.Entity.Request;

public class RequestDataHelper extends SQLiteOpenHelper {
    private static final int database_version = 3;
    private static String database_name = "BIT-Services";
    private static String table_name = "Request";
    private static String COL1 = "request_date";
    private static String COL2 = "title";
    private static String COL3 = "status";
    private static String COL4 = "info";
    private static String COL5 = "completed_date";

    public RequestDataHelper(Context context, boolean wipe) {
        super(context, database_name, null, database_version);
        if(wipe) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + table_name);
            onCreate(db);
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + " (id integer primary key autoincrement, " +
                        COL1 + " text, " +
                        COL2 + " text, " +
                        COL3 + " text, " +
                        COL4 + " text, " +
                        COL5 + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table_name);
        onCreate(db);
    }

    public void Add(Request request) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", request.getId());
        contentValues.put(COL1, request.getRequest_date());
        contentValues.put(COL2, request.getTitle());
        contentValues.put(COL3, request.getStatus());
        contentValues.put(COL4, request.getInfo());
        contentValues.put(COL5, request.getComplete_date());
        Log.d("TEST", "ADDED");
        long result = db.insert(table_name, null, contentValues);
    }

    public Cursor GetAllToCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("select * from " + table_name, null);
        return data;
    }
}
