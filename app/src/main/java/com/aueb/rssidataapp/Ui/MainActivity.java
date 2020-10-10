package com.aueb.rssidataapp.Ui;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aueb.rssidataapp.Connection.ApiService;
import com.aueb.rssidataapp.R;
import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.aueb.rssidataapp.Triangulation.Instraction;
import com.aueb.rssidataapp.Triangulation.InstractionSets;
import com.aueb.rssidataapp.Triangulation.Nav;
import com.aueb.rssidataapp.Triangulation.Position;
import com.aueb.rssidataapp.Triangulation.Triangulate;
import com.aueb.rssidataapp.WifiAccessPointCallback;
import com.aueb.rssidataapp.WifiBroadCastReceiver;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity implements WifiAccessPointCallback {
    private ListView simpleList;
    private TextView MainActivityTextViewX;
    private TextView MainActivityTextViewY;
    private Nav nav;
    private List<ScanResult> availableAccessPoints = new ArrayList<>();
    private ApiService apiService = new ApiService();
    private String navInstractions;
    List<AccessPoint> accessPointsList = new ArrayList<>();

    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.Q)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nav = (Nav) getIntent().getSerializableExtra("nav");
        }
        ApiRunner apiRunner = new ApiRunner();
        apiRunner.execute();
        checkPermissions();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }else{

            final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiBroadCastReceiver wifiScanner = new WifiBroadCastReceiver(wifiManager, this::updateAvailableWifiScanResults);

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
    }

    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},3);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_WIFI_STATE},1);
        }
    }

    private void updateAccessPointsList() {
        callculatePosition();
    }

    private void displayKnownAvailableAccessPoints(List<String> knownAvailableAccessPoints) {

        // simpleList.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.listview, R.id.textView, knownAvailableAccessPoints));
    }

    private void callculatePosition() {
        if (accessPointsList.size() > 2) {
            Triangulate tr = new Triangulate();
            Position position = tr.getPossition(accessPointsList);
            MainActivityTextViewX.setText(String.valueOf(position.getLat()));
            MainActivityTextViewY.setText(String.valueOf(position.getLon()));
        }
    }

    private String getDirections() throws IOException {
        System.out.println("start.getLat(): " + nav.getDestLat());
        System.out.println("+++++++++++++++++++++++++++++++++++++");
        System.out.println(nav);
        navInstractions = apiService.navInstructions("nav", nav);
        return navInstractions;
    }

    private void navInstractionsToList(String navInstractions) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InstractionSets instractionSets = objectMapper.readValue(navInstractions, InstractionSets.class);
        List<String> list = new ArrayList<>();
        for (Instraction instraction : instractionSets.getInstructions()) {
            list.add(instraction.toString());
        }
        simpleList.setAdapter(
                new ArrayAdapter(getApplicationContext(), R.layout.listview, R.id.textView, list)
        );
    }

    @Override
    public void updateAvailableWifiScanResults(List<ScanResult> newAccessPoints) {

    }

    class ApiRunner extends AsyncTask<Nav, Double, String> {

        @SneakyThrows
        @Override
        protected String doInBackground(Nav... navs) {
            return getDirections();
        }

        @SneakyThrows
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            navInstractionsToList(s);
        }
    }
}
