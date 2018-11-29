package com.example.kelro.bit_services.Entity;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.example.kelro.bit_services.DataBaseHelpers.AssignmentDataHelper;
import com.example.kelro.bit_services.DataBaseHelpers.DataBaseConnectionHelper;

import java.util.HashMap;

public class Task {
    public static void SetTaskAccepted(Context context, String id) {
        AssignmentDataHelper assignmentDataHelper = new AssignmentDataHelper(context, false);
        Cursor data = assignmentDataHelper.GetAssignmentData(id);
        String date = "";
        String Starttime = "";
        while(data.move(1)) {
            if(!data.getString(1).equals("Task")) {
                date = data.getString(4);
                Starttime = data.getString(5);
            }
        }
        Log.d("LOG", date + " " + Starttime);
        DataBaseConnectionHelper dataBaseConnectionHelper = new DataBaseConnectionHelper();
        HashMap hashMap = new HashMap();
        hashMap.put("date", date);
        hashMap.put("starttime", Starttime);
        dataBaseConnectionHelper.DatabasePOST("http://bit-services.ftp.sh/android/SetTaskAccepted.php",hashMap);
    }

    public static void SetTaskDenied(Context context, String id) {
        AssignmentDataHelper assignmentDataHelper = new AssignmentDataHelper(context, false);
        Cursor data = assignmentDataHelper.GetAssignmentData(id);
        String date = "";
        String Starttime = "";
        while(data.move(1)) {
            if(!data.getString(1).equals("Task")) {
                date = data.getString(4);
                Starttime = data.getString(5);
            }
        }
        Log.d("LOG", date + " " + Starttime);
        DataBaseConnectionHelper dataBaseConnectionHelper = new DataBaseConnectionHelper();
        HashMap hashMap = new HashMap();
        hashMap.put("date", date);
        hashMap.put("starttime", Starttime);
        dataBaseConnectionHelper.DatabasePOST("http://bit-services.ftp.sh/android/SetTaskDenied.php",hashMap);
    }
    public static void SetTaskCompleted(Context context, String id, String KM) {
        AssignmentDataHelper assignmentDataHelper = new AssignmentDataHelper(context, false);
        Cursor data = assignmentDataHelper.GetAssignmentData(id);
        String date = "";
        String Starttime = "";
        while(data.move(1)) {
            if(!data.getString(1).equals("Task")) {
                date = data.getString(4);
                Starttime = data.getString(5);
            }
        }
        Log.d("LOG", date + " " + Starttime);
        DataBaseConnectionHelper dataBaseConnectionHelper = new DataBaseConnectionHelper();
        HashMap hashMap = new HashMap();
        hashMap.put("date", date);
        hashMap.put("starttime", Starttime);
        hashMap.put("km", KM);
        dataBaseConnectionHelper.DatabasePOST("http://bit-services.ftp.sh/android/SetTaskCompleted.php",hashMap);
    }

}
