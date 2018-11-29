package com.example.kelro.bit_services;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.kelro.bit_services.DataBaseHelpers.DataBaseConnectionHelper;
import com.example.kelro.bit_services.DataBaseHelpers.UserDataHelper;
import com.example.kelro.bit_services.Entity.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
                String out = CH.DatabasePOST(LoginURL, test);
                try {
                    output = new JSONObject(out);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(output.has("id")) {
                        UserDataHelper userDataHelper = new UserDataHelper(getApplicationContext());
                        userDataHelper.addUser(new User(output.getInt("id"), output.getString("name"), output.getString("type")));
                        CheckJobs checkJobs = new CheckJobs();
                        checkJobs.checkJobsRecurExecute(userDataHelper.GetLoginID());
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
            CheckJobs checkJobs = new CheckJobs();
            checkJobs.checkJobsRecurExecute(userDataHelper.GetLoginID());
            Intent i = new Intent(getApplicationContext(), ContractorHome.class);
            startActivity(i);
        }

    }

    // Check jobs is responsible for pinging the server to check if the jobs have been updated
    class CheckJobs {
        public void checkJobsRecurExecute(int idin) {
            final String id = String.valueOf(idin);
            class CheckJobsAsync extends AsyncTask<Void, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(String s) {
                    if (android.os.Debug.isDebuggerConnected())
                        android.os.Debug.waitForDebugger();
                    super.onPostExecute(s);

                }

                //in this method we are fetching the json string
                @Override
                protected String doInBackground(Void... voids) {
                    if (android.os.Debug.isDebuggerConnected())
                        android.os.Debug.waitForDebugger();
                    try {
                        URL url = new URL("http://bit-services.ftp.sh/android/checkjob.php");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        String response = "";
                        con.setReadTimeout(15000);
                        con.setConnectTimeout(15000);
                        con.setRequestMethod("POST");
                        con.setDoInput(true);
                        con.setDoOutput(true);
                        Uri.Builder builder = new Uri.Builder();
                        HashMap hashMap = new HashMap();
                        hashMap.put("id", id);
                        for (Object o : hashMap.entrySet()) {
                            Map.Entry entry = (Map.Entry) o;
                            builder.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                        }
                        String query = builder.build().getEncodedQuery();
                        OutputStream os = con.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write(query);
                        writer.flush();
                        writer.close();
                        os.close();
                        con.connect();
                        StringBuilder sb = new StringBuilder();
                        InputStream in = new BufferedInputStream(con.getInputStream());
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String json;
                        while ((json = bufferedReader.readLine()) != null) {
                            sb.append(json + "\n");
                        }
                        Log.d("Connection", sb.toString().trim());
                        JSONObject jsonObject = new JSONObject(sb.toString().trim());
                        if(!jsonObject.getString("results").equals("Same")) {
                            NotificationCompat.Builder notification;
                            final int id = 5123;
                            notification = new NotificationCompat.Builder(getApplicationContext());
                            notification.setAutoCancel(true);
                            notification.setSmallIcon(R.mipmap.bitlogo);
                            notification.setTicker("This is the ticker");
                            notification.setWhen(System.currentTimeMillis());
                            notification.setContentTitle("You have updated Job Information");
                            notification.setContentTitle("You have updated job information");
                            notification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                            notification.setPriority(NotificationCompat.PRIORITY_HIGH);
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pendingIntent);
                            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(id, notification.build());
                        }
                        Thread.sleep(10000);
                        CheckJobsAsync checkJobs = new CheckJobsAsync();
                        checkJobs.execute();
                        return null;
                    } catch (Exception e) {
                        Log.e("Dammit", e.toString());
                        return e.toString();
                    }
                }
            }
            CheckJobsAsync checkJobsAsync = new CheckJobsAsync();
            checkJobsAsync.execute();
        }
    }

}
