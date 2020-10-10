package com.aueb.rssidataapp.Scan;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.aueb.rssidataapp.Triangulation.Position;
import com.aueb.rssidataapp.Triangulation.Triangulate;
import com.aueb.rssidataapp.Ui.AccessPointUtil;
import com.aueb.rssidataapp.WifiBroadCastReceiver;

import java.util.ArrayList;
import java.util.List;

public class ScanService {
    private List<AccessPoint> availableAccessPoints = new ArrayList<>();

    public void startScan(Context context) {
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiBroadCastReceiver wifiScanner = new WifiBroadCastReceiver(wifiManager, this::updateAvailableWifiScanResults);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiScanner, intentFilter);
        boolean success = wifiManager.startScan();
        if (!success) {
            System.out.println("Something when't wrong, probably permission where not given");
        } else {
            System.out.println("Scanning is allowed");
        }
    }

    private void updateAvailableWifiScanResults(List<ScanResult> newAccessPoints) {
        AccessPointUtil accessPointUtil = new AccessPointUtil();
        if (!availableAccessPoints.isEmpty()) {
            availableAccessPoints.clear();
        }
        availableAccessPoints = accessPointUtil.convertToKnowAccessPoints(newAccessPoints);
    }

    public List<AccessPoint> getAvailableAccessPoints() {
        return availableAccessPoints;
    }

    private Position currentPosition() {
        if (availableAccessPoints == null || availableAccessPoints.size() < 3) {
            System.out.println("access Points for triangulation les than 3");
        }
        Triangulate triangulate = new Triangulate();
        return triangulate.getPossition(availableAccessPoints);
    }

}
