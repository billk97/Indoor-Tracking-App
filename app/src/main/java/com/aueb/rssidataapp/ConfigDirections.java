package com.aueb.rssidataapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.aueb.rssidataapp.Connection.ApiService;
import com.aueb.rssidataapp.Triangulation.Nav;
import com.aueb.rssidataapp.Triangulation.PointOfInterest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfigDirections extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener ,Serializable {
    private Spinner starLocationSpinner, destinationLocationSpinner;
    private Button findMeButton, StartNavigationButton;
    private List<PointOfInterest> pois = null;
    private PointOfInterest startLocation, destinationLocation = null;
    //Todo implement Find me button
    //Todo get navigation info
    //Todo catch exceptions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_directions);
        initialize();
        PoiRunner runner = new PoiRunner();
        runner.execute("poi");
        starLocationSpinner.setOnItemSelectedListener(this);
        destinationLocationSpinner.setOnItemSelectedListener(this);
        onNavigateClick();
    }

    private void initialize(){
        starLocationSpinner = (Spinner) findViewById(R.id.ConfigSpinnerStart);
        destinationLocationSpinner = (Spinner) findViewById(R.id.ConfigDestinationSpinner);
        findMeButton = (Button) findViewById(R.id.ConfigFindMeButton);
        StartNavigationButton = (Button) findViewById(R.id.ConfigStartButoon);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner1 = (Spinner) parent;
        Spinner spinner2 = (Spinner) parent;
        if (spinner1.getId() == R.id.ConfigSpinnerStart){
            if(pois!=null){
                for (PointOfInterest po : pois){
                    if(po.getName().equals(parent.getItemAtPosition(position))){
                        startLocation = po;
                    }
                }
            }
        }
        if(spinner2.getId() == R.id.ConfigDestinationSpinner){
            if(pois!=null){
                for (PointOfInterest po : pois){
                    if(po.getName().equals(parent.getItemAtPosition(position))){
                        destinationLocation = po;
                    }
                }
            }
        }


    }
    public void onFindMeClick(){
        findMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void onNavigateClick(){
        StartNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavRunner navRunner = new NavRunner();
                Nav nav = new Nav(startLocation.getLat(), startLocation.getLon(), destinationLocation.getLat(), destinationLocation.getLon());
                navRunner.execute(nav);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Start", startLocation).putExtra("Destination", destinationLocation);
                startActivity(intent);
            }
        });
    }

    private class PoiRunner extends AsyncTask<String, String,String> {


        @Override
        protected String doInBackground(String... strings) {
            ApiService apiService = new ApiService();
            return apiService.getRequest(strings[0]);
        }
        @Override
        protected void onPostExecute(String returnValue){
            System.out.println(returnValue);
            ArrayList<String> poiName = new ArrayList<>();
            try {
                pois = new  ObjectMapper().readValue(returnValue, new TypeReference<List<PointOfInterest>>() {});
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
            destinationLocationSpinner.setAdapter(adapter);
        }

    }
    class NavRunner extends AsyncTask<Nav, Double,String>{

        @Override
        protected String doInBackground(Nav... doubles) {
            System.out.println(doubles[0]);
            ApiService apiService = new ApiService();
            try {
                System.out.println(apiService.NavInstructions("nav", doubles[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
