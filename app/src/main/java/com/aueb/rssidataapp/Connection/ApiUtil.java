package com.aueb.rssidataapp.Connection;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiUtil {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private String appUrl = "http://192.168.1.70:8080/";

    public String getRequest(String urlPath) throws IOException {
        OkHttpClient client = new OkHttpClient();
        final Request build = new Request.Builder()
                .url(appUrl + urlPath)
                .build();
        Request request = build;
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IllegalArgumentException("Failed to connect");
        }
        System.out.println(response.body().string());
        return response.body().string();
    }

    public String postRequest(String urlPath, String data) throws IOException {
        RequestBody body = RequestBody.create(data, JSON);
        final OkHttpClient client1 = new OkHttpClient();
        OkHttpClient client = client1;
        final Request build = new Request.Builder()
                .url(appUrl + urlPath)
                .post(body)
                .build();
        Request request = build;
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
