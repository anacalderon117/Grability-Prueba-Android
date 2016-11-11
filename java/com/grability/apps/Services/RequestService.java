package com.grability.apps.Services;

import android.os.StrictMode;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ana on 08/11/2016.
 */

public class RequestService {

    public RequestService (){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    // HTTP GET request
    public JSONObject doGetRequest(String path) throws Exception {
        JSONObject jsonObj = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("Sending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            jsonObj = new JSONObject(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  jsonObj;
    }
}
