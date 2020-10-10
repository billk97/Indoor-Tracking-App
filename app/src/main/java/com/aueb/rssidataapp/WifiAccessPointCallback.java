package com.aueb.rssidataapp;

import android.net.wifi.ScanResult;

import java.util.List;

public interface WifiAccessPointCallback {
    public void updateAvailableWifiScanResults(List<ScanResult> newAccessPoints);
}
