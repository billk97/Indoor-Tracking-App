package com.aueb.rssidataapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.aueb.rssidataapp.Triangulation.Triangulate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**This class handles the Scan**/
public class ScanBroadCastReceiver extends BroadcastReceiver {
    List<ScanResult> results = null;// a list with the scan results
    @Override
    public void onReceive(Context context, Intent intent) {
        WifiManager wifiManager =(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
        if (success) {
            Date date = new Date();
            System.out.println(date);
            Log.i("scannTime",date.toString());
            System.out.println("scanning");
            results = wifiManager.getScanResults();
            intent.putExtra("WifiScanResults", (Serializable) results);
        }
        else{
            System.out.println("Scanning failed");
        }

    }
}
