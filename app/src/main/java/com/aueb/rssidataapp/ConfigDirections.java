package com.aueb.rssidataapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

public class ConfigDirections extends AppCompatActivity {
    private Spinner starLocationSpinner, destinationLocationSpinner;
    private Button findMeButton, StartNavigationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_directions);
        initialize();
    }

    private void initialize(){
        starLocationSpinner = (Spinner) findViewById(R.id.ConfigSpinnerStart);
        destinationLocationSpinner = (Spinner) findViewById(R.id.ConfigDestinationSpinner);
        findMeButton = (Button) findViewById(R.id.ConfigFindMeButton);
        StartNavigationButton = (Button) findViewById(R.id.ConfigStartButoon);
    }
}
