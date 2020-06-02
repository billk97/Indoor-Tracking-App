package com.aueb.rssidataapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.aueb.rssidataapp.Connection.ConnectionHandler;
import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.aueb.rssidataapp.Triangulation.Position;
import com.aueb.rssidataapp.Triangulation.Triangulate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView simpleList;
    private TextView MainActivityTextViewX;
    private TextView MainActivityTextViewY;
    private Button MainActivityButton;
    private HashMap<String, AccessPoint> knownAccessPoint = new HashMap();
    //todo send location to navDemo
    //todo cleanUp and break to classes

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
        checkPermissions();
        InitAccessPoints();
        final List<AccessPoint> accessPointsList = new ArrayList<>();

        MainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }else{
            /**WifiManager is responsible for all the handling wifi related **/
            final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            /**The system send a broadcast message to all apps that are subscribed when a event happens
             * in our case when new scan results are available is performed by the system**/
            BroadcastReceiver wifiScanner = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
                    if (success) {
                        accessPointsList.clear();
                        Date date = new Date();
                        System.out.println(date);
                        Log.i("scannTime",date.toString());
                        System.out.println("scanning");
                        List<ScanResult> results = wifiManager.getScanResults();// a list with the scan results

                        List<String> bssid = new ArrayList<>(); // creating a list to display at list view
                        for (ScanResult result : results) {
                            if(knownAccessPoint.containsKey(result.BSSID)){
                                /** creating a object type AccessPoint for each scan result **/
                                AccessPoint ap = new AccessPoint(result.SSID, result.BSSID, result.level, -19, 4.5);
                                System.out.println("Ap: " +ap);
                                System.out.println("bssid: " +ap.getBssid()+" distance: " + ap.CalculateDistance());
                                System.out.println("knownAccessPoint.get(ap.getBssid()).getX(): " + knownAccessPoint.get(result.BSSID).getX());
                                System.out.println("knownAccessPoint.get(ap.getBssid()).getY(): " + knownAccessPoint.get(result.BSSID).getY());
                                ap.setX(knownAccessPoint.get(result.BSSID).getX());
                                ap.setY(knownAccessPoint.get(result.BSSID).getY());
                                System.out.println("x: "+ ap.getX());
                                System.out.println("Y: "+ ap.getY());
                                bssid.add("bssid: " +ap.getBssid()+"\n distance: " + ap.CalculateDistance() + "\n lever: " +ap.getLevel() );
                                accessPointsList.add(ap);
                                System.out.println("=====================");
                            }
                        }
                        if(accessPointsList.size()>2){
                            Triangulate tr = new Triangulate();
                            Position position = tr.getPossition(accessPointsList);
                            MainActivityTextViewX.setText(String.valueOf(position.getX()));
                            MainActivityTextViewY.setText(String.valueOf(position.getY()));
                        }
                        simpleList.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.listview, R.id.textView, bssid));
                    }
                    else{
                        System.out.println("Scanning failed");
                    }
                }
            };
            /**The broadcast receiver is cast in a Intent and requires the creation of a new Intent to be created**/
            //BroadcastReceiver brclass= new ScanBroadCastReceiver();
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
    /**From android 6 and later the user has the ability to revoke the permissions the app has
     * requested in the first install as such each time some action is made that requires permission
     * it must first check if permissions are stile granted**/
    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},3);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_WIFI_STATE},1);
        }
    }

    private void InitAccessPoints(){
        AccessPoint ap = new AccessPoint("ssid","dc:a6:32:29:d9:8c",-19,4.5 );
        ap.setX(2);
        ap.setY(2.5);
        knownAccessPoint.put(ap.getBssid(),ap);

        AccessPoint ap1 = new AccessPoint("ssid","dc:a6:32:2a:19:35",-19,4.5);
        ap1.setX(2.7);
        ap1.setY(0.6);
        knownAccessPoint.put(ap1.getBssid(),ap1);

        AccessPoint ap2 = new AccessPoint("ssid","dc:a6:32:26:cf:29",-19,4.5 );
        ap2.setX(6.2);
        ap2.setY(0.6);
        knownAccessPoint.put(ap2.getBssid(),ap2);

        AccessPoint ap3 = new AccessPoint("ssid","dc:a6:32:2a:04:11",-19,4.5 );
        ap3.setX(5.1);
        ap3.setY(5.1);
        knownAccessPoint.put(ap3.getBssid(),ap3);
       // AsyncTaskRunner runner = new AsyncTaskRunner();
        //runner.execute();

    }
    private class AsyncTaskRunnerSendLoc extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            ConnectionHandler connectionHandler = new ConnectionHandler();
            connectionHandler.sendLocation(strings[0],Double.valueOf(strings[1]),Double.valueOf(strings[2]));
            return null;
        }
    }
    private class AsyncTaskRunner extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            ConnectionHandler connectionHandler = new ConnectionHandler();
            List<AccessPoint> accessPoints =null;
            try {
                accessPoints = new ObjectMapper().readValue(connectionHandler.getRequest("access-point"), new TypeReference<List<AccessPoint>>() {});
                System.out.println("ok");

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            for (AccessPoint accessPoint : accessPoints){
                knownAccessPoint.put(accessPoint.getBssid(),accessPoint);
            }
            return null;
        }
    }
}
