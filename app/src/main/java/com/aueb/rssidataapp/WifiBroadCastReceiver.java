package com.aueb.rssidataapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

public class WifiBroadCastReceiver extends BroadcastReceiver {

    WifiManager wifiManager;
    WifiAccessPointCallback callback;

    public WifiBroadCastReceiver(WifiManager wifiManager, WifiAccessPointCallback callback) {
        this.wifiManager = wifiManager;
        this.callback = callback;
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
        if (!success) {
            System.out.println("Scanning failed");
            return;
        }
        List<ScanResult> results = wifiManager.getScanResults();
        for (ScanResult scanResult : results) {
            System.out.println(scanResult.toString());
        }
        callback.updateAvailableAccessPoints(results);
    }
}
