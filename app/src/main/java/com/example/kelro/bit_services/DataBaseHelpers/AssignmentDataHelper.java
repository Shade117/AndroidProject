package com.example.kelro.bit_services.DataBaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.kelro.bit_services.Entity.Assignment;

import java.util.Date;

public class AssignmentDataHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static String DATABASE_NAME = "BIT-Services";
    private static String TABLE_NAME = "Assignment";
    private static String COL1 = "task_name";
    private static String COL2 = "task_info";
    private static String COL3 = "status";
    private static String COL4 = "date";
    private static String COL5 = "start_time";
    private static String COL6 = "end_time";
    private static String COL7 = "contact_number";
    private static String COL8 = "street";
    private static String COL9 = "suburb";
    private static String COL10 = "state";
    private static String COL11 = "postcode";
    private static String COL12 = "unit_no";
    public AssignmentDataHelper(Context context, boolean wipe) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if(wipe) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + " (id integer primary key autoincrement, " +
                    COL1 + " text, " +
                    COL2 + " text, " +
                    COL3 + " text, " +
                    COL4 + " date, " +
                    COL5 + " time, " +
                    COL6 + " time, " +
                    COL7 + " text, " +
                    COL8 + " text, " +
                    COL9 + " text, " +
                    COL10 + " text, " +
                    COL11 + " text, " +
                    COL12 + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void Add(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, assignment.getTask_name());
        contentValues.put(COL2, assignment.getTask_info());
        contentValues.put(COL3, assignment.getStatus());
        contentValues.put(COL4, assignment.getDate());
        contentValues.put(COL5, assignment.getStart_time());
        contentValues.put(COL6, assignment.getEnd_time());
        contentValues.put(COL7, assignment.getContact_number());
        contentValues.put(COL8, assignment.getStreet());
        contentValues.put(COL9, assignment.getSuburb());
        contentValues.put(COL10, assignment.getState());
        contentValues.put(COL11, assignment.getPostcode());
        contentValues.put(COL12, assignment.getUnit_no());
        Log.d("ADDING", "Add: Adding Data");
        long result = db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor GetAllToCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("select * from " + TABLE_NAME, null);
        return data;
    }

    public Cursor GetAssignmentData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("select * from " + TABLE_NAME + " where id = "+Integer.parseInt(id), null);
        return data;
    }
}
