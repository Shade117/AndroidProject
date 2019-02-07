package com.example.kelro.bit_services;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Unavailabilty extends Activity {

    Button btnUnavail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unavailabilty);
        btnUnavail = findViewById(R.id.button);
        btnUnavail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
