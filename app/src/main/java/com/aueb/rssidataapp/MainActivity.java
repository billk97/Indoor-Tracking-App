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
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.aueb.rssidataapp.Triangulation.Position;
import com.aueb.rssidataapp.Triangulation.Triangulate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView simpleList;
    private TextView MainActivityTextViewX;
    private TextView MainActivityTextViewY;
    private Button MainActivityButton;
    private HashMap<String, AccessPoint> knownAccessPoint = new HashMap();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
        checkPermissions();
        InitAccessPoints();

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
                        System.out.println("scanning");
                        List<ScanResult> results = wifiManager.getScanResults();// a list with the scan results
                        List<AccessPoint> accessPointsList = new ArrayList<>();
                        List<String> bssid = new ArrayList<>(); // creating a list to display at list view
                        for (ScanResult result : results) {
                            Log.i("wifiscann",result.BSSID);
                            Log.i("wifiscann",String.valueOf(result.level));
                            Log.i("wifiscann", String.valueOf(result.level));
                            /** creating a object type AccessPoint for each scan result **/
                            AccessPoint ap = new AccessPoint(result.SSID, result.BSSID, result.level, -30, 4.5);
                            System.out.println("distance: " + ap.CalculateDistance());
                            if(knownAccessPoint.containsKey(ap.getBssid())){
                                ap.setX(knownAccessPoint.get(ap.getBssid()).getX());
                                ap.setY(knownAccessPoint.get(ap.getBssid()).getY());
                                accessPointsList.add(ap);
                            }
                            bssid.add("bssid: "+ result.BSSID+ "\n ssid: "+ result.SSID + " \nLEVEL: "+ result.level + " \ndist: "+ ap.CalculateDistance());
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.listview, R.id.textView, bssid);
                        simpleList.setAdapter(arrayAdapter);
                        for(AccessPoint ap : accessPointsList){
                            System.out.println(ap.getBssid() +" " + ap.getX() +" "+ ap.getY()+" "+ap.CalculateDistance());
                        }
                        Triangulate tr = new Triangulate();
                        Position position = tr.getPossition(accessPointsList);
                        MainActivityTextViewX.setText(String.valueOf(position.getX()));
                        MainActivityTextViewY.setText(String.valueOf(position.getY()));

                    }
                    else{
                        System.out.println("Scanning failed");
                    }
                }
            };
            /**The broadcast receiver is cast in a Intent and requires the creation of a new Intent to be created**/
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
        AccessPoint ap = new AccessPoint("ssid","4a:a0:5b:53:c6:e7",-30,3 );
        ap.setX(0);
        ap.setY(0);
        knownAccessPoint.put(ap.getBssid(),ap);

        AccessPoint ap1 = new AccessPoint("ssid","b8:27:eb:88:09:a2",-30,3 );
        ap1.setX(-2.1);
        ap1.setY(0);
        knownAccessPoint.put(ap1.getBssid(),ap1);

        AccessPoint ap2 = new AccessPoint("ssid","70:3a:51:1b:97:94",-30,3 );
        ap2.setX(-0.5);
        ap2.setY(-2.3);
        knownAccessPoint.put(ap2.getBssid(),ap2);
    }
}
