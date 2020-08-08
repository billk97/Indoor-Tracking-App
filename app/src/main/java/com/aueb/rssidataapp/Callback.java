package com.aueb.rssidataapp;

import com.aueb.rssidataapp.Triangulation.AccessPoint;

public interface Callback {
    void addKnownAccessPoint(AccessPoint newAccessPoint);

    // void updateAvailableAccessPoints(java.util.List<android.net.wifi.ScanResult> results);
}
