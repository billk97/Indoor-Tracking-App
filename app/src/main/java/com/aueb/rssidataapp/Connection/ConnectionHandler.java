package com.aueb.rssidataapp.Connection;

import com.aueb.rssidataapp.Triangulation.AccessPoint;

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

    public List<AccessPoint> getAccessPointList(){
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
            JSONArray jsonArray = new JSONArray(jsonString);
            //List<AccessPoint> = JSONArray
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        AccessPoint a1 = new AccessPoint("ssid", "bssid", 10,1d);
        AccessPoint a2 = new AccessPoint("ssid2", "bssid", 11,1d);
        List<AccessPoint> accessPoints = new ArrayList<>();
        accessPoints.add(a1);
        accessPoints.add(a2);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(a1);
        jsonArray.put(a2);
        //System.out.println(jsonArray.toString());
        //List<AccessPoint> = JSONArray
    }
}
