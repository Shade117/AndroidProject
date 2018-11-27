package com.example.kelro.bit_services;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.kelro.bit_services.DataBaseHelpers.AssignmentDataHelper;
import com.example.kelro.bit_services.DataBaseHelpers.DataBaseConnectionHelper;
import com.example.kelro.bit_services.DataBaseHelpers.UserDataHelper;
import com.example.kelro.bit_services.Entity.Assignment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ContractorAssignement extends Activity {
    ListView lvAssignemnts;
    String StringURL = "http://bit-services.ftp.sh/android/GetAssignmentsForContractor.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_assignement);
        lvAssignemnts = findViewById(R.id.lvAssignments);
        GetAssignmentsJSON();
        PopulateAssignments();
    }

    private void PopulateAssignments() {
        AssignmentDataHelper assignmentDataHelper = new AssignmentDataHelper(getApplicationContext(), false);
        Cursor data = assignmentDataHelper.GetAllToCursor();
        ArrayList<String> list = new ArrayList<>();
        while(data.move(1)) {
            if (!data.getString(1).equals("Task")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                SimpleDateFormat tdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = null;
                Date stime = null;
                Date etime = null;
                try {
                    date = sdf.parse(data.getString(4));
                    stime = sdf.parse(data.getString(5));
                    etime = sdf.parse(data.getString(6));
                    sdf.applyPattern("dd-MM-yyyy");
                    tdf.applyPattern("hh:mm");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                list.add(   data.getString(data.getColumnIndex("id")) + " " +
                            data.getString(1) + "\n" +
                            data.getString(2) + "\n" +
                            "Status: " + data.getString(3) + "\n" +
                            "Date: " + sdf.format(date));
            }
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvAssignemnts.setAdapter(adapter);
        lvAssignemnts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemid  = lvAssignemnts.getItemAtPosition(position).toString().substring(0, 1);
                Intent taskdetails = new Intent(getApplicationContext(), TaskDetails.class);
                taskdetails.putExtra("id", itemid);
                startActivity(taskdetails);

            }
        });
    }

    private void GetAssignmentsJSON() {
                UserDataHelper userDataHelper = new UserDataHelper(getApplicationContext());
                DataBaseConnectionHelper CH = new DataBaseConnectionHelper();
                HashMap test = new HashMap();
                String out;
                JSONArray output = null;
                test.put("id", userDataHelper.GetLoginID());
                out = CH.DatabasePOST(StringURL, test);
                try {
                    output = new JSONArray(out);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if(output.length() > 0) {
                        AssignmentDataHelper assignmentDataHelper = new AssignmentDataHelper(getApplicationContext(), true);
                        for (int i = 0; i < output.length(); i++) {
                            JSONObject obj = output.getJSONObject(i);
                            assignmentDataHelper.Add(new Assignment(obj.getString("task_name"),
                                    obj.getString("task_info"),
                                    obj.getString("status"),
                                    obj.getString("date_assigned"),
                                    obj.getString("start_time"),
                                    obj.getString("end_time"),
                                    obj.getString("contact_number"),
                                    obj.getString("streetname"),
                                    obj.getString("suburb"),
                                    obj.getString("state"),
                                    obj.getString("postcode"),
                                    obj.getString("unit_no")));
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "SHIT", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
    }
}
