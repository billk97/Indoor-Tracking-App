package com.aueb.rssidataapp.Connection;

import com.aueb.rssidataapp.Triangulation.AccessPoint;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ConnectionHandler {
    private String ipAddress = "192.168.1.70";
    private String port = "8080";

    public String getAccessPointList(){
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
            return jsonString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
