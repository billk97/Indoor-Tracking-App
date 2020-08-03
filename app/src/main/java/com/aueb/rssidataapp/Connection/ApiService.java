package com.aueb.rssidataapp.Connection;

import com.aueb.rssidataapp.Triangulation.Nav;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class ApiService {
    ApiUtil apiUtil = new ApiUtil();

    public String getRequest(String urlRequestParam) {
        try {
            return apiUtil.getRequest("poi");
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Connection failed");
    }

    public void sendLocation(String devName, double lat, double lon) {
        String jsonString = "{name :" + devName + ", lat: " + lat + ",lon: " + lon + "}";
        try {
            apiUtil.postRequest("poi", jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String NavInstructions(String urlRequestParam, Nav nav) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInputString = objectMapper.writeValueAsString(nav);
        return apiUtil.postRequest(urlRequestParam, jsonInputString);

    }

}
