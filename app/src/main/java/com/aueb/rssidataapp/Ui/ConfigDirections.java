package com.aueb.rssidataapp.Ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aueb.rssidataapp.FetchPointsOfInterest;
import com.aueb.rssidataapp.R;
import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.aueb.rssidataapp.Triangulation.Nav;
import com.aueb.rssidataapp.Triangulation.PointOfInterest;
import com.aueb.rssidataapp.Triangulation.Position;
import com.aueb.rssidataapp.Triangulation.Triangulate;
import com.aueb.rssidataapp.WifiBroadCastReceiver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfigDirections extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, Serializable {
    private Spinner destinationLocationSpinner;
    private Button findMeButton, StartNavigationButton;
    private List<PointOfInterest> pois = null;
    private PointOfInterest destinationLocation = new PointOfInterest();
    private PointOfInterest startLocation = new PointOfInterest();
    private List<ScanResult> availableAccessPoints = new ArrayList<>();
    private TextView textViewX;
    private TextView textViewY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_directions);
        initialize();
        FetchPointsOfInterest runner = new FetchPointsOfInterest();
        runner.execute("poi");
        destinationLocationSpinner.setOnItemSelectedListener(this);
        onNavigateClick();
        onClickFindMe();
        checkPermissions();
    }

    private void initialize(){
        destinationLocationSpinner = (Spinner) findViewById(R.id.ConfigDestinationSpinner);
        findMeButton = (Button) findViewById(R.id.ConfigFindMeButton);
        StartNavigationButton = (Button) findViewById(R.id.ConfigStartButoon);
        textViewX = (TextView) findViewById(R.id.MyTextViewX);
        textViewY = (TextView) findViewById(R.id.MyTextViewY);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.ConfigDestinationSpinner) {
            if (pois == null) {
                System.out.println("Pois = 0");
                return;
            }
            for (PointOfInterest po : pois) {
                if (po.getName().equals(parent.getItemAtPosition(position))) {
                    System.out.println(po.toString());
                    destinationLocation = po;
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void onNavigateClick(){
        StartNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("startLocation: " + startLocation.toString());
                System.out.println("destination: " + destinationLocation.toString());
                // Nav nav = new Nav(startLocation.getLat(), startLocation.getLon(), destinationLocation.getLat(), destinationLocation.getLon());
                Nav nav = new Nav(startLocation.getLat(), startLocation.getLon(),
                        38.00768329148, 23.71644625435);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("nav", nav);
                startActivity(intent);
            }
        });
    }

    public void onClickFindMe() {
        findMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWifiScan();
            }
        });
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 1);
        }
    }

    private void startWifiScan() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        } else {
            final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiBroadCastReceiver wifiScanner = new WifiBroadCastReceiver(wifiManager, this::updateAvailableAccessPoints);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            getApplicationContext().registerReceiver(wifiScanner, intentFilter);
            boolean success = wifiManager.startScan();
            if (!success) {
                System.out.println("Something when't wrong, probably permission where not given");
            } else {
                System.out.println("Scanning is allowed");
            }
        }
    }

    public void updateAvailableAccessPoints(List<ScanResult> newAccessPoints) {
        addNewAvailableAccessPoints(newAccessPoints);
        List<AccessPoint> accessPoints = filterAccessPoints();
        TriangulatePossition(accessPoints);
    }

    private void addNewAvailableAccessPoints(List<ScanResult> newAccessPoints) {
        if (availableAccessPoints != null) {
            availableAccessPoints.clear();
        }
        availableAccessPoints.addAll(newAccessPoints);
    }

    private List<AccessPoint> filterAccessPoints() {
        AccessPointUtil accessPointUtil = new AccessPointUtil();
        return accessPointUtil.converter(availableAccessPoints);
    }

    private void TriangulatePossition(List<AccessPoint> accessPoints) {
        if (accessPoints.size() <= 2) {
            startLocation.setLat(38.00765939044);
            System.out.println(startLocation);
            startLocation.setLon(23.71638813776);
            startLocation.setName("current position");
            textViewX.setText("default: " + 38.00765939044);
            textViewY.setText("default: " + 23.71638813776);
            System.out.println("not enougth access Points");
            Toast.makeText(this, "Not enouthg access points", Toast.LENGTH_LONG).show();
            return;
        }
        Triangulate triangulate = new Triangulate();
        Position position = triangulate.getPossition(accessPoints);
        textViewX.setText(String.valueOf(position.getLat()));
        textViewX.setText(String.valueOf(position.getLon()));
        startLocation.setName("current position");
        startLocation.setLat(position.getLat());
        startLocation.setLon(position.getLon());
    }
}
