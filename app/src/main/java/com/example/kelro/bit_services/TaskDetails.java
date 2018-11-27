package com.example.kelro.bit_services;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import com.example.kelro.bit_services.DataBaseHelpers.AssignmentDataHelper;
import com.example.kelro.bit_services.Entity.Assignment;

public class TaskDetails extends Activity {
    TextView txtTaskName, txtStatus, txtTaskInfo, txtDate, txtStartTime, txtEndTime, txtAddress, txtContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        txtTaskName = findViewById(R.id.txtTaskName);
        txtStatus = findViewById(R.id.txtStatus);
        txtTaskInfo = findViewById(R.id.txtInfo);
        txtDate = findViewById(R.id.txtDate);
        txtStartTime = findViewById(R.id.txtStartTime);
        txtEndTime = findViewById(R.id.txtEndTime);
        txtAddress = findViewById(R.id.txtAddress);
        txtContact = findViewById(R.id.txtContact);
        Intent intent = getIntent();
        SetDetails(intent.getStringExtra("id"));
    }

    private void SetDetails(String id) {
        AssignmentDataHelper assignmentDataHelper = new AssignmentDataHelper(getApplicationContext(), false);
        Cursor data = assignmentDataHelper.GetAssignmentData(id);
        while(data.move(1)) {
            if(!data.getString(1).equals("Task")) {
                txtTaskName.append(data.getString(1));
                txtStatus.append(data.getString(3));
                txtTaskInfo.setText(data.getString(2));
                txtDate.append(data.getString(4));
                txtStartTime.append(data.getString(5));
                txtEndTime.append(data.getString(6));
                txtContact.append(data.getString(7));
                if (!data.getString(12).equals("null")) {
                    txtAddress.setText("Unit "+data.getString(12)+"\n");
                }
                txtAddress.setText( data.getString(8) + "\n" +
                                    data.getString(9) + "\n" +
                                    data.getString(10) + " " +
                                    data.getString(11));
            }
        }
    }
}



/*
"task_name";
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
 */