package com.example.kelro.bit_services;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kelro.bit_services.DataBaseHelpers.AssignmentDataHelper;
import com.example.kelro.bit_services.DataBaseHelpers.DataBaseConnectionHelper;
import com.example.kelro.bit_services.Entity.Assignment;
import com.example.kelro.bit_services.Entity.Task;

import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskDetails extends Activity {
    TextView txtTaskName, txtStatus, txtTaskInfo, txtDate, txtStartTime, txtEndTime, txtAddress, txtContact;
    Button btnAccept, btnDecline, btnSMS, btnLocation, btnComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        btnLocation = findViewById(R.id.btnLocation);
        txtTaskName = findViewById(R.id.txtTaskName);
        txtStatus = findViewById(R.id.txtStatus);
        txtTaskInfo = findViewById(R.id.txtInfo);
        txtDate = findViewById(R.id.txtDate);
        txtStartTime = findViewById(R.id.txtStartTime);
        txtEndTime = findViewById(R.id.txtEndTime);
        txtAddress = findViewById(R.id.txtAddress);
        txtContact = findViewById(R.id.txtContact);
        btnSMS = findViewById(R.id.btnSMS);
        btnAccept = findViewById(R.id.btnAccept);
        btnDecline = findViewById(R.id.btnDecline);
        btnComplete = findViewById(R.id.btnComplete);
        final Intent intent = getIntent();
        SetDetails(intent.getStringExtra("id"));
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern p = Pattern.compile("(^\\d*) ([A-z ]*) (Rd|St|Ln|Av)\\n(\\w*)\\n([A-z]*) (\\d*)$");
                Matcher m = p.matcher(txtAddress.getText());
                if (m.find()) {
                    Uri navigationIntentUri = Uri.parse("google.navigation:q=" + m.group(0));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pattern p = Pattern.compile("(\\d{10})");
                //Matcher m = p.matcher(txtContact.getText());
                //if (m.find()) {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("sms: "+ txtContact.getText().toString().substring(12)));
                    // Allows adding template msg
                    sendIntent.putExtra("sms_body", "");
                    startActivity(sendIntent);
                //}
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", m.group(1), null));
                //startActivity(intent);
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtStatus.getText().toString().equals("Status: Pending")) {
                    Task.SetTaskAccepted(getApplicationContext(),intent.getStringExtra("id"));
                    txtStatus.setText("Accepted");
                    Toast.makeText(TaskDetails.this, "Accepted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(TaskDetails.this, "Unable to accept status not pending", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtStatus.getText().toString().equals("Status: Pending")) {
                    txtStatus.setText("Denied");
                    Task.SetTaskDenied(getApplicationContext(), intent.getStringExtra("id"));
                    Toast.makeText(TaskDetails.this, "Declined", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(TaskDetails.this, "Unable to decline status not pending", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtStatus.getText().toString().equals("Status: Accepted")) {
                    Intent i = new Intent(getApplicationContext(), Compete.class);
                    i.putExtra("id", intent.getStringExtra("id"));
                    startActivity(i);
                }
            }
        });
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