package com.aueb.rssidataapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.aueb.rssidataapp.Connection.ConnectionHandler;
import com.aueb.rssidataapp.Triangulation.PointOfInterest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConfigDirections extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner starLocationSpinner, destinationLocationSpinner;
    private Button findMeButton, StartNavigationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_directions);
        initialize();
        PoiRunner runner = new PoiRunner();
        runner.execute("poi");

    }

    private void initialize(){
        starLocationSpinner = (Spinner) findViewById(R.id.ConfigSpinnerStart);
        destinationLocationSpinner = (Spinner) findViewById(R.id.ConfigDestinationSpinner);
        findMeButton = (Button) findViewById(R.id.ConfigFindMeButton);
        StartNavigationButton = (Button) findViewById(R.id.ConfigStartButoon);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(parent.getItemAtPosition(position));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class PoiRunner extends AsyncTask<String, String,String> implements AdapterView.OnItemSelectedListener {
        String response = null;

        @Override
        protected String doInBackground(String... strings) {
            ConnectionHandler connectionHandler = new ConnectionHandler();
            response = connectionHandler.getRequest(strings[0]);
            return  response;
        }

        protected void onPostExecute(String... strings){
            ArrayList<String> poiName = null;
            List<PointOfInterest> pois = null;
            try {
                pois = new ObjectMapper().readValue(response, new TypeReference<List<PointOfInterest>>() {});
                for (PointOfInterest po : pois){
                    poiName.add(po.getName());
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>
                            (getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, poiName);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            starLocationSpinner.setAdapter(adapter);
            starLocationSpinner.setOnItemSelectedListener(this);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            System.out.println(parent.getItemAtPosition(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
