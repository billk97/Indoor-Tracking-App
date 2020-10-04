package com.aueb.rssidataapp;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.aueb.rssidataapp.Triangulation.Position;
import com.aueb.rssidataapp.Triangulation.Triangulate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Callback, WifiAccessPointCallback {
    private ListView simpleList;
    private TextView MainActivityTextViewX;
    private TextView MainActivityTextViewY;
    private Button MainActivityButton;
    private HashMap<String, AccessPoint> knownAccessPoint = new HashMap();
    private List<ScanResult> availableAccessPoints = new ArrayList<>();
    List<AccessPoint> accessPointsList = new ArrayList<>();

    //todo send location to navDemo
    //todo cleanUp and break to classes

    @RequiresApi(api = Build.VERSION_CODES.Q)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
        checkPermissions();
        InitAccessPoints();


        MainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }else{

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

    private void initializer(){
        simpleList = (ListView) findViewById(R.id.mylist);
        MainActivityTextViewX = (TextView) findViewById(R.id.MainActivityTextViewX);
        MainActivityTextViewY = (TextView) findViewById(R.id.MainActivityTextViewY);
        MainActivityButton = (Button) findViewById(R.id.MainActivityButton);
    }

    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},3);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_WIFI_STATE},1);
        }
    }


    public void addKnownAccessPoint(AccessPoint newAccessPoint) {
        knownAccessPoint.put(newAccessPoint.getBssid(), newAccessPoint);
    }

    public void updateAvailableAccessPoints(List<ScanResult> newAccessPoints) {
        if (availableAccessPoints != null) {
            availableAccessPoints.clear();
        }
        System.out.println(newAccessPoints.size());
        availableAccessPoints.addAll(newAccessPoints);
        System.out.println("2===> " + availableAccessPoints.size());
        updateAccessPointsList();
    }

    private void updateAccessPointsList() {
        List<String> knownAvailableAccessPoints = new ArrayList<>();
        if (availableAccessPoints == null) {
            return;
        }
        for (ScanResult scanResult : availableAccessPoints) {
            if (knownAccessPoint.containsKey(scanResult.BSSID)) {
                AccessPoint ap = new AccessPoint(scanResult.SSID, scanResult.BSSID, scanResult.level, -19, 4.5);
                ap.setX(knownAccessPoint.get(scanResult.BSSID).getX());
                ap.setY(knownAccessPoint.get(scanResult.BSSID).getY());
                knownAvailableAccessPoints.add("bssid: " + ap.getBssid() + "\n distance: " + ap.CalculateDistance() + "\n lever: " + ap.getLevel());
                accessPointsList.add(ap);
            }
        }
        displayKnownAvailableAccessPoints(knownAvailableAccessPoints);
        callculatePosition();
    }

    private void displayKnownAvailableAccessPoints(List<String> knownAvailableAccessPoints) {

        simpleList.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.listview, R.id.textView, knownAvailableAccessPoints));
    }

    private void callculatePosition() {
        if (accessPointsList.size() > 2) {
            Triangulate tr = new Triangulate();
            Position position = tr.getPossition(accessPointsList);
            MainActivityTextViewX.setText(String.valueOf(position.getLat()));
            MainActivityTextViewY.setText(String.valueOf(position.getLon()));
        }
    }


    private void InitAccessPoints() {
        AccessPoint ap = new AccessPoint("ssid", "dc:a6:32:29:d9:8c", -19, 4.5);
        ap.setX(2);
        ap.setY(2.5);
        knownAccessPoint.put(ap.getBssid(), ap);

        AccessPoint ap1 = new AccessPoint("ssid", "dc:a6:32:2a:19:35", -19, 4.5);
        ap1.setX(2.7);
        ap1.setY(0.6);
        knownAccessPoint.put(ap1.getBssid(), ap1);

        AccessPoint ap2 = new AccessPoint("ssid", "dc:a6:32:26:cf:29", -19, 4.5);
        ap2.setX(6.2);
        ap2.setY(0.6);
        knownAccessPoint.put(ap2.getBssid(), ap2);

        AccessPoint ap3 = new AccessPoint("ssid", "dc:a6:32:2a:04:11", -19, 4.5);
        ap3.setX(5.1);
        ap3.setY(5.1);
        knownAccessPoint.put(ap3.getBssid(), ap3);
        FetchAccessPointList runner = new FetchAccessPointList(this::addKnownAccessPoint);
        runner.execute();

    }
}
