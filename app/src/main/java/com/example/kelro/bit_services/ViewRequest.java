package com.example.kelro.bit_services;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.*;
import com.example.kelro.bit_services.DataBaseHelpers.DataBaseConnectionHelper;
import com.example.kelro.bit_services.DataBaseHelpers.RequestDataHelper;
import com.example.kelro.bit_services.DataBaseHelpers.UserDataHelper;
import com.example.kelro.bit_services.Entity.Assignment;
import com.example.kelro.bit_services.Entity.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewRequest extends Activity {
    ListView lvRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        lvRequests = findViewById(R.id.lvRequest);
        try {
            GetRequestJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PopulateRequests();
    }

    private void GetRequestJSON() throws JSONException {
        UserDataHelper userDataHelper = new UserDataHelper(getApplicationContext());
        DataBaseConnectionHelper ch = new DataBaseConnectionHelper();
        HashMap hash = new HashMap();
        hash.put("id", userDataHelper.GetLoginID());
        String out = ch.DatabasePOST("http://bit-services.ftp.sh/android/GetClientRequests.php", hash);
        JSONArray output = new JSONArray(out);
        if (output.length() > 0) {
            RequestDataHelper requestDataHelper = new RequestDataHelper(getApplicationContext(), true);
            for (int i = 0; i < output.length(); i++) {
                JSONObject obj = output.getJSONObject(i);
                requestDataHelper.Add(new Request(obj.getInt("job_request_id"),
                        obj.getString("title"),
                        obj.getString("request_information"),
                        obj.getString("request_date"),
                        obj.getString("date_completed"),
                        obj.getString("status")));
            }
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void PopulateRequests() {
        RequestDataHelper requestDataHelper = new RequestDataHelper(getApplicationContext(), false);
        Cursor data = requestDataHelper.GetAllToCursor();
        ArrayList<String> list = new ArrayList<>();
        while(data.move(1)) {
            list.add(data.getString(data.getColumnIndex("id")) + " "+
                    data.getString(2) + "\n Status: " +
                    data.getString(3) + "\n Info: " +
                    data.getString(4) + "\n Request-Date:" +
                    data.getString(1) + "\n" +
                    data.getString(5)
                    );
        }
        list.add("HELLO");
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvRequests.setAdapter(adapter);
    }
}
