package com.example.kelro.bit_services.DataBaseHelpers;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DataBaseConnectionHelper {
    String output = "";
    HashMap input;
    public String DatabasePOST(final String urlString, HashMap hashmap) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                if(android.os.Debug.isDebuggerConnected())
                    android.os.Debug.waitForDebugger();
                super.onPostExecute(s);

            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {
                if(android.os.Debug.isDebuggerConnected())
                    android.os.Debug.waitForDebugger();
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    String response = "";
                    con.setReadTimeout(13000);
                    con.setConnectTimeout(13000);
                    con.setRequestMethod("POST");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    Uri.Builder builder = new Uri.Builder();
                    for (Object o : input.entrySet()) {
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
                    output = sb.toString().trim();
                    return output;
                } catch (Exception e) {
                    Log.e("Dammit", e.toString());
                    return e.toString();
                }
            }
        }

        input = hashmap;
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
        try {
            String bob = getJSON.get(20,  TimeUnit.SECONDS);
            String hi = "hi";
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return output;
    }
}
