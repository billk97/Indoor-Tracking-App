package com.aueb.rssidataapp.Ui;


import android.net.wifi.ScanResult;

import com.aueb.rssidataapp.Callback;
import com.aueb.rssidataapp.FetchAccessPointList;
import com.aueb.rssidataapp.Triangulation.AccessPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccessPointUtil implements Callback {
    private HashMap<String, AccessPoint> knownAccessPoint = new HashMap();

    public AccessPointUtil() {
        InitAccessPoints();
    }

    public List<AccessPoint> convertToKnowAccessPoints(List<ScanResult> availableAccessPoints) {
        List<AccessPoint> accessPointsList = new ArrayList<>();
        if (availableAccessPoints == null) {
            throw new IllegalStateException("ScanResult list was  empty");
        }
        availableAccessPoints = filterScanResults(availableAccessPoints);
        for (ScanResult scanResult : availableAccessPoints) {
            AccessPoint ap = new AccessPoint(
                    scanResult.SSID, scanResult.BSSID, scanResult.level, -19, 4.5,
                    knownAccessPoint.get(scanResult.BSSID).getX(),
                    knownAccessPoint.get(scanResult.BSSID).getY()
            );
                accessPointsList.add(ap);
        }
        return accessPointsList;
    }

    public List<ScanResult> filterScanResults(List<ScanResult> newScanResults) {
        if (newScanResults.isEmpty()) {
            System.out.println("==> New scan results are empty <==");
            throw new IllegalArgumentException("New scan results are empty");
        }
        List<ScanResult> filteredScanResults = new ArrayList<>();
        for (ScanResult scanResult : newScanResults) {
            if (knownAccessPoint.containsKey(scanResult.BSSID)) {
                filteredScanResults.add(scanResult);
            }
        }
        return filteredScanResults;
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

    public void addKnownAccessPoint(AccessPoint newAccessPoint) {
        knownAccessPoint.put(newAccessPoint.getBssid(), newAccessPoint);
    }
}
