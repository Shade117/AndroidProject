package com.example.kelro.bit_services;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.kelro.bit_services.DataBaseHelpers.UserDataHelper;

public class ContractorHome extends Activity {
    Button btnLogout, btnAssignments, btnUnavailabilitys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_home);
        btnLogout = findViewById(R.id.btnLogout);
        btnAssignments = findViewById(R.id.btnAssignments);
        btnUnavailabilitys = findViewById(R.id.btnUnavailability);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDataHelper userDataHelper = new UserDataHelper(getApplicationContext());
                userDataHelper.Logout();
                finish();
            }
        });

        btnAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContractorAssignement.class);
                startActivity(intent);
            }
        });

        btnUnavailabilitys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Unavailabilty.class);
                startActivity(intent);
            }
        });
    }
}
