package com.example.kelro.bit_services;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kelro.bit_services.DataBaseHelpers.DataBaseConnectionHelper;
import com.example.kelro.bit_services.DataBaseHelpers.UserDataHelper;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateRequestFragment extends Fragment {
    Button btnConfirm;
    TextView txtTitle, txtInfo;

    public CreateRequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_request, container, false);

        btnConfirm = v.findViewById(R.id.btnConfirm);
        txtTitle = v.findViewById(R.id.txtTitle);
        txtInfo = v.findViewById(R.id.txtInfo);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CM", "Btn");
                DataBaseConnectionHelper dbc = new DataBaseConnectionHelper();
                UserDataHelper udh = new UserDataHelper(getContext());
                HashMap hash = new HashMap();
                hash.put("title", txtTitle.getText());
                hash.put("info", txtInfo.getText());
                hash.put("id", udh.GetLoginID());
                dbc.DatabasePOST("http://bit-services.ftp.sh/android/CreateRequest.php", hash);
                Toast.makeText(getContext(), "Request Created", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
