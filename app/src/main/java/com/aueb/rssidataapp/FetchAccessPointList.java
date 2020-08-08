package com.aueb.rssidataapp;

import android.os.AsyncTask;
import android.util.Log;

import com.aueb.rssidataapp.Connection.ApiService;
import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class FetchAccessPointList extends AsyncTask<String, String, List<AccessPoint>> {

    Callback callback;

    public FetchAccessPointList(Callback callback) {
        this.callback = callback;
    }

    @Override
    public List<AccessPoint> doInBackground(String... strings) {
        ApiService apiService = new ApiService();
        List<AccessPoint> accessPoints = null;
        Log.i("sout", "getting Access point List");
        Log.i("sout", apiService.getAccessPoints());
        try {
            accessPoints = new ObjectMapper().readValue(apiService.getAccessPoints(), new TypeReference<List<AccessPoint>>() {
            });
        } catch (JsonProcessingException e) {
            System.err.println("Error while parsing accessPoints");
            //e.printStackTrace();
        }
        if (accessPoints == null) {
            throw new IllegalArgumentException("list accessPoints is null");
        }
        System.out.println(accessPoints.size());
        return accessPoints;
    }

    @Override
    protected void onPostExecute(List<AccessPoint> accessPoints) {
        super.onPostExecute(accessPoints);
        for (AccessPoint accessPoint : accessPoints) {
            callback.addKnownAccessPoint(accessPoint);
        }
    }
}