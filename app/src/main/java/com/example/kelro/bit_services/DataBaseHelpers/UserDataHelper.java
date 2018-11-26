package com.example.kelro.bit_services.DataBaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.kelro.bit_services.Entity.User;

public class UserDataHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static String DATABASE_NAME = "BIT-Services";
    private static String TABLE_NAME = "User";
    private static String KEY_ID = "id";
    private static String KEY_NAME = "name";
    private static String KEY_TYPE = "type";
    private SQLiteDatabase db;

    public UserDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public UserDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE;
        CREATE_TABLE= "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " integer primary key, " +
                KEY_NAME + " text not null, " +
                KEY_TYPE + " text not null);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(User user)
    { // we are going to add Student to the database
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // you are using this class to share data in activity and database
        contentValues.put(KEY_ID,user.getId());
        contentValues.put(KEY_NAME,user.getName());
        contentValues.put(KEY_TYPE, user.getType());
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
        db = this.getReadableDatabase();
        Log.d("test", String.valueOf(DatabaseUtils.queryNumEntries(db, TABLE_NAME)));
        // nullColumnhack : sometimes you want to insert empty row  content values with null
        // so then you use this hack
        // so if you want null rows then instead of null give the name of atleast one column which can take null value
    }

    public boolean IsLoggedIn() {
        db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        if(count == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public void Logout() {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME);
    }
}
