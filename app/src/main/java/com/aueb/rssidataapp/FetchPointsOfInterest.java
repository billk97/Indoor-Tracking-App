package com.aueb.rssidataapp;

import android.os.AsyncTask;

import com.aueb.rssidataapp.Connection.ApiService;

public class FetchPointsOfInterest extends AsyncTask<String, String, String> {


    @Override
    protected String doInBackground(String... strings) {
        ApiService apiService = new ApiService();
        return apiService.getPois();
    }
//    @Override
//    protected void onPostExecute(String returnValue){
//        System.out.println(returnValue);
//        ArrayList<String> poiName = new ArrayList<>();
//        try {
//            pois = new ObjectMapper().readValue(returnValue, new TypeReference<List<PointOfInterest>>() {});
//            for (PointOfInterest po : pois){
//                poiName.add(po.getName());
//            }
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<String>
//                        (getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, poiName);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        starLocationSpinner.setAdapter(adapter);
//        destinationLocationSpinner.setAdapter(adapter);
//    }

}