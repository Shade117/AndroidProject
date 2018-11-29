package com.example.kelro.bit_services;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kelro.bit_services.Entity.Task;

public class Compete extends Activity {
    TextView txtKM;
    Button btnComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compete);
        txtKM = findViewById(R.id.txtKMsTraveled);
        btnComplete = findViewById(R.id.btnComplete);
        final Intent intent = getIntent();

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   SendComplete();
                Toast.makeText(Compete.this, "Assignment Completed", Toast.LENGTH_SHORT).show();
                   finish();
            }
        });
    }

    private void SendComplete() {
        if(!txtKM.getText().toString().equals(""))  {
            Task.SetTaskCompleted(getApplicationContext(), getIntent().getStringExtra("id"), txtKM.getText().toString());
        }
    }
}
