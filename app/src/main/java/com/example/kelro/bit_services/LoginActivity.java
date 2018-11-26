package com.example.kelro.bit_services;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.kelro.bit_services.DataBaseHelpers.DataBaseConnectionHelper;
import com.example.kelro.bit_services.DataBaseHelpers.UserDataHelper;
import com.example.kelro.bit_services.Entity.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends Activity {
    private EditText txtusername, txtpassword;
    private Button btnlogin;
    private static final String LoginURL = "http://bit-services.ftp.sh/android/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtusername = findViewById(R.id.txtUsername);
        txtpassword = findViewById(R.id.txtPassword);
        btnlogin = findViewById(R.id.btnLogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseConnectionHelper CH = new DataBaseConnectionHelper();
                HashMap test = new HashMap();
                test.put("username", txtusername.getText().toString());
                test.put("password", txtpassword.getText().toString());
                JSONObject output = null;
                output = CH.DatabasePOST(LoginURL, test);
                try {
                    if(output.has("id")) {
                        UserDataHelper userDataHelper = new UserDataHelper(getApplicationContext());
                        userDataHelper.addUser(new User(output.getInt("id"), output.getString("name"), output.getString("type")));
                        Intent i = new Intent(getApplicationContext(), ContractorHome.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, output.getString("fail"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        UserDataHelper userDataHelper = new UserDataHelper(getApplicationContext());

        if(userDataHelper.IsLoggedIn()) {
            Intent i = new Intent(getApplicationContext(), ContractorHome.class);
            startActivity(i);
        }
    }
}
