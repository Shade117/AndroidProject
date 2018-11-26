package com.example.kelro.bit_services.DataBaseHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class AssignmentDataHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static String DATABASE_NAME = "BIT-Services";
    private static String TABLE_NAME = "Assignment";
    private static String COL1  = "id";
    private static String COL2 = "name";
    private static String COL3 = "status";
    private static String COL4 = "info";
    private static String COL5 = "date";
    private static String COL6 = "start_time";
    private static String COL7 = "end_time";

    public AssignmentDataHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
