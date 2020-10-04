package com.aueb.rssidataapp;

import android.os.AsyncTask;

import com.aueb.rssidataapp.Connection.ApiService;

public class FetchPointsOfInterest extends AsyncTask<String, String, String> {


    @Override
    protected String doInBackground(String... strings) {
        ApiService apiService = new ApiService();
        return apiService.getPois();
    }


}