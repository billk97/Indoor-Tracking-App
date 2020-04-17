package com.aueb.rssidataapp.Connection;

import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ConnectionHandler {
    private String ipAddress = "192.168.1.80";
    private String port = "8080";

    public AccessPoint[] getAccessPointList(){
        URL url = null;
        BufferedReader reader = null;
        try {
            url = new URL("http://"+ipAddress+":"+port+"/access-point");
            HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null){
                System.out.println("Steam returned null");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String jsonString = reader.readLine();
            System.out.println(jsonString);
            Gson gson = new Gson();
            AccessPoint [] ap = gson.fromJson(jsonString,AccessPoint[].class);
            System.out.println(ap.length);

            return gson.fromJson(jsonString,AccessPoint[].class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
