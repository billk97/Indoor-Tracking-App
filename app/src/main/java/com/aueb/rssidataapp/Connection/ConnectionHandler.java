package com.aueb.rssidataapp.Connection;

import com.aueb.rssidataapp.Triangulation.AccessPoint;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class ConnectionHandler {
    private String ipAddress = "192.168.1.70";
    private String port = "8080";

    public String getRequest(String urlRequestParam){
        URL url = null;
        BufferedReader reader = null;
        try {
            url = new URL("http://"+ipAddress+":"+port+"/"+urlRequestParam);
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
            return jsonString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendLocation(String devName, double lat , double lon){
        URL url = null;
        BufferedReader reader = null;
        try {
            url = new URL("http://"+ipAddress+":"+port+"poi");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);
            String jsonString = "{name :" + devName + ", lat: " + lat + ",lon: " + lon +"}";
            try (OutputStream os = connection.getOutputStream()){
                byte [] input = jsonString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            System.out.println(line);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
