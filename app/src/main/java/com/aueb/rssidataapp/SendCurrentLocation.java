package com.aueb.rssidataapp;

import android.os.AsyncTask;

import com.aueb.rssidataapp.Connection.ApiService;

public class SendCurrentLocation extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings) {
        ApiService apiService = new ApiService();
        apiService.sendLocation(strings[0], Double.valueOf(strings[1]), Double.valueOf(strings[2]));
        return null;
    }
}