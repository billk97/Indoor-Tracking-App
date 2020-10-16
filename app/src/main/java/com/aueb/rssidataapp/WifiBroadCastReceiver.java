package com.aueb.rssidataapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.aueb.rssidataapp.Triangulation.AccessPoint;
import com.aueb.rssidataapp.Triangulation.Position;
import com.aueb.rssidataapp.Triangulation.Triangulate;
import com.aueb.rssidataapp.Ui.AccessPointUtil;

import java.io.IOException;
import java.util.List;

import lombok.SneakyThrows;

public class WifiBroadCastReceiver extends BroadcastReceiver {
    WifiManager wifiManager;
    WifiAccessPointCallback callback;

    public WifiBroadCastReceiver(WifiManager wifiManager, WifiAccessPointCallback callback) {
        this.wifiManager = wifiManager;
        this.callback = callback;
    }

    @SneakyThrows
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
        if (!success) {
            System.out.println("Scanning failed");
            return;
        }
        List<ScanResult> results = wifiManager.getScanResults();
        List<AccessPoint> accessPoints = getKnownAvailableAccessPoints(results);
        callback.getCurrentPosition(getCurrentPosition(accessPoints));
    }

    private List<AccessPoint> getKnownAvailableAccessPoints(List<ScanResult> newAccessPoints) throws IOException {
        if (newAccessPoints.isEmpty()) {
            System.out.println("Empty list");
            throw new IOException("Empty list");
        }
        AccessPointUtil accessPointUtil = new AccessPointUtil();
        return accessPointUtil.convertToKnowAccessPoints(newAccessPoints);
    }

    private Position getCurrentPosition(List<AccessPoint> availableAccessPoints) {
        if (availableAccessPoints == null || availableAccessPoints.size() < 3) {
            System.out.println("access Points for triangulation les than 3");
        }
        Triangulate triangulate = new Triangulate();
        return triangulate.getPossition(availableAccessPoints);
    }
}
