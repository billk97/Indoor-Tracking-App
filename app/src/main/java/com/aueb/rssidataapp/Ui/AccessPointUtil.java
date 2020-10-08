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

    AccessPointUtil() {
        InitAccessPoints();
    }

    public List<AccessPoint> converter(List<ScanResult> availableAccessPoints) {
        List<AccessPoint> accessPointsList = new ArrayList<>();
        if (availableAccessPoints == null) {
            throw new IllegalStateException("ScanResult list was  empty");
        }
        for (ScanResult scanResult : availableAccessPoints) {
            if (knownAccessPoint.containsKey(scanResult.BSSID)) {
                AccessPoint ap = new AccessPoint(scanResult.SSID, scanResult.BSSID, scanResult.level, -19, 4.5);
                ap.setX(knownAccessPoint.get(scanResult.BSSID).getX());
                ap.setY(knownAccessPoint.get(scanResult.BSSID).getY());
                accessPointsList.add(ap);
            }
        }
        return accessPointsList;
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
